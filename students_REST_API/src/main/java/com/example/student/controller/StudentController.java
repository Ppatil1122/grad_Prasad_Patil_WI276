package com.example.student.controller;

import com.example.student.entity.Student;
import com.example.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{regNo}")
    public ResponseEntity<Student> getStudentByRegNo(@PathVariable String regNo) {
        Optional<Student> student = studentService.getStudentByRegNo(regNo);
        return student.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }

    @PutMapping("/{regNo}")
    public ResponseEntity<Student> updateStudent(@PathVariable String regNo, @RequestBody Student student) {
        Student updatedStudent = studentService.updateStudent(regNo, student);
        if (updatedStudent != null) {
            return ResponseEntity.ok(updatedStudent);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{regNo}")
    public ResponseEntity<Student> patchStudent(@PathVariable String regNo, @RequestBody Student student) {
        Student updatedStudent = studentService.patchStudent(regNo, student);
        if (updatedStudent != null) {
            return ResponseEntity.ok(updatedStudent);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{regNo}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String regNo) {
        studentService.deleteStudent(regNo);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/school")
    public List<Student> getStudentsBySchool(@RequestParam String school) {
        return studentService.getStudentsBySchool(school);
    }

    @GetMapping("/school/count")
    public ResponseEntity<Long> getStudentCountBySchool(@RequestParam String school) {
        return ResponseEntity.ok(studentService.getStudentCountBySchool(school));
    }

    @GetMapping("/standard/count")
    public ResponseEntity<Long> getStudentCountByStandard(@RequestParam Integer standard) {
        return ResponseEntity.ok(studentService.getStudentCountByStandard(standard));
    }

    @GetMapping("/result")
    public List<Student> getStudentsByResult(@RequestParam Boolean pass) {
        return studentService.getStudentsByResult(pass);
    }

    @GetMapping("/strength")
    public ResponseEntity<Long> getStudentStrength(@RequestParam String gender, @RequestParam Integer standard) {
        return ResponseEntity.ok(studentService.getStudentCountByGenderAndStandard(gender, standard));
    }
}
