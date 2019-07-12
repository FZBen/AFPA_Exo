package Interface;

import ObjetsSimples.*;
import ObjetsSimples.Client;
import ObjetsSimples.Statut;
import exceptions.DataExceptions;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import xchange.Xchange;

public class JFGestionClient extends javax.swing.JFrame {

    private Client client;
    private HomePage JFCli;
    //private Vector rubrique; 
    private JButton button;
    private Adresse adr;

    public JFGestionClient() {
        initComponents();

        //this.client = new Client();
        //this.JFCli = new JFClients(); 
    }

//    public Vector getRubrique() {
//        return rubrique;
//    }
//
//    public void setRubrique(Vector rubrique) {
//        this.rubrique = rubrique;
//    }
    public JPanel getJpAfficher() {
        return jpAfficher;
    }

    public void setJpAfficher(JPanel jpAfficher) {
        this.jpAfficher = jpAfficher;
    }

    public JPanel getJpCreer() {
        return jpCreer;
    }

    public void setJpCreer(JPanel jpCreer) {
        this.jpCreer = jpCreer;
    }

    public JTabbedPane getjTabbedPane1() {
        return jTabbedPane1;
    }

    public JPanel getJpModifier() {
        return jpModifier;
    }

    public void setJpModifier(JPanel jpModifier) {
        this.jpModifier = jpModifier;
    }

//    public JPanel getJpSupp() {
//        return jpSupp;
//    }
//
//    public void setJpSupp(JPanel jpSupp) {
//        this.jpSupp = jpSupp;
//    }
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public HomePage getJFCli() {
        return JFCli;
    }

    public void setJFCli(HomePage JFCli) {
        this.JFCli = JFCli;
    }

    public JButton getButton() {
        return button;
    }

    public void setButton(JButton button) {
        this.button = button;
    }

    public void masquerJButton() {
        button.setVisible(false);
    }

    public static void afficherFenetre() {
        // creer methode pour contextualiser l'affichage de la fenetre 
        // en fonction du boutton source
    }

    public void textFill() {

        // préremplir les champs dans Afficher 
        jtfID.setText(client.getId());
        jtfDateInscrip.setText(client.getCustomerregistrationdate());
        jtfNom.setText(client.getCustomerlastname());
        jtfPrenom.setText(client.getCustomerfirstname());
        jtfMail.setText(client.getCustomermail());
        jtfTel.setText(client.getCustomerphone());
        jtfAnniv.setText(client.getCustomerbirthdate());

        jtaNotes.append(client.getCustomerNote());
        jtaNotes.setWrapStyleWord(true);
        jtaNotes.setLineWrap(true);

        for (int i = 0; i < jcbStatus.getItemCount(); i++) {
            Statut st = (Statut) jcbStatus.getItemAt(i);
            if (st.getId().equals(client.getStatus().getId())) {
                jcbStatus.setSelectedIndex(i);
            }
        }
        jcbAdrFact.removeAllItems();
        jcbAdrFact.setModel(initAdrFact(this.client.getId()));
        if (jcbAdrFact.getItemCount() != 0) {
            jcbAdrFact.setSelectedIndex(0);
            Adresse adrFact = (Adresse) jcbAdrFact.getSelectedItem();
            jtaAdrFact.setText(adrToString(adrFact));
        }
        jcbAdrExped.removeAllItems();
        jcbAdrExped.setModel(initAdrExp(this.client.getId()));
        if (jcbAdrExped.getItemCount() != 0) {
            jcbAdrExped.setSelectedIndex(0);
            Adresse adrExp = (Adresse) jcbAdrExped.getSelectedItem();
            jtaAdrExp.setText(adrToString(adrExp));
        }

        // préremplir les champs dans Modifier 
        jtfID1.setText(client.getId());
        jtfDateInscrip1.setText(client.getCustomerregistrationdate());
        jtfNom1.setText(client.getCustomerlastname());
        jtfPrenom1.setText(client.getCustomerfirstname());
        jtfMail1.setText(client.getCustomermail());
        jtfTel1.setText(client.getCustomerphone());
        jtfAnniv1.setText(client.getCustomerbirthdate());

        jtaNotes1.append(client.getCustomerNote());
        jtaNotes1.setWrapStyleWord(true);
        jtaNotes1.setLineWrap(true);

        for (int i = 0; i < jcbStatus1.getItemCount(); i++) {
            Statut st = (Statut) jcbStatus1.getItemAt(i);
            if (st.getId().equals(client.getStatus().getId())) {
                jcbStatus1.setSelectedIndex(i);
            }
        }
//        jcbAdrFactModif.removeAllItems();
//        jcbAdrFactModif.setModel(initAdrFact(this.client.getId()));
//        if (jcbAdrFactModif.getItemCount() != 0) {
//            jcbAdrFactModif.setSelectedIndex(0);
//            Adresse adrFactModif = (Adresse) jcbAdrFactModif.getSelectedItem();
//            jtAdrDesignation.setText(adrFactModif.getName());
//            jtAdrNum.setText(adrFactModif.getNumber());
//            jtAdrTypeVoi.setText(adrFactModif.getStreetType());
//            jtAdrNomVoi.setText(adrFactModif.getStreetName());
//            jtfAdrCP.setText(adrFactModif.getPostalCode());
//            jtfAdrVille.setText(adrFactModif.getCity());
//
//        }
//        jcbAdrExpeModif.removeAllItems();
//        jcbAdrExpeModif.setModel(initAdrExp(this.client.getId()));
//        if (jcbAdrExpeModif.getItemCount() != 0) {
//            jcbAdrExpeModif.setSelectedIndex(0);
//            Adresse adrExpeModif = (Adresse) jcbAdrExpeModif.getSelectedItem();
//            jtAdrDesignation2.setText(adrExpeModif.getName());
//            jtAdrNum2.setText(adrExpeModif.getNumber());
//            jtAdrTypeVoi2.setText(adrExpeModif.getStreetType());
//            jtAdrNomVoi2.setText(adrExpeModif.getStreetName());
//            jtfAdrCP2.setText(adrExpeModif.getPostalCode());
//            jtfAdrVille2.setText(adrExpeModif.getCity());
//        }

    }

