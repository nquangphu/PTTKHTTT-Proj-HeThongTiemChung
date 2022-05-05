package controllers;

import database.GoiTiemChungDB;
import dtos.GoiTiemChungDto;

import java.util.Enumeration;

/**
 * controllers
 * Created by NhatLinh - 19127652
 * Date 4/24/2022 - 11:04 AM
 * Description: ...
 */
public class XuLyGoiTiemChung {

    public static Iterable<GoiTiemChungDto> layGTC()
    {
        return GoiTiemChungDB.layGTC();
    }
}
