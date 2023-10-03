package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.StudentService;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("students")
@Tag(name = "API для работы со студентами")
public class StudentController {
    private final StudentService studentService;
    private final StudentRepository studentRepository;

    public StudentController(StudentService studentService, StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }

    @PostMapping
    @Operation (summary = "Создание студента")
    public Student create(@RequestBody Student studentRs) {
        return studentService.createStudent(studentRs);
    }

    @PutMapping
    @Operation (summary = "Изменение студента")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student updatedStudent = studentService.updateStudent(student);
        if (updatedStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("{id}")
    @Operation (summary = "Удаление студента")
    public ResponseEntity deleteStudent(@PathVariable Long id) {
        Student deletedStudent = studentService.deleteStudent(id);

        if (deletedStudent != null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение студента по ID")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);

        if (student != null) {
            return ResponseEntity.ok(student);
        } else {

            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("all")
    @Operation(summary = "Получение всех студентов")
    public ResponseEntity<Collection<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("age")
    @Operation(summary = "Получение студентов по возрасту")
    public ResponseEntity<Collection<Student>> getByAgeBetween(@RequestParam Integer min, @RequestParam Integer max) {
        Collection<Student> filteredStudents = studentService.findByAgeBetween(min, max);
        return ResponseEntity.ok(filteredStudents);
    }

    @GetMapping("faculty/{studentId}")
    @Operation (summary = "Полученіе факультета студента")
    public ResponseEntity<Faculty> getStudentFaculty(@PathVariable Long studentId) {
        Faculty faculty = studentService.getStudentById(studentId).getFaculty();
        return ResponseEntity.ok(faculty);
    }
    @GetMapping("/count")
    public ResponseEntity<Long> countAllStudents() {
        Long count = studentRepository.countAllStudents();
        return ResponseEntity.ok(count);
    }
    @GetMapping("/average-age")
    public ResponseEntity<Double> getAverageStudentAge() {
        Double averageAge = studentRepository.getAverageStudentAge();
        return ResponseEntity.ok(averageAge);
    }

    @GetMapping("/last-five")
    public ResponseEntity<List<Student>> getLastFiveStudents() {
        List<Student> lastFiveStudents = studentRepository.findTop5Students();
        return ResponseEntity.ok(lastFiveStudents);
    }
}

