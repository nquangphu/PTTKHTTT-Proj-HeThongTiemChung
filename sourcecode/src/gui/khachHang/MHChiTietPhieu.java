package gui.khachHang;

import gui.khachHang.MHDanhSachGoiTiemChung;
import gui.khachHang.MHDanhSachVacXin;
import gui.khachHang.MHThongTinKhachHangToanBo;

import javax.swing.*;
import java.awt.*;

/**
 * gui.register
 * Created by NhatLinh - 19127652
 * Date 4/29/2022 - 6:41 PM
 * Description: ...
 */
public class MHChiTietPhieu extends JPanel {

    protected final MHThongTinKhachHangToanBo mhThongTinKhachHang = new MHThongTinKhachHangToanBo(false);
    protected final MHDanhSachGoiTiemChung gtcShow;
    protected final MHDanhSachVacXin vacShow;
    protected final JTextField id = new JTextField();
    protected final JTextField date = new JTextField();
    protected final JTextField status = new JTextField();
    protected final JTextField staff = new JTextField();
    protected final JPanel priceSection = new JPanel(new BorderLayout());
    protected final JPanel detailedSection = new JPanel(new GridLayout(2, 1));

    public MHChiTietPhieu(MHDanhSachGoiTiemChung gtcShow, MHDanhSachVacXin vacShow)
    {
        super(new BorderLayout());
        this.gtcShow = gtcShow;
        this.vacShow = vacShow;
        gtcShow.setBorder(BorderFactory.createTitledBorder("Gói tiêm chủng"));
        vacShow.setBorder(BorderFactory.createTitledBorder("Vắc xin lẻ"));
        detailedSection.add(gtcShow);
        detailedSection.add(vacShow);
        priceSection.add(detailedSection, BorderLayout.CENTER);
        JPanel paperInfor = new JPanel(new GridLayout(1, 4));
        id.setBorder(BorderFactory.createTitledBorder("Mã phiếu"));
        date.setBorder(BorderFactory.createTitledBorder("Ngày lập phiếu"));
        status.setBorder(BorderFactory.createTitledBorder("Trạng thái"));
        staff.setBorder(BorderFactory.createTitledBorder("Nhân viên duyệt"));
        id.setEditable(false);
        date.setEditable(false);
        status.setEditable(false);
        staff.setEditable(false);
        paperInfor.add(id);
        paperInfor.add(date);
        paperInfor.add(status);
        paperInfor.add(staff);
        add(paperInfor, BorderLayout.PAGE_START);
        add(mhThongTinKhachHang, BorderLayout.LINE_START);
        add(priceSection, BorderLayout.CENTER);
    }

    public MHChiTietPhieu()
    {
        super(new BorderLayout());
        this.gtcShow = new MHDanhSachGoiTiemChung();
        this.vacShow = new MHDanhSachVacXin();
        gtcShow.setBorder(BorderFactory.createTitledBorder("Gói tiêm chủng"));
        vacShow.setBorder(BorderFactory.createTitledBorder("Vắc xin lẻ"));
        detailedSection.add(gtcShow);
        detailedSection.add(vacShow);
        priceSection.add(detailedSection, BorderLayout.CENTER);
        JPanel paperInfor = new JPanel(new GridLayout(1, 4));
        id.setBorder(BorderFactory.createTitledBorder("Mã phiếu"));
        date.setBorder(BorderFactory.createTitledBorder("Ngày lập phiếu"));
        status.setBorder(BorderFactory.createTitledBorder("Trạng thái"));
        staff.setBorder(BorderFactory.createTitledBorder("Nhân viên duyệt"));
        id.setEditable(false);
        date.setEditable(false);
        status.setEditable(false);
        staff.setEditable(false);
        paperInfor.add(id);
        paperInfor.add(date);
        paperInfor.add(status);
        paperInfor.add(staff);
        add(paperInfor, BorderLayout.PAGE_START);
        add(mhThongTinKhachHang, BorderLayout.LINE_START);
        add(priceSection, BorderLayout.CENTER);
    }
}
