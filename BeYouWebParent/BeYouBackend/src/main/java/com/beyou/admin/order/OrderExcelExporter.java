package com.beyou.admin.order;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.beyou.admin.AbstractExporter;
import com.beyou.common.entity.order.Order;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class OrderExcelExporter extends AbstractExporter{

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public OrderExcelExporter(){
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine(){
        sheet = workbook.createSheet("Orders");
        XSSFRow row = sheet.createRow(0);

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        cellStyle.setFont(font);

        createCell(row, 0, "Order-ID", cellStyle);
        createCell(row, 1, "Email-ID", cellStyle);
        createCell(row, 2, "First Name", cellStyle);
        createCell(row, 3, "Last Name", cellStyle);
        createCell(row, 4, "Phone Number", cellStyle);
        createCell(row, 5, "Address Line 1", cellStyle);
        createCell(row, 6, "Address Line 2", cellStyle);
        createCell(row, 7, "City", cellStyle);
        createCell(row, 8, "State", cellStyle);
        createCell(row, 9, "Country", cellStyle);
        createCell(row, 10, "Postal Code", cellStyle);
        createCell(row, 11, "Order Time", cellStyle);
        createCell(row, 12, "Shipping Cost", cellStyle);
        createCell(row, 13, "Product Cost", cellStyle);
        createCell(row, 14, "SubTotal", cellStyle);
        createCell(row, 15, "Tax", cellStyle);
        createCell(row, 16, "Total", cellStyle);
        createCell(row, 17, "Deliver Days", cellStyle);
        createCell(row, 18, "Delivery Date", cellStyle);
        createCell(row, 19, "Payment Method", cellStyle);
        createCell(row, 20, "Order Status", cellStyle);

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
        else if(value instanceof Float){
            style.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
            cell.setCellValue((Float) value);
        }
        else{
            cell.setCellValue((String) value);
        }
        
        cell.setCellStyle(style);
    }
    public void export(List<Order> listOrders,HttpServletResponse response) throws IOException{

        super.setResponseHeader(response,"application/octet-stream",".xlsx","orders");
    
        writeHeaderLine();
        writeDataLines(listOrders);

        ServletOutputStream outputStream =response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

    }

    private void writeDataLines(List<Order> listOrders){
        int rowIndex = 1;

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(12);
        cellStyle.setFont(font);

        for(Order order : listOrders){
            XSSFRow row = sheet.createRow(rowIndex++);
            int columnIndex = 0;

            createCell(row, columnIndex++, order.getId(), cellStyle);
            createCell(row, columnIndex++, order.getCustomer().getEmail(),cellStyle);
            createCell(row, columnIndex++, order.getFirstName(), cellStyle);
            createCell(row, columnIndex++, order.getLastName(), cellStyle);
            createCell(row, columnIndex++, order.getPhoneNumber(), cellStyle);
            createCell(row, columnIndex++, order.getAddressLine1(), cellStyle);
            createCell(row, columnIndex++, order.getAddressLine2(), cellStyle);
            createCell(row, columnIndex++, order.getCity(), cellStyle);
            createCell(row, columnIndex++, order.getState(), cellStyle);
            createCell(row, columnIndex++, order.getCountry(), cellStyle);
            createCell(row, columnIndex++, order.getPostalCode(), cellStyle);
            createCell(row, columnIndex++, String.valueOf(order.getOrderTime()), cellStyle);
            createCell(row, columnIndex++, order.getShippingCost(), cellStyle);
            createCell(row, columnIndex++, order.getProductCost(), cellStyle);
            createCell(row, columnIndex++, order.getSubtotal(), cellStyle);
            createCell(row, columnIndex++, order.getTax(), cellStyle);
            createCell(row, columnIndex++, order.getTotal(), cellStyle);
            createCell(row, columnIndex++, order.getDeliverDays(), cellStyle);
            createCell(row, columnIndex++, String.valueOf(order.getDeliverDate()), cellStyle);
            createCell(row, columnIndex++, String.valueOf(order.getPaymentMethod()), cellStyle);
            createCell(row, columnIndex++, String.valueOf(order.getStatus()), cellStyle);
        }
    }
}
