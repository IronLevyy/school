package ru.hogvartz.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogvartz.school.Controller.FacultyController;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyServiceRestTemplateTests {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;


    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    void testGetFaculty() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/2", String.class)).isEqualTo("{\"id\":2,\"name\":\"Slytherin\",\"color\":\"Green\"}");
    }

    @Test
    void testGetFacultyColor() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/filter/findByColor/Red", String.class)).isEqualTo("[{\"id\":3,\"name\":\"Gryffindor\",\"color\":\"Red\"}]");
    }

    @Test
    void testGetFacultyColorOrName() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/filter/findByColorOrName/Red", String.class)).isEqualTo("[{\"id\":3,\"name\":\"Gryffindor\",\"color\":\"Red\"}]");
    }

    @Test
    void testGetStudentOfFaculty() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/students/2", String.class)).isEqualTo("[{\"id\":6,\"name\":\"Олег\",\"age\":13,\"faculty\":{\"id\":2,\"name\":\"Slytherin\",\"color\":\"Green\"}},{\"id\":1,\"name\":\"Сережа\",\"age\":12,\"faculty\":{\"id\":2,\"name\":\"Slytherin\",\"color\":\"Green\"}}]");
    }

}
