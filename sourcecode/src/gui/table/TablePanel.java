package gui.table;

import gui.table.cells.ICell;
import gui.table.row.IRow;
import gui.table.row.NColumnsPanel;
import gui.table.row.TitleRowPanel;
import gui.utilities.ScrollablePanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * administrator.gui.customTable
 * Created by NhatLinh - 19127652
 * Date 2/22/2022 - 3:41 PM
 * Description: ...
 */
public class TablePanel extends ScrollablePanel implements ITablePanel  {

    protected final NColumnsPanel title;
    protected final List<IRow> currentRows = new ArrayList<>();
    protected final JScrollPane addComp;

    public TablePanel(ICell[] titleCells)
    {
        super();
        addComp = new JScrollPane(this, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setScrollableWidth(ScrollableSizeHint.FIT);
        title = new TitleRowPanel(titleCells);
        title.setMaximumSize(new Dimension(Integer.MAX_VALUE, title.getPreferredSize().height));
        add(title);
        //setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public NColumnsPanel getTitleRow()
    {
        return title;
    }
    //public NRowsPanel getRows() {
        //return rows;
    //}

    @Override
    public void addRow(IRow row)
    {
        currentRows.add(row);
        //row.getComponent().setMaximumSize(new Dimension(Integer.MAX_VALUE, row.getComponent().getPreferredSize().height));
        add(row.getComponent());
    }

/*    public void addRows(Collection<IRow> rows)
    {
        for (IRow row : rows)
            addRow(row);
    }*/


    public void swapRow(int index, int next) {
        int finalNext = ++next;
        int finalIndex = ++index;
        Component t = getComponent(finalIndex);
        super.remove(finalIndex);
        add(t, finalNext);
    }

    public void clearRows()
    {
        currentRows.clear();
        removeAll();
        add(title);
    }

    @Override
    public void deleteRow(String header)
    {
        IRow delRow = null;
        for (IRow row : currentRows)
            if (row.getHeader().equalsIgnoreCase(header))
                delRow = row;
        if (delRow != null)
        {
            currentRows.remove(delRow);
            remove(delRow.getComponent());
        }
    }

/*    @Override
    public int getRow(String header)
    {
        for (int i = 0; i < currentRows.size(); i++)
            if (currentRows.get(i).getHeader().equals(header))
                return i;
        return -1;
    }*/

    public void hideRows(String exception)
    {
        for (IRow row : currentRows)
        {
            String header = row.getHeader();
            if (header.length() > exception.length())
                header = header.substring(0, exception.length());
            row.getComponent().setVisible(header.equalsIgnoreCase(exception));
        }
    }

    public void showRows()
    {
        for (IRow row : currentRows)
            row.getComponent().setVisible(true);
    }

    @Override
    public JComponent getAddComponent() {
        return addComp;
    }

    @Override
    public void setVisible(boolean visible)
    {
        addComp.setVisible(visible);
    }

    /*@Override
    public void updateUI()
    {
        super.updateUI();
    }*/
    public void remove(Component t)
    {
        if (title == t)
            return;
        super.remove(t);
    }
    /*public int getComponentCount(){
        return super.getComponentCount();
    }
    public Component getComponent(int index)
    {
        return super.getComponent(index);
    }*/

/*
    public void remove(int finalIndex){

        super.remove(finalIndex + 1);
    }

    public void addRow(IRow c, int next)
    {
        currentRows.add(c);
        SwingUtilities.invokeLater(() ->
                add(c.getComponent(), next));
    }
*/

    public Component[] getComponents()
    {
        Component[] original = super.getComponents();
        Component[] cs = new Component[original.length - 1];
        for (int i = 1; i < original.length; i++)
            cs[i - 1] = original[i];
        return cs;
    }
}
