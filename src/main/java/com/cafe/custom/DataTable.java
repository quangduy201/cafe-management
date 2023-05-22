package com.cafe.custom;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

            @Override
            public void mouseEntered(MouseEvent e) {
                if (getSelectedRow() == -1) {
                    lastSelectedRow = -1;
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
        });
    }

    public DataTable(Object[][] data, Object[] columnNames, ActionListener actionListener, Boolean checkbox) {
        super(new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 6) {
                    return Boolean.class;
                }
                return String.class;
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
                lastSelectedRow = rowAtPoint(e.getPoint());
                if (actionListener != null) {
                    actionListener.actionPerformed(null);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (getSelectedRow() == -1) {
                    lastSelectedRow = -1;
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
        });
    }
}
