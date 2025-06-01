package ru.hogvartz.school.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogvartz.school.Model.Student;
import ru.hogvartz.school.Repository.StudentRepository;
import ru.hogvartz.school.dto.StudentDTO;
import ru.hogvartz.school.mapper.StudentMapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        student.setId(null);
        return studentRepository.save(student);
    }
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentDTO getStudent(Long id) {
        logger.info("Get student by id: " + id);
        Student student = studentRepository.getById(id);
        return StudentMapper.toDTO(student);
    }

    public Student updateStudent(Student student) {
        logger.info("Update student by id: " + student.getId());
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        logger.info("Delete student by id: " + id);
        studentRepository.deleteById(id);
    }

    public Collection<StudentDTO> getStudentsForAge(int age) {
        logger.info("Get students for age " + age);
        List<Student> students = studentRepository.findByAge(age);
        return students.stream()
                .map(StudentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Collection<StudentDTO> getStudentsBetweenAge(int min, int max) {
        logger.info("Get students between age " + min + " and " + max);
        List<Student> students = studentRepository.findByAgeBetween(min, max);
        return students.stream()
                .map(StudentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public long getTotalStudents() {
        logger.info("Get total students");
        return studentRepository.countAllStudents();
    }

    public Double getAverageAge() {
        logger.info("Get average age");
        return studentRepository.findAverageAge();
    }

    public List<StudentDTO> getLastFiveStudents() {
        logger.info("Get last five students");
        List<Student> students = studentRepository.findLastFiveStudents();
        return students.stream()
                .map(StudentMapper::toDTO)
                .collect(Collectors.toList());
    }

}
