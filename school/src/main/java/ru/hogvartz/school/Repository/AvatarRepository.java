package ru.hogvartz.school.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogvartz.school.Model.Avatar;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Optional<Avatar> findByStudentId(Long studentId);
}
