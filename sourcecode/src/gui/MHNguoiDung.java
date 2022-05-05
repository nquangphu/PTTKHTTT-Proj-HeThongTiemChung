package gui;

import javax.swing.*;
import java.awt.*;

/**
 * gui
 * Created by NhatLinh - 19127652
 * Date 4/30/2022 - 12:37 AM
 * Description: ...
 */
public class MHNguoiDung extends JFrame {

    private final Runnable onClose;

    public MHNguoiDung(Runnable onClose)
    {
        this.onClose = onClose;
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(500, 300));
        setLocationRelativeTo(null);

        setVisible(true);
        pack();
    }

    public void dispose()
    {
        onClose.run();
        super.dispose();
    }
}
