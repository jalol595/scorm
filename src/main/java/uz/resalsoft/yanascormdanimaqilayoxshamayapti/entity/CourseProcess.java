package uz.resalsoft.yanascormdanimaqilayoxshamayapti.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "course_process")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseProcess {

    @Id
    private String id;

    private String progress_measure;
    private String completion_status;
    private String lesson_status;
    private Integer score_raw;
    private Integer score_min;
    private Integer score_max;
    private Integer score_scaled;
    private String session_time;
    private String success_status;


    @Column(name = "suspend_data", columnDefinition = "TEXT")
    private String suspendData;

    private String courseId;
    private String studentId;






}
