package ru.hogvartz.school.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogvartz.school.Model.Faculty;
import ru.hogvartz.school.Model.Student;
import ru.hogvartz.school.Service.StudentService;
import ru.hogvartz.school.dto.StudentDTO;

import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("student")
public class StudentController {
    public final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public StudentDTO getStudent(@PathVariable Long id) {
        return studentService.getStudent(id);
    }

    @GetMapping("filter/{age}")
    public Collection<StudentDTO> getStudentsForAge(@PathVariable int age) {
        return studentService.getStudentsForAge(age);
    }

    @GetMapping("filter/{min}/{max}")
    public Collection<StudentDTO> getStudentsBetweenAge(@PathVariable int min, @PathVariable int max) {
        return studentService.getStudentsBetweenAge(min, max);
    }

    @GetMapping("faculty/{id}")
    public Faculty getFaculty(@PathVariable Long id) {
        return studentService.getStudent(id).faculty();
    }

    @GetMapping("/count")
    public long getTotalStudents() {
        return studentService.getTotalStudents();
    }

    @GetMapping("/average-age")
    public Double getAverageAge() {
        return studentService.getAverageAge();
    }

    @GetMapping("/last-five")
    public List<StudentDTO> getLastFiveStudents() {
        return studentService.getLastFiveStudents();
    }

    @GetMapping("/print-parallel")
    public void printParallel() {
        studentService.printNamesWithParallelThreads();
    }

    @GetMapping("/print-synchronized")
    public void printSynchronized() {
        studentService.printNamesWithSynchronizedThreads();
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
    public ResponseEntity deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
}
