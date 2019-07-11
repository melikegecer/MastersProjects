package stringsattached;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.awt.Desktop;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Melike Geçer
 */
public class SAinterface extends javax.swing.JFrame {

    private PreparedStatement statement;

    private ArrayList<SAType> dbTypeList;
    private ArrayList<SAStringLiteral> dbSLList;

    private final String GET_TYPES_QUERY = "SELECT sl_type.type_id, sl_type.type_name FROM sl_type";
    private final String GET_SL_TYPES_QUERY = "SELECT sl_type.type_name, sl_type.type_id FROM sl_type JOIN sl_has_type ON sl_type.type_id = sl_has_type.type_id JOIN string_literal ON sl_has_type.sl_id = string_literal.sl_id WHERE string_literal.sl_id = ";
    private final String INSERT_SL_TYPE_QUERY = "INSERT INTO sl_type (type_id, type_name) VALUES (?,?)";
    private final String INSERT_SL_HAS_TYPE_QUERY = "INSERT INTO sl_has_type (sl_id, type_id) VALUES (?,?)";
    private final String DELETE_SL_HAS_TYPE_QUERY = "DELETE FROM sl_has_type WHERE sl_id = ? AND type_id = ?";

    private DefaultComboBoxModel comboBoxModel;
    private DefaultTableModel slTableModel;
    private DefaultListModel slTypeListModel;

    private int currentStringLiteralID;

    public SAinterface() {
        // start db connection
        SADBConnector.getInstance();

        initComponents();
        slTable.getTableHeader().setReorderingAllowed(false);

        // set type combo box 
        setTypesComboBox();

        // set table
        setStringLiteralTable();
    }

