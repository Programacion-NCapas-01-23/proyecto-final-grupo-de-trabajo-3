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
        String imagePath = imageDirectory + "/" + imageName;

        try {
            // Save image to disk (create directory if not exists)
            Path destinationPath = Paths.get(imagePath);
            Files.createDirectories(destinationPath.getParent());
            Files.write(destinationPath, image.getBytes());

            return imagePath;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image: " + e.getMessage());
        }
    }
}
