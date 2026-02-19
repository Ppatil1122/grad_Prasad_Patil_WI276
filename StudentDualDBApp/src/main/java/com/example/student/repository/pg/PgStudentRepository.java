package com.example.student.repository.pg;

import com.example.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PgStudentRepository extends JpaRepository<Student, Integer> {
}
