package connection_objects;

import static connection_objects.DriverTemplate.*;

import java.sql.*;

public class ConnectionObj {

    static Connection con = null;

    public static Connection getConnection() {

        try {
            if(con == null || con.isClosed()) {
                    con = DriverManager.getConnection(url, user, pass);
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return con;
    }

    public static void closeConnection() {
        try {
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
    }
}
    

