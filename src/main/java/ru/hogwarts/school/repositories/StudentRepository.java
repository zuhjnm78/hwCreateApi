package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;
import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAll();
    List<Student> findAllByNameStartingWithIgnoreCase(String prefix);
    Collection<Student> findByAgeBetween(Integer min, Integer max);
    @Query("SELECT COUNT(s) FROM student s")
        Long countAllStudents();
    @Query("SELECT AVG(s.age) FROM student s")
    Double getAverageStudentAge();

@Query("SELECT s FROM student s ORDER BY s.id DESC ")
    List<Student> findTop5Students();

}
