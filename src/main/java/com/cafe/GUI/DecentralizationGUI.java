package com.cafe.GUI;

import com.cafe.BLL.AccountBLL;
import com.cafe.BLL.DecentralizationBLL;
import com.cafe.BLL.DecentralizationDetailBLL;
import com.cafe.BLL.ModuleBLL;
import com.cafe.DTO.Decentralization;
import com.cafe.DTO.DecentralizationDetail;
import com.cafe.DTO.Module;
import com.cafe.custom.Button;
import com.cafe.custom.DataTable;
import com.cafe.custom.RoundPanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DecentralizationGUI extends JPanel {
   private DecentralizationDetailBLL decentralizationDetailBLL = new DecentralizationDetailBLL();
    private DecentralizationBLL decentralizationBLL = new DecentralizationBLL();
    private AccountBLL accountBLL = new AccountBLL();
    private List<Module> modules = new ModuleBLL().searchModules();

    private DecentralizationDetail decentralizationMode;
    private DataTable dataTable;
    private RoundPanel decentralization;
    private RoundPanel roundPanel1;
    private RoundPanel roundPanel2;
    private RoundPanel roundPanel3;
    private RoundPanel search;
    private JScrollPane scrollPane;
    private JPanel pnlDecentralizationConfiguration;
    private RoundPanel mode;
    private JLabel[] jLabelsForm;
    private JLabel[] jLabelsForm1;
    private JComboBox<Object> cbbSearchFilter;
    private JCheckBox[] checkBox;
    private JTextField txtSearch;
    private JTextField[] jTextFieldsForm;
    private Button btAdd;
    private Button btUpd;
    private Button btDel;
    private Button btRef;

    private List<DecentralizationDetail> decentralizationDetail;

    public DecentralizationGUI(DecentralizationDetail decentralizationMode) {
        System.gc();
        this.decentralizationMode = decentralizationMode;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(70, 67, 67));
        initComponents();
    }

    public void initComponents() {
        decentralization = new RoundPanel();
        roundPanel1 = new RoundPanel();
        roundPanel2 = new RoundPanel();
        roundPanel3 = new RoundPanel();
        search = new RoundPanel();
        pnlDecentralizationConfiguration = new JPanel();
        mode = new RoundPanel();
        jLabelsForm = new JLabel[modules.size()];
        jLabelsForm1 = new JLabel[3];
        cbbSearchFilter = new JComboBox<>(new String[]{"Mã quyền", "Tên quyền"});
        //jComboBoxForm = new JComboBox[modules.size() - 3];
        checkBox = new JCheckBox[13 * 3];
        txtSearch = new JTextField(20);
        jTextFieldsForm = new JTextField[2];
        btAdd = new Button();
        btUpd = new Button();
        btDel = new Button();
        btRef = new Button();

        decentralization.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        decentralization.setBackground(new Color(70, 67, 67));
        this.add(decentralization, BorderLayout.CENTER);

        roundPanel2.setLayout(new BorderLayout(0, 10));
        roundPanel2.setBackground(new Color(70, 67, 67));
        roundPanel2.setPreferredSize(new Dimension(635, 670));
        roundPanel2.setAutoscrolls(true);
        roundPanel2.add(new JScrollPane(dataTable), BorderLayout.CENTER);
        decentralization.add(roundPanel2);

        roundPanel3.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 5));
        roundPanel3.setPreferredSize(new Dimension(635, 40));
        roundPanel3.setAutoscrolls(true);
        roundPanel2.add(roundPanel3, BorderLayout.NORTH);

        roundPanel1.setLayout(new BorderLayout());
        roundPanel1.setPreferredSize(new Dimension(350, 670));
        roundPanel1.setAutoscrolls(true);
        decentralization.add(roundPanel1);

        search.setLayout(new FlowLayout());
        search.setPreferredSize(new Dimension(635, 35));
        roundPanel1.add(search, BorderLayout.NORTH);

        cbbSearchFilter.addActionListener(e -> selectSearchFilter());
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

        dataTable = new DataTable(decentralizationBLL.getData(), new String[]{"Mã quyền", "Tên quyền"}, e -> fillForm());
        scrollPane = new JScrollPane(dataTable);
        roundPanel1.add(scrollPane);

        JScrollPane jScrollPane = new JScrollPane(pnlDecentralizationConfiguration);
        jScrollPane.setPreferredSize(new Dimension(600, 500));
        pnlDecentralizationConfiguration.setLayout(new GridLayout(14, 3, 40, 20));
        pnlDecentralizationConfiguration.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));
        pnlDecentralizationConfiguration.setPreferredSize(new Dimension(600, 500));
        roundPanel2.add(jScrollPane, BorderLayout.CENTER);

        jLabelsForm1[0] = new JLabel();
        jLabelsForm1[0].setText("Mã quyền: ");
        jTextFieldsForm[0] = new JTextField(decentralizationBLL.getAutoID());
        jTextFieldsForm[0].setEnabled(false);
        jTextFieldsForm[0].setBorder(null);
        jTextFieldsForm[0].setDisabledTextColor(null);
        roundPanel3.add(jLabelsForm1[0]);
        roundPanel3.add(jTextFieldsForm[0]);

        jLabelsForm1[1] = new JLabel();
        jLabelsForm1[1].setText("Tên quyền: ");
        jTextFieldsForm[1] = new JTextField();
        jTextFieldsForm[1].setPreferredSize(new Dimension(200, 30));
        jTextFieldsForm[1].setText(null);
        roundPanel3.add(jLabelsForm1[1]);
        roundPanel3.add(jTextFieldsForm[1]);


        int index2 = 0;
        for (int i = 0; i < modules.size(); i++) {
            jLabelsForm[i] = new JLabel();
            pnlDecentralizationConfiguration.add(jLabelsForm[i]);
            checkBox[index2] = new JCheckBox("Xem");
            checkBox[index2 + 1] = new JCheckBox("Sửa");
            checkBox[index2 + 2] = new JCheckBox("Xóa");
            pnlDecentralizationConfiguration.add(checkBox[index2]);
            pnlDecentralizationConfiguration.add(checkBox[index2 + 1]);
            pnlDecentralizationConfiguration.add(checkBox[index2 + 2]);
            index2 += 3;
        }

        jLabelsForm[0].setText("Bán hàng: ");
        jLabelsForm[1].setText("Nhập hàng: ");
        jLabelsForm[2].setText("Hoá đơn: ");
        jLabelsForm[3].setText("Nhà kho: ");
        jLabelsForm[4].setText("Sản phẩm: ");
        jLabelsForm[5].setText("Thể loại: ");
        jLabelsForm[6].setText("Công thức: ");
        jLabelsForm[7].setText("Giảm giá: ");
        jLabelsForm[8].setText("Khách hàng: ");
        jLabelsForm[9].setText("Nhân viên: ");
        jLabelsForm[10].setText("Tài khoản: ");
        jLabelsForm[11].setText("Phân quyền: ");
        jLabelsForm[12].setText("Nhà cung cấp: ");

        for (int i = 0; i < 39; i++) {
            checkBox[i].setSelected(false);
            checkBox[i].setEnabled(false);
        }

        mode.setLayout(new GridLayout(2, 2, 20, 20));
        mode.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mode.setPreferredSize(new Dimension(635, 130));
        roundPanel2.add(mode, BorderLayout.SOUTH);

        if (decentralizationMode.isCanADD()) {
            Button.configButton(btAdd, List.of("  Thêm", "img/icons/plus.png", true, (Runnable) this::addDecentralization));
            mode.add(btAdd);
        }

        if (decentralizationMode.isCanEDIT()) {
            Button.configButton(btUpd, List.of("  Sửa", "img/icons/wrench.png", false, (Runnable) this::updateDecentralization));
            mode.add(btUpd);
        }

        if (decentralizationMode.isCanREMOVE()) {
            Button.configButton(btDel, List.of("  Xóa", "img/icons/delete.png", false, (Runnable) this::deleteDecentralization));
            mode.add(btDel);
        }

        if (decentralizationMode.isCanADD()) {
            Button.configButton(btRef, List.of("  Làm mới", "img/icons/refresh.png", true, (Runnable) this::refreshForm));
            mode.add(btRef);
        } else {
            dataTable.setRowSelectionInterval(0, 0);
            fillForm();
        }
    }

    private void selectSearchFilter() {
        //jComboBoxSearch.setVisible(false);
        txtSearch.setVisible(true);
        searchDecentralizations();
    }

    public void searchDecentralizations() {
        if (txtSearch.getText().isEmpty()) {
            loadDataTable(decentralizationBLL.getDecentralizationList());
        } else {
            String key = null;
            switch (cbbSearchFilter.getSelectedIndex()) {
                case 0 -> key = "DECENTRALIZATION_ID";
                case 1 -> key = "DECENTRALIZATION_NAME";
                default -> {
                }
            }
            assert key != null;
            loadDataTable(decentralizationBLL.findDecentralizations(key, txtSearch.getText()));
        }
    }

    public void addDecentralization() {
        if (checkInput()) {
            Decentralization newDecentralization = getForm();
            if (decentralizationBLL.exists(newDecentralization))
                JOptionPane.showMessageDialog(this, "Quyền đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (decentralizationBLL.exists(Map.of("DECENTRALIZATION_NAME", newDecentralization.getDecentralizationName())))
                JOptionPane.showMessageDialog(this, "Quyền đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (decentralizationBLL.addDecentralization(newDecentralization)) {
                JOptionPane.showMessageDialog(this, "Thêm quyền mới thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                for(int i = 0; i < 13; i++) {
                    decentralizationDetailBLL.addDecentralizationDetail(
                        new DecentralizationDetail(newDecentralization.getDecentralizationID()
                        ,modules.get(i).getModuleID(),false,false,false,false));
                }
            }
            else
                JOptionPane.showMessageDialog(this, "Thêm quyền mới thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            refreshForm();
        }
    }

    public void updateDecentralization() {
        if (checkInput()) {
            Decentralization newDecentralization = getForm();
            int selectedRow = dataTable.getSelectedRow();
//            if (decentralizationBLL.exists(newDecentralization))
//                JOptionPane.showMessageDialog(this, "Quyền đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            if (decentralizationBLL.updateDecentralization(newDecentralization))
                JOptionPane.showMessageDialog(this, "Sửa quyền thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Sửa quyền thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            List<DecentralizationDetail> decentralizationDetails = getDecentralizationDetails();
            for (DecentralizationDetail decentralizationDetail1 : decentralizationDetails)
                decentralizationDetailBLL.updateDecentralizationDetail(decentralizationDetail1);;
            loadDataTable(decentralizationBLL.getDecentralizationList());
            dataTable.setRowSelectionInterval(selectedRow, selectedRow);
            fillForm();
//            Account account = CafeManagement.homeGUI.getAccount();
//            CafeManagement.homeGUI.setAccount(account);
        }
    }

    private void deleteDecentralization() {
        if (JOptionPane.showOptionDialog(this,
            "Bạn có chắc chắn muốn xoá quyền này?",
            "Xác nhận",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new String[]{"Xoá", "Huỷ"},
            "Xoá") == JOptionPane.YES_OPTION) {
            Decentralization decentralization = new Decentralization();
            decentralization.setDecentralizationID(jTextFieldsForm[0].getText());
            if (!accountBLL.findAccounts("DECENTRALIZATION_ID", decentralization.getDecentralizationID()).isEmpty())
                JOptionPane.showMessageDialog(this, "Quyền đang có tài khoản không được xoá!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (decentralizationBLL.deleteDecentralization(decentralization)) {
                JOptionPane.showMessageDialog(this, "Xoá quyền thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                decentralizationDetail = new DecentralizationDetailBLL()
                    .searchDecentralizationDetail("DECENTRALIZATION_ID = '" + jTextFieldsForm[0].getText() + "'");
//                int index = 0;
                for(int i = 0; i < 13; i++) {
                    decentralizationDetailBLL.deleteDecentralizationDetail(decentralizationDetail.get(i));
//                    if(decentralizationDetail.get(i).isCanADD() &&
//                    decentralizationDetail.get(i).isCanEDIT() &&
//                    decentralizationDetail.get(i).isCanREMOVE()) index++;
                }
//                Account account = CafeManagement.homeGUI.getAccount();
//                if(index == 12 && jTextFieldsForm[0].getText().equals(account.getDecentralizationID())) {
//                    CafeManagement.homeGUI.dispose();
//                    CafeManagement.loginGUI.setVisible(true);
//                }
            }

            else
                JOptionPane.showMessageDialog(this, "Xoá quyền thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            refreshForm();
        }
    }

    public void refreshForm() {
        cbbSearchFilter.setSelectedIndex(0);
        txtSearch.setText(null);
        loadDataTable(decentralizationBLL.getDecentralizationList());
        jTextFieldsForm[0].setText(decentralizationBLL.getAutoID());
        jTextFieldsForm[1].setText(null);
        for (int i = 0; i < 39; i++) {
            checkBox[i].setSelected(false);
            checkBox[i].setEnabled(false);
        }
        btAdd.setEnabled(true);
        btUpd.setEnabled(false);
        btDel.setEnabled(false);
    }

    public void fillForm() {
        for (int i = 0; i < 39; i++) {
            checkBox[i].setSelected(false);
            checkBox[i].setEnabled(true);
        }
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        Object[] rowData = model.getDataVector().elementAt(dataTable.getSelectedRow()).toArray();
        String[] data = new String[rowData.length];
        for (int i = 0; i < rowData.length; i++) {
            data[i] = rowData[i].toString();
        }
        String[] decentralization = String.join(" | ", data).split(" \\| ");
        decentralizationDetail = new DecentralizationDetailBLL()
            .searchDecentralizationDetail("DECENTRALIZATION_ID = '" + decentralization[0] + "'");
        jTextFieldsForm[0].setText(decentralization[0]);
        jTextFieldsForm[1].setText(decentralization[decentralization.length - 1]);
        int index = 0;
        for (int i = 0; i < decentralizationDetail.size(); i++) {
            if(decentralizationDetail.get(i).isCanADD()) checkBox[index].setSelected(true);
            if(decentralizationDetail.get(i).isCanEDIT())  checkBox[index + 1].setSelected(true);
            if(decentralizationDetail.get(i).isCanREMOVE()) checkBox[index + 2].setSelected(true);
            index += 3;
        }
        btAdd.setEnabled(false);
        btUpd.setEnabled(true);
        btDel.setEnabled(true);
    }

    public Decentralization getForm() {
        String decentralizationID;
        List<String> args = new ArrayList<>();
        String decentralizationName;
        decentralizationID = jTextFieldsForm[0].getText();
        decentralizationName = jTextFieldsForm[1].getText();
//        for (JComboBox objectJComboBox : jComboBoxForm) {
//            args.add(Objects.requireNonNull(objectJComboBox.getSelectedItem()).toString());
//        }
        return new Decentralization(decentralizationID, decentralizationName, false);
    }

    public List<DecentralizationDetail> getDecentralizationDetails() {
        List<DecentralizationDetail> decentralizationDetails = new ArrayList<>();
        String decentralizationID = jTextFieldsForm[0].getText();
        int index = 0;
        for (int i = 0; i < 13; ++i) {
            String moduleID = modules.get(i).getModuleID();
            boolean add, edit, remove;
            add = checkBox[index].isSelected();
            edit = checkBox[index + 1].isSelected();
            remove = checkBox[index + 2].isSelected();
            index += 3;
            decentralizationDetails.add(new DecentralizationDetail(decentralizationID, moduleID, add, edit, remove, false));
        }
        return decentralizationDetails;
    }

    public void loadDataTable(List<Decentralization> decentralizationList) {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);
        for (Decentralization decentralization : decentralizationList) {
            List<Object> objects = new ArrayList<>();
            objects.add(decentralization.getDecentralizationID());
            objects.add(decentralization.getDecentralizationName());
            model.addRow(objects.toArray());
        }
    }

    public boolean checkInput() {
        for (JTextField textField : jTextFieldsForm) {
            if (textField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                textField.requestFocusInWindow();
                return false;
            }
        }
        if (!jTextFieldsForm[1].getText().matches("^[^|]+$")) {
            // Name can't contain "|"
            jTextFieldsForm[1].requestFocusInWindow();
            jTextFieldsForm[1].selectAll();
            JOptionPane.showMessageDialog(this, "Tên quyền không được chứa \"|\"", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
