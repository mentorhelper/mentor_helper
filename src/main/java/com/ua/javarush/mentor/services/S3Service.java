package com.ua.javarush.mentor.services;

import com.ua.javarush.mentor.dto.FileDTO;
import com.ua.javarush.mentor.exceptions.GeneralException;
import org.springframework.web.multipart.MultipartFile;

public interface S3Service {

    FileDTO uploadFile(MultipartFile multipartFile) throws GeneralException;

    void deleteFile(String fileName);

    FileDTO getFile(String fileName);
}
