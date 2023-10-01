package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.hogwarts.school.exception.IncorrectArgumentException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.Optional;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {

        this.facultyRepository = facultyRepository;
    }

    public Collection<Student> getFacultyStudents(long facultyId) {
        Faculty faculty = facultyRepository.findById(facultyId).orElse(null);
        if (faculty != null) {
            return faculty.getStudents();
        }
        return null;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty editFaculty(Faculty faculty) {
            return facultyRepository.save(faculty);
    }
    public Faculty deleteFaculty(long id) {
        Faculty faculty = get(id);
            facultyRepository.deleteById(id);
            return faculty;
    }

    public Faculty get(Long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
       return faculty.orElse(null);
    }

    public Collection<Faculty> getAllFaculties() {

        return facultyRepository.findAll();
    }

    public Collection<Faculty> findByNameOrColor(String name, String color) throws IncorrectArgumentException {
        if (!StringUtils.hasText(color) && !StringUtils.hasText(name)) {
            throw new IncorrectArgumentException("Требуется указать цвет или наименование факультета");
        }
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }
}
