package ru.hogvartz.school.mapper;

import ru.hogvartz.school.Model.Student;
import ru.hogvartz.school.dto.StudentDTO;

public class StudentMapper {
    public static StudentDTO toDTO(Student student) {
        if (student == null) {
            return null;
        }

        return new StudentDTO(
                student.getId(),
                student.getName(),
                student.getAge(),
                student.getFaculty()
        );
    }
}