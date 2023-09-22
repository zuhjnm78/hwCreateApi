package ru.hogwarts.school.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.model.StudentAvatar;
import ru.hogwarts.school.service.StudentAvatarService;
import ru.hogwarts.school.service.StudentService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

@RestController
@RequestMapping("students")
public class StudentController {
    private final StudentService studentService;
    private final StudentAvatarService studentAvatarService;

    public StudentController(StudentService studentService, StudentAvatarService studentAvatarService) {
        this.studentService = studentService;
        this.studentAvatarService = studentAvatarService;
    }


    @GetMapping("{id}/faculty")
    public ResponseEntity<Faculty> getStudentFaculty(@PathVariable Long id) {
        Faculty faculty = studentService.getStudentFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {

        return studentService.createStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentService.editStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteStudent(@PathVariable Long id) {
        Student deletedStudent = studentService.deleteStudent(id);

        if (deletedStudent != null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/{studentId}/avatar" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadCover(@PathVariable Long studentId, @RequestParam MultipartFile avatar) throws IOException {
            if(avatar.getSize()>=1024*300) {
        return ResponseEntity.badRequest().body("file is too big");
    }
            studentAvatarService.uploadStudentAvatar(studentId, avatar);
            return ResponseEntity.ok().build();
}

    @GetMapping(value = "/{id}/avatar/preview")
    public ResponseEntity<byte[]> downloadCover(@PathVariable Long id) {
        StudentAvatar studentAvatar = studentAvatarService.findStudentAvatar(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(studentAvatar.getMediaType()));
        headers.setContentLength(studentAvatar.getPreview().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(studentAvatar.getPreview());
    }
    @GetMapping (value = "/{id}/avatar")
    public void downloadCover (@PathVariable Long id, HttpServletResponse response) throws IOException {
        StudentAvatar studentAvatar = studentAvatarService.findStudentAvatar(id);
        Path path = Path.of(studentAvatar.getFilePath());
        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();) {
            response.setContentType(studentAvatar.getMediaType());
            response.setContentLength((int) studentAvatar.getFileSize());
            is.transferTo(os);
        }
    }

        @GetMapping("filterByAge")
        public ResponseEntity<Collection<Student>> filterStudentsByAge ( @RequestParam int age){
            Collection<Student> filteredStudents = studentService.filterStudentsByAge(age);
            return ResponseEntity.ok(filteredStudents);
        }

        @GetMapping("filterByAgeRange")
        public ResponseEntity<Collection<Student>> filterStudentsByAgeRange (
                @RequestParam int min, @RequestParam int max){
            Collection<Student> filteredStudents = studentService.findStudentsByAgeRange(min, max);
            return ResponseEntity.ok(filteredStudents);
        }
    }

