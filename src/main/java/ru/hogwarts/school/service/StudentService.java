package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {

        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student) {

        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {

        studentRepository.deleteById(id);
    }

    public Collection<Student> getAllStudents() {

        return studentRepository.findAll();
    }

    public Collection<Student> filterStudentsByAge(int age) {

        return studentRepository.findByAge(age);
    }
    public Collection<Student> findStudentsByAgeRange(int min,int max) {

        return studentRepository.findByAgeBetween(min,max);
    }
}
