package database;

import dtos.KhachHangDto;
import dtos.LoaiVacXinDto;
import dtos.NguoiGiamHoDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * database
 * Created by NhatLinh - 19127652
 * Date 4/24/2022 - 11:22 AM
 * Description: ...
 */
public class KhachHangDB {

    public static KhachHangDto layThongTinKH(int maKH)
    {
        try {
            try (Connection conn = DBHandler.getInstance().getConnection();
                 PreparedStatement statement = conn.prepareStatement("" +
                         "SELECT kh.HoTen, kh.NgaySinh, kh.GioiTinh, kh.DiaChi, kh.SDT, kh.Email, ngh.HoTen, ngh.QuanHe, ngh.SDT " +
                         "FROM KHACH_HANG kh " +
                         "LEFT JOIN NGUOI_GIAM_HO ngh ON kh.MaKH = ngh.MaKH " +
                         "WHERE kh.MaKH = ?"))
            {
                statement.setInt(1, maKH);
                try (ResultSet set = statement.executeQuery()) {
                    if (set.next()) {

                        NguoiGiamHoDto ngh = null;
                        if (set.getObject(7) != null)
                            ngh = new NguoiGiamHoDto(set.getString(7), set.getString(8), set.getString(9));
                        return new KhachHangDto(maKH, set.getString(1), set.getDate(2),
                                set.getString(3).charAt(0), set.getString(4),
                                set.getString(5), set.getString(6), ngh);
                    }
                }
            }
            return null;
        } catch (SQLException e) {
            return null;
        }
    }
}
