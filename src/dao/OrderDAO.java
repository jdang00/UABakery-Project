package dao;

import java.util.ArrayList;
import connection_objects.ConnectionObj;
import java.sql.*;
import entities.*;
import interfaces.UABakeryDataAccessObject;

public class OrderDAO implements UABakeryDataAccessObject<Order> {

    @Override
    public ArrayList<Order> getItems() {
        try{
            Connection con = ConnectionObj.getConnection();
            ArrayList<Order> list = new ArrayList<>();
            ResultSet rst = con.prepareStatement("SELECT * FROM ORDERS").executeQuery();

            while(rst.next()){
                
                Order order = new Order();
                order.orderID = rst.getInt(1);
                order.date = rst.getString(2);
                order.time = rst.getString(3);
                order.customerID = rst.getInt(4);
                
                list.add(order);
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
            PreparedStatement pst = con.prepareStatement("DELETE FROM ORDER WHERE ORDER_ID = ?");
            pst.setInt(1, id);
            pst.execute();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Order order) {
        try{
            Connection con = ConnectionObj.getConnection();
            PreparedStatement pst = con.prepareStatement("INSERT INTO ORDER VALUES(DEFAULT, ?, ?, ?)");
            pst.setString(1, order.date);
            pst.setString(2, order.time);
            pst.setInt(3, order.customerID);
            pst.execute();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}