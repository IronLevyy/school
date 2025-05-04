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
class SchoolApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private FacultyController facultyController;
	@Autowired
	private StudentController studentController;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void contextLoads() throws Exception {
		Assertions.assertThat(facultyController).isNotNull();
	}

	@Test
	void testGetStudent() throws Exception {
		Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/1", String.class)).isNotNull();
	}

	@Test
	void testGetStudentsForAge() throws Exception {
		Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/filter/12", String.class)).isNotNull();
	}

	@Test
	void testGetStudentsBetweenAge() throws Exception {
		Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/filter/11/15", String.class)).isNotNull();
	}

	@Test
	void testGetStudentFaculty() throws Exception {
		Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/faculty/2", String.class)).isNotNull();
	}

	@Test
	void testGetFaculty() throws Exception {
		Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/2", String.class)).isNotNull();
	}

	@Test
	void testGetFacultyColor() throws Exception {
		Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/filter/findByColor/Red", String.class)).isNotNull();
	}

	@Test
	void testGetFacultyColorOrName() throws Exception {
		Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/filter/findByColorOrName/Red", String.class)).isNotNull();
	}

	@Test
	void testGetStudentOfFaculty() throws Exception {
		Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/students/2", String.class)).isNotNull();
	}

}
