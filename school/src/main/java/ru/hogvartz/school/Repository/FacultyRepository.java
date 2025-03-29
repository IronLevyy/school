package ru.hogvartz.school.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogvartz.school.Model.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
}
