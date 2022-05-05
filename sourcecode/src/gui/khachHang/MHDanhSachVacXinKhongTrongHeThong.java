package gui.khachHang;

import dtos.LoaiVacXinKhongTrongHeThongDto;
import gui.table.ITablePanel;
import gui.table.TablePanel;
import gui.table.cells.ICell;
import gui.table.cells.LabelCell;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * gui.table
 * Created by NhatLinh - 19127652
 * Date 4/29/2022 - 4:54 PM
 * Description: ...
 */
public class MHDanhSachVacXinKhongTrongHeThong extends JPanel {

    protected final ITablePanel vxHienThi = new TablePanel(new ICell[]{
            new LabelCell("Tên vắc xin (bắt buộc)"),
            new LabelCell("Nhà sản xuất"),
            new LabelCell("Giá ước lượng")
    });

    public MHDanhSachVacXinKhongTrongHeThong(boolean isEdit) {
        super(new BorderLayout());
        if (isEdit) {
            JButton addRowBtn = new JButton("Thêm loại mới");
            addRowBtn.addActionListener(e -> {
                VacXinKhongTrongHeThongRow row = new VacXinKhongTrongHeThongRow();
                vxHienThi.addRow(row);
                vxHienThi.updateUI();
            });
            add(addRowBtn, BorderLayout.PAGE_START);
        }
        add(vxHienThi.getAddComponent(), BorderLayout.CENTER);
    }

    public java.util.List<LoaiVacXinKhongTrongHeThongDto> layLvxs()
    {
        java.util.List<LoaiVacXinKhongTrongHeThongDto> vxs = new ArrayList<>();
        for (Component c : vxHienThi.getComponents())
        {
            try
            {
                VacXinKhongTrongHeThongRow row = (VacXinKhongTrongHeThongRow) c;
                LoaiVacXinKhongTrongHeThongDto lvx = row.getLvx();
                if (lvx != null)
                    vxs.add(lvx);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return vxs;
    }

    public ITablePanel layVacXinHienThi() {
        return vxHienThi;
    }
}
