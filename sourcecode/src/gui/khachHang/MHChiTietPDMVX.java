package gui.khachHang;

import dtos.*;

import java.awt.*;

/**
 * gui.register
 * Created by NhatLinh - 19127652
 * Date 4/29/2022 - 6:47 PM
 * Description: ...
 */
public class MHChiTietPDMVX extends MHChiTietPhieu {

    protected final MHDanhSachVacXinKhongTrongHeThong vacKhongHTShow = new MHDanhSachVacXinKhongTrongHeThong(false);
    public MHChiTietPDMVX() {
        super();
        detailedSection.setLayout(new GridLayout(3, 1));
        detailedSection.add(vacKhongHTShow);
    }

    public void setPDMVX(PhieuDMVXDto pdm)
    {
        gtcShow.getGtcHienThi().clearRows();
        vacShow.getVxHienThi().clearRows();
        vacKhongHTShow.layVacXinHienThi().clearRows();
        mhThongTinKhachHang.ganKH(pdm.kh());
        id.setText(String.valueOf(pdm.maPDMVX()));
        date.setText(pdm.ngayLap().toString());
        status.setText(pdm.trangThai());
        if (pdm.nv() == null)
            staff.setText("");
        else
            staff.setText(pdm.nv().hoTen());
        for (GoiTiemChungDto gtc : pdm.gtcs())
            gtcShow.getGtcHienThi().addRow(new GTCRow(gtc));
        for (LoaiVacXinDto lvx : pdm.lvxs())
            vacShow.getVxHienThi().addRow(new VacXinRow(lvx));
        for (LoaiVacXinKhongTrongHeThongDto lvx : pdm.lvxKhongHTs())
            vacKhongHTShow.layVacXinHienThi().addRow(new VacXinKhongTrongHeThongRow(lvx));
        gtcShow.getGtcHienThi().updateUI();
        vacShow.getVxHienThi().updateUI();
        vacKhongHTShow.layVacXinHienThi().updateUI();
    }
}
