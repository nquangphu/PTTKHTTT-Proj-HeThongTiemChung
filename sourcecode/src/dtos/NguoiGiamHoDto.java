package dtos;

/**
 * dtos
 * Created by NhatLinh - 19127652
 * Date 4/24/2022 - 10:27 AM
 * Description: ...
 */
public record NguoiGiamHoDto(String hoTen, String quanHe, String sdt) {

    public boolean anyEmpty()
    {
        return hoTen.isBlank() || quanHe.isBlank() || sdt.isBlank();
    }
}
