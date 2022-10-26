package com.ua.javarush.mentor.command;

import com.ua.javarush.mentor.enums.AppLocale;
import com.ua.javarush.mentor.enums.EmailTemplates;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendEmailCommand {
    private String email;
    private EmailTemplates emailTemplate;
    private Map<String, String> params;
    private AppLocale locale;
}

