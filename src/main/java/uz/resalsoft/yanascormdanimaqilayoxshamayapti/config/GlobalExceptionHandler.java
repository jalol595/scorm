package uz.resalsoft.yanascormdanimaqilayoxshamayapti.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import uz.resalsoft.yanascormdanimaqilayoxshamayapti.dto.ResponseDTO;
import uz.resalsoft.yanascormdanimaqilayoxshamayapti.exeption.CheckCourseDetailsException;
import uz.resalsoft.yanascormdanimaqilayoxshamayapti.exeption.CourseFileNotFoundException;
import uz.resalsoft.yanascormdanimaqilayoxshamayapti.exeption.CourseNotFoundException;
import uz.resalsoft.yanascormdanimaqilayoxshamayapti.exeption.CourseProcessNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<?> handleCourseNotFoundException(CourseNotFoundException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }


    @ExceptionHandler(CheckCourseDetailsException.class)
    public ResponseEntity<?> handleCheckCourseDetailsException(CheckCourseDetailsException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(CourseFileNotFoundException.class)
    public ResponseEntity<?> handleCourseFileNotFoundExceptionException(CheckCourseDetailsException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(CourseProcessNotFoundException.class)
    public ResponseEntity<?> handleCourseProcessNotFoundExceptionException(CheckCourseDetailsException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + ex.getMessage());
    }
}
