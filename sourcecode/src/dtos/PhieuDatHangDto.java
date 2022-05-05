package dtos;

import java.util.Date;

/**
 * dtos
 * Created by NhatLinh - 19127652
 * Date 4/29/2022 - 8:45 PM
 * Description: ...
 */
public record PhieuDatHangDto(int maPDH, PhieuDMVXDto pdmvx, Date ngayLap, String trangThai) {
}
