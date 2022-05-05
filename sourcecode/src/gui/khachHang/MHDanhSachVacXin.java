package gui.khachHang;

import dtos.ChiTietPhieuDKTCDto;
import dtos.LoaiVacXinDto;
import gui.table.ITablePanel;
import gui.table.TablePanel;
import gui.table.cells.ICell;
import gui.table.cells.LabelCell;
import gui.utilities.SearchBar;
import gui.utilities.SearchBarWithReload;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;

/**
 * gui.table
 * Created by NhatLinh - 19127652
 * Date 4/23/2022 - 11:23 PM
 * Description: ...
 */
public class MHDanhSachVacXin extends JPanel {

    public ITablePanel getVxHienThi() {
        return vxHienThi;
    }

    protected final ITablePanel vxHienThi = new TablePanel(new ICell[]{
            new LabelCell("Mã vắc xin"),
            new LabelCell("Tên vắc xin"),
            new LabelCell("Giá vắc xin"),
            new LabelCell("Số lượng còn lại"),
            new LabelCell("Mô tả")
    });

    private final SearchBar thanhTimKiem;

    public MHDanhSachVacXin() {
        super(new BorderLayout());
        thanhTimKiem = new SearchBar(new String[]{
                "Mã vắc xin", "Tên vắc xin"
        });
        addComponents();
    }

    public MHDanhSachVacXin(String[] moreTitles) {
        this();
        for (String t : moreTitles)
            vxHienThi.getTitleRow().addCell(new LabelCell(t));
        addComponents();
    }

    public MHDanhSachVacXin(Runnable reload) {
        super(new BorderLayout());
        thanhTimKiem = new SearchBarWithReload(new String[]{
                "Mã vắc xin", "Tên vắc xin"
        },
                () -> {
                    reload.run();
                    hint();
                });
        addComponents();
    }

    private void addComponents()
    {
        thanhTimKiem.getInputField().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                hint();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                hint();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                hint();
            }
        });
        add(thanhTimKiem, BorderLayout.PAGE_START);
        add(vxHienThi.getAddComponent(), BorderLayout.CENTER);
    }

    public MHDanhSachVacXin(String[] moreTitles, Runnable reload) {
        this(reload);
        for (String t : moreTitles)
            vxHienThi.getTitleRow().addCell(new LabelCell(t));
    }

    private void hint()
    {
        String input = thanhTimKiem.getInputField().getText();
        if (input.isBlank())
            vxHienThi.showRows();
        else
        {
            for (Component c : vxHienThi.getComponents())
            {
                try
                {
                    VacXinRow row = (VacXinRow) c;
                    String header =
                            thanhTimKiem.getFilter().getSelectedIndex() == 0 ?
                            String.valueOf(row.getVX().maLoaiVX())
                            :
                            String.valueOf(row.getVX().tenLoai()) ;
                    if (header.length() > input.length())
                        header = header.substring(0, input.length());
                    row.setVisible(header.equalsIgnoreCase(input));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }


    public java.util.List<ChiTietPhieuDKTCDto> layCtps()
    {
        java.util.List<ChiTietPhieuDKTCDto> ctps = new ArrayList<>();
        for (Component c : vxHienThi.getComponents())
        {
            try
            {
                ChonVacXinNgayTiemRow row = (ChonVacXinNgayTiemRow) c;
                ctps.add(new ChiTietPhieuDKTCDto(row.getVX(), row.getDate()));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return ctps;
    }

    public java.util.List<LoaiVacXinDto> layLVXs()
    {
        java.util.List<LoaiVacXinDto> lvxs = new ArrayList<>();
        for (Component c : vxHienThi.getComponents())
        {
            try
            {
                VacXinRow row = (VacXinRow) c;
                lvxs.add(row.getVX());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return lvxs;
    }
}
