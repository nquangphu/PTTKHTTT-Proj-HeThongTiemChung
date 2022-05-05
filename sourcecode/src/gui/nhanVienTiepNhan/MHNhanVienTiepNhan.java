package gui.nhanVienTiepNhan;

import controllers.XuLyDangKyTiemChung;
import controllers.XuLyDangNhap;
import controllers.XuLyDatMuaVacXin;
import gui.MHNguoiDung;
import gui.khachHang.MHXemDanhSachPDKTC;
import gui.khachHang.MHXemDanhSachPDMVX;

import javax.swing.*;

/**
 * PACKAGE_NAME
 * Created by NhatLinh - 19127652
 * Date 4/30/2022 - 12:42 AM
 * Description: ...
 */
public class MHNhanVienTiepNhan extends MHNguoiDung {

    public MHNhanVienTiepNhan(Runnable onClose) {
        super(onClose);
        setTitle("Nhân viên tiếp nhận: " + XuLyDangNhap.layId());
        JTabbedPane mh = new JTabbedPane(JTabbedPane.LEFT);
        mh.add("Duyệt phiếu đăng ký tiêm chủng", new MHXemDanhSachPDKTC(new MHChiTietPDKTCNhanVien(), XuLyDangKyTiemChung::layPhieuDKTCNV));
        mh.add("Thêm phiếu đặt mua vào danh sách đặt mua", new MHXemDanhSachPDMVX(new MHChiTietPDMVXNhanVien(), XuLyDatMuaVacXin::layPhieuDMVXNV));
        getContentPane().add(mh);
    }
}
