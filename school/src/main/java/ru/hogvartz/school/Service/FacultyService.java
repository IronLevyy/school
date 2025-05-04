package ru.hogvartz.school.Service;

import org.springframework.stereotype.Service;
import ru.hogvartz.school.Model.Faculty;
import ru.hogvartz.school.Repository.FacultyRepository;

import java.util.Collection;

@Service
public class FacultyService {
    final
    FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(null);
        return facultyRepository.save(faculty);
    }

    public Faculty getFaculty(Long id) {
        return facultyRepository.findById(id).get();
    }

    public Faculty updateFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getFacultyForColor(String color) {
        return facultyRepository.findByColor(color);
    }

    public Collection<Faculty> getFacultyForColorOrName(String findTerm) {
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(findTerm, findTerm);
    }
}
