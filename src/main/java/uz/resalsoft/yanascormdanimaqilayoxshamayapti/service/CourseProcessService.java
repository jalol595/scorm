package uz.resalsoft.yanascormdanimaqilayoxshamayapti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.resalsoft.yanascormdanimaqilayoxshamayapti.dto.CourseProcessDTO;
import uz.resalsoft.yanascormdanimaqilayoxshamayapti.dto.ResponseDTO;
import uz.resalsoft.yanascormdanimaqilayoxshamayapti.entity.CourseProcess;
import uz.resalsoft.yanascormdanimaqilayoxshamayapti.exeption.CourseProcessNotFoundException;
import uz.resalsoft.yanascormdanimaqilayoxshamayapti.repository.CourseRepository;
import uz.resalsoft.yanascormdanimaqilayoxshamayapti.repository.CourseProcessRepository;
import uz.resalsoft.yanascormdanimaqilayoxshamayapti.utils.ScormHelper;

import java.util.Optional;

@Service
public class CourseProcessService {

    @Autowired
    private CourseProcessRepository courseProcessRepository;

    @Autowired
    private CourseRepository courseFileRepository;



    public ResponseDTO<CourseProcessDTO> saveCourseProcess(CourseProcessDTO courseProcessDTO) {

        // Validate course existence and student ID
        if (isInvalidCourseOrStudent(courseProcessDTO)) {
            return new ResponseDTO<>("courseId and studentId must not be null or empty", null, false);
        }

        // Check if course process already exists
        if (doesCourseProcessExist(courseProcessDTO.getCourseId(), courseProcessDTO.getStudentId())) {
            return new ResponseDTO<>("This course process already exists", null, false);
        }

        // Map DTO to entity and set a unique ID
        CourseProcess courseProcess = mapToEntity(courseProcessDTO);
        courseProcess.setId(ScormHelper.generateCourseId());

        // Save the course process
        CourseProcess savedCourseProcess = courseProcessRepository.save(courseProcess);

        // Return success response with mapped DTO
        return new ResponseDTO<>("success", mapToDTO(savedCourseProcess), true);
    }


  /*  public ResponseDTO<CourseProcessDTO> saveCourseProcess(CourseProcessDTO courseProcessDTO) {

        if (!doesCourseExist(courseProcessDTO.getCourseId()) || courseProcessDTO.getStudentId().isBlank()) {
            return new ResponseDTO<>("courseId and studentId not must be null", null, false);
        }

        if (findCourseProcess(courseProcessDTO.getCourseId(), courseProcessDTO.getStudentId())!=null) {
            return new ResponseDTO<>("Already exists this course process", null, false);
        }

        // Mapping DTO to Entity
        CourseProcess courseProcess = mapToEntity(courseProcessDTO);

        // Generate unique course process ID
        courseProcess.setId(ScormHelper.generateCourseId());

        // Processni saqlash
        CourseProcess savedCourseProcess = courseProcessRepository.save(courseProcess);

        // Mapping Entity to DTO
        return new ResponseDTO<>("success", mapToDTO(savedCourseProcess), true);
    }*/


    public ResponseDTO<CourseProcessDTO> updateCourseProcess(CourseProcessDTO courseProcessDTO) {

        // Retrieve the existing course process or return a "not found" response
        return Optional.ofNullable(findCourseProcess(courseProcessDTO))
                .map(existingCourseProcess -> {
                    // Update the existing course process with data from the DTO
                    updateCourseProcessFromDTO(existingCourseProcess, courseProcessDTO);

                    // Save the updated course process
                    CourseProcess savedCourseProcess = courseProcessRepository.save(existingCourseProcess);

                    // Return a successful response with the updated DTO
                    return new ResponseDTO<>("success", mapToDTO(savedCourseProcess), true);
                })
                .orElseGet(() -> new ResponseDTO<>("not found", null, false));
    }


