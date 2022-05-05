package dtos;

import java.time.LocalDate;
import java.util.Date;

/**
 * dtos
 * Created by NhatLinh - 19127652
 * Date 4/24/2022 - 10:33 AM
 * Description: ...
 */
public record ChiTietPhieuDKTCDto (GoiTiemChungDto goiTiemChung, LoaiVacXinDto loaiVacXin, float donGia,
                                   Date ngayMuonTiem, Date ngayTiem) {
    public ChiTietPhieuDKTCDto(GoiTiemChungDto gtc, Date ngayMuonTiem)
    {
        this(gtc, null, 0, ngayMuonTiem, null);
    }

    public ChiTietPhieuDKTCDto(LoaiVacXinDto lvx, Date ngayMuonTiem)
    {
        this(null, lvx, 0, ngayMuonTiem, null);
    }
    @Override
    public String toString()
    {
        if (goiTiemChung != null)
        {
            return String.format(
                    "Gói tiêm: %s - %s - %s",
                    goiTiemChung.tenGoi(), ngayMuonTiem.toString(), goiTiemChung.giaGoi());
        }
        else
        {
            return String.format(
                    "Vắc xin: %s - %s - %s",
                    loaiVacXin.tenLoai(), ngayMuonTiem.toString(), loaiVacXin.giaBan());
        }
    }
}