    public void selectStat() {
        jcbStatus.setSelectedIndex(jcbStatus.getItemCount() - 1);
    }

    private DefaultComboBoxModel initStatus() {
        Vector v = new Vector();

        Xchange ech = new Xchange();
        Statement stat = null;
        String query = "SELECT STATUSID, STATUSNAME,STATUS_DESCR FROM STATUS "
                + "WHERE STATUSID BETWEEN 700 AND 799";
        ResultSet rs = null;

        try {

            ech.connect();
            stat = ech.getConnexion().createStatement();
            rs = stat.executeQuery(query);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Création connexion : " + ex.getErrorCode() + " / " + ex.getMessage(), "GestionClient", JOptionPane.ERROR_MESSAGE);;
        }
        try {
            while (rs.next()) {
                Statut st = new Statut();
                st.setId(rs.getString("STATUSID"));
                st.setStatusname(rs.getString("STATUSNAME"));
                st.setStatusdescr(rs.getString("STATUS_DESCR"));

                v.add(st);

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lecture données : " + ex.getErrorCode() + " / " + ex.getMessage(), "GestionClient", JOptionPane.ERROR_MESSAGE);
        } catch (DataExceptions ex) {
            JOptionPane.showMessageDialog(null, "Lecture données : " + ex.getErrorCode() + " / " + ex.getMessage(), "GestionClient", JOptionPane.ERROR_MESSAGE);
        } finally {
            ech.close();
        }

        return new DefaultComboBoxModel(v);
    }

    private DefaultComboBoxModel initStatus3() {
        Vector v = new Vector();

        Xchange ech = new Xchange();
        Statement stat = null;
        String query = "SELECT STATUSID, STATUSNAME,STATUS_DESCR FROM STATUS "
                + "WHERE STATUSID = 700";
        ResultSet rs = null;

        try {

            ech.connect();
            stat = ech.getConnexion().createStatement();
            rs = stat.executeQuery(query);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Création connexion : " + ex.getErrorCode() + " / " + ex.getMessage(), "GestionClient", JOptionPane.ERROR_MESSAGE);;
        }
        try {
            while (rs.next()) {
                Statut st = new Statut();
                st.setId(rs.getString("STATUSID"));
                st.setStatusname(rs.getString("STATUSNAME"));
                st.setStatusdescr(rs.getString("STATUS_DESCR"));

                v.add(st);

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lecture données : " + ex.getErrorCode() + " / " + ex.getMessage(), "GestionClient", JOptionPane.ERROR_MESSAGE);
        } catch (DataExceptions ex) {
            JOptionPane.showMessageDialog(null, "Lecture données : " + ex.getErrorCode() + " / " + ex.getMessage(), "GestionClient", JOptionPane.ERROR_MESSAGE);
        } finally {
            ech.close();
        }

        return new DefaultComboBoxModel(v);
    }

    // initialiser le model des combo box address 
    private DefaultComboBoxModel initAdrFact(String id) {
        Vector v = new Vector();

        Xchange ech = new Xchange();
        Statement stat = null;
        String query = "  SELECT ADDRESSID, ADDRESSNAME, ADDRESSNUMBER, ADDRESSSTREETTYPE, ADDRESSSTREETNAME, ADDRESSPOSTALCODE, ADDRESSCITY, ADDRESSMORE, ad.COUNTRYID, pa.Pays FROM ADRESSE ad JOIN PAYS pa ON ad.COUNTRYID = pa.COUNTRYID WHERE CLI_CUSTOMERID = " + id;
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

    public String adrToString(Adresse adr) {
        String str = "";
        str += adr.getNumber() + ", " + adr.getStreetType() + " " + adr.getStreetName();
        str += " \n" + adr.getPostalCode() + ", " + adr.getCity();
        return str;
    }

    public String moreToString(Adresse adr) {
        String str = "Complément : ";
        if (adr.getMore() == null) {
            return str;
        } else {
            return str + adr.getMore();
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
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jpAfficher = new javax.swing.JPanel();
        jlStatut = new javax.swing.JLabel();
        jcbStatus = new javax.swing.JComboBox();
        jlNom = new javax.swing.JLabel();
        jtfNom = new javax.swing.JTextField();
        jtfPrenom = new javax.swing.JTextField();
        jlPrenom = new javax.swing.JLabel();
        jlAdresse = new javax.swing.JLabel();
        jtfTel = new javax.swing.JTextField();
        jlTel = new javax.swing.JLabel();
        jtfMail = new javax.swing.JTextField();
        jlNotes = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaNotes = new javax.swing.JTextArea();
        jlAnniv = new javax.swing.JLabel();
        jtfAnniv = new javax.swing.JTextField();
        jlID = new javax.swing.JLabel();
        jlDateInscrip = new javax.swing.JLabel();
        jtfID = new javax.swing.JTextField();
        jtfDateInscrip = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jpAdresses = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jcbAdrFact = new javax.swing.JComboBox();
        jcbAdrExped = new javax.swing.JComboBox();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtaAdrFact = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        jtaAdrExp = new javax.swing.JTextArea();
        jbFermerAffich = new javax.swing.JButton();
        jpModifier = new javax.swing.JPanel();
        jtfDateInscrip1 = new javax.swing.JTextField();
        jtfID1 = new javax.swing.JTextField();
        jlDateInscrip1 = new javax.swing.JLabel();
        jlID1 = new javax.swing.JLabel();
        jtfAnniv1 = new javax.swing.JTextField();
        jlAnniv1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtaNotes1 = new javax.swing.JTextArea();
        jlNotes1 = new javax.swing.JLabel();
        jtfMail1 = new javax.swing.JTextField();
        jlTel1 = new javax.swing.JLabel();
        jtfTel1 = new javax.swing.JTextField();
        jlAdresse1 = new javax.swing.JLabel();
        jlPrenom1 = new javax.swing.JLabel();
        jtfPrenom1 = new javax.swing.JTextField();
        jtfNom1 = new javax.swing.JTextField();
        jlNom1 = new javax.swing.JLabel();
        jcbStatus1 = new javax.swing.JComboBox();
        jlStatut1 = new javax.swing.JLabel();
        jbEnregistrerModif = new javax.swing.JButton();
        jbFermerModif = new javax.swing.JButton();
        jbCreerAdr = new javax.swing.JButton();
        jbModifier = new javax.swing.JButton();
        jpCreer = new javax.swing.JPanel();
        jbEnregistrerCrea = new javax.swing.JButton();
        jbFermerCrea = new javax.swing.JButton();
        jlStatut3 = new javax.swing.JLabel();
        jcbStatus3 = new javax.swing.JComboBox();
        jlNom3 = new javax.swing.JLabel();
        jtfNom3 = new javax.swing.JTextField();
        jtfPrenom3 = new javax.swing.JTextField();
        jlPrenom3 = new javax.swing.JLabel();
        jlAdresse3 = new javax.swing.JLabel();
        jtfTel3 = new javax.swing.JTextField();
        jlTel3 = new javax.swing.JLabel();
        jtfMail3 = new javax.swing.JTextField();
        jlNotes3 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jtaNotes3 = new javax.swing.JTextArea();
        jlAnniv3 = new javax.swing.JLabel();
        jtfAnniv3 = new javax.swing.JTextField();
        jlID3 = new javax.swing.JLabel();
        jlDateInscrip3 = new javax.swing.JLabel();
        jtfID3 = new javax.swing.JTextField();
        jtfDateInscrip3 = new javax.swing.JTextField();
        jbCreerAdrC = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jlStatut.setText("Statut");

        jcbStatus.setModel(initStatus());
        jcbStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbStatusActionPerformed(evt);
            }
        });

        jlNom.setText("Nom");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jbFermerAffich, org.jdesktop.beansbinding.ELProperty.create("${selected}"), jtfNom, org.jdesktop.beansbinding.BeanProperty.create("editable"));
        bindingGroup.addBinding(binding);

        jtfNom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfNomActionPerformed(evt);
            }
        });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jbFermerAffich, org.jdesktop.beansbinding.ELProperty.create("${selected}"), jtfPrenom, org.jdesktop.beansbinding.BeanProperty.create("editable"));
        bindingGroup.addBinding(binding);

        jlPrenom.setText("Prénom");

        jlAdresse.setText("Téléphone");

        jtfTel.setEditable(false);

        jlTel.setText("Mail");

        jtfMail.setEditable(false);

        jlNotes.setText("Notes");

        jtaNotes.setEditable(false);
        jtaNotes.setColumns(20);
        jtaNotes.setLineWrap(true);
        jtaNotes.setRows(5);
        jtaNotes.setWrapStyleWord(true);
        jScrollPane1.setViewportView(jtaNotes);

        jlAnniv.setText("Date d'anniversaire ");

        jtfAnniv.setEditable(false);

        jlID.setText("Identifiant Client ");

        jlDateInscrip.setText("Date d'inscription");

        jtfID.setBackground(new java.awt.Color(204, 204, 204));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jbFermerAffich, org.jdesktop.beansbinding.ELProperty.create("${selected}"), jtfID, org.jdesktop.beansbinding.BeanProperty.create("editable"));
        bindingGroup.addBinding(binding);

        jtfID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfIDActionPerformed(evt);
            }
        });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jbFermerAffich, org.jdesktop.beansbinding.ELProperty.create("${selected}"), jtfDateInscrip, org.jdesktop.beansbinding.BeanProperty.create("editable"));
        bindingGroup.addBinding(binding);

        jtfDateInscrip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfDateInscripActionPerformed(evt);
            }
        });

        jLabel1.setText("Adresses");

        jLabel2.setText("Expedition");

        jLabel3.setText("Facturation");

        jcbAdrFact.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcbAdrFact.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbAdrFactItemStateChanged(evt);
            }
        });
        jcbAdrFact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbAdrFactActionPerformed(evt);
            }
        });

        jcbAdrExped.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcbAdrExped.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbAdrExpedItemStateChanged(evt);
            }
        });

        jtaAdrFact.setColumns(20);
        jtaAdrFact.setLineWrap(true);
        jtaAdrFact.setRows(5);
        jtaAdrFact.setWrapStyleWord(true);
        jScrollPane3.setViewportView(jtaAdrFact);

        jtaAdrExp.setColumns(20);
        jtaAdrExp.setLineWrap(true);
        jtaAdrExp.setRows(5);
        jtaAdrExp.setWrapStyleWord(true);
        jScrollPane5.setViewportView(jtaAdrExp);

        jbFermerAffich.setText("Fermer");
        jbFermerAffich.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbFermerAffichActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpAdressesLayout = new javax.swing.GroupLayout(jpAdresses);
        jpAdresses.setLayout(jpAdressesLayout);
        jpAdressesLayout.setHorizontalGroup(
            jpAdressesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpAdressesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpAdressesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpAdressesLayout.createSequentialGroup()
                        .addGroup(jpAdressesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jpAdressesLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jcbAdrFact, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jpAdressesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpAdressesLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jcbAdrExped, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane5)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpAdressesLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbFermerAffich)))
                .addContainerGap())
        );
        jpAdressesLayout.setVerticalGroup(
            jpAdressesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpAdressesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpAdressesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jcbAdrFact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbAdrExped, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpAdressesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jbFermerAffich)
                .addContainerGap(187, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jpAfficherLayout = new javax.swing.GroupLayout(jpAfficher);
        jpAfficher.setLayout(jpAfficherLayout);
        jpAfficherLayout.setHorizontalGroup(
            jpAfficherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpAfficherLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpAfficherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpAdresses, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jpAfficherLayout.createSequentialGroup()
                        .addGroup(jpAfficherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlDateInscrip)
                            .addComponent(jLabel1)
                            .addGroup(jpAfficherLayout.createSequentialGroup()
                                .addGroup(jpAfficherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jlAnniv)
                                    .addComponent(jlAdresse))
                                .addGap(63, 63, 63)
                                .addGroup(jpAfficherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtfTel, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtfAnniv, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jpAfficherLayout.createSequentialGroup()
                                .addGroup(jpAfficherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jlStatut, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlNom)
                                    .addComponent(jlPrenom)
                                    .addComponent(jlID)
                                    .addGroup(jpAfficherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jpAfficherLayout.createSequentialGroup()
                                            .addComponent(jlNotes)
                                            .addGap(21, 21, 21))
                                        .addGroup(jpAfficherLayout.createSequentialGroup()
                                            .addComponent(jlTel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGap(10, 10, 10))))
                                .addGap(76, 76, 76)
                                .addGroup(jpAfficherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtfMail, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jpAfficherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jtfNom, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jtfPrenom)
                                        .addComponent(jcbStatus, 0, 398, Short.MAX_VALUE)
                                        .addComponent(jtfID)
                                        .addComponent(jtfDateInscrip))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 20, Short.MAX_VALUE)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jpAfficherLayout.setVerticalGroup(
            jpAfficherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpAfficherLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpAfficherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlStatut)
                    .addComponent(jcbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpAfficherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlID)
                    .addComponent(jtfID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jpAfficherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlDateInscrip)
                    .addComponent(jtfDateInscrip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpAfficherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlNom)
                    .addComponent(jtfNom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpAfficherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlPrenom)
                    .addComponent(jtfPrenom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpAfficherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlAdresse))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpAfficherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlTel)
                    .addComponent(jtfMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpAfficherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlAnniv)
                    .addComponent(jtfAnniv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpAfficherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlNotes)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpAdresses, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(95, 95, 95))
        );

        jTabbedPane1.addTab("AFFICHER", jpAfficher);

        jtfID1.setEditable(false);
        jtfID1.setBackground(new java.awt.Color(204, 204, 204));
        jtfID1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfID1ActionPerformed(evt);
            }
        });

        jlDateInscrip1.setText("Date d'inscription");

        jlID1.setText("Identifiant Client ");

        jlAnniv1.setText("Date d'anniversaire ");

        jtaNotes1.setColumns(20);
        jtaNotes1.setLineWrap(true);
        jtaNotes1.setRows(5);
        jtaNotes1.setWrapStyleWord(true);
        jScrollPane2.setViewportView(jtaNotes1);

        jlNotes1.setText("Notes");

        jlTel1.setText("Mail");

        jlAdresse1.setText("Téléphone");

        jlPrenom1.setText("Prénom");

        jlNom1.setText("Nom");

        jcbStatus1.setModel(initStatus());

        jlStatut1.setText("Statut");

        jbEnregistrerModif.setText("Enregistrer");
        jbEnregistrerModif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEnregistrerModifActionPerformed(evt);
            }
        });

        jbFermerModif.setText("Fermer");
        jbFermerModif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbFermerModifActionPerformed(evt);
            }
        });

        jbCreerAdr.setText("Créer Adresse");
        jbCreerAdr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCreerAdrActionPerformed(evt);
            }
        });

        jbModifier.setText("Modifier Adresse");
        jbModifier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbModifierActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpModifierLayout = new javax.swing.GroupLayout(jpModifier);
        jpModifier.setLayout(jpModifierLayout);
        jpModifierLayout.setHorizontalGroup(
            jpModifierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpModifierLayout.createSequentialGroup()
                .addGroup(jpModifierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jpModifierLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jpModifierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlTel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpModifierLayout.createSequentialGroup()
                                .addGroup(jpModifierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jlStatut1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlNom1)
                                    .addComponent(jlPrenom1)
                                    .addComponent(jlID1))
                                .addGap(76, 76, 76)
                                .addGroup(jpModifierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jtfNom1, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jtfPrenom1)
                                    .addComponent(jcbStatus1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jtfID1)
                                    .addComponent(jtfDateInscrip1, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jlDateInscrip1)
                            .addGroup(jpModifierLayout.createSequentialGroup()
                                .addGroup(jpModifierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jpModifierLayout.createSequentialGroup()
                                        .addGroup(jpModifierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jlAnniv1)
                                            .addComponent(jlAdresse1))
                                        .addGap(63, 63, 63))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jpModifierLayout.createSequentialGroup()
                                        .addComponent(jlNotes1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(jpModifierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtfTel1, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtfMail1, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtfAnniv1, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jpModifierLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jbEnregistrerModif)
                        .addGap(18, 18, 18)
                        .addComponent(jbCreerAdr)
                        .addGap(18, 18, 18)
                        .addComponent(jbModifier)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbFermerModif)))
                .addGap(23, 23, 23))
        );
        jpModifierLayout.setVerticalGroup(
            jpModifierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpModifierLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jpModifierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlStatut1)
                    .addComponent(jcbStatus1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpModifierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlID1)
                    .addComponent(jtfID1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jpModifierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlDateInscrip1)
                    .addComponent(jtfDateInscrip1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpModifierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlNom1)
                    .addComponent(jtfNom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpModifierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlPrenom1)
                    .addComponent(jtfPrenom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpModifierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfTel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlAdresse1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpModifierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlTel1)
                    .addComponent(jtfMail1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpModifierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlAnniv1)
                    .addComponent(jtfAnniv1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpModifierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlNotes1))
                .addGap(300, 300, 300)
                .addGroup(jpModifierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbFermerModif)
                    .addComponent(jbEnregistrerModif)
                    .addComponent(jbCreerAdr)
                    .addComponent(jbModifier))
                .addGap(81, 81, 81))
        );

        jTabbedPane1.addTab("MODIFIER", jpModifier);

        jbEnregistrerCrea.setText("Enregistrer");
        jbEnregistrerCrea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEnregistrerCreaActionPerformed(evt);
            }
        });

        jbFermerCrea.setText("Fermer");
        jbFermerCrea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbFermerCreaActionPerformed(evt);
            }
        });

        jlStatut3.setText("Statut");

        jcbStatus3.setModel(initStatus3());
        jcbStatus3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbStatus3ActionPerformed(evt);
            }
        });

        jlNom3.setText("Nom");

        jtfNom3.setText("Champs obligatoire");
        jtfNom3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfNom3FocusGained(evt);
            }
        });
        jtfNom3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jtfNom3MousePressed(evt);
            }
        });
        jtfNom3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfNom3ActionPerformed(evt);
            }
        });

        jtfPrenom3.setText("Champs obligatoire");
        jtfPrenom3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfPrenom3FocusGained(evt);
            }
        });

        jlPrenom3.setText("Prénom");

        jlAdresse3.setText("Téléphone");

        jtfTel3.setText("Champs obligatoire");
        jtfTel3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfTel3FocusGained(evt);
            }
        });
        jtfTel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jtfTel3MousePressed(evt);
            }
        });

        jlTel3.setText("Mail");

        jtfMail3.setText("Champs obligatoire");
        jtfMail3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfMail3FocusGained(evt);
            }
        });
        jtfMail3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jtfMail3MousePressed(evt);
            }
        });

        jlNotes3.setText("Notes");

        jtaNotes3.setColumns(20);
        jtaNotes3.setLineWrap(true);
        jtaNotes3.setRows(5);
        jtaNotes3.setWrapStyleWord(true);
        jScrollPane4.setViewportView(jtaNotes3);
        jtaNotes3.getAccessibleContext().setAccessibleParent(jpCreer);

        jlAnniv3.setText("Date d'anniversaire ");

        jtfAnniv3.setText("Champs obligatoire");
        jtfAnniv3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfAnniv3FocusGained(evt);
            }
        });
        jtfAnniv3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jtfAnniv3MousePressed(evt);
            }
        });
        jtfAnniv3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfAnniv3ActionPerformed(evt);
            }
        });

        jlID3.setText("Identifiant Client");

        jlDateInscrip3.setText("Date d'inscription");

        jtfID3.setEditable(false);
        jtfID3.setBackground(new java.awt.Color(204, 204, 204));
        jtfID3.setText("Champs autoincrémenté");
        jtfID3.setName("ID Autoincrémenté"); // NOI18N
        jtfID3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfID3ActionPerformed(evt);
            }
        });

        jtfDateInscrip3.setText("Champs obligatoire");
        jtfDateInscrip3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfDateInscrip3FocusGained(evt);
            }
        });
        jtfDateInscrip3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jtfDateInscrip3MousePressed(evt);
            }
        });

        jbCreerAdrC.setText("Créer Adresse");
        jbCreerAdrC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCreerAdrCActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpCreerLayout = new javax.swing.GroupLayout(jpCreer);
        jpCreer.setLayout(jpCreerLayout);
        jpCreerLayout.setHorizontalGroup(
            jpCreerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpCreerLayout.createSequentialGroup()
                .addGroup(jpCreerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpCreerLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jpCreerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlTel3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlStatut3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlNom3)
                            .addComponent(jlPrenom3)
                            .addComponent(jlID3)
                            .addComponent(jlAdresse3)
                            .addComponent(jlDateInscrip3)
                            .addComponent(jlAnniv3)
                            .addComponent(jlNotes3)))
                    .addGroup(jpCreerLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jbEnregistrerCrea)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpCreerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpCreerLayout.createSequentialGroup()
                        .addComponent(jbCreerAdrC)
                        .addGap(121, 121, 121)
                        .addComponent(jbFermerCrea)
                        .addGap(0, 202, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpCreerLayout.createSequentialGroup()
                        .addGroup(jpCreerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
                            .addComponent(jtfMail3)
                            .addComponent(jtfTel3)
                            .addComponent(jtfNom3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jtfPrenom3)
                            .addComponent(jtfID3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jtfDateInscrip3)
                            .addComponent(jtfAnniv3)
                            .addComponent(jcbStatus3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(32, 32, 32))))
        );
        jpCreerLayout.setVerticalGroup(
            jpCreerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCreerLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jpCreerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlStatut3)
                    .addComponent(jcbStatus3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpCreerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlID3)
                    .addComponent(jtfID3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jpCreerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlDateInscrip3)
                    .addComponent(jtfDateInscrip3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpCreerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlNom3)
                    .addComponent(jtfNom3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCreerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlPrenom3)
                    .addComponent(jtfPrenom3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCreerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfTel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlAdresse3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCreerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlTel3)
                    .addComponent(jtfMail3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpCreerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlAnniv3)
                    .addComponent(jtfAnniv3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpCreerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlNotes3))
                .addGap(312, 312, 312)
                .addGroup(jpCreerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbFermerCrea)
                    .addComponent(jbEnregistrerCrea)
                    .addComponent(jbCreerAdrC))
                .addContainerGap())
        );

        jbEnregistrerCrea.getAccessibleContext().setAccessibleParent(jpCreer);
        jbFermerCrea.getAccessibleContext().setAccessibleParent(jpCreer);
        jlStatut3.getAccessibleContext().setAccessibleParent(jpCreer);
        jcbStatus3.getAccessibleContext().setAccessibleParent(jpCreer);
        jlNom3.getAccessibleContext().setAccessibleParent(jpCreer);
        jtfNom3.getAccessibleContext().setAccessibleParent(jpCreer);
        jtfPrenom3.getAccessibleContext().setAccessibleParent(jpCreer);
        jlPrenom3.getAccessibleContext().setAccessibleParent(jpCreer);
        jlAdresse3.getAccessibleContext().setAccessibleParent(jpCreer);
        jtfTel3.getAccessibleContext().setAccessibleParent(jpCreer);
        jlTel3.getAccessibleContext().setAccessibleParent(jpCreer);
        jtfMail3.getAccessibleContext().setAccessibleParent(jpCreer);
        jlNotes3.getAccessibleContext().setAccessibleParent(jpCreer);
        jlAnniv3.getAccessibleContext().setAccessibleParent(jpCreer);
        jtfAnniv3.getAccessibleContext().setAccessibleParent(jpCreer);
        jlID3.getAccessibleContext().setAccessibleParent(jpCreer);
        jlDateInscrip3.getAccessibleContext().setAccessibleParent(jpCreer);
        jtfID3.getAccessibleContext().setAccessibleParent(jpCreer);
        jtfDateInscrip3.getAccessibleContext().setAccessibleParent(jpCreer);

        jTabbedPane1.addTab("CREER", jpCreer);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleParent(this);

        bindingGroup.bind();

        setBounds(0, 0, 642, 938);
    }// </editor-fold>//GEN-END:initComponents

    private void jbFermerAffichActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbFermerAffichActionPerformed
        this.dispose();
    }//GEN-LAST:event_jbFermerAffichActionPerformed

    private void jbEnregistrerModifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEnregistrerModifActionPerformed
        // TODO add your handling code here:
        //mise à jour de l'objet avec controle d'erreur grâce à error

        int error = 0;

