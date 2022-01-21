package com.beyou.admin.brand;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.beyou.admin.AbstractExporter;
import com.beyou.common.entity.Brand;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

public class BrandCsvExporter extends AbstractExporter {

    public void export(List<Brand> listBrands,HttpServletResponse response) throws IOException{

        super.setResponseHeader(response,"text/csv",".csv","brands");

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader ={"Brand ID","Brand Name"};
        String[] fieldMapping ={"id","name"};
        
        csvWriter.writeHeader(csvHeader);

        for(Brand user : listBrands){
            csvWriter.write(user, fieldMapping);
        }
        
        csvWriter.close();
    }
}
