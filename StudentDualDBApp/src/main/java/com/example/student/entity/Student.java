package com.example.student.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class Student {

    @Id
    private Integer rollNo;
    private String name;
    private String standard;
    private String school;

    // Default constructor
    public Student() {
    }

    // Parameterized constructor
    public Student(Integer rollNo, String name, String standard, String school) {
        this.rollNo = rollNo;
        this.name = name;
        this.standard = standard;
        this.school = school;
    }

    // Getters and Setters
    public Integer getRollNo() {
        return rollNo;
    }

    public void setRollNo(Integer rollNo) {
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
