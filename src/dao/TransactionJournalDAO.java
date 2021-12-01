package dao;

import java.util.ArrayList;
import entities.*;
import interfaces.UABakeryDataAccessObject;
import tools.DateTimeHandler;
import connection_objects.ConnectionObj;
import java.sql.*;

public class TransactionJournalDAO implements UABakeryDataAccessObject<TransactionJournal> {

    @Override
    public ArrayList<TransactionJournal> getItems() {
        try{
            Connection con = ConnectionObj.getConnection();
            ArrayList<TransactionJournal> list = new ArrayList<>();
            ResultSet rst = con.prepareStatement("SELECT * FROM TRANSACTION_JOURNAL").executeQuery();

            while(rst.next()){
                
                TransactionJournal journal = new TransactionJournal();
                journal.journalID = rst.getInt(1);
                journal.journalDescription = rst.getString(2);
                journal.journalAmount = rst.getFloat(3);
                journal.timeStamp = rst.getString(4);
                
                list.add(journal);
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
            PreparedStatement pst = con.prepareStatement("DELETE FROM TRANSACTION_JOURNAL WHERE JOURNAL_ID = ?");
            pst.setInt(1, id);
            pst.execute();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void insert(TransactionJournal journal) {
        try{
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            Connection con = ConnectionObj.getConnection();
            PreparedStatement pst = con.prepareStatement("INSERT INTO TRANSACTION_JOURNAL VALUES(DEFAULT, ?, ?, ?)");
            pst.setString(1, journal.journalDescription);
            pst.setFloat(2, journal.journalAmount);
            pst.setTimestamp(3, timestamp);
            pst.execute();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public int getCompanyFunds(){
        try{
            Connection con = ConnectionObj.getConnection();
            
            ResultSet rst = con.prepareStatement("SELECT JOURNAL_AMOUNT FROM TRANSACTION_JOURNAL WHERE JOURNAL_ID = 1").executeQuery();

            int companyFunds = rst.getInt(1);

            ConnectionObj.closeConnection();

            return companyFunds;
        }catch(Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    public void addCompanyFunds(float amount){
        try{
            Connection con = ConnectionObj.getConnection();
            PreparedStatement pst = con.prepareStatement("UPDATE TRANSACTION_JOURNAL SET JOURNAL_AMOUNT = JOURNAL_AMOUNT + ? WHERE JOURNAL_ID = 1");
            pst.setFloat(1, amount);
            pst.execute();
            con.close();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public void subtractCompanyFunds(float amount){
        try{
            Connection con = ConnectionObj.getConnection();
            PreparedStatement pst = con.prepareStatement("UPDATE TRANSACTION_JOURNAL SET JOURNAL_AMOUNT = JOURNAL_AMOUNT - ? WHERE JOURNAL_ID = 1");

            

            pst.setFloat(1, amount);
            pst.execute();
            con.close();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    
    
}
