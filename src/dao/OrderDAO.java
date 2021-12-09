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
import tools.DateTimeHandler;

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
            PreparedStatement pst = con.prepareStatement("DELETE FROM ORDERS WHERE ORDER_ID = ?");
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
            Date date = new Date(System.currentTimeMillis());
            Time time = new Time(System.currentTimeMillis());
            Connection con = ConnectionObj.getConnection();
            PreparedStatement pst = con.prepareStatement("INSERT INTO ORDERS VALUES(DEFAULT, ?, ?, 1)");
            pst.setDate(1, date);
            pst.setTime(2, time);
            pst.execute();
            con.commit();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
            try{
                ConnectionObj.getConnection().rollback();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

}