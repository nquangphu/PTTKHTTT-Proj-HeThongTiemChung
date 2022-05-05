package dtos;

/**
 * dtos
 * Created by NhatLinh - 19127652
 * Date 4/24/2022 - 10:36 AM
 * Description: ...
 */

import java.util.Date;

public record PhieuDKTCDto (int maPDKTC, KhachHangDto khachHang, NhanVienDto nhanVien, TrungTamDto trungTam, String trangThai,
                           Date ngayTao,
                           float tongGia,
                           Iterable<ChiTietPhieuDKTCDto> chiTietPhieu)
{
    public PhieuDKTCDto(KhachHangDto kh, Iterable<ChiTietPhieuDKTCDto> ctps)
    {
        this(0, kh, null, null, null, null, 0, ctps);
    }


    public PhieuDKTCDto(int maPDKTC)
    {
        this (maPDKTC, null, null, null, null, null, 0F, null);
    }

    public Date layNgayMuonTiemGanNhat()
    {
        Date min = new Date(2500, 1, 1);
        for (ChiTietPhieuDKTCDto ctp : chiTietPhieu)
        {
            if (ctp.ngayMuonTiem().before(min))
                min = ctp.ngayMuonTiem();
        }
        return new Date(min.getTime());
    }
    @Override
    public String toString()
    {
        StringBuilder str = new StringBuilder(String.format(
                "Mã phiếu đăng ký: %s\n" +
                        "Khách hàng: %s\n" +
                        "Địa chỉ: %s\n" +
                        "Ngày lập: %s\n" +
                        "Tổng số tiền: %s\n",
                maPDKTC, khachHang.hoTen(), khachHang.diaChi(), ngayTao.toString(), tongGia
        ));
        for (ChiTietPhieuDKTCDto ctp : chiTietPhieu)
            str.append(ctp.toString()).append('\n');
        return str.toString();
    }
}
