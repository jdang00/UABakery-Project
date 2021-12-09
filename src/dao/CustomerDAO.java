/* 	Name: Tyler Johnson, Justin Dang, Branigan Geoates
	Username: group1
	Problem Set: Final Project
	Due Date: December 9th, 2021
*/
package dao;

import entities.Customer;
import interfaces.UABakeryDataAccessObject;
import java.util.ArrayList;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

import connection_objects.ConnectionObj;

/**
 *
 * @author tyler
 */
public class CustomerDAO implements UABakeryDataAccessObject<Customer> {
    
    
    public ArrayList<Customer> getItems(){
        
        
        ArrayList<Customer> list = new ArrayList<>();
        
        try{
            Connection con = ConnectionObj.getConnection();
            ResultSet rst = con.prepareStatement("SELECT * FROM CUSTOMERS").executeQuery();
            if(rst != null){
                while(rst.next()){
                    Customer customer = new Customer();
                    
                    customer.id = rst.getInt(1);
                    customer.firstName = rst.getString(2);
                    customer.lastName = rst.getString(3);
                    customer.address = rst.getString(4);
                    customer.city = rst.getString(5);
                    customer.state = rst.getString(6);
                    customer.zipCode = rst.getString(7);
                    customer.phone = rst.getString(8);
                    customer.email = rst.getString(9);
                    customer.username = rst.getString(10);
                    customer.password = rst.getString(11);
                    
                    
                    
                    list.add(customer);
                }
            }
            con.close();
            return list;
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return null;
    }
    
    public void delete(int id){
        
       
        
        try{
             Connection con = ConnectionObj.getConnection();
            PreparedStatement pst = con.prepareStatement("DELETE FROM CUSTOMERS WHERE CUST_ID = ?");
            pst.setInt(1, id);
            pst.execute();
            con.close();
        }catch(Exception e){
            
            e.printStackTrace();
            
        }
        
    }
    
    public void insert(Customer item){
       
        
        try{
             Connection con = ConnectionObj.getConnection();
            PreparedStatement pst = con.prepareStatement("INSERT INTO CUSTOMERS VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pst.setString(1, item.firstName);
            pst.setString(2, item.lastName);
            pst.setString(3, item.address);
            pst.setString(4, item.city);
            pst.setString(5, item.state);
            pst.setString(6, item.zipCode);
            pst.setString(7, item.phone);
            pst.setString(8, item.email);
            pst.setString(9, item.username);
            
            String password = BCrypt.hashpw(item.password, BCrypt.gensalt());
            System.out.println(password);
            
            pst.setString(10, password);
            
            pst.execute();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public ArrayList<Customer> search(String searchTerm){
        searchTerm = "%" + searchTerm + "%";
        ArrayList<Customer> list = new ArrayList<>();
        try{
             Connection con = ConnectionObj.getConnection();
            PreparedStatement pst = con.prepareStatement("SELECT * FROM CUSTOMERS WHERE CUST_ID LIKE ? OR CUST_FIRST_NAME LIKE ? OR CUST_LAST_NAME LIKE ? OR CUSST_ADDRESS LIKE ? OR CUST_CITY LIKE ? OR CUST_STATE LIKE ? OR CUST_ZIP LIKE ? OR CUST_PHONE LIKE ? OR CUST_EMAIL LIKE ? OR CUST_USERNAME LIKE ?;");
            pst.setString(1, searchTerm);
            pst.setString(2, searchTerm);
            pst.setString(3, searchTerm);
            pst.setString(4, searchTerm);
            pst.setString(5, searchTerm);
            pst.setString(6, searchTerm);
            pst.setString(7, searchTerm);
            pst.setString(8, searchTerm);
            pst.setString(9, searchTerm);
            pst.setString(10, searchTerm);
            ResultSet rst = pst.executeQuery();
            
            if(rst != null){
                while(rst.next()){
                    Customer customer = new Customer();
                    
                    customer.id = rst.getInt(1);
                    customer.firstName = rst.getString(2);
                    customer.lastName = rst.getString(3);
                    customer.address = rst.getString(4);
                    customer.city = rst.getString(5);
                    customer.state = rst.getString(6);
                    customer.zipCode = rst.getString(7);
                    customer.phone = rst.getString(8);
                    customer.email = rst.getString(9);
                    customer.username = rst.getString(10);
                    customer.password = rst.getString(11);
                    
                    System.out.println(customer.firstName);
                    
                    list.add(customer);
                }
            }
            con.close();
            return list;
            
        }catch(Exception e){
            
            e.printStackTrace();
            
        }
        return list;
    }
}
