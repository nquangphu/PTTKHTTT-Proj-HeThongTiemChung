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
 * Date 4/29/2022 - 5:18 PM
 * Description: ...
 */
public class PhieuDMVXDB {

    public static int taoPhieuDMVX(PhieuDMVXDto pdmvx)
    {
        try {
            try(Connection conn = DBHandler.getInstance().getConnection();
                SQLServerCallableStatement statement = (SQLServerCallableStatement) conn.prepareCall("{CALL dbo.tao_pdmvx(?, ?, ?, ?, ?)}"))
            {
                int maPDMVX = 0;
                SQLServerDataTable gtcs = new SQLServerDataTable();
                gtcs.addColumnMetadata("id", Types.INTEGER);
                SQLServerDataTable vxs = new SQLServerDataTable();
                vxs.addColumnMetadata("id", Types.INTEGER);
                SQLServerDataTable vxKhongHT = new SQLServerDataTable();
                vxKhongHT.addColumnMetadata("TenLoaiVX", Types.NVARCHAR);
                vxKhongHT.addColumnMetadata("NhaSV", Types.NVARCHAR);
                vxKhongHT.addColumnMetadata("Gia", Types.DECIMAL);

                for (GoiTiemChungDto gtc : pdmvx.gtcs())
                    gtcs.addRow(gtc.maGoi());
                for (LoaiVacXinDto vx : pdmvx.lvxs())
                    vxs.addRow(vx.maLoaiVX());
                for (LoaiVacXinKhongTrongHeThongDto vx : pdmvx.lvxKhongHTs())
                    vxKhongHT.addRow(vx.ten(), vx.nsx(), vx.giaUocLuong());

                statement.setInt(1, pdmvx.kh().maKH());
                statement.setStructured(2, "dbo.IDS", gtcs);
                statement.setStructured(3, "dbo.IDS", vxs);
                statement.setStructured(4, "dbo.LVX_KHONG_HT", vxKhongHT);
                statement.registerOutParameter(5, Types.INTEGER, maPDMVX);
                statement.execute();
                maPDMVX = statement.getInt(5);
                return maPDMVX;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int taoPhieuDMVXKhongDK(PhieuDMVXDto pdmvx)
    {
        try {
            try(Connection conn = DBHandler.getInstance().getConnection();
                SQLServerCallableStatement statement = (SQLServerCallableStatement) conn.prepareCall("{CALL dbo.tao_pdmvx_khong_dk(?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?, ?, ?)}"))
            {
                int maPDMVX = 0;
                SQLServerDataTable gtcs = new SQLServerDataTable();
                gtcs.addColumnMetadata("id", Types.INTEGER);
                SQLServerDataTable vxs = new SQLServerDataTable();
                vxs.addColumnMetadata("id", Types.INTEGER);
                SQLServerDataTable vxKhongHT = new SQLServerDataTable();
                vxKhongHT.addColumnMetadata("TenLoaiVX", Types.NVARCHAR);
                vxKhongHT.addColumnMetadata("NhaSV", Types.NVARCHAR);
                vxKhongHT.addColumnMetadata("Gia", Types.DECIMAL);

                for (GoiTiemChungDto gtc : pdmvx.gtcs())
                    gtcs.addRow(gtc.maGoi());
                for (LoaiVacXinDto vx : pdmvx.lvxs())
                    vxs.addRow(vx.maLoaiVX());
                for (LoaiVacXinKhongTrongHeThongDto vx : pdmvx.lvxKhongHTs())
                    vxKhongHT.addRow(vx.ten(), vx.nsx(), vx.giaUocLuong());

                KhachHangDto kh = pdmvx.kh();
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
                statement.setStructured(10, "dbo.IDS", gtcs);
                statement.setStructured(11, "dbo.IDS", vxs);
                statement.setStructured(12, "dbo.LVX_KHONG_HT", vxKhongHT);
                statement.registerOutParameter(13, Types.INTEGER, maPDMVX);
                statement.execute();
                maPDMVX = statement.getInt(13);
                return maPDMVX;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static List<PhieuDMVXDto> layPhieuDMVX() {
        try {
            try(Connection conn = DBHandler.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("SELECT MaPDMVX, NgayLap, TrangThai, MaKH FROM PHIEU_DMVX"))
            {
                List<PhieuDMVXDto> pdmvxs = new ArrayList<>();
                try (ResultSet result = statement.executeQuery())
                {
                    while (result.next())
                        pdmvxs.add(new PhieuDMVXDto(result.getInt(1),
                                new KhachHangDto(result.getInt(4)),
                                result.getDate(2),
                                result.getString(3)));
                }
                return pdmvxs;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    public static PhieuDMVXDto layChiTietPhieuDMVX(int maPDMVX) {
        try {
            try(Connection conn = DBHandler.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("" +
                        "SELECT MaKH, MaNV, TrangThai, NgayLap FROM PHIEU_DMVX WHERE MaPDMVX = ?");
                PreparedStatement gtc = conn.prepareStatement(
                        "SELECT gtc.MaGoi, gtc.TenGoi, gtc.GiaGoi, MIN(lvx.TonKho), gtc.MoTa " +
                                "FROM GOI_TIEM_CHUNG gtc " +
                                "JOIN CT_PHIEU_DMVX_GOI ctp ON gtc.MaGoi = ctp.MaGoi " +
                                "JOIN CT_GOI_TIEM_CHUNG ct ON gtc.MaGoi = ct.MaGoi " +
                                "JOIN LOAI_VAC_XIN lvx ON lvx.MaLoaiVX = ct.MaLoaiVX " +
                                "WHERE ctp.MaPDMVX = ? " +
                                "GROUP BY gtc.MaGoi, gtc.TenGoi, gtc.GiaGoi, gtc.MoTa");
                PreparedStatement vac = conn.prepareStatement(
                        "SELECT lvx.MaLoaiVX, lvx.TenLoai, lvx.TonKho, lvx.GiaBan, lvx.MoTa " +
                                "FROM LOAI_VAC_XIN lvx " +
                                "JOIN CT_PHIEU_DMVX_LE ctp ON ctp.MaLoaiVX = lvx.MaLoaiVX " +
                                "WHERE ctp.MaPDMVX = ?");
                PreparedStatement vacNo = conn.prepareStatement(
                        "SELECT ctp.TenLoaiVX, ctp.NhaSX, ctp.Gia " +
                                "FROM CT_PHIEU_DMVX_KHONG_TRONG_HE_THONG ctp " +
                                "WHERE ctp.MaPDMVX = ?");
                PreparedStatement nv = conn.prepareStatement(
                        "SELECT nv.HoTen " +
                                "FROM NHAN_VIEN nv " +
                                "WHERE nv.MaNV = ?");
                PreparedStatement khKhongDK = conn.prepareStatement(
                        "SELECT HoTen, NgaySinh, GioiTinh, DiaChi, SDT, Email, HoTenNGH, QuanHeNGH, SDTNGH " +
                                "FROM PHIEU_DMVX_KHONG_DK p " +
                                "WHERE p.MaPDMVX = ?"))
            {
                statement.setInt(1, maPDMVX);
                gtc.setInt(1, maPDMVX);
                vac.setInt(1, maPDMVX);
                vacNo.setInt(1, maPDMVX);
                try (ResultSet resultSet = statement.executeQuery())
                {
                    resultSet.next();
                    Object maKH = resultSet.getObject(1);
                    KhachHangDto kh = null;
                    if (maKH == null)
                    {
                        khKhongDK.setInt(1, maPDMVX);
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
                    List<GoiTiemChungDto> gotGtc = new ArrayList<>();
                    List<LoaiVacXinDto> gotVx = new ArrayList<>();
                    List<LoaiVacXinKhongTrongHeThongDto> gotVxNo = new ArrayList<>();
                    try (ResultSet gtcRes = gtc.executeQuery())
                    {
                        while (gtcRes.next())
                            gotGtc.add(new GoiTiemChungDto(gtcRes.getInt(1), null, gtcRes.getString(2), gtcRes.getFloat(3), gtcRes.getInt(4), gtcRes.getString(5)));
                    }
                    try (ResultSet vacRes = vac.executeQuery())
                    {
                        while (vacRes.next())
                            gotVx.add(new LoaiVacXinDto(vacRes.getInt(1), vacRes.getString(2), vacRes.getInt(3), vacRes.getFloat(4), vacRes.getString(5)));
                    }
                    try (ResultSet vacNoRes = vacNo.executeQuery())
                    {
                        while (vacNoRes.next())
                            gotVxNo.add(new LoaiVacXinKhongTrongHeThongDto(vacNoRes.getString(1), vacNoRes.getString(2), vacNoRes.getFloat(3)));
                    }
                    return new PhieuDMVXDto(maPDMVX, kh, nvInfor, null, null, ngayLap, trangThai, gotGtc, gotVx, gotVxNo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean chinhTrangThaiPDM(int maPDMVX, int maNV, String trangThai) {
        try
        {
            try (Connection conn = DBHandler.getInstance().getConnection();
                 PreparedStatement statement = conn.prepareStatement(
                         "UPDATE PHIEU_DMVX " +
                                 "SET TrangThai = ?, MaNV = ?, MaTT = (SELECT nv.MaTT FROM NHAN_VIEN nv WHERE nv.MaNV = ?) " +
                                 "WHERE MaPDMVX = ?"))
            {
                statement.setString(1, trangThai);
                statement.setInt(2, maNV);
                statement.setInt(3, maNV);
                statement.setInt(4, maPDMVX);
                statement.executeUpdate();
                return true;
            }
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
