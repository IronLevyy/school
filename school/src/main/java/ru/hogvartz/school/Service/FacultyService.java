package ru.hogvartz.school.Service;

import org.springframework.stereotype.Service;
import ru.hogvartz.school.Model.Faculty;
import ru.hogvartz.school.Model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private HashMap<Long, Faculty> facults = new HashMap<>();
    private long lastId = 0;

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++lastId);
        facults.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty getFaculty(long id) {
        return facults.get(id);
    }

    public Faculty updateFaculty(Faculty faculty) {
        facults.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty deleteFaculty(long id) {
        return facults.remove(id);
    }

    public Collection<Faculty> getFacultyForColor(String color) {
        return facults.values().stream()
                .filter(student -> student.getColor().equals(color))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
