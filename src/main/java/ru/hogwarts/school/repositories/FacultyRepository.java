package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Collection<Faculty> findFacultiesByColorIgnoreCase(String color);
    Collection<Faculty>findFacultiesByNameIgnoreCase(String name);

    Collection<Faculty> findFacultiesByNameIgnoreCaseOrColorIgnoreCase(String query, String query1);
}

