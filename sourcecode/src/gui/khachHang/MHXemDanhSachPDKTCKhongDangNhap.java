package gui.khachHang;

import controllers.XuLyDangKyTiemChung;
import dtos.PhieuDKTCDto;

import javax.swing.*;

/**
 * gui.register
 * Created by NhatLinh - 19127652
 * Date 4/30/2022 - 11:30 AM
 * Description: ...
 */
public class MHXemDanhSachPDKTCKhongDangNhap extends MHXemDanhSachKhongDangNhap {

    private final MHChiTietPDKTCKhachHang mhKhachHangChiTietPDKTC;

    public MHXemDanhSachPDKTCKhongDangNhap() {
        super(new MHChiTietPDKTCKhachHang());
        this.mhKhachHangChiTietPDKTC = (MHChiTietPDKTCKhachHang) chiTietPhieu;

    }

    @Override
    public void layPhieu() {
        try
        {
            PhieuDKTCDto pdk = XuLyDangKyTiemChung.layChiTietPDKTC(Integer.parseInt(inputField.getText()));
            if (pdk != null)
                mhKhachHangChiTietPDKTC.setPDK(pdk);
            else
                throw new NullPointerException("error");
            chiTietPhieu.setVisible(true);
        }
        catch (Exception e)
        {
            chiTietPhieu.setVisible(false);
            JOptionPane.showMessageDialog(this, "Mã phiếu đăng kí tiêm chủng không hợp lệ! Xin vui lòng xem lại!");
        }
    }
}
