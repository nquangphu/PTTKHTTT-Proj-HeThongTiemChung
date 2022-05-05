package database;

import dtos.GoiTiemChungDto;
import dtos.LoaiVacXinDto;
import dtos.LoaiVacXinSoLuongDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * database
 * Created by NhatLinh - 19127652
 * Date 4/24/2022 - 11:20 AM
 * Description: ...
 */
public class GoiTiemChungDB {

    public static Iterable<GoiTiemChungDto> layGTC()
    {
        try {
            try(Connection conn = DBHandler.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("SELECT gtc.MaGoi, gtc.TenGoi, gtc.GiaGoi, MIN(lvx.TonKho), gtc.MoTa " +
                        "FROM GOI_TIEM_CHUNG gtc " +
                        "JOIN CT_GOI_TIEM_CHUNG ct ON gtc.MaGoi = ct.MaGoi " +
                        "JOIN LOAI_VAC_XIN lvx ON lvx.MaLoaiVX = ct.MaLoaiVX " +
                        "GROUP BY gtc.MaGoi, gtc.TenGoi, gtc.GiaGoi, gtc.MoTa;");
                ResultSet result = statement.executeQuery())
            {
                List<GoiTiemChungDto> gtcs = new ArrayList<>();
                while (result.next())
                    gtcs.add(new GoiTiemChungDto(result.getInt(1), null, result.getString(2), result.getFloat(3), result.getInt(4), result.getString(5)));
                return gtcs;
            }
        } catch (SQLException e) {
            return null;
        }
    }
}
