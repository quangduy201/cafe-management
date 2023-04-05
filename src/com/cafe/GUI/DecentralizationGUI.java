package com.cafe.GUI;

import com.cafe.BLL.DecentralizationBLL;
import com.cafe.DTO.Decentralization;
import com.cafe.custom.Button;
import com.cafe.custom.DataTable;
import com.cafe.custom.RoundPanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DecentralizationGUI extends JPanel {
    private DecentralizationBLL decentralizationBLL = new DecentralizationBLL();
    private DataTable dataTable;
    private RoundPanel decentralization;
    private RoundPanel roundPanel1;
    private RoundPanel roundPanel2;
    private RoundPanel search;
    private JScrollPane scrollPane;
    private JPanel pnlDecentralizationConfiguration;
    private JPanel mode;
    private JLabel jLabelsForm[];
    private JComboBox<Object> cbbSearchFilter;
    private JComboBox<Object> jComboBoxForm[];
    private JComboBox<Object> jComboBoxSearch;
    private JTextField txtSearch;
    private JTextField jTextFieldsForm[];
    private com.cafe.custom.Button btAdd;
    private com.cafe.custom.Button btUpd;
    private com.cafe.custom.Button btDel;
    private Button btRef;
    public DecentralizationGUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(51, 51, 51));
        initComponents();
    }

    public void initComponents() {
        List<String> columsName = decentralizationBLL.getDecentralizationDAL().getColumnNames();
        decentralization = new RoundPanel();
        roundPanel1 = new RoundPanel();
        roundPanel2 = new RoundPanel();
        search = new RoundPanel();
        pnlDecentralizationConfiguration = new JPanel();
        mode = new JPanel();
        jLabelsForm = new JLabel[columsName.size() - 1];
        cbbSearchFilter = new JComboBox<>(columsName.subList(0, columsName.size() - 1).toArray());
        jComboBoxForm = new JComboBox[columsName.size()-3];
        jComboBoxSearch = new JComboBox<>(new String [] {"Không", "Xem", "Sửa", "Xem và sửa"});
        txtSearch = new JTextField(20);
        jTextFieldsForm = new JTextField[2];
        btAdd = new Button();
        btUpd = new Button();
        btDel = new Button();
        btRef = new Button();

        decentralization.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        decentralization.setBackground(new Color(51, 51, 51));
        this.add(decentralization, BorderLayout.CENTER);

        roundPanel1.setLayout(new BorderLayout(0, 10));
        roundPanel1.setBackground(new Color(51, 51, 51));
        roundPanel1.setPreferredSize(new Dimension(635, 680));
        roundPanel1.setAutoscrolls(true);
        roundPanel1.add(new JScrollPane(dataTable), BorderLayout.CENTER);
        decentralization.add(roundPanel1);

        roundPanel2.setLayout(new BorderLayout());
        roundPanel2.setBackground(new Color(0xFFFFFF));
        roundPanel2.setPreferredSize(new Dimension(350, 680));
        roundPanel2.setAutoscrolls(true);
        decentralization.add(roundPanel2);

        search.setLayout(new FlowLayout());
        search.setBackground(new Color(0xFFFFFF));
        search.setPreferredSize(new Dimension(635, 35));
        roundPanel1.add(search, BorderLayout.NORTH);

        cbbSearchFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectSearchFilter();
            }
        });
        search.add(cbbSearchFilter);
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchDecentralizations();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchDecentralizations();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchDecentralizations();
            }
        });
        search.add(txtSearch);
        jComboBoxSearch.setVisible(false);
        jComboBoxSearch.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                comboboxSearch();
            }
        });
        search.add(jComboBoxSearch);

        dataTable = new DataTable(decentralizationBLL.getData(), columsName.subList(0, columsName.size() - 1).toArray(), getListSelectionListener());
        scrollPane = new JScrollPane(dataTable);
        roundPanel1.add(scrollPane);

        JScrollPane jScrollPane = new JScrollPane(pnlDecentralizationConfiguration);
        jScrollPane.setPreferredSize(new Dimension(600, 550));
        pnlDecentralizationConfiguration.setLayout(new GridLayout(14, 2, 20, 20));
        pnlDecentralizationConfiguration.setBackground(new Color(0xFFFFFF));
        pnlDecentralizationConfiguration.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));
        pnlDecentralizationConfiguration.setPreferredSize(new Dimension(jScrollPane.getViewport().getWidth(), 700));
        roundPanel2.add(jScrollPane, BorderLayout.NORTH);

        int index1 = 0;
        int index2 = 0;
        for (int i = 0; i < columsName.size() - 1; i++) {
            jLabelsForm[i] = new JLabel();
            jLabelsForm[i].setText(columsName.get(i) + ": ");
            pnlDecentralizationConfiguration.add(jLabelsForm[i]);
            switch (columsName.get(i)) {
                case "DECENTRALIZATION_ID" -> {
                    jTextFieldsForm[index1] = new JTextField(decentralizationBLL.getAutoID());
                    jTextFieldsForm[index1].setEnabled(false);
                    jTextFieldsForm[index1].setBorder(null);
                    jTextFieldsForm[index1].setDisabledTextColor(new Color(0x000000));
                    pnlDecentralizationConfiguration.add(jTextFieldsForm[index1]);
                    index1++;
                }
                case "DECENTRALIZATION_NAME" -> {
                    jTextFieldsForm[index1] = new JTextField();
                    jTextFieldsForm[index1].setText(null);
                    pnlDecentralizationConfiguration.add(jTextFieldsForm[index1]);
                    index1++;
                }
                default -> {
                    jComboBoxForm[index2] = new JComboBox<>(new String [] {"Không", "Xem", "Sửa", "Xem và sửa"});
                    jComboBoxForm[index2].setSelectedIndex(0);
                    pnlDecentralizationConfiguration.add(jComboBoxForm[index2]);
                    index2++;
                }
            }
        }

        mode.setLayout(new GridLayout(2, 2, 20, 20));
        mode.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mode.setBackground(new Color(0xFFFFFF));
        mode.setPreferredSize(new Dimension(635, 130));
        roundPanel2.add(mode, BorderLayout.SOUTH);

        btAdd.setBackground(new Color(35, 166, 97));
        btAdd.setBorder(null);
        btAdd.setIcon(new ImageIcon("img/plus.png"));
        btAdd.setText("  Add");
        btAdd.setColor(new Color(240, 240, 240));
        btAdd.setColorClick(new Color(141, 222, 175));
        btAdd.setColorOver(new Color(35, 166, 97));
        btAdd.setFocusPainted(false);
        btAdd.setRadius(20);
        btAdd.setEnabled(true);
        btAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (btAdd.isEnabled()) {
                    addDecentralization();
                }
            }
        });
        mode.add(btAdd);

        btUpd.setBackground(new Color(35, 166, 97));
        btUpd.setBorder(null);
        btUpd.setIcon(new ImageIcon("img/wrench.png"));
        btUpd.setText("  Update");
        btUpd.setColor(new Color(240, 240, 240));
        btUpd.setColorClick(new Color(141, 222, 175));
        btUpd.setColorOver(new Color(35, 166, 97));
        btUpd.setFocusPainted(false);
        btUpd.setRadius(20);
        btUpd.setEnabled(false);
        btUpd.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (btUpd.isEnabled()) {
                    updateDecentralization();
                }
            }
        });
        mode.add(btUpd);

        btDel.setBackground(new Color(35, 166, 97));
        btDel.setBorder(null);
        btDel.setIcon(new ImageIcon("img/delete.png"));
        btDel.setText("  Delete");
        btDel.setColor(new Color(240, 240, 240));
        btDel.setColorClick(new Color(141, 222, 175));
        btDel.setColorOver(new Color(35, 166, 97));
        btDel.setFocusPainted(false);
        btDel.setRadius(20);
        btDel.setEnabled(false);
        btDel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (btDel.isEnabled()) {
                    deleteDecentralization();
                }
            }
        });
        mode.add(btDel);

        btRef.setBackground(new Color(35, 166, 97));
        btRef.setBorder(null);
        btRef.setIcon(new ImageIcon("img/refresh.png"));
        btRef.setText("  Refresh");
        btRef.setColor(new Color(240, 240, 240));
        btRef.setColorClick(new Color(141, 222, 175));
        btRef.setColorOver(new Color(35, 166, 97));
        btRef.setFocusPainted(false);
        btRef.setRadius(20);
        btRef.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                refreshForm();
            }
        });
        mode.add(btRef);
    }

    private void comboboxSearch() {
        String value = null;
        switch (Objects.requireNonNull(jComboBoxSearch.getSelectedItem()).toString()){
            case "Không" -> value = "0";
            case "Xem" -> value = "1";
            case "Sửa" -> value = "2";
            case "Xem và sửa" -> value = "3";
            default -> {}
        }
        loadDataTable(decentralizationBLL.findDecentralizations(Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString(), value));
    }

    private void selectSearchFilter() {
        if (Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString().contains("DECENTRALIZATION_ID") ||Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString().contains("DECENTRALIZATION_NAME")) {
            jComboBoxSearch.setVisible(false);
            txtSearch.setVisible(true);
            searchDecentralizations();
        } else {
            txtSearch.setVisible(false);
            jComboBoxSearch.setSelectedIndex(0);
            jComboBoxSearch.setVisible(true);
            comboboxSearch();
        }
    }

    public void searchDecentralizations() {
        if (txtSearch.getText().isEmpty()) {
            loadDataTable(decentralizationBLL.getDecentralizationList());
        } else {
            loadDataTable(decentralizationBLL.findDecentralizations(Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString(), txtSearch.getText()));
        }
    }

    public ActionListener getListSelectionListener() {
        return e -> {
            DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
            String rowData = model.getDataVector().elementAt(dataTable.getSelectedRow()).toString();
            String[] decentralization = rowData.substring(1, rowData.length() - 1).split(", ");
            jTextFieldsForm[0].setText(decentralization[0]);
            jTextFieldsForm[1].setText(decentralization[decentralization.length-1]);
            int index = 0;
            for (int i = 1; i < decentralization.length-1; i++) {
                jComboBoxForm[index].setSelectedItem(decentralization[i]);
                index++;
            }
            btAdd.setEnabled(false);
            btUpd.setEnabled(true);
            btDel.setEnabled(true);
        };
    }


    public void addDecentralization() {
        if (checkInput()) {
            Decentralization newDecentralization;
            String decentralizationID;
            List<String> args = new ArrayList<>();
            String DecentralizationName;
            decentralizationID = jTextFieldsForm[0].getText();
            DecentralizationName = jTextFieldsForm[1].getText();
            for (JComboBox<Object> objectJComboBox : jComboBoxForm) {
                args.add(Objects.requireNonNull(objectJComboBox.getSelectedItem()).toString());
            }
            newDecentralization = new Decentralization(decentralizationID, args, DecentralizationName, false);
            decentralizationBLL.addDecentralization(newDecentralization);
            refreshForm();
        }
    }

    public void updateDecentralization() {
        if (checkInput()) {
            Decentralization newDecentralization;
            String decentralizationID;
            List<String> args = new ArrayList<>();
            String DecentralizationName;
            decentralizationID = jTextFieldsForm[0].getText();
            DecentralizationName = jTextFieldsForm[1].getText();
            for (JComboBox<Object> objectJComboBox : jComboBoxForm) {
                args.add(Objects.requireNonNull(objectJComboBox.getSelectedItem()).toString());
            }
            newDecentralization = new Decentralization(decentralizationID, args, DecentralizationName, false);
            decentralizationBLL.updateDecentralization(newDecentralization);
            loadDataTable(decentralizationBLL.getDecentralizationList());
        }
    }

    private void deleteDecentralization() {
        Decentralization decentralization = new Decentralization();
        decentralization.setDecentralizationID(jTextFieldsForm[0].getText());
        decentralizationBLL.deleteDecentralization(decentralization);
        refreshForm();
    }

    public void refreshForm() {
        cbbSearchFilter.setSelectedIndex(0);
        txtSearch.setText(null);
        loadDataTable(decentralizationBLL.getDecentralizationList());
        jTextFieldsForm[0].setText(decentralizationBLL.getAutoID());
        jTextFieldsForm[1].setText(null);
        for (JComboBox<Object> objectJComboBox : jComboBoxForm) {
            objectJComboBox.setSelectedIndex(0);
        }
        btAdd.setEnabled(true);
        btUpd.setEnabled(false);
        btDel.setEnabled(false);
    }

    public String parse(int n) {
        switch (n){
            case 0 : return "Không";
            case 1 : return "Xem";
            case 2 : return "Sửa";
            case 3 : return "Xem và sửa";
            default : {}
        }
        return null;
    }
    public void loadDataTable(List<Decentralization> decentralizationList) {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);
        for (Decentralization decentralization : decentralizationList) {
            List<Object> objects = new ArrayList<>();
            objects.add(decentralization.getDecentralizationID());
            objects.add(parse(decentralization.getIsSale()));
            objects.add(parse(decentralization.getIsProduct()));
            objects.add(parse(decentralization.getIsCategory()));
            objects.add(parse(decentralization.getIsRecipe()));
            objects.add(parse(decentralization.getIsImport()));
            objects.add(parse(decentralization.getIsBill()));
            objects.add(parse(decentralization.getIsWarehouses()));
            objects.add(parse(decentralization.getIsAccount()));
            objects.add(parse(decentralization.getIsStaff()));
            objects.add(parse(decentralization.getIsCustomer()));
            objects.add(parse(decentralization.getIsDiscount()));
            objects.add(parse(decentralization.getIsDecentralization()));
            objects.add(decentralization.getDecentralizationName());
            model.addRow(objects.toArray());
        }
    }

    public boolean checkInput() {
        for (JTextField textField : jTextFieldsForm) {
            if (textField.getText().isEmpty()) {
                System.out.println(textField.getText());
                JOptionPane.showMessageDialog(this, "Please fill in information!", "Notification", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
        }
        return true;
    }
}