//        Adresse adrFactModif = (Adresse) jcbAdrFactModif.getSelectedItem();
//
//        Adresse adrExpeModif = (Adresse) jcbAdrExpeModif.getSelectedItem();
        //client.setID(jtfID1.getText());
        try {

            client.setCustomerregistrationdate(jtfDateInscrip1.getText());
        } catch (DataExceptions ex) {
            JOptionPane.showMessageDialog(null, "Ecriture Date d'inscription : " + ex.getErrorCode() + " / " + ex.getMessage(), "GestionClientAfficher", JOptionPane.ERROR_MESSAGE);
            error += 1;
        }
        try {

            client.setCustomerlastname(jtfNom1.getText());
        } catch (DataExceptions ex) {
            JOptionPane.showMessageDialog(null, "Ecriture Nom : " + ex.getErrorCode() + " / " + ex.getMessage(), "GestionClientAfficher", JOptionPane.ERROR_MESSAGE);
            error += 1;
        }

        try {

            client.setCustomerfirstname(jtfPrenom1.getText());
        } catch (DataExceptions ex) {
            JOptionPane.showMessageDialog(null, "Ecriture Préom : " + ex.getErrorCode() + " / " + ex.getMessage(), "GestionClientAfficher", JOptionPane.ERROR_MESSAGE);
            error += 1;
        }

        try {
            //System.out.println(jtfTel1.getText());
            client.setCustomerphone(jtfTel1.getText());
        } catch (DataExceptions ex) {
            JOptionPane.showMessageDialog(null, "Ecriture Téléphone : " + ex.getErrorCode() + " / " + ex.getMessage(), "GestionClientAfficher", JOptionPane.ERROR_MESSAGE);
            error += 1;
        }
        client.setCustomermail(jtfMail1.getText());
        try {
            //System.out.println(jtfAnniv1.getText());
            client.setCustomerbirthdate(jtfAnniv1.getText());
        } catch (DataExceptions ex) {
            JOptionPane.showMessageDialog(null, "Ecriture Anniversaire : " + ex.getErrorCode() + " / " + ex.getMessage(), "GestionClientAfficher", JOptionPane.ERROR_MESSAGE);
            error += 1;
        }
        client.setCustomerNote(jtaNotes1.getText());

        Statut st = (Statut) jcbStatus1.getSelectedItem();
        client.setStatus(st);

