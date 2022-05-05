import controllers.XuLyDangNhap;
import gui.khachHang.MHKhachHangDaDangNhap;
import gui.khachHang.MHKhachHangKhongDangNhap;
import gui.nhanVienTiepNhan.MHNhanVienTiepNhan;
import gui.quanLy.MHQuanLy;

import javax.swing.*;
import java.awt.*;

import static gui.khachHang.MHThongTinKhachHang.addComponent;

/**
 * PACKAGE_NAME
 * Created by NhatLinh - 19127652
 * Date 4/8/2022 - 12:44 AM
 * Description: ...
 */
public class MainFrame extends JFrame {

    private final JTextField username = new JTextField();
    private final JPasswordField password = new JPasswordField();

    public MainFrame()
    {
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 300));
        setLocationRelativeTo(null);
        setTitle("Đăng nhập");
        addComponents();
        setVisible(true);
        pack();
    }

    public void addComponents()
    {
        Container container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
        JButton loginBtn = new JButton("Đăng nhập bằng tài khoản hệ thống");
        loginBtn.addActionListener(e -> {
            String vaiTro = XuLyDangNhap.dangNhap(username.getText(), String.valueOf(password.getPassword()));
            Runnable onClose = () -> {
                XuLyDangNhap.dangXuat();
                setVisible(true);
            };
            if (vaiTro != null)
            {
                setVisible(false);
                switch (vaiTro)
                {
                    case "KH" -> new MHKhachHangDaDangNhap(onClose);
                    case "NV" -> new MHNhanVienTiepNhan(onClose);
                    case "QL" -> new MHQuanLy(onClose);
                }
            }
            else
                JOptionPane.showMessageDialog(this, "Đăng nhập thất bại! Xin vui lòng xem lại thông tin!");
        });
        JButton noLoginBtn = new JButton("Đăng nhập bằng tài khoản khách");
        noLoginBtn.addActionListener(e -> {
            Runnable onClose = () -> setVisible(true);
            setVisible(false);
            new MHKhachHangKhongDangNhap(onClose);
        });
        addComponent(container, username, "Tên đăng nhập");
        addComponent(container, password, "Mặt khẩu");
        loginBtn.setAlignmentX(CENTER_ALIGNMENT);
        addComponent(container, loginBtn);
        noLoginBtn.setAlignmentX(CENTER_ALIGNMENT);
        addComponent(container, noLoginBtn);
    }
}
