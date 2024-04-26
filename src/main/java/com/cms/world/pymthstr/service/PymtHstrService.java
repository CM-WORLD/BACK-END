package com.cms.world.pymthstr.service;


import com.cms.world.pymthstr.domain.PymtHstrEntity;
import com.cms.world.pymthstr.repository.PymtHstrRepository;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import org.apache.poi.ss.usermodel.DateUtil;

@Service
@AllArgsConstructor
public class PymtHstrService {

    private final PymtHstrRepository pymtHstrRepository;

    @Transactional
    public void uploadExcel(MultipartFile file) throws Exception {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet worksheet = workbook.getSheetAt(1);

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) { // 말 그대로 시트의 998개의 행이 있으면 그걸 다 돈다
            Row row = worksheet.getRow(i);
            if (row != null) {
                String[] values = new String[6];
                for (int j = 0; j <= 5; j++) {
                    Cell cell = row.getCell(j);
                    if (!isValidCell(cell)) {
                        return;
                    }
                    values[j] = formatCell(cell);
                }

                PymtHstrEntity pymtHstrEntity = PymtHstrEntity.builder()
                        .paymentDate(values[0])
                        .id(Long.parseLong(values[1]))
                        .username(values[2])
                        .amount(Long.parseLong(values[3]))
                        .paymentMethod(values[4])
                        .paymentMethodName(values[5])
                        .build();
                pymtHstrRepository.save(pymtHstrEntity);
            }
        }
    }

    private boolean isValidCell (Cell cell) {
        return cell != null && formatCell(cell) != null && !formatCell(cell).isEmpty();
    }

    private String formatCell (Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf((int) cell.getNumericCellValue()); // 소수점 이하가 없는 경우 정수로 처리
                }
            default:
                return "";
        }
    }
}