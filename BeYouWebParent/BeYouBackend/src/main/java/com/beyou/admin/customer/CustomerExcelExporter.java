package com.beyou.admin.customer;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.beyou.admin.AbstractExporter;
import com.beyou.common.entity.Customer;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CustomerExcelExporter extends AbstractExporter{

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public CustomerExcelExporter(){
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine(){
        sheet = workbook.createSheet("Customers");
        XSSFRow row = sheet.createRow(0);

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        cellStyle.setFont(font);

        createCell(row, 0, "User-ID", cellStyle);
        createCell(row, 1, "E-mail", cellStyle);
        createCell(row, 2, "First Name", cellStyle);
        createCell(row, 3, "Last Name", cellStyle);
        createCell(row, 4, "Phone Number", cellStyle);
        createCell(row, 5, "Address Line 1", cellStyle);
        createCell(row, 6, "Address Line 2", cellStyle);
        createCell(row, 7, "City", cellStyle);
        createCell(row, 8, "State", cellStyle);
        createCell(row, 9, "Country", cellStyle);
        createCell(row, 10, "Postal Code", cellStyle);

    }

    private void createCell(XSSFRow row,int columnIndex,Object value, CellStyle style){
        XSSFCell cell = row.createCell(columnIndex);
        sheet.autoSizeColumn(columnIndex);

        if(value instanceof Integer){
            cell.setCellValue((Integer) value);
        }
        else if(value instanceof Boolean){
            cell.setCellValue((Boolean) value);
        }
        else{
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
    public void export(List<Customer> listCustomers,HttpServletResponse response) throws IOException{

        super.setResponseHeader(response,"application/octet-stream",".xlsx","customers");
    
        writeHeaderLine();
        writeDataLines(listCustomers);

        ServletOutputStream outputStream =response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

    }

    private void writeDataLines(List<Customer> listCustomers){
        int rowIndex = 1;

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(12);
        cellStyle.setFont(font);

        for(Customer customer : listCustomers){
            XSSFRow row = sheet.createRow(rowIndex++);
            int columnIndex = 0;

            createCell(row, columnIndex++, customer.getId(), cellStyle);
            createCell(row, columnIndex++, customer.getEmail(), cellStyle);
            createCell(row, columnIndex++, customer.getFirstName(), cellStyle);
            createCell(row, columnIndex++, customer.getLastName(), cellStyle);
            createCell(row, columnIndex++, customer.getPhoneNumber(), cellStyle);
            createCell(row, columnIndex++, customer.getAddressLine1(), cellStyle);
            createCell(row, columnIndex++, customer.getAddressLine2(), cellStyle);
            createCell(row, columnIndex++, customer.getCity(), cellStyle);
            createCell(row, columnIndex++, customer.getState(), cellStyle);
            createCell(row, columnIndex++, customer.getCountry().getName(), cellStyle);
            createCell(row, columnIndex++, customer.getPostalCode(), cellStyle);
        }
    }
}
