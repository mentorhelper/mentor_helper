package com.ua.javarush.mentor.services.impl;

import com.ua.javarush.mentor.command.SendEmailCommand;
import com.ua.javarush.mentor.enums.AppLocale;
import com.ua.javarush.mentor.enums.EmailTemplates;
import com.ua.javarush.mentor.enums.NotificationProvider;
import com.ua.javarush.mentor.exceptions.Error;
import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.services.EmailService;
import com.ua.javarush.mentor.services.NotificationService;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static com.ua.javarush.mentor.exceptions.GeneralExceptionUtils.createGeneralException;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    private static final String CONTENT_TYPE = "text/html; charset=utf-8";
    @Value("${spring.mail.username}")
    private String username;

    private final JavaMailSender mailSender;
    private final Configuration freeMarkerConfiguration;
    private final NotificationService notificationService;
    private final MessageSource messageSource;

    public EmailServiceImpl(JavaMailSender mailSender, Configuration freeMarkerConfiguration, NotificationService notificationService, MessageSource messageSource) {
        this.mailSender = mailSender;
        this.freeMarkerConfiguration = freeMarkerConfiguration;
        this.notificationService = notificationService;
        this.messageSource = messageSource;
    }

    @Override
    public void sendConfirmationEmail(SendEmailCommand sendEmailCommand) throws GeneralException {
        log.info("Sending confirmation email to {}", sendEmailCommand.getEmail());
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            mimeMessage.setFrom(username);
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendEmailCommand.getEmail()));
            mimeMessage.setSubject(messageSource.getMessage(sendEmailCommand.getEmailTemplate().getSubject(), null, sendEmailCommand.getLocale().getLocaleObject()));
            mimeMessage.setContent(generateMailContent(getEmailContent(sendEmailCommand)));
            mailSender.send(mimeMessage);
            log.info("Send mail to {}", sendEmailCommand.getEmail());

            notificationService.saveNotification(notificationService.createNotification(sendEmailCommand, NotificationProvider.EMAIL));
        } catch (Exception e) {
            log.error("Error send email to " + sendEmailCommand.getEmail() + ". " + e.getLocalizedMessage());
            throw createGeneralException("Cannot send email", HttpStatus.NOT_FOUND, Error.EMAIL_SEND_ERROR);
        }
    }

    @Override
    public void sendResetPasswordEmail(SendEmailCommand sendEmailCommand) throws GeneralException {
        log.info("Sending reset password email to {}", sendEmailCommand.getEmail());
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            mimeMessage.setFrom(username);
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendEmailCommand.getEmail()));
            mimeMessage.setSubject(messageSource.getMessage(sendEmailCommand.getEmailTemplate().getSubject(), null, sendEmailCommand.getLocale().getLocaleObject()));
            mimeMessage.setContent(generateMailContent(getEmailContent(sendEmailCommand)));
            mailSender.send(mimeMessage);
            log.info("Send mail to {}", sendEmailCommand.getEmail());

            notificationService.saveNotification(notificationService.createNotification(sendEmailCommand, NotificationProvider.EMAIL));
        } catch (Exception e) {
            log.error("Error send email to " + sendEmailCommand.getEmail() + ". " + e.getLocalizedMessage());
            throw createGeneralException("Cannot send email", HttpStatus.NOT_FOUND, Error.EMAIL_SEND_ERROR);
        }
    }

    @Override
    public SendEmailCommand buildEmail(String email, AppLocale appLocale, EmailTemplates emailTemplates, Map<String, String> newParams) {
        return SendEmailCommand.builder()
                .email(email)
                .locale(appLocale != null ? appLocale : AppLocale.EN)
                .params(newParams)
                .emailTemplate(emailTemplates)
                .build();
    }

    private String getEmailContent(SendEmailCommand sendEmailCommand) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("titleName", sendEmailCommand.getEmailTemplate().getSubject());
        if (sendEmailCommand.getParams() != null) {
            model.putAll(sendEmailCommand.getParams());
        }
        freeMarkerConfiguration.getTemplate(sendEmailCommand.getEmailTemplate().getEmailTemplate()).process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }


    private Multipart generateMailContent(String message) throws MessagingException {
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(message, CONTENT_TYPE);
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        return multipart;
    }
}
