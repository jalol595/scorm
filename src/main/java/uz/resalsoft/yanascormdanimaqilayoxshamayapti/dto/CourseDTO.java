package uz.resalsoft.yanascormdanimaqilayoxshamayapti.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDTO {


    private String id;

    private String courseName;

    private LocalDateTime createdDate;

    private String courseOwner;

    private String courseImsmanifestPath;

    private String studentId;



}
