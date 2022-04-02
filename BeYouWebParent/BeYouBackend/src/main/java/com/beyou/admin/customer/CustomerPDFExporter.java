package com.beyou.admin.customer;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.beyou.admin.AbstractExporter;
import com.beyou.common.entity.Customer;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class CustomerPDFExporter extends AbstractExporter {
    public void export(List<Customer> listCustomers,HttpServletResponse response) throws IOException{

        super.setResponseHeader(response,"application/pdf",".pdf","customers");

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document,response.getOutputStream());

        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph paragraph = new Paragraph("List of customers",font);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);

        PdfPTable table = new PdfPTable(11);
        table.setWidthPercentage(100f);
        table.setSpacingBefore(15);
        table.setWidths(new float[] {2.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f });

        writeTableHeader(table);
        writeTableData(table, listCustomers);


        document.add(table);

        document.close();

    }

    private void writeTableData(PdfPTable table, List<Customer> listCustomers) {
        for(Customer customer : listCustomers){
            table.addCell(String.valueOf(customer.getId()));
            table.addCell(customer.getEmail());
            table.addCell(customer.getFirstName());
            table.addCell(customer.getLastName());
            table.addCell(customer.getPhoneNumber());
            table.addCell(customer.getAddressLine1());
            table.addCell(customer.getAddressLine2());
            table.addCell(customer.getCity());
            table.addCell(customer.getState());
            table.addCell(customer.getCountry().getName());
            table.addCell(customer.getPostalCode());
            
        }
    }

    private void writeTableHeader(PdfPTable table){
        PdfPCell cell =new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ID",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Email-ID",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("First Name",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Last Name",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Phone Number",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Address Line 1",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Address Line 2",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("City",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("State",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Country",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Postal Code",font));
        table.addCell(cell);


        

    }
}
