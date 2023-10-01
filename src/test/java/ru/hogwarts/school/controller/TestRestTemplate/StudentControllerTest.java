package ru.hogwarts.school.controller.TestRestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.TestConstants;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.hogwarts.school.controller.TestConstants.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @LocalServerPort
    private int port;
    @MockBean
    private StudentService studentService;

    @Test
    public void createStudent() {

        Long studentId = MOCK_STUDENT_ID;
        when(studentService.getStudentById(studentId)).thenReturn(MOCK_STUDENT);

        String url = "http://localhost:" + port + "/students/{id}";
        ResponseEntity<Student> newStudentRs = testRestTemplate.getForEntity(url, Student.class, studentId);

        assertEquals(HttpStatus.OK, newStudentRs.getStatusCode());
        Student newStudent = newStudentRs.getBody();

        assertThat(newStudent.getName()).isEqualTo(MOCK_STUDENT.getName());
        assertThat(newStudent.getAge()).isEqualTo(MOCK_STUDENT.getAge());

    }


    @Test
    public void testGetStudentById() {

        Long studentId = MOCK_STUDENT_ID;
        when(studentService.getStudentById(studentId)).thenReturn(MOCK_STUDENT);

        String url = "http://localhost:" + port + "/students/{id}";
        ResponseEntity<Student> response = testRestTemplate.getForEntity(url, Student.class, studentId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Student student = response.getBody();
        assertEquals(MOCK_STUDENT_ID, student.getId());
        assertEquals(MOCK_STUDENT_NAME, student.getName());
        assertEquals(MOCK_STUDENT_AGE, student.getAge());
    }


    @Test
    public void getByAgeBetween() {
        Student newStudent = createTestStudent();
        assertThat(testRestTemplate.getForEntity("http://localhost:" + port + "/student?min=10&max=30", String.class))
                .isNotNull();
    }
    @Test
    public void getAllStudents() {
        Student newStudent = createTestStudent();
        assertThat(testRestTemplate.getForEntity("http://localhost:" + port + "/student/all", String.class))
                .isNotNull();
    }

    public Student createTestStudent() {
        ResponseEntity<Student> newStudentRs =
                testRestTemplate.postForEntity("http://localhost:" + port + "/students", MOCK_STUDENT, Student.class);
        assertThat(newStudentRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        return newStudentRs.getBody();
    }

}