package controllers;

import database.PhieuDKTCDB;
import dtos.*;

import java.time.LocalDate;
import java.util.List;

/**
 * controllers
 * Created by NhatLinh - 19127652
 * Date 4/24/2022 - 11:01 AM
 * Description: ...
 */
public class XuLyDangKyTiemChung {

    public static boolean kiemTraThongTin(KhachHangDto khachHang, StringBuilder msg)
    {
        if (khachHang.maKH() <= 0)
        {
            if (khachHang.anyEmpty())
            {
                msg.append("Bạn cần điền đầy đủ thông tin cá nhân");
                return false;
            }
            long diffYears = LocalDate.now().getYear() - khachHang.ngaySinh().getYear() - 1900;
            if (diffYears < 18 && (khachHang.nguoiGiamHo() == null || khachHang.nguoiGiamHo().anyEmpty()))
            {
                msg.append("Cần điền thông tin người giám hộ vì bạn chưa đủ 18 tuổi!");
                return false;
            }
        }
        return true;
    }

    public static boolean duyetPDKTC(int maPDKTC)
    {
        boolean state = PhieuDKTCDB.chinhTrangThaiPDKTC(maPDKTC, XuLyDangNhap.layId(), "Đã duyệt");
        if (state)
        {
            PhieuDKTCDto pdktc = PhieuDKTCDB.layChiTietPDKTC(maPDKTC);
            XuLyEmail.guiMail(pdktc.khachHang().email(),
                        "Thông báo phiếu đăng ký tiêm chủng đã được duyệt",
                    String.format(
                            """
                            Khách hàng %s
                            Phiếu đăng ký tiêm chủng %s đã được nhân viên %s xét duyệt 
                            """,
                            pdktc.khachHang().hoTen(), pdktc.maPDKTC(), pdktc.nhanVien().hoTen())
                    );
            return true;
        }
        return false;
    }

    public static boolean tuChoiPDKTC(int maPDKTC, String lyDo)
    {
        lyDo = String.format("Từ chối (%s)", lyDo);
        boolean state = PhieuDKTCDB.chinhTrangThaiPDKTC(maPDKTC, XuLyDangNhap.layId(), lyDo);
        if (state)
        {
            PhieuDKTCDto pdktc = PhieuDKTCDB.layChiTietPDKTC(maPDKTC);
            XuLyEmail.guiMail(pdktc.khachHang().email(),
                    "Thông báo phiếu đăng ký tiêm chủng đã bị từ chối",
                    String.format(
                            """
                            Khách hàng %s
                            Phiếu đăng ký tiêm chủng %s đã bị nhân viên %s từ chối xét duyệt
                            Lý do: %s
                            """,
                            pdktc.khachHang().hoTen(), pdktc.maPDKTC(), pdktc.nhanVien().hoTen(), lyDo)
            );
            return true;
        }
        return false;
    }

    public static Iterable<PhieuDKTCDto> layPhieuDKTCKH()
    {
        List<PhieuDKTCDto> pdktcs = PhieuDKTCDB.layPhieuDKTC();
        if (pdktcs == null)
            return null;
        pdktcs.removeIf(e -> e.khachHang().maKH() != XuLyDangNhap.layId());
        return pdktcs;
    }

    public static Iterable<PhieuDKTCDto> layPhieuDKTCNV()
    {
        List<PhieuDKTCDto> pdktcs = PhieuDKTCDB.layPhieuDKTC();
        if (pdktcs == null)
            return null;
        pdktcs.removeIf(e -> !e.trangThai().equalsIgnoreCase("Chưa duyệt"));
        return pdktcs;
    }

    public static PhieuDKTCDto layChiTietPDKTC(int maPDKTC)
    {
        return PhieuDKTCDB.layChiTietPDKTC(maPDKTC);
    }

    public static boolean taoPDKTC(KhachHangDto kh, Iterable<ChiTietPhieuDKTCDto> ctps)
    {
        PhieuDKTCDto pdktc = new PhieuDKTCDto(kh, ctps);
        int maPDKTC = kh.maKH() > 0 ? PhieuDKTCDB.taoPhieuDKTC(pdktc) : PhieuDKTCDB.taoPhieuDKTCKhongDK(pdktc);
        if (maPDKTC > 0)
        {
            XuLyEmail.guiMail(kh.email(), "Thông tin đăng ký tiêm chủng", PhieuDKTCDB.layChiTietPDKTC(maPDKTC).toString());
            return true;
        }
        return false;
    }
}
