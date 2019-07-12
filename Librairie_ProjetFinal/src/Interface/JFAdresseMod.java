/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import ObjetsSimples.*;
import exceptions.DataExceptions;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import xchange.Xchange;

/**
 *
 * @author cda219
 */
public class JFAdresseMod extends javax.swing.JFrame {

    private JFGestionClient JFGC;
    private Client client;
    private Adresse adresse;
    private String fonction;

    public JFAdresseMod() {
        initComponents();

    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public JFGestionClient getJFGC() {
        return JFGC;
    }

    public void setJFGC(JFGestionClient JFGC) {
        this.JFGC = JFGC;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public void textFill() {
        if (client != null) {
            //cbAdrFactModif.setModel(initAdrFact(client.getId()));
            if (this.fonction.equals("new")) {
                jcbAdrFactModif.setVisible(false);
                jcbPays.setModel(initPays());
                for (int i = 0; i < jcbPays.getItemCount(); i++) {
                    Pays pays = (Pays) jcbPays.getItemAt(i);
                    if (pays.getCountryID().equals("74")) {
                        jcbPays.setSelectedIndex(i);
                    }
                }

            }

        } else {
            //System.out.println("Client = " + client);
        }

        if (fonction.equals("mod")) {
            jtAdrDesignation.setVisible(true);
            jcbAdrFactModif.setVisible(true);
            jcbAdrFactModif.setModel(initAdrFact(client.getId()));

            this.adresse = (Adresse) jcbAdrFactModif.getItemAt(0);
            if (adresse.getCustomer().getId() != null) {
                if (adresse.getCustomer().getId().equals(client.getId())) {
                    jrbLiv.setSelected(true);
                } else {
                    jrbLiv.setSelected(false);
                }
            } else {
                jrbLiv.setSelected(false);
            }
            if (adresse.getCli_Customer().getId() != null) {
                System.out.println("client Fact : " + adresse.getCli_Customer());
                if (adresse.getCli_Customer().getId().equals(client.getId())) {
                    jrbFact.setSelected(true);
                } else {
                    jrbFact.setSelected(false);
                }
            } else {
                jrbFact.setSelected(false);
            }
            jtAdrNum.setText(adresse.getNumber());
            jtAdrTypeVoi.setText(adresse.getStreetType());
            jtAdrNomVoi.setText(adresse.getStreetName());
            jtfAdrCP.setText(adresse.getPostalCode());
            jtfAdrVille.setText(adresse.getCity());
            jcbPays.setModel(initPays());
            for (int i = 0; i < jcbPays.getItemCount(); i++) {
                Pays pays = (Pays) jcbPays.getItemAt(i);
                if (pays.getCountryID().equals(adresse.getCountry().getCountryID())) {
                    jcbPays.setSelectedIndex(i);
                }
            }
        }

    }

    private DefaultComboBoxModel initPays() {
        Vector v = new Vector();

        Xchange ech = new Xchange();
        Statement stat = null;
        String query = "SELECT COUNTRYID, Pays FROM Pays";
        ResultSet rs = null;

        try {

            ech.connect();
            stat = ech.getConnexion().createStatement();
            rs = stat.executeQuery(query);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Création connexion Pays: " + ex.getErrorCode() + " / " + ex.getMessage(), "AdresseMod", JOptionPane.ERROR_MESSAGE);;
        }
        try {
            while (rs.next()) {

                Pays pa = new Pays() {
                    public String toString() {
                        return getName();
                    }
                };
                pa.setCountryID(rs.getString("COUNTRYID"));
                pa.setName(rs.getString("Pays"));

                v.add(pa);

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lecture données Pays : " + ex.getErrorCode() + " / " + ex.getMessage(), "AdresseMod", JOptionPane.ERROR_MESSAGE);
        } catch (DataExceptions ex) {
            JOptionPane.showMessageDialog(null, "Lecture données Pays: " + ex.getErrorCode() + " / " + ex.getMessage(), "AdresseMod", JOptionPane.ERROR_MESSAGE);
        } finally {
            ech.close();
        }

        return new DefaultComboBoxModel(v);
    }

    private DefaultComboBoxModel initAdrFact(String id) {
        Vector v = new Vector();

        Xchange ech = new Xchange();
        Statement stat = null;
        String query = "  SELECT CUSTOMERID, CLI_CUSTOMERID, ADDRESSID, ADDRESSNAME, ADDRESSNUMBER, ADDRESSSTREETTYPE, ADDRESSSTREETNAME, ADDRESSPOSTALCODE, ADDRESSCITY, ADDRESSMORE, ad.COUNTRYID, pa.Pays FROM ADRESSE ad JOIN PAYS pa ON ad.COUNTRYID = pa.COUNTRYID WHERE (CLI_CUSTOMERID = " + id + " OR CUSTOMERID = " + id + ") AND ADDRESSACTIVE = 1";
        ResultSet rs = null;

        try {

            ech.connect();
            stat = ech.getConnexion().createStatement();
            rs = stat.executeQuery(query);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Création connexion AdrExp: " + ex.getErrorCode() + " / " + ex.getMessage(), "CommandeMod", JOptionPane.ERROR_MESSAGE);;
        }
        try {
            while (rs.next()) {
                Adresse adr = new Adresse();
                adr.setAddrID(rs.getString("ADDRESSID"));
                Client clL = new Client();
                clL.setID(rs.getString("CUSTOMERID"));
                Client clF = new Client();
                clF.setID(rs.getString("CLI_CUSTOMERID"));
                adr.setCustomer(clL);
                adr.setCli_Customer(clF);
                adr.setName(rs.getString("ADDRESSNAME"));
                adr.setNumber(rs.getString("ADDRESSNUMBER"));
                adr.setStreetType(rs.getString("ADDRESSSTREETTYPE"));
                adr.setStreetName(rs.getString("ADDRESSSTREETNAME"));
                adr.setPostalCode(rs.getString("ADDRESSPOSTALCODE"));
                adr.setCity(rs.getString("ADDRESSCITY"));
                Pays pa = new Pays();
                pa.setCountryID(rs.getString("COUNTRYID"));
                pa.setName(rs.getString("Pays"));
                adr.setCountry(pa);

                v.add(adr);

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lecture données Client : " + ex.getErrorCode() + " / " + ex.getMessage(), "CommandeMod", JOptionPane.ERROR_MESSAGE);
        } catch (DataExceptions ex) {
            JOptionPane.showMessageDialog(null, "Lecture données Client : " + ex.getErrorCode() + " / " + ex.getMessage(), "CommandeMod", JOptionPane.ERROR_MESSAGE);
        } finally {
            ech.close();
        }

        return new DefaultComboBoxModel(v);

    }

    private DefaultComboBoxModel initAdrExp(String id) {
        Vector v = new Vector();

        Xchange ech = new Xchange();
        Statement stat = null;
        String query = "  SELECT ADDRESSID, ADDRESSNAME, ADDRESSNUMBER, ADDRESSSTREETTYPE, ADDRESSSTREETNAME, ADDRESSPOSTALCODE, ADDRESSCITY, ADDRESSMORE, ad.COUNTRYID, pa.Pays FROM ADRESSE ad JOIN PAYS pa ON ad.COUNTRYID = pa.COUNTRYID WHERE CUSTOMERID = " + id;
        ResultSet rs = null;

        try {

            ech.connect();
            stat = ech.getConnexion().createStatement();
            rs = stat.executeQuery(query);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Création connexion AdrExp: " + ex.getErrorCode() + " / " + ex.getMessage(), "CommandeMod", JOptionPane.ERROR_MESSAGE);;
        }
        try {
            while (rs.next()) {
                Adresse adr = new Adresse();
                adr.setAddrID(rs.getString("ADDRESSID"));
                adr.setName(rs.getString("ADDRESSNAME"));
                adr.setNumber(rs.getString("ADDRESSNUMBER"));
                adr.setStreetType(rs.getString("ADDRESSSTREETTYPE"));
                adr.setStreetName(rs.getString("ADDRESSSTREETNAME"));
                adr.setPostalCode(rs.getString("ADDRESSPOSTALCODE"));
                adr.setCity(rs.getString("ADDRESSCITY"));
                Pays pa = new Pays();
                pa.setCountryID(rs.getString("COUNTRYID"));
                pa.setName(rs.getString("Pays"));
                adr.setCountry(pa);

                v.add(adr);

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lecture données Client : " + ex.getErrorCode() + " / " + ex.getMessage(), "CommandeMod", JOptionPane.ERROR_MESSAGE);
        } catch (DataExceptions ex) {
            JOptionPane.showMessageDialog(null, "Lecture données Client : " + ex.getErrorCode() + " / " + ex.getMessage(), "CommandeMod", JOptionPane.ERROR_MESSAGE);
        } finally {
            ech.close();
        }

        return new DefaultComboBoxModel(v);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpAdresseModif = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jtAdrDesignation = new javax.swing.JTextField();
        jtAdrNum = new javax.swing.JTextField();
        jtAdrTypeVoi = new javax.swing.JTextField();
        jtAdrNomVoi = new javax.swing.JTextField();
        jtfAdrCP = new javax.swing.JTextField();
        jtfAdrVille = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jcbAdrFactModif = new javax.swing.JComboBox();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jrbLiv = new javax.swing.JRadioButton();
        jrbFact = new javax.swing.JRadioButton();
        jcbPays = new javax.swing.JComboBox();
        jlPays = new javax.swing.JLabel();
        jbEnregistrer = new javax.swing.JButton();
        jbFermer = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jpAdresseModif.setBackground(new java.awt.Color(204, 204, 204));
        jpAdresseModif.setForeground(new java.awt.Color(255, 255, 255));

        jLabel4.setText("Designation");

        jLabel5.setText("Numéro");

        jLabel6.setText("Type voie");

        jLabel7.setText("Nom voie");

        jLabel8.setText("Code Postal");

        jLabel9.setText("Commune");

        jtAdrDesignation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtAdrDesignationActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Adresse");

        jcbAdrFactModif.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcbAdrFactModif.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbAdrFactModifItemStateChanged(evt);
            }
        });
        jcbAdrFactModif.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jcbAdrFactModifInputMethodTextChanged(evt);
            }
        });
        jcbAdrFactModif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbAdrFactModifActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(102, 102, 102));
        jLabel18.setText("Facturation");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(102, 102, 102));
        jLabel19.setText("Livraison");

        jrbLiv.setText("jRadioButton1");

        jrbFact.setText("jRadioButton2");

        jcbPays.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jlPays.setText("Pays");

        javax.swing.GroupLayout jpAdresseModifLayout = new javax.swing.GroupLayout(jpAdresseModif);
        jpAdresseModif.setLayout(jpAdresseModifLayout);
        jpAdresseModifLayout.setHorizontalGroup(
            jpAdresseModifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpAdresseModifLayout.createSequentialGroup()
                .addGap(190, 190, 190)
                .addComponent(jrbFact, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpAdresseModifLayout.createSequentialGroup()
                .addGroup(jpAdresseModifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpAdresseModifLayout.createSequentialGroup()
                        .addGroup(jpAdresseModifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpAdresseModifLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jpAdresseModifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(102, 102, 102))
                            .addGroup(jpAdresseModifLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jpAdresseModifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jpAdresseModifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jpAdresseModifLayout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel19))
                            .addComponent(jtAdrDesignation)
                            .addComponent(jtAdrNum)
                            .addComponent(jcbAdrFactModif, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpAdresseModifLayout.createSequentialGroup()
                                .addComponent(jrbLiv, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20))
                            .addComponent(jtAdrTypeVoi, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtAdrNomVoi, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfAdrCP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jpAdresseModifLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jpAdresseModifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jlPays))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jpAdresseModifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jcbPays, 0, 192, Short.MAX_VALUE)
                            .addComponent(jtfAdrVille))))
                .addGap(213, 213, 213))
        );
        jpAdresseModifLayout.setVerticalGroup(
            jpAdresseModifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpAdresseModifLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jpAdresseModifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpAdresseModifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jrbFact)
                    .addComponent(jrbLiv))
                .addGap(6, 6, 6)
                .addGroup(jpAdresseModifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpAdresseModifLayout.createSequentialGroup()
                        .addGroup(jpAdresseModifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addGroup(jpAdresseModifLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(50, 50, 50)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel7))
                    .addGroup(jpAdresseModifLayout.createSequentialGroup()
                        .addComponent(jcbAdrFactModif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtAdrDesignation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jtAdrNum, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtAdrTypeVoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtAdrNomVoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addGroup(jpAdresseModifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtfAdrCP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpAdresseModifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfAdrVille, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jpAdresseModifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbPays, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlPays))
                .addContainerGap())
        );

        jbEnregistrer.setText("Enregistrer");
        jbEnregistrer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEnregistrerActionPerformed(evt);
            }
        });

        jbFermer.setText("Fermer");
        jbFermer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbFermerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jbEnregistrer)
                .addGap(156, 156, 156)
                .addComponent(jbFermer)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jpAdresseModif, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpAdresseModif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbEnregistrer)
                    .addComponent(jbFermer))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jcbAdrFactModifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbAdrFactModifActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbAdrFactModifActionPerformed

    private void jcbAdrFactModifInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jcbAdrFactModifInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbAdrFactModifInputMethodTextChanged

    private void jcbAdrFactModifItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbAdrFactModifItemStateChanged
        // TODO add your handling code here:
        Adresse adrFactModif = (Adresse) jcbAdrFactModif.getSelectedItem();

        if (adrFactModif != null) {

            if (adrFactModif.getCustomer().getId() != null) {
                if (adrFactModif.getCustomer().getId().equals(client.getId())) {
                    jrbLiv.setSelected(true);
                } else {
                    jrbLiv.setSelected(false);
                }
            } else {
                jrbLiv.setSelected(false);
            }
            if (adrFactModif.getCli_Customer().getId() != null) {
                System.out.println("client Fact : " + adrFactModif.getCli_Customer());
                if (adrFactModif.getCli_Customer().getId().equals(client.getId())) {
                    jrbFact.setSelected(true);
                } else {
                    jrbFact.setSelected(false);
                }
            } else {
                jrbFact.setSelected(false);
            }
            jtAdrDesignation.setText(adrFactModif.getName());
            jtAdrNum.setText(adrFactModif.getNumber());
            jtAdrTypeVoi.setText(adrFactModif.getStreetType());
            jtAdrNomVoi.setText(adrFactModif.getStreetName());
            jtfAdrCP.setText(adrFactModif.getPostalCode());
            jtfAdrVille.setText(adrFactModif.getCity());
        }
    }//GEN-LAST:event_jcbAdrFactModifItemStateChanged

    private void jtAdrDesignationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtAdrDesignationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtAdrDesignationActionPerformed

    private void jbEnregistrerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEnregistrerActionPerformed
        Adresse adr = new Adresse();
        if (this.fonction.equals("mod")) {
            this.adresse = (Adresse) jcbAdrFactModif.getSelectedItem();
        }

        if (jrbFact.isSelected()) {
            adr.setCustomer(client);
        }
        if (jrbLiv.isSelected()) {
            adr.setCli_Customer(client);
        }

        if (!jrbFact.isSelected() && !jrbLiv.isSelected()) {
            JOptionPane.showMessageDialog(null, "Veuiller choissir au moins un type d'Adresse", "AdresseMod", JOptionPane.ERROR_MESSAGE);
        } else {
            int error = 0;
            try {

                adr.setName(jtAdrDesignation.getText());

            } catch (DataExceptions ex) {
                JOptionPane.showMessageDialog(null, "Sauvegarde Adresse: " + ex.getErrorCode() + " / " + ex.getMessage(), "AdresseMod", JOptionPane.ERROR_MESSAGE);
                error += 1;
            }

            try {
                adr.setNumber(jtAdrNum.getText());
            } catch (DataExceptions ex) {
                JOptionPane.showMessageDialog(null, "Sauvegarde Adresse: " + ex.getErrorCode() + " / " + ex.getMessage(), "AdresseMod", JOptionPane.ERROR_MESSAGE);;
                error += 1;
            }
            try {
                adr.setStreetType(jtAdrTypeVoi.getText());
            } catch (DataExceptions ex) {
                JOptionPane.showMessageDialog(null, "Sauvegarde Adresse: " + ex.getErrorCode() + " / " + ex.getMessage(), "AdresseMod", JOptionPane.ERROR_MESSAGE);;
                error += 1;
            }
            try {
                adr.setStreetName(jtAdrNomVoi.getText());
            } catch (DataExceptions ex) {
                JOptionPane.showMessageDialog(null, "Sauvegarde Adresse: " + ex.getErrorCode() + " / " + ex.getMessage(), "AdresseMod", JOptionPane.ERROR_MESSAGE);;
                error += 1;
            }
            try {
                adr.setPostalCode(jtfAdrCP.getText());
            } catch (DataExceptions ex) {
                JOptionPane.showMessageDialog(null, "Sauvegarde Adresse: " + ex.getErrorCode() + " / " + ex.getMessage(), "AdresseMod", JOptionPane.ERROR_MESSAGE);;
                error += 1;
            }
            try {
                adr.setCity(jtfAdrVille.getText());
            } catch (DataExceptions ex) {
                JOptionPane.showMessageDialog(null, "Sauvegarde Adresse: " + ex.getErrorCode() + " / " + ex.getMessage(), "AdresseMod", JOptionPane.ERROR_MESSAGE);;
                error += 1;
            }
            Pays pays = (Pays) jcbPays.getSelectedItem();
            adr.setCountry(pays);
            if (error == 0) {
                Xchange ech = new Xchange();
                ech.connect();
                if (this.fonction.equals("mod")) {
                    adresse.save(ech, adresse.genValue());
                }

                adr.save(ech, adr.genValue());
                ech.close();

            }
            
            jcbAdrFactModif.removeAllItems();
            jcbAdrFactModif.setModel(initAdrFact(adresse.getCustomer().getId()));
            
        }

    }//GEN-LAST:event_jbEnregistrerActionPerformed

    private void jbFermerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbFermerActionPerformed
        this.dispose();
    }//GEN-LAST:event_jbFermerActionPerformed

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
            java.util.logging.Logger.getLogger(JFAdresseMod.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFAdresseMod.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFAdresseMod.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFAdresseMod.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFAdresseMod().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JButton jbEnregistrer;
    private javax.swing.JButton jbFermer;
    private javax.swing.JComboBox jcbAdrFactModif;
    private javax.swing.JComboBox jcbPays;
    private javax.swing.JLabel jlPays;
    private javax.swing.JPanel jpAdresseModif;
    private javax.swing.JRadioButton jrbFact;
    private javax.swing.JRadioButton jrbLiv;
    private javax.swing.JTextField jtAdrDesignation;
    private javax.swing.JTextField jtAdrNomVoi;
    private javax.swing.JTextField jtAdrNum;
    private javax.swing.JTextField jtAdrTypeVoi;
    private javax.swing.JTextField jtfAdrCP;
    private javax.swing.JTextField jtfAdrVille;
    // End of variables declaration//GEN-END:variables
}
