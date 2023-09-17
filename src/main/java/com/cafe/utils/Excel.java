package com.cafe.utils;

import com.cafe.BLL.*;
import com.cafe.DTO.*;
import com.cafe.GUI.IngredientGUI;
import com.cafe.main.CafeManagement;
import javafx.util.Pair;
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

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Excel {
    private Workbook workbook;
    private Sheet sheet;

    public Excel(String path) {
        try (FileInputStream inputStream = new FileInputStream(path)) {
            workbook = new XSSFWorkbook(inputStream);
            sheet = workbook.getSheetAt(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void close() {
        try {
            workbook.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean importExcel(Supplier supplier, String path) {
        Excel excel = new Excel(path);
        int numColumns = excel.sheet.getRow(0).getPhysicalNumberOfCells();
        if (numColumns != 2) {
            JOptionPane.showMessageDialog(
                CafeManagement.homeGUI,
                "Cần 2 cột \"Tên nguyên liệu\" và \"Số lượng\"",
                "Lỗi định dạng",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        List<Ingredient> ingredientList = new IngredientBLL().findIngredientsBy(Map.of("SUPPLIER_ID", supplier.getSupplierID()));
        List<Pair<Ingredient, Integer>> receiptDetailsList = new ArrayList<>();
        int numRows = excel.sheet.getLastRowNum();
        for (int i = 1; i <= numRows; ++i) {
            Object ingredientName = excel.readCell(i, 0);
            Object quantity = excel.readCell(i, 1);

            if (!(ingredientName instanceof String && quantity instanceof Double)) {
                String message = "Tìm thấy lỗi ở dòng thứ " + (i + 1) + ":\n" +
                                "\"" + ingredientName + " | " + quantity + "\"\n\n" +
                                "Định dạng đúng: Chuỗi | Số";
                String[] options = new String[]{"Bỏ qua", "Dừng lại", "Hủy bỏ"};
                int choice = JOptionPane.showOptionDialog(CafeManagement.homeGUI, message, "Lỗi định dạng",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
                if (choice == 0) continue;
                if (choice == 1) break;
                if (choice == 2) return false;
            }

            assert ingredientName instanceof String;
            assert quantity instanceof Double;

            String comparedName = VNString.removeAccent((String) ingredientName).toLowerCase();
            boolean found = false;
            for (Ingredient ingredient : ingredientList) {
                if (VNString.removeAccent(ingredient.getName()).toLowerCase().equals(comparedName)) {
                    receiptDetailsList.add(new Pair<>(ingredient, (int) Math.round((Double) quantity)));
                    found = true;
                    break;
                }
            }
            if (!found) {
                String message = "Không tìm thấy nguyên liệu ở dòng thứ " + (i + 1) + ":\n" +
                    "\"" + ingredientName + " | " + quantity + "\"\n\n\n";
                String[] options = new String[]{"Bỏ qua", "Dừng lại", "Hủy bỏ"};
                int choice = JOptionPane.showOptionDialog(CafeManagement.homeGUI, message, "Lỗi định dạng",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
                if (choice == 0) continue;
                if (choice == 1) break;
                if (choice == 2) return false;
            }
        }
        for (Pair<Ingredient, Integer> pair : receiptDetailsList) {
            IngredientGUI ingredientGUI = (IngredientGUI) CafeManagement.homeGUI.getCurrentGUI();
            int index = ingredientGUI.findReceiptDetailsIndex(pair.getKey().getName());
            if (index == -1) {
                ingredientGUI.getReceiptDetails().add(new Pair<>(pair.getKey(), pair.getValue()));
            } else {
                Pair<Ingredient, Integer> existingReceiptDetails = ingredientGUI.getReceiptDetails().get(index);
                ingredientGUI.getReceiptDetails().set(index, new Pair<>(pair.getKey(), existingReceiptDetails.getValue() + pair.getValue()));
            }
            ingredientGUI.getRoundPanel().removeAll();
            ingredientGUI.addIngredient(ingredientGUI.getReceiptDetails());
        }
        excel.close();
        return true;
    }

    public static void main(String[] args) {
        Supplier supplier = new SupplierBLL().searchSuppliers("SUPPLIER_ID = 'SUP001'").get(0);
        Excel.importExcel(supplier, "D:\\Workspace\\Project\\cafe-management\\target\\test.xlsx");
    }

    public Object readCell(int row, int column) {
        Cell cell = sheet.getRow(row).getCell(column);
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> cell.getNumericCellValue();
            case BOOLEAN -> cell.getBooleanCellValue();
            case FORMULA -> cell.getCellFormula();
            case ERROR -> cell.getErrorCellValue();
            case _NONE, BLANK -> "";
        };
    }
}
