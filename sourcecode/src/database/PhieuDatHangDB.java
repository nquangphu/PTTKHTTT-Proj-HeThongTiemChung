package database;

import com.microsoft.sqlserver.jdbc.SQLServerCallableStatement;
import dtos.PhieuDatHangDto;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * database
 * Created by NhatLinh - 19127652
 * Date 4/29/2022 - 8:45 PM
 * Description: ...
 */

public class PhieuDatHangDB {
    public static boolean taoPhieuDH(int maPDMVX, int maNV)
    {
        try
        {
            try (Connection conn = DBHandler.getInstance().getConnection();
                 PreparedStatement statement = conn.prepareStatement("" +
                         "INSERT INTO PHIEU_DAT_HANG(MaPDMVX, MaNV, MaTT) " +
                         "SELECT ?, nv.MaNV, nv.MaTT " +
                         "FROM NHAN_VIEN nv WHERE nv.MaNV = ?"))
            {
                statement.setInt(1, maPDMVX);
                statement.setInt(2, maNV);
                return statement.executeUpdate() > 0;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
