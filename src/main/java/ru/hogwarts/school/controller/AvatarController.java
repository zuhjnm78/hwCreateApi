package ru.hogwarts.school.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.StudentAvatar;
import ru.hogwarts.school.repositories.StudentAvatarRepository;
import ru.hogwarts.school.service.StudentAvatarService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("students")
public class AvatarController {
    private final StudentAvatarService studentAvatarService;
    private final StudentAvatarRepository studentAvatarRepository;

    public AvatarController(StudentAvatarService studentAvatarService, StudentAvatarRepository studentAvatarRepository) {
        this.studentAvatarService = studentAvatarService;
        this.studentAvatarRepository = studentAvatarRepository;
    }

    @PostMapping(value = "/{studentId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadCover(@PathVariable Long studentId, @RequestParam MultipartFile avatar) throws IOException {
        if (avatar.getSize() >= 1024 * 300) {
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

    @GetMapping(value = "/{id}/avatar")
    public void downloadCover(@PathVariable Long id, HttpServletResponse response) throws IOException {
        StudentAvatar studentAvatar = studentAvatarService.findStudentAvatar(id);
        Path path = Path.of(studentAvatar.getFilePath());
        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();) {
            response.setContentType(studentAvatar.getMediaType());
            response.setContentLength((int) studentAvatar.getFileSize());
            is.transferTo(os);
        }
    }
    @GetMapping
    public ResponseEntity<List<StudentAvatar>> getAllAvatars(
            @RequestParam("page") Integer pageNumber,
            @RequestParam("size") Integer pageSize) {
        List<StudentAvatar> avatarPage = studentAvatarService.getAllAvatars(pageNumber,pageSize);

        if (avatarPage.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(avatarPage);
        }
    }
}
