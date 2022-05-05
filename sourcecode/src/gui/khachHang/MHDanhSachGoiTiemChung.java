package gui.khachHang;

import dtos.ChiTietPhieuDKTCDto;
import dtos.GoiTiemChungDto;
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
 * Date 4/23/2022 - 11:20 PM
 * Description: ...
 */
public class MHDanhSachGoiTiemChung extends JPanel {

    protected final ITablePanel gtcHienThi = new TablePanel(new ICell[]{
            new LabelCell("Mã gói"),
            new LabelCell("Tên gói"),
            new LabelCell("Giá gói"),
            new LabelCell("Số lượng còn lại"),
            new LabelCell("Mô tả")
    });

    private final SearchBar thanhTimKiem;

    public MHDanhSachGoiTiemChung() {
        super(new BorderLayout());
        thanhTimKiem = new SearchBar(new String[]{
                "Mã gói", "Tên gói"
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
        add(gtcHienThi.getAddComponent(), BorderLayout.CENTER);
    }


    public MHDanhSachGoiTiemChung(Runnable reload) {
        super(new BorderLayout());
        thanhTimKiem = new SearchBarWithReload(new String[]{
                "Mã gói", "Tên gói"
        },
                () -> {
                    reload.run();
                    hint();
                });
        addComponents();
    }

    public MHDanhSachGoiTiemChung(String[] moreTitles, Runnable reload) {
        this(reload);
        for (String t : moreTitles)
            gtcHienThi.getTitleRow().addCell(new LabelCell(t));
    }

    public MHDanhSachGoiTiemChung(String[] moreTitles) {
        this();
        for (String t : moreTitles)
            gtcHienThi.getTitleRow().addCell(new LabelCell(t));
    }

    private void hint()
    {
        String input = thanhTimKiem.getInputField().getText();
        if (input.isBlank())
            gtcHienThi.showRows();
        else
        {
            for (Component c : gtcHienThi.getComponents())
            {
                try
                {
                    GTCRow row = (GTCRow) c;
                    String header =
                            thanhTimKiem.getFilter().getSelectedIndex() == 0 ?
                                    String.valueOf(row.getGtc().maGoi())
                                    :
                                    String.valueOf(row.getGtc().tenGoi()) ;
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


    public java.util.List<GoiTiemChungDto> layGTCs()
    {
        java.util.List<GoiTiemChungDto> gtcs = new ArrayList<>();
        for (Component c : gtcHienThi.getComponents())
        {
            try
            {
                GTCRow row = (GTCRow) c;
                gtcs.add(row.getGtc());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return gtcs;
    }

    public java.util.List<ChiTietPhieuDKTCDto> layCtps()
    {
        java.util.List<ChiTietPhieuDKTCDto> ctps = new ArrayList<>();
        for (Component c : gtcHienThi.getComponents())
        {
            try
            {
                ChonGTCNgayTiemRow row = (ChonGTCNgayTiemRow) c;
                ctps.add(new ChiTietPhieuDKTCDto(row.getGtc(), row.getDate()));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return ctps;
    }


    public ITablePanel getGtcHienThi() {
        return gtcHienThi;
    }
}
