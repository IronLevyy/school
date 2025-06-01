package ru.hogvartz.school.dto;

import ru.hogvartz.school.Model.Faculty;

public record StudentDTO(Long id, String name, int age, Faculty faculty) {
    
}
