package Interface;

import ObjetsSimples.*;

import com.sun.corba.se.impl.resolver.SplitLocalResolverImpl;
import exceptions.DataExceptions;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import xchange.Xchange;

/**
 *
 * @author cda201
 */
public class JFCommandeMod extends javax.swing.JFrame {

    private Commande commande;
    private HomePage JFC;
    private String button;

    /**
     * Creates new form JFEditeurMod
     */
    public JFCommandeMod() {
        initComponents();
        this.commande = new Commande();
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }

    public HomePage getJFE() {
        return JFC;
    }

    public void setJFC(HomePage JFC) {
        this.JFC = JFC;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {

        this.commande = commande;
        //System.out.println("================== set commande : " + this.commande);
    }

    public void enregOff() {
        jbEnregistrer.setVisible(false);
    }

    private String transDate(String d) {
        if (d != null) {
            String[] da = d.split(" ");
            //System.out.println(da[0]);

            String dat[] = da[0].split("-");
            String date = dat[2] + "/" + dat[1] + "/" + dat[0];
            return date;

        } else {
            return null;
        }
    }

    public String adrToString(Adresse adr) {
        String str = "";
        str += adr.getNumber() + " " + adr.getStreetType() + " " + adr.getStreetName();
        str += " " + adr.getPostalCode() + " " + adr.getCity();
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

    public void refresh() {
        jTableLineCmd.removeAll();
        jTableLineCmd.setModel(initModelLigneCmd());
        jtfMontantGlobal.setText(commande.getMontantGlobal());

    }

    public void textFill() {
        //choix du status
        for (int i = 0; i < jcbStatus.getItemCount(); i++) {
            Statut st = (Statut) jcbStatus.getItemAt(i);
            if (st.getId().equals(commande.getStatus().getId())) {
                jcbStatus.setSelectedIndex(i);
            }
        }
        //System.out.println(commande);
        //System.out.println(commande.getClient());
        //remplissage de la partie commande
        jtfCmdNum.setText(commande.getID());
        jtfCmdDate.setText(commande.getCmdDate());
        jtfEnvoiNum.setText(commande.getNumEnvoi());
        jtfEnvDate.setText(commande.getTranspDate());
        if (commande.getNotes() != null) {
            jtfMore.setText(commande.getNotes());
        }
        if (this.button.equals("new")) {
            String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
            jtfCmdDate.setText(currentDate);

        }
        //remplissage de la partie client
        //System.out.println("commandAAA = " + commande);

        jtfNom.setText(commande.getClient().getCustomerlastname());
        jtfPrenom.setText(commande.getClient().getCustomerfirstname());
        jtfDateNais.setText(commande.getClient().getCustomerbirthdate());
        jtfMail.setText(commande.getClient().getCustomermail());
        jtfTel.setText(commande.getClient().getCustomerphone());
//        if (commande.getClient().getCustomernotes() != null) {
//            jtaNotes.setText(commande.getClient().getCustomernotes());
//        }

//        jtaNotes.setText(commande.getNotes());
//        jtaNotes.setWrapStyleWord(true);
//        jtaNotes.setLineWrap(true);
        //remplissage des adresses
        jlAdrExp.setText(adrToString(commande.getAddrExp()));
        jlAdrFact.setText(adrToString(commande.getAddrInv()));

        jlAdrExpMore.setText(moreToString(commande.getAddrExp()));
        jlAdrFactMore.setText((moreToString(commande.getAddrInv())));

        //remplissage des combo box
        if (this.button.equals("aff")) {
            jcbClient.removeAllItems();
            //String Client = commande.getClient().getCustomerlastname() + ", " + commande.getClient().getCustomerfirstname();
            jcbClient.addItem(commande.getClient());

            jcbAdrExp.removeAllItems();
            jcbAdrExp.setModel(initAdrExp(commande.getClient().getId()));
            for (int i = 0; i < jcbAdrExp.getItemCount(); i++) {

                Adresse adr = (Adresse) jcbAdrExp.getItemAt(i);

                if (adr.getAddrID().equals(commande.getClient().getId())) {
                    jcbAdrExp.setSelectedIndex(i);

                }
            }

            jcbAdrFact.removeAllItems();
            jcbAdrFact.setModel(initAdrFact(commande.getClient().getId()));
            for (int i = 0; i < jcbAdrFact.getItemCount(); i++) {

                Adresse adr = (Adresse) jcbAdrFact.getItemAt(i);

                if (adr.getAddrID().equals(commande.getClient().getId())) {
                    jcbAdrFact.setSelectedIndex(i);

                }
            }
        }

        if (this.button.equals("mod")) {
            jcbClient.removeAllItems();
            jcbClient.addItem(commande.getClient());
//            jcbClient.setModel(initClient());
//            for (int i = 0; i < jcbClient.getItemCount(); i++) {
//
//                Client cl = (Client) jcbClient.getItemAt(i);
//
//                if (cl.getId().equals(commande.getClient().getId())) {
//                    jcbClient.setSelectedIndex(i);
//
//                }
//            }

            jcbAdrExp.removeAllItems();
            jcbAdrExp.setModel(initAdrExp(commande.getClient().getId()));
            for (int i = 0; i < jcbAdrExp.getItemCount(); i++) {

                Adresse adr = (Adresse) jcbAdrExp.getItemAt(i);

                if (adr.getAddrID().equals(commande.getClient().getId())) {
                    jcbAdrExp.setSelectedIndex(i);

                }
            }

            jcbAdrFact.removeAllItems();
            jcbAdrFact.setModel(initAdrFact(commande.getClient().getId()));
            for (int i = 0; i < jcbAdrFact.getItemCount(); i++) {

                Adresse adr = (Adresse) jcbAdrFact.getItemAt(i);

                if (adr.getAddrID().equals(commande.getClient().getId())) {
                    jcbAdrFact.setSelectedIndex(i);

                }
            }
        }
        if (this.button.equals("new")) {
            jcbClient.removeAllItems();
            jcbClient.setModel(initClient());
//            jcbClient.setSelectedIndex(0);
//            Client cl = (Client) jcbClient.getSelectedItem();
//            commande.setClient(cl);

            jcbAdrExp.setModel(initAdrExp(commande.getClient().getId()));
//            jcbAdrExp.setSelectedIndex(0);
//            Adresse adrE = (Adresse) jcbAdrExp.getSelectedItem();
//            jlAdrExp.setText(adrToString(adrE));
            jcbAdrFact.setModel(initAdrFact(commande.getClient().getId()));
//            jcbAdrFact.setSelectedIndex(0);
//            Adresse adrF = (Adresse) jcbAdrFact.getSelectedItem();
//            jlAdrFact.setText(adrToString(adrF));
        }

        //remplissage de la table ligne de cmd
        jTableLineCmd.setModel(initModelLigneCmd());
//

        jtfMontantGlobal.setText(commande.getMontantGlobal());
        jtfMontantRegle.setText((commande.getMontantPaiement()));
        jtfEtatEnv.setText((commande.getEtatEnvoi()));
    }

    private DefaultTableModel initModelLigneCmd() {
        Vector v = new Vector();
        v.add("ISBN13");
        v.add("Titre");
        v.add("Quantité");
        v.add("Prix HT");
        v.add("TVA");
        v.add("Total TTC");

        return new DefaultTableModel(initLigneCmd(), v) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

    }

    public void selectStat() {
        jcbStatus.setSelectedIndex(jcbStatus.getItemCount() - 1);
    }

    private Vector initLigneCmd() {
        Vector v = new Vector();
        float sum = 0;
        Xchange ech = new Xchange();
        Statement stat = null;
        String query = "SELECT lc.BOOKISBN13, li.BOOKTITLE,LINEQUANTITY"
                + ", LINEID, LINEPRICEHT,LINEVAT, ROUND((LINEQUANTITY * LINEPRICEHT *( 1 + LINEVAT / 100) ),2) as PRIXTOTAL FROM LIGNECMD lc "
                + "JOIN LIVRE li ON lc.BOOKISBN13 = li.BOOKISBN13 "
                + "WHERE ORDERID = " + commande.getID();
        ResultSet rs = null;

        try {

            ech.connect();
            stat = ech.getConnexion().createStatement();
            rs = stat.executeQuery(query);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Création connexion : " + ex.getErrorCode() + " / " + ex.getMessage(), "CommandeligneMod", JOptionPane.ERROR_MESSAGE);;
        }
        try {
            while (rs.next()) {
//                Vector vv = new Vector();
//                vv.add(rs.getString("BOOKISBN13"));
//                vv.add(rs.getString("BOOKTITLE"));
//                vv.add(rs.getString("LINEQUANTITY"));
//                vv.add(rs.getString("LINEPRICEHT"));
//                vv.add(rs.getString("LINEVAT"));
//                vv.add(rs.getString("PRIXTOTAL"));

                Livre livre = new Livre();
                LigneCmd ligne = new LigneCmd();

                try {
                    livre.setIsbn13(rs.getString("BOOKISBN13"));
                } catch (DataExceptions ex) {
                    JOptionPane.showMessageDialog(null, "Creation Vector " + ex.getErrorCode() + " / " + ex.getMessage(), "LigneCMD", JOptionPane.ERROR_MESSAGE);
                }
                try {
                    livre.setTitle(rs.getString("BOOKTITLE"));
                } catch (DataExceptions ex) {
                    JOptionPane.showMessageDialog(null, "Creation Vector " + ex.getErrorCode() + " / " + ex.getMessage(), "LigneCMD", JOptionPane.ERROR_MESSAGE);
                }
                ligne.setLivre(livre);
                try {
                    ligne.setQuantite(rs.getString("LINEQUANTITY"));
                } catch (DataExceptions ex) {
                    JOptionPane.showMessageDialog(null, "Creation Vector " + ex.getErrorCode() + " / " + ex.getMessage(), "LigneCMD", JOptionPane.ERROR_MESSAGE);
                }
                try {
                    ligne.setPrixHT(rs.getString("LINEPRICEHT"));
                } catch (DataExceptions ex) {
                    JOptionPane.showMessageDialog(null, "Creation Vector " + ex.getErrorCode() + " / " + ex.getMessage(), "LigneCMD", JOptionPane.ERROR_MESSAGE);
                }
                try {
                    ligne.setTva(rs.getString("LINEVAT"));
                } catch (DataExceptions ex) {
                    JOptionPane.showMessageDialog(null, "Creation Vector " + ex.getErrorCode() + " / " + ex.getMessage(), "LigneCMD", JOptionPane.ERROR_MESSAGE);
                }
                ligne.setID(rs.getString("LINEID"));

                sum += Float.parseFloat(rs.getString("PRIXTOTAL"));
                v.add(ligne.getVector());
//                v.add(vv);

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lecture données : " + ex.getErrorCode() + " / " + ex.getMessage(), "CommandeligneMod", JOptionPane.ERROR_MESSAGE);

        } finally {
            ech.close();
        }
        try {
            sum = sum * 100;
            sum = Math.round(sum);
            sum = sum / 100;

            commande.setMontantGlobal(String.valueOf(sum));
        } catch (DataExceptions ex) {
            JOptionPane.showMessageDialog(null, "Conversion somme : " + ex.getErrorCode() + " / " + ex.getMessage(), "CommandeligneMod", JOptionPane.ERROR_MESSAGE);
        }
        return v;
    }

    private DefaultComboBoxModel initStatus() {
        Vector v = new Vector();

        Xchange ech = new Xchange();
        Statement stat = null;
        String query = "SELECT STATUSID, STATUSNAME,STATUS_DESCR FROM STATUS "
                + "WHERE STATUSID BETWEEN 100 AND 198";
        ResultSet rs = null;

        try {

            ech.connect();
            stat = ech.getConnexion().createStatement();
            rs = stat.executeQuery(query);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Création connexion : " + ex.getErrorCode() + " / " + ex.getMessage(), "CommandeMod", JOptionPane.ERROR_MESSAGE);;
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
            JOptionPane.showMessageDialog(null, "Lecture données : " + ex.getErrorCode() + " / " + ex.getMessage(), "CommandeMod", JOptionPane.ERROR_MESSAGE);
        } catch (DataExceptions ex) {
            JOptionPane.showMessageDialog(null, "Lecture données : " + ex.getErrorCode() + " / " + ex.getMessage(), "CommandeMod", JOptionPane.ERROR_MESSAGE);
        } finally {
            ech.close();
        }

        return new DefaultComboBoxModel(v);
    }

    private DefaultComboBoxModel initAdrFact(String id) {
        Vector v = new Vector();

        Xchange ech = new Xchange();
        Statement stat = null;
        String query = "  SELECT ADDRESSID, ADDRESSNAME, ADDRESSNUMBER, ADDRESSSTREETTYPE, ADDRESSSTREETNAME, ADDRESSPOSTALCODE, ADDRESSCITY, ADDRESSMORE, ad.COUNTRYID, pa.Pays FROM ADRESSE ad JOIN PAYS pa ON ad.COUNTRYID = pa.COUNTRYID WHERE CLI_CUSTOMERID = " + id + "AND ASSRESSACTIVE = 1";
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
        String query = "  SELECT ADDRESSID, ADDRESSNAME, ADDRESSNUMBER, ADDRESSSTREETTYPE, ADDRESSSTREETNAME, ADDRESSPOSTALCODE, ADDRESSCITY, ADDRESSMORE, ad.COUNTRYID, pa.Pays FROM ADRESSE ad JOIN PAYS pa ON ad.COUNTRYID = pa.COUNTRYID WHERE CUSTOMERID = " + id + "AND ADRESSACTIVE = 1";
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

    private DefaultComboBoxModel initClient() {
        Vector v = new Vector();

        Xchange ech = new Xchange();
        Statement stat = null;
        String query = "SELECT CUSTOMERID, CUSTOMERLASTNAME, CUSTOMERFIRSTNAME"
                + ", CUSTOMERMAIL, CUSTOMERPHONE, CUSTOMERNOTES, CUSTOMERREGISTRATIONDATE"
                + ", CUSTOMERBIRTHDATE FROM CLIENT WHERE STATUSID BETWEEN 700 AND 798";
        ResultSet rs = null;

        try {

            ech.connect();
            stat = ech.getConnexion().createStatement();
            rs = stat.executeQuery(query);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Création connexion Client: " + ex.getErrorCode() + " / " + ex.getMessage(), "CommandeMod", JOptionPane.ERROR_MESSAGE);;
        }
        try {
            while (rs.next()) {
                Client cl = new Client();
                cl.setID(rs.getString("CUSTOMERID"));
                cl.setCustomerlastname(rs.getString("CUSTOMERLASTNAME"));
                cl.setCustomerfirstname(rs.getString("CUSTOMERFIRSTNAME"));
                cl.setCustomerbirthdate(transDate(rs.getString("CUSTOMERBIRTHDATE")));
                cl.setCustomermail(rs.getString("CUSTOMERMAIL"));
                cl.setCustomerphone(rs.getString("CUSTOMERPHONE"));
                cl.setCustomerregistrationdate(transDate(rs.getString("CUSTOMERREGISTRATIONDATE")));

                v.add(cl);

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

        jbEnregistrer = new javax.swing.JButton();
        jbFermer = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableLineCmd = new javax.swing.JTable();
        jlMontantRegle = new javax.swing.JLabel();
        jlEtatEnv = new javax.swing.JLabel();
        jtfMontantRegle = new javax.swing.JTextField();
        jlMontantGlobal = new javax.swing.JLabel();
        jtfMontantGlobal = new javax.swing.JTextField();
        jtfEtatEnv = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jlTel = new javax.swing.JLabel();
        jtfTel = new javax.swing.JTextField();
        jcbClient = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jlNom = new javax.swing.JLabel();
        jlPrenom = new javax.swing.JLabel();
        jlDateNais = new javax.swing.JLabel();
        jlMail = new javax.swing.JLabel();
        jtfNom = new javax.swing.JTextField();
        jtfPrenom = new javax.swing.JTextField();
        jtfDateNais = new javax.swing.JTextField();
        jtfMail = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtaNotes = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jlActif = new javax.swing.JLabel();
        jcbStatus = new javax.swing.JComboBox();
        jlCmdNum = new javax.swing.JLabel();
        jlCmdDate = new javax.swing.JLabel();
        jtfCmdNum = new javax.swing.JTextField();
        jlEnvoiNum = new javax.swing.JLabel();
        jtfCmdDate = new javax.swing.JTextField();
        jlDateEnv = new javax.swing.JLabel();
        jtfEnvDate = new javax.swing.JTextField();
        jlDateEnv1 = new javax.swing.JLabel();
        jtfMore = new javax.swing.JTextField();
        jtfEnvoiNum = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jcbAdrExp = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jcbAdrFact = new javax.swing.JComboBox();
        jlAdrExp = new javax.swing.JLabel();
        jlAdrFact = new javax.swing.JLabel();
        jlAdrExpMore = new javax.swing.JLabel();
        jlAdrFactMore = new javax.swing.JLabel();
        jbAjouter = new javax.swing.JButton();
        jbModifier = new javax.swing.JButton();
        jbSupprimer = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

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

        jTableLineCmd.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jTableLineCmd);

        jlMontantRegle.setText("Montant Réglé");

        jlEtatEnv.setText("Etat Envoi");

        jtfMontantRegle.setText("jTextField10");

        jlMontantGlobal.setText("Montant Total");

        jtfMontantGlobal.setText("jTextField11");

        jtfEtatEnv.setText("jTextField1");

        jlTel.setText("Téléphone");

        jcbClient.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcbClient.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbClientItemStateChanged(evt);
            }
        });
        jcbClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbClientActionPerformed(evt);
            }
        });

