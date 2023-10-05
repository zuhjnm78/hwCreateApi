package ru.hogwarts.school.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.model.StudentAvatar;
import ru.hogwarts.school.repositories.StudentAvatarRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.cert.Extension;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class StudentAvatarService {
    @Value("avatars")
    private String avatarsDir;

    private final StudentRepository studentRepository;

    private final StudentAvatarRepository studentAvatarRepository;

    public StudentAvatarService(StudentRepository studentRepository, StudentAvatarRepository studentAvatarRepository) {
        this.studentRepository = studentRepository;
        this.studentAvatarRepository = studentAvatarRepository;
    }

    public void uploadStudentAvatar(Long studentId, MultipartFile file) throws IOException {
        Student student = studentRepository.getById(studentId);
        Path filePath = Path.of(avatarsDir, studentId + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = file.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }


        StudentAvatar studentAvatar = findStudentAvatar(studentId);
        studentAvatar.setFilePath(filePath.toString());
        studentAvatar.setMediaType(file.getContentType());
        studentAvatar.setFileSize(file.getSize());
        studentAvatar.setStudent(student);
        studentAvatar.setPreview(generateImagePreview(filePath));

        studentAvatarRepository.save(studentAvatar);

    }


    public StudentAvatar findStudentAvatar(Long studentId) {
        return studentAvatarRepository.findByStudentId(studentId).orElse(new StudentAvatar());
    }

    private byte[] generateImagePreview(Path filePath) throws IOException {
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);
            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, height, null);
            graphics.dispose();
            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }

    private String getExtension(String fileName) {
        Extension[] extensions = new Extension[0];
        return fileName.substring(fileName.lastIndexOf(".") + 1);

    }
    public List<StudentAvatar> getAllAvatars(Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return studentAvatarRepository.findAll(pageRequest).getContent();
    }

}





