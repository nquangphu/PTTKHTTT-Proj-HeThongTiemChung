package gui.utilities;

import javax.swing.*;
import java.awt.*;

/**
 * gui.utilities
 * Created by NhatLinh - 19127652
 * Date 4/23/2022 - 11:39 PM
 * Description: ...
 */
public class SearchBar extends JPanel {

    public JTextField getInputField() {
        return inputField;
    }

    public JComboBox<String> getFilter() {
        return filter;
    }

    private final JTextField inputField = new JTextField();
    private final JComboBox<String> filter;
    public SearchBar(String[] filters)
    {
        super(new BorderLayout());
        filter = new JComboBox<>(filters);
        filter.setSelectedIndex(0);
        add(inputField, BorderLayout.CENTER);
        add(filter, BorderLayout.LINE_END);
    }
}
