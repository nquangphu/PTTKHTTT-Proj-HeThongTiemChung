package gui.nhanVienTiepNhan;

import javax.swing.*;

import static gui.khachHang.MHThongTinKhachHang.addComponent;

/**
 * gui.register
 * Created by NhatLinh - 19127652
 * Date 4/28/2022 - 1:11 AM
 * Description: ...
 */
public abstract class MHDuyetPhieu extends JPanel {

    private final JButton chapNhanBtn = new JButton("Chấp nhận phiếu");
    private final JButton tuChoiBtn = new JButton("Từ chối phiếu");
    protected final JTextField lyDoTuChoiField = new JTextField();
    private final JButton xacNhanTuChoiBtn = new JButton("Xác nhận từ chối phiếu");
    protected final JPanel notApproved = new JPanel();
    protected int maPhieu;
    protected final JPanel self;

    public MHDuyetPhieu()
    {
        super();
        self = this;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        notApproved.setVisible(false);
        JPanel buttons = new JPanel();
        chapNhanBtn.addActionListener(e -> {
            Object[] options = {"Xác nhận",
                    "Hủy bỏ"};
            int n = JOptionPane.showOptionDialog(this,
                    "Bạn thật sự muốn duyệt thông tin này?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
            if (n == 0)
                xacNhan();
        });
        xacNhanTuChoiBtn.addActionListener(e -> tuChoi());
        buttons.add(chapNhanBtn);
        buttons.add(tuChoiBtn);
        tuChoiBtn.addActionListener(e -> notApproved.setVisible(true));
        addComponent(this, buttons);
        notApproved.setLayout(new BoxLayout(notApproved, BoxLayout.PAGE_AXIS));
        addComponent(notApproved, lyDoTuChoiField, "Lý do từ chối");
        addComponent(notApproved, xacNhanTuChoiBtn);
        addComponent(this, notApproved);
    }

    protected abstract void xacNhan();
    protected abstract void tuChoi();
    public void setID(int id)
    {
        this.maPhieu = id;
    }
}
