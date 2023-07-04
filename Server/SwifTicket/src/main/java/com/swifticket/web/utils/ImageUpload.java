package com.swifticket.web.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Component
public class ImageUpload {
    private static final List<String> SUPPORTED_IMAGE_TYPES = Arrays.asList(
            "image/jpeg",
            "image/png",
            "image/webp",
            "image/webp2",
            "image/gif",
            "image/svg+xml"
    );
    private static final long MAX_IMAGE_SIZE = 5 * 1024 * 1024; // 5MB

    private final Cloudinary cloudinary;

    public ImageUpload(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile image) {
        try {
            // Validate image type
            String contentType = image.getContentType();
            if (!SUPPORTED_IMAGE_TYPES.contains(contentType)) {
                return null;
            }

            // Validate image size
            long imageSize = image.getSize();
            if (imageSize > MAX_IMAGE_SIZE) {
                return null;
            }

            // Upload the image to Cloudinary
            Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());

            // Process the upload result as needed
            return (String) uploadResult.get("secure_url");
        } catch (Exception e) {
            System.out.println("Failed to upload image" + e.getMessage());
            return null;
        }
    }
}

