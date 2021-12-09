/* 	Name: Tyler Johnson, Justin Dang, Branigan Geoates
	Username: group1
	Problem Set: Final Project
	Due Date: December 9th, 2021
*/

package dao;

import interfaces.*;
import entities.Payment;
import java.sql.*;
import java.util.ArrayList;
import connection_objects.ConnectionObj;

/**
 *
 * @author tyler
 */
public class PaymentDAO implements UABakeryDataAccessObject<Payment>{
    
    
    public ArrayList<Payment> getItems(){
        return null;
    }
    
    public void delete(int id){
        try{
            Connection con = ConnectionObj.getConnection();
            PreparedStatement pst = con.prepareStatement("DELETE FROM PAYMENT WHERE PAYMENT_ID = ?");
            pst.setInt(1, id);
            pst.execute();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void insert(Payment item){
        try{
            Connection con = ConnectionObj.getConnection();
            PreparedStatement pst = con.prepareStatement("INSERT INTO PAYMENT VALUES(DEFAULT, ?, ?, ?)");
            pst.setInt(1, item.custID);
            pst.setString(2, item.paymentType);
            pst.setFloat(3, item.paymentAmount);
            pst.execute();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
