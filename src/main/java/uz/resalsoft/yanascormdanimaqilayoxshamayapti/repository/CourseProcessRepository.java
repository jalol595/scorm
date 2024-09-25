package uz.resalsoft.yanascormdanimaqilayoxshamayapti.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.resalsoft.yanascormdanimaqilayoxshamayapti.entity.Course;
import uz.resalsoft.yanascormdanimaqilayoxshamayapti.entity.CourseProcess;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseProcessRepository extends CrudRepository<CourseProcess, String> {


   Optional<CourseProcess> findByCourseIdAndStudentId(String courseId, String studentId);


   Optional<List<CourseProcess>> findCourseProcessByStudentId(String studentId);

/*    @Query("SELECT s FROM CourseProcess cp WHERE cp.courseId = :fileName")
    Optional<CourseProcess> findByFileName(@Param("fileName") String courseId);*/

/*    @Query("SELECT cp FROM CourseProcess cp WHERE cp.courseId = :courseId AND cp.studentId = :studentId")
    Optional<CourseProcess> findByCourseIdAndStudentId(@Param("courseId") String courseId, @Param("studentId") String studentId);*/

}

