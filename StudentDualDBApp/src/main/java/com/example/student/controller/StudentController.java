package com.example.student.controller;

import com.example.student.entity.Student;
import com.example.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("student", new Student());
        return "student";
    }

    @PostMapping("/insert")
    public String insertStudent(@ModelAttribute("student") Student student, Model model) {
        if (studentService.existsByRollNo(student.getRollNo())) {
            model.addAttribute("error", "Student with Roll No " + student.getRollNo() + " already exists!");
            return "student"; // Return to the form with error message
        }
        studentService.saveStudentToBothDbs(student);
        return "redirect:/success";
    }

    @GetMapping("/success")
    public String showSuccess() {
        return "success";
    }
}
