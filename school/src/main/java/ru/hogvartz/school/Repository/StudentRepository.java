package ru.hogvartz.school.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogvartz.school.Model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
