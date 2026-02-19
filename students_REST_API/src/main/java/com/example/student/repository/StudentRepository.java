package com.example.student.repository;

import com.example.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    List<Student> findBySchoolIgnoreCase(String school);

    long countBySchoolIgnoreCase(String school);

    long countByStandard(Integer standard);

    long countByGenderIgnoreCaseAndStandard(String gender, Integer standard);

    List<Student> findByPercentageGreaterThanEqualOrderByPercentageDesc(Double percentage);

    List<Student> findByPercentageLessThanOrderByPercentageDesc(Double percentage);
}
