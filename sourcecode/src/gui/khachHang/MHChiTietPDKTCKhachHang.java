package gui.khachHang;

import dtos.PhieuDKTCDto;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * gui.register
 * Created by NhatLinh - 19127652
 * Date 4/28/2022 - 1:12 AM
 * Description: ...
 */
public class MHChiTietPDKTCKhachHang extends MHChiTietPDKTC {

    private final MHThanhToanPDKTC mhThanhToanPDKTC = new MHThanhToanPDKTC();

    public MHChiTietPDKTCKhachHang()
    {
        mhThanhToanPDKTC.setBorder(BorderFactory.createTitledBorder(mhThanhToanPDKTC.getBorder(), "Thanh toán", TitledBorder.CENTER, TitledBorder.TOP));
        add(mhThanhToanPDKTC, BorderLayout.PAGE_END);
    }

    @Override
    public void setPDK(PhieuDKTCDto pdk)
    {
        super.setPDK(pdk);
        if (pdk.trangThai().equals("Đã duyệt") || pdk.trangThai().equals("Đang thanh toán theo đợt"))
        {
            mhThanhToanPDKTC.setPdktcHienTai(pdk);
            mhThanhToanPDKTC.tuCapNhat();
            mhThanhToanPDKTC.setVisible(true);
        }
        else
            mhThanhToanPDKTC.setVisible(false);
    }
}
