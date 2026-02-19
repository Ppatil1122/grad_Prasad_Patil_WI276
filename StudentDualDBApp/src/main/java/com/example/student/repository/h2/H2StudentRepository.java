package com.example.student.repository.h2;

import com.example.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface H2StudentRepository extends JpaRepository<Student, Integer> {
}
