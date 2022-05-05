package controllers;

import database.KhachHangDB;
import dtos.KhachHangDto;

/**
 * controllers
 * Created by NhatLinh - 19127652
 * Date 4/24/2022 - 11:21 AM
 * Description: ...
 */
public class XuLyKhachHang {
    public static KhachHangDto layThongTinKH()
    {
        return KhachHangDB.layThongTinKH(XuLyDangNhap.layId());
    }
}
