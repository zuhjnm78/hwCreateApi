package ru.hogwarts.school.controller.TestRestTemplate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static ru.hogwarts.school.controller.TestConstants.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @MockBean
    private FacultyService facultyService;

    @Test
    public void createFaculty() {
        Long facultyId = MOCK_FACULTY_ID;
        when(facultyService.get(facultyId)).thenReturn(MOCK_FACULTY);

        String url = "http://localhost:" + port + "/faculty/{id}";
        ResponseEntity<Faculty> newFacultyRq = testRestTemplate.getForEntity(url, Faculty.class, facultyId);

        assertThat(newFacultyRq.getStatusCode()).isEqualTo(HttpStatus.OK);
        Faculty newFaculty = newFacultyRq.getBody();
        assertThat(newFaculty.getName()).isEqualTo(MOCK_FACULTY.getName());
        assertThat(newFaculty.getColor()).isEqualTo(MOCK_FACULTY.getColor());

    }
    @Test
    public void testGetFacultyById() {

        Long facultyId = MOCK_FACULTY_ID;
        when(facultyService.get(facultyId)).thenReturn(MOCK_FACULTY);

        String url = "http://localhost:" + port + "/faculty/{id}";
        ResponseEntity<Faculty> response = testRestTemplate.getForEntity(url, Faculty.class, facultyId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Faculty faculty = response.getBody();
        assertEquals(MOCK_FACULTY_ID, faculty.getId());
        assertEquals(MOCK_FACULTY_NAME, faculty.getName());
        assertEquals(MOCK_FACULTY_COLOR, faculty.getColor());
    }
    @Test
    public void getByNameOrColor() {
        Faculty newFaculty = createTestFaculty();
        assertThat(testRestTemplate.getForEntity("http://localhost:" + port + "/faculty?name=Jack&color=red", String.class))
                .isNotNull();
    }
    @Test
    public void getAllFaculties() {
        Faculty newFaculty = createTestFaculty();
        assertThat(testRestTemplate.getForEntity("http://localhost:" + port + "/faculty/all", String.class))
                .isNotNull();
    }

    public Faculty createTestFaculty() {
        ResponseEntity<Faculty> newFacultyRq =
                testRestTemplate.postForEntity("http://localhost:" + port + "/faculty", MOCK_FACULTY, Faculty.class);
        assertThat(newFacultyRq.getStatusCode()).isEqualTo(HttpStatus.OK);
        return newFacultyRq.getBody();
    }
}