        jLabel1.setText("Client");

        jlNom.setText("Nom");

        jlPrenom.setText("Prenom");

        jlDateNais.setText("Date Naissance");

        jlMail.setText("Mail");

        jtfNom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfNomActionPerformed(evt);
            }
        });

        jtaNotes.setColumns(20);
        jtaNotes.setRows(5);
        jScrollPane2.setViewportView(jtaNotes);

        jLabel4.setText("Note client");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jlPrenom)
                                .addComponent(jlDateNais)
                                .addComponent(jlNom)
                                .addComponent(jlMail)
                                .addComponent(jlTel)
                                .addComponent(jLabel1))
                            .addGap(25, 25, 25)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jtfMail, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                                .addComponent(jtfDateNais)
                                .addComponent(jtfPrenom)
                                .addComponent(jtfNom)
                                .addComponent(jtfTel)
                                .addComponent(jcbClient, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jcbClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlNom)
                    .addComponent(jtfNom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlPrenom)
                    .addComponent(jtfPrenom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlDateNais)
                    .addComponent(jtfDateNais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlMail)
                    .addComponent(jtfMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlTel)
                    .addComponent(jtfTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jlActif.setText("Statut");

        jcbStatus.setModel(initStatus());

        jlCmdNum.setText("Commande n°");

        jlCmdDate.setText("Date Commande");

        jlEnvoiNum.setText("Envoi n°");

        jlDateEnv.setText("Date Envoi");

        jlDateEnv1.setText("Commentaire");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlCmdNum)
                            .addComponent(jlActif, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlCmdDate)
                            .addComponent(jlEnvoiNum)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlDateEnv)
                            .addComponent(jlDateEnv1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfMore, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfCmdNum, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfCmdDate, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfEnvDate, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfEnvoiNum, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(193, 193, 193))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlActif)
                    .addComponent(jcbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlCmdNum)
                    .addComponent(jtfCmdNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlCmdDate)
                    .addComponent(jtfCmdDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlEnvoiNum)
                    .addComponent(jtfEnvoiNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfEnvDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlDateEnv))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlDateEnv1)
                    .addComponent(jtfMore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jLabel3.setText("Adr Expédition");

        jcbAdrExp.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcbAdrExp.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbAdrExpItemStateChanged(evt);
            }
        });

        jLabel2.setText("Adr Facture");

        jcbAdrFact.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcbAdrFact.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbAdrFactItemStateChanged(evt);
            }
        });

        jlAdrExp.setText("jLabel5");

        jlAdrFact.setText("jLabel5");

        jlAdrExpMore.setText("jLabel5");

        jlAdrFactMore.setText("jLabel6");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jcbAdrExp, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jcbAdrFact, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jlAdrExp, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                            .addComponent(jlAdrExpMore, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jlAdrFact, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                            .addComponent(jlAdrFactMore, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(131, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jcbAdrExp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jcbAdrFact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jlAdrExp, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(jlAdrFact, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlAdrFactMore, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(jlAdrExpMore, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jbAjouter.setText("Ajouter");
        jbAjouter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAjouterActionPerformed(evt);
            }
        });

        jbModifier.setText("Modifier");
        jbModifier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbModifierActionPerformed(evt);
            }
        });

        jbSupprimer.setText("Supprimer");
        jbSupprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSupprimerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jbEnregistrer)
                                .addGap(410, 410, 410)
                                .addComponent(jbFermer))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jlMontantGlobal)
                                .addGap(31, 31, 31)
                                .addComponent(jtfMontantGlobal, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jlMontantRegle)
                                .addGap(18, 18, 18)
                                .addComponent(jtfMontantRegle, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(145, 145, 145)
                                .addComponent(jbAjouter)
                                .addGap(215, 215, 215)
                                .addComponent(jbModifier)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jbSupprimer)))
                        .addGap(18, 18, 18)
                        .addComponent(jlEtatEnv)
                        .addGap(18, 18, 18)
                        .addComponent(jtfEtatEnv, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 722, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbAjouter)
                    .addComponent(jbModifier)
                    .addComponent(jbSupprimer))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlMontantGlobal)
                    .addComponent(jtfMontantGlobal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlMontantRegle)
                    .addComponent(jtfMontantRegle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlEtatEnv)
                    .addComponent(jtfEtatEnv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbEnregistrer)
                    .addComponent(jbFermer))
                .addGap(108, 108, 108))
        );

        setBounds(0, 0, 1177, 817);
    }// </editor-fold>//GEN-END:initComponents

    private void jbFermerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbFermerActionPerformed
        if (!this.button.equals("new")) {
            Xchange ech = new Xchange();
            ech.connect();
            commande.save(ech, commande.genValue());
            ech.close();
            JFC.refreshCmd();
        }

        this.dispose();
    }//GEN-LAST:event_jbFermerActionPerformed

    private void jbEnregistrerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEnregistrerActionPerformed
        //mise à jour de l'objet avec controle d'erreur grâce à error
        int error = 0;

        if (this.button.equals("mod")) {

        }

        //si pas d'erreur ( error = 0) je sauvegarde        
        int n = 0;
        if (error == 0) {
            if (commande.getStatus().getId().equals("199")) {
                n = JOptionPane.showConfirmDialog(rootPane, "ATTENTION vous allez supprimer une commande.\nCette action est irrémediable\nEtes-vous sûr ?", "Suppression", JOptionPane.OK_CANCEL_OPTION);
            }
            if (n == 0) {
                Xchange ech = new Xchange();
                ech.connect();
                commande.save(ech, commande.genValue());
                ech.close();

            }

        }

        //action selon la fenetre mere
        //refresh de la table de la fenetre mère
        this.JFC.refreshCmd();
    }//GEN-LAST:event_jbEnregistrerActionPerformed

    private void jcbClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbClientActionPerformed

