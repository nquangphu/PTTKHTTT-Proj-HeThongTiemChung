package gui.khachHang;

import dtos.ChiTietPhieuDKTCDto;
import dtos.GoiTiemChungDto;
import dtos.LoaiVacXinDto;
import dtos.PhieuDKTCDto;

import javax.swing.*;
import java.awt.*;

/**
 * gui.user
 * Created by NhatLinh - 19127652
 * Date 4/24/2022 - 12:01 AM
 * Description: ...
 */
public class MHChiTietPDKTC extends MHChiTietPhieu {
    private final JTextField priceField = new JTextField();

    public MHChiTietPDKTC()
    {
        super(
                new MHDanhSachGoiTiemChung(new String[]{
                        "Ngày muốn tiêm"
                }),
                new MHDanhSachVacXin(new String[]{
                        "Ngày muốn tiêm"
                })
        );
        priceField.setBorder(BorderFactory.createTitledBorder("Tổng giá phải trả"));
        priceSection.add(priceField, BorderLayout.PAGE_END);
    }

    public void setPDK(PhieuDKTCDto pdk)
    {
        gtcShow.getGtcHienThi().clearRows();
        vacShow.getVxHienThi().clearRows();
        mhThongTinKhachHang.ganKH(pdk.khachHang());
        id.setText(String.valueOf(pdk.maPDKTC()));
        date.setText(pdk.ngayTao().toString());
        status.setText(pdk.trangThai());
        priceField.setEditable(false);
        priceField.setText(String.valueOf(pdk.tongGia()));
        if (pdk.nhanVien() == null)
            staff.setText("");
        else
            staff.setText(pdk.nhanVien().hoTen());
        for (ChiTietPhieuDKTCDto ctp : pdk.chiTietPhieu())
        {
            if (ctp.goiTiemChung() != null)
            {
                GoiTiemChungDto gtc = ctp.goiTiemChung();
                NgayGTCRow row = new NgayGTCRow(gtc, ctp.ngayMuonTiem());
                gtcShow.getGtcHienThi().addRow(row);
            }
            else
            {
                LoaiVacXinDto vac = ctp.loaiVacXin();
                NgayVacXinRow row = new NgayVacXinRow(vac, ctp.ngayMuonTiem());
                vacShow.getVxHienThi().addRow(row);
            }
        }
        gtcShow.getGtcHienThi().updateUI();
        vacShow.getVxHienThi().updateUI();
    }
}
