package uz.resalsoft.yanascormdanimaqilayoxshamayapti.exeption;

public class CourseNotFoundException extends RuntimeException {

    public CourseNotFoundException(String message) {
        super(message);
    }
}