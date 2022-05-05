package gui.khachHang;

import controllers.*;
import dtos.GoiTiemChungDto;
import dtos.KhachHangDto;
import dtos.LoaiVacXinDto;
import gui.khachHang.*;
import gui.khachHang.MHThongTinKhachHangToanBo;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * gui.register
 * Created by NhatLinh - 19127652
 * Date 4/29/2022 - 4:22 PM
 * Description: ...
 */
public abstract class MHTaoPhieu extends JPanel {
    protected final MHDanhSachGoiTiemChung gtcHeThong = new MHDanhSachGoiTiemChung(new String[]{"Chọn"}, this::taiLaiGTC);
    protected final MHDanhSachGoiTiemChung gtcChon;
    protected final MHDanhSachVacXin vxHeThong = new MHDanhSachVacXin(new String[]{"Chọn"}, this::taiLaiVX);
    protected final MHDanhSachVacXin vxChon;
    protected final MHThongTinKhachHangToanBo mhThongTinKH;
    protected final JTabbedPane mainTab = new JTabbedPane(JTabbedPane.TOP);

    public void taiLaiGTC()
    {
        Iterable<GoiTiemChungDto> gtcs = XuLyGoiTiemChung.layGTC();
        gtcHeThong.getGtcHienThi().clearRows();
        List<GoiTiemChungDto> storedGTC = gtcChon.layGTCs();
        for (GoiTiemChungDto gtc : gtcs) {
            if (storedGTC.stream().noneMatch(o -> o.maGoi() == gtc.maGoi()))
                themDongHienThiGTC(gtc);
        }
    }

    protected void luaChonGTC(GoiTiemChungDto gtc)
    {
        gtcHeThong.getGtcHienThi().deleteRow(String.valueOf(gtc.maGoi()));
        gtcChon.getGtcHienThi().addRow(taoDongLuaChonGTC(gtc));
        gtcHeThong.getGtcHienThi().updateUI();
        gtcChon.getGtcHienThi().updateUI();
    }

    protected abstract GTCRow taoDongLuaChonGTC(GoiTiemChungDto gtc);
    protected abstract VacXinRow taoDongLuaChonVX(LoaiVacXinDto vx);

    protected void themDongHienThiGTC(GoiTiemChungDto gtc)
    {
        gtcHeThong.getGtcHienThi().addRow(new ChonGTCRow(gtc, () -> luaChonGTC(gtc)));
        gtcHeThong.getGtcHienThi().updateUI();
        gtcChon.getGtcHienThi().updateUI();
    }

    public void taiLaiVX()
    {
        Iterable<LoaiVacXinDto> lvxs = XuLyVacXin.layLoaiVacXin();
        vxHeThong.getVxHienThi().clearRows();
        List<LoaiVacXinDto> storedLvx = vxChon.layLVXs();
        for (LoaiVacXinDto lvx : lvxs) {
            if (storedLvx.stream().noneMatch(o -> o.maLoaiVX() == lvx.maLoaiVX()))
                themDongHienThiVX(lvx);
        }
    }

    protected void luaChonVX(LoaiVacXinDto lvx)
    {
        vxHeThong.getVxHienThi().deleteRow(String.valueOf(lvx.maLoaiVX()));
        vxChon.getVxHienThi().addRow(taoDongLuaChonVX(lvx));
        vxHeThong.getVxHienThi().updateUI();
        vxChon.getVxHienThi().updateUI();
    }

    protected void themDongHienThiVX(LoaiVacXinDto lvx)
    {
        vxHeThong.getVxHienThi().addRow(new ChonVacXinRow(lvx, () -> luaChonVX(lvx)));
        vxHeThong.getVxHienThi().updateUI();
        vxChon.getVxHienThi().updateUI();
    }

    public MHTaoPhieu(MHDanhSachGoiTiemChung gtcChon, MHDanhSachVacXin vaccineChon)
    {
        super(new BorderLayout());
        this.gtcChon = gtcChon;
        this.vxChon = vaccineChon;
        JPanel packageSection = new JPanel(new GridLayout(2, 1));
        JPanel vaccineSection = new JPanel(new GridLayout(2, 1));
        boolean login = XuLyDangNhap.isLogin();
        mhThongTinKH = new MHThongTinKhachHangToanBo(!login);
        gtcHeThong.setBorder(BorderFactory.createTitledBorder("Gói tiêm chủng trong hệ thống"));
        gtcChon.setBorder(BorderFactory.createTitledBorder("Gói tiêm chủng đã chọn"));
        vxHeThong.setBorder(BorderFactory.createTitledBorder("Vắc xin trong hệ thống"));
        vaccineChon.setBorder(BorderFactory.createTitledBorder("Vắc xin đã chọn"));
        packageSection.add(gtcHeThong);
        packageSection.add(gtcChon);
        vaccineSection.add(vxHeThong);
        vaccineSection.add(vaccineChon);
        mainTab.add("Chọn gói tiêm chủng", packageSection);
        mainTab.add("Chọn vắc xin lẻ", vaccineSection);
        JButton confirmBtn = new JButton("Xác nhận");
        confirmBtn.addActionListener(e -> {
            Object[] options = {
                    "Xác nhận",
                    "Hủy bỏ"};
            int n = JOptionPane.showOptionDialog(this,
                    "Bạn thật sự muốn tạo thông tin này?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
            if (n == 0)
                xacNhan();
        });

        if (login)
        {
            KhachHangDto kh = XuLyKhachHang.layThongTinKH();
            mhThongTinKH.ganKH(kh);
            add(mhThongTinKH, BorderLayout.LINE_START);
        }
        else
        {
            JPanel inforSection = new JPanel(new BorderLayout());
            JButton clearInfor = new JButton("Xóa thông tin đã điền");
            clearInfor.addActionListener(e -> {
                mhThongTinKH.clear();
            });
            inforSection.add(mhThongTinKH, BorderLayout.CENTER);
            inforSection.add(clearInfor, BorderLayout.PAGE_END);
            add(inforSection, BorderLayout.LINE_START);
        }
        add(mainTab, BorderLayout.CENTER);
        add(confirmBtn, BorderLayout.PAGE_END);
        taiLaiVX();
        taiLaiGTC();
    }

    protected abstract void xacNhan();
}
