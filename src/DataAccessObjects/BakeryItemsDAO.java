/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAccessObjects;
import java.util.*;
import java.sql.*;
import ConnectionObjects.ConnectionObj;

import java.util.List;

/**
 *
 * @author justindang
 */
public class BakeryItemsDAO implements UABakeryInterface<BakeryItems> {
    
    
    Connection con;
    PreparedStatement pstmt;
    ResultSet rs;

    
    @Override
    public List getAllItemsFromDatabase() {
    List<BakeryItems> allItems = new ArrayList<>();

        try {
            Class.forName("com.ibm.db2.jcc.DB2Driver");

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        try {

            String sql = "SELECT BAKERY_ITEM_ID, BAKERY_ITEM_NAME, BAKERY_ITEM_DESCRIPTION, BAKERY_ITEM_PRICE FROM BAKERY_ITEMS";
            con = ConnectionObj.getConnection();

            pstmt = con.prepareStatement(sql);

            rs = pstmt.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    int id = rs.getInt("BAKERY_ITEM_ID");
                    String name = rs.getString("BAKERY_ITEM_NAME");
                    String desc = rs.getString("BAKERY_ITEM_DESCRIPTION");
                    double price = rs.getDouble("BAKERY_ITEM_PRICE");

                    BakeryItems item = new BakeryItems();
                    item.setID(id);
                    item.setITEM_NAME(name);
                    item.setDESCRIPTION(desc);
                    item.setPRICE(price);
                    allItems.add(item);

                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return allItems;    }

    @Override
    public List getItem(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(BakeryItems item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(BakeryItems item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(BakeryItems item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
