package uz.resalsoft.yanascormdanimaqilayoxshamayapti.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "course")
@Data
public class Course {

    @Id
    private String id;

    private String courseName;

    private LocalDateTime createdDate;

    private String courseOwner;


}
