/* 	Name: Tyler Johnson, Justin Dang, Branigan Geoates
	Username: group1
	Problem Set: Final Project
	Due Date: December 9th, 2021
*/



package dao;

import java.util.ArrayList;
import connection_objects.ConnectionObj;
import java.sql.*;
import entities.*;
import interfaces.UABakeryDataAccessObject;

public class OrderDetailsDAO implements UABakeryDataAccessObject<OrderDetails> {

    @Override
    public ArrayList<OrderDetails> getItems() {
        try{
            Connection con = ConnectionObj.getConnection();
            ArrayList<OrderDetails> list = new ArrayList<>();
            ResultSet rst = con.prepareStatement("SELECT * FROM ORDER_DETAILS").executeQuery();

            while(rst.next()){
                
                OrderDetails orderDetails = new OrderDetails();
                orderDetails.orderDetailsID = rst.getInt(1);
                orderDetails.orderID = rst.getInt(2);
                orderDetails.bakeryItemID = rst.getInt(3);
                orderDetails.quantity = rst.getInt(4);
                
                list.add(orderDetails);
            }

            ConnectionObj.closeConnection();

            return list;
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return null;
    }

    @Override
    public void delete(int id) {
        try{
            Connection con = ConnectionObj.getConnection();
            PreparedStatement pst = con.prepareStatement("DELETE FROM ORDER_DETAILS WHERE ORDER_DETAILS_ID = ?");
            pst.setInt(1, id);
            pst.execute();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void insert(OrderDetails orderDetails) {
        try{
            Connection con = ConnectionObj.getConnection();
            PreparedStatement pst = con.prepareStatement("INSERT INTO ORDER_DETAILS VALUES(DEFAULT, ?, ?, ?)");
            pst.setInt(1, orderDetails.orderID);
            pst.setInt(2, orderDetails.bakeryItemID);
            pst.setInt(3, orderDetails.quantity);
            pst.execute();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}