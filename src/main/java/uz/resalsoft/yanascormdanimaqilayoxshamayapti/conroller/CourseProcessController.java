package uz.resalsoft.yanascormdanimaqilayoxshamayapti.conroller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.resalsoft.yanascormdanimaqilayoxshamayapti.dto.CourseProcessDTO;
import uz.resalsoft.yanascormdanimaqilayoxshamayapti.dto.ResponseDTO;
import uz.resalsoft.yanascormdanimaqilayoxshamayapti.service.CourseProcessService;

import java.util.List;

@RestController
@RequestMapping("/api/scorm")
@CrossOrigin(origins = "https://scorm-api.lazyprogrammer.uz, https://malaka-oshirish.uz")
public class CourseProcessController {

    private final CourseProcessService courseSaveProcess;

    public CourseProcessController(CourseProcessService courseSaveProcess) {
        this.courseSaveProcess = courseSaveProcess;
    }

    @PostMapping("/saveCourseProcess")
    public ResponseEntity<ResponseDTO<CourseProcessDTO>> saveCourseProcess(@RequestBody CourseProcessDTO courseProcessDTO) {
        return ResponseEntity.ok(courseSaveProcess.saveCourseProcess(courseProcessDTO));
    }

    @PutMapping("/updateCourseProcess")
    public ResponseEntity<ResponseDTO<CourseProcessDTO>> updateCourseProcess(@RequestBody CourseProcessDTO courseProcessDTO) {
        return ResponseEntity.ok(courseSaveProcess.updateCourseProcess(courseProcessDTO));
    }

    @PostMapping("/getCourseSaveProcess")
    public ResponseEntity<ResponseDTO<CourseProcessDTO>> getCourseProcess(@RequestBody CourseProcessDTO courseProcessDTO) {
        return ResponseEntity.ok(courseSaveProcess.getCourseProcess(courseProcessDTO));
    }

}
