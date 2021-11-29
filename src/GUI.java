
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.table.DefaultTableModel;
import ConnectionObjects.ConnectionObj;
import DataAccessObjects.*;
import java.util.List;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author justindang
 */
public class GUI extends javax.swing.JFrame {
    
    private DefaultTableModel bakeryTable;
    private DefaultTableModel ordersTableModel;
    private DefaultTableModel transactionsTableModel;
    private DefaultTableModel inventoryTableModel;
    BakeryItemsDAO itemsDAO = new BakeryItemsDAO();


    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();
        fillTables(); // This method fills every table with data
        ConnectionObj.closeConnection();
    }
    
    public void fillTables(){
        
        bakeryTable = new DefaultTableModel();
        bakeryTable.addColumn("ID");
        bakeryTable.addColumn("Name");
        bakeryTable.addColumn("Description");
        bakeryTable.addColumn("Price");
        bakeryMenu.setModel(bakeryTable);
        
        ordersTableModel = new DefaultTableModel();
        ordersTableModel.addColumn("Order ID");
        ordersTableModel.addColumn("Order Date");       
        ordersTableModel.addColumn("Order Time");
        ordersTableModel.addColumn("Customer ID");
        
        ordersTable.setModel(ordersTableModel);
        
        transactionsTableModel = new DefaultTableModel();
        transactionsTableModel.addColumn("Journal ID");
        transactionsTableModel.addColumn("Journal Description");
        transactionsTableModel.addColumn("Journal Amount");
        transactionsTableModel.addColumn("Journal Timestamp");
        
        transactionsTable.setModel(transactionsTableModel);

        inventoryTableModel = new DefaultTableModel();
        inventoryTableModel.addColumn("ID");
        inventoryTableModel.addColumn("Name");
        inventoryTableModel.addColumn("Description");
        inventoryTableModel.addColumn("Quantity Onhand");
        inventoryTableModel.addColumn("Reorder Amount");
        inventoryTableModel.addColumn("Reorder Price");

        inventoryTable.setModel(inventoryTableModel);
        
        refreshItems();
        
        
        
    }
    
    public void refreshItems(){
        bakeryTable.setNumRows(0);

        List<BakeryItems> myList = itemsDAO.getAllItemsFromDatabase();

        for (int i = 0; i < myList.size(); i++) {
            int id = myList.get(i).getID();
            String name = myList.get(i).getITEM_NAME();
            String description = myList.get(i).getDESCRIPTION();
            double price = myList.get(i).getPRICE();

            String[] row = new String[4];
            row[0] = id + "";
            row[1] = name;
            row[2] = description;
            row[3] = "$" + price + "0";

            bakeryTable.addRow(row);
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

        jPanel2 = new javax.swing.JPanel();
        payButton = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        shopButton = new javax.swing.JButton();
        viewButton = new javax.swing.JButton();
        mainPanel = new javax.swing.JPanel();
        shopPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        bakeryMenu = new javax.swing.JTable();
        qtyIn = new javax.swing.JTextField();
        addButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        viewPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ordersTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        transactionsTable = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        inventoryTable = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        showPrice = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        payButton.setIcon(new javax.swing.ImageIcon("/Users/justindang/Documents/CS/DATABASE SYSTEMS II/FINAL PROJECT/ASSETS/PayButton.png")); // NOI18N
        payButton.setBorderPainted(false);
        payButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                payButtonActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon("/Users/justindang/Documents/CS/DATABASE SYSTEMS II/FINAL PROJECT/ASSETS/slice7.png")); // NOI18N
        jButton2.setBorderPainted(false);

        jButton3.setIcon(new javax.swing.ImageIcon("/Users/justindang/Documents/CS/DATABASE SYSTEMS II/FINAL PROJECT/ASSETS/slice7.png")); // NOI18N
        jButton3.setBorderPainted(false);

        jButton4.setBorderPainted(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon("/Users/justindang/Documents/CS/DATABASE SYSTEMS II/FINAL PROJECT/ASSETS/slice7.png")); // NOI18N
        jButton5.setBorderPainted(false);

        shopButton.setIcon(new javax.swing.ImageIcon("/Users/justindang/Documents/CS/DATABASE SYSTEMS II/FINAL PROJECT/ASSETS/slice3.png")); // NOI18N
        shopButton.setBorderPainted(false);
        shopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shopButtonActionPerformed(evt);
            }
        });

        viewButton.setIcon(new javax.swing.ImageIcon("/Users/justindang/Documents/CS/DATABASE SYSTEMS II/FINAL PROJECT/ASSETS/slice1.png")); // NOI18N
        viewButton.setBorderPainted(false);
        viewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewButtonActionPerformed(evt);
            }
        });

        mainPanel.setLayout(new java.awt.CardLayout());

        shopPanel.setBackground(new java.awt.Color(225, 225, 225));

        jLabel4.setIcon(new javax.swing.ImageIcon("/Users/justindang/Documents/CS/DATABASE SYSTEMS II/FINAL PROJECT/ASSETS/slice4.png")); // NOI18N

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

        addButton.setBackground(new java.awt.Color(204, 204, 204));
        addButton.setIcon(new javax.swing.ImageIcon("/Users/justindang/Documents/CS/DATABASE SYSTEMS II/FINAL PROJECT/ASSETS/Add.png")); // NOI18N
        addButton.setBorderPainted(false);
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("SF Pro Display", 0, 18)); // NOI18N
        jLabel5.setText("Quantity:");

        javax.swing.GroupLayout shopPanelLayout = new javax.swing.GroupLayout(shopPanel);
        shopPanel.setLayout(shopPanelLayout);
        shopPanelLayout.setHorizontalGroup(
            shopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(shopPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(shopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(shopPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 899, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, shopPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(shopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, shopPanelLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(qtyIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(addButton)
                                .addGap(16, 16, 16))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, shopPanelLayout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(203, 203, 203))))))
        );
        shopPanelLayout.setVerticalGroup(
            shopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(shopPanelLayout.createSequentialGroup()
                .addComponent(jLabel4)
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addGroup(shopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addButton)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, shopPanelLayout.createSequentialGroup()
                        .addGroup(shopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(qtyIn, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(10, 10, 10)))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        mainPanel.add(shopPanel, "card2");

        viewPanel.setBackground(new java.awt.Color(225, 225, 225));

        jLabel3.setIcon(new javax.swing.ImageIcon("/Users/justindang/Documents/CS/DATABASE SYSTEMS II/FINAL PROJECT/ASSETS/slice4.png")); // NOI18N

        jTabbedPane1.setFont(new java.awt.Font("SF Pro Display", 0, 13)); // NOI18N

        ordersTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(ordersTable);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 887, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Orders", jPanel1);

        transactionsTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(transactionsTable);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 887, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Transaction Journal", jPanel3);

        inventoryTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(inventoryTable);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 887, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Inventory", jPanel4);

        javax.swing.GroupLayout viewPanelLayout = new javax.swing.GroupLayout(viewPanel);
        viewPanel.setLayout(viewPanelLayout);
        viewPanelLayout.setHorizontalGroup(
            viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        viewPanelLayout.setVerticalGroup(
            viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewPanelLayout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 587, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        mainPanel.add(viewPanel, "card2");

        jButton6.setIcon(new javax.swing.ImageIcon("/Users/justindang/Documents/CS/DATABASE SYSTEMS II/FINAL PROJECT/ASSETS/slice5.png")); // NOI18N
        jButton6.setBorderPainted(false);
        jButton6.setFocusPainted(false);

        jLabel2.setIcon(new javax.swing.ImageIcon("/Users/justindang/Documents/CS/DATABASE SYSTEMS II/FINAL PROJECT/ASSETS/slice2.png")); // NOI18N

        jLabel1.setFont(new java.awt.Font("SF Pro Rounded", 0, 14)); // NOI18N
        jLabel1.setText("Code MACKEY for 69% off");

        jPanel5.setBackground(new java.awt.Color(225, 225, 225));

        jLabel6.setBackground(new java.awt.Color(225, 225, 225));
        jLabel6.setFont(new java.awt.Font("SF Pro Rounded", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(112, 112, 112));
        jLabel6.setText("TOTAL:");

        showPrice.setFont(new java.awt.Font("SF Pro Rounded", 1, 48)); // NOI18N
        showPrice.setText("$0.00");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(110, Short.MAX_VALUE)
                .addComponent(showPrice)
                .addGap(29, 29, 29))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(showPrice)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jButton7.setIcon(new javax.swing.ImageIcon("/Users/justindang/Documents/CS/DATABASE SYSTEMS II/FINAL PROJECT/ASSETS/slice9.png")); // NOI18N
        jButton7.setBorderPainted(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 911, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(shopButton)
                        .addGap(18, 18, 18)
                        .addComponent(viewButton)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton6)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(payButton, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3)))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(143, 143, 143))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(shopButton)
                            .addComponent(viewButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(12, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton4)
                            .addComponent(jButton2)
                            .addComponent(jButton7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton3)
                            .addComponent(jButton5))
                        .addGap(12, 12, 12)
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(payButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1252, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void payButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payButtonActionPerformed
       
        // TODO: This is the checkout button. It will return a prompt whether or not the transaction succeded. 
       
    }//GEN-LAST:event_payButtonActionPerformed

    private void shopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shopButtonActionPerformed

        
        
        mainPanel.removeAll();
        mainPanel.add(shopPanel);
        mainPanel.repaint();
        mainPanel.revalidate();
        
        
        
    }//GEN-LAST:event_shopButtonActionPerformed

    private void viewButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewButtonActionPerformed
        
       mainPanel.removeAll();
        mainPanel.add(viewPanel);
        mainPanel.repaint();
        mainPanel.revalidate(); 
        
        
    }//GEN-LAST:event_viewButtonActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // TODO: Add to cart button. We ONLY have to have an order consist of only one item.
    }//GEN-LAST:event_addButtonActionPerformed

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
    private javax.swing.JTable inventoryTable;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTable ordersTable;
    private javax.swing.JButton payButton;
    private javax.swing.JTextField qtyIn;
    private javax.swing.JButton shopButton;
    private javax.swing.JPanel shopPanel;
    private javax.swing.JLabel showPrice;
    private javax.swing.JTable transactionsTable;
    private javax.swing.JButton viewButton;
    private javax.swing.JPanel viewPanel;
    // End of variables declaration//GEN-END:variables
}
