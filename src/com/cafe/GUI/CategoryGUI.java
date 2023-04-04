package com.cafe.GUI;

import com.cafe.BLL.CategoryBLL;
import com.cafe.custom.Button;
import com.cafe.custom.DataTable;
import com.cafe.custom.RoundPanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class CategoryGUI extends JPanel {
    private CategoryBLL categoryBLL = new CategoryBLL();
    private RoundPanel category;
    private RoundPanel roundPanel1;
    private RoundPanel roundPanel2;
    private RoundPanel searchPanel;
    private RoundPanel tablePanel;
    private RoundPanel formPanel;
    private RoundPanel functionPanel;
    private DataTable dataTable;
    private JTextField[] jTextFieldsForm;

    public CategoryGUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(70, 67, 67));
        initComponents();
    }

    public void initComponents() {
        category = new RoundPanel();
        roundPanel1 = new RoundPanel();
        roundPanel2 = new RoundPanel();
        searchPanel = new RoundPanel();
        tablePanel = new RoundPanel();
        formPanel = new RoundPanel();
        functionPanel = new RoundPanel();

        // initialize main panel
        category.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        category.setBackground(new Color(70, 67, 67));
        this.add(category, BorderLayout.CENTER);

        // initialize left panel
        roundPanel1.setLayout(new BorderLayout(0, 10));
        roundPanel1.setBackground(new Color(51, 51, 51));
        roundPanel1.setPreferredSize(new Dimension(635, 680));
        roundPanel1.setAutoscrolls(true);
        category.add(roundPanel1);

        // initialize right panel
        roundPanel2.setBackground(new Color(0xFFFFFF));
        roundPanel2.setPreferredSize(new Dimension(350, 680));
        roundPanel2.setAutoscrolls(true);
        category.add(roundPanel2);

        // add components to left panel
        roundPanel1.add(searchPanel, BorderLayout.NORTH);
        roundPanel1.add(tablePanel, BorderLayout.CENTER);

        Vector<String> combo = new Vector<>();
        combo.add("CATEGORY_ID");
        combo.add("NAME");
        combo.add("QUANTITY");
        searchPanel.setLayout(new BorderLayout(30, 10));
        searchPanel.setPreferredSize(new Dimension(635, 50));
        searchPanel.setAutoscrolls(true);
        searchPanel.add(new JComboBox<>(combo), BorderLayout.WEST);
        searchPanel.add(new JTextField("", 35), BorderLayout.EAST);
        ArrayList<String> columnNames = (ArrayList<String>) categoryBLL.getCategoryDAL().getColumnsName();
        dataTable = new DataTable(
            categoryBLL.getData(),
            columnNames.subList(0, columnNames.size() - 1).toArray(),
            getListSelectionListener()
        );
        tablePanel.add(new JScrollPane(dataTable));
        tablePanel.setAutoscrolls(true);

        // add components to right panel
        roundPanel2.add(formPanel);
        roundPanel2.add(functionPanel);

        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setPreferredSize(new Dimension(350, 100));
        formPanel.setAutoscrolls(true);
        jTextFieldsForm = new JTextField[columnNames.size() - 1];
        for (int i = 0; i < columnNames.size() - 1; i++) {
            jTextFieldsForm[i] = new JTextField(30);
            formPanel.add(jTextFieldsForm[i]);
        }
        jTextFieldsForm[0].setEnabled(false);
    }

    public ActionListener getListSelectionListener() {
        return e -> {
            String[] category = (String[]) categoryBLL.getData()[dataTable.getSelectedRow()];
            for (int i = 0; i < category.length; i++)
                jTextFieldsForm[i].setText(category[i]);
        };
    }

}
