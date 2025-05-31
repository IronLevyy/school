package ru.hogvartz.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import ru.hogvartz.school.Controller.FacultyController;
import ru.hogvartz.school.Controller.StudentController;
import ru.hogvartz.school.Model.Faculty;
import ru.hogvartz.school.Model.Student;
import ru.hogvartz.school.Repository.FacultyRepository;
import ru.hogvartz.school.Repository.StudentRepository;

import java.util.List;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentServiceRestTemplateTests {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;
    @Autowired
    private FacultyController facultyController;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private Student Oleg;
    private Student Sereja;
    private Faculty slytherin;

    @BeforeEach
    void setupData() {
        studentRepository.deleteAll();
        facultyRepository.deleteAll();

        slytherin = new Faculty(null, "Слизерин", "Зеленый");

        Oleg = new Student(null, "Олег", 13, slytherin);
        Sereja = new Student(null, "Сережа", 12, slytherin);

        facultyRepository.save(slytherin);
        studentRepository.saveAll(List.of(Oleg,Sereja));

    }

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    void testGetStudent() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/" + Oleg.getId(), String.class))
                .contains("\"name\":\"Олег\",\"age\":13")
                .contains("\"name\":\"Слизерин\",\"color\":\"Зеленый\"}}");
    }

    @Test
    void testGetStudentsForAge() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/filter/" + Sereja.getAge(), String.class))
                .contains("\"name\":\"Сережа\",\"age\":12")
                .contains("\"name\":\"Слизерин\",\"color\":\"Зеленый\"}}");
    }

    @Test
    void testGetStudentsBetweenAge() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/filter/11/15", String.class))
                .contains("\"name\":\"Олег\",\"age\":13")
                .contains("\"name\":\"Слизерин\",\"color\":\"Зеленый\"}}")
                .contains("\"name\":\"Сережа\",\"age\":12")
                .contains("\"name\":\"Слизерин\",\"color\":\"Зеленый\"}}");
    }

    @Test
    void testGetStudentFaculty() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/faculty/" + Oleg.getId(), String.class))
                .contains("\"name\":\"Слизерин\",\"color\":\"Зеленый\"}");
    }
}
