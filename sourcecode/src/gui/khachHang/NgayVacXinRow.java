package gui.khachHang;

import dtos.LoaiVacXinDto;
import gui.table.cells.LabelCell;

import java.awt.*;
import java.util.Date;

/**
 * gui.table
 * Created by NhatLinh - 19127652
 * Date 4/27/2022 - 10:31 PM
 * Description: ...
 */
public class NgayVacXinRow extends VacXinRow {
    private final LabelCell date;
    public NgayVacXinRow(LoaiVacXinDto lvx, Date ngayTiem)
    {
            super(lvx);
            date = new LabelCell(ngayTiem.toString());
            setLayout(new GridLayout(1, 6));
            addCell(date);
    }
}
