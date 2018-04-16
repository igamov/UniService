package io.vscale.uniservice.services.implementations.excel;

import io.vscale.uniservice.services.interfaces.excel.ExcelService;
import io.vscale.uniservice.utils.ExcelReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private ExcelReader excelReader;

    @Override
    public void save(FileInputStream fileInputStream) {
        excelReader.readAndSave(fileInputStream);

        
    }

}
