package gui.khachHang;

import dtos.LoaiVacXinDto;
import gui.table.cells.ButtonCell;
import gui.table.cells.DatePickerCell;

import java.awt.*;
import java.util.Date;

/**
 * gui.table
 * Created by NhatLinh - 19127652
 * Date 4/24/2022 - 3:58 PM
 * Description: ...
 */
public class ChonVacXinNgayTiemRow extends VacXinRow {
    private final DatePickerCell datePicker = new DatePickerCell();
    public ChonVacXinNgayTiemRow(LoaiVacXinDto vacXin, Runnable onSelect) {
        super(vacXin);
        ButtonCell select = new ButtonCell("Chọn", onSelect);
        setLayout(new GridLayout(1, 7));
        addCell(datePicker, select);
    }

    public Date getDate()
    {
        return datePicker.getDateModel().getValue();
    }
}
