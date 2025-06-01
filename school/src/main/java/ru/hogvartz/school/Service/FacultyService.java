package ru.hogvartz.school.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogvartz.school.Model.Faculty;
import ru.hogvartz.school.Repository.FacultyRepository;

import java.util.Collection;

@Service
public class FacultyService {
    final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public Faculty createFaculty(Faculty faculty) {
        logger.info("Creating faculty: " + faculty);
        faculty.setId(null);
        return facultyRepository.save(faculty);
    }

    public Faculty getFaculty(Long id) {
        logger.info("Getting faculty: " + id);
        return facultyRepository.findById(id).get();
    }

    public Faculty updateFaculty(Faculty faculty) {
        logger.info("Updating faculty: " + faculty);
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        logger.info("Deleting faculty: " + id);
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getFacultyForColor(String color) {
        logger.info("Getting faculty for color: " + color);
        return facultyRepository.findByColor(color);
    }

    public Collection<Faculty> getFacultyForColorOrName(String findTerm) {
        logger.info("Getting faculty for term: " + findTerm);
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(findTerm, findTerm);
    }
}
