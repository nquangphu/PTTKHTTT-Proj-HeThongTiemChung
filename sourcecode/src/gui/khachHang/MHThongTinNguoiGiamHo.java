package gui.khachHang;

import dtos.NguoiGiamHoDto;

import javax.swing.*;

import static gui.khachHang.MHThongTinKhachHang.addComponent;

/**
 * gui.user
 * Created by NhatLinh - 19127652
 * Date 4/23/2022 - 11:01 PM
 * Description: ...
 */
public class MHThongTinNguoiGiamHo extends JPanel {

    private final JTextField tenField = new JTextField();
    private final JTextField mqhField = new JTextField();
    private final JTextField sdtField = new JTextField();

    public MHThongTinNguoiGiamHo()
    {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        addComponent(this, tenField, "Họ tên người giám hộ");
        addComponent(this, mqhField, "Mối quan hệ");
        addComponent(this, sdtField, "Số điện thoại");
    }

    public void ganNGH(NguoiGiamHoDto ngh)
    {
        if (ngh != null)
        {
            tenField.setText(ngh.hoTen());
            sdtField.setText(ngh.sdt());
            mqhField.setText(ngh.quanHe());
        }
        else
        {
            tenField.setText("");
            sdtField.setText("");
            mqhField.setText("");
        }
    }

    public void clear()
    {
        tenField.setText("");
        mqhField.setText("");
        sdtField.setText("");
    }

    public void setEnable(boolean enable) {
        tenField.setEditable(enable);
        mqhField.setEditable(enable);
        sdtField.setEditable(enable);
    }

    public NguoiGiamHoDto layNGH()
    {
        NguoiGiamHoDto ngh = new NguoiGiamHoDto(tenField.getText(), mqhField.getText(), sdtField.getText());
        if (ngh.anyEmpty())
            return null;
        return ngh;
    }
}
