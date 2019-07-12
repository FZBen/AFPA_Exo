package Interface;

import Interface.NewAccountJFrame;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import pwdUtils.PwdUtils;
import xchange.Xchange;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author cda210
 */
public class LoginJDialog extends javax.swing.JDialog {

    /**
     * Creates new form LoginJDialog
     */
    public LoginJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titlejPanel = new javax.swing.JPanel();
        titlejLabel = new javax.swing.JLabel();
        loginjPanel = new javax.swing.JPanel();
        usernamejLabel = new javax.swing.JLabel();
        passwordjLabel = new javax.swing.JLabel();
        newAccountjLabel = new javax.swing.JLabel();
        usernamejTextField = new javax.swing.JTextField();
        connectjButton = new javax.swing.JButton();
        canceljButton = new javax.swing.JButton();
        jPasswordField = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("S'identifier");
        setResizable(false);

        titlejPanel.setBackground(new java.awt.Color(0, 102, 153));
        titlejPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(0, 102, 153)));

        titlejLabel.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        titlejLabel.setForeground(new java.awt.Color(255, 255, 255));
        titlejLabel.setText("MA LIBRAIRIE");

        javax.swing.GroupLayout titlejPanelLayout = new javax.swing.GroupLayout(titlejPanel);
        titlejPanel.setLayout(titlejPanelLayout);
        titlejPanelLayout.setHorizontalGroup(
            titlejPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlejPanelLayout.createSequentialGroup()
                .addGap(152, 152, 152)
                .addComponent(titlejLabel)
                .addContainerGap(152, Short.MAX_VALUE))
        );
        titlejPanelLayout.setVerticalGroup(
            titlejPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlejPanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(titlejLabel)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        usernamejLabel.setText("Nom d'utilisateur :");

        passwordjLabel.setText("Mot de passe :");

        newAccountjLabel.setText("Créer un nouveau compte utilisateur");
        newAccountjLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newAccountjLabelMouseClicked(evt);
            }
        });

        connectjButton.setText("Connexion");
        connectjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectjButtonActionPerformed(evt);
            }
        });

        canceljButton.setText("Quitter");
        canceljButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                canceljButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout loginjPanelLayout = new javax.swing.GroupLayout(loginjPanel);
        loginjPanel.setLayout(loginjPanelLayout);
        loginjPanelLayout.setHorizontalGroup(
            loginjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginjPanelLayout.createSequentialGroup()
                .addGroup(loginjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(newAccountjLabel)
                    .addGroup(loginjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(loginjPanelLayout.createSequentialGroup()
                            .addGap(47, 47, 47)
                            .addGroup(loginjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(usernamejLabel)
                                .addComponent(passwordjLabel))
                            .addGap(18, 18, 18)
                            .addGroup(loginjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(usernamejTextField)
                                .addComponent(jPasswordField, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loginjPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(connectjButton)
                            .addGap(18, 18, 18)
                            .addComponent(canceljButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        loginjPanelLayout.setVerticalGroup(
            loginjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginjPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(loginjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernamejLabel)
                    .addComponent(usernamejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(loginjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordjLabel)
                    .addComponent(jPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(newAccountjLabel)
                .addGap(18, 18, 18)
                .addGroup(loginjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(canceljButton)
                    .addComponent(connectjButton))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titlejPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(loginjPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titlejPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loginjPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void canceljButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_canceljButtonActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_canceljButtonActionPerformed

    private void connectjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectjButtonActionPerformed
        // TODO add your handling code here:
        Xchange xch = new Xchange();
        xch.connect();
        String salt = "OOJ8TJVQcDB/0Byx2PvZrVKCxJ4pdHJNzKeNnwrK";
        String pw = PwdUtils.hashPassword(String.valueOf(jPasswordField.getPassword()), salt).toString();
        System.out.println(pw);

        PreparedStatement ps;
        ResultSet rs;

        try {
            ps = xch.getConnexion().prepareStatement("SELECT * FROM EMPLOYES where EMPLOGIN = ? and EMPPASSWORD = ?");
            ps.setString(1, usernamejTextField.getText());
            ps.setString(2, pw);
            rs = ps.executeQuery();

            if (rs.next()) {
                //JOptionPane.showMessageDialog(null, "Connecté");
                HomePage home = new HomePage();
                home.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Le nom d'utilisateur ou le mot de passe est incorrect.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        xch.close();

    }//GEN-LAST:event_connectjButtonActionPerformed

    private void newAccountjLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newAccountjLabelMouseClicked
        // TODO add your handling code here:
        this.dispose();
        NewAccountJFrame najf = new NewAccountJFrame();
        najf.setVisible(true);
        najf.pack();
        najf.setLocationRelativeTo(null);
        najf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_newAccountjLabelMouseClicked

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
            java.util.logging.Logger.getLogger(LoginJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoginJDialog dialog = new LoginJDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton canceljButton;
    private javax.swing.JButton connectjButton;
    private javax.swing.JPasswordField jPasswordField;
    private javax.swing.JPanel loginjPanel;
    private javax.swing.JLabel newAccountjLabel;
    private javax.swing.JLabel passwordjLabel;
    private javax.swing.JLabel titlejLabel;
    private javax.swing.JPanel titlejPanel;
    private javax.swing.JLabel usernamejLabel;
    private javax.swing.JTextField usernamejTextField;
    // End of variables declaration//GEN-END:variables
}
