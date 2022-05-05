package gui.nhanVienTiepNhan;

import controllers.XuLyDangKyTiemChung;

import javax.swing.*;

/**
 * gui.register
 * Created by NhatLinh - 19127652
 * Date 4/29/2022 - 8:04 PM
 * Description: ...
 */
public class MHDuyetPhieuDKTC extends MHDuyetPhieu {

    public MHDuyetPhieuDKTC()
    {
        super();
    }

    @Override
    protected void xacNhan() {
        if (XuLyDangKyTiemChung.duyetPDKTC(maPhieu))
        {
            JOptionPane.showMessageDialog(this, "Duyệt phiếu thành công!");
            self.setVisible(false);
        }
        else
            JOptionPane.showMessageDialog(this, "Duyệt phiếu thất bại! Vui lòng thử lại xem");
    }

    @Override
    protected void tuChoi() {
        if (lyDoTuChoiField.getText().isBlank())
        {
            JOptionPane.showMessageDialog(this, "Cần điền lý do từ chối phiếu!");
            return;
        }
        if (XuLyDangKyTiemChung.tuChoiPDKTC(maPhieu, lyDoTuChoiField.getText()))
        {
            JOptionPane.showMessageDialog(this, "Đã từ chối phiếu đăng ký tiêm chủng!");
            self.setVisible(false);
        }
        else
            JOptionPane.showMessageDialog(this, "Đã từ chối phiếu đăng ký tiêm chủng thất bại! Vui lòng thử lại sau!");
    }
}
