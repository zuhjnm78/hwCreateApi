package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@RestController
@RequestMapping("student")
@Tag(name = "API для работы со студентами")
public class StudentController {
    private final StudentService studentService;
    private final StudentRepository studentRepository;

    public StudentController(StudentService studentService, StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }

    @PostMapping
    @Operation(summary = "Создание студента")
    public Student create(@RequestBody Student studentRs) {
        return studentService.createStudent(studentRs);
    }

    @PutMapping
    @Operation(summary = "Изменение студента")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student updatedStudent = studentService.updateStudent(student);
        if (updatedStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление студента")
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
    @Operation(summary = "Полученіе факультета студента")
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

    @GetMapping("/names-starting-with-a")
    public ResponseEntity<List<String>> getStudentNamesStartingWithA() {
        List<Student> students = studentRepository.findAllByNameStartingWithIgnoreCase("A");

        List<String> sortedNames = students.stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList());

        return new ResponseEntity<>(sortedNames, HttpStatus.OK);
    }

    @GetMapping("/average-age-stream")
    public double getAverageAge() {
        List<Student> students = studentRepository.findAll();

        OptionalDouble averageAge = students.stream()
                .mapToInt(Student::getAge)
                .average();

        return averageAge.orElse(0.0);
    }

    @GetMapping("/print-names")
    public void printStudentNames() {
       studentService.printStudentNamesAsync();
    }
    @GetMapping("/print-names-synchron")
    public void printStudentNamesSynchron() {
        studentService.printStudentNamesSync();
    }
}

