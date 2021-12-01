package dao;

import entities.*;
import interfaces.UABakeryDataAccessObject;
import java.util.ArrayList;
import connection_objects.ConnectionObj;
import java.sql.*;



public class BakeryItemDAO implements UABakeryDataAccessObject<BakeryItem> {
    
    public ArrayList<BakeryItem> getItems(){

        try{
            Connection con = ConnectionObj.getConnection();
            ArrayList<BakeryItem> list = new ArrayList<>();
            ResultSet rst = con.prepareStatement("SELECT * FROM BAKERY_ITEMS").executeQuery();

            while(rst.next()){
                
                BakeryItem item = new BakeryItem();
                item.id = rst.getInt(1);
                item.name = rst.getString(2);
                item.description = rst.getString(3);
                item.price = rst.getFloat(4);
                item.recipeItems = getRecipeItems(rst.getInt(1));
                
                list.add(item);
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
            PreparedStatement pst = con.prepareStatement("DELETE FROM BAKERY_ITEMS WHERE BAKERY_ITEM_ID = ?");
            pst.setInt(1, id);
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
    
    private ArrayList<Recipe> getRecipeItems(int bakeryItemID) throws Exception{
        Connection con = ConnectionObj.getConnection();
        PreparedStatement pst = con.prepareStatement("SELECT * FROM RECIPE WHERE BAKERY_ITEM_ID = ?");
        pst.setInt(1, bakeryItemID);

        ResultSet rs2 = pst.executeQuery();

        ArrayList<Recipe> rList = new ArrayList();

        while(rs2.next()){
            Recipe r = new Recipe();
            r.recipeID = rs2.getInt(1);
            r.bakeryItemID = rs2.getInt(2);
            r.invID = rs2.getInt(3);
            r.invQuantityNeeded = rs2.getInt(4);
            rList.add(r);
        }
        
        
        return rList;
    }
    
    public void insert(BakeryItem item){
       
        try{
            Connection con = ConnectionObj.getConnection();
            PreparedStatement pst = con.prepareStatement("INSERT INTO BAKERY_ITEMS VALUES(DEFAULT, ?, ?, ?)");
            pst.setString(1, item.name);
            pst.setString(2, item.description);
            pst.setFloat(3, item.price);
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
