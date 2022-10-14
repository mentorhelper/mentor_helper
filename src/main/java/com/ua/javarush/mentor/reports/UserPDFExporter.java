package com.ua.javarush.mentor.reports;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.ua.javarush.mentor.persist.model.User;

public class UserPDFExporter {
    private List<User> listUsers;

    public UserPDFExporter(List<User> listUsers) {
        this.listUsers = listUsers;
    }

    private void writeTableHeader (PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("First Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Last Name", font));
        table.addCell(cell);

        cell.setPhrase((new Phrase("Country", font)));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Registered_at", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Telegram nickname", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Role id", font));
        table.addCell(cell);
    }

    private void writeTableData (PdfPTable table) {
        for (User user : listUsers) {
            table.addCell(user.getFirstName());
            table.addCell(user.getLastName());
            table.addCell(user.getCountry());
            table.addCell(String.valueOf(user.getRegisteredAt()));
            table.addCell(user.getTelegramNickname());
            table.addCell(String.valueOf(user.getRoleId()));
        }
    }

    public void export (HttpServletResponse response) throws  DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLACK);

        Paragraph p = new Paragraph("USER REPORT", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {3.5f, 3.5f, 2.0f, 1.5f, 3.5f, 2.0f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();
    }
}
