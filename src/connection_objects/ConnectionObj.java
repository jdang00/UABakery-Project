/* 	Name: Tyler Johnson, Justin Dang, Branigan Geoates
	Username: group1
	Problem Set: Final Project
	Due Date: December 9th, 2021
*/

package connection_objects;

import static connection_objects.DriverTemplate.*;

import java.sql.*;

public class ConnectionObj {

    static Connection con;

    public static Connection getConnection() {

        checkForDrivers();

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
            System.out.println("Closing connection\n\n\n\n\n\n\n");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
    }

    public static void checkForDrivers(){
        try {
            Class.forName("com.ibm.db2.jcc.DB2Driver");
            System.out.println("Drivers loaded!");
        } catch (Exception ex) {
            System.out.println("Drivers not loaded!");
            ex.printStackTrace();
            return;
        }
    }
}
    

