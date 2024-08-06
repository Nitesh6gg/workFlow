package com.workFlow.serviceImpl;

import com.workFlow.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    @Value("${profileImageUpload.dir}")
    private String uploadDir;

    @Value("${server.base.url}")
    private String baseUrl;

    @Override
    public String processImageAndGenerateUrl(MultipartFile file) {
        try {
            // Save the file to the specified directory
            String fileName = file.getOriginalFilename();
            Path targetLocation = Paths.get(uploadDir).resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            // Generate the different  URL for the saved file for secured endpoint
            String fileUrl = baseUrl + "/img/profile?filename=" + fileName;
            return fileUrl;
        } catch (Exception ex) {
            log.error("Error while processing image: {}", ex.getMessage());
            return null; // Return null in case of error
        }
    }


}
