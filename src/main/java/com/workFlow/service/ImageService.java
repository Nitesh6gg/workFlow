package com.workFlow.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String processImageAndGenerateUrl(MultipartFile file);
}
