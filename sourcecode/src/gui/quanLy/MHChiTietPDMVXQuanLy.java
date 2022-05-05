package gui.quanLy;

import dtos.PhieuDMVXDto;
import gui.khachHang.MHChiTietPDMVX;

import java.awt.*;

/**
 * gui
 * Created by NhatLinh - 19127652
 * Date 4/30/2022 - 6:44 PM
 * Description: ...
 */
public class MHChiTietPDMVXQuanLy extends MHChiTietPDMVX {

    private final MHDuyetPhieuDMVXQuanLy mhDuyetPhieu = new MHDuyetPhieuDMVXQuanLy();
    public MHChiTietPDMVXQuanLy()
    {
        super();
        add(mhDuyetPhieu, BorderLayout.PAGE_END);
    }

    public void setPDMVX(PhieuDMVXDto pdmvx)
    {
        super.setPDMVX(pdmvx);
        mhDuyetPhieu.setID(pdmvx.maPDMVX());
        mhDuyetPhieu.setVisible(pdmvx.trangThai().equals("Chưa duyệt"));
    }
}
