package gui.khachHang;

import dtos.GoiTiemChungDto;
import gui.table.cells.ICell;
import gui.table.cells.LabelCell;
import gui.table.row.IRow;
import gui.table.row.NColumnsPanel;

import java.awt.*;

/**
 * gui.table
 * Created by NhatLinh - 19127652
 * Date 4/24/2022 - 12:23 PM
 * Description: ...
 */
public class GTCRow extends NColumnsPanel {

    private final GoiTiemChungDto gtc;
    private final LabelCell id;
    private final LabelCell name;
    private final LabelCell price;
    private final LabelCell amount;
    private final LabelCell description;

    public GTCRow(GoiTiemChungDto gtc)
    {
        super(5);
        this.gtc = gtc;
        id = new LabelCell(String.valueOf(gtc.maGoi()));
        name = new LabelCell(gtc.tenGoi());
        price = new LabelCell(String.valueOf(gtc.giaGoi()));
        amount = new LabelCell(String.valueOf(gtc.tonKho()));
        description = new LabelCell(gtc.moTa());
        description.setToolTipText(gtc.moTa());
        addCell(id, name, price, amount, description);
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public String getHeader() {
        return String.valueOf(id.getText());
    }

    public GoiTiemChungDto getGtc() {
        return gtc;
    }
}
