package gui.khachHang;

import dtos.GoiTiemChungDto;
import gui.table.cells.LabelCell;

import java.awt.*;
import java.util.Date;

/**
 * gui.table
 * Created by NhatLinh - 19127652
 * Date 4/27/2022 - 10:29 PM
 * Description: ...
 */
public class NgayGTCRow extends GTCRow {

    private final LabelCell date;
    public NgayGTCRow(GoiTiemChungDto gtc, Date ngayTiem) {
        super(gtc);
        date = new LabelCell(ngayTiem.toString());
        setLayout(new GridLayout(1, 6));
        addCell(date);
    }
}
