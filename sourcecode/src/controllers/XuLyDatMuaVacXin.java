package controllers;

import database.PhieuDMVXDB;
import database.PhieuDatHangDB;
import dtos.*;

import java.util.List;

/**
 * controllers
 * Created by NhatLinh - 19127652
 * Date 4/29/2022 - 5:05 PM
 * Description: ...
 */
public class XuLyDatMuaVacXin {

    public static boolean datMuaVacXin(KhachHangDto kh, Iterable<GoiTiemChungDto> gtcs, Iterable<LoaiVacXinDto> lvxs, Iterable<LoaiVacXinKhongTrongHeThongDto> lvxKhongHTs)
    {

        PhieuDMVXDto pdmvx = new PhieuDMVXDto(kh, gtcs, lvxs, lvxKhongHTs);
        int maPDMVX = kh.maKH() > 0 ? PhieuDMVXDB.taoPhieuDMVX(pdmvx) : PhieuDMVXDB.taoPhieuDMVXKhongDK(pdmvx);
        if (maPDMVX > 0)
        {
            XuLyEmail.guiMail(kh.email(), "Thông tin đặt mua vắc xin", PhieuDMVXDB.layChiTietPhieuDMVX(maPDMVX).toString());
            return true;
        }
        return false;
    }

    public static Iterable<PhieuDMVXDto> layPhieuDMVXKH()
    {
        List<PhieuDMVXDto> pdmvxs = PhieuDMVXDB.layPhieuDMVX();
        if (pdmvxs == null)
            return null;
        pdmvxs.removeIf(e -> e.kh().maKH() != XuLyDangNhap.layId());
        return pdmvxs;
    }

    public static Iterable<PhieuDMVXDto> layPhieuDMVXNV()
    {
        List<PhieuDMVXDto> pdmvxs = PhieuDMVXDB.layPhieuDMVX();
        if (pdmvxs == null)
            return null;
        pdmvxs.removeIf(e -> !e.trangThai().equalsIgnoreCase("Chưa đặt"));
        return pdmvxs;
    }

    public static Iterable<PhieuDMVXDto> layPhieuDMVXQL()
    {
        List<PhieuDMVXDto> pdmvxs = PhieuDMVXDB.layPhieuDMVX();
        if (pdmvxs == null)
            return null;
        pdmvxs.removeIf(e -> !e.trangThai().equalsIgnoreCase("Chưa duyệt"));
        return pdmvxs;
    }

    public static PhieuDMVXDto layChiTietPhieuDMVX(int maPDMVX)
    {
        return PhieuDMVXDB.layChiTietPhieuDMVX(maPDMVX);
    }

    public static boolean kiemTraThongTin(KhachHangDto khachHang, StringBuilder msg)
    {
        return XuLyDangKyTiemChung.kiemTraThongTin(khachHang, msg);
    }

    public static boolean duaPDMVaoDanhSachDatMua(int maPDMVX)
    {
        return PhieuDMVXDB.chinhTrangThaiPDM(maPDMVX, XuLyDangNhap.layId(), "Chưa duyệt");
    }

    public static boolean tuChoiPDMVX(int maPDMVX, String lyDo)
    {
        lyDo = String.format("Từ chối (%s)", lyDo);
        boolean state = PhieuDMVXDB.chinhTrangThaiPDM(maPDMVX, XuLyDangNhap.layId(), lyDo);
        if (state)
        {
            PhieuDMVXDto phieuDMVX = PhieuDMVXDB.layChiTietPhieuDMVX(maPDMVX);
            XuLyEmail.guiMail(phieuDMVX.kh().email(),
                    "Thông báo phiếu đặt mua vắc xin đã được duyệt",
                    String.format(
                            """
                            Khách hàng %s
                            Phiếu đặt mua vắc xin %s đã bị nhân viên %s từ chối
                            Lý do: %s
                            """,
                            phieuDMVX.kh().hoTen(), phieuDMVX.maPDMVX(), phieuDMVX.nv().hoTen(), lyDo)
            );
            return true;
        }
        return false;
    }

    public static boolean duyetPDMVX(int maPDMVX)
    {
        boolean state = PhieuDMVXDB.chinhTrangThaiPDM(maPDMVX, XuLyDangNhap.layId(), "Đã duyệt") && PhieuDatHangDB.taoPhieuDH(maPDMVX, XuLyDangNhap.layId());
        if (state)
        {
            PhieuDMVXDto phieuDMVX = PhieuDMVXDB.layChiTietPhieuDMVX(maPDMVX);
            XuLyEmail.guiMail(phieuDMVX.kh().email(),
                    "Thông báo phiếu đặt mua vắc xin đã được duyệt",
                    String.format(
                            """
                            Khách hàng %s
                            Phiếu đặt mua vắc xin %s đã được quản lý %s xét duyệt 
                            """,
                            phieuDMVX.kh().hoTen(), phieuDMVX.maPDMVX(), phieuDMVX.nv().hoTen())
            );
            return true;
        }
        return false;
    }
}
