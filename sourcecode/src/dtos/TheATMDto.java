package dtos;

/**
 * dtos
 * Created by NhatLinh - 19127652
 * Date 4/24/2022 - 11:53 PM
 * Description: ...
 */
public record TheATMDto(String nganHang, String chuThe, String soThe, String pin) {

    public TheATMDto(String nganHang, String chuThe, String soThe)
    {
        this(nganHang, chuThe, soThe, null);
    }
    public boolean anyEmpty()
    {
        return nganHang.isBlank() || chuThe.isBlank() || soThe.isBlank() || (pin != null && pin.isBlank());
    }

    @Override
    public String toString()
    {
        return String.format("%s - %s - %s", nganHang, chuThe, soThe);
    }
}
