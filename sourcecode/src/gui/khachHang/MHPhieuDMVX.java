package gui.khachHang;

import gui.khachHang.MHDatMuaVacXin;

import javax.swing.*;

/**
 * gui.register
 * Created by NhatLinh - 19127652
 * Date 4/30/2022 - 12:39 AM
 * Description: ...
 */
public class MHPhieuDMVX extends JTabbedPane {

    public MHPhieuDMVX(JPanel mhXemDanhSach)
    {
        super(JTabbedPane.TOP);
        add("Đặt mua vắc xin", new MHDatMuaVacXin());
        add("Xem danh sách đặt mua vắc xin", mhXemDanhSach);
    }
}