package ru.hogvartz.school.Service;

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
    final
    StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        student.setId(null);
        return studentRepository.save(student);
    }

    public StudentDTO getStudent(Long id) {
        Student student = studentRepository.getById(id);
        return StudentMapper.toDTO(student);
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<StudentDTO> getStudentsForAge(int age) {
        List<Student> students = studentRepository.findByAge(age);
        return students.stream()
                .map(StudentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Collection<StudentDTO> getStudentsBetweenAge(int min, int max) {
        List<Student> students = studentRepository.findByAgeBetween(min, max);
        return students.stream()
                .map(StudentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public long getTotalStudents() {
        return studentRepository.countAllStudents();
    }

    public Double getAverageAge() {
        return studentRepository.findAverageAge();
    }

    public List<StudentDTO> getLastFiveStudents() {
        List<Student> students = studentRepository.findLastFiveStudents();
        return students.stream()
                .map(StudentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void printNamesWithParallelThreads() {
        List<String> names = studentRepository.findAll().stream()
                .map(Student::getName)
                .limit(6)
                .toList();

        System.out.println(names.get(0));
        System.out.println(names.get(1));

        Thread threadOne = new Thread(() -> {
                System.out.println(names.get(2));
                System.out.println(names.get(3));
        });

        Thread threadTwo = new Thread(() -> {
            System.out.println(names.get(4));
            System.out.println(names.get(5));
        });

        threadOne.start();
        threadTwo.start();
    }

    public synchronized void printNameSync(String name) {
        System.out.println(name);
    }

    public void printNamesWithSynchronizedThreads() {
        List<String> names = studentRepository.findAll().stream()
                .map(Student::getName)
                .limit(6)
                .toList();

        printNameSync(names.get(0));
        printNameSync(names.get(1));

        Thread threadOne = new Thread(() -> {
            printNameSync(names.get(2));
            printNameSync(names.get(3));
        });

        Thread threadTwo = new Thread(() -> {
            printNameSync(names.get(4));
            printNameSync(names.get(5));
        });

        threadOne.start();
        threadTwo.start();
    }
}
