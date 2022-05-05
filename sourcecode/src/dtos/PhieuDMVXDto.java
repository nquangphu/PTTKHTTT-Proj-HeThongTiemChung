package dtos;

/**
 * dtos
 * Created by NhatLinh - 19127652
 * Date 4/29/2022 - 5:07 PM
 * Description: ...
 */

import java.util.Date;
public record PhieuDMVXDto (int maPDMVX, KhachHangDto kh, NhanVienDto nv, TrungTamDto tt,
                            PhieuDKTCDto pdktc, Date ngayLap, String trangThai,
                            Iterable<GoiTiemChungDto> gtcs,
                            Iterable<LoaiVacXinDto> lvxs,
                            Iterable<LoaiVacXinKhongTrongHeThongDto> lvxKhongHTs)
{

    public PhieuDMVXDto(KhachHangDto kh, Iterable<GoiTiemChungDto> gtcs,
                        Iterable<LoaiVacXinDto> lvxs,
                        Iterable<LoaiVacXinKhongTrongHeThongDto> lvxKhongHTs)
    {
        this (0, kh, null, null, null, null, null, gtcs, lvxs, lvxKhongHTs);
    }

    public PhieuDMVXDto(int maPDMVX, KhachHangDto kh, Date ngayLap, String trangThai)
    {
        this (maPDMVX, kh, null, null, null, ngayLap, trangThai, null, null, null);
    }

    public PhieuDMVXDto(int maPDMVX)
    {
        this (maPDMVX, null, null, null, null, null, null, null, null, null);
    }
    @Override
    public String toString()
    {
        StringBuilder msg = new StringBuilder();
        msg.append(String.format(
                """
                Khách hàng %s đã đặt mua vắc xin, mã phiếu đặt mua là %s
                Địa chỉ %s
                Ngày đặt: %s
                Chi tiết phiếu đặt mua
                """,
                kh.hoTen(), maPDMVX, kh.diaChi(), ngayLap.toString()
        ));
        for (GoiTiemChungDto gtc : gtcs)
            msg.append(String.format("Gói tiêm: %s (%s)\n", gtc.tenGoi(), gtc.giaGoi()));
        for (LoaiVacXinDto lvx : lvxs)
            msg.append(String.format("Vắc xin: %s (%s)\n", lvx.tenLoai(), lvx.giaBan()));
        for (LoaiVacXinKhongTrongHeThongDto lvx : lvxKhongHTs)
            msg.append(String.format("Vắc xin tự điền: %s (%s) - %s\n", lvx.ten(), lvx.nsx(), lvx.giaUocLuong()));
        return msg.toString();
    }
}


