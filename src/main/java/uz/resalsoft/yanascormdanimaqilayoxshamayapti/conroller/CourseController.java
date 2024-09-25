package uz.resalsoft.yanascormdanimaqilayoxshamayapti.conroller;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.resalsoft.yanascormdanimaqilayoxshamayapti.dto.CourseDTO;
import uz.resalsoft.yanascormdanimaqilayoxshamayapti.dto.CourseProcessDTO;
import uz.resalsoft.yanascormdanimaqilayoxshamayapti.dto.ResponseDTO;
import uz.resalsoft.yanascormdanimaqilayoxshamayapti.service.CourserService;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@RestController
@RequestMapping("/api/scorm")
@CrossOrigin(origins = "http://localhost:5173")
public class CourseController {


    @Value("${scorm.storage.path}")
    private String scormStoragePath;

    private final CourserService courserService;

    /*    private static final String scormStoragePath = "scorm_packages/";*/


    public CourseController(CourserService courserService) {
        this.courserService = courserService;
    }


    //zor
    @PostMapping("/upload")
    public ResponseEntity<ResponseDTO> uploadScormPackage(@RequestParam("file") MultipartFile file, @RequestParam String courseName) {
        return ResponseEntity.ok(courserService.uploadScormPackage(file, courseName));
    }


    //tekshirilmadi
    @GetMapping("/getFile")
    public ResponseEntity<Resource> getHtmlFile() {
        try {
            // Construct the file path
            Path filePathFull = Paths.get("html/index.html").toAbsolutePath().normalize();
            File file = filePathFull.toFile();

            // Ensure the file exists
            if (!file.exists()) {
                return ResponseEntity.notFound().build();
            }

            // Create a resource for the file
            Resource resource = new UrlResource(file.toURI());

            // Determine content type
            String contentType = Files.probeContentType(filePathFull);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);


        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    //muammo bor
    @GetMapping("/files/{courseId}/{*filePath}")
    public ResponseEntity<Resource> getFile(@PathVariable String courseId, @PathVariable String filePath) {
        try {
            // FileService orqali faylni yuklash
            Resource resource = courserService.getResourceFile(courseId, filePath);

            // Fayl yo'lidan kontent turini aniqlash
            String contentType = courserService.determineContentType(Paths.get(scormStoragePath, courseId, filePath));

            // Faylni HTTP javobga qo'shish
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);

        } catch (MalformedURLException e) {
            // Xato bo'lsa, INTERNAL_SERVER_ERROR qaytarish
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (IOException e) {
            // Fayl bilan bog'liq xatolar uchun INTERNAL_SERVER_ERROR qaytarish
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (IllegalArgumentException e) {
            // Fayl topilmasa, NOT_FOUND statusi qaytariladi
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @GetMapping("/courses")
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        return ResponseEntity.ok(courserService.getAllCourses());
    }


    @GetMapping("/coursesById/{id}")
    public ResponseEntity<CourseDTO> getCoursesById(@PathVariable String id) {
        return ResponseEntity.ok(courserService.getCoursesById(id));
    }


    @GetMapping("/getCourseAndCourseProcess")
    public ResponseEntity<?> getCourseByStudentId() {
        return ResponseEntity.ok(courserService.getCoursesAndStudents());
    }


    @GetMapping("/getCourseByStudentId/{studentId}")
    public ResponseEntity<?> getCoursesByStudentsId(@PathVariable String studentId) {
        return ResponseEntity.ok(courserService.getCoursesByStudentsId(studentId));
    }

}















