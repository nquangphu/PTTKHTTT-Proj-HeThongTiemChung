package dtos;

import database.GoiTiemChungDB;

import java.util.Enumeration;

/**
 * dtos
 * Created by NhatLinh - 19127652
 * Date 4/24/2022 - 10:30 AM
 * Description: ...
 */
public record GoiTiemChungDto(int maGoi, Iterable<LoaiVacXinSoLuongDto> cacLoaiVacXin, String tenGoi, float giaGoi, int tonKho, String moTa) {
    public GoiTiemChungDto(int maGoi)
    {
        this(maGoi, null, null, 0, 0, null);
    }
}
