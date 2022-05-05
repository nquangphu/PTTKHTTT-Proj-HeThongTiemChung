package gui.nhanVienTiepNhan;

import dtos.PhieuDMVXDto;
import gui.khachHang.MHChiTietPDMVX;

import java.awt.*;

/**
 * gui.register
 * Created by NhatLinh - 19127652
 * Date 4/29/2022 - 7:58 PM
 * Description: ...
 */
public class MHChiTietPDMVXNhanVien extends MHChiTietPDMVX {

    private final MHDuyetPhieuDMVXNhanVien mhDuyetPhieu = new MHDuyetPhieuDMVXNhanVien();
    public MHChiTietPDMVXNhanVien()
    {
        super();
        add(mhDuyetPhieu, BorderLayout.PAGE_END);
    }

    public void setPDMVX(PhieuDMVXDto pdmvx)
    {
        super.setPDMVX(pdmvx);
        mhDuyetPhieu.setID(pdmvx.maPDMVX());
        mhDuyetPhieu.setVisible(pdmvx.trangThai().equals("Chưa đặt"));
    }
}
