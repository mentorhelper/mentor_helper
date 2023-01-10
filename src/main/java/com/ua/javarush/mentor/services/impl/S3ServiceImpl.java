package com.ua.javarush.mentor.services.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.ua.javarush.mentor.dto.FileDTO;
import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.exceptions.UiError;
import com.ua.javarush.mentor.services.S3Service;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Date;

import static com.ua.javarush.mentor.exceptions.GeneralExceptionUtils.createGeneralException;

@Service
@Slf4j
public class S3ServiceImpl implements S3Service {
    private static final String PATH_TO_TEMP_FOLDER = "/tmp/";
    private static final String PATH_TO_FILE_SYSTEM = "file://";
    private static final String LOAD_LINK = "/load";
    public static final String SLASH = "/";
    public static final String SPACE = " ";
    public static final String UNDER_SCORE = "_";

    private static final String ERROR_WHILE_LOADING_FILE_FROM_S_3 = "Error while loading file from S3";
    private static final String FILE_ERROR = "Could not get file {}. Error: {}";
    private static final String COULD_NOT_GET_FILE = "Could not get file";

    private static final char[] ABC_CYR = new char[]{' ', 'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я', 'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static final String[] ABC_LAT = new String[]{" ", "a", "b", "v", "g", "d", "e", "e", "zh", "z", "i", "y", "k", "l", "m", "n", "o", "p", "r", "s", "t", "u", "f", "h", "ts", "ch", "sh", "sch", "", "i", "", "e", "ju", "ja", "A", "B", "V", "G", "D", "E", "E", "Zh", "Z", "I", "Y", "K", "L", "M", "N", "O", "P", "R", "S", "T", "U", "F", "H", "Ts", "Ch", "Sh", "Sch", "", "I", "", "E", "Ju", "Ja", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    @Value("${app.aws.s3.bucket}")
    private String mainBucket;

    @Value("${app.host}")
    private String host;

    @Value("${app.multipart.available.file.types}")
    private String[] allowedFileTypes;

    private final AmazonS3 amazonS3Client;

    @Autowired
    public S3ServiceImpl(AmazonS3 amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    @Override
    public FileDTO uploadFile(MultipartFile multipartFile) throws GeneralException {
        try {
            if (multipartFile == null || multipartFile.isEmpty()) {
                return new FileDTO();
            }
            if (isFileFormatAvailableToUpload(multipartFile)) {
                String filename = generateJpgCorrectFileName(multipartFile);
                amazonS3Client.putObject(mainBucket, filename, multipartFile.getInputStream(), generateMetadata(multipartFile));
                return FileDTO.builder()
                        .url(host + LOAD_LINK + SLASH + filename)
                        .name(filename)
                        .build();
            } else {
                throw createGeneralException("File type not supported. Your file type is " + multipartFile.getContentType() + ". Allowed file types are " + Arrays.toString(allowedFileTypes), HttpStatus.BAD_REQUEST, UiError.FILE_TYPE_NOT_SUPPORTED);
            }
        } catch (IOException e) {
            log.error("Could not store file {}. Error: {}", multipartFile.getOriginalFilename(), e.getMessage());
            throw createGeneralException("Could not store file " + multipartFile.getOriginalFilename(), HttpStatus.INTERNAL_SERVER_ERROR, UiError.FILE_UPLOAD_ERROR);
        }
    }

    @Override
    public void deleteFile(String fileName) {

    }

    @Override
    public FileDTO getFile(String fileName) {
        return null;
    }

    private String generateJpgCorrectFileName(MultipartFile multipartFile) {
        String transliteratedFilename = transliterate(multipartFile.getOriginalFilename()) + multipartFile.getContentType();
        return StringUtils.cleanPath(new Date().getTime() + UNDER_SCORE + StringUtils.replace(transliteratedFilename, SPACE, UNDER_SCORE));
    }

    private UrlResource getResourceFromBucket(String bucketName, String filename) throws GeneralException {
        try {
            S3Object s3object = amazonS3Client.getObject(bucketName, filename);
            S3ObjectInputStream inputStream = s3object.getObjectContent();
            FileUtils.copyInputStreamToFile(inputStream, new File(PATH_TO_TEMP_FOLDER + filename));
            return new UrlResource(PATH_TO_FILE_SYSTEM + PATH_TO_TEMP_FOLDER + filename);
        } catch (AmazonServiceException amazonServiceException) {
            if (amazonServiceException.getStatusCode() == HttpStatus.NOT_FOUND.value()) {
                log.error("File {} not found. Error: {}", filename, amazonServiceException.getMessage());
                throw createGeneralException("File not found", HttpStatus.NOT_FOUND, UiError.FILE_NOT_FOUND);
            } else {
                log.error(FILE_ERROR, filename, amazonServiceException.getMessage());
                throw createGeneralException(COULD_NOT_GET_FILE, HttpStatus.INTERNAL_SERVER_ERROR, UiError.UNABLE_TO_GET_FILE_FROM_S3, amazonServiceException);
            }
        } catch (IOException ioException) {
            log.error(FILE_ERROR, filename, ioException.getMessage());
            throw createGeneralException(COULD_NOT_GET_FILE, HttpStatus.INTERNAL_SERVER_ERROR, UiError.UNABLE_TO_GET_FILE_FROM_S3, ioException);
        }
    }

    private String getContentTypeFromResource(Resource resource) throws GeneralException {
        try {
            return URLConnection.guessContentTypeFromStream(resource.getInputStream());
        } catch (IOException e) {
            throw createGeneralException("File type not determine", HttpStatus.BAD_REQUEST, UiError.FILE_TYPE_NOT_DETERMINE, e);
        }
    }

    private ObjectMetadata generateMetadata(MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        metadata.addUserMetadata("x-amz-meta-title", "MentorHelper");
        return metadata;
    }

    private String transliterate(String message) {
        if (message == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            for (int x = 0; x < ABC_CYR.length; x++) {
                if (message.charAt(i) == ABC_CYR[x]) {
                    builder.append(ABC_LAT[x]);
                }
            }
        }
        return builder.toString();
    }

    private boolean isFileFormatAvailableToUpload(MultipartFile multipartFile) {
        String currentFileFormat = multipartFile.getContentType();
        for (String allowedFileFormat : allowedFileTypes) {
            if (currentFileFormat != null && currentFileFormat.equals(allowedFileFormat)) {
                return true;
            }
        }
        return false;
    }
}