  /*  public ResponseDTO<CourseProcessDTO> updateCourseProcess(CourseProcessDTO courseProcessDTO) {

        CourseProcess existingCourseProcess = findCourseProcess(courseProcessDTO);

        if (existingCourseProcess == null) {
            return new ResponseDTO<>("not found", null, false);
        }

        // Kurs jarayonini yangilash
        updateCourseProcessFromDTO(existingCourseProcess, courseProcessDTO);

        // Yangilangan jarayonni saqlash
        CourseProcess savedCourseProcess = courseProcessRepository.save(existingCourseProcess);

        return new ResponseDTO<>("success", mapToDTO(savedCourseProcess), true);
    }*/




/*    public CourseProcessDTO updateCourseProcess(CourseProcessDTO courseProcessDTO) {

        CourseProcess fromDatabaseCourseProcess = courseProcessRepository
                .findByCourseIdAndStudentId(courseProcessDTO.getCourseId(), courseProcessDTO.getStudentId())
                .orElseThrow(() -> new CourseNotFoundException("CourseProcess not found for courseId: " + courseProcessDTO.getCourseId() + " and studentId: " + courseProcessDTO.getStudentId()));

        CourseProcess courseProcess = new CourseProcess();

    *//*    courseProcess.setId(ScormHelper.generateCourseId());*//*

        fromDatabaseCourseProcess.setCourseId(courseProcessDTO.getCourseId());
        fromDatabaseCourseProcess.setCompletion_status(courseProcessDTO.getCompletion_status());
        fromDatabaseCourseProcess.setScore_max(courseProcessDTO.getScore_max());
        fromDatabaseCourseProcess.setLesson_status(courseProcessDTO.getLesson_status());
        fromDatabaseCourseProcess.setProgress_measure(courseProcessDTO.getProgress_measure());
        fromDatabaseCourseProcess.setScore_min(courseProcessDTO.getScore_min());
        fromDatabaseCourseProcess.setScore_raw(courseProcessDTO.getScore_raw());
        fromDatabaseCourseProcess.setSession_time(courseProcessDTO.getSession_time());
        fromDatabaseCourseProcess.setScore_scaled(courseProcessDTO.getScore_scaled());
        fromDatabaseCourseProcess.setStudentId(courseProcessDTO.getStudentId());
        fromDatabaseCourseProcess.setSuspendData(courseProcessDTO.getSuspend_data());

        CourseProcess process = courseProcessRepository.save(fromDatabaseCourseProcess);

        CourseProcessDTO processDTO = new CourseProcessDTO();

        processDTO.setId(process.getId());
        processDTO.setCourseId(process.getCourseId());
        processDTO.setScore_max(process.getScore_max());
        processDTO.setScore_min(process.getScore_min());
        processDTO.setScore_raw(process.getScore_raw());
        processDTO.setScore_scaled(process.getScore_scaled());
        processDTO.setLesson_status(process.getLesson_status());
        processDTO.setCompletion_status(process.getCompletion_status());
        processDTO.setSession_time(process.getSession_time());
        processDTO.setProgress_measure(process.getProgress_measure());
        processDTO.setSuccess_status(process.getSuccess_status());
        processDTO.setStudentId(process.getStudentId());
        processDTO.setSuspend_data(process.getSuspendData());

        return processDTO;

    }*/


    public ResponseDTO<CourseProcessDTO> getCourseProcess(CourseProcessDTO courseProcessDTO) {

        if (findCourseProcess(courseProcessDTO) != null) {
            CourseProcess existingCourseProcess = findCourseProcess(courseProcessDTO);
            return new ResponseDTO<>("success", mapToDTO(existingCourseProcess), true);
        }
        return new ResponseDTO<>("courseId and studentId not must be null", null, false);
    }





/*    public ResponseDTO<List<CourseProcessDTO>> getCoursesByStudentId(String studentId) {
        return courseProcessRepository.findCourseProcessByStudentId(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Courses not found for student ID: " + studentId))
                .stream()
                .map(this::mapToDTO)  // Method reference to map CourseProcess to CourseDTO
                .collect(Collectors.toList());
    }*/

