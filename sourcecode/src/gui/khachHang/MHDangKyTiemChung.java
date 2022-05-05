package gui.khachHang;

import controllers.*;
import dtos.ChiTietPhieuDKTCDto;
import dtos.GoiTiemChungDto;
import dtos.KhachHangDto;
import dtos.LoaiVacXinDto;

import javax.swing.*;
import java.util.List;

/**
 * gui.register
 * Created by NhatLinh - 19127652
 * Date 4/23/2022 - 11:24 PM
 * Description: ...
 */
public class MHDangKyTiemChung extends MHTaoPhieu {

    private final Runnable denDMVX;

    public MHDangKyTiemChung(Runnable denDMVX) {
        super(new MHDanhSachGoiTiemChung(new String[]{
                        "Ngày muốn tiêm",
                        "Xóa"
                }),
                new MHDanhSachVacXin(new String[]{
                        "Ngày muốn tiêm",
                        "Xóa"
                }));
        this.denDMVX = denDMVX;
    }

    @Override
    protected GTCRow taoDongLuaChonGTC(GoiTiemChungDto gtc) {
        return new ChonGTCNgayTiemRow(gtc, () -> {
            gtcChon.getGtcHienThi().deleteRow(String.valueOf(gtc.maGoi()));
            themDongHienThiGTC(gtc);
        });
    }

    @Override
    protected VacXinRow taoDongLuaChonVX(LoaiVacXinDto lvx) {
        return new ChonVacXinNgayTiemRow(lvx, () -> {
            vxChon.getVxHienThi().deleteRow(String.valueOf(lvx.maLoaiVX()));
            themDongHienThiVX(lvx);
        });
    }

    private void suDungDMVX()
    {
        Object[] options = {
                "Đặt mua vắc xin"};
        int n = JOptionPane.showOptionDialog(this,
                "Dịch vụ đã hết số lượng. Bạn vui lòng sử dụng dịch vụ đặt mua vắc xin để được ưu tiên.",
                "Số lượng đã hết",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        if (n == 0)
            denDMVX.run();
    }

    @Override
    protected void luaChonGTC(GoiTiemChungDto gtc)
    {
        if (gtc.tonKho() <= 0)
        {
            suDungDMVX();
            return;
        }
        super.luaChonGTC(gtc);
    }

    @Override
    protected void luaChonVX(LoaiVacXinDto lvx)
    {
        if (lvx.tonKho() <= 0)
        {
            suDungDMVX();
            return;
        }
        super.luaChonVX(lvx);
    }

    protected void xacNhan()
    {
        KhachHangDto kh = mhThongTinKH.layKH();
        StringBuilder msg = new StringBuilder();
        if (!XuLyDangKyTiemChung.kiemTraThongTin(kh, msg))
            JOptionPane.showMessageDialog(this, msg);
        else
        {
            List<ChiTietPhieuDKTCDto> ctps = gtcChon.layCtps();
            ctps.addAll(vxChon.layCtps());
            if (ctps.isEmpty())
            {
                JOptionPane.showMessageDialog(this, "Bạn phải chọn ít nhất 1 loại dịch vụ");
                return;
            }
            for (ChiTietPhieuDKTCDto ctp : ctps)
                if (ctp.ngayMuonTiem() == null)
                {
                    JOptionPane.showMessageDialog(this, "Bạn cần điền đầy đủ ngày muốn tiêm");
                    return;
                }
            if (XuLyDangKyTiemChung.taoPDKTC(kh, ctps))
                JOptionPane.showMessageDialog(this, "Bạn vừa đăng ký tiêm chủng thành công. Xin vui lòng kiểm tra lại trong mục danh sách phiếu đăng ký tiêm chủng!");
            else
                JOptionPane.showMessageDialog(this, "Bạn vừa tạo phiếu đăng ký thất bại. Xin vui thử lại sau!");
            gtcChon.getGtcHienThi().clearRows();
            vxChon.getVxHienThi().clearRows();
            taiLaiGTC();
            taiLaiVX();
        }
    }
}
