package io.vscale.uniservice.utils;

import io.vscale.uniservice.domain.ExcelEntity;
import io.vscale.uniservice.repositories.data.ExcelEntityRepository;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;

@Component
public class ExcelReader {

    @Autowired
    private ExcelEntityRepository excelEntityRepository;

    @SneakyThrows
    public void readAndSave(FileInputStream fileInputStream){
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        for (int i = 1; i <= sheet.getLastRowNum(); i++){
            Row row = sheet.getRow(i);
            for (int j = 0 ; i < row.getLastCellNum(); i++){
                ExcelEntity entity = ExcelEntity.builder()
                        .name(row.getCell(0).getStringCellValue())
                        .surname(row.getCell(1).getStringCellValue())
                        .patronymic(row.getCell(2).getStringCellValue())
                        .mark((int) row.getCell(3).getNumericCellValue())
                        .subject(row.getCell(4).getStringCellValue())
                        .build();
                excelEntityRepository.save(entity);
            }
        }
        fileInputStream.close();
    }

}
