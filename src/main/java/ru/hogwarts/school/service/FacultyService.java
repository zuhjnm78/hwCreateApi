package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;

@Service
public class FacultyService {
    private final HashMap<Long, Faculty> faculties = new HashMap<>();
    private long firstFacultyId = 0;

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++firstFacultyId);
        faculties.put(firstFacultyId, faculty);
        return faculty;
    }
    public Faculty findFaculty(long id) {
        return faculties.get(id);
    }
    public Faculty editFaculty(Faculty faculty) {
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }
    public Faculty deleteFaculty(long id) {
        return faculties.remove(id);
    }

    public Collection<Faculty> getAllFaculties () {
        return faculties.values();
    }
}