//si pas d'erreur ( error = 0) je sauvegarde
        int n = 0;
        if (error == 0) {
            if (client.getStatus().getId().equals("799")) {
                n = JOptionPane.showConfirmDialog(rootPane, "ATTENTION vous allez supprimer un client.\nCette action est irrémediable\n'OK' pour valider la suppression\nou 'Annuler' et selectionner un autre statut", "Suppression", JOptionPane.OK_CANCEL_OPTION);
            }
            if (n == 0) {
                Xchange ech = new Xchange();
                ech.connect();
                client.save(ech, client.genValue());
                ech.close();

            }
        }
        JFCli.refreshClient();

        // Modification adresse facturation 
//        try {
//            adrFactModif.setName(jtAdrDesignation.getText());
//            adrFactModif.setNumber(jtAdrNum.getText());
//            adrFactModif.setStreetType(jtAdrTypeVoi.getText());
//            adrFactModif.setStreetName(jtAdrNomVoi.getText());
//            adrFactModif.setPostalCode(jtfAdrCP.getText());
//            adrFactModif.setCity(jtfAdrVille.getText());
//            
//            adrFactModif.setCli_Customer(client);
//            
//        } catch (DataExceptions ex) {
//            JOptionPane.showMessageDialog(null, "Ecriture Adresse : " + ex.getErrorCode() + " / " + ex.getMessage(), "ModifAdresseFacturation", JOptionPane.ERROR_MESSAGE);
//        }
//        
//                Xchange ech = new Xchange();
//                ech.connect();
//                adrFactModif.save(ech, adrFactModif.genValue());
//                ech.close();
//
//        //Modification adresse expedition         
//        
//        try {
//            adrExpeModif.setName(jtAdrDesignation2.getText());
//            adrExpeModif.setNumber(jtAdrNum2.getText());
//            adrExpeModif.setStreetType(jtAdrTypeVoi2.getText());
//            adrExpeModif.setStreetName(jtAdrNomVoi2.getText());
//            adrExpeModif.setPostalCode(jtfAdrCP2.getText());
//            adrExpeModif.setCity(jtfAdrVille2.getText());
//            
//            adrExpeModif.setCustomer(client);
//            
//        } catch (DataExceptions ex) {
//            JOptionPane.showMessageDialog(null, "Ecriture Adresse : " + ex.getErrorCode() + " / " + ex.getMessage(), "ModifAdresseFacturation", JOptionPane.ERROR_MESSAGE);
//        }
//        
//                ech.connect();
//                adrExpeModif.save(ech, adrExpeModif.genValue());
//                ech.close();
        this.dispose();
    }//GEN-LAST:event_jbEnregistrerModifActionPerformed

    private void jbFermerModifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbFermerModifActionPerformed
        this.dispose();
    }//GEN-LAST:event_jbFermerModifActionPerformed

    private void jtfIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfIDActionPerformed
        // TODO add your handling code here:
        jtfID.setEditable(false);

    }//GEN-LAST:event_jtfIDActionPerformed

    private void jtfID1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfID1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfID1ActionPerformed

    private void jtfID3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfID3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfID3ActionPerformed

    private void jbFermerCreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbFermerCreaActionPerformed
        this.dispose();
    }//GEN-LAST:event_jbFermerCreaActionPerformed

    private void jbEnregistrerCreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEnregistrerCreaActionPerformed
        this.client = new Client();
        int error = 0;
        //client.setID(jtfID3.getText());
        try {
            System.out.println(jtfDateInscrip3.getText());
            client.setCustomerregistrationdate(jtfDateInscrip3.getText());
        } catch (DataExceptions ex) {
            JOptionPane.showMessageDialog(null, "Ecriture Date d'inscription : " + ex.getErrorCode() + " / " + ex.getMessage(), "GestionClientCréation", JOptionPane.ERROR_MESSAGE);
            error += 1;
        }
        try {

            client.setCustomerlastname(jtfNom3.getText());
        } catch (DataExceptions ex) {
            JOptionPane.showMessageDialog(null, "Ecriture Nom : " + ex.getErrorCode() + " / " + ex.getMessage(), "GestionClientCréation", JOptionPane.ERROR_MESSAGE);
            error += 1;
        }

        try {

            client.setCustomerfirstname(jtfPrenom3.getText());
        } catch (DataExceptions ex) {
            JOptionPane.showMessageDialog(null, "Ecriture Préom : " + ex.getErrorCode() + " / " + ex.getMessage(), "GestionClientCréation", JOptionPane.ERROR_MESSAGE);
            error += 1;
        }

        try {
            //System.out.println(jtfTel1.getText());
            client.setCustomerphone(jtfTel3.getText());
        } catch (DataExceptions ex) {
            JOptionPane.showMessageDialog(null, "Ecriture Téléphone : " + ex.getErrorCode() + " / " + ex.getMessage(), "GestionClientCréation", JOptionPane.ERROR_MESSAGE);
            error += 1;
        }
        client.setCustomermail(jtfMail3.getText());
        try {
            //System.out.println(jtfAnniv1.getText());
            client.setCustomerbirthdate(jtfAnniv3.getText());
        } catch (DataExceptions ex) {
            JOptionPane.showMessageDialog(null, "Ecriture Anniversaire : " + ex.getErrorCode() + " / " + ex.getMessage(), "GestionClientCréation", JOptionPane.ERROR_MESSAGE);
            error += 1;
        }
        client.setCustomerNote(jtaNotes3.getText());

        Statut st = (Statut) jcbStatus3.getSelectedItem();
        //System.out.println(st);
        client.setStatus(st);

