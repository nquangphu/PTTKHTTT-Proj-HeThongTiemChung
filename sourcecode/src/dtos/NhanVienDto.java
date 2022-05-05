package dtos;

import java.util.Date;

/**
 * dtos
 * Created by NhatLinh - 19127652
 * Date 4/24/2022 - 10:31 AM
 * Description: ...
 */
public record NhanVienDto(int maNV, String hoTen, Date NgaySinh, String diaChi, String sdt, String email,
                          float luong, String loaiNV, String bangCap) {
}
