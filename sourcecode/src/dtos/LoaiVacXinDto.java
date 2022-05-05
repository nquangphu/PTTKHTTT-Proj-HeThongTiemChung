package dtos;

/**
 * dtos
 * Created by NhatLinh - 19127652
 * Date 4/24/2022 - 10:29 AM
 * Description: ...
 */
public record LoaiVacXinDto(int maLoaiVX, String tenLoai, int tonKho, float giaBan, String moTa) {

    public LoaiVacXinDto(int maLoaiVX)
    {
        this(maLoaiVX, null, 0, 0, null);
    }
}
