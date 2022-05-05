package gui.nhanVienTiepNhan;

import dtos.PhieuDKTCDto;
import gui.khachHang.MHChiTietPDKTC;
import gui.nhanVienTiepNhan.MHDuyetPhieu;
import gui.nhanVienTiepNhan.MHDuyetPhieuDKTC;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * gui.register
 * Created by NhatLinh - 19127652
 * Date 4/28/2022 - 1:39 AM
 * Description: ...
 */
public class MHChiTietPDKTCNhanVien extends MHChiTietPDKTC {

    private final MHDuyetPhieu mhDuyetPhieuDKTC = new MHDuyetPhieuDKTC();

    public MHChiTietPDKTCNhanVien()
    {
        super();
        mhDuyetPhieuDKTC.setBorder(BorderFactory.createTitledBorder(mhDuyetPhieuDKTC.getBorder(), "Xét duyệt", TitledBorder.CENTER, TitledBorder.TOP));
        add(mhDuyetPhieuDKTC, BorderLayout.PAGE_END);
    }

    @Override
    public void setPDK(PhieuDKTCDto pdk)
    {
        super.setPDK(pdk);
        mhDuyetPhieuDKTC.setID(pdk.maPDKTC());
        mhDuyetPhieuDKTC.setVisible(pdk.trangThai().equals("Chưa duyệt"));
    }
}
