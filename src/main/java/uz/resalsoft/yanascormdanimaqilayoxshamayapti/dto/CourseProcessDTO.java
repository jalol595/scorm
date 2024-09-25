package uz.resalsoft.yanascormdanimaqilayoxshamayapti.dto;

import lombok.Data;

@Data
public class CourseProcessDTO {


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

    private String suspend_data;

    private String courseId;

    private String studentId;


}




