package ru.hogvartz.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogvartz.school.Controller.FacultyController;
import ru.hogvartz.school.Controller.StudentController;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentServiceRestTemplateTests {


    @LocalServerPort
    private int port;


    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    void testGetStudent() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/1", String.class)).isEqualTo("{\"id\":1,\"name\":\"Сережа\",\"age\":12,\"faculty\":{\"id\":2,\"name\":\"Slytherin\",\"color\":\"Green\"}}");
    }

    @Test
    void testGetStudentsForAge() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/filter/12", String.class)).isEqualTo("[{\"id\":1,\"name\":\"Сережа\",\"age\":12,\"faculty\":{\"id\":2,\"name\":\"Slytherin\",\"color\":\"Green\"}}]");
    }

    @Test
    void testGetStudentsBetweenAge() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/filter/11/15", String.class)).isEqualTo("[{\"id\":3,\"name\":\"Аня\",\"age\":11,\"faculty\":{\"id\":1,\"name\":\"Hufflepuff\",\"color\":\"Yellow\"}},{\"id\":6,\"name\":\"Олег\",\"age\":13,\"faculty\":{\"id\":2,\"name\":\"Slytherin\",\"color\":\"Green\"}},{\"id\":5,\"name\":\"Игорь\",\"age\":15,\"faculty\":{\"id\":4,\"name\":\"Ravenclaw\",\"color\":\"Blue\"}},{\"id\":4,\"name\":\"Варвара\",\"age\":14,\"faculty\":{\"id\":3,\"name\":\"Gryffindor\",\"color\":\"Red\"}},{\"id\":1,\"name\":\"Сережа\",\"age\":12,\"faculty\":{\"id\":2,\"name\":\"Slytherin\",\"color\":\"Green\"}}]");
    }

    @Test
    void testGetStudentFaculty() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/faculty/3", String.class)).isEqualTo("{\"id\":1,\"name\":\"Hufflepuff\",\"color\":\"Yellow\"}");
    }
}
