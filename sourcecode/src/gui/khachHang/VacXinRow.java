package gui.khachHang;

import dtos.GoiTiemChungDto;
import dtos.LoaiVacXinDto;
import gui.table.cells.LabelCell;
import gui.table.row.NColumnsPanel;

import java.awt.*;

/**
 * gui.table
 * Created by NhatLinh - 19127652
 * Date 4/24/2022 - 2:48 PM
 * Description: ...
 */
public class VacXinRow extends NColumnsPanel {
    private final LoaiVacXinDto vacXin;
    private final LabelCell id;
    private final LabelCell name;
    private final LabelCell price;
    private final LabelCell amount;
    private final LabelCell description;

    public VacXinRow(LoaiVacXinDto vacXin)
    {
        super(5);
        this.vacXin = vacXin;
        id = new LabelCell(String.valueOf(vacXin.maLoaiVX()));
        name = new LabelCell(vacXin.tenLoai());
        price = new LabelCell(String.valueOf(vacXin.giaBan()));
        amount = new LabelCell(String.valueOf(vacXin.tonKho()));
        description = new LabelCell(String.valueOf(vacXin.moTa()));
        description.setToolTipText(String.valueOf(vacXin.moTa()));

        addCell(id, name, price, amount, description);
    }

    @Override
    public String getHeader() {
        return id.getText();
    }

    @Override
    public Component getComponent() {
        return this;
    }

    public LoaiVacXinDto getVX() {
        return vacXin;
    }
}
