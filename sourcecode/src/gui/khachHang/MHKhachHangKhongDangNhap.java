package gui.khachHang;

/**
 * gui.table
 * Created by NhatLinh - 19127652
 * Date 4/30/2022 - 11:42 AM
 * Description: ...
 */
public class MHKhachHangKhongDangNhap extends MHKhachHang {
    public MHKhachHangKhongDangNhap(Runnable onClose) {
        super(onClose, new MHXemDanhSachPDKTCKhongDangNhap(), new MHXemDanhSachPDMVXKhongDangNhap());
        setTitle("Khách hành chưa đăng nhập");
    }
}
