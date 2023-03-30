package com.cafe.custom;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Function;

public class DataTable extends JTable {
    private int lastSelectedRow = -1;
    public DataTable(Object[][] data, Object[] columnNames, ActionListener actionListener) {
        super(new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        getTableHeader().setFont(new Font("Public Sans", Font.BOLD | Font.ITALIC, 15));
        getTableHeader().setReorderingAllowed(false);
        getTableHeader().setResizingAllowed(false);

        setFont(new Font("Public Sans", Font.PLAIN, 15));
        setAutoCreateRowSorter(false);
        setRowHeight(20);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = rowAtPoint(e.getPoint());
                if (row != -1 && row != lastSelectedRow) {
                    lastSelectedRow = row;
                    if (actionListener != null) {
                        actionListener.actionPerformed(null);
                    }
                }
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int row = rowAtPoint(e.getPoint());
                if (row != -1 && row != lastSelectedRow) {
                    setRowSelectionInterval(lastSelectedRow, lastSelectedRow);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int row = rowAtPoint(e.getPoint());
                if (row != -1 && row != lastSelectedRow) {
                    setRowSelectionInterval(lastSelectedRow, lastSelectedRow);
                }
            }
        });
    }
}
