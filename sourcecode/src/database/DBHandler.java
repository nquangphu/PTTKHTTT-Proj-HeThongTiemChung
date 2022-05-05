package database;

import com.microsoft.sqlserver.jdbc.SQLServerCallableStatement;

import java.sql.*;

/**
 * administrator.dbHandler
 * Created by NhatLinh - 19127652
 * Date 3/24/2022 - 1:15 PM
 * Description: ...
 */
public class DBHandler {
    private static DBHandler instance = null;
    //private static final String dbURL = "jdbc:oracle:thin:@localhost:11521:XE";
    private final String connectionUrl = "jdbc:sqlserver://NHATLINH\\SQLEXPRESS:1433;databaseName=HTTC;";
    private final String userName = "sa";
    private final String password = "nhatlinh";


    private DBHandler()
    {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static DBHandler getInstance()
    {
        if (instance == null)
            instance = new DBHandler();
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection
                (connectionUrl, userName, password);
    }

    public Integer login(String userName, String password, StringBuilder role)
    {
        try
        {
            try (Connection conn = DriverManager.getConnection(connectionUrl, userName, password);
                 SQLServerCallableStatement statement = (SQLServerCallableStatement)conn.prepareCall("{CALL lay_vai_tro()}"))
            {
                try (ResultSet res = statement.executeQuery())
                {
                    if (res.next())
                    {
                        role.append(res.getString(2));
                        return res.getInt(1);
                    }
                }
            }
            return null;
        }
        catch (SQLException s)
        {
            s.printStackTrace();
            return null;
        }
    }

}
