package database;

import com.microsoft.sqlserver.jdbc.SQLServerCallableStatement;
import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import dtos.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * database
 * Created by NhatLinh - 19127652
 * Date 4/27/2022 - 2:42 PM
 * Description: ...
 */
public class HoaDonDB {

    public static int taoHDToanBo(PhieuDKTCDto pdktc, TheATMDto atm)
    {
        try
        {
            try (Connection conn = DBHandler.getInstance().getConnection();
                 SQLServerCallableStatement statement = (SQLServerCallableStatement)conn.prepareCall("{CALL tao_hd_toan_bo(?,?,?,?,?,?)}"))
            {
                int maHD = 0;
                statement.setInt(1, pdktc.maPDKTC());
                statement.setFloat(2, pdktc.tongGia());
                statement.setString(3, atm.nganHang());
                statement.setString(4, atm.soThe());
                statement.setString(5, atm.chuThe());
                statement.registerOutParameter(6, Types.INTEGER, maHD);
                statement.execute();
                maHD = statement.getInt(6);
                return maHD;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    public static int taoHDTheoDot(PhieuDKTCDto pdktc, Iterable<DotThanhToanDto> dotThanhToan) {
        try
        {
            try (Connection conn = DBHandler.getInstance().getConnection();
                 SQLServerCallableStatement statement = (SQLServerCallableStatement)conn.prepareCall("{CALL tao_hd_theo_dot(?,?,?,?)}"))
            {
                int maHD = 0;
                SQLServerDataTable dttAdd = new SQLServerDataTable();
                dttAdd.addColumnMetadata("NgayBD", Types.DATE);
                dttAdd.addColumnMetadata("NgayKT", Types.DATE);
                dttAdd.addColumnMetadata("SoTien", Types.DECIMAL);
                for (DotThanhToanDto dtt : dotThanhToan)
                    dttAdd.addRow(new Date(dtt.ngayBD().getTime()), new Date(dtt.ngayKT().getTime()), dtt.soTien());
                statement.setInt(1, pdktc.maPDKTC());
                statement.setFloat(2, pdktc.tongGia());
                statement.setStructured(3, "dbo.tao_dot_thanh_toan_type", dttAdd);
                statement.registerOutParameter(4, Types.INTEGER, maHD);
                statement.execute();
                maHD = statement.getInt(4);
                return maHD;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    public static boolean thanhToanDot(int maHD, DotThanhToanDto dtt, TheATMDto atm) {
        try
        {
            try (Connection conn = DBHandler.getInstance().getConnection();
                 SQLServerCallableStatement statement = (SQLServerCallableStatement)conn.prepareCall("{CALL thanh_toan_dot(?,?,?,?,?)}"))
            {
                statement.setInt(1, maHD);
                statement.setDate(2, new Date(dtt.ngayBD().getTime()));
                statement.setString(3, atm.nganHang());
                statement.setString(4, atm.soThe());
                statement.setString(5, atm.chuThe());
                statement.execute();
                return true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static HoaDonDto layChiTietHDTheoPDKTC(int maPDKTC) {
        try {
            try(Connection conn = DBHandler.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement(
                        "SELECT hd.MaHD, hd.NgayLap, hd.TongTien, hd.TrangThai, atm.NganHang, atm.ChuThe, atm.SoThe " +
                                "FROM HOA_DON hd " +
                                "LEFT JOIN THE_ATM atm ON hd.NganHang = atm.NganHang AND hd.SoThe = atm.SoThe " +
                                "WHERE hd.MaPDKTC = ?");
                PreparedStatement turns = conn.prepareStatement(
                        "SELECT dtt.NgayBD, dtt.NgayKT, dtt.SoTien, dtt.TrangThai, atm.NganHang, atm.ChuThe, atm.SoThe " +
                                "FROM DOT_THANH_TOAN dtt " +
                                "LEFT JOIN THE_ATM atm ON dtt.NganHang = atm.NganHang AND dtt.SoThe = atm.SoThe " +
                                "WHERE dtt.MaHD = ?"))
            {
                statement.setInt(1, maPDKTC);
                List<TheATMDto> atms = new ArrayList<>();
                try (ResultSet set = statement.executeQuery())
                {
                    if (set.next())
                    {
                        int maHD = set.getInt(1);
                        Date ngLap = set.getDate(2);
                        float tongTien = set.getFloat(3);
                        String trangThai = set.getString(4);
                        Object ngHang = set.getObject(5);
                        TheATMDto atm = null;
                        if (ngHang != null)
                            atm = new TheATMDto((String)ngHang, set.getString(6), set.getString(7));

                        List<DotThanhToanDto> dotThanhToan = new ArrayList<>();
                        turns.setInt(1, maHD);
                        try (ResultSet dttSet = turns.executeQuery()) {
                            while (dttSet.next())
                            {
                                TheATMDto atmDto = null;
                                ngHang = dttSet.getObject(5);
                                if (ngHang != null)
                                    atmDto = new TheATMDto(dttSet.getString(5), dttSet.getString(6), dttSet.getString(7));
                                dotThanhToan.add(new DotThanhToanDto(dttSet.getDate(1),
                                        dttSet.getDate(2),
                                        dttSet.getFloat(3),
                                        dttSet.getString(4),
                                        atmDto));
                            }
                        }

                        return new HoaDonDto(maHD, ngLap, tongTien, trangThai, dotThanhToan.isEmpty() ? null : dotThanhToan, atm);
                    }
                }
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    public static HoaDonDto layChiTietHDTheoMaHD(int maHD) {
        try {
            try(Connection conn = DBHandler.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement(
                        "SELECT hd.MaHD, hd.NgayLap, hd.TongTien, hd.TrangThai, atm.NganHang, atm.ChuThe, atm.SoThe " +
                                "FROM HOA_DON hd " +
                                "LEFT JOIN THE_ATM atm ON hd.NganHang = atm.NganHang AND hd.SoThe = atm.SoThe " +
                                "WHERE hd.MaHD = ?");
                PreparedStatement turns = conn.prepareStatement(
                        "SELECT dtt.NgayBD, dtt.NgayKT, dtt.SoTien, dtt.TrangThai, atm.NganHang, atm.ChuThe, atm.SoThe " +
                                "FROM DOT_THANH_TOAN dtt " +
                                "LEFT JOIN THE_ATM atm ON dtt.NganHang = atm.NganHang AND dtt.SoThe = atm.SoThe " +
                                "WHERE dtt.MaHD = ?"))
            {
                statement.setInt(1, maHD);
                try (ResultSet set = statement.executeQuery())
                {
                    if (set.next())
                    {
                        Date ngLap = set.getDate(2);
                        float tongTien = set.getFloat(3);
                        String trangThai = set.getString(4);
                        Object ngHang = set.getObject(5);
                        TheATMDto atm = null;
                        if (ngHang != null)
                            atm = new TheATMDto((String)ngHang, set.getString(6), set.getString(7));

                        List<DotThanhToanDto> dotThanhToan = new ArrayList<>();
                        turns.setInt(1, maHD);
                        try (ResultSet dttSet = turns.getResultSet()) {
                            while (dttSet.next())
                                dotThanhToan.add(new DotThanhToanDto(dttSet.getDate(1),
                                        dttSet.getDate(2),
                                        dttSet.getFloat(3),
                                        dttSet.getString(4),
                                        new TheATMDto(dttSet.getString(5), dttSet.getString(6), dttSet.getString(7))));
                        }

                        return new HoaDonDto(maHD, ngLap, tongTien, trangThai, dotThanhToan.isEmpty() ? null : dotThanhToan, atm);
                    }
                }
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }
}
