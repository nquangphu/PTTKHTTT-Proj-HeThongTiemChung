package controllers;

import database.VacXinDB;
import dtos.LoaiVacXinDto;

import java.util.Enumeration;

/**
 * controllers
 * Created by NhatLinh - 19127652
 * Date 4/24/2022 - 11:02 AM
 * Description: ...
 */
public class XuLyVacXin {

    public static Iterable<LoaiVacXinDto> layLoaiVacXin()
    {
        return VacXinDB.layLoaiVacXin();
    }

}
