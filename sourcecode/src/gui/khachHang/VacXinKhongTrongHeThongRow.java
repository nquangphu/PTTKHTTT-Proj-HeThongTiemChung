package gui.khachHang;

import dtos.LoaiVacXinKhongTrongHeThongDto;
import gui.table.cells.LabelCell;
import gui.table.cells.TextCell;
import gui.table.row.NColumnsPanel;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * gui.table
 * Created by NhatLinh - 19127652
 * Date 4/29/2022 - 4:47 PM
 * Description: ...
 */
public class VacXinKhongTrongHeThongRow extends NColumnsPanel {

    private final TextCell nameField;
    private final TextCell factoryField;
    private final TextCell priceField;

    public VacXinKhongTrongHeThongRow()
    {
        super(3);
        nameField = new TextCell("");
        factoryField = new TextCell("");
        priceField = new TextCell("");
        addCell(nameField, factoryField, priceField);
    }

    public VacXinKhongTrongHeThongRow(LoaiVacXinKhongTrongHeThongDto lvx)
    {
        super(3);
        nameField = new TextCell(lvx.ten());
        factoryField = new TextCell(lvx.nsx());
        priceField = new TextCell(String.valueOf(lvx.giaUocLuong()));
        nameField.setEditable(false);
        factoryField.setEditable(false);
        priceField.setEditable(false);
        priceField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar()))
                    e.consume();
            }
        });
        addCell(nameField, factoryField, priceField);
    }

    public LoaiVacXinKhongTrongHeThongDto getLvx()
    {
        if (nameField.getText().isBlank())
            return null;
        return new LoaiVacXinKhongTrongHeThongDto(nameField.getText(), factoryField.getText(), Float.parseFloat(priceField.getText()));
    }


    @Override
    public String getHeader() {
        return nameField.getText();
    }

    @Override
    public Component getComponent() {
        return this;
    }
}
