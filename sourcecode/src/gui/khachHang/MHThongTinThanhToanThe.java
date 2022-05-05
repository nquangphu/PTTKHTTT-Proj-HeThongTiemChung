package gui.khachHang;

import dtos.TheATMDto;

import javax.swing.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static gui.khachHang.MHThongTinKhachHang.addComponent;

/**
 * gui.user
 * Created by NhatLinh - 19127652
 * Date 4/24/2022 - 12:49 AM
 * Description: ...
 */
public class MHThongTinThanhToanThe extends JPanel {
    protected final JTextField bank = new JTextField();
    protected final JTextField owner = new JTextField();
    protected final JTextField number = new JTextField();

    public MHThongTinThanhToanThe()
    {
        super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        number.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar()))
                    e.consume();
            }
        });
        addComponent(this, bank, "Ngân hàng");
        addComponent(this, owner, "Chủ thẻ");
        addComponent(this, number, "Mã thẻ");
        //addComponent(this, pin, "Số");
    }

    public void setATM(TheATMDto atm)
    {
        bank.setText(atm.nganHang());
        bank.setEditable(false);
        owner.setText(atm.chuThe());
        owner.setEditable(false);
        number.setText(atm.soThe());
        number.setEditable(false);
        //addComponent(this, pin, "Số");
    }

    public TheATMDto getATM()
    {
        return new TheATMDto(bank.getText(), owner.getText(), number.getText());
    }
}
