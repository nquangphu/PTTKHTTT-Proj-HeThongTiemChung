package controllers;

import database.DBHandler;

/**
 * controllers
 * Created by NhatLinh - 19127652
 * Date 4/24/2022 - 11:01 AM
 * Description: ...
 */
public class XuLyDangNhap {
    private static int id;
    private static boolean login = false;
    public static int layId()
    {
        return id;
    }

    public static void dangXuat()
    {
        login = false;
    }

    public static boolean isLogin()
    {
        return login;
    }
    public static String dangNhap(String username, String password)
    {
        StringBuilder state = new StringBuilder();
        Integer gotID = DBHandler.getInstance().login(username, password, state);
        if (gotID != null)
        {
            id = gotID;
            login = true;
            return state.toString();
        }
        login = false;
        return null;
    }
}
