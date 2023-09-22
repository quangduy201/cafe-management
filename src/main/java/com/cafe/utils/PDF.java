package com.cafe.utils;

import com.cafe.BLL.*;
import com.cafe.DTO.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDImmutableRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class PDF {
    private PDDocument document;
    private PDPage currentPage;
    private PDPageContentStream contentStream;
    private PDType0Font regularFont;
    private PDType0Font boldFont;
    private PDType0Font italicFont;
    public float pageWidth;
    public float pageHeight;
    public float TAB;
    public float LINE;

    public PDF(float numberOfTabs, float numberOfLines, float tabSize, float lineHeight) {
        Configurator.setLevel("org.apache.fontbox.ttf.gsub.GlyphSubstitutionDataExtractor", org.apache.logging.log4j.Level.OFF);
        Configurator.setLevel("org.apache.pdfbox.pdmodel.PDDocument", org.apache.logging.log4j.Level.OFF);
        Configurator.setAllLevels(LogManager.getRootLogger().getName(), org.apache.logging.log4j.Level.OFF);
        Logger.getLogger("org.apache.fontbox.ttf.gsub.GlyphSubstitutionDataExtractor").setLevel(java.util.logging.Level.OFF);
        Logger.getLogger("org.apache.pdfbox.pdmodel.PDDocument").setLevel(java.util.logging.Level.OFF);
        TAB = tabSize;
        LINE = lineHeight;
        document = new PDDocument();
        currentPage = new PDPage(new PDImmutableRectangle(numberOfTabs * TAB, numberOfLines * LINE));
        document.addPage(currentPage);
        pageWidth = currentPage.getMediaBox().getWidth();
        pageHeight = currentPage.getMediaBox().getHeight();
        regularFont = newFont("Roboto/Roboto-Regular.ttf");
        boldFont = newFont("Roboto/Roboto-Bold.ttf");
        italicFont = newFont("Roboto/Roboto-Italic.ttf");
        try {
            contentStream = new PDPageContentStream(document, currentPage);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void closeDocument(File file) {
        try {
            contentStream.close();
            document.save(file);
            document.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addText(String text, float x, float y, PDFont font, float fontSize) {
        try {
            contentStream.beginText();
            contentStream.setFont(font, fontSize);
            contentStream.newLineAtOffset(x, pageHeight - y);
            contentStream.showText(text);
            contentStream.endText();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addTextAt(String text, float tabs, float lines, PDFont font, float fontSize) {
        addText(text, tabs * TAB, lines * LINE, font, fontSize);
    }

    public void addLine(float x1, float y1, float x2, float y2, float lineWidth) {
        try {
            contentStream.setLineWidth(lineWidth);
            contentStream.moveTo(x1, pageHeight - y1);
            contentStream.lineTo(x2, pageHeight - y2);
            contentStream.stroke();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addLineAt(float tabs1, float lines1, float tabs2, float lines2, float lineWidth) {
        addLine(tabs1 * TAB, lines1 * LINE, tabs2 * TAB, lines2 * LINE, lineWidth);
    }

    public void addTable(List<String[]> data, float fontSize, float startLine, float firstRow, float[] columns) {
        int rows = data.size();

        for (int i = 0; i <= rows; ++i)
            addLineAt(columns[0], startLine + i, columns[columns.length - 1], startLine + i, 0.5F);
        for (float column : columns)
            addLineAt(column, startLine, column, startLine + rows, 0.5F);
        for (int j = 0; j < columns.length - 1; ++j)
            addTextAt(data.get(0)[j], columns[j] + 0.1F, firstRow, boldFont, fontSize);
        for (int i = 1; i < rows; ++i)
            for (int j = 0; j < columns.length - 1; ++j)
                addTextAt(data.get(i)[j], columns[j] + 0.1F, firstRow + i, regularFont, fontSize - 1);
    }

    public static boolean exportPDF(Bill bill, String path) {
        try {
            Files.createDirectories(Paths.get(path));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        List<BillDetails> billDetailsList = new BillDetailsBLL().searchBillDetails("BILL_ID = '" + bill.getBillID() + "'");
        int billSize = billDetailsList.size();
        int numberOfNotes = 0;
        for (BillDetails billDetails : billDetailsList) {
            if (!billDetails.getNote().isEmpty())
                numberOfNotes++;
        }
        int extraLines = numberOfNotes <= 2 ? 0 : numberOfNotes - 2;
        File file = new File(path + "/" + bill.getBillID() + ".pdf");
        PDF pdf = new PDF(15, 11 + billSize + extraLines, 20F, 15F);

        pdf.addTextAt("HÓA ĐƠN", 6.5F, 2, pdf.boldFont, 14);

        pdf.addTextAt("Mã hóa đơn:", 1, 3.5F, pdf.boldFont, 7);
        pdf.addTextAt(bill.getBillID(), 4, 3.5F, pdf.regularFont, 6);

        pdf.addTextAt("Ngày:", 9, 3.5F, pdf.boldFont, 7);
        pdf.addTextAt(bill.getDateOfPurchase().toString(), 12, 3.5F, pdf.regularFont, 6);

        pdf.addTextAt("Tên khách hàng:", 1, 4.5F, pdf.boldFont, 7);
        Customer customer = new CustomerBLL().searchCustomers("CUSTOMER_ID = '" + bill.getCustomerID() + "'").get(0);
        pdf.addTextAt(customer.getName(), 4, 4.5F, pdf.regularFont, 6);

        pdf.addTextAt("Tên nhân viên:", 9, 4.5F, pdf.boldFont, 7);
        Staff staff = new StaffBLL().searchStaffs("STAFF_ID = '" + bill.getStaffID() + "'").get(0);
        String[] name = staff.getName().split(" ");
        if (name.length < 2)
            pdf.addTextAt(name[0], 12, 4.5F, pdf.regularFont, 6);
        else
            pdf.addTextAt(name[name.length - 2] + " " + name[name.length - 1], 12, 4.5F, pdf.regularFont, 6);

        List<String[]> tableData = new ArrayList<>();
        tableData.add(new String[]{ "STT", "Sản phẩm", "Size", "SL", "Giá", "Giá giảm", "Thành tiền" });
        ProductBLL productBLL = new ProductBLL();
        for (int i = 0; i < billSize; ++i) {
            BillDetails billDetails = billDetailsList.get(i);
            Product product = productBLL.findProductsBy(Map.of("PRODUCT_ID", billDetails.getProductID())).get(0);
            String[] data = new String[7];
            data[0] = String.valueOf(i + 1);
            data[1] = product.getName();
            data[2] = product.getSized().equals("null") ? "" : product.getSized();
            data[3] = String.valueOf(billDetails.getQuantity());
            data[4] = VNString.currency(product.getCost());
            double percent = billDetails.getPercent();
            double total;
            if (percent > 0) {
                double discountedCost = product.getCost() - product.getCost() * percent / 100.0;
                data[5] = VNString.currency(discountedCost);
                try {
                    pdf.contentStream.setLineWidth(0.5F);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                pdf.addLineAt(8.1F, 6.8F + i, 9.9F, 6.8F + i, 0.1F);
                total = billDetails.getQuantity() * discountedCost;
            } else {
                data[5] = "";
                total = billDetails.getQuantity() * product.getCost();
            }

            data[6] = VNString.currency(total);
            tableData.add(data);
        }
        pdf.addTable(tableData, 7F,  5.3F, 6F, new float[]{1, 2, 6, 7, 8, 10, 12, 14});

        pdf.addTextAt("Ghi chú:", 1, 8 + billSize, pdf.boldFont, 7);
        for (int i = 0; i < billSize; ++i) {
            BillDetails billDetails = billDetailsList.get(i);
            Product product = productBLL.findProductsBy(Map.of("PRODUCT_ID", billDetails.getProductID())).get(0);
            String note = billDetails.getNote();
            if (!note.isEmpty()) {
                pdf.addTextAt("- " + product.getName() + ": " + note, 2, 9 + billSize + i, pdf.regularFont, 6);
            }
        }

        pdf.addTextAt("Tổng tiền:", 9, 8 + billSize, pdf.boldFont, 7);
        pdf.addTextAt(VNString.currency(bill.getTotal()), 11, 8 + billSize, pdf.regularFont, 6);

        pdf.addTextAt("Tiền nhận:", 9, 9 + billSize, pdf.boldFont, 7);
        pdf.addTextAt(VNString.currency(bill.getReceived()), 11, 9 + billSize, pdf.regularFont, 6);

        pdf.addTextAt("Tiền thối:", 9, 10 + billSize, pdf.boldFont, 7);
        pdf.addTextAt(VNString.currency(bill.getExcess()), 11, 10 + billSize, pdf.regularFont, 6);

        pdf.closeDocument(file);
        return true;
    }

    public static boolean exportPDF(Receipt receipt, String path) {
        try {
            Files.createDirectories(Paths.get(path));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        List<ReceiptDetails> receiptDetailsList = new ReceiptDetailsBLL().searchReceiptDetails("RECEIPT_ID = '" + receipt.getReceiptID() + "'");
        int receiptSize = receiptDetailsList.size();
        File file = new File(path + "/" + receipt.getReceiptID() + ".pdf");
        PDF pdf = new PDF(15, 11 + receiptSize, 20F, 15F);

        pdf.addTextAt("PHIẾU NHẬP HÀNG", 4.5F, 2, pdf.boldFont, 14);

        pdf.addTextAt("Mã phiếu:", 1, 3.5F, pdf.boldFont, 7);
        pdf.addTextAt(receipt.getReceiptID(), 4, 3.5F, pdf.regularFont, 6);

        pdf.addTextAt("Ngày:", 9, 3.5F, pdf.boldFont, 7);
        pdf.addTextAt(receipt.getDor().toString(), 12, 3.5F, pdf.regularFont, 6);

        pdf.addTextAt("Nhà cung cấp:", 1, 4.5F, pdf.boldFont, 7);
        Supplier supplier = new SupplierBLL().searchSuppliers("SUPPLIER_ID = '" + receipt.getSupplierID() + "'").get(0);
        pdf.addTextAt(supplier.getName(), 4, 4.5F, pdf.regularFont, 6);

        pdf.addTextAt("Tên nhân viên:", 9, 4.5F, pdf.boldFont, 7);
        Staff staff = new StaffBLL().searchStaffs("STAFF_ID = '" + receipt.getStaffID() + "'").get(0);
        String[] name = staff.getName().split(" ");
        pdf.addTextAt(name[name.length - 2] + " " + name[name.length - 1], 12, 4.5F, pdf.regularFont, 6);

        List<String[]> tableData = new ArrayList<>();
        tableData.add(new String[]{ "STT", "Nguyên liệu", "SL", "Đơn giá", "Thành tiền" });
        IngredientBLL ingredientBLL = new IngredientBLL();
        for (int i = 0; i < receiptSize; ++i) {
            ReceiptDetails receiptDetails = receiptDetailsList.get(i);
            Ingredient ingredient = ingredientBLL.searchIngredients("INGREDIENT_ID = '" + receiptDetails.getIngredientID() + "'").get(0);
            String[] data = new String[5];
            data[0] = String.valueOf(i + 1);
            data[1] = ingredient.getName();
            data[2] = String.valueOf(receiptDetails.getQuantity());
            data[3] = VNString.currency(ingredient.getUnitPrice());
            double total = receiptDetails.getQuantity() * ingredient.getUnitPrice();
            data[4] = VNString.currency(total);
            tableData.add(data);
        }
        pdf.addTable(tableData, 7F, 5.3F, 6F, new float[]{1, 2, 8, 9, 11, 14});

        pdf.addTextAt("Tổng tiền:", 9, 8 + receiptSize, pdf.boldFont, 7);
        pdf.addTextAt(VNString.currency(receipt.getGrandTotal()), 11, 8 + receiptSize, pdf.regularFont, 6);

        pdf.closeDocument(file);
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
        return String.format("%s/%s%03d_%s_%s.pdf", path, name, max + 1, from.toMySQLString(), to.toMySQLString());
    }

    public static boolean exportBillsPDF(List<Bill> bills, Day from, Day to, String path) {
        try {
            Files.createDirectories(Paths.get(path));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        File file = new File(getNextFileName(path, "bills", from, to));
        PDF pdf = new PDF(8, 6 + bills.size(), 125F, 30F);
        pdf.addTextAt("BẢNG HÓA ĐƠN", 3.3F, 2, pdf.boldFont, 25);
        pdf.addTextAt("Từ ngày " + from + " đến ngày " + to, 2.5F, 3, pdf.italicFont, 20);

        List<String[]> tableData = new ArrayList<>();
        tableData.add(new BillBLL().getBillDAL().getColumnNames().toArray(new String[0]));
        for (Bill bill : bills) {
            String[] data = new String[7];
            data[0] = bill.getBillID();
            data[1] = bill.getCustomerID();
            data[2] = bill.getStaffID();
            data[3] = bill.getDateOfPurchase().toMySQLString();
            data[4] = VNString.currency(bill.getTotal());
            data[5] = VNString.currency(bill.getReceived());
            data[6] = VNString.currency(bill.getExcess());
            tableData.add(data);
        }
        pdf.addTable(tableData, 16F, 4.3F, 5F, new float[]{0.5F, 1.2F, 2.5F, 3.2F, 4.5F, 5.5F, 6.5F, 7.5F});

        pdf.closeDocument(file);
        return true;
    }

    public static boolean exportReceiptsPDF(List<Receipt> receipts, Day from, Day to, String path) {
        try {
            Files.createDirectories(Paths.get(path));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        File file = new File(getNextFileName(path, "receipts", from, to));
        PDF pdf = new PDF(6, 6 + receipts.size(), 167F, 30F);
        pdf.addTextAt("BẢNG PHIẾU NHẬP HÀNG", 2.2F, 2, pdf.boldFont, 25);
        pdf.addTextAt("Từ ngày " + from + " đến ngày " + to, 2F, 3, pdf.italicFont, 20);

        List<String[]> tableData = new ArrayList<>();
        tableData.add(new ReceiptBLL().getReceiptDAL().getColumnNames().toArray(new String[0]));
        for (Receipt receipt : receipts) {
            String[] data = new String[7];
            data[0] = receipt.getReceiptID();
            data[1] = receipt.getStaffID();
            data[2] = receipt.getDor().toMySQLString();
            data[3] = VNString.currency(receipt.getGrandTotal());
            data[4] = receipt.getSupplierID();
            tableData.add(data);
        }
        pdf.addTable(tableData, 16F, 4.3F, 5F, new float[]{0.5F, 1.5F, 2.5F, 3.5F, 4.5F, 5.5F});

        pdf.closeDocument(file);
        return true;
    }

    PDType0Font newFont(String path) {
        try (InputStream fontFile = Resource.loadInputStream("font/" + path)) {
            return PDType0Font.load(document, fontFile);
        } catch (Exception e) {
            return null;
        }
    }
}
