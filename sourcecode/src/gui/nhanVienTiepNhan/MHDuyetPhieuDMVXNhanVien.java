package gui.nhanVienTiepNhan;

import controllers.XuLyDatMuaVacXin;
import gui.nhanVienTiepNhan.MHDuyetPhieu;

import javax.swing.*;

/**
 * gui.register
 * Created by NhatLinh - 19127652
 * Date 4/29/2022 - 8:10 PM
 * Description: ...
 */
public class MHDuyetPhieuDMVXNhanVien extends MHDuyetPhieu {

    public MHDuyetPhieuDMVXNhanVien()
    {
        super();
    }

    @Override
    protected void xacNhan() {
        if (XuLyDatMuaVacXin.duaPDMVaoDanhSachDatMua(maPhieu))
        {
            JOptionPane.showMessageDialog(this, "Đưa phiếu vào danh sách đặt mua thành công!");
            self.setVisible(false);
        }
        else
            JOptionPane.showMessageDialog(this, "Đưa phiếu vào danh sách đặt mua thất bại! Xin vui lòng thử lại sau!");
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
        else
            JOptionPane.showMessageDialog(this, "Đã từ chối phiếu đặt mua thất bại! Vui lòng thử lại sau!");
    }
}
