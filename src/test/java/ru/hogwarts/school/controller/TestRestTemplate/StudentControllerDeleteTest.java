package ru.hogwarts.school.controller.TestRestTemplate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.hogwarts.school.controller.TestConstants.MOCK_STUDENT;
import static ru.hogwarts.school.controller.TestConstants.MOCK_STUDENT_ID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerDeleteTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @MockBean
    private StudentRepository studentRepository;

    @Test
    public void deleteStudent() {
        // given
        when(studentRepository.findById(MOCK_STUDENT_ID)).thenReturn(Optional.of(MOCK_STUDENT));

        // when
        testRestTemplate.delete("http://localhost:" + port + "/students/" + MOCK_STUDENT_ID);

        // then
        verify(studentRepository).deleteById(MOCK_STUDENT_ID);
    }
}
