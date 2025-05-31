package ru.hogvartz.school.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.hogvartz.school.Model.Avatar;
import ru.hogvartz.school.Model.Student;
import ru.hogvartz.school.Repository.AvatarRepository;
import ru.hogvartz.school.Repository.StudentRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

@Service
public class AvatarService {

    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;

    @Value("${avatar.dir.path}")
    private String avatarsDir;

    public AvatarService(AvatarRepository avatarRepository, StudentRepository studentRepository) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
    }

    @Transactional
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        Student student = studentRepository.getById(studentId);

        String extension = getExtensions(avatarFile.getOriginalFilename());
        Path filePath = Path.of(avatarsDir, studentId + "." + extension);

        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, StandardOpenOption.CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }

        Avatar avatar = avatarRepository.findByStudentId(studentId).orElse(new Avatar());

        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());

        avatarRepository.save(avatar);
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }


    public byte[] getAvatarFromDb(Long studentId) {
        return avatarRepository.findByStudentId(studentId)
                .map(Avatar::getData)
                .orElseThrow(() -> new RuntimeException("Avatar not found"));
    }

    public byte[] getAvatarFromFile(Long studentId) throws IOException {
        Avatar avatar = avatarRepository.findByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("Avatar not found"));

        return Files.readAllBytes(Paths.get(avatar.getFilePath()));
    }

    @Transactional(readOnly = true)
    public Avatar findAvatar(Long studentId) {
        return avatarRepository.findByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("Avatar not found for student with id: " + studentId));
    }
}
