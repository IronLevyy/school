package ru.hogvartz.school.Controller;

import org.springframework.web.bind.annotation.*;
import ru.hogvartz.school.Model.Student;
import ru.hogvartz.school.Service.StudentService;

import java.util.Collection;


@RestController
@RequestMapping("student")
public class StudentController {
    public final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentService.getStudent(id);
    }

    @GetMapping("filter/{age}")
    public Collection<Student> getStudentsForAge(@PathVariable int age) {
        return studentService.getStudentsForAge(age);
    }


    @PutMapping
    public Student updateStudent(@RequestBody Student student) {
        return studentService.updateStudent(student);
    }

    @PostMapping
    public Student addStudent(Student student) {
        return studentService.createStudent(student);
    }

    @DeleteMapping("{id}")
    public Student deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }
}
