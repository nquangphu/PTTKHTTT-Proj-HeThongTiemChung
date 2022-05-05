package dtos;

import java.util.Date;

/**
 * dtos
 * Created by NhatLinh - 19127652
 * Date 4/24/2022 - 11:34 PM
 * Description: ...
 */
public record DotThanhToanDto(Date ngayBD, Date ngayKT, float soTien, String trangThai, TheATMDto atm) {

    @Override
    public String toString()
    {
        return String.format("%s -> %s: %s", ngayBD.toString(), ngayKT.toString(), soTien);
    }
}
