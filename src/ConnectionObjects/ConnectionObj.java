package ConnectionObjects;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author justindang
 */
import static ConnectionObjects.DriverTemplate.*;
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
    

