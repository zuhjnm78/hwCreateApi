package ru.hogwarts.school.controller.TestRestTemplate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.hogwarts.school.controller.TestConstants.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerDeleteTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @MockBean
    private FacultyRepository facultyRepository;
    @Test
    public void deleteFaculty() {
        // given
        when(facultyRepository.findById(MOCK_FACULTY_ID)).thenReturn(Optional.of(MOCK_FACULTY));

        // when
        testRestTemplate.delete("http://localhost:" + port + "/faculty/" + MOCK_FACULTY_ID);

        // then
        verify(facultyRepository).deleteById(MOCK_FACULTY_ID);
    }
}
