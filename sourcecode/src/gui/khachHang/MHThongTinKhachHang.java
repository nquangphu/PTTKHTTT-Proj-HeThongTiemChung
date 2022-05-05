package gui.khachHang;

import dtos.KhachHangDto;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

/**
 * gui.user
 * Created by NhatLinh - 19127652
 * Date 4/23/2022 - 10:26 PM
 * Description: ...
 */
public class MHThongTinKhachHang extends JPanel {

    public int getMaKH() {
        return maKH;
    }

    private int maKH;
    private final JTextField maField = new JTextField();
    private final JTextField tenField = new JTextField();
    private final UtilDateModel ngSinhModel = new UtilDateModel();
    private final  JTextField diaChiField = new JTextField();
    private final  JTextField sdtField = new JTextField();
    private final  JTextField emailField = new JTextField();

    private final  JComboBox<String> gioiTinhCbb = new JComboBox<>(new String[]{
            "Nam", "Nữ"
    });

    private final JDatePickerImpl datePicker;

    public void ganKH(KhachHangDto kh)
    {
        if (kh.maKH() > 0)
            maField.setText(String.valueOf(kh.maKH()));
        else
            maField.setText("Chưa đăng nhập");
        maKH = kh.maKH();
        tenField.setText(kh.hoTen());
        ngSinhModel.setValue(kh.ngaySinh());
        diaChiField.setText(kh.diaChi());
        sdtField.setText(kh.sdt());
        emailField.setText(kh.email());
        if (kh.gioiTinh() == 'M')
            gioiTinhCbb.setSelectedIndex(0);
        else
            gioiTinhCbb.setSelectedIndex(1);

    }

    public void clear()
    {
        tenField.setText("");
        diaChiField.setText("");
        sdtField.setText("");
        emailField.setText("");
        gioiTinhCbb.setSelectedIndex(0);
    }

    public MHThongTinKhachHang()
    {
        super();
        maField.setEditable(false);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(ngSinhModel, p);
        datePicker = new JDatePickerImpl(datePanel, new JFormattedTextField.AbstractFormatter() {
            private final String datePattern = "yyyy-MM-dd";
            private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
            @Override
            public Object stringToValue(String text) throws ParseException {
                return dateFormatter.parseObject(text);
            }
            @Override
            public String valueToString(Object value) throws ParseException {
                if (value != null) {
                    Calendar cal = (Calendar) value;
                    return dateFormatter.format(cal.getTime());
                }
                return "";
            }
        });
        datePicker.setBackground(Color.WHITE);
        addComponent(this, maField, "Mã khách hàng");
        addComponent(this, tenField, "Họ tên");
        addComponent(this, datePicker, "Ngày sinh");
        addComponent(this, diaChiField, "Địa chỉ");
        addComponent(this, sdtField, "Số điện thoại");

        addComponent(this, emailField, "Email");
        gioiTinhCbb.setBackground(Color.WHITE);
        gioiTinhCbb.setSelectedIndex(0);
        addComponent(this, gioiTinhCbb, "Giới tính");
    }

    public static void addComponent(Container container, JComponent component, String title)
    {
        component.setBorder(BorderFactory.createTitledBorder(title));
        component.setMaximumSize(new Dimension(Integer.MAX_VALUE, component.getPreferredSize().height));
        container.add(component);
    }

    public static void addComponent(Container container, JComponent component)
    {
        component.setMaximumSize(new Dimension(Integer.MAX_VALUE, component.getPreferredSize().height));
        container.add(component);
    }

    public void setEnable(boolean enable) {
        tenField.setEditable(enable);
        diaChiField.setEditable(enable);
        sdtField.setEditable(enable);
        emailField.setEditable(enable);
        gioiTinhCbb.setEnabled(enable);
        datePicker.setEnabled(enable);
        //datePicker.getJFormattedTextField().setEditable(enable);
    }

    public String layMaKH() {
        return tenField.getText();
    }

    public Date layNgSinh() {
        return ngSinhModel.getValue();
    }

    public String layDiaChi() {
        return diaChiField.getText();
    }

    public String laySDT() {
        return sdtField.getText();
    }

    public String layEmail() {
        return emailField.getText();
    }

    public char layGioiTinh() {
        return gioiTinhCbb.getSelectedIndex() == 0 ? 'M' : 'F';
    }
}
