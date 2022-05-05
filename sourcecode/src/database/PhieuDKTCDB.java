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
 * Date 4/24/2022 - 11:19 AM
 * Description: ...
 */
public class PhieuDKTCDB {

    public static int taoPhieuDKTC(PhieuDKTCDto phieuDKTC)
    {
        try {
            try(Connection conn = DBHandler.getInstance().getConnection();
                SQLServerCallableStatement statement = (SQLServerCallableStatement) conn.prepareCall("{CALL dbo.tao_pdktc(?, ?, ?, ?)}"))
            {
                int maPDKTC = 0;
                SQLServerDataTable ctpGTC = new SQLServerDataTable();
                ctpGTC.addColumnMetadata("id", Types.INTEGER);
                ctpGTC.addColumnMetadata("date", Types.DATE);
                SQLServerDataTable ctpVX = new SQLServerDataTable();
                ctpVX.addColumnMetadata("id", Types.INTEGER);
                ctpVX.addColumnMetadata("date", Types.DATE);
                for (ChiTietPhieuDKTCDto ctp : phieuDKTC.chiTietPhieu())
                {
                    java.util.Date ngTiem = ctp.ngayMuonTiem();
                    if (ctp.goiTiemChung() != null)
                        ctpGTC.addRow(ctp.goiTiemChung().maGoi(), new Date(ngTiem.getYear(), ngTiem.getMonth(), ngTiem.getDate()));
                    else
                        ctpVX.addRow(ctp.loaiVacXin().maLoaiVX(), new Date(ngTiem.getYear(), ngTiem.getMonth(), ngTiem.getDate()));
                }
                statement.setInt(1, phieuDKTC.khachHang().maKH());
                statement.setStructured(2, "dbo.ID_DATE", ctpGTC);
                statement.setStructured(3, "dbo.ID_DATE", ctpVX);
                statement.registerOutParameter(4, Types.INTEGER, maPDKTC);
                statement.execute();
                maPDKTC = statement.getInt(4);
                return maPDKTC;
            }
        } catch (SQLException e) {
            return 0;
        }
    }

    public static int taoPhieuDKTCKhongDK(PhieuDKTCDto phieuDKTC)
    {
        try {
            try(Connection conn = DBHandler.getInstance().getConnection();
                SQLServerCallableStatement statement = (SQLServerCallableStatement) conn.prepareCall("{CALL tao_pdktc_khong_dk(?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?, ?)}"))
            {
                int maPDKTC = 0;
                SQLServerDataTable ctpGTC = new SQLServerDataTable();
                ctpGTC.addColumnMetadata("id", Types.INTEGER);
                ctpGTC.addColumnMetadata("date", Types.DATE);
                SQLServerDataTable ctpVX = new SQLServerDataTable();
                ctpVX.addColumnMetadata("id", Types.INTEGER);
                ctpVX.addColumnMetadata("date", Types.DATE);
                for (ChiTietPhieuDKTCDto ctp : phieuDKTC.chiTietPhieu())
                {
                    java.util.Date ngTiem = ctp.ngayMuonTiem();
                    if (ctp.goiTiemChung() != null)
                        ctpGTC.addRow(ctp.goiTiemChung().maGoi(), new Date(ngTiem.getYear(), ngTiem.getMonth(), ngTiem.getDate()));
                    else
                        ctpVX.addRow(ctp.loaiVacXin().maLoaiVX(), new Date(ngTiem.getYear(), ngTiem.getMonth(), ngTiem.getDate()));
                }
                KhachHangDto kh = phieuDKTC.khachHang();
                statement.setString(1, kh.hoTen());
                java.util.Date ngSinh = kh.ngaySinh();
                statement.setDate(2, new Date(ngSinh.getTime()));
                statement.setString(3, String.valueOf(kh.gioiTinh()));
                statement.setString(4, kh.diaChi());
                statement.setString(5, kh.sdt());
                statement.setString(6, kh.email());
                NguoiGiamHoDto ngh = kh.nguoiGiamHo();
                if (ngh == null)
                {
                    statement.setNull(7, Types.NVARCHAR);
                    statement.setNull(8, Types.NVARCHAR);
                    statement.setNull(9, Types.NVARCHAR);
                }
                else
                {
                    statement.setString(7, ngh.hoTen());
                    statement.setString(8, ngh.quanHe());
                    statement.setString(9, ngh.sdt());
                }
                statement.setStructured(10, "dbo.ID_DATE", ctpGTC);
                statement.setStructured(11, "dbo.ID_DATE", ctpVX);
                statement.registerOutParameter(12, Types.INTEGER, maPDKTC);
                statement.execute();
                maPDKTC = statement.getInt(12);
                return maPDKTC;
            }
        } catch (SQLException e) {
            return 0;
        }
    }

