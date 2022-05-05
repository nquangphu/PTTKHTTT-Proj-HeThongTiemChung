package gui.khachHang;

import gui.MHNguoiDung;

import javax.swing.*;

/**
 * gui
 * Created by NhatLinh - 19127652
 * Date 4/24/2022 - 10:52 AM
 * Description: ...
 */
public class MHKhachHang extends MHNguoiDung {

    public MHKhachHang(Runnable onClose, JPanel mhXemDanhSachPDKTC, JPanel mhXemDanhSachPDMVX)
    {
        super(onClose);
        JTabbedPane tab = new JTabbedPane(JTabbedPane.LEFT);
        tab.add("Đăng ký tiêm chủng", new MHPhieuDKTC(mhXemDanhSachPDKTC, () -> tab.setSelectedIndex(1)));
        tab.add("Đặt mua vắc xin", new MHPhieuDMVX(mhXemDanhSachPDMVX));
        getContentPane().add(tab);
    }
}
