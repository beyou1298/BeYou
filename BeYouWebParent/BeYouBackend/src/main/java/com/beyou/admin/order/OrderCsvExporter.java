package com.beyou.admin.order;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.beyou.admin.AbstractExporter;
import com.beyou.common.entity.order.Order;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

public class OrderCsvExporter extends AbstractExporter {

    public void export(List<Order> listOrders,HttpServletResponse response) throws IOException{

        super.setResponseHeader(response,"text/csv",".csv","orders");

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader ={"Order ID","First Name","Last Name","Phone Number","Address Line1","Address Line2","City","State","Country","PostalCode","Order Time","Shipping Cost","Product Cost","SubTotal","Tax","Total","Deliver Days","Delivery Date","Payment Method","Order Status"};
        String[] fieldMapping ={"id","firstName","lastName","phoneNumber","addressLine1","addressLine2","city","state","country","postalCode","orderTime","shippingCost","productCost","subTotal","tax","total","deliverDays","deliveryDate","paymentMethod","orderStatus"};
        
        csvWriter.writeHeader(csvHeader);

        for(Order order : listOrders){
            csvWriter.write(order, fieldMapping);
        }
        
        csvWriter.close();
    }
}
