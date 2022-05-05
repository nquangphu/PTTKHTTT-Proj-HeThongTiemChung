package gui.quanLy;

import controllers.XuLyDatMuaVacXin;
import gui.nhanVienTiepNhan.MHDuyetPhieu;

import javax.swing.*;

/**
 * gui.register
 * Created by NhatLinh - 19127652
 * Date 4/29/2022 - 8:32 PM
 * Description: ...
 */
public class MHDuyetPhieuDMVXQuanLy extends MHDuyetPhieu {

    public MHDuyetPhieuDMVXQuanLy()
    {
        super();
    }

    @Override
    protected void xacNhan() {
        if (XuLyDatMuaVacXin.duyetPDMVX(maPhieu))
        {
            JOptionPane.showMessageDialog(this, "Duyệt phiếu đặt mua vắc xin thành công!");
            self.setVisible(false);
        }
    }

    @Override
    protected void tuChoi() {
        if (lyDoTuChoiField.getText().isBlank())
        {
            JOptionPane.showMessageDialog(this, "Cần điền lý do từ chối phiếu!");
            return;
        }
        if (XuLyDatMuaVacXin.tuChoiPDMVX(maPhieu, lyDoTuChoiField.getText()))
        {
            JOptionPane.showMessageDialog(this, "Đã từ chối phiếu đặt mua vắc xin!");
            self.setVisible(false);
        }
    }
}
