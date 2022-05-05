package controllers;

import database.HoaDonDB;
import database.TheATMDB;
import dtos.DotThanhToanDto;
import dtos.HoaDonDto;
import dtos.PhieuDKTCDto;
import dtos.TheATMDto;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * controllers
 * Created by NhatLinh - 19127652
 * Date 4/27/2022 - 1:48 PM
 * Description: ...
 */
public class XuLyThanhToan {

    public static TheATMDto layTheATM(String ngHang, String maThe)
    {
        return TheATMDB.layTheATM(ngHang, maThe);
    }

    public static Iterable<TheATMDto> layToanBoTheATM(int maKH)
    {
        return TheATMDB.layToanBoTheATM(maKH);
    }

    public static HoaDonDto layHDTheoPDKTC(int maPDKTC)
    {
        return HoaDonDB.layChiTietHDTheoPDKTC(maPDKTC);
    }

    public static HoaDonDto layHDTheoMaHD(int maHD)
    {
        return HoaDonDB.layChiTietHDTheoMaHD(maHD);
    }

    public static boolean thanhToanToanBo(PhieuDKTCDto pdktc, TheATMDto atm)
    {
        if (thanhToanVoiNH(atm, pdktc.tongGia()))
        {
            int maHD = HoaDonDB.taoHDToanBo(pdktc, atm);
            if (maHD > 0){
                XuLyEmail.guiMail(pdktc.khachHang().email(),
                        "Thanh toán toàn bộ hóa đơn",
                        String.format(
                                """
                                Khách hàng %s đã thanh toán toàn bộ phiếu đăng ký tiêm chủng có mã %s
                                Thông tin thanh toán
                                Mã hóa đơn: %s
                                Ngày thanh toán: %s
                                Số tiền: %s
                                Thẻ thanh toán: %s
                                """,
                                pdktc.khachHang().hoTen(), pdktc.maPDKTC(), maHD, Date.from(Instant.now()).toString(), pdktc.tongGia(), atm.toString()
                        ));
                return true;
            }
        }
        return false;
    }

    public static boolean thanhToanDot(int maHD, PhieuDKTCDto pdktc, DotThanhToanDto dtt, TheATMDto atm)
    {
        if (thanhToanVoiNH(atm, dtt.soTien()))
        {
            if (HoaDonDB.thanhToanDot(maHD, dtt, atm))
            {
                XuLyEmail.guiMail(pdktc.khachHang().email(),
                        "Thông báo đợt thanh toán",
                        String.format(
                                """
                                Khách hàng %s đã thanh toán đợt %s của hóa đơn
                                Thông tin đợt: %s
                                Thông tin thanh toán
                                %s
                                """, pdktc.khachHang().hoTen(), dtt.ngayBD(), dtt.toString(), atm.toString()));
                return true;
            }
        }
        return false;
    }

    public static boolean thanhToanVoiNH(TheATMDto atm, float soTien)
    {
        return true;
    }


    public static boolean kiemTraThanhToanTheoDot(PhieuDKTCDto pdktc, StringBuilder msg)
    {
        if (pdktc.tongGia() < 10000000)
        {
            msg.append("Giá trị đơn hàng nhỏ hơn 10 triệu, không thể thanh toán theo đợt!");
            return false;
        }
        return true;
    }
    public static boolean taoHDThanhToanTheoDot(PhieuDKTCDto pdktc, Iterable<DotThanhToanDto> dotThanhToan)
    {
        int maHD = HoaDonDB.taoHDTheoDot(pdktc, dotThanhToan);
        if (maHD > 0){
            StringBuilder noiDungMail = new StringBuilder(
                    String.format("""
                                    Khách hàng %s đã tạo đợt thanh toán cho phiếu đăng ký tiêm chủng có mã là %s
                                    Thông tin đợt thanh toán
                                    Mã hóa đơn: %s
                                    Ngày lập: %s
                                    Số tiền: %s
                                    Các đợt thanh toán                                    
                                    """,
                            pdktc.khachHang().hoTen(), pdktc.maPDKTC(), maHD, Date.from(Instant.now()).toString(), pdktc.tongGia())
            );
            for (DotThanhToanDto dtt : dotThanhToan)
                noiDungMail.append(dtt.toString()).append("\n");
            XuLyEmail.guiMail(pdktc.khachHang().email(), "Tạo đợt thanh toán", noiDungMail.toString());
            return true;
        }
        return false;
    }

    public static Iterable<DotThanhToanDto> taoDotThanhToan(float soTien, Date ngayMuonTiemGanNhat, float tienMoiDot, StringBuilder msg)
    {
        try
        {
            Date now = Date.from(Instant.now());
            long diffInMillies = Math.abs(ngayMuonTiemGanNhat.getTime() - now.getTime());
            long days = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            if (days <= 0)
            {
                msg.append("Đã đến ngày tiêm, không thể thanh toán theo đợt nữa!");
                return null;
            }
            int turnCount = (int)(soTien / tienMoiDot);
            if (turnCount <= 0)
            {
                msg.append("Số tiền mỗi đợt không thể lớn hơn tổng giá tiền! Xin vui lòng xem lại thông tin!");
                return null;
            }
            float divMoney = soTien % tienMoiDot;
            long daysEachTurn = days / turnCount;
            if (divMoney > 0)
                turnCount++;
            if (daysEachTurn <= 0)
            {
                msg.append("Mỗi đợt không thể ít hơn 1 ngày! Xin vui lòng xem lại thông tin!");
                return null;
            }
            long divDay = divMoney > 0 ? days - (daysEachTurn * (turnCount - 1)) : 0;
            List<DotThanhToanDto> turns = new ArrayList<>();
            Calendar cal = Calendar.getInstance();
            cal.setTime(now);
            while (turnCount > 0)
            {
                Date startDate = cal.getTime();
                if (turnCount == 1 && divMoney > 0)
                {
                    cal.add(Calendar.DAY_OF_MONTH, (int)divDay);
                    turns.add(new DotThanhToanDto(startDate, cal.getTime(), divMoney, null, null));
                }
                else
                {
                    cal.add(Calendar.DAY_OF_MONTH, (int)daysEachTurn - 1);
                    turns.add(new DotThanhToanDto(startDate, cal.getTime(), tienMoiDot, null, null));
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                }
                turnCount--;
            }

            return turns;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
