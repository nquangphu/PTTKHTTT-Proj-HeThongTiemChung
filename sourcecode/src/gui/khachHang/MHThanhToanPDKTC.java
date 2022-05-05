package gui.khachHang;

import controllers.XuLyThanhToan;
import dtos.DotThanhToanDto;
import dtos.HoaDonDto;
import dtos.PhieuDKTCDto;
import dtos.TheATMDto;
import gui.table.ITablePanel;
import gui.table.TablePanel;
import gui.table.cells.ButtonCell;
import gui.table.cells.ICell;
import gui.table.cells.LabelCell;
import gui.table.row.NColumnsPanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static gui.khachHang.MHThongTinKhachHang.addComponent;

/**
 * gui.user
 * Created by NhatLinh - 19127652
 * Date 4/24/2022 - 12:30 AM
 * Description: ...
 */
public class MHThanhToanPDKTC extends JPanel {
    private final JComboBox<String> hinhThucTTCbb = new JComboBox<>(new String[]{
            "Thanh toán toàn bộ",
            "Thanh toán theo đợt"
    });
    private final JTextField trangThaiField = new JTextField();
    private final JTextField ngayLapField = new JTextField();
    //private final JTextField turnMoneySpinner = new JTextField();
    protected final JTextField tienMoiDotField = new JTextField();
    private float minMoney;
    private final ITablePanel dotThanhToanTable = new TablePanel(new ICell[]{
            new LabelCell("Ngày bắt đầu"),
            new LabelCell("Ngày kết thúc"),
            new LabelCell("Số tiền"),
            new LabelCell("Trạng thái"),
            new LabelCell("Thanh toán")
    });
    protected final JButton taoDotThanhToanBtn = new JButton("Xác nhận thanh toán theo đợt");
    private boolean isAll = true;
    private DotThanhToanDto dotThanhToanVuaChon;
    private HoaDonDto hdHienTai;
    private PhieuDKTCDto pdktcHienTai;
    private Iterable<DotThanhToanDto> dotThanhToan;
    private final MHThanhToanThe mhThanhToanThe = new MHThanhToanThe(this::thanhToan);
    private final MHThongTinThanhToanThe mhThongTinThanhToanThe = new MHThongTinThanhToanThe();

