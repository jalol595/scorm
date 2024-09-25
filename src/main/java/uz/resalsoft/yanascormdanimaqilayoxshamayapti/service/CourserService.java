package uz.resalsoft.yanascormdanimaqilayoxshamayapti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import uz.resalsoft.yanascormdanimaqilayoxshamayapti.dto.CourseDTO;
import uz.resalsoft.yanascormdanimaqilayoxshamayapti.dto.ResponseDTO;
import uz.resalsoft.yanascormdanimaqilayoxshamayapti.entity.Course;
import uz.resalsoft.yanascormdanimaqilayoxshamayapti.exeption.CheckCourseDetailsException;
import uz.resalsoft.yanascormdanimaqilayoxshamayapti.exeption.CourseFileNotFoundException;
import uz.resalsoft.yanascormdanimaqilayoxshamayapti.exeption.CourseNotFoundException;
import uz.resalsoft.yanascormdanimaqilayoxshamayapti.repository.CourseRepository;
import uz.resalsoft.yanascormdanimaqilayoxshamayapti.utils.ScormHelper;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CourserService {


    @Autowired
    private CourseRepository courseFileRepository;

    @Autowired
    private ScormHelper scormHelper;

    @Value("${scorm.storage.path}")
    private String scormStoragePath;


    public ResponseDTO<String> uploadScormPackage(@RequestParam("file") MultipartFile file,
                                                  @RequestParam String courseName) {
        try {
            // SCORM paketini saqlash va qayta ishlash
            String uniquePackageName = processScormPackage(file);

            // Kursni yaratish va ma'lumotlar omboriga saqlash
            saveCourseToDatabase(uniquePackageName, courseName);

            // Muvaffaqiyatli javob qaytarish
            return new ResponseDTO<>("SCORM package uploaded and extracted successfully.", uniquePackageName, true);
        } catch (IOException e) {
            // Xato holatida javob qaytarish
            return new ResponseDTO<>(e.getMessage(), null, false);
        }
    }


    /*public Resource getIndexHtmlFile() {
        try {
            // Construct the file path
            Path filePathFull = Paths.get("html/index.html").toAbsolutePath().normalize();
            File file = filePathFull.toFile();

            // Ensure the file exists
            if (!file.exists()) {
                return (Resource) new ResponseDTO<Resource>(ResponseStatus.NOT_FOUND, ResponseStatus.NOT_FOUND.name());
            }

            // Create a resource for the file
            Resource resource = new UrlResource(file.toURI());

            // Determine content type
            String contentType = Files.probeContentType(filePathFull);

            return (Resource) new ResponseDTO<Resource>(ResponseStatus.OK, ResponseStatus.OK.name());


        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
*/


/*
    public ResponseDTO<Resource> getResourceFile1(String courseId, String filePath) {
        try {
            // Fayl yo'lini to'liq tuzish
            Path filePathFull = Paths.get(scormStoragePath, courseId, filePath).toAbsolutePath().normalize();
            File file = filePathFull.toFile();

            // Fayl mavjudligini tekshirish
            if (!file.exists()) {
                return new ResponseDTO<>("Fayl topilmadi", null, false);
            }

            // Fayl resursini yaratish
            Resource resource = new UrlResource(file.toURI());

            return new ResponseDTO<>("Fayl muvaffaqiyatli yuklandi.", resource, true);
        } catch (MalformedURLException e) {
            return new ResponseDTO<>("Fayl URL xatosi: " + e.getMessage(), null, false);
        } catch (IOException e) {
            return new ResponseDTO<>("Faylni yuklashda xato yuz berdi: " + e.getMessage(), null, false);
        }
    }
*/


 /*   public Resource getResourceFile(String courseId, String filePath) throws MalformedURLException, IOException {
        // Fayl yo'lini to'liq tuzish
        Path filePathFull = Paths.get(scormStoragePath, courseId, filePath).toAbsolutePath().normalize();
        File file = filePathFull.toFile();

        // Fayl mavjudligini tekshirish
        if (!file.exists()) {
            throw new IllegalArgumentException("Fayl topilmadi");
        }

        // Fayl resursini yaratish
        return new UrlResource(file.toURI());
    }*/

    public Resource getResourceFile(String courseId, String filePath) throws MalformedURLException, IOException {

        if (!findCourse(courseId)) {
            throw new CourseNotFoundException("course not found");
        }

        // Fayl yo'lini to'liq tuzish
        Path filePathFull = Paths.get(scormStoragePath, courseId, filePath).toAbsolutePath().normalize();
        File file = filePathFull.toFile();

        // Fayl mavjudligini tekshirish
        if (!file.exists()) {
            throw new CourseFileNotFoundException("Fayl topilmadi");
        }

        // Fayl resursini yaratish
        return new UrlResource(file.toURI());
    }


    public String determineContentType(Path filePath) throws IOException {
        // Faylning kontent turini aniqlash
        return Files.probeContentType(filePath);
    }


    public List<CourseDTO> getAllCourses() {
        return StreamSupport.stream(courseFileRepository.findAll().spliterator(), false)
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    public CourseDTO getCoursesById(String id) {
        return courseFileRepository.findById(id)
                .map(this::mapToDTO)  // Agar kurs topilsa, DTO ga o'tkazish
                .orElseThrow(() -> new CourseNotFoundException("Course not found for id: " + id));  // Agar topilmasa, xato qaytarish
    }

    public ResponseDTO<List<CourseDTO>> getCoursesAndStudents() {
        // Fetch the raw data from the repository
        List<Object[]> allCourseFilesWithProcesses = courseFileRepository.findAllCourseFilesWithProcesses();

        // Process the raw result into a list of CourseDTO objects
        List<CourseDTO> courses = allCourseFilesWithProcesses.stream()
                .map(objects -> {
                    CourseDTO courseDTO = new CourseDTO();

                    // Assuming the order of the columns in the query corresponds to this structure:
                    courseDTO.setId((String) objects[0]);                          // Course ID
                    courseDTO.setCourseName((String) objects[1]);                // Course Name
                    courseDTO.setCourseOwner((String) objects[2]);               // Course Owner

                    // Handling the conversion from java.sql.Timestamp to java.time.LocalDateTime
                    Timestamp timestamp = (Timestamp) objects[3];                // Created Date (as Timestamp)
                    courseDTO.setCreatedDate(timestamp.toLocalDateTime());       // Convert to LocalDateTime

                    courseDTO.setStudentId((String) objects[4]);                 // Student ID from the course_process table

                    return courseDTO;
                })
                .collect(Collectors.toList());

        // Wrapping the result in a ResponseDTO
        return new ResponseDTO<>("", courses, true);
    }

    public ResponseDTO<List<CourseDTO>> getCoursesByStudentsId(String studentId) {
        // Fetch the raw data from the repository
        List<Object[]> allCourseFilesWithProcesses = courseFileRepository.findAllCourseFilesWithProcessesByStudentId(studentId);

        // Process the raw result into a list of CourseDTO objects
        List<CourseDTO> courses = allCourseFilesWithProcesses.stream()
                .map(objects -> {
                    CourseDTO courseDTO = new CourseDTO();

                    // Assuming the order of the columns in the query corresponds to this structure:
                    courseDTO.setId((String) objects[0]);                          // Course ID
                    courseDTO.setCourseName((String) objects[1]);                // Course Name
                    courseDTO.setCourseOwner((String) objects[2]);               // Course Owner

                    // Handling the conversion from java.sql.Timestamp to java.time.LocalDateTime
                    Timestamp timestamp = (Timestamp) objects[3];                // Created Date (as Timestamp)
                    courseDTO.setCreatedDate(timestamp.toLocalDateTime());       // Convert to LocalDateTime

                    courseDTO.setStudentId((String) objects[4]);                 // Student ID from the course_process table

                    return courseDTO;
                })
                .collect(Collectors.toList());

        // Wrapping the result in a ResponseDTO
        return new ResponseDTO<>("", courses, true);
    }

    private CourseDTO mapToDTO(Course course) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(course.getId());
        courseDTO.setCourseName(course.getCourseName());
        courseDTO.setCourseOwner(course.getCourseOwner());
        courseDTO.setCreatedDate(course.getCreatedDate());
        courseDTO.setCourseImsmanifestPath(scormHelper.getHrefFileName(course.getId()));
        return courseDTO;
    }

    private CourseDTO mapToDTOWithStudentID(Course course, String studentId) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(course.getId());
        courseDTO.setCourseName(course.getCourseName());
        courseDTO.setCourseOwner(course.getCourseOwner());
        courseDTO.setCreatedDate(course.getCreatedDate());
        courseDTO.setStudentId(studentId);
        courseDTO.setCourseImsmanifestPath(scormHelper.getHrefFileName(course.getId()));
        return courseDTO;
    }


    private String processScormPackage(MultipartFile file) throws IOException {
        String uniquePackageName = ScormHelper.generateCourseId();
        File scormDirectory = new File(scormStoragePath + uniquePackageName);

        // Create the directory if it doesn't exist
        if (!scormDirectory.exists()) {
            scormDirectory.mkdirs();
        }

        // Extract and save the files from the zip
        ScormHelper.unzip(file.getInputStream(), scormDirectory.getAbsolutePath());

        return uniquePackageName;
    }

    private void saveCourseToDatabase(String uniquePackageName, String courseName) {
        createCourse(uniquePackageName, courseName);
    }


    private String createCourse(String uniquePackageName, String courseName) {
        validateCourseDetails(uniquePackageName, courseName);

        Course course = buildCourse(uniquePackageName, courseName);

        // Save to the database
        courseFileRepository.save(course);

        return "success";
    }

    private void validateCourseDetails(String uniquePackageName, String courseName) {
        if (uniquePackageName == null || uniquePackageName.isBlank() || courseName.isBlank() || courseName == null) {
            throw new CheckCourseDetailsException("Course name and package name must not be null");
        }
    }

    private Course buildCourse(String uniquePackageName, String courseName) {
        Course course = new Course();
        course.setId(uniquePackageName);
        course.setCourseName(courseName);
        course.setCourseOwner("jalol");
        course.setCreatedDate(LocalDateTime.now());
        return course;
    }


    private boolean findCourse(String courseId) {
        return courseFileRepository
                .findById(courseId)
                .isPresent();
    }

}

