package database;

import dtos.GoiTiemChungDto;
import dtos.LoaiVacXinDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * database
 * Created by NhatLinh - 19127652
 * Date 4/24/2022 - 2:58 PM
 * Description: ...
 */
public class VacXinDB {

    public static Iterable<LoaiVacXinDto> layLoaiVacXin() {

        try {
            try(Connection conn = DBHandler.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("SELECT MaLoaiVX, TenLoai, TonKho, GiaBan, MoTa FROM LOAI_VAC_XIN lvx");
                ResultSet result = statement.executeQuery())
            {
                List<LoaiVacXinDto> lvxs = new ArrayList<>();
                while (result.next())
                    lvxs.add(new LoaiVacXinDto(result.getInt(1), result.getString(2), result.getInt(3), result.getFloat(4), result.getString(5)));
                return lvxs;
            }
        } catch (SQLException e) {
            return null;
        }
    }
}
