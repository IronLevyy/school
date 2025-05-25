package ru.hogvartz.school.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogvartz.school.Model.Faculty;
import ru.hogvartz.school.Model.Student;
import ru.hogvartz.school.Service.FacultyService;

import java.util.Collection;


@RestController
@RequestMapping("faculty")
public class FacultyController {
    public final FacultyService facultyService;

    public FacultyController(FacultyService FacultyService) {
        this.facultyService = FacultyService;
    }

    @GetMapping("{id}")
    public Faculty getFaculty(@PathVariable Long id) {
        return facultyService.getFaculty(id);
    }

    @GetMapping("filter/findByColor/{color}")
    public Collection<Faculty> getFacultyForColor(@PathVariable String color) {
        return facultyService.getFacultyForColor(color);
    }

    @GetMapping("filter/findByColorOrName/{findTerm}")
    public Collection<Faculty> getFacultyForColorOrName(@PathVariable String findTerm) {
        return facultyService.getFacultyForColorOrName(findTerm);
    }

    @GetMapping("students/{id}")
    public Collection<Student> getStudents(@PathVariable Long id) {
        return facultyService.getFaculty(id).getStudents();
    }

    @PutMapping
    public Faculty updateFaculty(@RequestBody Faculty Faculty) {
        return facultyService.updateFaculty(Faculty);
    }

    @PostMapping
    public Faculty addFaculty(@RequestBody Faculty Faculty) {
        return facultyService.createFaculty(Faculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }
}
