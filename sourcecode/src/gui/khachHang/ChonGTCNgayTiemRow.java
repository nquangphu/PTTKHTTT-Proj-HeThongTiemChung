package gui.khachHang;

import dtos.GoiTiemChungDto;
import gui.table.cells.ButtonCell;
import gui.table.cells.DatePickerCell;

import java.awt.*;
import java.util.Date;

/**
 * gui.table
 * Created by NhatLinh - 19127652
 * Date 4/24/2022 - 3:50 PM
 * Description: ...
 */
public class ChonGTCNgayTiemRow extends GTCRow {
    private final DatePickerCell datePicker = new DatePickerCell();
    public ChonGTCNgayTiemRow(GoiTiemChungDto gtc, Runnable onSelect) {
        super(gtc);
        ButtonCell select = new ButtonCell("Xóa", onSelect);
        setLayout(new GridLayout(1, 7));
        addCell(datePicker, select);
    }
    public Date getDate()
    {
        return datePicker.getDateModel().getValue();
    }
}
