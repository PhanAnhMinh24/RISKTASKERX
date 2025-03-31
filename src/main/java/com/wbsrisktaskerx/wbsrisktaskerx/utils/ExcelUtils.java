package com.wbsrisktaskerx.wbsrisktaskerx.utils;

import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.CustomerResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.ExportCustomerResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.PurchaseHistoryResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.WarrantyHistoryResponse;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DirectoryNode;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ExcelUtils {
    public static String[] HEADER = {"Customer ID", "Customer Name", "Phone number", "Address", "Email", "Tier", "Actions"};
    public static String[] PURCHASE_HISTORY_HEADER = {"Customer Name", "Customer ID", "Active Status", "Date of Birth",
            "Phone Number", "Email", "Address", "Car Model", "Vehicle Identification Number", "Price", "Payment Method", "Purchase Date"};
    public static String[] WARRANTY_HISTORY_HEADER = {"Customer Name", "Customer ID", "Active Status", "Date of Birth",
            "Phone Number", "Email", "Address", "Car model", "License Plate", "Service Type", "Service Center", "Service Date", "Service Cost"};
    public static ExportCustomerResponse customerToExcel(List<CustomerResponse> customerList, String password, String fileName) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sheet1");
            createHeader(sheet, workbook, HEADER);

            int rowIndex = 1;
            for (CustomerResponse c : customerList) {
                Row row1 = sheet.createRow(rowIndex);
                rowIndex++;
                row1.createCell(0).setCellValue(c.getId());
                row1.createCell(1).setCellValue(c.getFullName());
                row1.createCell(2).setCellValue(c.getPhoneNumber());
                row1.createCell(3).setCellValue(c.getAddress());
                row1.createCell(4).setCellValue(c.getEmail());
                row1.createCell(5).setCellValue(c.getTier().toString());
                row1.createCell(6).setCellValue(c.getIsActive());
            }

            for (int i = 0; i < HEADER.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream encryptedBaos = encryptExcelFile(workbook, password);

            return ExportCustomerResponse.builder()
                    .fileName(fileName)
                    .password(password)
                    .response(encryptedBaos.toByteArray())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ExportCustomerResponse purchaseHistoryToExcel(List<PurchaseHistoryResponse> purchaseHistory, String password, String fileName) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sheet1");
            createHeader(sheet, workbook, PURCHASE_HISTORY_HEADER);

            int rowIndex = 1;
            for (PurchaseHistoryResponse p : purchaseHistory) {
                Row row1 = sheet.createRow(rowIndex);
                rowIndex++;
                row1.createCell(0).setCellValue(p.getCustomer().getFullName());
                row1.createCell(1).setCellValue(p.getCustomer().getId());
                row1.createCell(2).setCellValue(p.getCustomer().getIsActive());
                row1.createCell(3).setCellValue(p.getCustomer().getDateOfBirth());
                row1.createCell(4).setCellValue(p.getCustomer().getPhoneNumber());
                row1.createCell(5).setCellValue(p.getCustomer().getEmail());
                row1.createCell(6).setCellValue(p.getCustomer().getAddress());
                row1.createCell(7).setCellValue(p.getCarModel());
                row1.createCell(8).setCellValue(p.getVehicleIdentificationNumber());
                row1.createCell(9).setCellValue(p.getPrice());
                row1.createCell(10).setCellValue(p.getPaymentMethod());
                row1.createCell(11).setCellValue(p.getPurchaseDate());
            }

            for (int i = 0; i < HEADER.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream encryptedBaos = encryptExcelFile(workbook, password);

            return ExportCustomerResponse.builder()
                    .fileName(fileName)
                    .password(password)
                    .response(encryptedBaos.toByteArray())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ExportCustomerResponse warrantyHistoryToExcel(List<WarrantyHistoryResponse> warrantyHistory, String password, String fileName) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sheet1");
            createHeader(sheet, workbook, WARRANTY_HISTORY_HEADER);

            int rowIndex = 1;
            for (WarrantyHistoryResponse w : warrantyHistory) {
                Row row1 = sheet.createRow(rowIndex);
                rowIndex++;
                row1.createCell(0).setCellValue(w.getCustomer().getFullName());
                row1.createCell(1).setCellValue(w.getCustomer().getId());
                row1.createCell(2).setCellValue(w.getCustomer().getIsActive());
                row1.createCell(3).setCellValue(w.getCustomer().getDateOfBirth());
                row1.createCell(4).setCellValue(w.getCustomer().getPhoneNumber());
                row1.createCell(5).setCellValue(w.getCustomer().getEmail());
                row1.createCell(6).setCellValue(w.getCustomer().getAddress());
                row1.createCell(7).setCellValue(w.getCarModel());
                row1.createCell(8).setCellValue(w.getLicensePlate());
                row1.createCell(9).setCellValue(w.getServiceType());
                row1.createCell(10).setCellValue(w.getServiceCenter());
                row1.createCell(11).setCellValue(w.getServiceDate());
                row1.createCell(12).setCellValue(w.getServiceCost());
            }

            for (int i = 0; i < HEADER.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream encryptedBaos = encryptExcelFile(workbook, password);

            return ExportCustomerResponse.builder()
                    .fileName(fileName)
                    .password(password)
                    .response(encryptedBaos.toByteArray())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ByteArrayOutputStream encryptExcelFile(Workbook workbook, String password) throws IOException {
        try (POIFSFileSystem fs = new POIFSFileSystem()) {
            EncryptionInfo info = new EncryptionInfo(EncryptionMode.agile);
            Encryptor enc = info.getEncryptor();
            enc.confirmPassword(password);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);
            workbook.close();

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());

            DirectoryEntry root = fs.getRoot();
            OutputStream os = enc.getDataStream((DirectoryNode) root);
            IOUtils.copy(bais, os);
            os.close();

            ByteArrayOutputStream encryptedBaos = new ByteArrayOutputStream();
            fs.writeFilesystem(encryptedBaos);

            return encryptedBaos;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void createHeader(Sheet sheet, Workbook workbook, String[] headers) {
        Row headerRow = sheet.createRow(0);
        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        headerStyle.setFont(font);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
            sheet.autoSizeColumn(i);
        }
    }
}