    // DTO ni Entity ga o'zgartirish uchun yordamchi metod
    private CourseProcess mapToEntity(CourseProcessDTO courseProcessDTO) {
        CourseProcess courseProcess = new CourseProcess();
        courseProcess.setCourseId(courseProcessDTO.getCourseId());
        courseProcess.setCompletion_status(courseProcessDTO.getCompletion_status());
        courseProcess.setScore_max(courseProcessDTO.getScore_max());
        courseProcess.setLesson_status(courseProcessDTO.getLesson_status());
        courseProcess.setProgress_measure(courseProcessDTO.getProgress_measure());
        courseProcess.setScore_min(courseProcessDTO.getScore_min());
        courseProcess.setScore_raw(courseProcessDTO.getScore_raw());
        courseProcess.setSession_time(courseProcessDTO.getSession_time());
        courseProcess.setScore_scaled(courseProcessDTO.getScore_scaled());
        courseProcess.setStudentId(courseProcessDTO.getStudentId());
        courseProcess.setSuspendData(courseProcessDTO.getSuspend_data());
        return courseProcess;
    }

    // Entity ni DTO ga o'zgartirish uchun yordamchi metod
    private CourseProcessDTO mapToDTO(CourseProcess courseProcess) {
        CourseProcessDTO processDTO = new CourseProcessDTO();
        processDTO.setId(courseProcess.getId());
        processDTO.setCourseId(courseProcess.getCourseId());
        processDTO.setScore_max(courseProcess.getScore_max());
        processDTO.setScore_min(courseProcess.getScore_min());
        processDTO.setScore_raw(courseProcess.getScore_raw());
        processDTO.setScore_scaled(courseProcess.getScore_scaled());
        processDTO.setLesson_status(courseProcess.getLesson_status());
        processDTO.setCompletion_status(courseProcess.getCompletion_status());
        processDTO.setSession_time(courseProcess.getSession_time());
        processDTO.setProgress_measure(courseProcess.getProgress_measure());
        processDTO.setSuccess_status(courseProcess.getSuccess_status());
        processDTO.setStudentId(courseProcess.getStudentId());
        processDTO.setSuspend_data(courseProcess.getSuspendData());
        return processDTO;
    }


    private CourseProcess findCourseProcess(CourseProcessDTO courseProcessDTO) {
        return courseProcessRepository
                .findByCourseIdAndStudentId(courseProcessDTO.getCourseId(), courseProcessDTO.getStudentId())
                .orElseThrow(() -> new CourseProcessNotFoundException(
                        "CourseProcess not found for courseId: " + courseProcessDTO.getCourseId() +
                                " and studentId: " + courseProcessDTO.getStudentId()));
    }

    private boolean doesCourseProcessExist(String courseId, String studentId) {
        try {
            courseProcessRepository.findByCourseIdAndStudentId(courseId, studentId);
            return true; // CourseProcess exists
        } catch (CourseProcessNotFoundException e) {
            return false; // CourseProcess not found
        }
    }

    public boolean doesCourseExist(String courseId) {
        return courseFileRepository.findById(courseId)
                .map(course -> true) // Return true if the course is found
                .orElse(false); // Return false if the course is not found
    }

    private void updateCourseProcessFromDTO(CourseProcess courseProcess, CourseProcessDTO courseProcessDTO) {
        courseProcess.setCourseId(courseProcessDTO.getCourseId());
        courseProcess.setCompletion_status(courseProcessDTO.getCompletion_status());
        courseProcess.setScore_max(courseProcessDTO.getScore_max());
        courseProcess.setLesson_status(courseProcessDTO.getLesson_status());
        courseProcess.setProgress_measure(courseProcessDTO.getProgress_measure());
        courseProcess.setScore_min(courseProcessDTO.getScore_min());
        courseProcess.setScore_raw(courseProcessDTO.getScore_raw());
        courseProcess.setSession_time(courseProcessDTO.getSession_time());
        courseProcess.setScore_scaled(courseProcessDTO.getScore_scaled());
        courseProcess.setStudentId(courseProcessDTO.getStudentId());
        courseProcess.setSuspendData(courseProcessDTO.getSuspend_data());
    }


    private boolean isInvalidCourseOrStudent(CourseProcessDTO courseProcessDTO) {
        return !doesCourseExist(courseProcessDTO.getCourseId()) || courseProcessDTO.getStudentId().isBlank();
    }





}




