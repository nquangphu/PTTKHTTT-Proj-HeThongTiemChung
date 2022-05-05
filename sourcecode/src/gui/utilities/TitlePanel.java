package gui.utilities;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * gui.utilities
 * Created by NhatLinh - 19127652
 * Date 4/23/2022 - 11:12 PM
 * Description: ...
 */
public class TitlePanel extends JPanel {

    public TitlePanel(String title)
    {
        setBorder(BorderFactory.createTitledBorder(getBorder(), title, TitledBorder.CENTER, TitledBorder.TOP));
    }
}
