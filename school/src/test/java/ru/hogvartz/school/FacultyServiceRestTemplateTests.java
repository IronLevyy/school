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
import ru.hogvartz.school.Model.Faculty;
import ru.hogvartz.school.Model.Student;
import ru.hogvartz.school.Repository.FacultyRepository;
import ru.hogvartz.school.Repository.StudentRepository;

import java.util.List;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyServiceRestTemplateTests {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private StudentRepository studentRepository;

    private Faculty slytherin;

    @BeforeEach
    void setupData() {
        studentRepository.deleteAll();
        facultyRepository.deleteAll();
        slytherin = new Faculty(null, "Slytherin", "Green");
        Faculty gryffindor = new Faculty(null, "Gryffindor", "Red");

        facultyRepository.saveAll(List.of(slytherin, gryffindor));

        studentRepository.save(new Student(null, "Олег", 13, slytherin));
        studentRepository.save(new Student(null, "Сережа", 12, slytherin));

    }

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    void testGetFaculty() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/" + slytherin.getId(), String.class))
                .contains("\"name\":\"Slytherin\"").contains("\"color\":\"Green\"");
    }

    @Test
    void testGetFacultyColor() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/filter/findByColor/Red", String.class))
                .contains("\"name\":\"Gryffindor\"").contains("\"color\":\"Red\"");
    }

    @Test
    void testGetFacultyColorOrName() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/filter/findByColorOrName/Red", String.class))
                .contains("\"name\":\"Gryffindor\"").contains("\"color\":\"Red\"");
    }

    @Test
    void testGetStudentOfFaculty() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/students/" + slytherin.getId(), String.class))
                .contains("\"name\":\"Олег\",\"age\":13").contains("\"name\":\"Slytherin\",\"color\":\"Green\"")
                .contains("\"name\":\"Сережа\",\"age\":12").contains("\"name\":\"Slytherin\",\"color\":\"Green\"");
    }

}
