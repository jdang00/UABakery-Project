/* 	Name: Tyler Johnson
	Username: uafs408
	Problem Set: PS4
	Due Date: October 12th, 2021
*/
package dao;

import entities.Inventory;
import interfaces.UABakeryDataAccessObject;
import java.util.ArrayList;
import java.sql.*;
import connection_objects.ConnectionObj;

/**
 *
 * @author tyler
 */
public class InventoryDAO implements UABakeryDataAccessObject<Inventory> {
    

    public ArrayList<Inventory> getItems() {
        try{
            Connection con = ConnectionObj.getConnection();
            ArrayList<Inventory> list = new ArrayList<>();
            ResultSet rst = con.prepareStatement("SELECT * FROM INVENTORY").executeQuery();
            
            while(rst.next()){
                Inventory inventory = new Inventory();
                inventory.id = rst.getInt(1);
                inventory.name = rst.getString(2);
                inventory.description = rst.getString(3);
                inventory.quantity = rst.getInt(4);
                inventory.reorderAmount = rst.getInt(5);
                inventory.reorderPrice = rst.getFloat(6);
                
                list.add(inventory);
            }
            con.close();
            return list;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public void removeInventory(int totalAmount, int invID){
        try{
            Connection con = ConnectionObj.getConnection();
            PreparedStatement pst = con.prepareStatement("UPDATE INVENTORY SET INV_QTY_ONHAND = INV_QTY_ONHAND - ? WHERE INV_ID = ?");
            pst.setInt(1, totalAmount);
            pst.setInt(2, invID);
            pst.execute();
            con.close();
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }

    public void addInventory(int totalAmount, int invID){
        try{
            Connection con = ConnectionObj.getConnection();
            PreparedStatement pst = con.prepareStatement("UPDATE INVENTORY SET INV_QTY_ONHAND = INV_QTY_ONHAND + ? WHERE INV_ID = ?");
            pst.setInt(1, totalAmount);
            pst.setInt(2, invID);
            pst.execute();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    
    }
    
    public Inventory getItem(int id){
        try{
            Connection con = ConnectionObj.getConnection();
            ArrayList<Inventory> list = new ArrayList<>();
            PreparedStatement pst = con.prepareStatement("SELECT * FROM INVENTORY WHERE INV_ID = ?");
            pst.setInt(1, id);
            
            ResultSet rst = pst.executeQuery();
            
            if(rst.next()){
                Inventory inventory = new Inventory();
                inventory.id = rst.getInt(1);
                inventory.name = rst.getString(2);
                inventory.description = rst.getString(3);
                inventory.quantity = rst.getInt(4);
                inventory.reorderAmount = rst.getInt(5);
                inventory.reorderPrice = rst.getFloat(6);
                return inventory;
            }
            
            con.close();
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void delete(int id) {
        
        try{
            Connection con = ConnectionObj.getConnection();
            PreparedStatement pst = con.prepareStatement("DELETE FROM INVENTORY WHERE INV_ID = ?");
            pst.setInt(1, id);
            pst.execute();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }

    public void insert(Inventory item) {
        try{
            Connection con = ConnectionObj.getConnection();
            PreparedStatement pst = con.prepareStatement("INSERT INTO INVENTORY VALUES(DEFAULT, ?, ?, ?, ?, ?)");
            pst.setString(1, item.name);
            pst.setString(2, item.description);
            pst.setInt(3, item.quantity);
            pst.setInt(4, item.reorderAmount);
            pst.setFloat(5, item.reorderPrice);
            pst.execute();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    
}