//si pas d'erreur ( error = 0) je sauvegarde
        int n = 0;
        if (error == 0) {
            if (client.getStatus().getId().equals("799")) {
                n = JOptionPane.showConfirmDialog(rootPane, "ATTENTION vous allez supprimer un client.\nCette action est irrémediable\n'OK' pour valider la suppression\nou 'Annuler' et selectionner un autre statut", "Suppression", JOptionPane.OK_CANCEL_OPTION);
            }

            //client.setStatus(st);
            jtfDateInscrip3.setText("");
            jtfNom3.setText("");
            jtfPrenom3.setText("");
            jtfMail3.setText("");
            jtfTel3.setText("");
            jtfAnniv3.setText("");
            jtaNotes3.setText("");
            jtaNotes3.setWrapStyleWord(true);
            jtaNotes3.setLineWrap(true);
            if (n == 0) {
                Xchange ech = new Xchange();
                ech.connect();
                client.save(ech, client.genValue());
                ech.close();

            }
            JFCli.refreshClient();
            this.dispose();

        }

    }//GEN-LAST:event_jbEnregistrerCreaActionPerformed

    private void jcbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbStatusActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jcbStatusActionPerformed

    private void jtfDateInscripActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfDateInscripActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfDateInscripActionPerformed

    private void jtfNomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfNomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfNomActionPerformed

    private void jcbStatus3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbStatus3ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jcbStatus3ActionPerformed

    private void jtfAnniv3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfAnniv3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfAnniv3ActionPerformed

    private void jtfDateInscrip3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtfDateInscrip3MousePressed
        // TODO add your handling code here:
        jtfDateInscrip3.setText("");
    }//GEN-LAST:event_jtfDateInscrip3MousePressed

    private void jtfNom3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfNom3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfNom3ActionPerformed

    private void jtfNom3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtfNom3MousePressed
        // TODO add your handling code here:
        jtfNom3.setText("");
    }//GEN-LAST:event_jtfNom3MousePressed

    private void jtfDateInscrip3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfDateInscrip3FocusGained
        // TODO add your handling code here:
        jtfDateInscrip3.setText("");
    }//GEN-LAST:event_jtfDateInscrip3FocusGained

    private void jtfPrenom3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfPrenom3FocusGained
        // TODO add your handling code here:
        jtfPrenom3.setText("");
    }//GEN-LAST:event_jtfPrenom3FocusGained

    private void jtfNom3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfNom3FocusGained
        // TODO add your handling code here:
        jtfNom3.setText("");
    }//GEN-LAST:event_jtfNom3FocusGained

    private void jtfTel3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfTel3FocusGained
        // TODO add your handling code here:
        jtfTel3.setText("");
    }//GEN-LAST:event_jtfTel3FocusGained

    private void jtfTel3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtfTel3MousePressed
        // TODO add your handling code here:
        jtfTel3.setText("");
    }//GEN-LAST:event_jtfTel3MousePressed

    private void jtfMail3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfMail3FocusGained
        // TODO add your handling code here:
        jtfMail3.setText("");
    }//GEN-LAST:event_jtfMail3FocusGained

    private void jtfMail3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtfMail3MousePressed
        // TODO add your handling code here:
        jtfMail3.setText("");
    }//GEN-LAST:event_jtfMail3MousePressed

    private void jtfAnniv3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfAnniv3FocusGained
        // TODO add your handling code here:
        jtfAnniv3.setText("");
    }//GEN-LAST:event_jtfAnniv3FocusGained

    private void jtfAnniv3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtfAnniv3MousePressed
        // TODO add your handling code here:
        jtfAnniv3.setText("");
    }//GEN-LAST:event_jtfAnniv3MousePressed

    private void jcbAdrFactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbAdrFactActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbAdrFactActionPerformed

    private void jcbAdrFactItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbAdrFactItemStateChanged
        // TODO add your handling code here:
        Adresse adrFact = (Adresse) jcbAdrFact.getSelectedItem();
        System.out.println("adresse : " + adrFact);
        if (adrFact != null) {
            jtaAdrFact.setText(adrToString(adrFact));
        }
    }//GEN-LAST:event_jcbAdrFactItemStateChanged

    private void jcbAdrExpedItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbAdrExpedItemStateChanged
        // TODO add your handling code here:
        Adresse adrExp = (Adresse) jcbAdrExped.getSelectedItem();
        //System.out.println("adresse : " + adrExp);
        if (adrExp != null) {
            jtaAdrExp.setText(adrToString(adrExp));
        }
    }//GEN-LAST:event_jcbAdrExpedItemStateChanged

    private void jbCreerAdrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCreerAdrActionPerformed
        JFAdresseMod JFaM = new JFAdresseMod();
        JFaM.setClient(client);
        JFaM.setFonction("new");
        JFaM.textFill();
        JFaM.setVisible(true);


    }//GEN-LAST:event_jbCreerAdrActionPerformed

    private void jbCreerAdrCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCreerAdrCActionPerformed
        JFAdresseMod JFaM = new JFAdresseMod();
        JFaM.setClient(client);
        JFaM.setFonction("new");
        JFaM.textFill();
        JFaM.setVisible(true);
    }//GEN-LAST:event_jbCreerAdrCActionPerformed

    private void jbModifierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbModifierActionPerformed
        JFAdresseMod JFaM = new JFAdresseMod();
        JFaM.setClient(client);
        JFaM.setFonction("mod");
        JFaM.textFill();
        JFaM.setVisible(true);
    }//GEN-LAST:event_jbModifierActionPerformed

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
            java.util.logging.Logger.getLogger(JFGestionClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFGestionClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFGestionClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFGestionClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFGestionClient().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton jbCreerAdr;
    private javax.swing.JButton jbCreerAdrC;
    private javax.swing.JButton jbEnregistrerCrea;
    private javax.swing.JButton jbEnregistrerModif;
    private javax.swing.JButton jbFermerAffich;
    private javax.swing.JButton jbFermerCrea;
    private javax.swing.JButton jbFermerModif;
    private javax.swing.JButton jbModifier;
    private javax.swing.JComboBox jcbAdrExped;
    private javax.swing.JComboBox jcbAdrFact;
    private javax.swing.JComboBox jcbStatus;
    private javax.swing.JComboBox jcbStatus1;
    private javax.swing.JComboBox jcbStatus3;
    private javax.swing.JLabel jlAdresse;
    private javax.swing.JLabel jlAdresse1;
    private javax.swing.JLabel jlAdresse3;
    private javax.swing.JLabel jlAnniv;
    private javax.swing.JLabel jlAnniv1;
    private javax.swing.JLabel jlAnniv3;
    private javax.swing.JLabel jlDateInscrip;
    private javax.swing.JLabel jlDateInscrip1;
    private javax.swing.JLabel jlDateInscrip3;
    private javax.swing.JLabel jlID;
    private javax.swing.JLabel jlID1;
    private javax.swing.JLabel jlID3;
    private javax.swing.JLabel jlNom;
    private javax.swing.JLabel jlNom1;
    private javax.swing.JLabel jlNom3;
    private javax.swing.JLabel jlNotes;
    private javax.swing.JLabel jlNotes1;
    private javax.swing.JLabel jlNotes3;
    private javax.swing.JLabel jlPrenom;
    private javax.swing.JLabel jlPrenom1;
    private javax.swing.JLabel jlPrenom3;
    private javax.swing.JLabel jlStatut;
    private javax.swing.JLabel jlStatut1;
    private javax.swing.JLabel jlStatut3;
    private javax.swing.JLabel jlTel;
    private javax.swing.JLabel jlTel1;
    private javax.swing.JLabel jlTel3;
    private javax.swing.JPanel jpAdresses;
    private javax.swing.JPanel jpAfficher;
    private javax.swing.JPanel jpCreer;
    private javax.swing.JPanel jpModifier;
    private javax.swing.JTextArea jtaAdrExp;
    private javax.swing.JTextArea jtaAdrFact;
    private javax.swing.JTextArea jtaNotes;
    private javax.swing.JTextArea jtaNotes1;
    private javax.swing.JTextArea jtaNotes3;
    private javax.swing.JTextField jtfAnniv;
    private javax.swing.JTextField jtfAnniv1;
    private javax.swing.JTextField jtfAnniv3;
    private javax.swing.JTextField jtfDateInscrip;
    private javax.swing.JTextField jtfDateInscrip1;
    private javax.swing.JTextField jtfDateInscrip3;
    private javax.swing.JTextField jtfID;
    private javax.swing.JTextField jtfID1;
    private javax.swing.JTextField jtfID3;
    private javax.swing.JTextField jtfMail;
    private javax.swing.JTextField jtfMail1;
    private javax.swing.JTextField jtfMail3;
    private javax.swing.JTextField jtfNom;
    private javax.swing.JTextField jtfNom1;
    private javax.swing.JTextField jtfNom3;
    private javax.swing.JTextField jtfPrenom;
    private javax.swing.JTextField jtfPrenom1;
    private javax.swing.JTextField jtfPrenom3;
    private javax.swing.JTextField jtfTel;
    private javax.swing.JTextField jtfTel1;
    private javax.swing.JTextField jtfTel3;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
