package gui.khachHang;

import dtos.GoiTiemChungDto;
import gui.table.cells.ButtonCell;

import java.awt.*;

/**
 * gui.table
 * Created by NhatLinh - 19127652
 * Date 4/24/2022 - 2:44 PM
 * Description: ...
 */
public class ChonGTCRow extends GTCRow {

    public ChonGTCRow(GoiTiemChungDto gtc, Runnable onSelect) {
        this(gtc, "Chọn", onSelect);
    }

    public ChonGTCRow(GoiTiemChungDto gtc, String title, Runnable onSelect) {
        super(gtc);
        ButtonCell select = new ButtonCell(title, onSelect);
        setLayout(new GridLayout(1, 6));
        addCell(select);
    }
}
