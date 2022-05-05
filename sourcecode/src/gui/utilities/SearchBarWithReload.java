package gui.utilities;

import javax.swing.*;
import java.awt.*;

/**
 * gui.utilities
 * Created by NhatLinh - 19127652
 * Date 4/24/2022 - 12:11 PM
 * Description: ...
 */
public class SearchBarWithReload extends SearchBar {
    public SearchBarWithReload(String[] filters, Runnable reload) {
        super(filters);
        JButton reloadBtn = new JButton("Tải lại");
        reloadBtn.addActionListener(e -> reload.run());
        add(reloadBtn, BorderLayout.LINE_START);
    }
}
