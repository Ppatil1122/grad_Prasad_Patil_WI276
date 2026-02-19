package com.example.student.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "student")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    private String regNo;

    private Integer rollNo;
    private String name;
    private Integer standard;
    private String school;
    private String gender;
    private Double percentage;
}