    private void setStringLiteralTable() {
        dbSLList = new ArrayList<>();
        slTableModel = new DefaultTableModel();
        int minInterval = (Integer) minValSpinner.getValue();
        int maxInterval = (Integer) numValSpinner.getValue();
        try {
            String q = "SELECT selected_rows.sl_id, selected_rows.line_number, selected_rows.content, selected_rows.file_id, sl_file.file_name, sl_file.file_path "
                    + "FROM "
                    + "(SELECT * FROM string_literal LIMIT " + minInterval + "," + maxInterval + " ) as selected_rows "
                    + "JOIN sl_file ON selected_rows.file_id = sl_file.file_id";
            statement = SADBConnector.getConnection().prepareStatement(q);
            ResultSet rs = statement.executeQuery(q);
            while (rs.next()) {
                int slId = rs.getInt("selected_rows.sl_id");
                int slLineNumber = rs.getInt("selected_rows.line_number");
                String slContent = rs.getString("selected_rows.content");
                int slFileId = rs.getInt("selected_rows.file_id");
                String slFileName = rs.getString("sl_file.file_name");
                String slFilePath = rs.getString("sl_file.file_path");
                dbSLList.add(new SAStringLiteral(slId, slLineNumber, slContent, slFileId, slFileName, slFilePath));
            }

            Object[] columnsName = new Object[4];
            columnsName[0] = "SL ID";
            columnsName[1] = "Line";
            columnsName[2] = "Content";
            columnsName[3] = "File Path";

            slTableModel.setColumnIdentifiers(columnsName);

            Object[] rowData = new Object[4];

            for (SAStringLiteral aSL : dbSLList) {
                rowData[0] = aSL.getStringLiteralId();
                rowData[1] = aSL.getLineNumber();
                rowData[2] = aSL.getContent();
                rowData[3] = aSL.getFilePath();
                slTableModel.addRow(rowData);
            }

            slTable.setModel(slTableModel);
        } catch (SQLException ex) {
            Logger.getLogger(SAinterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setTypesComboBox() {
        dbTypeList = new ArrayList<>();
        comboBoxModel = new DefaultComboBoxModel();
        comboBoxModel.addElement("Choose Type");

        try {
            //
            statement = SADBConnector.getConnection().prepareStatement(GET_TYPES_QUERY);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                dbTypeList.add(new SAType(rs.getInt("sl_type.type_id"), rs.getString("sl_type.type_name")));
                comboBoxModel.addElement(rs.getString("sl_type.type_name"));
            }
            //
        } catch (SQLException ex) {
            Logger.getLogger(SAinterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        typeComboBox.setModel(comboBoxModel);
    }

    private void setTypesList() {
        try {
            slTypeListModel = new DefaultListModel();
            statement = SADBConnector.getConnection().prepareStatement(GET_SL_TYPES_QUERY + currentStringLiteralID + "");
            ResultSet rs = statement.executeQuery(GET_SL_TYPES_QUERY + currentStringLiteralID + "");

            while (rs.next()) {
                slTypeListModel.addElement(rs.getString("sl_type.type_name"));
            }

            typeList.setModel(slTypeListModel);
        } catch (SQLException ex) {
            Logger.getLogger(SAinterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        minValSpinner = new javax.swing.JSpinner();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        numValSpinner = new javax.swing.JSpinner();
        jPanel5 = new javax.swing.JPanel();
        retrieveButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        typeList = new javax.swing.JList<>();
        jPanel8 = new javax.swing.JPanel();
        typeComboBox = new javax.swing.JComboBox<>();
        newComboTypeField = new javax.swing.JTextField();
        insertButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        slTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 2));

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));

        jLabel1.setText("From");
        jPanel3.add(jLabel1);

        minValSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 100));
        jPanel3.add(minValSpinner);

        jPanel6.add(jPanel3);

        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));

        jLabel2.setText("Num ");
        jPanel4.add(jLabel2);

        numValSpinner.setModel(new javax.swing.SpinnerNumberModel(100, 100, null, 100));
        jPanel4.add(numValSpinner);

        jPanel6.add(jPanel4);

        jPanel5.setLayout(new java.awt.GridLayout(1, 0));

        retrieveButton.setText("RETRIEVE");
        retrieveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retrieveButtonActionPerformed(evt);
            }
        });
        jPanel5.add(retrieveButton);

        jPanel6.add(jPanel5);

        jPanel1.add(jPanel6, java.awt.BorderLayout.NORTH);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.PAGE_AXIS));

        jLabel3.setText("Types");
        jPanel7.add(jLabel3);

        typeList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                typeListMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(typeList);

        jPanel7.add(jScrollPane2);

        jPanel2.add(jPanel7, java.awt.BorderLayout.NORTH);

        jPanel8.setLayout(new java.awt.GridLayout(3, 1));

        typeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose Type" }));
        typeComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                typeComboBoxItemStateChanged(evt);
            }
        });
        jPanel8.add(typeComboBox);
        jPanel8.add(newComboTypeField);

        insertButton.setText("INSERT");
        insertButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertButtonActionPerformed(evt);
            }
        });
        jPanel8.add(insertButton);

        jPanel2.add(jPanel8, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(jPanel1);

        slTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        slTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        slTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                slTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(slTable);

        jSplitPane1.setRightComponent(jScrollPane1);

        getContentPane().add(jSplitPane1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void retrieveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retrieveButtonActionPerformed
        setStringLiteralTable();
    }//GEN-LAST:event_retrieveButtonActionPerformed

    private void slTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_slTableMouseClicked
        try {
            JTable source = (JTable) evt.getSource();
            int row = source.rowAtPoint(evt.getPoint());
            currentStringLiteralID = (Integer) source.getModel().getValueAt(row, 0);
            String path = String.valueOf(source.getModel().getValueAt(row, 3));
            setTypesList();
            Desktop.getDesktop().open(new File(path));
        } catch (IOException ex) {
            Logger.getLogger(SAinterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_slTableMouseClicked

    private void insertButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertButtonActionPerformed
        try {
            String newType = newComboTypeField.getText();
            int newTypeID = newType.hashCode();
            statement = SADBConnector.getConnection().prepareStatement(INSERT_SL_TYPE_QUERY);
            statement.setInt(1, newTypeID);
            statement.setString(2, newType);
            statement.executeUpdate();
            setTypesComboBox();
            newComboTypeField.setText("");
        } catch (SQLException ex) {
            Logger.getLogger(SAinterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_insertButtonActionPerformed

    private void typeComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_typeComboBoxItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String selectedType = String.valueOf(evt.getItem());
            if (!selectedType.equals("Choose Type")) {
                for (SAType sAType : dbTypeList) {
                    if (!selectedType.equals(sAType)) {
                        try {
                            statement = SADBConnector.getConnection().prepareStatement(INSERT_SL_HAS_TYPE_QUERY);
                            statement.setInt(1, currentStringLiteralID);
                            statement.setInt(2, selectedType.hashCode());
                            statement.executeUpdate();
                            setTypesComboBox();
                            newComboTypeField.setText("");
                        } catch (MySQLIntegrityConstraintViolationException ex) {
                            Logger.getLogger(SAinterface.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(SAinterface.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
        setTypesList();
    }//GEN-LAST:event_typeComboBoxItemStateChanged

    private void typeListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_typeListMouseClicked
        Object sel = null;
        int selectedIx = typeList.getSelectedIndex();
        sel = typeList.getModel().getElementAt(selectedIx);

        if (JOptionPane.showConfirmDialog(null, "Do you want to remove " + sel.toString() + "?", "Remove Element",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                statement = SADBConnector.getConnection().prepareStatement(DELETE_SL_HAS_TYPE_QUERY);
                int stringLiteralID = currentStringLiteralID;
                int typeID = sel.toString().hashCode();
                statement.setInt(1, stringLiteralID);
                statement.setInt(2, typeID);
                statement.executeUpdate();
                setTypesList();
            } catch (SQLException ex) {
                Logger.getLogger(SAinterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_typeListMouseClicked

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SAinterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SAinterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SAinterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SAinterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SAinterface().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton insertButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSpinner minValSpinner;
    private javax.swing.JTextField newComboTypeField;
    private javax.swing.JSpinner numValSpinner;
    private javax.swing.JButton retrieveButton;
    private javax.swing.JTable slTable;
    private javax.swing.JComboBox<String> typeComboBox;
    private javax.swing.JList<String> typeList;
    // End of variables declaration//GEN-END:variables

}
