package gui.quanLy;

import controllers.XuLyDangNhap;
import controllers.XuLyDatMuaVacXin;
import gui.MHNguoiDung;
import gui.khachHang.MHXemDanhSachPDMVX;

import javax.swing.*;

/**
 * gui
 * Created by NhatLinh - 19127652
 * Date 4/30/2022 - 12:46 AM
 * Description: ...
 */
public class MHQuanLy extends MHNguoiDung {
    public MHQuanLy(Runnable onClose) {
        super(onClose);
        JTabbedPane tab = new JTabbedPane(JTabbedPane.LEFT);
        setTitle("Quản lý: " + XuLyDangNhap.layId());
        tab.add("Duyệt danh sách đặt mua vắc xin",
                new MHXemDanhSachPDMVX(
                        new MHChiTietPDMVXQuanLy(), XuLyDatMuaVacXin::layPhieuDMVXQL));
        getContentPane().add(tab);
    }
}
