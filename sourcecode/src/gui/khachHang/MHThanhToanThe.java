package gui.khachHang;

import dtos.TheATMDto;

import javax.swing.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

import static gui.khachHang.MHThongTinKhachHang.addComponent;

/**
 * gui.user
 * Created by NhatLinh - 19127652
 * Date 4/24/2022 - 1:07 AM
 * Description: ...
 */
public class MHThanhToanThe extends MHThongTinThanhToanThe {
    private final JTextField pin = new JTextField();
    private final JButton confirm = new JButton("Xác nhận thanh toán");
    public MHThanhToanThe(Consumer<TheATMDto> paymentCallback)
    {
        super();
        addComponent(this, pin, "Mã bảo mật");
        pin.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar()))
                    e.consume();
            }
        });
        confirm.addActionListener(e -> paymentCallback.accept(getATM()));
        confirm.setAlignmentX(CENTER_ALIGNMENT);
        confirm.setMaximumSize(confirm.getPreferredSize());
        add(confirm);
    }

    public TheATMDto getATM()
    {
        return new TheATMDto(bank.getText(), owner.getText(), number.getText(), pin.getText());
    }
}
