package gui.khachHang;

import controllers.XuLyDangKyTiemChung;
import dtos.PhieuDKTCDto;
import gui.table.TablePanel;
import gui.table.cells.ButtonCell;
import gui.table.cells.ICell;
import gui.table.cells.LabelCell;
import gui.table.row.NColumnsPanel;
import gui.utilities.SearchBar;
import gui.utilities.SearchBarWithReload;

import javax.swing.*;
import java.awt.*;

/**
 * gui.register
 * Created by NhatLinh - 19127652
 * Date 4/29/2022 - 6:01 PM
 * Description: ...
 */
public abstract class MHXemDanhSach extends JPanel {

    protected final SearchBar searchBar = new SearchBarWithReload(new String[]{
            "Mã phiếu", "Trạng thái phiếu"
    }, this::reloadPaperList);
    protected final CardLayout cardLayout = new CardLayout();
    protected final TablePanel paperList;
    protected final JPanel detailPanel = new JPanel(new BorderLayout());

    public MHXemDanhSach(TablePanel paperList)
    {
        super();
        this.paperList = paperList;
        setLayout(cardLayout);
        JPanel listPanel = new JPanel(new BorderLayout());
        JButton backToListBtn = new JButton("Trở về xem danh sách");
        backToListBtn.addActionListener(e -> {
            cardLayout.show(this, "list");
        });
        detailPanel.add(backToListBtn, BorderLayout.PAGE_START);

        searchBar.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        paperList.setBorder(BorderFactory.createTitledBorder("Danh sách phiếu"));
        listPanel.add(searchBar, BorderLayout.PAGE_START);
        listPanel.add(paperList.getAddComponent(), BorderLayout.CENTER);

        add("list", listPanel);
        add("detail", new JScrollPane(detailPanel));

        cardLayout.show(this, "list");
    }

    public abstract void reloadPaperList();
}
