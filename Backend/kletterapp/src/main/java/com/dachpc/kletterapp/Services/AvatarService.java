package com.dachpc.kletterapp.Services;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dachpc.kletterapp.Entities.User;
import com.dachpc.kletterapp.Repositories.UserRepository;

@Service
public class AvatarService {

    @Autowired
    private UserRepository userRepository;

    @Value("${app.upload.dir}")
    private String uploadDir;

    private Path getUploadRoot() {
        return Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    public AvatarData getAvatar(String userId) {
        String bildUrl = userRepository.getReferenceById(userId).getBildUrl();
        if (bildUrl == null || bildUrl.isBlank()) {
            try {
                byte[] defaultBytes = Files.readAllBytes(getUploadRoot().resolve("default-pic.png"));
                return new AvatarData(defaultBytes, "image/png");
            } catch (IOException e) {
                throw new RuntimeException("Fehler beim Laden des Standard-Avatars", e);
            }
        }
        try {
            Path avatarPath = getUploadRoot().resolve(bildUrl).normalize();
            byte[] bytes = Files.readAllBytes(avatarPath);
            return new AvatarData(bytes, Files.probeContentType(avatarPath));
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Laden des Avatars: " + e.getMessage(), e);
        }
    }

    public Path storeAvatar(String userId,MultipartFile file) {
        validateImage(file);

        String ext = switch (file.getContentType()) {
            case "image/png"     -> "png";
            case "image/webp"    -> "webp";
            case "image/svg+xml" -> "svg";
            case "image/gif"     -> "gif";
            default              -> "jpg";
        };

        String filename = userId + "." + ext;

        Path uploadPath = getUploadRoot();
        Path fullPath = uploadPath.resolve(filename);

        try {
            User user = userRepository.findById(userId).orElseThrow();
            String oldExt = user.getBildUrl() != null ? user.getBildUrl().substring(user.getBildUrl().lastIndexOf(".") + 1) : null;
            Path oldPath = oldExt != null ? uploadPath.resolve(user.getBildUrl()) : null;
            Files.createDirectories(uploadPath);
            try (InputStream in = file.getInputStream()) {
                Files.copy(in, fullPath, StandardCopyOption.REPLACE_EXISTING);
            }
            user.setBildUrl(filename);
            userRepository.save(user);
            if (oldExt != null && !oldExt.equals(ext)) {
                Files.deleteIfExists(oldPath);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Could not store avatar: " + ex.getMessage(), ex);
        }
        return fullPath;
    }


    private void validateImage(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Datei darf nicht leer sein.");
        }
        
        String contentType = file.getContentType();
        if (!List.of("image/jpeg", "image/png", "image/webp", "image/svg+xml", "image/gif").contains(contentType)) {
            throw new IllegalArgumentException("Typ nicht erlaubt. Erlaubt sind: JPEG, PNG, WEBP, SVG, GIF.");
        }

        if (file.getSize() > 1024 * 1024) {
            throw new IllegalArgumentException("Datei darf nicht größer als 1 MB sein.");
        }

        try (InputStream in = file.getInputStream()) {
            BufferedImage image = ImageIO.read(in);
            if (image == null) {
                throw new IllegalArgumentException("Datei ist kein gültiges Bild.");
            }
        } catch (IOException ex) {
            throw new IllegalStateException("Konnte hochgeladene Datei nicht lesen");
        }
    }
    
}
