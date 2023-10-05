package ru.hogwarts.school.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.StudentAvatar;

import java.util.List;
import java.util.Optional;

public interface StudentAvatarRepository extends JpaRepository<StudentAvatar, Long> {

    Optional<StudentAvatar> findByStudentId(Long studentId);

    Page<StudentAvatar> findAll(Pageable pageable);
}
