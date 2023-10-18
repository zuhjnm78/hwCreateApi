package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentService {
    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Faculty getStudentFaculty(long studentId) {
        logger.info("Method getStudentFaculty called with studentId: {}", studentId);
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student != null) {
            return student.getFaculty();
        }
        return null;
    }

    public Student createStudent(Student student) {
        logger.info("Method createStudent called");
        Student createdStudent = studentRepository.save(student);
        if (createdStudent == null) {
            logger.error("Error: createdStudent is null");
        } else {
            logger.info("Created student: {}", createdStudent);
        }
        return createdStudent;
    }

    public Student findStudent(long id) {
        logger.info("Method findStudent called");
        Optional<Student> studentOptional = studentRepository.findById(id);
        return studentOptional.orElse(null);
    }

    public Student updateStudent(Student student) {
        logger.info("Method updateStudent called");
        return studentRepository.save(student);
    }

    public Student deleteStudent(long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isPresent()) {
            studentRepository.deleteById(id);
            logger.info("Student with id = {} deleted successfully.", id);
            return studentOptional.get();
        } else {
            logger.warn("Attempt to delete non-existent student with id = " + id);
        }
        return null;
    }

    public Student getStudentById(Long id) {
        logger.info("Method getStudentById called");
        Optional<Student> optionalStudent = studentRepository.findById(id);
        return optionalStudent.orElse(null);
    }

    public Collection<Student> getAllStudents() {
        logger.debug("Method getAllStudents called");
        Collection<Student> allStudents = studentRepository.findAll();
        logger.debug("Number of students returned: {}", allStudents.size());
        return allStudents;
    }

    public Collection<Student> findByAgeBetween(Integer min, Integer max) {
        logger.info("Method findByAgeBetween called");
        return studentRepository.findByAgeBetween(min, max);
    }
    public List<String> getAllStudentNames() {
        return studentRepository.findAll()
                .stream()
                .map(Student::getName)
                .collect(Collectors.toList());
    }
    public void printStudentNames(){
        List<String> studentNames = getAllStudentNames();
        studentNames.subList(0, 2).forEach(System.out::println);

        new Thread(() -> {
            studentNames.subList(2, 4).forEach(System.out::println);
        }).start();


        new Thread(() -> {
            studentNames.subList(4, 6).forEach(System.out::println);
        }).start();
    }

    public synchronized void printStudentNamesSync() {
        List<String> studentNames = getAllStudentNames();
        studentNames.subList(0, 2).forEach(System.out::println);

        new Thread(() -> {
            studentNames.subList(2, 4).forEach(System.out::println);
        }).start();


        new Thread(() -> {
            studentNames.subList(4, 6).forEach(System.out::println);
        }).start();
    }
}