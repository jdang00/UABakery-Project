/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
