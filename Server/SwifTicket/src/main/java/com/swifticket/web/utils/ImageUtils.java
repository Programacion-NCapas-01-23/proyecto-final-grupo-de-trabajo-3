package com.swifticket.web.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class ImageUtils {
    /*
    private final Environment environment;
    @Autowired
    public ImageUtils(Environment environment) {
        this.environment = environment;
    }
    */

    public String saveImage(MultipartFile image, String imageDirectory) {
        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("Image file is required.");
        }

        if (StringUtils.isEmpty(imageDirectory)) {
            throw new IllegalArgumentException("Image directory path is required.");
        }

        String imageName = image.getOriginalFilename();
        String extension = StringUtils.getFilenameExtension(imageName);
        String fileName = UUID.randomUUID().toString() + "." + extension;
        Path destinationPath = Paths.get(imageDirectory, fileName);

        try {
            // Save image to disk (create directory if not exists)
            Files.createDirectories(destinationPath.getParent());
            Files.write(destinationPath, image.getBytes());

            return destinationPath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image: " + e.getMessage());
        }
    }
}
