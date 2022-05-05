package gui.khachHang;

import gui.khachHang.MHChiTietPhieu;

import javax.swing.*;
import java.awt.*;

/**
 * gui.register
 * Created by NhatLinh - 19127652
 * Date 4/30/2022 - 11:21 AM
 * Description: ...
 */
public abstract class MHXemDanhSachKhongDangNhap extends JPanel {

    protected final JTextField inputField = new JTextField();
    private final JButton loadBtn = new JButton("Lấy thông tin");
    protected final MHChiTietPhieu chiTietPhieu;

    public MHXemDanhSachKhongDangNhap(MHChiTietPhieu chiTietPhieu)
    {
        super(new BorderLayout());
        this.chiTietPhieu = chiTietPhieu;
        JPanel searchSection = new JPanel(new BorderLayout());
        searchSection.add(inputField, BorderLayout.CENTER);
        searchSection.add(loadBtn, BorderLayout.LINE_END);
        loadBtn.addActionListener(e -> layPhieu());
        searchSection.setBorder(BorderFactory.createTitledBorder("Tìm phiếu theo mã"));
        add(searchSection, BorderLayout.PAGE_START);
        chiTietPhieu.setVisible(false);
        add(new JScrollPane(chiTietPhieu), BorderLayout.CENTER);
    }

    public abstract void layPhieu();
}