;
    }//GEN-LAST:event_jcbClientActionPerformed

    private void jcbClientItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbClientItemStateChanged
        Client cl = (Client) jcbClient.getSelectedItem();

        if (cl != null) {
            jtfNom.setText(cl.getCustomerlastname());
            jtfPrenom.setText(cl.getCustomerfirstname());
            jtfDateNais.setText(cl.getCustomerbirthdate());
            jtfMail.setText(cl.getCustomermail());
            jtfTel.setText(cl.getCustomerphone());

            jcbAdrExp.removeAllItems();
            jcbAdrExp.setModel(initAdrExp(cl.getId()));
            //System.out.println("id du client selectionné : " + cl.getId());
            //System.out.println("nb item dans combo exp : " + jcbAdrExp.getItemCount());
            if (jcbAdrExp.getItemCount() != 0) {
                jcbAdrExp.setSelectedIndex(0);
                Adresse adrExp = (Adresse) jcbAdrExp.getSelectedItem();
                jlAdrExp.setText(adrToString(adrExp));
                jlAdrExpMore.setText(moreToString(adrExp));
            }

            jcbAdrFact.removeAllItems();
            jcbAdrFact.setModel(initAdrFact(cl.getId()));
            if (jcbAdrFact.getItemCount() != 0) {
                jcbAdrFact.setSelectedIndex(0);
                Adresse adrFact = (Adresse) jcbAdrFact.getSelectedItem();
                jlAdrFact.setText(adrToString(adrFact));
                jlAdrFactMore.setText((moreToString(adrFact)));
            }

        }
    }//GEN-LAST:event_jcbClientItemStateChanged

    private void jcbAdrExpItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbAdrExpItemStateChanged

        Adresse adr = (Adresse) jcbAdrExp.getSelectedItem();
        if (adr != null) {
            jlAdrExp.setText(adrToString(adr));
            jlAdrExpMore.setText(moreToString(adr));
        }
    }//GEN-LAST:event_jcbAdrExpItemStateChanged

    private void jcbAdrFactItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbAdrFactItemStateChanged

        Adresse adr = (Adresse) jcbAdrFact.getSelectedItem();
        if (adr != null) {
            jlAdrFact.setText(adrToString(adr));
            jlAdrFactMore.setText(moreToString(adr));
        }
    }//GEN-LAST:event_jcbAdrFactItemStateChanged

    private void jbAjouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAjouterActionPerformed

        if (this.button.equals("new") && commande.getID() == null) {
            Client cl = (Client) jcbClient.getSelectedItem();
            if (cl == null) {
                JOptionPane.showMessageDialog(rootPane, "Veuillez choisir un client", "Choix Client", JOptionPane.INFORMATION_MESSAGE);
            } else {
                commande.setClient(cl);
            }
            Statut st = (Statut) jcbStatus.getSelectedItem();
            if (st == null) {
                JOptionPane.showMessageDialog(rootPane, "Veuillez choisir un status", "Choix Status", JOptionPane.INFORMATION_MESSAGE);
            } else {
                commande.setStatus(st);
            }
            Adresse adrE = (Adresse) jcbAdrExp.getSelectedItem();
            if (adrE == null) {
                JOptionPane.showMessageDialog(rootPane, "Veuillez choisir une adresse de livraison", "Choix Adresse Livraison", JOptionPane.INFORMATION_MESSAGE);
            } else {
                commande.setAddrExp(adrE);
            }
            Adresse adrF = (Adresse) jcbAdrFact.getSelectedItem();
            if (adrF == null) {
                JOptionPane.showMessageDialog(rootPane, "Veuillez choisir une adresse de facturation", "Choix Adresse Facturation", JOptionPane.INFORMATION_MESSAGE);
            } else {
                commande.setAddrInv(adrF);
            }

            Xchange ech = new Xchange();
            Statement stat = null;
            String query = "SELECT * FROM TRANSPORTEUR ";
            ResultSet rs = null;
            Transporteur tr = new Transporteur();
            try {

                ech.connect();
                stat = ech.getConnexion().createStatement();
                rs = stat.executeQuery(query);

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Création connexion : " + ex.getErrorCode() + " / " + ex.getMessage(), "CommandeligneMod", JOptionPane.ERROR_MESSAGE);;
            }
            try {
                while (rs.next()) {

                    tr.setID(rs.getString("SHIPPINGID"));
                    tr.setFraisLivraison(rs.getString("SHIPPINGFEES"));

                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Lecture données : " + ex.getErrorCode() + " / " + ex.getMessage(), "NewCmd", JOptionPane.ERROR_MESSAGE);

            } catch (DataExceptions ex) {
                JOptionPane.showMessageDialog(null, "Création Transporteur : " + ex.getErrorCode() + " / " + ex.getMessage(), "NewCmd", JOptionPane.ERROR_MESSAGE);
            } finally {
                ech.close();
            }
            commande.setTransp(tr);
            try {
                commande.setCmdDate(jtfCmdDate.getText());
                commande.setCoutTransp(tr.getFraisLivraison());
            } catch (DataExceptions ex) {
                JOptionPane.showMessageDialog(null, "commande set : " + ex.getErrorCode() + " / " + ex.getMessage(), "NewCmd", JOptionPane.ERROR_MESSAGE);
            }

            ech.connect();
            commande.save(ech, commande.genValue());
            stat = null;
            rs = null;
            query = "SELECT @@IDENTITY AS Number FROM COMMANDE";
            try {
                stat = ech.getConnexion().createStatement();
                rs = stat.executeQuery(query);

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Création connexion : " + ex.getErrorCode() + " / " + ex.getMessage(), "NewCmd", JOptionPane.ERROR_MESSAGE);;
            }
            try {
                while (rs.next()) {
                    commande.setID(rs.getString("Number"));
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Lecture données : " + ex.getErrorCode() + " / " + ex.getMessage(), "NewCmd", JOptionPane.ERROR_MESSAGE);
                ech.close();
            }
            jtfCmdNum.setText(commande.getID());
        }

        JFLigneMod ajout = new JFLigneMod();
        ajout.setJfMere(this);
        Client cl = (Client) jcbClient.getSelectedItem();
        ajout.setClient(cl);
        ajout.setCommande(commande);
        ajout.setVisible(true);


    }//GEN-LAST:event_jbAjouterActionPerformed

    private void jbModifierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbModifierActionPerformed
        int row = jTableLineCmd.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(rootPane, "Veuillez sélectionner une ligne", "Sélection", JOptionPane.INFORMATION_MESSAGE);
        } else {
            LigneCmd ligne = (LigneCmd) jTableLineCmd.getValueAt(row, 0);
            String qt = JOptionPane.showInputDialog(rootPane, "Veuillez la quantité désirée ", "Quantité", JOptionPane.QUESTION_MESSAGE);
            try {
                ligne.setQuantite(qt);
                //System.out.println("commande ID : " + commande.getID());
                ligne.setCommande(commande);
                ligne.setClient(commande.getClient());

                Xchange ech = new Xchange();
                ech.connect();
                ligne.save(ech, ligne.genValue());
                ech.close();
            } catch (DataExceptions ex) {
                JOptionPane.showMessageDialog(rootPane, "MaJ Quantité", "Ligne de Commande", JOptionPane.INFORMATION_MESSAGE);
            }
            jTableLineCmd.removeAll();
            jTableLineCmd.setModel(initModelLigneCmd());
            jtfMontantGlobal.setText(commande.getMontantGlobal());

        }
    }//GEN-LAST:event_jbModifierActionPerformed

    private void jtfNomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfNomActionPerformed

    }//GEN-LAST:event_jtfNomActionPerformed

    private void jbSupprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSupprimerActionPerformed
        int row = jTableLineCmd.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(rootPane, "Veuillez sélectionner une ligne", "Sélection", JOptionPane.INFORMATION_MESSAGE);
        } else {
            int n;
            n = JOptionPane.showConfirmDialog(rootPane, "ATTENTION vous allez supprimer une Ligne de Commande.\nCette action est irrémediable\nEtes-vous sûr ?", "Suppression", JOptionPane.OK_CANCEL_OPTION);
            if (n == 0) {

                LigneCmd ligne = (LigneCmd) jTableLineCmd.getValueAt(row, 0);
                //System.out.println("LINEID = " + ligne.getID());
                Xchange ech = new Xchange();
                Statement stat = null;

                try {
                    ech.connect();
                    stat = ech.getConnexion().createStatement();
                    String query = "";
                    query = "DELETE FROM LIGNECMD WHERE LINEID = " + ligne.getID();
                    //System.out.println(query);
                    stat.executeUpdate(query);

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Ecriture Table Suppr : " + ex.getErrorCode() + " / " + ex.getMessage(), "LigneCMD", JOptionPane.ERROR_MESSAGE);
                } finally {
                    if (stat != null) {
                        try {
                            stat.close();
                            ech.close();
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Fermeture statement Suppr: " + ex.getErrorCode() + " / " + ex.getMessage(), "LigneCMD", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }

            }
            refresh();
        }


    }//GEN-LAST:event_jbSupprimerActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (!this.button.equals("new")) {
            Xchange ech = new Xchange();
            ech.connect();
            commande.save(ech, commande.genValue());
            ech.close();
            JFC.refreshCmd();
        }
    }//GEN-LAST:event_formWindowClosing

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
            java.util.logging.Logger.getLogger(JFCommandeMod.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFCommandeMod.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFCommandeMod.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFCommandeMod.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFCommandeMod().setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableLineCmd;
    private javax.swing.JButton jbAjouter;
    private javax.swing.JButton jbEnregistrer;
    private javax.swing.JButton jbFermer;
    private javax.swing.JButton jbModifier;
    private javax.swing.JButton jbSupprimer;
    private javax.swing.JComboBox jcbAdrExp;
    private javax.swing.JComboBox jcbAdrFact;
    private javax.swing.JComboBox jcbClient;
    private javax.swing.JComboBox jcbStatus;
    private javax.swing.JLabel jlActif;
    private javax.swing.JLabel jlAdrExp;
    private javax.swing.JLabel jlAdrExpMore;
    private javax.swing.JLabel jlAdrFact;
    private javax.swing.JLabel jlAdrFactMore;
    private javax.swing.JLabel jlCmdDate;
    private javax.swing.JLabel jlCmdNum;
    private javax.swing.JLabel jlDateEnv;
    private javax.swing.JLabel jlDateEnv1;
    private javax.swing.JLabel jlDateNais;
    private javax.swing.JLabel jlEnvoiNum;
    private javax.swing.JLabel jlEtatEnv;
    private javax.swing.JLabel jlMail;
    private javax.swing.JLabel jlMontantGlobal;
    private javax.swing.JLabel jlMontantRegle;
    private javax.swing.JLabel jlNom;
    private javax.swing.JLabel jlPrenom;
    private javax.swing.JLabel jlTel;
    private javax.swing.JTextArea jtaNotes;
    private javax.swing.JTextField jtfCmdDate;
    private javax.swing.JTextField jtfCmdNum;
    private javax.swing.JTextField jtfDateNais;
    private javax.swing.JTextField jtfEnvDate;
    private javax.swing.JTextField jtfEnvoiNum;
    private javax.swing.JTextField jtfEtatEnv;
    private javax.swing.JTextField jtfMail;
    private javax.swing.JTextField jtfMontantGlobal;
    private javax.swing.JTextField jtfMontantRegle;
    private javax.swing.JTextField jtfMore;
    private javax.swing.JTextField jtfNom;
    private javax.swing.JTextField jtfPrenom;
    private javax.swing.JTextField jtfTel;
    // End of variables declaration//GEN-END:variables
}
