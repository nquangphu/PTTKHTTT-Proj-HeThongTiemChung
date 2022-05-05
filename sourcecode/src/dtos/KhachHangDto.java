package dtos;

import java.util.Date;

/**
 * dtos
 * Created by NhatLinh - 19127652
 * Date 4/24/2022 - 10:26 AM
 * Description: ...
 */
public record KhachHangDto(int maKH, String hoTen, Date ngaySinh, char gioiTinh, String diaChi, String sdt, String email,
                           NguoiGiamHoDto nguoiGiamHo) {

    public KhachHangDto(int maKH)
    {
        this(maKH, null, null, 'M', null, null, null, null);
    }
    public boolean anyEmpty()
    {
        return hoTen.isBlank() || ngaySinh.toString().isBlank() || diaChi.isBlank() || sdt.isBlank() || email.isBlank();
    }
}
