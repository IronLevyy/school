package ru.hogvartz.school.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogvartz.school.Model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(int age);
}
