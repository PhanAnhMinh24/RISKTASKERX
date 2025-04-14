package com.wbsrisktaskerx.wbsrisktaskerx.utils;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.ExportConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.data.PaymentOptions;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.*;
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
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ExcelUtils {
    public static String[] HEADER = {"Customer ID", "Customer Name", "Phone number",
            "Address", "Email", "Tier", "Actions"};
    public static String[] PURCHASE_HISTORY_HEADER = {
            "Customer Name", "Customer ID", "Active Status", "Date of Birth",
            "Phone Number", "Email", "Address", "Sales Representative", "Service Center",
            "Vehicle Identification Number", "Price", "Payment Method", "Invoice Number",
            "Warranty Start Date", "Warranty Expired Date",
            "Initial Payment", "Installment Amount", "Installment Plan", "Remaining Installment Months"
    };
    public static String[] WARRANTY_HISTORY_HEADER = {"Customer Name", "Customer ID", "Active Status",
            "Date of Birth", "Phone Number", "Email", "Address", "Car model", "License Plate",
            "Service Type", "Service Center", "Service Date", "Service Cost"};

    public static String[] ADMIN_HEADER = {"ID", "Full Name", "Email", "Role", "Department", "Last Login", "Is Active"};
    public static ExportResponse customerToExcel(List<CustomerResponse> customerList,
                                                 String password, String fileName) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sheet1");
            createHeader(sheet, workbook, HEADER);

            int rowIndex = 1;
            for (CustomerResponse c : customerList) {
                Row row = sheet.createRow(rowIndex);
                rowIndex++;
                row.createCell(0).setCellValue(c.getId());
                row.createCell(1).setCellValue(c.getFullName());
                row.createCell(2).setCellValue(c.getPhoneNumber());
                row.createCell(3).setCellValue(c.getAddress());
                row.createCell(4).setCellValue(c.getEmail());
                row.createCell(5).setCellValue(c.getTier().toString());
                row.createCell(6).setCellValue(c.getIsActive());
            }

            for (int i = 0; i < HEADER.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream encryptedBaos = encryptExcelFile(workbook, password);

            return ExportResponse.builder()
                    .fileName(fileName)
                    .password(password)
                    .response(encryptedBaos.toByteArray())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ExportResponse purchaseHistoryToExcel(List<PurchaseHistoryResponse> purchaseHistory,
                                                        String password, String fileName) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sheet1");
            createHeader(sheet, workbook, PURCHASE_HISTORY_HEADER);

            int rowIndex = 1;
            for (PurchaseHistoryResponse p : purchaseHistory) {
                Row row = sheet.createRow(rowIndex);
                rowIndex++;
                row.createCell(0).setCellValue(p.getCustomer().getFullName());
                row.createCell(1).setCellValue(p.getCustomer().getId());
                row.createCell(2).setCellValue(p.getCustomer().getIsActive());
                row.createCell(3).setCellValue(p.getCustomer().getDateOfBirth().toLocalDate().toString());
                row.createCell(4).setCellValue(p.getCustomer().getPhoneNumber());
                row.createCell(5).setCellValue(p.getCustomer().getEmail());
                row.createCell(6).setCellValue(p.getCustomer().getAddress());
                row.createCell(7).setCellValue(p.getSalesRepresentative());
                row.createCell(8).setCellValue(String.valueOf(p.getServiceCenter()));
                row.createCell(9).setCellValue(p.getVehicleIdentificationNumber());
                NumberFormat nf = NumberFormat.getNumberInstance(Locale.forLanguageTag(ExportConstants.VI_VN));
                row.createCell(10).setCellValue(nf.format(p.getPayment().getPrice()) + ExportConstants.VND);
                String strPaymentOption = String.valueOf(p.getPayment().getPaymentOption());
                row.createCell(11).setCellValue(strPaymentOption);
                row.createCell(12).setCellValue(p.getPayment().getInvoice());
                row.createCell(13).setCellValue(p.getWarranty().getStartedDate().toLocalDate().toString());
                row.createCell(14).setCellValue(p.getWarranty().getExpiredDate().toLocalDate().toString());

                if (strPaymentOption.equals(String.valueOf(PaymentOptions.Installment))) {
                    row.createCell(15).setCellValue(p.getPayment().getInitialPayment());
                    row.createCell(16).setCellValue(p.getPayment().getInstallmentAmount());
                    row.createCell(17).setCellValue(p.getPayment().getInstallmentPlan());
                    row.createCell(18).setCellValue(p.getPayment().getRemainingInstallmentMonths());
                } else {
                    row.createCell(15).setCellValue(ExportConstants.N_A);
                    row.createCell(16).setCellValue(ExportConstants.N_A);
                    row.createCell(17).setCellValue(ExportConstants.N_A);
                    row.createCell(18).setCellValue(ExportConstants.N_A);
                }
            }

            for (int i = 0; i < HEADER.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream encryptedBaos = encryptExcelFile(workbook, password);

            return ExportResponse.builder()
                    .fileName(fileName)
                    .password(password)
                    .response(encryptedBaos.toByteArray())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ExportResponse warrantyHistoryToExcel(List<WarrantyHistoryResponse> warrantyHistory,
                                                        String password, String fileName) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sheet1");
            createHeader(sheet, workbook, WARRANTY_HISTORY_HEADER);

            int rowIndex = 1;
            for (WarrantyHistoryResponse w : warrantyHistory) {
                Row row = sheet.createRow(rowIndex);
                rowIndex++;
                row.createCell(0).setCellValue(w.getCustomer().getFullName());
                row.createCell(1).setCellValue(w.getCustomer().getId());
                row.createCell(2).setCellValue(w.getCustomer().getIsActive());
                row.createCell(3).setCellValue(w.getCustomer().getDateOfBirth().toLocalDate().toString());
                row.createCell(4).setCellValue(w.getCustomer().getPhoneNumber());
                row.createCell(5).setCellValue(w.getCustomer().getEmail());
                row.createCell(6).setCellValue(w.getCustomer().getAddress());
                row.createCell(7).setCellValue(w.getCarModel());
                row.createCell(8).setCellValue(w.getLicensePlate());
                row.createCell(9).setCellValue(w.getServiceType());
                row.createCell(10).setCellValue(w.getServiceCenter());
                row.createCell(11).setCellValue(w.getServiceDate().toLocalDate().toString());
                NumberFormat nf = NumberFormat.getNumberInstance(Locale.forLanguageTag(ExportConstants.VI_VN));
                row.createCell(12).setCellValue(nf.format(w.getServiceCost()) + ExportConstants.VND);
            }

            for (int i = 0; i < HEADER.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream encryptedBaos = encryptExcelFile(workbook, password);

            return ExportResponse.builder()
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

        public static ExportResponse adminToExcel(List<AdminResponse> adminList,
                                                       String password, String fileName) throws IOException {
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Sheet1");
                createHeader(sheet, workbook, ADMIN_HEADER);

                int rowIndex = 1;
                for (AdminResponse a : adminList) {
                    Row row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue(a.getId());
                    row.createCell(1).setCellValue(a.getFullName());
                    row.createCell(2).setCellValue(a.getEmail());
                    row.createCell(3).setCellValue(a.getRole() != null ? a.getRole().getName() : ExportConstants.EMPTY);
                    row.createCell(4).setCellValue(a.getDepartmentName() != null ? a.getDepartmentName().toString() : ExportConstants.EMPTY);
                    row.createCell(5).setCellValue(a.getLastLogin() != null ? a.getLastLogin().toString() : ExportConstants.EMPTY);
                    row.createCell(6).setCellValue(a.getIsActive() != null ? a.getIsActive() : false);
                }

                for (int i = 0; i < ADMIN_HEADER.length; i++) {
                    sheet.autoSizeColumn(i);
                }

                ByteArrayOutputStream encryptedBaos = encryptExcelFile(workbook, password);

                return ExportResponse.builder()
                        .fileName(fileName)
                        .password(password)
                        .response(encryptedBaos.toByteArray())
                        .build();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

}
