package gui.khachHang;

import controllers.XuLyDangKyTiemChung;
import dtos.PhieuDKTCDto;
import gui.table.TablePanel;
import gui.table.cells.ButtonCell;
import gui.table.cells.ICell;
import gui.table.cells.LabelCell;
import gui.table.row.NColumnsPanel;

import java.awt.*;
import java.util.function.Supplier;

/**
 * gui.user
 * Created by NhatLinh - 19127652
 * Date 4/23/2022 - 11:53 PM
 * Description: ...
 */
public class MHXemDanhSachPDKTC extends MHXemDanhSach {

    private final MHChiTietPDKTC mhChiTietPDKTC;
    private final Supplier<Iterable<PhieuDKTCDto>> layPhieuDKTC;

    public MHXemDanhSachPDKTC(MHChiTietPDKTC mhChiTiet, Supplier<Iterable<PhieuDKTCDto>> layPhieuDKTC)
    {
        super(new TablePanel(new ICell[]{
                new LabelCell("Mã phiếu"),
                new LabelCell("Trạng thái phiếu"),
                new LabelCell("Ngày tạo phiếu"),
                new LabelCell("Tổng giá"),
                new LabelCell("Xem chi tiết")
        }));
        this.layPhieuDKTC = layPhieuDKTC;
        this.mhChiTietPDKTC = mhChiTiet;
        detailPanel.add(mhChiTietPDKTC, BorderLayout.CENTER);
        reloadPaperList();
    }

    public void reloadPaperList()
    {
        paperList.clearRows();
        Iterable<PhieuDKTCDto> pdks = layPhieuDKTC.get();
        for (PhieuDKTCDto pdk : pdks)
        {
            NColumnsPanel row = new NColumnsPanel(new ICell[]{
                    new LabelCell(String.valueOf(pdk.maPDKTC())),
                    new LabelCell(pdk.trangThai()),
                    new LabelCell(pdk.ngayTao().toString()),
                    new LabelCell(String.valueOf(pdk.tongGia())),
                    new ButtonCell("Xem chi tiết", () -> {
                        PhieuDKTCDto pdktc = XuLyDangKyTiemChung.layChiTietPDKTC(pdk.maPDKTC());
                        mhChiTietPDKTC.setPDK(pdktc);
                        cardLayout.show(this, "detail");
                    })
            }) {
                @Override
                public String getHeader() {
                    return String.valueOf(pdk.maPDKTC());
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
