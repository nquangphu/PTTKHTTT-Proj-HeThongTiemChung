package gui.khachHang;

import dtos.KhachHangDto;
import gui.utilities.TitlePanel;

import javax.swing.*;

import static gui.khachHang.MHThongTinKhachHang.addComponent;

/**
 * gui.user
 * Created by NhatLinh - 19127652
 * Date 4/23/2022 - 11:08 PM
 * Description: ...
 */
public class MHThongTinKhachHangToanBo extends TitlePanel {

    private final MHThongTinNguoiGiamHo mhThongTinNguoiGiamHo = new MHThongTinNguoiGiamHo();
    private final MHThongTinKhachHang mhThongTinKhachHang = new MHThongTinKhachHang();

    public MHThongTinKhachHangToanBo(boolean enable)
    {
        super("Thông tin khách hàng");
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        addComponent(this, mhThongTinKhachHang, "Thông tin cá nhân");
        addComponent(this, mhThongTinNguoiGiamHo, "Thông tin người giám hộ");
        mhThongTinNguoiGiamHo.setEnable(enable);
        mhThongTinKhachHang.setEnable(enable);
    }

    public KhachHangDto layKH()
    {
        return new KhachHangDto(mhThongTinKhachHang.getMaKH(), mhThongTinKhachHang.layMaKH(),
                mhThongTinKhachHang.layNgSinh(), mhThongTinKhachHang.layGioiTinh(),
                mhThongTinKhachHang.layDiaChi(), mhThongTinKhachHang.laySDT(),
                mhThongTinKhachHang.layEmail(), mhThongTinNguoiGiamHo.layNGH());
    }

    public void clear()
    {
        mhThongTinKhachHang.clear();
        mhThongTinNguoiGiamHo.clear();
    }

    public void ganKH(KhachHangDto kh)
    {
        mhThongTinKhachHang.ganKH(kh);
        mhThongTinNguoiGiamHo.ganNGH(kh.nguoiGiamHo());
    }
}
