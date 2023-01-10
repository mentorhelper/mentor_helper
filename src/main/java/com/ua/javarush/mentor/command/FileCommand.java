package com.ua.javarush.mentor.command;

import com.ua.javarush.mentor.enums.UploadingType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "File command")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileCommand {
    private MultipartFile[] files;
    private UploadingType uploadingType;
}
