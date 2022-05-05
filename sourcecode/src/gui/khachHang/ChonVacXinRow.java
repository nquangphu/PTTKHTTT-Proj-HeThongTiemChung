package gui.khachHang;

import dtos.LoaiVacXinDto;
import gui.table.cells.ButtonCell;

import java.awt.*;

/**
 * gui.table
 * Created by NhatLinh - 19127652
 * Date 4/24/2022 - 2:54 PM
 * Description: ...
 */
public class ChonVacXinRow extends VacXinRow {

    public ChonVacXinRow(LoaiVacXinDto lvx, Runnable onSelect) {
        super(lvx);
        ButtonCell select = new ButtonCell("Chọn", onSelect);
        setLayout(new GridLayout(1, 6));
        addCell(select);
    }
}
