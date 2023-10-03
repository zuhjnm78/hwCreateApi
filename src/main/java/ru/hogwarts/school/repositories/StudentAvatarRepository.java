package ru.hogwarts.school.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.StudentAvatar;

import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface StudentAvatarRepository extends JpaRepository<StudentAvatar, Long> {

    Optional<StudentAvatar> findByStudentId(Long studentId);
    Page<StudentAvatar> findByStudentId(Long studentId, Pageable pageable);
}

