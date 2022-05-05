package gui.khachHang;

import controllers.XuLyDangKyTiemChung;
import controllers.XuLyDangNhap;
import controllers.XuLyDatMuaVacXin;

/**
 * gui
 * Created by NhatLinh - 19127652
 * Date 4/30/2022 - 11:45 AM
 * Description: ...
 */
public class MHKhachHangDaDangNhap extends MHKhachHang {
    public MHKhachHangDaDangNhap(Runnable onClose) {
        super(onClose, new MHXemDanhSachPDKTC(new MHChiTietPDKTCKhachHang(), XuLyDangKyTiemChung::layPhieuDKTCKH),
                new MHXemDanhSachPDMVX(new MHChiTietPDMVX(), XuLyDatMuaVacXin::layPhieuDMVXKH));
        setTitle("Khách hàng: " + XuLyDangNhap.layId());
    }
}
