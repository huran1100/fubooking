package com.hz.booking.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String upload(MultipartFile file, String path);
}
