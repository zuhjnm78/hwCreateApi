package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;

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

    public Faculty findFaculty(long id) {
        Optional<Faculty> facultyOptional = facultyRepository.findById(id);
        return facultyOptional.orElse(null);
    }

    public Faculty editFaculty(Faculty updatedFaculty) {

        Faculty existingFaculty = facultyRepository.findById(updatedFaculty.getId()).orElse(null);

        if (existingFaculty != null) {

            existingFaculty.setName(updatedFaculty.getName());
            existingFaculty.setColor(updatedFaculty.getColor());

            return facultyRepository.save(existingFaculty);
        } else {

            return null;
        }
    }

    public Faculty deleteFaculty(long id) {
        Optional<Faculty> facultyOptional = facultyRepository.findById(id);
        if (facultyOptional.isPresent()) {
            facultyRepository.deleteById(id);
            return facultyOptional.get();
        } else {
            return null;
        }
    }

    public Collection<Faculty> getAllFaculties() {

        return facultyRepository.findAll();
    }

    public Collection<Faculty> findFacultiesByNameOrColorIgnoreCase(String query) {
        return facultyRepository.findFacultiesByNameIgnoreCaseOrColorIgnoreCase(query, query);
    }
}