    public MHThanhToanPDKTC()
    {
        super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        ngayLapField.setEditable(false);
        trangThaiField.setEditable(false);
        hinhThucTTCbb.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED)
                kichHoatChoPhepThanhToan();
        });
        tienMoiDotField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar()))
                    e.consume();
            }
        });
        tienMoiDotField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                showChangedValue();
            }
            @Override
            public void insertUpdate(DocumentEvent e) {
                showChangedValue();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                showChangedValue();
            }
            private void showChangedValue(){
                StringBuilder msg = new StringBuilder();
                dotThanhToanTable.clearRows();
                float val = Float.parseFloat(tienMoiDotField.getText());
                dotThanhToan = XuLyThanhToan.taoDotThanhToan(pdktcHienTai.tongGia(), pdktcHienTai.layNgayMuonTiemGanNhat(), val, msg);
                if (dotThanhToan == null)
                    JOptionPane.showMessageDialog(hinhThucTTCbb, msg);
                else
                {
                    for (DotThanhToanDto d : dotThanhToan)
                    {
                        NColumnsPanel row = new NColumnsPanel(new ICell[]{
                                new LabelCell(d.ngayBD().toString()),
                                new LabelCell(d.ngayKT().toString()),
                                new LabelCell(String.valueOf(d.soTien())),
                                new LabelCell(""),
                                new LabelCell("")})
                        {
                            @Override
                            public String getHeader() {
                                return null;
                            }

                            @Override
                            public Component getComponent() {
                                return this;
                            }
                        };
                        dotThanhToanTable.addRow(row);
                    }
                }
                dotThanhToanTable.updateUI();
            }
        });

        addComponent(this, hinhThucTTCbb, "Phương thức thanh toán");
        addComponent(this, trangThaiField, "Trạng thái hóa đơn");
        addComponent(this, ngayLapField, "Ngày lập hóa đơn");
        addComponent(this, tienMoiDotField, "Tiền thanh toán mỗi đợt");
        dotThanhToanTable.getAddComponent().setBorder(BorderFactory.createTitledBorder("Các đợt thanh toán"));
        add(dotThanhToanTable.getAddComponent());
        addComponent(this, mhThanhToanThe, "Thẻ thanh toán");
        addComponent(this, mhThongTinThanhToanThe, "Thẻ thanh toán");
        taoDotThanhToanBtn.setAlignmentX(CENTER_ALIGNMENT);
        taoDotThanhToanBtn.addActionListener(e -> {
            if (dotThanhToan == null)
                JOptionPane.showMessageDialog(this, "Đợt thanh toán không hợp lệ! Xin vui lòng xem lại!");
            else
            {
                if (XuLyThanhToan.taoHDThanhToanTheoDot(pdktcHienTai, dotThanhToan)) {
                    JOptionPane.showMessageDialog(this, "Tạo hóa đơn thành công!");
                    tuCapNhat();
                }
                else
                    JOptionPane.showMessageDialog(this, "Tạo hóa đơn thất bại. Xin vui lòng thử lại sau!");
            }
        });
        add(taoDotThanhToanBtn);
    }

    //
    public void thanhToan(TheATMDto atm)
    {
        if (atm.anyEmpty())
        {
            JOptionPane.showMessageDialog(this, "Cần điền đầy đủ thông tin thẻ!");
            return;
        }
        if (isAll)
        {
            if (XuLyThanhToan.thanhToanToanBo(pdktcHienTai, atm))
            {
                JOptionPane.showMessageDialog(this, "Thanh toán phiếu đăng ký tiêm chủng thành công");
                tuCapNhat();
            }
            else
                JOptionPane.showMessageDialog(this, "Đã gặp lỗi trong quá trình thanh toán! Xin vui lòng thử lại!");
        }
        else
        {

            if (XuLyThanhToan.thanhToanDot(hdHienTai.maHD(), pdktcHienTai, dotThanhToanVuaChon, atm))
            {
                JOptionPane.showMessageDialog(this, "Thanh toán đợt thành công");
                tuCapNhat();
            }
            else
                JOptionPane.showMessageDialog(this, "Đã gặp lỗi trong quá trình thanh toán! Xin vui lòng thử lại!");
        }
    }

    private void kichHoatChoPhepThanhToan()
    {
        if (hdHienTai == null)
        {
            if (hinhThucTTCbb.getSelectedIndex() == 0)
            {
                isAll = true;
                tienMoiDotField.setVisible(false);
                dotThanhToanTable.setVisible(false);
                mhThanhToanThe.setVisible(true);
                taoDotThanhToanBtn.setVisible(false);
            }
            else
            {
                StringBuilder msg = new StringBuilder();
                if (!XuLyThanhToan.kiemTraThanhToanTheoDot(pdktcHienTai, msg))
                {
                    JOptionPane.showMessageDialog(this, msg);
                    hinhThucTTCbb.setSelectedIndex(0);
                    kichHoatChoPhepThanhToan();
                    return;
                }
                isAll = false;
                mhThanhToanThe.setVisible(false);
                tienMoiDotField.setVisible(true);
                dotThanhToanTable.setVisible(true);
                taoDotThanhToanBtn.setVisible(true);
            }
        }
    }

    public void thanhToan()
    {
        minMoney = pdktcHienTai.tongGia() / 4;
        tienMoiDotField.setVisible(true);
        tienMoiDotField.setText(String.valueOf((int)minMoney));
        hinhThucTTCbb.setEnabled(true);
        trangThaiField.setVisible(false);
        ngayLapField.setVisible(false);
        mhThongTinThanhToanThe.setVisible(false);
        hinhThucTTCbb.setSelectedIndex(0);
        kichHoatChoPhepThanhToan();
    }

    public void tuCapNhat()
    {
        hdHienTai = XuLyThanhToan.layHDTheoPDKTC(pdktcHienTai.maPDKTC());
        if (hdHienTai != null)
            hienThiHD();
        else
            thanhToan();
    }

    public void hienThiHD()
    {
        ngayLapField.setVisible(true);
        trangThaiField.setVisible(true);
        mhThongTinThanhToanThe.setVisible(true);
        ngayLapField.setText(hdHienTai.ngayLap().toString());
        trangThaiField.setText(hdHienTai.trangThai());
        hinhThucTTCbb.setEnabled(false);
        tienMoiDotField.setVisible(false);
        mhThanhToanThe.setVisible(false);
        taoDotThanhToanBtn.setVisible(false);
        dotThanhToanTable.clearRows();
        if (hdHienTai.dotThanhToan() == null)
        {
            dotThanhToanTable.setVisible(false);
            mhThongTinThanhToanThe.setATM(hdHienTai.atm());
            hinhThucTTCbb.setSelectedIndex(0);
        }
        else
        {
            dotThanhToanTable.setVisible(true);
            mhThongTinThanhToanThe.setVisible(false);
            hinhThucTTCbb.setSelectedIndex(1);
            for (DotThanhToanDto dtt : hdHienTai.dotThanhToan())
            {
                ButtonCell detail;
                if (dtt.atm() != null)
                    detail = new ButtonCell("Xem thanh toán", () -> {
                        mhThongTinThanhToanThe.setVisible(true);
                        mhThongTinThanhToanThe.setATM(dtt.atm());
                    });
                else
                    detail = new ButtonCell("Thanh toán", () -> {
                        isAll = false;
                        dotThanhToanVuaChon = dtt;
                        mhThanhToanThe.setVisible(true);
                    });

                NColumnsPanel turn = new NColumnsPanel(new ICell[]{
                        new LabelCell(dtt.ngayBD().toString()),
                        new LabelCell(dtt.ngayKT().toString()),
                        new LabelCell(String.valueOf(dtt.soTien())),
                        new LabelCell(dtt.trangThai()),
                        detail})
                {
                    @Override
                    public String getHeader() {
                        return dtt.ngayBD().toString();
                    }

                    @Override
                    public Component getComponent() {
                        return this;
                    }
                };
                dotThanhToanTable.addRow(turn);
                dotThanhToanTable.updateUI();
            }

        }
    }
    public void setPdktcHienTai(PhieuDKTCDto pdktcHienTai) {
        this.pdktcHienTai = pdktcHienTai;
    }
}
