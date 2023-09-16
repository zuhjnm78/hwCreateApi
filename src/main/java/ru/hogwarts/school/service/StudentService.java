package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    public Faculty getStudentFaculty(long studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student != null) {
            return student.getFaculty();
        }
        return null;
    }

    public Student createStudent(Student student) {

        return studentRepository.save(student);
    }

    public Student findStudent(long id) {

        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student updatedStudent) {

        Student existingStudent = studentRepository.findById(updatedStudent.getId()).orElse(null);

        if (existingStudent != null) {

            existingStudent.setName(updatedStudent.getName());
            existingStudent.setAge(updatedStudent.getAge());

            return studentRepository.save(existingStudent);
        } else {

            return null;
        }
    }

    public Student deleteStudent(long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isPresent()) {
            studentRepository.deleteById(id);
            return studentOptional.get();
        } else {
            return null;
        }
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
