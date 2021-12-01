
import com.formdev.flatlaf.FlatLightLaf;

import connection_objects.ConnectionObj;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import dao.*;
import entities.BakeryItem;
import entities.Inventory;
import entities.Order;
import entities.Recipe;
import entities.TransactionJournal;
import tools.MessageCreator;
import tools.MoneyHandler;

import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author justindang
 */



public class GUI extends javax.swing.JFrame {


    private BakeryItem selectedBakeryItem;
    private ArrayList<BakeryItem> bakeryItems = new ArrayList();


    //Tyler: "Table Models"
    private DefaultTableModel bakeryTableModel;
    private DefaultTableModel ordersTableModel;
    private DefaultTableModel transactionsTableModel;
    private DefaultTableModel inventoryTableModel;

    //Tyler: "DAO"
    BakeryItemDAO bakeryDAO = new BakeryItemDAO();
    InventoryDAO inventoryDAO = new InventoryDAO();
    TransactionJournalDAO transactionJournalDAO = new TransactionJournalDAO();
    OrderDAO orderDAO = new OrderDAO();

    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();
        initBakeryTable();
        refreshBakeryItems();
    }


    private void initBakeryTable(){
        bakeryTableModel = new DefaultTableModel();
        bakeryTableModel.addColumn("ID");
        bakeryTableModel.addColumn("Name");
        bakeryTableModel.addColumn("Description");
        bakeryTableModel.addColumn("Price");
        bakeryMenu.setModel(bakeryTableModel);
    }

    public void refreshBakeryItems(){
        bakeryTableModel.setNumRows(0);

        bakeryItems = bakeryDAO.getItems();
        

        for (int i = 0; i < bakeryItems.size(); i++) {
            int id = bakeryItems.get(i).id;
            String name = bakeryItems.get(i).name;
            String description = bakeryItems.get(i).description;
            float price = bakeryItems.get(i).price;

            String[] row = new String[4];
            row[0] = String.valueOf(id);
            row[1] = name;
            row[2] = description;
            row[3] = MoneyHandler.getFormattedMoney(price);

            bakeryTableModel.addRow(row);
        }
    }

    private void refreshTransactionJournalTable( DefaultTableModel model ){
        model.setNumRows(0);


        ArrayList<TransactionJournal> myList = transactionJournalDAO.getItems();
        

        for (int i = 0; i < myList.size(); i++) {
            int id = myList.get(i).journalID;
            String description = myList.get(i).journalDescription;
            float amount = myList.get(i).journalAmount;
            String timeStamp = myList.get(i).timeStamp;

            String[] row = new String[4];
            row[0] = String.valueOf(id);
            row[1] = description;
            row[2] = MoneyHandler.getFormattedMoney(amount);
            row[3] = timeStamp;

            model.addRow(row);
        }

    }

    private void refreshOrderTable( DefaultTableModel model ){
        model.setNumRows(0);

        ArrayList<Order> myList = orderDAO.getItems();
        

        for (int i = 0; i < myList.size(); i++) {
            int id = myList.get(i).orderID;
            String date = myList.get(i).date;
            String time = myList.get(i).time;
            int customerID = myList.get(i).customerID;

            String[] row = new String[4];
            row[0] = String.valueOf(id);
            row[1] = date;
            row[2] = time;
            row[3] = String.valueOf(customerID);

            model.addRow(row);
        }
    }

    private void refreshInventoryTable( DefaultTableModel model ){
        model.setNumRows(0);

        ArrayList<Inventory> myList = inventoryDAO.getItems();
        

        for (int i = 0; i < myList.size(); i++) {
            int id = myList.get(i).id;
            String name = myList.get(i).name;
            String description = myList.get(i).description;
            int quantity = myList.get(i).quantity;
            int reorderAmount = myList.get(i).reorderAmount;
            float reorderPrice = myList.get(i).reorderPrice;

            String[] row = new String[6];
            row[0] = String.valueOf(id);
            row[1] = name;
            row[2] = description;
            row[3] = String.valueOf(quantity);
            row[4] = String.valueOf(reorderAmount);
            row[5] = MoneyHandler.getFormattedMoney(reorderPrice);

            model.addRow(row);
        }
    }


    private void validatePurchase(){

        //Tyler: "So the user can't change the quantity mid purchase"

        qty.setEditable(false);

        updateSelectedBakeryItem();

        bakeryMenu.setEnabled(false);
        
        boolean enoughInventory = checkInventory();


        if(enoughInventory){

            for(Recipe recipie : selectedBakeryItem.recipeItems){
                inventoryDAO.removeInventory(getQuantity() * recipie.invQuantityNeeded, recipie.invID);
            }
            
            float userAmountPurchased =  getQuantity() * selectedBakeryItem.price;
            
            TransactionJournal journal = createTransactionJournal(getJournalPurchaseDesc(selectedBakeryItem.name, getQuantity()), userAmountPurchased);
            transactionJournalDAO.insert(journal);

            transactionJournalDAO.addCompanyFunds(userAmountPurchased);

            Order order = new Order();
            order.customerID = 1;

            orderDAO.insert(order);


            MessageCreator.createSuccessMessage("Item purhcased! Company gained " + MoneyHandler.getFormattedMoney(userAmountPurchased) + " cash, and the user ordered " + getJournalPurchaseDesc(selectedBakeryItem.name, getQuantity()));


        }else{
            //Tyler: "If there isn't enough companyFunds, decline the entire order"
            MessageCreator.createErrorMessage("Not enough company funds");
        }
        
        
        
        clearCart();

        qty.setEditable(true);   
        bakeryMenu.setEnabled(true);
    }


    private boolean checkInventory(){
        
        for(Recipe recipe : selectedBakeryItem.recipeItems){

            //Tyler: "If there isn't enough inventory, order some more."

            int quantityRequested = recipe.invQuantityNeeded * getQuantity();
            Inventory cInventory = inventoryDAO.getItem(recipe.invID);
            int currentQuantity = cInventory.quantity;

            if( quantityRequested > currentQuantity ){
                if(!orderdMoreInventory(cInventory, quantityRequested - currentQuantity))
                    return false;
            }
        }

        return true;
        
    }


    private int getQuantity(){
        //Tyler :"TODO: Error risk"

        try{
            int num = Integer.parseInt(qty.getText());
            return num;
        }catch(Exception e){
            qty.setText("0");
            return -1;
        }
    }


    private boolean orderdMoreInventory(Inventory inventory, int amountNeeded){

        int companyFunds = transactionJournalDAO.getCompanyFunds();

        int reorderMultiplier = getReorderAmountMultiplier(inventory, amountNeeded);

        int amountToReorder = inventory.reorderAmount * reorderMultiplier;

        float inventoryCosts = MoneyHandler.getRoundedMoney(reorderMultiplier * inventory.reorderPrice);

        if(companyFunds < inventoryCosts)
            return false;
        else {
            
            inventoryDAO.addInventory(amountToReorder, inventory.id);

            TransactionJournal journal = createTransactionJournal(getJournalReorderDesc(inventory.name, amountToReorder), -inventoryCosts);
            transactionJournalDAO.insert(journal);

            transactionJournalDAO.subtractCompanyFunds(inventoryCosts);
            return true;
        }


        
    }

    private int getReorderAmountMultiplier(Inventory inventory, int amountNeeded){
        int i = 0;

        while (++i * inventory.reorderAmount < amountNeeded){}

        return i;
    }

    private void clearCart(){
        qty.setText("0");
    }

    private String getJournalReorderDesc(String inventoryItem, int quantity){
        return "ORDERING INVENTORY ITEM " + inventoryItem + " WITH A QUANTITY OF " + String.valueOf(quantity);
    }


    private String getJournalPurchaseDesc(String bakeryItem, int quantity){
        return "SUCCESSFUL ORDER OF " + bakeryItem + " x" + String.valueOf(quantity);
    }

    private TransactionJournal createTransactionJournal(String description, float amount){

        TransactionJournal journal  = new TransactionJournal();
        journal.journalDescription = description;
        journal.journalAmount = amount;

        return journal;

    }

    private void updateSelectedBakeryItem(){
        try{
            selectedBakeryItem = bakeryItems.get(bakeryMenu.getSelectedRow());
        }catch(Exception e){
            return;
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lowerHolder = new javax.swing.JButton();
        payButton = new javax.swing.JButton();
        placeHolderRight = new javax.swing.JButton();
        voidButton = new javax.swing.JButton();
        placeHolderLeft = new javax.swing.JButton();
        undoButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        totalLabel = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();
        shopLayout = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        bakeryMenu = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        qty = new javax.swing.JTextField();
        addButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        menuLayout = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        omniTable = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        ordersButton = new javax.swing.JButton();
        inventoryButton = new javax.swing.JButton();
        journalButton = new javax.swing.JButton();
        menuButton = new javax.swing.JButton();
        shopButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1280, 720));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lowerHolder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PhotoAssets/Lower Button.png"))); // NOI18N
        lowerHolder.setBorderPainted(false);
        lowerHolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lowerHolderActionPerformed(evt);
            }
        });

        payButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PhotoAssets/PayButton.png"))); // NOI18N
        payButton.setBorderPainted(false);
        payButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                payButtonActionPerformed(evt);
            }
        });

        placeHolderRight.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PhotoAssets/Placeholder.png"))); // NOI18N
        placeHolderRight.setBorderPainted(false);

        voidButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PhotoAssets/Void Button.png"))); // NOI18N
        voidButton.setBorderPainted(false);

        placeHolderLeft.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PhotoAssets/Placeholder.png"))); // NOI18N
        placeHolderLeft.setBorderPainted(false);

        undoButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PhotoAssets/Undo.png"))); // NOI18N
        undoButton.setBorderPainted(false);

        jPanel3.setBackground(new java.awt.Color(225, 225, 225));

        jLabel1.setFont(new java.awt.Font("SF Pro Rounded", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(123, 123, 123));
        jLabel1.setText("TOTAL:");

        totalLabel.setFont(new java.awt.Font("SF Pro Rounded", 1, 48)); // NOI18N
        totalLabel.setText("$0.00");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(totalLabel)
                .addGap(18, 18, 18))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(totalLabel)
                .addGap(35, 35, 35))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(placeHolderLeft)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(placeHolderRight))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(undoButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(voidButton))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(payButton)
                            .addComponent(lowerHolder))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(undoButton)
                    .addComponent(voidButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(placeHolderRight)
                    .addComponent(placeHolderLeft))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lowerHolder)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(payButton)
                .addContainerGap())
        );

        mainPanel.setBackground(new java.awt.Color(225, 225, 225));
        mainPanel.setLayout(new java.awt.CardLayout());

        shopLayout.setBackground(new java.awt.Color(225, 225, 225));

        bakeryMenu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(bakeryMenu);

        jPanel5.setBackground(new java.awt.Color(225, 225, 225));

        qty.setFont(new java.awt.Font("SF Pro Rounded", 0, 24)); // NOI18N
        qty.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                qtyMouseExited(evt);
            }
        });
        qty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                qtyKeyReleased(evt);
            }
        });

        addButton.setBackground(new java.awt.Color(225, 225, 225));
        addButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PhotoAssets/Add.png"))); // NOI18N
        addButton.setBorderPainted(false);
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(qty, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addButton)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(qty)
                    .addComponent(addButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(61, Short.MAX_VALUE))
        );

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PhotoAssets/Current.png"))); // NOI18N

        javax.swing.GroupLayout shopLayoutLayout = new javax.swing.GroupLayout(shopLayout);
        shopLayout.setLayout(shopLayoutLayout);
        shopLayoutLayout.setHorizontalGroup(
            shopLayoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, shopLayoutLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(shopLayoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 953, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, shopLayoutLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(159, 159, 159))
        );
        shopLayoutLayout.setVerticalGroup(
            shopLayoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(shopLayoutLayout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainPanel.add(shopLayout, "card2");

        menuLayout.setBackground(new java.awt.Color(225, 225, 225));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PhotoAssets/Current.png"))); // NOI18N

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        omniTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(omniTable);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        ordersButton.setFont(new java.awt.Font("SF Pro Rounded", 0, 13)); // NOI18N
        ordersButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PhotoAssets/list.bullet.rectangle.portrait.fill@2x.png"))); // NOI18N
        ordersButton.setText("Orders");
        ordersButton.setBorderPainted(false);
        ordersButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ordersButtonActionPerformed(evt);
            }
        });

        inventoryButton.setFont(new java.awt.Font("SF Pro Rounded", 0, 13)); // NOI18N
        inventoryButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PhotoAssets/chart.bar.xaxis@2x.png"))); // NOI18N
        inventoryButton.setText("Inventory");
        inventoryButton.setBorderPainted(false);
        inventoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inventoryButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ordersButton, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inventoryButton, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ordersButton)
                    .addComponent(inventoryButton))
                .addGap(0, 90, Short.MAX_VALUE))
        );

        journalButton.setFont(new java.awt.Font("SF Pro Rounded", 0, 13)); // NOI18N
        journalButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PhotoAssets/book.closed.fill@2x.png"))); // NOI18N
        journalButton.setText("Transaction Journal");
        journalButton.setBorderPainted(false);
        journalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                journalButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(journalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(journalButton)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout menuLayoutLayout = new javax.swing.GroupLayout(menuLayout);
        menuLayout.setLayout(menuLayoutLayout);
        menuLayoutLayout.setHorizontalGroup(
            menuLayoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuLayoutLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        menuLayoutLayout.setVerticalGroup(
            menuLayoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuLayoutLayout.createSequentialGroup()
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainPanel.add(menuLayout, "card3");

        menuButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PhotoAssets/MenuBt.png"))); // NOI18N
        menuButton.setBorderPainted(false);
        menuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuButtonActionPerformed(evt);
            }
        });

        shopButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PhotoAssets/ShopBt.png"))); // NOI18N
        shopButton.setBorderPainted(false);
        shopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shopButtonActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PhotoAssets/Title.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(shopButton)
                        .addGap(18, 18, 18)
                        .addComponent(menuButton))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(jLabel2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(menuButton)
                        .addComponent(shopButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lowerHolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lowerHolderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lowerHolderActionPerformed

    private void payButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payButtonActionPerformed
        
        validatePurchase();

    }//GEN-LAST:event_payButtonActionPerformed

    private void menuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuButtonActionPerformed
       
        mainPanel.removeAll();
        mainPanel.add(menuLayout);
        mainPanel.repaint();
        mainPanel.revalidate();     

        
        
    }//GEN-LAST:event_menuButtonActionPerformed

    private void shopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shopButtonActionPerformed
        mainPanel.removeAll();
        mainPanel.add(shopLayout);
        mainPanel.repaint();
        mainPanel.revalidate();        
        
        
        
    }//GEN-LAST:event_shopButtonActionPerformed

    private void journalButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_journalButtonActionPerformed
        
        transactionsTableModel = new DefaultTableModel();
        transactionsTableModel.addColumn("Journal ID");
        transactionsTableModel.addColumn("Journal Description");
        transactionsTableModel.addColumn("Journal Amount");
        transactionsTableModel.addColumn("Journal Timestamp");
        
        omniTable.setModel(transactionsTableModel);

        refreshTransactionJournalTable(transactionsTableModel);
        
        
    }//GEN-LAST:event_journalButtonActionPerformed

    private void ordersButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ordersButtonActionPerformed
        
        ordersTableModel = new DefaultTableModel();
        ordersTableModel.addColumn("Order ID");
        ordersTableModel.addColumn("Order Date");
        ordersTableModel.addColumn("Order Time");
        ordersTableModel.addColumn("Customer ID");
        
        omniTable.setModel(ordersTableModel);

        refreshOrderTable(ordersTableModel);
        
        
    }//GEN-LAST:event_ordersButtonActionPerformed

    private void inventoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inventoryButtonActionPerformed
        
        
        
        inventoryTableModel = new DefaultTableModel();
        inventoryTableModel.addColumn("ID");
        inventoryTableModel.addColumn("Name");
        inventoryTableModel.addColumn("Description");
        inventoryTableModel.addColumn("Quantity Onhand");
        inventoryTableModel.addColumn("Reorder Amount");
        inventoryTableModel.addColumn("Reorder Price");

        omniTable.setModel(inventoryTableModel);

        refreshInventoryTable(inventoryTableModel);

    }//GEN-LAST:event_inventoryButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        updateSelectedBakeryItem();
       try{
            totalLabel.setText(MoneyHandler.getFormattedMoney(getQuantity() * selectedBakeryItem.price));
       }catch(Exception e){
            totalLabel.setText("$0.00");
            e.printStackTrace();

            
       }
    }//GEN-LAST:event_addButtonActionPerformed

    private void qtyMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_qtyMouseExited
        try{

            if(!qty.getText().equals(""))
                Integer.parseInt(qty.getText());

        }catch(Exception e){
            qty.setText("0");
        }
    }//GEN-LAST:event_qtyMouseExited

    private void qtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_qtyKeyReleased
        try{
        
            if(!qty.getText().equals(""))
                Integer.parseInt(qty.getText());

        }catch(Exception e){
            qty.setText("0");
        }
    }//GEN-LAST:event_qtyKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatLightLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JTable bakeryMenu;
    private javax.swing.JButton inventoryButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton journalButton;
    private javax.swing.JButton lowerHolder;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton menuButton;
    private javax.swing.JPanel menuLayout;
    private javax.swing.JTable omniTable;
    private javax.swing.JButton ordersButton;
    private javax.swing.JButton payButton;
    private javax.swing.JButton placeHolderLeft;
    private javax.swing.JButton placeHolderRight;
    private javax.swing.JTextField qty;
    private javax.swing.JButton shopButton;
    private javax.swing.JPanel shopLayout;
    private javax.swing.JLabel totalLabel;
    private javax.swing.JButton undoButton;
    private javax.swing.JButton voidButton;
    // End of variables declaration//GEN-END:variables
}
