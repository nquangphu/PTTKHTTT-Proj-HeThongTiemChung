package gui.khachHang;

import controllers.XuLyDatMuaVacXin;
import dtos.PhieuDMVXDto;

import javax.swing.*;

/**
 * gui.register
 * Created by NhatLinh - 19127652
 * Date 4/30/2022 - 11:35 AM
 * Description: ...
 */
public class MHXemDanhSachPDMVXKhongDangNhap extends MHXemDanhSachKhongDangNhap {
    private final MHChiTietPDMVX mhChiTietPDMVX;

    public MHXemDanhSachPDMVXKhongDangNhap() {
        super(new MHChiTietPDMVX());
        this.mhChiTietPDMVX = (MHChiTietPDMVX) chiTietPhieu;
    }

    @Override
    public void layPhieu() {
        try
        {
            PhieuDMVXDto pdm = XuLyDatMuaVacXin.layChiTietPhieuDMVX(Integer.parseInt(inputField.getText()));
            if (pdm != null)
                mhChiTietPDMVX.setPDMVX(pdm);
            else
                throw new NullPointerException("error");
            chiTietPhieu.setVisible(true);
        }
        catch (Exception e)
        {
            chiTietPhieu.setVisible(false);
            JOptionPane.showMessageDialog(this, "Mã phiếu đặt mua vắc xin không hợp lệ! Xin vui lòng xem lại!");
        }
    }
}
