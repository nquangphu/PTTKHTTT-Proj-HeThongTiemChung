package gui.table;

import gui.table.row.IRow;
import gui.table.row.NColumnsPanel;

import javax.swing.*;
import java.awt.*;

/**
 * table
 * Created by NhatLinh - 19127652
 * Date 3/30/2022 - 9:54 AM
 * Description: ...
 */
public interface ITablePanel {
    void addRow(IRow row);
    void deleteRow(String header);
    void remove(Component t);
    void clearRows();
    NColumnsPanel getTitleRow();
    JComponent getAddComponent();
    Component[] getComponents();
    void setVisible(boolean visible);
    void updateUI();
    void hideRows(String exception);
    void showRows();
}
