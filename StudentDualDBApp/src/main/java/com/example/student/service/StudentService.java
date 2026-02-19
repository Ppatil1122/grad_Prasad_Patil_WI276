package com.example.student.service;

import com.example.student.entity.Student;
import com.example.student.repository.h2.H2StudentRepository;
import com.example.student.repository.pg.PgStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {

    @Autowired
    private H2StudentRepository h2Repository;

    @Autowired
    private PgStudentRepository pgRepository;

    @Transactional
    public void saveStudentToBothDbs(Student student) {
        // Save to H2
        h2Repository.save(student);
        
        // Save to PostgreSQL
        pgRepository.save(student);
    }

    public boolean existsByRollNo(Integer rollNo) {
        return h2Repository.existsById(rollNo) || pgRepository.existsById(rollNo);
    }
}
