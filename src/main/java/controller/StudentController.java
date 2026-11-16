
package com.kiet.student.controller;

import com.kiet.student.model.Student;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final List<Student> students = new ArrayList<>();

    public StudentController() {
        students.add(new Student(101, "Rahul Sharma",
                "rahul@kiet.edu", "CSE(AI & ML)", 2, 8.65));
        students.add(new Student(102, "Priya Singh",
                "priya@kiet.edu", "CSE", 2, 9.10));
        students.add(new Student(103, "Aman Kumar",
                "aman@kiet.edu", "IT", 3, 8.20));
        students.add(new Student(104, "Neha Verma",
                "neha@kiet.edu", "CSE", 2, 9.30));
        students.add(new Student(105, "Rohit Gupta",
                "rohit@kiet.edu", "ECE", 3, 7.90));
    }

    // GET all students
    @GetMapping
    public List<Student> getAllStudents() {
        return students;
    }

    // GET student by ID
    @GetMapping("/{studentId}")
    public Object getStudentById(@PathVariable int studentId) {
        return students.stream()
                .filter(s -> s.getStudentId() == studentId)
                .findFirst()
                .<Object>map(s -> s)
                .orElse(Map.of("message", "Student not found"));
    }

    // POST new student
    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        students.add(student);
        return student;
    }

    // PUT update student
    @PutMapping("/{studentId}")
    public Object updateStudent(@PathVariable int studentId,
                                @RequestBody Student updatedStudent) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentId() == studentId) {
                updatedStudent.setStudentId(studentId);
                students.set(i, updatedStudent);
                return updatedStudent;
            }
        }
        return Map.of("message", "Student not found");
    }

    // DELETE student
    @DeleteMapping("/{studentId}")
    public Map<String, String> deleteStudent(@PathVariable int studentId) {
        boolean removed = students.removeIf(
                s -> s.getStudentId() == studentId);

        if (removed) {
            return Map.of("message", "Student deleted successfully");
        }
        return Map.of("message", "Student not found");
    }

    // Search by branch and year
    @GetMapping("/search")
    public List<Student> searchStudents(
            @RequestParam String branch,
            @RequestParam int year) {
        return students.stream()
                .filter(s -> s.getBranch().equalsIgnoreCase(branch)
                        && s.getYear() == year)
                .toList();
    }

    // Students with CGPA >= given value
    @GetMapping("/topper")
    public List<Student> getToppers(@RequestParam double cgpa) {
        return students.stream()
                .filter(s -> s.getCgpa() >= cgpa)
                .toList();
    }
}
