package gui.khachHang;

import controllers.XuLyDatMuaVacXin;
import dtos.*;

import javax.swing.*;
import java.util.List;

/**
 * gui.register
 * Created by NhatLinh - 19127652
 * Date 4/29/2022 - 4:21 PM
 * Description: ...
 */
public class MHDatMuaVacXin extends MHTaoPhieu {

    private final MHDanhSachVacXinKhongTrongHeThong mhDanhSachVacXinKhongTrongHeThong = new MHDanhSachVacXinKhongTrongHeThong(true);
    public MHDatMuaVacXin() {
        super(new MHDanhSachGoiTiemChung(new String[]{
                        "Xóa"
                }),
                new MHDanhSachVacXin(new String[]{
                        "Xóa"
                }));
        mainTab.add("Vắc xin không có trong hệ thống", mhDanhSachVacXinKhongTrongHeThong);
    }

    @Override
    protected GTCRow taoDongLuaChonGTC(GoiTiemChungDto gtc) {
        return new ChonGTCRow(gtc,() -> {
            gtcChon.getGtcHienThi().deleteRow(String.valueOf(gtc.maGoi()));
            themDongHienThiGTC(gtc);
        });
    }

    @Override
    protected VacXinRow taoDongLuaChonVX(LoaiVacXinDto lvx) {
        return new ChonVacXinRow(lvx, () -> {
            vxChon.getVxHienThi().deleteRow(String.valueOf(lvx.maLoaiVX()));
            themDongHienThiVX(lvx);
        });
    }

    @Override
    protected void xacNhan() {
        KhachHangDto kh = mhThongTinKH.layKH();
        StringBuilder msg = new StringBuilder();
        if (!XuLyDatMuaVacXin.kiemTraThongTin(kh, msg))
            JOptionPane.showMessageDialog(this, msg);
        else
        {
            List<GoiTiemChungDto> gtc = gtcChon.layGTCs();
            List<LoaiVacXinDto> lvxs = vxChon.layLVXs();
            List<LoaiVacXinKhongTrongHeThongDto> lvxNos = mhDanhSachVacXinKhongTrongHeThong.layLvxs();
            if (gtc.isEmpty() && lvxNos.isEmpty() && lvxs.isEmpty())
            {
                JOptionPane.showMessageDialog(this, "Bạn phải chọn ít nhất 1 loại dịch vụ");
                return;
            }
            if (XuLyDatMuaVacXin.datMuaVacXin(kh, gtc, lvxs, lvxNos))
                JOptionPane.showMessageDialog(this, "Bạn vừa đăng ký tiêm chủng thành công. Xin vui lòng kiểm tra lại trong mục danh sách phiếu đăng ký tiêm chủng!");
            else
            {
                JOptionPane.showMessageDialog(this, "Bạn vừa đăng ký tiêm chủng thất bại. Xin vui lòng kiểm tra lại thông tin!");
                return;
            }
            gtcChon.getGtcHienThi().clearRows();
            vxChon.getVxHienThi().clearRows();
            taiLaiGTC();
            taiLaiVX();
        }
    }
}
