package gui.khachHang;

import controllers.XuLyDatMuaVacXin;
import dtos.PhieuDMVXDto;
import gui.table.TablePanel;
import gui.table.cells.ButtonCell;
import gui.table.cells.ICell;
import gui.table.cells.LabelCell;
import gui.table.row.NColumnsPanel;

import java.awt.*;
import java.util.function.Supplier;

/**
 * gui.register
 * Created by NhatLinh - 19127652
 * Date 4/29/2022 - 6:36 PM
 * Description: ...
 */
public class MHXemDanhSachPDMVX extends MHXemDanhSach {
    private final MHChiTietPDMVX mhChiTietPDMVX;
    private final Supplier<Iterable<PhieuDMVXDto>> layPhieuDMVX;

    public MHXemDanhSachPDMVX(MHChiTietPDMVX mhChiTiet, Supplier<Iterable<PhieuDMVXDto>> layPhieuDMVX)
    {
        super(new TablePanel(new ICell[]{
                new LabelCell("Mã phiếu"),
                new LabelCell("Ngày tạo phiếu"),
                new LabelCell("Trạng thái phiếu"),
                new LabelCell("Xem chi tiết")
        }));
        this.layPhieuDMVX = layPhieuDMVX;
        this.mhChiTietPDMVX = mhChiTiet;
        detailPanel.add(mhChiTiet, BorderLayout.CENTER);
        reloadPaperList();
    }

    public void reloadPaperList()
    {
        paperList.clearRows();
        Iterable<PhieuDMVXDto> pdmvs = layPhieuDMVX.get();
        for (PhieuDMVXDto pdm : pdmvs)
        {
            NColumnsPanel row = new NColumnsPanel(new ICell[]{
                    new LabelCell(String.valueOf(pdm.maPDMVX())),
                    new LabelCell(pdm.ngayLap().toString()),
                    new LabelCell(pdm.trangThai()),
                    new ButtonCell("Xem chi tiết", () -> {
                        PhieuDMVXDto pdmvx = XuLyDatMuaVacXin.layChiTietPhieuDMVX(pdm.maPDMVX());
                        mhChiTietPDMVX.setPDMVX(pdmvx);
                        cardLayout.show(this, "detail");
                    })
            }) {
                @Override
                public String getHeader() {
                    return String.valueOf(pdm.maPDMVX());
                }

                @Override
                public Component getComponent() {
                    return this;
                }
            };
            paperList.addRow(row);
        }
        paperList.updateUI();
    }
}
