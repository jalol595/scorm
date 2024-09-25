package uz.resalsoft.yanascormdanimaqilayoxshamayapti.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import uz.resalsoft.yanascormdanimaqilayoxshamayapti.entity.Course;

import java.util.List;

@Repository
public interface CourseRepository extends CrudRepository<Course, String> {


    @Query(value = "SELECT cf.id, cf.course_name, cf.course_owner, cf.created_date, cp.student_id " +
            "FROM course cf " +
            "LEFT JOIN course_process cp ON cf.id = cp.course_id", nativeQuery = true)
    List<Object[]> findAllCourseFilesWithProcesses();



    @Query(value = "SELECT cf.id, cf.course_name, cf.course_owner, cf.created_date, cp.student_id " +
            "FROM course cf " +
            "LEFT JOIN course_process cp ON cf.id = cp.course_id " +
            "WHERE cp.student_id = :studentId", nativeQuery = true)
    List<Object[]> findAllCourseFilesWithProcessesByStudentId(@Param("studentId") String studentId);

/*    @Query("SELECT  cf FROM Course cf JOIN CourseProcess cp ON cf.id = cp.courseId")
    List<Object[]> findAllCourseFilesWithProcesses();*/





}

