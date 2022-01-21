package com.beyou.admin.user.export;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.beyou.admin.AbstractExporter;
import com.beyou.common.entity.User;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

public class UserCsvExporter extends AbstractExporter {

    public void export(List<User> listUsers,HttpServletResponse response) throws IOException{

        super.setResponseHeader(response,"text/csv",".csv","users");

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader ={"User ID","Email-ID","First Name","Last Name","Roles","Enabled"};
        String[] fieldMapping ={"id","email","firstName","lastName","roles","enabled"};
        
        csvWriter.writeHeader(csvHeader);

        for(User user : listUsers){
            csvWriter.write(user, fieldMapping);
        }
        
        csvWriter.close();
    }
}
