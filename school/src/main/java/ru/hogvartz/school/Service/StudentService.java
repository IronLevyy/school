package ru.hogvartz.school.Service;

import org.springframework.stereotype.Service;
import ru.hogvartz.school.Model.Student;
import ru.hogvartz.school.Repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentService {
    final
    StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        student.setId(null);
        return studentRepository.save(student);
    }

    public Student getStudent(Long id) {
        return studentRepository.findById(id).get();
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> getStudentsForAge(int age) {
        return studentRepository.findByAge(age);
    }

    public Collection<Student> getStudentsBetweenAge(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }
}
