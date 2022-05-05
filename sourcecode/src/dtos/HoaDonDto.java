package dtos;

import java.util.Date;

/**
 * dtos
 * Created by NhatLinh - 19127652
 * Date 4/24/2022 - 11:35 PM
 * Description: ...
 */

public record HoaDonDto(int maHD, Date ngayLap, float tongTien, String trangThai, Iterable<DotThanhToanDto> dotThanhToan,
                        TheATMDto atm)
{
}
