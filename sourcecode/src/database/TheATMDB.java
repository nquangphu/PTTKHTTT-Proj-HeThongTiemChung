package database;

import dtos.LoaiVacXinDto;
import dtos.TheATMDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * database
 * Created by NhatLinh - 19127652
 * Date 4/27/2022 - 2:37 PM
 * Description: ...
 */
public class TheATMDB {

    public static TheATMDto layTheATM(String ngHang, String maThe) {
        try {
            try(Connection conn = DBHandler.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("SELECT atm.NganHang, atm.ChuThe, atm.SoThe FROM THE_ATM atm WHERE atm.NganHang = ? AND atm.SoThe = ?"))
            {
                statement.setString(1, ngHang);
                statement.setString(2, maThe);
                try (ResultSet set = statement.executeQuery())
                {
                    if (set.next())
                        return new TheATMDto(set.getString(1), set.getString(2), set.getString(3));
                }
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    public static Iterable<TheATMDto> layToanBoTheATM(int maKH) {
        try {
            try(Connection conn = DBHandler.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement(
                        "SELECT atm.NganHang, atm.ChuThe, atm.SoThe " +
                        "FROM " +
                        "THE_ATM atm " +
                        "JOIN HOA_DON hd ON atm.NganHang = hd.NganHang AND atm.SoThe = hd.SoThe " +
                        "JOIN PHIEU_DKTC pdk ON hd.MaPDKTC = pdk.MaPDKTC " +
                        "WHERE pdk.MaKH = ?"))
            {
                statement.setInt(1, maKH);
                List<TheATMDto> atms = new ArrayList<>();
                try (ResultSet set = statement.executeQuery())
                {
                    while (set.next())
                        atms.add(new TheATMDto(set.getString(1), set.getString(2), set.getString(3)));
                }
                return atms;
            }
        } catch (SQLException e) {
            return null;
        }
    }
}
