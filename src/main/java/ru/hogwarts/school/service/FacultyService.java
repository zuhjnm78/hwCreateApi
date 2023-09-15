package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {

        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {

        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty) {

        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {

        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getAllFaculties() {

        return facultyRepository.findAll();
    }
    public Collection<Faculty> findFacultiesByColorIgnoreCase(String color) {
        return facultyRepository.findFacultiesByColorIgnoreCase(color);
    }
    public Collection<Faculty> findFacultiesByNameIgnoreCase(String name) {
        return facultyRepository.findFacultiesByNameIgnoreCase(name);
    }
}
