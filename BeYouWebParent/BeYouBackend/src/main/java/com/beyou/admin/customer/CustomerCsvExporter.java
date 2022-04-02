package com.beyou.admin.customer;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.beyou.admin.AbstractExporter;
import com.beyou.common.entity.Customer;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

public class CustomerCsvExporter extends AbstractExporter {

    public void export(List<Customer> listCustomers,HttpServletResponse response) throws IOException{

        super.setResponseHeader(response,"text/csv",".csv","customers");

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader ={"Customer ID","Email-ID","First Name","Last Name","Phone Number","Address Line1","Address Line2","City","State","Country","PostalCode","Enabled"};
        String[] fieldMapping ={"id","email","firstName","lastName","phoneNumber","addressLine1","addressLine2","city","state","country","postalCode","enabled"};
        
        csvWriter.writeHeader(csvHeader);

        for(Customer customer : listCustomers){
            csvWriter.write(customer, fieldMapping);
        }
        
        csvWriter.close();
    }
}
