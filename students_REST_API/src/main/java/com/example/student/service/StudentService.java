package com.example.student.service;

import com.example.student.entity.Student;
import com.example.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentByRegNo(String regNo) {
        return studentRepository.findById(regNo);
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(String regNo, Student studentDetails) {
        return studentRepository.findById(regNo).map(student -> {
            student.setRollNo(studentDetails.getRollNo());
            student.setName(studentDetails.getName());
            student.setStandard(studentDetails.getStandard());
            student.setSchool(studentDetails.getSchool());
            student.setGender(studentDetails.getGender());
            student.setPercentage(studentDetails.getPercentage());
            return studentRepository.save(student);
        }).orElse(null);
    }

    public Student patchStudent(String regNo, Student studentDetails) {
        return studentRepository.findById(regNo).map(student -> {
            if (studentDetails.getRollNo() != null) student.setRollNo(studentDetails.getRollNo());
            if (studentDetails.getName() != null) student.setName(studentDetails.getName());
            if (studentDetails.getStandard() != null) student.setStandard(studentDetails.getStandard());
            if (studentDetails.getSchool() != null) student.setSchool(studentDetails.getSchool());
            if (studentDetails.getGender() != null) student.setGender(studentDetails.getGender());
            if (studentDetails.getPercentage() != null) student.setPercentage(studentDetails.getPercentage());
            return studentRepository.save(student);
        }).orElse(null);
    }

    public void deleteStudent(String regNo) {
        studentRepository.deleteById(regNo);
    }

    public List<Student> getStudentsBySchool(String school) {
        return studentRepository.findBySchoolIgnoreCase(school);
    }

    public long getStudentCountBySchool(String school) {
        return studentRepository.countBySchoolIgnoreCase(school);
    }

    public long getStudentCountByStandard(Integer standard) {
        return studentRepository.countByStandard(standard);
    }

    public List<Student> getStudentsByResult(Boolean pass) {
        if (pass) {
            return studentRepository.findByPercentageGreaterThanEqualOrderByPercentageDesc(40.0);
        } else {
            return studentRepository.findByPercentageLessThanOrderByPercentageDesc(40.0);
        }
    }

    public long getStudentCountByGenderAndStandard(String gender, Integer standard) {
        return studentRepository.countByGenderIgnoreCaseAndStandard(gender, standard);
    }
}
