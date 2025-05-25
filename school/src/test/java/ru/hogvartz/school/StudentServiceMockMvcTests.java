package ru.hogvartz.school;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogvartz.school.Controller.StudentController;
import ru.hogvartz.school.Model.Faculty;
import ru.hogvartz.school.Model.Student;
import ru.hogvartz.school.Repository.StudentRepository;
import ru.hogvartz.school.Service.StudentService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(StudentService.class)
@WebMvcTest(StudentController.class)
public class StudentServiceMockMvcTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentRepository studentRepository;

    @MockitoSpyBean
    private StudentService studentService;


    @Test
    public void saveStudentTest() throws Exception {
        Long id = 1L;
        String name = "Oleg";
        int age = 3;
        String facultyName = "NameFaculty";
        String color = "Green";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", facultyName);
        facultyObject.put("color", color);


        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(facultyName);
        faculty.setColor(color);

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);
        studentObject.put("faculty", facultyObject);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);
        student.setFaculty(faculty);


        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student") //send
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //receive
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.faculty.name").value(facultyName))
                .andExpect(jsonPath("$.faculty.color").value(color));
    }

    @Test
    public void deleteStudentTest() throws Exception {
        Long id = 1L;
        String name = "Oleg";
        int age = 3;

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);


        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/1") //send
                        .content(String.valueOf(id.longValue()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateStudentTest() throws Exception {
        Long id = 1L;
        String name = "Oleg";
        int age = 3;
        String facultyName = "NameFaculty";
        String color = "Green";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", facultyName);
        facultyObject.put("color", color);


        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(facultyName);
        faculty.setColor(color);

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);
        studentObject.put("faculty", facultyObject);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);
        student.setFaculty(faculty);


        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student") //send
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //receive
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.faculty.name").value(facultyName))
                .andExpect(jsonPath("$.faculty.color").value(color));
    }
}
