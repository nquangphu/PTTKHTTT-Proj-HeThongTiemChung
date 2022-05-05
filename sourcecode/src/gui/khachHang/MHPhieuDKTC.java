package gui.khachHang;

import gui.khachHang.MHDangKyTiemChung;

import javax.swing.*;

/**
 * gui.register
 * Created by NhatLinh - 19127652
 * Date 4/24/2022 - 10:56 AM
 * Description: ...
 */
public class MHPhieuDKTC extends JTabbedPane {

    public MHPhieuDKTC(JPanel mhXemDanhSach, Runnable denDMVX)
    {
        super(JTabbedPane.TOP);
        add("Đăng ký tiêm chủng", new MHDangKyTiemChung(denDMVX));
        add("Xem danh sách phiếu đăng ký tiêm chủng", mhXemDanhSach);
    }
}
