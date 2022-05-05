package gui.khachHang;

import gui.table.TablePanel;
import gui.table.cells.ICell;
import gui.table.cells.LabelCell;
import gui.utilities.SearchBar;

import javax.swing.*;
import java.awt.*;

/**
 * gui.user
 * Created by NhatLinh - 19127652
 * Date 4/24/2022 - 1:16 AM
 * Description: ...
 */
public class MHVacXinKhongCoTrongHeThong extends JPanel {

    protected final TablePanel list = new TablePanel(new ICell[]{
            new LabelCell("Tên vắc xin"),
            new LabelCell("Nhà sản xuất"),
            new LabelCell("Mô tả"),
            new LabelCell("Ước lượng giá")
    });

    private final SearchBar searchBar = new SearchBar(new String[]{
            "Tên vắc xin",
            "Nhà sản xuất",
            "Mô tả"
    });

    public MHVacXinKhongCoTrongHeThong() {
        super(new BorderLayout());
        add(searchBar, BorderLayout.PAGE_START);
        add(list, BorderLayout.CENTER);
    }

    public MHVacXinKhongCoTrongHeThong(String[] moreTitles) {
        this();
        for (String t : moreTitles)
            list.getTitleRow().addCell(new LabelCell(t));
    }
}