    public static List<PhieuDKTCDto> layPhieuDKTC() {

        try {
            try(Connection conn = DBHandler.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("SELECT MaPDKTC, TrangThai, NgayLap, TongGia, MaKH FROM PHIEU_DKTC"))
            {
                List<PhieuDKTCDto> pdktcs = new ArrayList<>();
                try (ResultSet result = statement.executeQuery())
                {
                    while (result.next())
                        pdktcs.add(new PhieuDKTCDto(result.getInt(1), new KhachHangDto(result.getInt(5)), null, null, result.getString(2), result.getDate(3), result.getFloat(4), null));
                }
                return pdktcs;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    public static PhieuDKTCDto layChiTietPDKTC(int maPDKTC) {
        try {
            try(Connection conn = DBHandler.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("SELECT MaKH, MaNV, TrangThai, NgayLap, TongGia FROM PHIEU_DKTC WHERE MaPDKTC = ?");
                PreparedStatement gtc = conn.prepareStatement(
                        "SELECT gtc.MaGoi, gtc.TenGoi, ctp.DonGia, MIN(lvx.TonKho), gtc.MoTa, ctp.NgayMuonTiem " +
                        "FROM GOI_TIEM_CHUNG gtc " +
                        "JOIN CT_PHIEU_DKTC_GOI ctp ON gtc.MaGoi = ctp.MaGoi " +
                        "JOIN CT_GOI_TIEM_CHUNG ct ON gtc.MaGoi = ct.MaGoi " +
                        "JOIN LOAI_VAC_XIN lvx ON lvx.MaLoaiVX = ct.MaLoaiVX " +
                        "WHERE ctp.MaPDKTC = ? " +
                        "GROUP BY gtc.MaGoi, gtc.TenGoi, ctp.DonGia, gtc.MoTa, ctp.NgayMuonTiem;");
                PreparedStatement vac = conn.prepareStatement(
                        "SELECT lvx.MaLoaiVX, lvx.TenLoai, lvx.TonKho, ctp.DonGia, lvx.MoTa, ctp.NgayMuonTiem " +
                                "FROM LOAI_VAC_XIN lvx " +
                                "JOIN CT_PHIEU_DKTC_LE ctp ON ctp.MaLoaiVX = lvx.MaLoaiVX " +
                                "WHERE ctp.MaPDKTC = ?");
                PreparedStatement nv = conn.prepareStatement(
                        "SELECT nv.HoTen " +
                                "FROM NHAN_VIEN nv " +
                                "WHERE nv.MaNV = ?");
                PreparedStatement khKhongDK = conn.prepareStatement(
                        "SELECT HoTen, NgaySinh, GioiTinh, DiaChi, SDT, Email, HoTenNGH, QuanHeNGH, SDTNGH " +
                                "FROM PHIEU_DKTC_KHONG_DK p " +
                                "WHERE p.MaPDKTC = ?"))
            {
                statement.setInt(1, maPDKTC);
                gtc.setInt(1, maPDKTC);
                vac.setInt(1, maPDKTC);
                try (ResultSet resultSet = statement.executeQuery())
                {
                    resultSet.next();
                    Object maKH = resultSet.getObject(1);
                    KhachHangDto kh = null;
                    if (maKH == null)
                    {
                        khKhongDK.setInt(1, maPDKTC);
                        try (ResultSet khRes = khKhongDK.executeQuery())
                        {
                            if (khRes.next())
                            {
                                NguoiGiamHoDto ngh = null;
                                if (khRes.getObject(7) != null)
                                    ngh = new NguoiGiamHoDto(khRes.getString(7), khRes.getString(8), khRes.getString(9));
                                kh = new KhachHangDto(0, khRes.getString(1), khRes.getDate(2),
                                        khRes.getString(3).charAt(0), khRes.getString(4),
                                        khRes.getString(5), khRes.getString(6), ngh);
                            }

                        }
                    }
                    else
                        kh = KhachHangDB.layThongTinKH((Integer)maKH);

                    Object maNV = resultSet.getObject(2);
                    NhanVienDto nvInfor = null;
                    if (maNV != null)
                    {
                        nv.setInt(1, (Integer)maNV);
                        try (ResultSet nvRes = nv.executeQuery())
                        {
                            if (nvRes.next())
                                nvInfor = new NhanVienDto(0, nvRes.getString(1), null, null, null, null, 0, null, null);
                        }
                    }

                    String trangThai = resultSet.getString(3);
                    java.util.Date ngayLap = resultSet.getDate(4);
                    float tongGia = resultSet.getFloat(5);
                    List<ChiTietPhieuDKTCDto> ctps = new ArrayList<>();
                    try (ResultSet gtcRes = gtc.executeQuery())
                    {
                        while (gtcRes.next())
                            ctps.add(new ChiTietPhieuDKTCDto(new GoiTiemChungDto(gtcRes.getInt(1), null, gtcRes.getString(2), gtcRes.getFloat(3), gtcRes.getInt(4), gtcRes.getString(5)), gtcRes.getDate(6)));
                    }
                    try (ResultSet vacRes = vac.executeQuery())
                    {
                        while (vacRes.next())
                            ctps.add(new ChiTietPhieuDKTCDto(new LoaiVacXinDto(vacRes.getInt(1), vacRes.getString(2), vacRes.getInt(3), vacRes.getFloat(4), vacRes.getString(5)), vacRes.getDate(6)));
                    }


                    return new PhieuDKTCDto(maPDKTC, kh, nvInfor, null, trangThai, ngayLap, tongGia, ctps);
                }
            }
        } catch (SQLException e) {
            return null;
        }

    }

    public static boolean chinhTrangThaiPDKTC(int maPDKTC, int maNV, String trangThai) {
        try
        {
            try (Connection conn = DBHandler.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement(
                        "UPDATE PHIEU_DKTC " +
                                "SET TrangThai = ?, MaNV = ?, MaTT = (SELECT nv.MaTT FROM NHAN_VIEN nv WHERE nv.MaNV = ?) " +
                                "WHERE MaPDKTC = ?"))
            {
                statement.setString(1, trangThai);
                statement.setInt(2, maNV);
                statement.setInt(3, maNV);
                statement.setInt(4, maPDKTC);
                statement.executeUpdate();
                return true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
