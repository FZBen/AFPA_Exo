    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import ObjetsSimples.Statut;
import exceptions.DataExceptions;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import xchange.Xchange;

/**
 *
 * @author cda210
 */
public class DisplayAuteurJFrame extends javax.swing.JFrame {

    /**
     * Creates new form DisplayAuteurJFrame
     */
    public DisplayAuteurJFrame() {
        initComponents();
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
        StatusjComboBox = new javax.swing.JComboBox();
        lastNamejLabel = new javax.swing.JLabel();
        firstNamejLabel = new javax.swing.JLabel();
        biojLabel = new javax.swing.JLabel();
        picturejLabel = new javax.swing.JLabel();
        notesjLabel = new javax.swing.JLabel();
        lastNamejTextField = new javax.swing.JTextField();
        firstNamejTextField = new javax.swing.JTextField();
        biojScrollPane = new javax.swing.JScrollPane();
        biojTextArea = new javax.swing.JTextArea();
        notesjScrollPane = new javax.swing.JScrollPane();
        notesjTextArea = new javax.swing.JTextArea();
        imagejLabel = new javax.swing.JLabel();
        canceljButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Afficher auteur");

        jPanel1.setToolTipText("");

        StatusjComboBox.setModel(initModelStatus());

        lastNamejLabel.setText("Nom :");

        firstNamejLabel.setText("Prénom :");

        biojLabel.setText("Biographie :");

        picturejLabel.setText("Image :");

        notesjLabel.setText("Commentaires :");

        lastNamejTextField.setEditable(false);

        firstNamejTextField.setEditable(false);

        biojTextArea.setEditable(false);
        biojTextArea.setColumns(20);
        biojTextArea.setLineWrap(true);
        biojTextArea.setRows(5);
        biojTextArea.setWrapStyleWord(true);
        biojScrollPane.setViewportView(biojTextArea);

        notesjTextArea.setEditable(false);
        notesjTextArea.setColumns(20);
        notesjTextArea.setLineWrap(true);
        notesjTextArea.setRows(5);
        notesjTextArea.setWrapStyleWord(true);
        notesjScrollPane.setViewportView(notesjTextArea);

        imagejLabel.setOpaque(true);

        canceljButton.setText("Annuler");
        canceljButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                canceljButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(canceljButton)
                        .addComponent(StatusjComboBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lastNamejLabel)
                                .addComponent(firstNamejLabel)
                                .addComponent(biojLabel))
                            .addGap(41, 41, 41)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lastNamejTextField)
                                .addComponent(firstNamejTextField)
                                .addComponent(biojScrollPane))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(notesjLabel)
                            .addComponent(picturejLabel))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(notesjScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(imagejLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(StatusjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lastNamejLabel)
                    .addComponent(lastNamejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstNamejLabel)
                    .addComponent(firstNamejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(biojLabel)
                    .addComponent(biojScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(notesjScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(notesjLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(picturejLabel)
                    .addComponent(imagejLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(canceljButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void canceljButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_canceljButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_canceljButtonActionPerformed
    
    private DefaultComboBoxModel initModelStatus(){
        return new DefaultComboBoxModel(initVectorStatus());
    }
    
    private Vector initVectorStatus(){
        Vector v = new Vector();
        Xchange xch = new Xchange();
        xch.connect();
               
        try {
            String query = "SELECT STATUSID, STATUSNAME, STATUS_DESCR FROM STATUS WHERE STATUSID BETWEEN 500 AND 598";
            Statement s = xch.getConnexion().createStatement();
            ResultSet rs = s.executeQuery(query);
            
            while (rs.next()){
                Statut status = new Statut();
                try {
                    status.setId(rs.getString("STATUSID"));
                    status.setStatusname(rs.getString("STATUSNAME"));
                    status.setStatusdescr(rs.getString("STATUS_DESCR"));
                } catch (DataExceptions ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }                
                v.add(status);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        xch.close();
        return v;
    }
    
    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(DisplayAuteurJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DisplayAuteurJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DisplayAuteurJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DisplayAuteurJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DisplayAuteurJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JComboBox StatusjComboBox;
    private javax.swing.JLabel biojLabel;
    private javax.swing.JScrollPane biojScrollPane;
    public javax.swing.JTextArea biojTextArea;
    private javax.swing.JButton canceljButton;
    private javax.swing.JLabel firstNamejLabel;
    public javax.swing.JTextField firstNamejTextField;
    public javax.swing.JLabel imagejLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lastNamejLabel;
    public javax.swing.JTextField lastNamejTextField;
    private javax.swing.JLabel notesjLabel;
    private javax.swing.JScrollPane notesjScrollPane;
    public javax.swing.JTextArea notesjTextArea;
    private javax.swing.JLabel picturejLabel;
    // End of variables declaration//GEN-END:variables
}
