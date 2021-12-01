package dao;

import java.util.ArrayList;
import entities.*;
import interfaces.UABakeryDataAccessObject;

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
            Connection con = ConnectionObj.getConnection();
            PreparedStatement pst = con.prepareStatement("INSERT INTO TRANSACTION_JOURNAL VALUES(DEFAULT, ?, ?, ?)");
            pst.setString(1, journal.journalDescription);
            pst.setFloat(2, journal.journalAmount);
            pst.setString(3, journal.timeStamp);
            pst.execute();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
