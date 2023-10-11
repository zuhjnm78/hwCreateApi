package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.hogwarts.school.exception.IncorrectArgumentException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class FacultyService {
    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Collection<Student> getFacultyStudents(long facultyId) {
        logger.info("Method getFacultyStudents called with facultyId: {}", facultyId);
        Faculty faculty = facultyRepository.findById(facultyId).orElse(null);
        if (faculty != null) {
            return faculty.getStudents();
        }
        return null;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("Method createFaculty called");
        return facultyRepository.save(faculty);
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.info("Method editFaculty called");
        return facultyRepository.save(faculty);
    }

    public Faculty deleteFaculty(long id) {
        logger.info("Method deleteFaculty called with id: {}", id);
        Faculty faculty = get(id);
        facultyRepository.deleteById(id);
        return faculty;
    }

    public Faculty get(Long id) {
        logger.info("Method get called with id: {}", id);
        Optional<Faculty> faculty = facultyRepository.findById(id);
        return faculty.orElse(null);
    }

    public Collection<Faculty> getAllFaculties() {
        logger.info("Method getAllFaculties called");
        return facultyRepository.findAll();
    }

    public Collection<Faculty> findByNameOrColor(String name, String color) throws IncorrectArgumentException {
        logger.info("Method findByNameOrColor called");
        if (!StringUtils.hasText(color) && !StringUtils.hasText(name)) {
            throw new IncorrectArgumentException("Color or name of the faculty must be specified");
        }
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }
}