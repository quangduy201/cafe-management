package com.cafe.utils;

import com.cafe.BLL.*;
import com.cafe.DTO.*;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTable;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumns;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class Excel {
    private Workbook workbook;
    private Sheet sheet;
    private CellStyle commonLeftCellStyle, commonRightCellStyle, commonCenterCellStyle;
    private CellStyle boldLeftCellStyle, boldRightCellStyle, boldCenterCellStyle;
    private CellStyle italicLeftCellStyle, italicRightCellStyle, italicCenterCellStyle;
    private CellStyle underlineLeftCellStyle, underlineRightCellStyle, underlineCenterCellStyle;
    private CellStyle strikeoutLeftCellStyle, strikeoutRightCellStyle, strikeoutCenterCellStyle;

    public Excel(String sheetName) {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet(sheetName);
        sheet.setDefaultRowHeightInPoints(18);
        sheet.setDefaultColumnWidth(3);

        Font commonFont = newFont((short) 9, false, false, false, Font.U_NONE);
        Font boldFont = newFont((short) 9, true, false, false, Font.U_NONE);
        Font italicFont = newFont((short) 9, false, true, false, Font.U_NONE);
        Font strikeoutFont = newFont((short) 9, false, false, true, Font.U_NONE);
        Font underlineFont = newFont((short) 9, false, false, false, Font.U_SINGLE);

        commonLeftCellStyle = newCellStyle(commonFont, HorizontalAlignment.LEFT);
        commonRightCellStyle = newCellStyle(commonFont, HorizontalAlignment.RIGHT);
        commonCenterCellStyle = newCellStyle(commonFont, HorizontalAlignment.CENTER);

        boldLeftCellStyle = newCellStyle(boldFont, HorizontalAlignment.LEFT);
        boldRightCellStyle = newCellStyle(boldFont, HorizontalAlignment.RIGHT);
        boldCenterCellStyle = newCellStyle(boldFont, HorizontalAlignment.CENTER);

        italicLeftCellStyle = newCellStyle(italicFont, HorizontalAlignment.LEFT);
        italicRightCellStyle = newCellStyle(italicFont, HorizontalAlignment.RIGHT);
        italicCenterCellStyle = newCellStyle(italicFont, HorizontalAlignment.CENTER);

        strikeoutLeftCellStyle = newCellStyle(strikeoutFont, HorizontalAlignment.LEFT);
        strikeoutRightCellStyle = newCellStyle(strikeoutFont, HorizontalAlignment.RIGHT);
        strikeoutCenterCellStyle = newCellStyle(strikeoutFont, HorizontalAlignment.CENTER);

        underlineLeftCellStyle = newCellStyle(underlineFont, HorizontalAlignment.LEFT);
        underlineRightCellStyle = newCellStyle(underlineFont, HorizontalAlignment.RIGHT);
        underlineCenterCellStyle = newCellStyle(underlineFont, HorizontalAlignment.CENTER);
    }

    public static boolean writeToExcel(Bill bill, String path) {
        try {
            Files.createDirectories(Paths.get(path));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        File file = new File(path + "/" + bill.getBillID() + ".xlsx");
        Excel excel = new Excel("Hóa đơn");
        //--- DECLARE COMPONENTS FOR EXCEL ---//
        // title
        Font titleFont = excel.newFont((short) 14, true, false, false, Font.U_NONE);
        CellStyle titleCellStyle = excel.newCellStyle(titleFont, HorizontalAlignment.CENTER);

        //--- WRITING INTO EXCEL ---//
        /* HEADER */
        // Title
        excel.sheet.createRow(1);
        excel.setCellRange("HÓA ĐƠN", new CellRangeAddress(1, 1, 0, 12), titleCellStyle);

        // Mã hóa đơn
        excel.sheet.createRow(3);
        excel.setCellRange("Mã hóa đơn:", new CellRangeAddress(3, 3, 0, 2), excel.boldLeftCellStyle);
        excel.setCellRange(bill.getBillID(), new CellRangeAddress(3, 3, 3, 6), excel.commonLeftCellStyle);

        // Ngày
        excel.setCellRange("Ngày:", new CellRangeAddress(3, 3, 8, 10), excel.boldLeftCellStyle);
        excel.setCellRange(bill.getDateOfPurchase(), new CellRangeAddress(3, 3, 11, 12), excel.commonLeftCellStyle);

        // Tên khách hàng
        excel.sheet.createRow(4);
        excel.setCellRange("Tên khách hàng:", new CellRangeAddress(4, 4, 0, 2), excel.boldLeftCellStyle);
        Customer customer = new CustomerBLL().searchCustomers("CUSTOMER_ID = '" + bill.getCustomerID() + "'").get(0);
        excel.setCellRange(customer.getName(), new CellRangeAddress(4, 4, 3, 6), excel.commonLeftCellStyle);

        // Mã nhân viên
        excel.setCellRange("Mã nhân viên:", new CellRangeAddress(4, 4, 8, 10), excel.boldLeftCellStyle);
        excel.setCellRange(bill.getStaffID(), new CellRangeAddress(4, 4, 11, 12), excel.commonLeftCellStyle);

        /* ----- */
        excel.sheet.createRow(6);
        excel.setCellRange("--------------------------------------------------------------------------------",
            new CellRangeAddress(6, 6, 1, 11), excel.boldCenterCellStyle);

        /* BILL DETAILS */
        // Table header
        excel.sheet.createRow(8);
        excel.setCell("STT", 8, 0, excel.boldLeftCellStyle);
        excel.setCellRange("Sản phẩm", new CellRangeAddress(8, 8, 1, 4), excel.boldLeftCellStyle);
        excel.setCell("Size", 8, 5, excel.boldRightCellStyle);
        excel.setCell("SL", 8, 6, excel.boldRightCellStyle);
        excel.setCellRange("Giá", new CellRangeAddress(8, 8, 7, 8), excel.boldRightCellStyle);
        excel.setCellRange("Giá giảm", new CellRangeAddress(8, 8, 9, 10), excel.boldRightCellStyle);
        excel.setCellRange("Thành tiền", new CellRangeAddress(8, 8, 11, 12), excel.boldRightCellStyle);

        // Table rows
        List<BillDetails> billDetailsList = new BillDetailsBLL().searchBillDetails("BILL_ID = '" + bill.getBillID() + "'");
        int billSize = billDetailsList.size();
        ProductBLL productBLL = new ProductBLL();
        for (int i = 0; i < billSize; i++) {
            BillDetails billDetails = billDetailsList.get(i);
            Product product = productBLL.searchProducts("PRODUCT_ID = '" + billDetails.getProductID() + "'").get(0);

            excel.sheet.createRow(10 + i);
            excel.setCell(i + 1, 10 + i, 0, excel.commonLeftCellStyle);

            excel.setCellRange(product.getName(), new CellRangeAddress(10 + i, 10 + i, 1, 4), excel.commonLeftCellStyle);

            String productSized = product.getSized().equals("null") ? "" : product.getSized();
            excel.setCell(productSized, 10 + i, 5, excel.commonRightCellStyle);

            excel.setCell(billDetails.getQuantity(), 10 + i, 6, excel.commonRightCellStyle);

            String productCost = VNString.currency(product.getCost());
            excel.setCellRange(productCost, new CellRangeAddress(10 + i, 10 + i, 7, 8), excel.commonRightCellStyle);

            double percent = billDetails.getPercent();
            double total;
            if (percent > 0) {
                double discountedCost = product.getCost() - product.getCost() * percent / 100.0;
                excel.setCellRange(VNString.currency(discountedCost), new CellRangeAddress(10 + i, 10 + i, 9, 10), excel.commonRightCellStyle);
                excel.sheet.getRow(10 + i).getCell(7).setCellStyle(excel.strikeoutRightCellStyle);
                total = billDetails.getQuantity() * discountedCost;
            } else {
                excel.setCellRange("", new CellRangeAddress(10 + i, 10 + i, 9, 10), excel.commonRightCellStyle);
                total = billDetails.getQuantity() * product.getCost();
            }

            excel.setCellRange(VNString.currency(total), new CellRangeAddress(10 + i, 10 + i, 11, 12), excel.commonRightCellStyle);
        }

        /* ----- */
        excel.sheet.createRow(11 + billSize);
        excel.setCellRange("--------------------------------------------------------------------------------",
            new CellRangeAddress(11 + billSize, 11 + billSize, 1, 11), excel.boldCenterCellStyle);

        /* FOOTER */
        // Tổng
        excel.sheet.createRow(13 + billSize);
        String billTotal = VNString.currency(bill.getTotal());
        excel.setCellRange("Tổng tiền:", new CellRangeAddress(13 + billSize, 13 + billSize, 8, 9), excel.boldRightCellStyle);
        excel.setCell(billTotal, 13 + billSize, 10, excel.commonLeftCellStyle);

        // Tiền nhận
        excel.sheet.createRow(14 + billSize);
        String billReceived = VNString.currency(bill.getReceived());
        excel.setCellRange("Tiền nhận:", new CellRangeAddress(14 + billSize, 14 + billSize, 8, 9), excel.boldRightCellStyle);
        excel.setCell(billReceived, 14 + billSize, 10, excel.commonLeftCellStyle);

        // Tiền thối
        excel.sheet.createRow(15 + billSize);
        String billExcess = VNString.currency(bill.getExcess());
        excel.setCellRange("Tiền thối:", new CellRangeAddress(15 + billSize, 15 + billSize, 8, 9), excel.boldRightCellStyle);
        excel.setCell(billExcess, 15 + billSize, 10, excel.commonLeftCellStyle);

        /* BORDER */
        CellRangeAddress billRange = new CellRangeAddress(0, 16 + billDetailsList.size(), 0, 12);
        RegionUtil.setBorderTop(BorderStyle.DOUBLE, billRange, excel.sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, billRange, excel.sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, billRange, excel.sheet);
        RegionUtil.setBorderLeft(BorderStyle.DOUBLE, billRange, excel.sheet);

        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            excel.workbook.write(outputStream);
            excel.workbook.close();
            outputStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean writeToExcel(Receipt receipt, String path) {
        try {
            Files.createDirectories(Paths.get(path));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        File file = new File(path + "/" + receipt.getReceiptID() + ".xlsx");
        Excel excel = new Excel("Phiếu nhập hàng");
        //--- DECLARE COMPONENTS FOR EXCEL ---//
        // title
        Font titleFont = excel.newFont((short) 14, true, false, false, Font.U_NONE);
        CellStyle titleCellStyle = excel.newCellStyle(titleFont, HorizontalAlignment.CENTER);

        //--- WRITING INTO EXCEL ---//
        /* HEADER */
        // Title
        excel.sheet.createRow(1);
        excel.setCellRange("PHIẾU NHẬP HÀNG", new CellRangeAddress(1, 1, 0, 12), titleCellStyle);

        // Mã hóa đơn
        excel.sheet.createRow(3);
        excel.setCellRange("Mã phiếu:", new CellRangeAddress(3, 3, 0, 2), excel.boldLeftCellStyle);
        excel.setCellRange(receipt.getReceiptID(), new CellRangeAddress(3, 3, 3, 6), excel.commonLeftCellStyle);

        // Ngày
        excel.setCellRange("Ngày:", new CellRangeAddress(3, 3, 8, 10), excel.boldLeftCellStyle);
        excel.setCellRange(receipt.getDor(), new CellRangeAddress(3, 3, 11, 12), excel.commonLeftCellStyle);

        // Nhà cung cấp
        excel.sheet.createRow(4);
        excel.setCellRange("Nhà cung cấp:", new CellRangeAddress(4, 4, 0, 2), excel.boldLeftCellStyle);
        Supplier supplier = new SupplierBLL().searchSuppliers("SUPPLIER_ID = 'SUP001'").get(0);
        excel.setCellRange(supplier.getName(), new CellRangeAddress(4, 4, 3, 6), excel.commonLeftCellStyle);
//        Supplier supplier = new SupplierBLL().searchSuppliers("SUPPLIER_ID = '" + receipt.getSupplierID() + "'").get(0);
//        excel.setCellRange(supplier.getName(), new CellRangeAddress(4, 4, 3, 6), excel.commonLeftCellStyle);

        // Mã nhân viên
        excel.setCellRange("Mã nhân viên:", new CellRangeAddress(4, 4, 8, 10), excel.boldLeftCellStyle);
        excel.setCellRange(receipt.getStaffID(), new CellRangeAddress(4, 4, 11, 12), excel.commonLeftCellStyle);

        /* ----- */
        excel.sheet.createRow(6);
        excel.setCellRange("--------------------------------------------------------------------------------",
            new CellRangeAddress(6, 6, 1, 11), excel.boldCenterCellStyle);

        /* RECEIPT DETAILS */
        // Table header
        excel.sheet.createRow(8);
        excel.setCell("STT", 8, 0, excel.boldLeftCellStyle);
        excel.setCellRange("Nguyên liệu", new CellRangeAddress(8, 8, 1, 5), excel.boldLeftCellStyle);
        excel.setCellRange("SL", new CellRangeAddress(8, 8, 6, 7), excel.boldRightCellStyle);
        excel.setCellRange("Đơn giá", new CellRangeAddress(8, 8, 8, 9), excel.boldRightCellStyle);
        excel.setCellRange("Thành tiền", new CellRangeAddress(8, 8, 10, 12), excel.boldRightCellStyle);

        // Table rows
        List<ReceiptDetails> receiptDetailsList = new ReceiptDetailsBLL().searchReceiptDetails("RECEIPT_ID = '" + receipt.getReceiptID() + "'");
        int receiptSize = receiptDetailsList.size();
        IngredientBLL ingredientBLL = new IngredientBLL();
        for (int i = 0; i < receiptSize; i++) {
            ReceiptDetails receiptDetails = receiptDetailsList.get(i);
            Ingredient ingredient = ingredientBLL.searchIngredients("INGREDIENT_ID = '" + receiptDetails.getIngredientID() + "'").get(0);

            excel.sheet.createRow(10 + i);
            excel.setCell(i + 1, 10 + i, 0, excel.commonLeftCellStyle);

            excel.setCellRange(ingredient.getName(), new CellRangeAddress(10 + i, 10 + i, 1, 5), excel.commonLeftCellStyle);

            String ingredientQuantity = receiptDetails.getQuantity() + ingredient.getUnit();
            excel.setCellRange(ingredientQuantity, new CellRangeAddress(10 + i, 10 + i, 6, 7), excel.commonRightCellStyle);

            String ingredientUnitPrice = VNString.currency(30000);
            excel.setCellRange(ingredientUnitPrice, new CellRangeAddress(10 + i, 10 + i, 8, 9), excel.commonRightCellStyle);
//            String ingredientUnitPrice = VNString.currency(ingredient.getUnitPrice());
//            excel.setCellRange(ingredientUnitPrice, new CellRangeAddress(10 + i, 10 + i, 8, 9), excel.commonRightCellStyle);
//
            double total = receiptDetails.getQuantity() * 30000;
            excel.setCellRange(VNString.currency(total), new CellRangeAddress(10 + i, 10 + i, 10, 12), excel.commonRightCellStyle);
//            double total = receiptDetails.getQuantity() * ingredient.getUnitPrice();
//            excel.setCellRange(VNString.currency(total), new CellRangeAddress(10 + i, 10 + i, 10, 12), excel.commonRightCellStyle);
        }

        /* ----- */
        excel.sheet.createRow(11 + receiptSize);
        excel.setCellRange("--------------------------------------------------------------------------------",
            new CellRangeAddress(11 + receiptSize, 11 + receiptSize, 1, 11), excel.boldCenterCellStyle);

        /* FOOTER */
        // Tổng
        excel.sheet.createRow(13 + receiptSize);
        String receiptGrandTotal = VNString.currency(receipt.getGrandTotal());
        excel.setCellRange("Tổng tiền:", new CellRangeAddress(13 + receiptSize, 13 + receiptSize, 8, 9), excel.boldRightCellStyle);
        excel.setCell(receiptGrandTotal, 13 + receiptSize, 10, excel.commonLeftCellStyle);

        /* BORDER */
        CellRangeAddress receiptRange = new CellRangeAddress(0, 16 + receiptSize, 0, 12);
        RegionUtil.setBorderTop(BorderStyle.DOUBLE, receiptRange, excel.sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, receiptRange, excel.sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, receiptRange, excel.sheet);
        RegionUtil.setBorderLeft(BorderStyle.DOUBLE, receiptRange, excel.sheet);

        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            excel.workbook.write(outputStream);
            excel.workbook.close();
            outputStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public static String getNextFileName(String path, String name, Day from, Day to) {
        int max = 0;
        File[] files = new File(path).listFiles();
        for (File file : Objects.requireNonNull(files)) {
            if (file.getName().startsWith(name)) {
                String numStr = file.getName().substring(name.length(), file.getName().indexOf("_"));
                int num = Integer.parseInt(numStr);
                if (num > max)
                    max = num;
            }
        }
        return String.format("%s/%s%03d_%s_%s.xlsx", path, name, max + 1, from.toMySQLString(), to.toMySQLString());
    }

    public static boolean writeBillsToExcel(List<Bill> bills, Day from, Day to, String path) {
        try {
            Files.createDirectories(Paths.get(path));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        File file = new File(getNextFileName(path, "bills", from, to));
        Excel excel = new Excel("Hóa đơn");
        excel.sheet.setDefaultRowHeightInPoints(14.4F);
        excel.sheet.setDefaultColumnWidth(10);
        //--- DECLARE COMPONENTS FOR EXCEL ---//
        // title
        Font titleFont = excel.newFont((short) 14, true, false, false, Font.U_NONE);
        CellStyle titleCellStyle = excel.newCellStyle(titleFont, HorizontalAlignment.CENTER);

        // day
        Font dayFont = excel.newFont((short) 11, false, true, false, Font.U_NONE);
        CellStyle dayCellStyle = excel.newCellStyle(dayFont, HorizontalAlignment.CENTER);

        // header
        Font headerFont = excel.newFont((short) 9, true, true, false, Font.U_NONE);
        CellStyle headerStyle = excel.newCellStyle(headerFont, HorizontalAlignment.CENTER);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        //--- WRITING INTO EXCEL ---//
        /* HEADER */
        // Title
        excel.sheet.createRow(1);
        excel.sheet.getRow(1).setHeightInPoints(20);
        excel.setCellRange("BẢNG HÓA ĐƠN", new CellRangeAddress(1, 1, 0, 6), titleCellStyle);

        excel.sheet.createRow(2);
        String day = "Từ ngày " + from + " đến ngày " + to;
        excel.setCellRange(day, new CellRangeAddress(2, 2, 0, 6), dayCellStyle);

        XSSFTable table = ((XSSFSheet) excel.sheet).createTable(null);
        CTTable ctTable = table.getCTTable();
        ctTable.setId(1);

        CTTableColumns columns = ctTable.addNewTableColumns();
        columns.setCount(7);

        List<String> columnNames = new BillBLL().getBillDAL().getColumnNames();
        Row headerRow = excel.sheet.createRow(4);
        for (int i = 0; i < columnNames.size() - 1; i++) {
            columns.addNewTableColumn().setId(i + 1);
            Cell headerCell = headerRow.createCell(i);
            headerCell.setCellValue(columnNames.get(i));
            headerCell.setCellStyle(headerStyle);
        }

        AreaReference dataRange = new AreaReference(
            new CellReference(4, 0),
            new CellReference(4 + bills.size(), 6),
            SpreadsheetVersion.EXCEL2007);
        ctTable.setRef(dataRange.formatAsString());
        ctTable.addNewAutoFilter().setRef(dataRange.formatAsString());

        int rowIndex = 5;
        for (Bill bill : bills) {
            excel.sheet.createRow(rowIndex);
            excel.setCell(bill.getBillID(), rowIndex, 0, excel.commonCenterCellStyle);
            excel.setCell(bill.getCustomerID(), rowIndex, 1, excel.commonCenterCellStyle);
            excel.setCell(bill.getStaffID(), rowIndex, 2, excel.commonCenterCellStyle);
            excel.setCell(bill.getDateOfPurchase().toMySQLString(), rowIndex, 3, excel.commonCenterCellStyle);
            excel.setCell(VNString.currency(bill.getTotal()), rowIndex, 4, excel.commonCenterCellStyle);
            excel.setCell(VNString.currency(bill.getTotal()), rowIndex, 5, excel.commonCenterCellStyle);
            excel.setCell(VNString.currency(bill.getTotal()), rowIndex, 6, excel.commonCenterCellStyle);
            rowIndex++;
        }

        /* BORDER */
        CellRangeAddress receiptRange = new CellRangeAddress(0, 5 + bills.size(), 0, 6);
        RegionUtil.setBorderTop(BorderStyle.DOUBLE, receiptRange, excel.sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, receiptRange, excel.sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, receiptRange, excel.sheet);
        RegionUtil.setBorderLeft(BorderStyle.DOUBLE, receiptRange, excel.sheet);

        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            excel.workbook.write(outputStream);
            excel.workbook.close();
            outputStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean writeReceiptsToExcel(List<Receipt> receipts, Day from, Day to, String path) {
        try {
            Files.createDirectories(Paths.get(path));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        File file = new File(getNextFileName(path, "receipts", from, to));
        Excel excel = new Excel("Phiếu nhập hàng");
        excel.sheet.setDefaultRowHeightInPoints(14.4F);
        excel.sheet.setDefaultColumnWidth(12);
        //--- DECLARE COMPONENTS FOR EXCEL ---//
        // title
        Font titleFont = excel.newFont((short) 14, true, false, false, Font.U_NONE);
        CellStyle titleCellStyle = excel.newCellStyle(titleFont, HorizontalAlignment.CENTER);

        // day
        Font dayFont = excel.newFont((short) 11, false, true, false, Font.U_NONE);
        CellStyle dayCellStyle = excel.newCellStyle(dayFont, HorizontalAlignment.CENTER);

        // header
        Font headerFont = excel.newFont((short) 9, true, true, false, Font.U_NONE);
        CellStyle headerStyle = excel.newCellStyle(headerFont, HorizontalAlignment.CENTER);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        //--- WRITING INTO EXCEL ---//
        /* HEADER */
        // Title
        excel.sheet.createRow(1);
        excel.sheet.getRow(1).setHeightInPoints(20);
        excel.setCellRange("BẢNG PHIẾU NHẬP HÀNG", new CellRangeAddress(1, 1, 0, 4), titleCellStyle);

        excel.sheet.createRow(2);
        String day = "Từ ngày " + from + " đến ngày " + to;
        excel.setCellRange(day, new CellRangeAddress(2, 2, 0, 4), dayCellStyle);

        XSSFTable table = ((XSSFSheet) excel.sheet).createTable(null);
        CTTable ctTable = table.getCTTable();
        ctTable.setId(1);

        CTTableColumns columns = ctTable.addNewTableColumns();
        columns.setCount(5);

        List<String> columnNames = new ReceiptBLL().getReceiptDAL().getColumnNames();
        Row headerRow = excel.sheet.createRow(4);
        for (int i = 0; i < columnNames.size() - 1; i++) {
            columns.addNewTableColumn().setId(i + 1);
            Cell headerCell = headerRow.createCell(i);
            headerCell.setCellValue(columnNames.get(i));
            headerCell.setCellStyle(headerStyle);
        }

        AreaReference dataRange = new AreaReference(
            new CellReference(4, 0),
            new CellReference(4 + receipts.size(), 4),
            SpreadsheetVersion.EXCEL2007);
        ctTable.setRef(dataRange.formatAsString());
        ctTable.addNewAutoFilter().setRef(dataRange.formatAsString());

        int rowIndex = 5;
        for (Receipt receipt : receipts) {
            excel.sheet.createRow(rowIndex);
            excel.setCell(receipt.getReceiptID(), rowIndex, 0, excel.commonCenterCellStyle);
            excel.setCell(receipt.getStaffID(), rowIndex, 1, excel.commonCenterCellStyle);
            excel.setCell(receipt.getDor().toMySQLString(), rowIndex, 2, excel.commonCenterCellStyle);
            excel.setCell(VNString.currency(receipt.getGrandTotal()), rowIndex, 3, excel.commonCenterCellStyle);
            excel.setCell(receipt.getSupplierID(), rowIndex, 4, excel.commonCenterCellStyle);
            rowIndex++;
        }

        /* BORDER */
        CellRangeAddress receiptRange = new CellRangeAddress(0, 5 + receipts.size(), 0, 4);
        RegionUtil.setBorderTop(BorderStyle.DOUBLE, receiptRange, excel.sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, receiptRange, excel.sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, receiptRange, excel.sheet);
        RegionUtil.setBorderLeft(BorderStyle.DOUBLE, receiptRange, excel.sheet);

        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            excel.workbook.write(outputStream);
            excel.workbook.close();
            outputStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public Font newFont(short size, boolean bold, boolean italic, boolean strikeout, byte underline) {
        Font font = workbook.createFont();
        font.setFontHeightInPoints(size);
        font.setColor(IndexedColors.BLACK1.getIndex());
        font.setBold(bold);
        font.setItalic(italic);
        font.setStrikeout(strikeout);
        font.setUnderline(underline);
        return font;
    }

    public CellStyle newCellStyle(Font font, HorizontalAlignment align) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setAlignment(align);
        return cellStyle;
    }

    public void setCell(Object value, int row, int column, CellStyle style) {
        Cell cell = sheet.getRow(row).createCell(column);
        cell.setCellStyle(style);
        switch (value.getClass().getSimpleName()) {
            case "Integer" -> cell.setCellValue((Integer) value);
            case "Float" -> cell.setCellValue((Float) value);
            case "Double" -> cell.setCellValue((Double) value);
            default -> cell.setCellValue(value.toString());
        }
    }

    public void setCellRange(Object value, CellRangeAddress rangeAddress, CellStyle style) {
        sheet.addMergedRegion(rangeAddress);
        Cell cell = sheet.getRow(rangeAddress.getFirstRow()).createCell(rangeAddress.getFirstColumn());
        cell.setCellStyle(style);
        switch (value.getClass().getSimpleName()) {
            case "Integer" -> cell.setCellValue((Integer) value);
            case "Float" -> cell.setCellValue((Float) value);
            case "Double" -> cell.setCellValue((Double) value);
            default -> cell.setCellValue(value.toString());
        }
    }
}
