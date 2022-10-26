package com.ua.javarush.mentor.reports;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.ua.javarush.mentor.dto.UserDTO;
import com.ua.javarush.mentor.enums.AppLocale;
import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.exceptions.UiError;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.ua.javarush.mentor.exceptions.GeneralExceptionUtils.createGeneralException;

@Service
public class UserPDFExporter {

    private static final String CANNOT_CREATE_USER_REPORT = "Cannot create user report";
    private static final int FONT_SIZE = 18;
    private static final int NUM_COLUMNS = 6;
    private static final float WIDTH_PERCENTAGE = 100f;
    private static final int SPACING = 10;

    private final float[] columnWidths = new float[]{3.5f, 3.5f, 2.0f, 1.5f, 3.5f, 2.0f};
    private final MessageSource messageSource;

    public UserPDFExporter(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public void export(HttpServletResponse response, List<UserDTO> users, AppLocale appLocale) throws GeneralException {
        try (Document document = new Document(PageSize.A4)) {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();


            Paragraph paragraph = generateParagraph("report.user.reportName", appLocale, generateDefaultFont());
            document.add(paragraph);

            PdfPTable table = generateTable();
            writeTableHeader(table, appLocale);
            writeTableData(table, users);

            document.add(table);
        } catch (IOException e) {
            throw createGeneralException(CANNOT_CREATE_USER_REPORT, HttpStatus.BAD_REQUEST, UiError.PDF_EXPORT_ERROR);
        }
    }

    private void writeTableHeader(PdfPTable table, AppLocale appLocale) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        addCell(cell, "report.user.firstName", appLocale, table, font);
        addCell(cell, "report.user.lastName", appLocale, table, font);
        addCell(cell, "report.user.country", appLocale, table, font);
        addCell(cell, "report.user.registrationDate", appLocale, table, font);
        addCell(cell, "report.user.telegramNickname", appLocale, table, font);
        addCell(cell, "report.user.roleName", appLocale, table, font);
    }

    private void addCell(PdfPCell cell, String resourceName, AppLocale appLocale, PdfPTable table, Font font) {
        cell.setPhrase(new Phrase(messageSource.getMessage(resourceName, null, appLocale.getLocaleObject()), font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table, List<UserDTO> users) {
        users.forEach(user -> {
            table.addCell(user.getFirstName());
            table.addCell(user.getLastName());
            table.addCell(user.getCountry());
            table.addCell(user.getRegisteredAt());
            table.addCell(user.getTelegramNickname());
            table.addCell(user.getRoleName());
        });
    }

    private Paragraph generateParagraph(String paragraphName, AppLocale appLocale, Font font) {
        Paragraph paragraph = new Paragraph(messageSource.getMessage(paragraphName, null, appLocale.getLocaleObject()), font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        return paragraph;
    }

    private Font generateDefaultFont() {
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(FONT_SIZE);
        font.setColor(Color.BLACK);
        return font;
    }

    private PdfPTable generateTable() {
        PdfPTable table = new PdfPTable(NUM_COLUMNS);
        table.setWidthPercentage(WIDTH_PERCENTAGE);
        table.setWidths(columnWidths);
        table.setSpacingBefore(SPACING);
        return table;
    }
}
