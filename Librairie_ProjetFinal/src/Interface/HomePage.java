/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import ObjetsSimples.*;

import exceptions.DataExceptions;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import xchange.Xchange;

/**
 *
 * @author cda219
 */
public class HomePage extends javax.swing.JFrame {

    private JFGestionClient JFGClient = new JFGestionClient();
    private Client cliSelect = new Client();
    String imagePth = null;
    private Commentaire comment;

//        public HomePage getJFGClient() {
//        return this;
//    }
//
//    public void setJFGClient(JFGestionClient JFGClient) {
//        this.HomePage = JFGClient;
//    }
    public Client getCliSelect() {
        return cliSelect;
    }

    public void setCliSelect(Client cliSelect) {
        this.cliSelect = cliSelect;
    }

    /**
     * Creates new form HomePage
     */
    public HomePage() {
        initComponents();
        jpLivres.setVisible(false);
        jpAuteurs.setVisible(false);
        jpEditeurs.setVisible(false);
        jpEvents.setVisible(false);
        jpDivers.setVisible(false);
        jpCommandes.setVisible(false);
        jpClients.setVisible(false);
        jpAvis.setVisible(false);
        jpDivers.setVisible(false);
        IDjLabel.setVisible(false);

//        jPSupprimerLivre.setVisible(false);
//        jPAfficherLivre.setVisible(false);
//        jPModifierLivre.setVisible(false);
//        jPCreerLivre.setVisible(false);
//        jPSupprimerEditeur.setVisible(false);
//        jPAfficherEditeur.setVisible(false);
//        jPModifierEditeur.setVisible(false);
//        jPCreerEditeur.setVisible(false);
//
//        jPSupprimerAuteur.setVisible(false);
//        jPAfficherAuteur.setVisible(false);
//        jPModifierAuteur.setVisible(false);
//        jPCreerAuteur.setVisible(false);
//
//        jPSupprimerCommande.setVisible(false);
//        jPAfficherCommande.setVisible(false);
//        jPModifierCommande.setVisible(false);
//        jPCreerCommande.setVisible(false);
//
//        jPSupprimerClient.setVisible(false);
//        jPAfficherClient.setVisible(false);
//        jPModifierClient.setVisible(false);
//        jPCreerClient.setVisible(false);
    }

    public void refreshComment(String statut) {
        jtAvis.removeAll();
        jtAvis.setModel(initModelCommentaire(statut));
    }

    public DefaultComboBoxModel initCommentStatus() {
        Vector v = new Vector();
        Xchange xch = new Xchange();
        xch.connect();

        try {
            String query = "SELECT STATUSID, STATUSNAME, STATUS_DESCR FROM STATUS WHERE STATUSID BETWEEN 300 AND 399";
            Statement s = xch.getConnexion().createStatement();
            ResultSet rs = s.executeQuery(query);

            while (rs.next()) {
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
        return new DefaultComboBoxModel(v);
    }

    private DefaultTableModel initModelCommentaire(String statut) {
        Vector v = new Vector();
        v.add("Evaluation");
        v.add("Titre");
        v.add("Prenom");
        v.add("Nom");

        try {

            return new DefaultTableModel(initAvis(statut), v) {

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

        } catch (DataExceptions ex) {
            JOptionPane.showMessageDialog(null, "Création Table : " + ex.getErrorCode() + " / " + ex.getMessage(), "Commentaire", JOptionPane.ERROR_MESSAGE);

            return new DefaultTableModel();
        }
    }

    private Vector initAvis(String statut) throws DataExceptions {
        Vector v = new Vector();
        Xchange ech = new Xchange();

        ech.connect();
        String query = "SELECT COMMENTID,co.BOOKISBN13, li.BOOKTITLE, LIG_LINEID,co.CUSTOMERID, cl.CUSTOMERLASTNAME, cl.CUSTOMERFIRSTNAME, co.STATUSID, st.STATUSNAME,st.STATUS_DESCR, EMPLOGINID,COMMENTNOTES,COMMENTEVAL,COMMENTDATE,COMMENTYESCOUNT ,COMMENTNOCOUNT,COMMENTIPADDRESS,STATCOMMENTDATE,STATCOMMENTCOMMENT FROM COMMENTAIRES co JOIN STATUS st ON co.STATUSID = st.STATUSID JOIN LIVRE li ON co.BOOKISBN13 = li.BOOKISBN13 JOIN CLIENT cl ON co.CUSTOMERID = cl.CUSTOMERID WHERE co.STATUSID = " + statut + " ORDER BY COMMENTID";
        //System.out.println(query);
        Statement stmt = null;
        try {
            stmt = ech.getConnexion().createStatement();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Création connexion : " + ex.getErrorCode() + " / " + ex.getMessage(), "Commentaire", JOptionPane.ERROR_MESSAGE);
        }
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(query);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Recupération Table : " + ex.getErrorCode() + " / " + ex.getMessage(), "Commentaire", JOptionPane.ERROR_MESSAGE);
        }

        try {
            while (rs.next()) {
                //System.out.println("je lis rs");
                Commentaire commentaire = new Commentaire();
                commentaire.setID(rs.getString("COMMENTID"));
                commentaire.setNotes(rs.getString("COMMENTNOTES"));
                commentaire.setEval(rs.getString("COMMENTEVAL"));
                commentaire.setDate(rs.getString("COMMENTDATE"));
                commentaire.setYesCount(rs.getString("COMMENTYESCOUNT"));
                commentaire.setNoCount(rs.getString("COMMENTNOCOUNT"));
                commentaire.setIp(rs.getString("COMMENTIPADDRESS"));
                commentaire.setComment(rs.getString("STATCOMMENTCOMMENT"));
                Statut st = new Statut();
                st.setId(rs.getString("STATUSID"));
                st.setStatusname(rs.getString("STATUSNAME"));
                st.setStatusdescr(rs.getString("STATUS_DESCR"));
                commentaire.setStatut(st);
                Employe emp = new Employe();
                emp.setLoginID(rs.getString("EMPLOGINID"));
                commentaire.setEmploye(emp);
                Livre livre = new Livre();
                livre.setIsbn13(rs.getString("BOOKISBN13"));
                livre.setTitle(rs.getString("BOOKTITLE"));
                commentaire.setLivre(livre);
                Client client = new Client();
                client.setID(rs.getString("CUSTOMERID"));
                client.setCustomerfirstname(rs.getString("CUSTOMERFIRSTNAME"));
                client.setCustomerlastname(rs.getString("CUSTOMERLASTNAME"));
                commentaire.setClient(client);
                LigneCmd ligne = new LigneCmd();
                ligne.setID(rs.getString("LIG_LINEID"));
                commentaire.setLigne(ligne);
                //System.out.println("commentaire : "+ commentaire);
                v.add(commentaire.getVector());

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lecture Table : " + ex.getErrorCode() + " / " + ex.getMessage(), "Commentaire", JOptionPane.ERROR_MESSAGE);
        }

        ech.close(); // ferme la connection a la BDD

        return v;
    }

    public void refreshClient() {
        jTableClients.removeAll();
        jTableClients.setModel(initModelClient());
    }

    private DefaultTableModel initModelClient() {
        Vector v = new Vector();
        v.add("ID Client");
        v.add("Prénom");
        v.add("Nom");
        v.add("Téléphone");
        v.add("Mail");
        v.add("Date d'inscription");
        v.add("Status");

        try {
            return new DefaultTableModel(initClient(), v) {

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
        } catch (DataExceptions ex) {
            JOptionPane.showMessageDialog(null, "Création Table : " + ex.getErrorCode() + " / " + ex.getMessage(), "Client", JOptionPane.ERROR_MESSAGE);

            return new DefaultTableModel();
        }
    }

    private Vector initClient() throws DataExceptions {
        Vector v = new Vector();
        Xchange ech = new Xchange();

        ech.connect();
        String query = "SELECT CUSTOMERID, CUSTOMERLASTNAME, CUSTOMERFIRSTNAME, CUSTOMERPHONE, CUSTOMERMAIL, CONVERT(varchar,CUSTOMERBIRTHDATE, 103) AS CUSTOMERBIRTHDATE, CONVERT(varchar,CUSTOMERREGISTRATIONDATE, 103) AS CUSTOMERREGISTRATIONDATE, CUSTOMERNOTES, st.STATUSNAME, st.STATUSID "
                + "from CLIENT client "
                + "JOIN STATUS st ON client.STATUSID = st.STATUSID WHERE client.STATUSID != 799 ORDER BY CUSTOMERLASTNAME";

        Statement stmt = null;
        try {
            stmt = ech.getConnexion().createStatement();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Création connexion : " + ex.getErrorCode() + " / " + ex.getMessage(), "Client", JOptionPane.ERROR_MESSAGE);
        }
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(query);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Recupération Table : " + ex.getErrorCode() + " / " + ex.getMessage(), "Client", JOptionPane.ERROR_MESSAGE);
        }

        try {
            while (rs.next()) {
                Client client = new Client();
                client.setID(rs.getString("CUSTOMERID"));
                client.setCustomerlastname(rs.getString("CUSTOMERLASTNAME"));
                client.setCustomerfirstname(rs.getString("CUSTOMERFIRSTNAME"));
                client.setCustomerphone(rs.getString("CUSTOMERPHONE"));
                client.setCustomermail(rs.getString("CUSTOMERMAIL"));
                client.setCustomerbirthdate(rs.getString("CUSTOMERBIRTHDATE"));
                client.setCustomerregistrationdate(rs.getString("CUSTOMERREGISTRATIONDATE"));
                client.setCustomerNote(rs.getString("CUSTOMERNOTES"));
                Statut st = new Statut();
                st.setId(rs.getString("STATUSID"));
                st.setStatusname(rs.getString("STATUSNAME"));
                client.setStatus(st);

                v.add(client.getVector());

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lecture Table : " + ex.getErrorCode() + " / " + ex.getMessage(), "Client", JOptionPane.ERROR_MESSAGE);
        }

        ech.close(); // ferme la connection a la BDD

        return v;
    }

    public void refreshLivre() {
        jTableLivres.removeAll();
        jTableLivres.setModel(initModelLivres());
    }

    private DefaultTableModel initModelLivres() {
        Vector v = new Vector();

        v.add("Titre");
        v.add("Sous-titres");
        v.add("Serie");
        v.add("Auteur");
        v.add("Editeur");
        v.add("Collection");
        v.add("Pages");
        v.add("Format");
        v.add("Thème");
        v.add("Sous-thème");
        v.add("Date de sortie");
        v.add("Quantité");
        v.add("Prix");
        v.add("Statut");

        try {
            return new DefaultTableModel(initLivres(), v) {

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
        } catch (DataExceptions ex) {

            JOptionPane.showMessageDialog(null, "Creation table : " + ex.getErrorCode() + " / " + ex.getMessage(), "Livre", JOptionPane.ERROR_MESSAGE);
            return new DefaultTableModel();
        }
    }

    private Vector initLivres() throws DataExceptions {
        Vector v = new Vector();
        Xchange ech = new Xchange();

        ech.connect();
//        String query = "SELECT BOOKISBN13, BOOKTITLE, BOOKSUBTITLE, BOOKISBN10, at.AUTHORID, at.AUTHORFIRSTNAME ,at.AUTHORLASTNAME, pb.PUBLISHERID, pb.EDITEUR_NOM,ec.PUBLISHERCOLLID, ec.PUBLISHERCOLLNAME, BOOKPAGING,fr.FORMATID, fr.FORMATNAME,CONVERT(VARCHAR,BOOKRELEASEDATE,103) as DATEDESORTIE, BOOKSYNOPSIS, BOOKAVAILABLESTOCK, BOOKPRICE, BOOKNOTES, st.STATUSID, st.STATUSNAME FROM LIVRE lv"
//                + " JOIN AUTEUR at on lv.AUTHORID = at.AUTHORID "
//                + " JOIN EDITEURCOLLECTION ec on lv.PUBLISHERCOLLID = ec.PUBLISHERCOLLID"
//                + " JOIN EDITEUR pb on ec.PUBLISHERID = pb.PUBLISHERID"
//                + " JOIN FORMAT fr on lv.FORMATID = fr.FORMATID"
//                + " JOIN STATUS st on lv.STATUSID = st.STATUSID";

        String query = "SELECT lv.BOOKISBN13, BOOKVOLID, GRIDID, BOOKTITLE, BOOKSUBTITLE, BOOKSERIES, BOOKISBN10, at.AUTHORID, at.AUTHORFIRSTNAME ,at.AUTHORLASTNAME, pb.PUBLISHERID, pb.EDITEUR_NOM,ec.PUBLISHERCOLLID, ec.PUBLISHERCOLLNAME, BOOKPAGING, fr.FORMATID, fr.FORMATNAME, th.THEMEID, th.THEMENAME, ssth.SOUS_THEMEID,th2.THEMENAME as SOUS_THEMENAME  ,CONVERT(VARCHAR,BOOKRELEASEDATE,103) as DATEDESORTIE, BOOKSYNOPSIS, BOOKAVAILABLESTOCK, BOOKPRICE, BOOKNOTES, st.STATUSID, st.STATUSNAME FROM LIVRE lv"
                + " JOIN AUTEUR at on lv.AUTHORID = at.AUTHORID "
                + " JOIN EDITEURCOLLECTION ec on lv.PUBLISHERCOLLID = ec.PUBLISHERCOLLID "
                + " JOIN EDITEUR pb on ec.PUBLISHERID = pb.PUBLISHERID "
                + " JOIN FORMAT fr on lv.FORMATID = fr.FORMATID "
                + " JOIN POSSEDER ps on lv.BOOKISBN13 = ps.BOOKISBN13 "
                + " JOIN INCLURE ssth on ps.THEMEID = ssth.SOUS_THEMEID"
                + " JOIN THEME th on ssth.THEMEID = th.THEMEID "
                + " JOIN THEME th2 on ssth.SOUS_THEMEID = th2.THEMEID "
                + " JOIN STATUS st on lv.STATUSID = st.STATUSID "
                + " WHERE lv.STATUSID != 1010 ";

        Statement stmt = null;
        try {
            stmt = ech.getConnexion().createStatement();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Création connexion : " + ex.getErrorCode() + " / " + ex.getMessage(), "Editeur", JOptionPane.ERROR_MESSAGE);
        }
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(query);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Recupération Table : " + ex.getErrorCode() + " / " + ex.getMessage(), "Editeur", JOptionPane.ERROR_MESSAGE);
        }

        try {
            while (rs.next()) {
                Livre lv = new Livre();
                lv.setIsbn13(rs.getString("BOOKISBN13"));
                lv.setTitle(rs.getString("BOOKTITLE"));
                lv.setSubTitle(rs.getString("BOOKSUBTITLE"));
                lv.setSerie(rs.getString("BOOKSERIES"));
                lv.setIsbn10(rs.getString("BOOKISBN10"));
                LivreVolume vol = new LivreVolume();
                vol.setId(rs.getString("BOOKVOLID"));
                lv.setBookVol(vol);
                Grille grid = new Grille();
                grid.setGridID(rs.getString("GRIDID"));
                lv.setGrid(grid);
                Auteur at = new Auteur();
                at.setID(rs.getString("AUTHORID"));
                at.setFirstName(rs.getString("AUTHORFIRSTNAME"));
                at.setLastName(rs.getString("AUTHORLASTNAME"));
                lv.setAuthor(at);
                Editeur ed = new Editeur();
                ed.setID(rs.getString("PUBLISHERID"));
                ed.setName(rs.getString("EDITEUR_NOM"));
                EditeurCollection ec = new EditeurCollection();
                ec.setPublisher(ed);
                ec.setPublisherCollID(rs.getString("PUBLISHERCOLLID"));
                ec.setPublisherCollName(rs.getString("PUBLISHERCOLLNAME"));
                lv.setPublisherColl(ec);
                lv.setPaging(rs.getString("BOOKPAGING"));
                Format frmt = new Format();
                frmt.setId(rs.getString("FORMATID"));
                frmt.setFormatname(rs.getString("FORMATNAME"));
                lv.setFormat(frmt);
                Theme theme = new Theme();
                theme.setID(rs.getString("THEMEID"));
                theme.setName(rs.getString("THEMENAME"));
                lv.setTheme(theme);
                Theme sstheme = new Theme();
                sstheme.setID(rs.getString("SOUS_THEMEID"));
                sstheme.setName(rs.getString("SOUS_THEMENAME"));
                lv.setSoustheme(sstheme);
                lv.setReleaseDate(rs.getString("DATEDESORTIE"));
                lv.setSynopsis(rs.getString("BOOKSYNOPSIS"));
                lv.setAvailableStock(rs.getString("BOOKAVAILABLESTOCK"));
                lv.setPrice(rs.getString("BOOKPRICE"));
                lv.setNotes(rs.getString("BOOKNOTES"));
                Statut st = new Statut();
                st.setId(rs.getString("STATUSID"));
                st.setStatusname(rs.getString("STATUSNAME"));
                lv.setStatut(st);

                v.add(lv.getVector());

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lecture Table : " + ex.getErrorCode() + " / " + ex.getMessage(), "Editeur", JOptionPane.ERROR_MESSAGE);
        }

        ech.close();

        return v;
    }

    public void refreshTableEvents() {
        EventjTable.removeAll();
        EventjTable.setModel(initModelEvents());
    }

    private DefaultTableModel initModelEvents() {
        Vector v = new Vector();
        v.add("Nom");
        v.add("Date de début");
        v.add("Date de fin");
        v.add("Statut");
        return new DefaultTableModel(initVectorEvents(), v) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private Vector initVectorEvents() {
        Vector v = new Vector();
        Xchange xch = new Xchange();
        xch.connect();

        try {
            String query = "SELECT EVENTID, EVENTNAME, CONVERT(varchar,EVENTSTARTINGDATE,103) as EVENTSTARTINGDATE, CONVERT(varchar,EVENTENDINGDATE,103) as EVENTENDINGDATE, EVENTDISCOUNT, EVENTDESCR, EVENTPICTURE, EVENTNOTES, status.STATUSID, STATUSNAME "
                    + "FROM EVENEMENT event JOIN STATUS status ON event.STATUSID = status.STATUSID "
                    + "WHERE event.STATUSID between 200 and 298";
            Statement s = xch.getConnexion().createStatement();
            ResultSet rs = s.executeQuery(query);

            while (rs.next()) {
                Event e = new Event();
                try {
                    e.setEventID(rs.getString("EVENTID"));
                    e.setName(rs.getString("EVENTNAME"));
                    e.setStarterDate(rs.getString("EVENTSTARTINGDATE"));
                    e.setEndingDate(rs.getString("EVENTENDINGDATE"));
                    e.setDiscount(rs.getFloat("EVENTDISCOUNT"));
                    e.setDescription(rs.getString("EVENTDESCR"));
                    e.setImage(rs.getBytes("EVENTPICTURE"));
                    e.setNote(rs.getString("EVENTNOTES"));

                    Statut status = new Statut();
                    status.setId(rs.getString("STATUSID"));
                    status.setStatusname(rs.getString("STATUSNAME"));
                    e.setStatut(status);
                } catch (DataExceptions ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
                v.add(e.getVector());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        xch.close();
        System.out.println("done");
        return v;
    }

    public DefaultComboBoxModel initModelStatus() {
        return new DefaultComboBoxModel(initVectorStatus());
    }

    public Vector initVectorStatus() {
        Vector v = new Vector();
        Xchange xch = new Xchange();
        xch.connect();

        try {
            String query = "SELECT STATUSID, STATUSNAME, STATUS_DESCR FROM STATUS WHERE STATUSID BETWEEN 200 AND 298";
            Statement s = xch.getConnexion().createStatement();
            ResultSet rs = s.executeQuery(query);

            while (rs.next()) {
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

    private DefaultTableModel initModelAuteur() {
        Vector v = new Vector();
        v.add("Nom");
        v.add("Prénom");
        v.add("Statut");
        return new DefaultTableModel(initVectorAuteur(), v) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private Vector initVectorAuteur() {
        Vector v = new Vector();
        Xchange xch = new Xchange();
        xch.connect();

        try {
            String query = "SELECT AUTHORID, AUTHORLASTNAME, AUTHORFIRSTNAME, AUTHORBIO, AUTHORPICTURE, AUTHORNOTES, status.STATUSID, STATUSNAME "
                    + "FROM AUTEUR auteur JOIN STATUS status ON auteur.STATUSID = status.STATUSID "
                    + "WHERE auteur.STATUSID between 500 and 598";
            Statement s = xch.getConnexion().createStatement();
            ResultSet rs = s.executeQuery(query);

            while (rs.next()) {
                Auteur a = new Auteur();
                try {
                    a.setID(rs.getString("AUTHORID"));
                    a.setLastName(rs.getString("AUTHORLASTNAME"));
                    a.setFirstName(rs.getString("AUTHORFIRSTNAME"));
                    a.setBio(rs.getString("AUTHORBIO"));
                    a.setPicture(rs.getBytes("AUTHORPICTURE"));
                    a.setNotes(rs.getString("AUTHORNOTES"));

                    Statut status = new Statut();
                    status.setId(rs.getString("STATUSID"));
                    status.setStatusname(rs.getString("STATUSNAME"));
                    a.setStatus(status);
                } catch (DataExceptions ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
                v.add(a.getVector());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        xch.close();
        //System.out.println("done");
        return v;
    }

    public void refreshTable() {
        jTableAuteurs.removeAll();
        jTableAuteurs.setModel(initModelAuteur());
    }

    public void refreshEdit() {
        jTableEditeurs.removeAll();
        jTableEditeurs.setModel(initModelEditeur());
    }

    private DefaultTableModel initModelEditeur() {
        Vector v = new Vector();
        v.add("Nom");
        v.add("Adresse");
        v.add("Téléphone");
        v.add("Status");

        try {
            return new DefaultTableModel(initEditeur(), v) {

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
        } catch (DataExceptions ex) {

            return new DefaultTableModel();
        }
    }

    private Vector initEditeur() throws DataExceptions {
        Vector v = new Vector();
        Xchange ech = new Xchange();

        ech.connect();
        String query = "SELECT PUBLISHERID, EDITEUR_NOM, PUBLISHERMAIL, PUBLISHERPHONE, PUBLISHERNOTES, st.STATUSNAME, st.STATUSID "
                + "from EDITEUR ed "
                + "JOIN STATUS st ON ed.STATUSID = st.STATUSID WHERE ed.STATUSID != 699 ORDER BY EDITEUR_NOM";

        Statement stmt = null;
        try {
            stmt = ech.getConnexion().createStatement();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Création connexion : " + ex.getErrorCode() + " / " + ex.getMessage(), "Editeur", JOptionPane.ERROR_MESSAGE);
        }
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(query);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Recupération Table : " + ex.getErrorCode() + " / " + ex.getMessage(), "Editeur", JOptionPane.ERROR_MESSAGE);
        }

        try {
            while (rs.next()) {
                Editeur ed = new Editeur();
                ed.setID(rs.getString("PUBLISHERID"));
                ed.setName(rs.getString("EDITEUR_NOM"));
                ed.setMail(rs.getString("PUBLISHERMAIL"));
                ed.setTel(rs.getString("PUBLISHERPHONE"));
                ed.setNotes(rs.getString("PUBLISHERNOTES"));
                Statut st = new Statut();
                st.setId(rs.getString("STATUSID"));
                st.setStatusname(rs.getString("STATUSNAME"));
                ed.setStatus(st);

                v.add(ed.getVector());

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lecture Table : " + ex.getErrorCode() + " / " + ex.getMessage(), "Editeur", JOptionPane.ERROR_MESSAGE);
        }

        ech.close();

        return v;
    }

    private String transDate(String d) {
        if (d != null) {
            String[] da = d.split(" ");
            String dat[] = da[0].split("-");
            String date = dat[2] + "/" + dat[1] + "/" + dat[0];
            return date;
        } else {
            return null;
        }
    }

    public void refreshCmd() {
        jTableCommande.removeAll();
        jTableCommande.setModel(initModelCommande());
    }

    private DefaultTableModel initModelCommande() {
        Vector v = new Vector();
        v.add("Commande n°");
        v.add("Date de CMD");
        v.add("Nom");
        v.add("Prénom");
        v.add("Email");
        v.add("Montant total");
        v.add("Montant réglé");
        v.add("Etat Envoi");
        v.add("Status");

        try {
            return new DefaultTableModel(initCommande(), v) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
        } catch (DataExceptions ex) {
            JOptionPane.showMessageDialog(null, "Création table : " + ex.getErrorCode() + " / " + ex.getMessage(), "Commande", JOptionPane.ERROR_MESSAGE);
            return new DefaultTableModel();
        }
    }

    private Vector initCommande() throws DataExceptions {
        Vector v = new Vector();
        Xchange ech = new Xchange();

        ech.connect();
        String query = "SELECT ORDERID,CUSTOMERLASTNAME,CUSTOMERFIRSTNAME"
                + ",CUSTOMERMAIL, CUSTOMERBIRTHDATE,CUSTOMERPHONE,CUSTOMERNOTES"
                + ",CUSTOMERREGISTRATIONDATE, co.CUSTOMERID, ADDRESSINVOICEID, adi.ADDRESSCITY, adi.ADDRESSMORE"
                + ", adi.ADDRESSNAME, adi.ADDRESSNUMBER, adi.ADDRESSPOSTALCODE"
                + ", adi.ADDRESSSTREETNAME, adi.ADDRESSSTREETTYPE, adi.COUNTRYID"
                + ", co.ADDRESSSHIPPID, ads.ADDRESSCITY  ADDRESSCITYSH"
                + ", ads.ADDRESSMORE  ADDRESSMORESH, ads.ADDRESSNAME  ADDRESSNAMESH"
                + ", ads.ADDRESSNUMBER  ADDRESSNUMBERSH, ads.ADDRESSPOSTALCODE  ADDRESSPOSTALCODESH"
                + ", ads.ADDRESSSTREETNAME  ADDRESSSTREETNAMESH, ads.ADDRESSSTREETTYPE ADDRESSSTREETTYPESH  "
                + ", ads.COUNTRYID as COUNTRYIDSH,co.STATUSID,STATUSNAME,STATUS_DESCR"
                + ",co.SHIPPINGID,SHIPPINGCARRIERNAME,tr.SHIPPINGFEES,ORDERDATE"
                + ",ORDERGLOBALAMOUNT,ORDERSHIPPINGFEES,ORDERSHIPPINGDATE"
                + ",ORDERPAYMENTCOUNT,ORDERPAYMENTAMOUNT,ORDERPAYMENTDATE"
                + ",ORDERCUSTOMERIPADDRESS,ORDERNOTES,ORDERSHIPPNUMBER"
                + ",ORDERSHIPPSTAT,STATORDERDATE,STATORDERCOMMENT  "
                + "FROM COMMANDE co  JOIN CLIENT cl ON co.CUSTOMERID = cl.CUSTOMERID  "
                + "JOIN ADRESSE adi ON co.ADDRESSINVOICEID = adi.ADDRESSID  "
                + "JOIN ADRESSE ads ON co.ADDRESSSHIPPID = ads.ADDRESSID  "
                + "JOIN STATUS st ON co.STATUSID = st.STATUSID  "
                + "JOIN TRANSPORTEUR tr ON co.SHIPPINGID = tr.SHIPPINGID "
                + "JOIN PAYS pai ON adi.COUNTRYID = pai.COUNTRYID "
                + "JOIN PAYS pas ON ads.COUNTRYID = pas.COUNTRYID WHERE co.STATUSID BETWEEN 100 AND 198";

        Statement stmt = null;
        try {
            stmt = ech.getConnexion().createStatement();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Création connexion : " + ex.getErrorCode() + " / " + ex.getMessage(), "Commande", JOptionPane.ERROR_MESSAGE);
        }
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(query);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Recupération Table : " + ex.getErrorCode() + " / " + ex.getMessage(), "Commande", JOptionPane.ERROR_MESSAGE);
        }

        try {
            while (rs.next()) {
                Commande cmd = new Commande();
                cmd.setID(rs.getString("ORDERID"));
                cmd.setCmdDate(transDate(rs.getString("ORDERDATE")));
                cmd.setCoutTransp(rs.getString("ORDERSHIPPINGFEES"));
                cmd.setEtatEnvoi(rs.getString("ORDERSHIPPSTAT"));
                if (rs.getString("ORDERGLOBALAMOUNT") == null) {
                    cmd.setMontantGlobal("0");
                } else {
                    cmd.setMontantGlobal(rs.getString("ORDERGLOBALAMOUNT"));
                }
                if (rs.getString("ORDERPAYMENTAMOUNT") == null) {
                    cmd.setMontantPaiement("0");
                } else {
                    cmd.setMontantPaiement(rs.getString("ORDERPAYMENTAMOUNT"));

                }

                cmd.setNotes(rs.getString("ORDERNOTES"));
                cmd.setNumEnvoi(rs.getString("ORDERSHIPPNUMBER"));
                cmd.setPaiementDate(transDate(rs.getString("ORDERPAYMENTDATE")));
                cmd.setTranspDate(transDate(rs.getString("ORDERSHIPPINGDATE")));
                cmd.setClientIP(rs.getString("ORDERSHIPPSTAT"));

                Statut st = new Statut();
                st.setId(rs.getString("STATUSID"));
                st.setStatusname(rs.getString("STATUSNAME"));
                cmd.setStatus(st);

                Adresse adrE = new Adresse();
                adrE.setAddrID(rs.getString("ADDRESSSHIPPID"));
                adrE.setCity(rs.getString("ADDRESSCITYSH"));
                //adrE.setMore(rs.getString("ADDRESSMORESH"));
                adrE.setName(rs.getString("ADDRESSNAMESH"));
                adrE.setNumber(rs.getString("ADDRESSNUMBERSH"));
                adrE.setPostalCode(rs.getString("ADDRESSPOSTALCODESH"));
                adrE.setStreetName(rs.getString("ADDRESSSTREETNAMESH"));
                adrE.setStreetType(rs.getString("ADDRESSSTREETTYPESH"));
                cmd.setAddrExp(adrE);

                Adresse adrI = new Adresse();
                adrI.setAddrID(rs.getString("ADDRESSINVOICEID"));
                adrI.setCity(rs.getString("ADDRESSCITY"));
                //adrI.setMore(rs.getString("ADDRESSMORE"));
                adrI.setName(rs.getString("ADDRESSNAME"));
                adrI.setNumber(rs.getString("ADDRESSNUMBER"));
                adrI.setPostalCode(rs.getString("ADDRESSPOSTALCODE"));
                adrI.setStreetName(rs.getString("ADDRESSSTREETNAME"));
                adrI.setStreetType(rs.getString("ADDRESSSTREETTYPE"));
                cmd.setAddrInv(adrI);

                Client cl = new Client();
                cl.setCustomerlastname(rs.getString("CUSTOMERLASTNAME"));
                cl.setCustomerfirstname(rs.getString("CUSTOMERFIRSTNAME"));
                cl.setCustomermail(rs.getString("CUSTOMERMAIL"));
                cl.setCustomerbirthdate(transDate(rs.getString("CUSTOMERBIRTHDATE")));
                cl.setCustomerphone(rs.getString("CUSTOMERPHONE"));
                cl.setCustomerregistrationdate(transDate(rs.getString("CUSTOMERREGISTRATIONDATE")));
                cl.setID(rs.getString("CUSTOMERID"));
                //cl.setCustomernotes(rs.getString("CUSTOMERNOTES"));

                cmd.setClient(cl);

                Transporteur tr = new Transporteur();
                tr.setFraisLivraison(rs.getString("SHIPPINGFEES"));
                tr.setID(rs.getString("SHIPPINGID"));
                tr.setName(rs.getString("SHIPPINGCARRIERNAME"));
                cmd.setTransp(tr);

                v.add(cmd.getVector());

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lecture Table : " + ex.getErrorCode() + " / " + ex.getMessage(), "Commande", JOptionPane.ERROR_MESSAGE);
        }

        ech.close();

        return v;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        IDjLabel = new javax.swing.JLabel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jPanelTitre = new javax.swing.JPanel();
        jLabTitre = new javax.swing.JLabel();
        jbLivres = new javax.swing.JButton();
        jpLivres = new javax.swing.JPanel();
        jScrollTableLivres = new javax.swing.JScrollPane();
        jTableLivres = new javax.swing.JTable();
        jlabSelectionnerLivre = new javax.swing.JLabel();
        jbAfficherLivre = new javax.swing.JButton();
        jbModifierLivre = new javax.swing.JButton();
        jbSupprimerLivre = new javax.swing.JButton();
        jbCreeLivre = new javax.swing.JButton();
        jbEditeurs = new javax.swing.JButton();
        jpEditeurs = new javax.swing.JPanel();
        jScrollTableEditeurs = new javax.swing.JScrollPane();
        jTableEditeurs = new javax.swing.JTable();
        jlabSelectionnerEditeur = new javax.swing.JLabel();
        jbAfficherEditeur = new javax.swing.JButton();
        jbModifierEditeur = new javax.swing.JButton();
        jbSupprimerEditeur = new javax.swing.JButton();
        jbCreeEditeur = new javax.swing.JButton();
        jbAuteurs = new javax.swing.JButton();
        jpAuteurs = new javax.swing.JPanel();
        jScrollTableAuteurs = new javax.swing.JScrollPane();
        jTableAuteurs = new javax.swing.JTable();
        jlabSelectionnerAuteurs = new javax.swing.JLabel();
        jbAfficheAuteur = new javax.swing.JButton();
        jbModifierAuteur = new javax.swing.JButton();
        jbSupprimerAuteur = new javax.swing.JButton();
        jbCreerAuteur = new javax.swing.JButton();
        jbCommandes = new javax.swing.JButton();
        jpCommandes = new javax.swing.JPanel();
        jScrollTableCommandes = new javax.swing.JScrollPane();
        jTableCommande = new javax.swing.JTable();
        jlabSelectionnerCommandes = new javax.swing.JLabel();
        jbAfficherCommande = new javax.swing.JButton();
        jbModifierCommande = new javax.swing.JButton();
        jbSupprimerCommande = new javax.swing.JButton();
        jbCreerCommande = new javax.swing.JButton();
        jbClients = new javax.swing.JButton();
        jpClients = new javax.swing.JPanel();
        jScrollTableClients = new javax.swing.JScrollPane();
        jTableClients = new javax.swing.JTable();
        jlabSelectionnerClients = new javax.swing.JLabel();
        jbAfficherClient = new javax.swing.JButton();
        jbModifieClient = new javax.swing.JButton();
        jbSupprimerClient = new javax.swing.JButton();
        jbCreerClient = new javax.swing.JButton();
        jbFermerJFClients = new javax.swing.JButton();
        jbEvents = new javax.swing.JButton();
        jpEvents = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        EventjTable = new javax.swing.JTable();
        namejLabel = new javax.swing.JLabel();
        statusjLabel = new javax.swing.JLabel();
        date1jLabel = new javax.swing.JLabel();
        date2jLabel = new javax.swing.JLabel();
        discountjLabel = new javax.swing.JLabel();
        descrjLabel = new javax.swing.JLabel();
        notesjLabel = new javax.swing.JLabel();
        picjLabel = new javax.swing.JLabel();
        namejTextField = new javax.swing.JTextField();
        StatusjComboBox = new javax.swing.JComboBox();
        date1jTextField = new javax.swing.JTextField();
        date2jTextField = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        descrjTextArea = new javax.swing.JTextArea();
        discountjTextField = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        notesjTextArea = new javax.swing.JTextArea();
        imagejLabel = new javax.swing.JLabel();
        browsejButton = new javax.swing.JButton();
        addjButton = new javax.swing.JButton();
        editjButton = new javax.swing.JButton();
        deletejButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        canceljButton = new javax.swing.JButton();
        jbNouveau = new javax.swing.JButton();
        jbAvis = new javax.swing.JButton();
        jpAvis = new javax.swing.JPanel();
        jpAvisSelection = new javax.swing.JPanel();
        jcbAvisSt = new javax.swing.JComboBox();
        jlSelectionavis = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jbAfficher = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtAvis = new javax.swing.JTable();
        jpAvisAffichage = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtaDetailAvis = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jbValiderAvis = new javax.swing.JButton();
        jbRefuser = new javax.swing.JButton();
        jtfAvisStatut = new javax.swing.JTextField();
        jbFermer = new javax.swing.JButton();
        jbDivers = new javax.swing.JButton();
        jpDivers = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel12 = new javax.swing.JPanel();
        StatusjComboBox2 = new javax.swing.JComboBox();
        lastNamejLabel1 = new javax.swing.JLabel();
        firstNamejLabel1 = new javax.swing.JLabel();
        picturejLabel1 = new javax.swing.JLabel();
        notesjLabel2 = new javax.swing.JLabel();
        lastNamejTextField1 = new javax.swing.JTextField();
        firstNamejTextField1 = new javax.swing.JTextField();
        notesjScrollPane1 = new javax.swing.JScrollPane();
        notesjTextArea2 = new javax.swing.JTextArea();
        imagejLabel2 = new javax.swing.JLabel();
        editjButton2 = new javax.swing.JButton();
        canceljButton1 = new javax.swing.JButton();
        BrowsejButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jlabSelectionnerLivre1 = new javax.swing.JLabel();
        jbModifierMotClé = new javax.swing.JButton();
        jbSupprimerMC = new javax.swing.JButton();
        jbCreerMC = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        IDjLabel.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jLayeredPane1.setBackground(new java.awt.Color(204, 255, 255));
        getContentPane().add(jLayeredPane1);
        jLayeredPane1.setBounds(10, 150, 1300, 680);

        jPanelTitre.setBackground(new java.awt.Color(0, 102, 153));

        jLabTitre.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabTitre.setForeground(new java.awt.Color(255, 255, 255));
        jLabTitre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabTitre.setText("NOTRE INTERFACE LIBRAIRIE");

        javax.swing.GroupLayout jPanelTitreLayout = new javax.swing.GroupLayout(jPanelTitre);
        jPanelTitre.setLayout(jPanelTitreLayout);
        jPanelTitreLayout.setHorizontalGroup(
            jPanelTitreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTitreLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabTitre, javax.swing.GroupLayout.PREFERRED_SIZE, 1258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanelTitreLayout.setVerticalGroup(
            jPanelTitreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTitreLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabTitre, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jLabTitre.getAccessibleContext().setAccessibleParent(this);

        getContentPane().add(jPanelTitre);
        jPanelTitre.setBounds(0, 11, 1320, 100);

        jbLivres.setBackground(new java.awt.Color(0, 102, 153));
        jbLivres.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jbLivres.setForeground(new java.awt.Color(255, 255, 255));
        jbLivres.setText("Livres");
        jbLivres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbLivresActionPerformed(evt);
            }
        });
        getContentPane().add(jbLivres);
        jbLivres.setBounds(0, 117, 176, 25);

        jScrollTableLivres.setBackground(new java.awt.Color(255, 255, 255));

        jTableLivres.setModel(initModelLivres());
        jTableLivres.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTableLivres.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableLivres.getColumnModel().getColumn(4).setPreferredWidth(15);
        jTableLivres.getColumnModel().getColumn(5).setPreferredWidth(15);
        jTableLivres.getColumnModel().getColumn(6).setPreferredWidth(15);
        jTableLivres.getColumnModel().getColumn(7).setPreferredWidth(25);
        jTableLivres.getColumnModel().getColumn(8).setPreferredWidth(20);
        jTableLivres.getColumnModel().getColumn(9).setPreferredWidth(30);
        jTableLivres.getColumnModel().getColumn(10).setPreferredWidth(15);
        jTableLivres.getColumnModel().getColumn(11).setPreferredWidth(15);
        jTableLivres.getColumnModel().getColumn(12).setPreferredWidth(15);
        jScrollTableLivres.setViewportView(jTableLivres);

        jlabSelectionnerLivre.setText("Veuillez selectionner une ligne pour choisir le livre");

        jbAfficherLivre.setText("Afficher le livre");
        jbAfficherLivre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAfficherLivreActionPerformed(evt);
            }
        });

        jbModifierLivre.setText("Modifier le livre");
        jbModifierLivre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbModifierLivreActionPerformed(evt);
            }
        });

        jbSupprimerLivre.setText("Supprimer le livre");
        jbSupprimerLivre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSupprimerLivreActionPerformed(evt);
            }
        });

        jbCreeLivre.setText("Créer un nouveau livre ");
        jbCreeLivre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCreeLivreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpLivresLayout = new javax.swing.GroupLayout(jpLivres);
        jpLivres.setLayout(jpLivresLayout);
        jpLivresLayout.setHorizontalGroup(
            jpLivresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpLivresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpLivresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpLivresLayout.createSequentialGroup()
                        .addGroup(jpLivresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jlabSelectionnerLivre, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jpLivresLayout.createSequentialGroup()
                                .addComponent(jbAfficherLivre)
                                .addGap(102, 102, 102)
                                .addComponent(jbModifierLivre)
                                .addGap(114, 114, 114)
                                .addComponent(jbSupprimerLivre)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 595, Short.MAX_VALUE)
                        .addComponent(jbCreeLivre)
                        .addGap(19, 19, 19))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpLivresLayout.createSequentialGroup()
                        .addComponent(jScrollTableLivres)
                        .addContainerGap())))
        );
        jpLivresLayout.setVerticalGroup(
            jpLivresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpLivresLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollTableLivres, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
                .addComponent(jlabSelectionnerLivre)
                .addGap(3, 3, 3)
                .addGroup(jpLivresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbAfficherLivre)
                    .addComponent(jbModifierLivre)
                    .addComponent(jbSupprimerLivre)
                    .addComponent(jbCreeLivre))
                .addGap(22, 22, 22))
        );

        getContentPane().add(jpLivres);
        jpLivres.setBounds(0, 150, 1310, 690);

        jbEditeurs.setBackground(new java.awt.Color(0, 102, 153));
        jbEditeurs.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jbEditeurs.setForeground(new java.awt.Color(255, 255, 255));
        jbEditeurs.setText("Editeurs");
        jbEditeurs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEditeursActionPerformed(evt);
            }
        });
        getContentPane().add(jbEditeurs);
        jbEditeurs.setBounds(182, 117, 163, 25);

        jScrollTableEditeurs.setBackground(new java.awt.Color(255, 255, 255));

        jTableEditeurs.setModel(initModelEditeur());
        jTableEditeurs.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollTableEditeurs.setViewportView(jTableEditeurs);

        jlabSelectionnerEditeur.setText("Veuillez selectionner une ligne pour choisir l'éditeur");

        jbAfficherEditeur.setText("Afficher l'éditeur");
        jbAfficherEditeur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAfficherEditeurActionPerformed(evt);
            }
        });

        jbModifierEditeur.setText("Modifier l'éditeur");
        jbModifierEditeur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbModifierEditeurActionPerformed(evt);
            }
        });

        jbSupprimerEditeur.setText("Supprimer l'éditeur");
        jbSupprimerEditeur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSupprimerEditeurActionPerformed(evt);
            }
        });

        jbCreeEditeur.setText("Créer un nouvel éditeur ");
        jbCreeEditeur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCreeEditeurActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpEditeursLayout = new javax.swing.GroupLayout(jpEditeurs);
        jpEditeurs.setLayout(jpEditeursLayout);
        jpEditeursLayout.setHorizontalGroup(
            jpEditeursLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpEditeursLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpEditeursLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpEditeursLayout.createSequentialGroup()
                        .addComponent(jlabSelectionnerEditeur, javax.swing.GroupLayout.PREFERRED_SIZE, 715, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jpEditeursLayout.createSequentialGroup()
                        .addGroup(jpEditeursLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollTableEditeurs)
                            .addGroup(jpEditeursLayout.createSequentialGroup()
                                .addComponent(jbAfficherEditeur)
                                .addGap(86, 86, 86)
                                .addComponent(jbModifierEditeur)
                                .addGap(142, 142, 142)
                                .addComponent(jbSupprimerEditeur)
                                .addGap(539, 539, 539)
                                .addComponent(jbCreeEditeur)))
                        .addContainerGap())))
        );
        jpEditeursLayout.setVerticalGroup(
            jpEditeursLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpEditeursLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollTableEditeurs, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jlabSelectionnerEditeur)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpEditeursLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbAfficherEditeur)
                    .addComponent(jbModifierEditeur)
                    .addComponent(jbSupprimerEditeur)
                    .addComponent(jbCreeEditeur))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jpEditeurs);
        jpEditeurs.setBounds(0, 150, 1300, 570);

        jbAuteurs.setBackground(new java.awt.Color(0, 102, 153));
        jbAuteurs.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jbAuteurs.setForeground(new java.awt.Color(255, 255, 255));
        jbAuteurs.setText("Auteurs");
        jbAuteurs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAuteursActionPerformed(evt);
            }
        });
        getContentPane().add(jbAuteurs);
        jbAuteurs.setBounds(351, 117, 132, 25);

        jScrollTableAuteurs.setBackground(new java.awt.Color(255, 255, 255));

        jTableAuteurs.setModel(initModelAuteur());
        jTableAuteurs.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollTableAuteurs.setViewportView(jTableAuteurs);

        jlabSelectionnerAuteurs.setText("Veuillez selectionner une ligne pour choisir l'auteur");

        jbAfficheAuteur.setText("Afficher l'auteur");
        jbAfficheAuteur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAfficheAuteurActionPerformed(evt);
            }
        });

        jbModifierAuteur.setText("Modifier l'auteur");
        jbModifierAuteur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbModifierAuteurActionPerformed(evt);
            }
        });

        jbSupprimerAuteur.setText("Supprimer l'auteur");
        jbSupprimerAuteur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSupprimerAuteurActionPerformed(evt);
            }
        });

        jbCreerAuteur.setText("Créer un nouvel auteur ");
        jbCreerAuteur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCreerAuteurActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpAuteursLayout = new javax.swing.GroupLayout(jpAuteurs);
        jpAuteurs.setLayout(jpAuteursLayout);
        jpAuteursLayout.setHorizontalGroup(
            jpAuteursLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpAuteursLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpAuteursLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpAuteursLayout.createSequentialGroup()
                        .addComponent(jScrollTableAuteurs)
                        .addContainerGap())
                    .addGroup(jpAuteursLayout.createSequentialGroup()
                        .addGroup(jpAuteursLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jlabSelectionnerAuteurs, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpAuteursLayout.createSequentialGroup()
                                .addComponent(jbAfficheAuteur)
                                .addGap(102, 102, 102)
                                .addComponent(jbModifierAuteur)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jbSupprimerAuteur)))
                        .addGap(569, 569, 569)
                        .addComponent(jbCreerAuteur)
                        .addGap(21, 21, 21))))
        );
        jpAuteursLayout.setVerticalGroup(
            jpAuteursLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpAuteursLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollTableAuteurs, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jlabSelectionnerAuteurs)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpAuteursLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbAfficheAuteur)
                    .addComponent(jbModifierAuteur)
                    .addComponent(jbSupprimerAuteur)
                    .addComponent(jbCreerAuteur))
                .addContainerGap())
        );

        getContentPane().add(jpAuteurs);
        jpAuteurs.setBounds(0, 150, 1300, 580);

        jbCommandes.setBackground(new java.awt.Color(0, 102, 153));
        jbCommandes.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jbCommandes.setForeground(new java.awt.Color(255, 255, 255));
        jbCommandes.setText("Commandes");
        jbCommandes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCommandesActionPerformed(evt);
            }
        });
        getContentPane().add(jbCommandes);
        jbCommandes.setBounds(489, 117, 192, 25);

        jScrollTableCommandes.setBackground(new java.awt.Color(255, 255, 255));

        jTableCommande.setModel(initModelCommande());
        jTableCommande.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollTableCommandes.setViewportView(jTableCommande);

        jlabSelectionnerCommandes.setText("Veuillez selectionner une ligne pour choisir la commande");

        jbAfficherCommande.setText("Afficher la commande");
        jbAfficherCommande.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAfficherCommandeActionPerformed(evt);
            }
        });

        jbModifierCommande.setText("Modifier la commande");
        jbModifierCommande.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbModifierCommandeActionPerformed(evt);
            }
        });

        jbSupprimerCommande.setText("Supprimer la commande");
        jbSupprimerCommande.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSupprimerCommandeActionPerformed(evt);
            }
        });

        jbCreerCommande.setText("Créer une nouvelle commande");
        jbCreerCommande.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCreerCommandeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpCommandesLayout = new javax.swing.GroupLayout(jpCommandes);
        jpCommandes.setLayout(jpCommandesLayout);
        jpCommandesLayout.setHorizontalGroup(
            jpCommandesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCommandesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpCommandesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpCommandesLayout.createSequentialGroup()
                        .addGroup(jpCommandesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jlabSelectionnerCommandes, javax.swing.GroupLayout.PREFERRED_SIZE, 631, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpCommandesLayout.createSequentialGroup()
                                .addComponent(jbAfficherCommande)
                                .addGap(102, 102, 102)
                                .addComponent(jbModifierCommande)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jbSupprimerCommande)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 461, Short.MAX_VALUE)
                        .addComponent(jbCreerCommande)
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpCommandesLayout.createSequentialGroup()
                        .addComponent(jScrollTableCommandes)
                        .addContainerGap())))
        );
        jpCommandesLayout.setVerticalGroup(
            jpCommandesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCommandesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollTableCommandes, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jlabSelectionnerCommandes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCommandesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbAfficherCommande)
                    .addComponent(jbModifierCommande)
                    .addComponent(jbSupprimerCommande)
                    .addComponent(jbCreerCommande))
                .addContainerGap())
        );

        getContentPane().add(jpCommandes);
        jpCommandes.setBounds(0, 150, 1300, 570);

        jbClients.setBackground(new java.awt.Color(0, 102, 153));
        jbClients.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jbClients.setForeground(new java.awt.Color(255, 255, 255));
        jbClients.setText("Clients");
        jbClients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbClientsActionPerformed(evt);
            }
        });
        getContentPane().add(jbClients);
        jbClients.setBounds(687, 117, 125, 25);

        jScrollTableClients.setBackground(new java.awt.Color(255, 255, 255));

        jTableClients.setModel(initModelClient());
        jScrollTableClients.setViewportView(jTableClients);

        jlabSelectionnerClients.setText("Veuillez selectionner une ligne pour choisir le client");

        jbAfficherClient.setText("Afficher le client");
        jbAfficherClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAfficherClientActionPerformed(evt);
            }
        });

        jbModifieClient.setText("Modifier le client");
        jbModifieClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbModifieClientActionPerformed(evt);
            }
        });

        jbSupprimerClient.setText("Supprimer  le client");
        jbSupprimerClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSupprimerClientActionPerformed(evt);
            }
        });

        jbCreerClient.setText("Créer un nouveau client ");
        jbCreerClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCreerClientActionPerformed(evt);
            }
        });

        jbFermerJFClients.setText("Ferrmer");
        jbFermerJFClients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbFermerJFClientsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpClientsLayout = new javax.swing.GroupLayout(jpClients);
        jpClients.setLayout(jpClientsLayout);
        jpClientsLayout.setHorizontalGroup(
            jpClientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpClientsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpClientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollTableClients, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpClientsLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jlabSelectionnerClients, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(733, 733, 733))
                    .addGroup(jpClientsLayout.createSequentialGroup()
                        .addComponent(jbAfficherClient)
                        .addGap(91, 91, 91)
                        .addComponent(jbModifieClient)
                        .addGap(117, 117, 117)
                        .addComponent(jbSupprimerClient)
                        .addGap(130, 130, 130)
                        .addComponent(jbCreerClient)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbFermerJFClients)))
                .addContainerGap())
        );
        jpClientsLayout.setVerticalGroup(
            jpClientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpClientsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollTableClients, javax.swing.GroupLayout.PREFERRED_SIZE, 531, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlabSelectionnerClients)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpClientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbAfficherClient)
                    .addComponent(jbModifieClient)
                    .addComponent(jbSupprimerClient)
                    .addComponent(jbCreerClient)
                    .addComponent(jbFermerJFClients))
                .addContainerGap())
        );

        getContentPane().add(jpClients);
        jpClients.setBounds(0, 150, 1310, 690);

        jbEvents.setBackground(new java.awt.Color(0, 102, 153));
        jbEvents.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jbEvents.setForeground(new java.awt.Color(255, 255, 255));
        jbEvents.setText("Evènements");
        jbEvents.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEventsActionPerformed(evt);
            }
        });
        getContentPane().add(jbEvents);
        jbEvents.setBounds(818, 117, 193, 25);

        EventjTable.setModel(initModelEvents());
        EventjTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        EventjTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EventjTableMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(EventjTable);

        namejLabel.setText("Nom* :");

        statusjLabel.setText("Statut :");

        date1jLabel.setText("Date de début* :");

        date2jLabel.setText("Date de fin* :");

        discountjLabel.setText("Réduction (%)* :");

        descrjLabel.setText("Description :");

        notesjLabel.setText("Commentaires :");

        picjLabel.setText("Image :");

        StatusjComboBox.setModel(initModelStatus());

        descrjTextArea.setColumns(20);
        descrjTextArea.setRows(5);
        jScrollPane3.setViewportView(descrjTextArea);

        notesjTextArea.setColumns(20);
        notesjTextArea.setRows(5);
        jScrollPane4.setViewportView(notesjTextArea);

        browsejButton.setText("Parcourir");
        browsejButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browsejButtonActionPerformed(evt);
            }
        });

        addjButton.setText("Enregistrer");
        addjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addjButtonActionPerformed(evt);
            }
        });

        editjButton.setText("Modifier");
        editjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editjButtonActionPerformed(evt);
            }
        });

        deletejButton.setText("Supprimer");
        deletejButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletejButtonActionPerformed(evt);
            }
        });

        jLabel5.setText("(Veuillez retélécharger l'image pour une modification)");

        jLabel6.setText("* Champs obligatoires");

        canceljButton.setText("Fermer");
        canceljButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                canceljButtonActionPerformed(evt);
            }
        });

        jbNouveau.setText("Nouveau");
        jbNouveau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbNouveauActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpEventsLayout = new javax.swing.GroupLayout(jpEvents);
        jpEvents.setLayout(jpEventsLayout);
        jpEventsLayout.setHorizontalGroup(
            jpEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpEventsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane5)
                    .addGroup(jpEventsLayout.createSequentialGroup()
                        .addGroup(jpEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpEventsLayout.createSequentialGroup()
                                .addGroup(jpEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(date1jLabel)
                                    .addComponent(discountjLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jpEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jpEventsLayout.createSequentialGroup()
                                        .addGap(274, 274, 274)
                                        .addComponent(date2jLabel)
                                        .addGap(18, 18, 18)
                                        .addComponent(date2jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jpEventsLayout.createSequentialGroup()
                                        .addGap(31, 31, 31)
                                        .addGroup(jpEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(deletejButton)
                                            .addGroup(jpEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(discountjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpEventsLayout.createSequentialGroup()
                                .addGroup(jpEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(namejLabel)
                                    .addComponent(statusjLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jpEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(namejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(date1jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(StatusjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(descrjLabel)
                            .addComponent(notesjLabel)
                            .addGroup(jpEventsLayout.createSequentialGroup()
                                .addGroup(jpEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addGroup(jpEventsLayout.createSequentialGroup()
                                        .addComponent(jbNouveau)
                                        .addGap(42, 42, 42)
                                        .addComponent(addjButton)))
                                .addGap(53, 53, 53)
                                .addComponent(editjButton)))
                        .addGroup(jpEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpEventsLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(jpEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jpEventsLayout.createSequentialGroup()
                                        .addComponent(picjLabel)
                                        .addGap(18, 18, 18)
                                        .addComponent(browsejButton))
                                    .addComponent(jLabel5))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpEventsLayout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addGroup(jpEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(canceljButton, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(imagejLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 612, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(48, 48, 48))
        );
        jpEventsLayout.setVerticalGroup(
            jpEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpEventsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jpEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jpEventsLayout.createSequentialGroup()
                        .addGroup(jpEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(namejLabel)
                            .addComponent(namejTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jpEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(statusjLabel)
                            .addComponent(StatusjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jpEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(date1jLabel)
                            .addComponent(date1jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(date2jLabel)
                            .addComponent(date2jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jpEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(discountjLabel)
                            .addComponent(discountjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jpEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(descrjLabel))
                        .addGap(18, 18, 18)
                        .addGroup(jpEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(notesjLabel)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jpEventsLayout.createSequentialGroup()
                        .addGroup(jpEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpEventsLayout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jLabel5))
                            .addGroup(jpEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(picjLabel)
                                .addComponent(browsejButton)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(imagejLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14)
                .addComponent(jLabel6)
                .addGap(10, 10, 10)
                .addGroup(jpEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addjButton)
                    .addComponent(editjButton)
                    .addComponent(deletejButton)
                    .addComponent(canceljButton)
                    .addComponent(jbNouveau))
                .addGap(19, 19, 19))
        );

        getContentPane().add(jpEvents);
        jpEvents.setBounds(8, 150, 1290, 690);

        jbAvis.setBackground(new java.awt.Color(0, 102, 153));
        jbAvis.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jbAvis.setForeground(new java.awt.Color(255, 255, 255));
        jbAvis.setText("Avis");
        jbAvis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAvisActionPerformed(evt);
            }
        });
        getContentPane().add(jbAvis);
        jbAvis.setBounds(1017, 117, 119, 25);

        jcbAvisSt.setModel(initCommentStatus());
        jcbAvisSt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbAvisStActionPerformed(evt);
            }
        });

        jlSelectionavis.setText("Selectionnez un avis");

        jLabel4.setText("Selectionnez un statut");

        jbAfficher.setText("Afficher");
        jbAfficher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAfficherActionPerformed(evt);
            }
        });

        jtAvis.setModel(initModelCommentaire("300"));
        jtAvis.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jtAvis);

        javax.swing.GroupLayout jpAvisSelectionLayout = new javax.swing.GroupLayout(jpAvisSelection);
        jpAvisSelection.setLayout(jpAvisSelectionLayout);
        jpAvisSelectionLayout.setHorizontalGroup(
            jpAvisSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpAvisSelectionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpAvisSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpAvisSelectionLayout.createSequentialGroup()
                        .addGroup(jpAvisSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlSelectionavis)
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jcbAvisSt, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jpAvisSelectionLayout.createSequentialGroup()
                .addGap(258, 258, 258)
                .addComponent(jbAfficher)
                .addGap(300, 300, 300))
            .addGroup(jpAvisSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpAvisSelectionLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 609, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jpAvisSelectionLayout.setVerticalGroup(
            jpAvisSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpAvisSelectionLayout.createSequentialGroup()
                .addContainerGap(66, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jcbAvisSt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jlSelectionavis)
                .addGap(304, 304, 304)
                .addComponent(jbAfficher)
                .addGap(22, 22, 22))
            .addGroup(jpAvisSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpAvisSelectionLayout.createSequentialGroup()
                    .addContainerGap(164, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(64, Short.MAX_VALUE)))
        );

        jtaDetailAvis.setColumns(20);
        jtaDetailAvis.setLineWrap(true);
        jtaDetailAvis.setRows(5);
        jtaDetailAvis.setWrapStyleWord(true);
        jScrollPane2.setViewportView(jtaDetailAvis);

        jLabel2.setText("Détail de l'avis");

        jLabel3.setText("Statut de l'avis ");

        jbValiderAvis.setText("Valider");
        jbValiderAvis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbValiderAvisActionPerformed(evt);
            }
        });

        jbRefuser.setText("Refuser");
        jbRefuser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRefuserActionPerformed(evt);
            }
        });

        jtfAvisStatut.setEditable(false);
        jtfAvisStatut.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jpAvisAffichageLayout = new javax.swing.GroupLayout(jpAvisAffichage);
        jpAvisAffichage.setLayout(jpAvisAffichageLayout);
        jpAvisAffichageLayout.setHorizontalGroup(
            jpAvisAffichageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpAvisAffichageLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jpAvisAffichageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfAvisStatut)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                    .addGroup(jpAvisAffichageLayout.createSequentialGroup()
                        .addGroup(jpAvisAffichageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jpAvisAffichageLayout.createSequentialGroup()
                        .addComponent(jbValiderAvis)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbRefuser)))
                .addGap(38, 38, 38))
        );
        jpAvisAffichageLayout.setVerticalGroup(
            jpAvisAffichageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpAvisAffichageLayout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(jLabel3)
                .addGap(11, 11, 11)
                .addComponent(jtfAvisStatut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jpAvisAffichageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbValiderAvis)
                    .addComponent(jbRefuser))
                .addGap(26, 26, 26))
        );

        jbFermer.setText("Fermer");
        jbFermer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbFermerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpAvisLayout = new javax.swing.GroupLayout(jpAvis);
        jpAvis.setLayout(jpAvisLayout);
        jpAvisLayout.setHorizontalGroup(
            jpAvisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpAvisLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jpAvisSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpAvisAffichage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpAvisLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbFermer)
                .addGap(46, 46, 46))
        );
        jpAvisLayout.setVerticalGroup(
            jpAvisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpAvisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpAvisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpAvisSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpAvisAffichage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbFermer)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jpAvis);
        jpAvis.setBounds(10, 150, 1300, 680);

        jbDivers.setBackground(new java.awt.Color(0, 102, 153));
        jbDivers.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jbDivers.setForeground(new java.awt.Color(255, 255, 255));
        jbDivers.setText("Divers fonctions");
        jbDivers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDiversActionPerformed(evt);
            }
        });
        getContentPane().add(jbDivers);
        jbDivers.setBounds(1142, 117, 163, 25);

        jpDivers.setLayout(null);

        jPanel12.setBackground(new java.awt.Color(255, 153, 153));
        jPanel12.setToolTipText("");

        StatusjComboBox2.setModel(initModelStatus());

        lastNamejLabel1.setText("Nom:");

        firstNamejLabel1.setText("Prénom:");

        picturejLabel1.setText("Image :");

        notesjLabel2.setText("Note :");

        notesjTextArea2.setColumns(20);
        notesjTextArea2.setLineWrap(true);
        notesjTextArea2.setRows(5);
        notesjTextArea2.setWrapStyleWord(true);
        notesjScrollPane1.setViewportView(notesjTextArea2);

        imagejLabel2.setBackground(new java.awt.Color(255, 255, 255));

        editjButton2.setText("Enregistrer les modification ");

        canceljButton1.setText("Fermer");

        BrowsejButton1.setText("Parcourir");

        jLabel1.setText("* Champs obligatoires");

        jLabel7.setText("Login");

        jLabel8.setText("Mot de passe");

        jTextField2.setText("jTextField2");

        jTextField3.setText("jTextField3");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(89, 89, 89)
                                .addComponent(StatusjComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lastNamejLabel1)
                                    .addComponent(firstNamejLabel1)
                                    .addComponent(notesjLabel2)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addGap(25, 25, 25)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(firstNamejTextField1, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lastNamejTextField1)
                                        .addComponent(notesjScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(picturejLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(BrowsejButton1))
                            .addComponent(imagejLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(130, Short.MAX_VALUE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(editjButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(canceljButton1)
                        .addGap(22, 22, 22))))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(StatusjComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lastNamejLabel1)
                                    .addComponent(lastNamejTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(firstNamejLabel1)
                                    .addComponent(firstNamejTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(BrowsejButton1)
                            .addComponent(picturejLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(notesjScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(notesjLabel2))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1))
                    .addComponent(imagejLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editjButton2)
                    .addComponent(canceljButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Mon profil salarié", jPanel12);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1205, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 502, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Collections", jPanel10);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1205, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 502, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Thèmes", jPanel2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1205, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 502, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Grille tarifaire", jPanel3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1205, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 502, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("TVA", jPanel4);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1205, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 502, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Volumes du  livre", jPanel5);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1205, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 502, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Formats du livre", jPanel6);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1205, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 502, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Statuts", jPanel7);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1205, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 502, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Transporteurs", jPanel8);

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane6.setViewportView(jList1);

        jlabSelectionnerLivre1.setText("Veuillez selectionner une ligne pour choisirun mot cké");

        jbModifierMotClé.setText("Modifier le mot Clé");
        jbModifierMotClé.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbModifierMotCléActionPerformed(evt);
            }
        });

        jbSupprimerMC.setText("Supprimer le mot clé");
        jbSupprimerMC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSupprimerMCActionPerformed(evt);
            }
        });

        jbCreerMC.setText("Créer un nouveau mot clé");
        jbCreerMC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCreerMCActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 1171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(24, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jbModifierMotClé)
                        .addGap(312, 312, 312)
                        .addComponent(jbSupprimerMC)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbCreerMC)
                        .addGap(38, 38, 38))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jlabSelectionnerLivre1, javax.swing.GroupLayout.PREFERRED_SIZE, 541, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlabSelectionnerLivre1)
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbModifierMotClé)
                    .addComponent(jbSupprimerMC)
                    .addComponent(jbCreerMC))
                .addGap(89, 89, 89))
        );

        jTabbedPane1.addTab("Mots clés", jPanel9);

        jpDivers.add(jTabbedPane1);
        jTabbedPane1.setBounds(10, 10, 1210, 530);

        getContentPane().add(jpDivers);
        jpDivers.setBounds(10, 160, 1290, 560);
        jpDivers.getAccessibleContext().setAccessibleParent(jbDivers);

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        setSize(new java.awt.Dimension(1332, 906));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbLivresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLivresActionPerformed
        jpLivres.setVisible(true);
        jpAuteurs.setVisible(false);
        jpEditeurs.setVisible(false);
        jpEvents.setVisible(false);
        jpDivers.setVisible(false);
        jpCommandes.setVisible(false);
        jpClients.setVisible(false);
        jpAvis.setVisible(false);

//        jPSupprimerLivre.setVisible(false);
//        jPAfficherLivre.setVisible(false);
//        jPModifierLivre.setVisible(false);
//        jPCreerLivre.setVisible(false);
//
//        jPSupprimerEditeur.setVisible(false);
//        jPAfficherEditeur.setVisible(false);
//        jPModifierEditeur.setVisible(false);
//        jPCreerEditeur.setVisible(false);
//
//        jPSupprimerAuteur.setVisible(false);
//        jPAfficherAuteur.setVisible(false);
//        jPModifierAuteur.setVisible(false);
//        jPCreerAuteur.setVisible(false);
//
//        jPSupprimerCommande.setVisible(false);
//        jPAfficherCommande.setVisible(false);
//        jPModifierCommande.setVisible(false);
//        jPCreerCommande.setVisible(false);
//
//        jPSupprimerClient.setVisible(false);
//        jPAfficherClient.setVisible(false);
//        jPModifierClient.setVisible(false);
//        jPCreerClient.setVisible(false);

    }//GEN-LAST:event_jbLivresActionPerformed

    private void jbEditeursActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEditeursActionPerformed
        jpLivres.setVisible(false);
        jpAuteurs.setVisible(false);
        jpEditeurs.setVisible(true);
        jpEvents.setVisible(false);
        jpDivers.setVisible(false);
        jpCommandes.setVisible(false);
        jpClients.setVisible(false);
        jpAvis.setVisible(false);

//        jPSupprimerLivre.setVisible(false);
//        jPAfficherLivre.setVisible(false);
//        jPModifierLivre.setVisible(false);
//        jPCreerLivre.setVisible(false);
//
//        jPSupprimerEditeur.setVisible(false);
//        jPAfficherEditeur.setVisible(false);
//        jPModifierEditeur.setVisible(false);
//        jPCreerEditeur.setVisible(false);
//
//        jPSupprimerAuteur.setVisible(false);
//        jPAfficherAuteur.setVisible(false);
//        jPModifierAuteur.setVisible(false);
//        jPCreerAuteur.setVisible(false);
//
//        jPSupprimerCommande.setVisible(false);
//        jPAfficherCommande.setVisible(false);
//        jPModifierCommande.setVisible(false);
//        jPCreerCommande.setVisible(false);
//
//        jPSupprimerClient.setVisible(false);
//        jPAfficherClient.setVisible(false);
//        jPModifierClient.setVisible(false);
//        jPCreerClient.setVisible(false);
    }//GEN-LAST:event_jbEditeursActionPerformed

    private void jbAuteursActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAuteursActionPerformed
        jpLivres.setVisible(false);
        jpAuteurs.setVisible(true);
        jpEditeurs.setVisible(false);
        jpEvents.setVisible(false);
        jpDivers.setVisible(false);
        jpCommandes.setVisible(false);
        jpClients.setVisible(false);
        jpAvis.setVisible(false);

//        jPSupprimerLivre.setVisible(false);
//        jPAfficherLivre.setVisible(false);
//        jPModifierLivre.setVisible(false);
//        jPCreerLivre.setVisible(false);
//
//        jPSupprimerEditeur.setVisible(false);
//        jPAfficherEditeur.setVisible(false);
//        jPModifierEditeur.setVisible(false);
//        jPCreerEditeur.setVisible(false);
//
//        jPSupprimerAuteur.setVisible(false);
//        jPAfficherAuteur.setVisible(false);
//        jPModifierAuteur.setVisible(false);
//        jPCreerAuteur.setVisible(false);
//
//        jPSupprimerCommande.setVisible(false);
//        jPAfficherCommande.setVisible(false);
//        jPModifierCommande.setVisible(false);
//        jPCreerCommande.setVisible(false);
//
//        jPSupprimerClient.setVisible(false);
//        jPAfficherClient.setVisible(false);
//        jPModifierClient.setVisible(false);
//        jPCreerClient.setVisible(false);
    }//GEN-LAST:event_jbAuteursActionPerformed

    private void jbCommandesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCommandesActionPerformed
        jpLivres.setVisible(false);
        jpAuteurs.setVisible(false);
        jpEditeurs.setVisible(false);
        jpEvents.setVisible(false);
        jpDivers.setVisible(false);
        jpCommandes.setVisible(true);
        jpClients.setVisible(false);
        jpAvis.setVisible(false);

//        jPSupprimerLivre.setVisible(false);
//        jPAfficherLivre.setVisible(false);
//        jPModifierLivre.setVisible(false);
////        jPCreerLivre.setVisible(false);
//
//        jPSupprimerEditeur.setVisible(false);
//        jPAfficherEditeur.setVisible(false);
//        jPModifierEditeur.setVisible(false);
//        jPCreerEditeur.setVisible(false);
//
//        jPSupprimerAuteur.setVisible(false);
//        jPAfficherAuteur.setVisible(false);
//        jPModifierAuteur.setVisible(false);
//        jPCreerAuteur.setVisible(false);
//
//        jPSupprimerCommande.setVisible(false);
//        jPAfficherCommande.setVisible(false);
//        jPModifierCommande.setVisible(false);
//        jPCreerCommande.setVisible(false);
//
//        jPSupprimerClient.setVisible(false);
//        jPAfficherClient.setVisible(false);
//        jPModifierClient.setVisible(false);
//        jPCreerClient.setVisible(false);
    }//GEN-LAST:event_jbCommandesActionPerformed

    private void jbClientsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbClientsActionPerformed
        jpLivres.setVisible(false);
        jpAuteurs.setVisible(false);
        jpEditeurs.setVisible(false);
        jpEvents.setVisible(false);
        jpDivers.setVisible(false);
        jpCommandes.setVisible(false);
        jpClients.setVisible(true);
        jpAvis.setVisible(false);

//        jPSupprimerLivre.setVisible(false);
//        jPAfficherLivre.setVisible(false);
//        jPModifierLivre.setVisible(false);
//        jPCreerLivre.setVisible(false);
//
//        jPSupprimerEditeur.setVisible(false);
//        jPAfficherEditeur.setVisible(false);
//        jPModifierEditeur.setVisible(false);
//        jPCreerEditeur.setVisible(false);
//
//        jPSupprimerAuteur.setVisible(false);
//        jPAfficherAuteur.setVisible(false);
//        jPModifierAuteur.setVisible(false);
//        jPCreerAuteur.setVisible(false);
//
//        jPSupprimerCommande.setVisible(false);
//        jPAfficherCommande.setVisible(false);
//        jPModifierCommande.setVisible(false);
//        jPCreerCommande.setVisible(false);
//
//        jPSupprimerClient.setVisible(false);
//        jPAfficherClient.setVisible(false);
//        jPModifierClient.setVisible(false);
//        jPCreerClient.setVisible(false);
    }//GEN-LAST:event_jbClientsActionPerformed

    private void jbEventsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEventsActionPerformed
        jpLivres.setVisible(false);
        jpAuteurs.setVisible(false);
        jpEditeurs.setVisible(false);
        jpEvents.setVisible(true);
        jpDivers.setVisible(false);
        jpCommandes.setVisible(false);
        jpClients.setVisible(false);
        jpAvis.setVisible(false);

//        jPSupprimerLivre.setVisible(false);
//        jPAfficherLivre.setVisible(false);
//        jPModifierLivre.setVisible(false);
//        jPCreerLivre.setVisible(false);
//
//        jPSupprimerEditeur.setVisible(false);
//        jPAfficherEditeur.setVisible(false);
//        jPModifierEditeur.setVisible(false);
//        jPCreerEditeur.setVisible(false);
//
//        jPSupprimerAuteur.setVisible(false);
//        jPAfficherAuteur.setVisible(false);
//        jPModifierAuteur.setVisible(false);
//        jPCreerAuteur.setVisible(false);
//
//        jPSupprimerCommande.setVisible(false);
//        jPAfficherCommande.setVisible(false);
//        jPModifierCommande.setVisible(false);
//        jPCreerCommande.setVisible(false);
//
//        jPSupprimerClient.setVisible(false);
//        jPAfficherClient.setVisible(false);
//        jPModifierClient.setVisible(false);
//        jPCreerClient.setVisible(false);
    }//GEN-LAST:event_jbEventsActionPerformed

    private void jbAvisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAvisActionPerformed
        jpLivres.setVisible(false);
        jpAuteurs.setVisible(false);
        jpEditeurs.setVisible(false);
        jpEvents.setVisible(false);
        jpDivers.setVisible(false);
        jpCommandes.setVisible(false);
        jpClients.setVisible(false);
        jpAvis.setVisible(true);

//        jPSupprimerLivre.setVisible(false);
//        jPAfficherLivre.setVisible(false);
//        jPModifierLivre.setVisible(false);
//        jPCreerLivre.setVisible(false);
//
//        jPSupprimerEditeur.setVisible(false);
//        jPAfficherEditeur.setVisible(false);
//        jPModifierEditeur.setVisible(false);
//        jPCreerEditeur.setVisible(false);
//
//        jPSupprimerAuteur.setVisible(false);
//        jPAfficherAuteur.setVisible(false);
//        jPModifierAuteur.setVisible(false);
//        jPCreerAuteur.setVisible(false);
//
//        jPSupprimerCommande.setVisible(false);
//        jPAfficherCommande.setVisible(false);
//        jPModifierCommande.setVisible(false);
//        jPCreerCommande.setVisible(false);
//
//        jPSupprimerClient.setVisible(false);
//        jPAfficherClient.setVisible(false);
//        jPModifierClient.setVisible(false);
//        jPCreerClient.setVisible(false);
    }//GEN-LAST:event_jbAvisActionPerformed

    private void jbDiversActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDiversActionPerformed
        jpLivres.setVisible(false);
        jpAuteurs.setVisible(false);
        jpEditeurs.setVisible(false);
        jpEvents.setVisible(false);
        jpDivers.setVisible(true);
        jpCommandes.setVisible(false);
        jpClients.setVisible(false);
        jpAvis.setVisible(false);

//        jPSupprimerLivre.setVisible(false);
//        jPAfficherLivre.setVisible(false);
//        jPModifierLivre.setVisible(false);
//        jPCreerLivre.setVisible(false);
//
//        jPSupprimerEditeur.setVisible(false);
//        jPAfficherEditeur.setVisible(false);
//        jPModifierEditeur.setVisible(false);
//        jPCreerEditeur.setVisible(false);
//
//        jPSupprimerAuteur.setVisible(false);
//        jPAfficherAuteur.setVisible(false);
//        jPModifierAuteur.setVisible(false);
//        jPCreerAuteur.setVisible(false);
//
//        jPSupprimerCommande.setVisible(false);
//        jPAfficherCommande.setVisible(false);
//        jPModifierCommande.setVisible(false);
//        jPCreerCommande.setVisible(false);
//
//        jPSupprimerClient.setVisible(false);
//        jPAfficherClient.setVisible(false);
//        jPModifierClient.setVisible(false);
//        jPCreerClient.setVisible(false);
    }//GEN-LAST:event_jbDiversActionPerformed

    private void jbAfficherCommandeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAfficherCommandeActionPerformed
        jpLivres.setVisible(false);
        jpAuteurs.setVisible(false);
        jpEditeurs.setVisible(false);
        jpEvents.setVisible(false);
        jpDivers.setVisible(false);

        jpClients.setVisible(false);
        jpAvis.setVisible(false);
        jpCommandes.setVisible(true);
        //        jPSupprimerLivre.setVisible(false);
        //        jPAfficherLivre.setVisible(false);
        //        jPModifierLivre.setVisible(false);
        //        jPCreerLivre.setVisible(false);

        //        jPSupprimerEditeur.setVisible(false);
        //        jPAfficherEditeur.setVisible(false);
        //        jPModifierEditeur.setVisible(false);
        //        jPCreerEditeur.setVisible(false);
        //
        //        jPSupprimerAuteur.setVisible(false);
        //        jPAfficherAuteur.setVisible(false);
        //        jPModifierAuteur.setVisible(false);
        //        jPCreerAuteur.setVisible(false);
        //
        //        jPSupprimerCommande.setVisible(false);
        //        jPAfficherCommande.setVisible(true);
        //        jPModifierCommande.setVisible(false);
        //        jPCreerCommande.setVisible(false);
        //
        //        jPSupprimerClient.setVisible(false);
        //        jPAfficherClient.setVisible(false);
        //        jPModifierClient.setVisible(false);
        //        jPCreerClient.setVisible(false);
        int rowSel = jTableCommande.getSelectedRow();

        if (rowSel == -1) {
            JOptionPane.showMessageDialog(rootPane, "Veuillez sélectionner une ligne", "Sélection", JOptionPane.INFORMATION_MESSAGE);
        } else {
            Commande cmdSel = (Commande) jTableCommande.getValueAt(rowSel, 0);
            JFCommandeMod cmdMod = new JFCommandeMod();

            //System.out.println("cmd ====>>> " + cmdSel);
            //System.out.println("client =================>>> " + cmdSel.getClient());
            cmdMod.setCommande(cmdSel);
            cmdMod.setTitle("Affichage");
            cmdMod.setButton("aff");
            cmdMod.setJFC(this);
            cmdMod.textFill();
            cmdMod.setVisible(true);
            cmdMod.enregOff();
        }
    }//GEN-LAST:event_jbAfficherCommandeActionPerformed

    private void jbModifierCommandeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbModifierCommandeActionPerformed
        //        jpLivres.setVisible(false);
        //        jpAuteurs.setVisible(false);
        //        jpEditeurs.setVisible(false);
        //        jpEvents.setVisible(false);
        //        jpDivers.setVisible(false);
        //
        //        jpClients.setVisible(false);
        //        jpAvis.setVisible(false);
        jpCommandes.setVisible(true);
        //        jPSupprimerLivre.setVisible(false);
        //        jPAfficherLivre.setVisible(false);
        //        jPModifierLivre.setVisible(false);
        //        jPCreerLivre.setVisible(false);

        //        jPSupprimerEditeur.setVisible(false);
        //        jPAfficherEditeur.setVisible(false);
        //        jPModifierEditeur.setVisible(false);
        //        jPCreerEditeur.setVisible(false);
        //
        //        jPSupprimerAuteur.setVisible(false);
        //        jPAfficherAuteur.setVisible(false);
        //        jPModifierAuteur.setVisible(false);
        //        jPCreerAuteur.setVisible(false);
        //
        //        jPSupprimerCommande.setVisible(false);
        //        jPAfficherCommande.setVisible(false);
        //        jPModifierCommande.setVisible(true);
        //        jPCreerCommande.setVisible(false);
        //
        //        jPSupprimerClient.setVisible(false);
        //        jPAfficherClient.setVisible(false);
        //        jPModifierClient.setVisible(false);
        //        jPCreerClient.setVisible(false);
        int rowSel = jTableCommande.getSelectedRow();
        //System.out.println("ligne selectionnée : " + rowSel);
        if (rowSel == -1) {
            JOptionPane.showMessageDialog(rootPane, "Veuillez sélectionner une ligne", "Sélection", JOptionPane.INFORMATION_MESSAGE);
        } else {
            Commande cmdSel = (Commande) jTableCommande.getValueAt(rowSel, 0);
            //System.out.println(cmdSel);
            JFCommandeMod cmdMod = new JFCommandeMod();
            cmdMod.setCommande(cmdSel);
            cmdMod.setTitle("Modification");
            cmdMod.setButton("mod");
            cmdMod.setJFC(this);
            cmdMod.textFill();
            cmdMod.setVisible(true);

        }
    }//GEN-LAST:event_jbModifierCommandeActionPerformed

    private void jbSupprimerCommandeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSupprimerCommandeActionPerformed
        //        jpLivres.setVisible(false);
        //        jpAuteurs.setVisible(false);
        //        jpEditeurs.setVisible(false);
        //        jpEvents.setVisible(false);
        //        jpDivers.setVisible(false);
        //
        //        jpClients.setVisible(false);
        //        jpAvis.setVisible(false);
        jpCommandes.setVisible(true);
        //        jPSupprimerLivre.setVisible(false);
        //        jPAfficherLivre.setVisible(false);
        //        jPModifierLivre.setVisible(false);
        //        jPCreerLivre.setVisible(false);

        //        jPSupprimerEditeur.setVisible(false);
        //        jPAfficherEditeur.setVisible(false);
        //        jPModifierEditeur.setVisible(false);
        //        jPCreerEditeur.setVisible(false);
        //
        //        jPSupprimerAuteur.setVisible(false);
        //        jPAfficherAuteur.setVisible(false);
        //        jPModifierAuteur.setVisible(false);
        //        jPCreerAuteur.setVisible(false);
        //
        //        jPSupprimerCommande.setVisible(true);
        //        jPAfficherCommande.setVisible(false);
        //        jPModifierCommande.setVisible(false);
        //        jPCreerCommande.setVisible(false);
        //
        //        jPSupprimerClient.setVisible(false);
        //        jPAfficherClient.setVisible(false);
        //        jPModifierClient.setVisible(false);
        //        jPCreerClient.setVisible(false);
        int rowSel = jTableCommande.getSelectedRow();
        //System.out.println("ligne selectionnée : " + rowSel);
        int n;
        if (rowSel == -1) {
            JOptionPane.showMessageDialog(rootPane, "Veuillez sélectionner une ligne", "Sélection", JOptionPane.INFORMATION_MESSAGE);
        } else {
            n = JOptionPane.showConfirmDialog(rootPane, "ATTENTION vous allez supprimer une commande.\nCette action est irrémediable\nEtes-vous sûr ?", "Suppression", JOptionPane.OK_CANCEL_OPTION);
            if (n == 0) {

                Commande commande = (Commande) jTableCommande.getValueAt(rowSel, 0);
                try {
                    commande.getStatus().setId("199");
                } catch (DataExceptions ex) {
                    JOptionPane.showMessageDialog(null, "Modif Status: " + ex.getErrorCode() + " / " + ex.getMessage(), "Commande", JOptionPane.ERROR_MESSAGE);
                }
                Xchange ech = new Xchange();
                ech.connect();
                commande.save(ech, commande.genValue());
                ech.connect();

                refreshCmd();

            }

        }
    }//GEN-LAST:event_jbSupprimerCommandeActionPerformed

    private void jbCreerCommandeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCreerCommandeActionPerformed
        //        jpLivres.setVisible(false);
        //        jpAuteurs.setVisible(false);
        //        jpEditeurs.setVisible(false);
        //        jpEvents.setVisible(false);
        //        jpDivers.setVisible(false);
        //
        //        jpClients.setVisible(false);
        //        jpAvis.setVisible(false);
        jpCommandes.setVisible(true);
        //        jPSupprimerLivre.setVisible(false);
        //        jPAfficherLivre.setVisible(false);
        //        jPModifierLivre.setVisible(false);
        //        jPCreerLivre.setVisible(false);

        //        jPSupprimerEditeur.setVisible(false);
        //        jPAfficherEditeur.setVisible(false);
        //        jPModifierEditeur.setVisible(false);
        //        jPCreerEditeur.setVisible(false);
        //
        //        jPSupprimerAuteur.setVisible(false);
        //        jPAfficherAuteur.setVisible(false);
        //        jPModifierAuteur.setVisible(false);
        //        jPCreerAuteur.setVisible(false);
        //
        //        jPSupprimerCommande.setVisible(false);
        //        jPAfficherCommande.setVisible(false);
        //        jPModifierCommande.setVisible(false);
        //        jPCreerCommande.setVisible(true);
        //
        //        jPSupprimerClient.setVisible(false);
        //        jPAfficherClient.setVisible(false);
        //        jPModifierClient.setVisible(false);
        //        jPCreerClient.setVisible(false);
        JFCommandeMod cmdNew = new JFCommandeMod();

        cmdNew.setTitle("Création");
        cmdNew.setButton("new");
        Commande commande = new Commande();
        cmdNew.setCommande(commande);
        cmdNew.setJFC(this);
        cmdNew.textFill();
        cmdNew.setVisible(true);

    }//GEN-LAST:event_jbCreerCommandeActionPerformed

    private void jbAfficherEditeurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAfficherEditeurActionPerformed
        //        jpLivres.setVisible(false);
        //        jpAuteurs.setVisible(false);
        //        jpEditeurs.setVisible(false);
        //        jpEvents.setVisible(false);
        //        jpDivers.setVisible(false);
        //        jpCommandes.setVisible(false);
        //        jpClients.setVisible(false);
        //        jpAvis.setVisible(false);
        //
        //        jPSupprimerLivre.setVisible(false);
        //        jPAfficherLivre.setVisible(false);
        //        jPModifierLivre.setVisible(false);
        //        jPCreerLivre.setVisible(false);
        //
        //        jPSupprimerEditeur.setVisible(false);
        //        jPAfficherEditeur.setVisible(true);
        //        jPModifierEditeur.setVisible(false);
        //        jPCreerEditeur.setVisible(false);
        //
        //        jPSupprimerAuteur.setVisible(false);
        //        jPAfficherAuteur.setVisible(false);
        //        jPModifierAuteur.setVisible(false);
        //        jPCreerAuteur.setVisible(false);
        //
        //        jPSupprimerCommande.setVisible(false);
        //        jPAfficherCommande.setVisible(false);
        //        jPModifierCommande.setVisible(false);
        //        jPCreerCommande.setVisible(false);
        //
        //        jPSupprimerClient.setVisible(false);
        //        jPAfficherClient.setVisible(false);
        //        jPModifierClient.setVisible(false);
        //        jPCreerClient.setVisible(false);
        int rowSel = jTableEditeurs.getSelectedRow();
        if (rowSel == -1) {
            JOptionPane.showMessageDialog(rootPane, "Veuillez sélectionner une ligne", "Sélection", JOptionPane.INFORMATION_MESSAGE);
        } else {
            Editeur EdiSel = (Editeur) jTableEditeurs.getValueAt(rowSel, 0);
            JFEditeurMod editMod = new JFEditeurMod();
            editMod.setEditeur(EdiSel);
            editMod.setTitle("Affichage");
            editMod.setButton("aff");
            editMod.setJFE(this);
            editMod.textFill();
            editMod.setVisible(true);
            editMod.enregOff();
        }
    }//GEN-LAST:event_jbAfficherEditeurActionPerformed

    private void jbModifierEditeurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbModifierEditeurActionPerformed
        //        jpLivres.setVisible(false);
        //        jpAuteurs.setVisible(false);
        //        jpEditeurs.setVisible(false);
        //        jpEvents.setVisible(false);
        //        jpDivers.setVisible(false);
        //        jpCommandes.setVisible(false);
        //        jpClients.setVisible(false);
        //        jpAvis.setVisible(false);
        //
        //        jPSupprimerLivre.setVisible(false);
        //        jPAfficherLivre.setVisible(false);
        //        jPModifierLivre.setVisible(false);
        //        jPCreerLivre.setVisible(false);
        //
        //        jPSupprimerEditeur.setVisible(false);
        //        jPAfficherEditeur.setVisible(false);
        //        jPModifierEditeur.setVisible(true);
        //        jPCreerEditeur.setVisible(false);
        //
        //        jPSupprimerAuteur.setVisible(false);
        //        jPAfficherAuteur.setVisible(false);
        //        jPModifierAuteur.setVisible(false);
        //        jPCreerAuteur.setVisible(false);
        //
        //        jPSupprimerCommande.setVisible(false);
        //        jPAfficherCommande.setVisible(false);
        //        jPModifierCommande.setVisible(false);
        //        jPCreerCommande.setVisible(false);
        //
        //        jPSupprimerClient.setVisible(false);
        //        jPAfficherClient.setVisible(false);
        //        jPModifierClient.setVisible(false);
        //        jPCreerClient.setVisible(false);
        int rowSel = jTableEditeurs.getSelectedRow();
        if (rowSel == -1) {
            JOptionPane.showMessageDialog(rootPane, "Veuillez sélectionner une ligne", "Sélection", JOptionPane.INFORMATION_MESSAGE);
        } else {
            Editeur EdiSel = (Editeur) jTableEditeurs.getValueAt(rowSel, 0);
            JFEditeurMod editMod = new JFEditeurMod();
            editMod.setTitle("Modification");
            editMod.setEditeur(EdiSel);
            editMod.setButton("mod");
            editMod.setJFE(this);
            editMod.textFill();
            editMod.setVisible(true);
        }

    }//GEN-LAST:event_jbModifierEditeurActionPerformed

    private void jbSupprimerEditeurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSupprimerEditeurActionPerformed
        //        jpLivres.setVisible(false);
        //        jpAuteurs.setVisible(false);
        //        jpEditeurs.setVisible(false);
        //        jpEvents.setVisible(false);
        //        jpDivers.setVisible(false);
        //        jpCommandes.setVisible(false);
        //        jpClients.setVisible(false);
        //        jpAvis.setVisible(false);
        //
        //        jPSupprimerLivre.setVisible(false);
        //        jPAfficherLivre.setVisible(false);
        //        jPModifierLivre.setVisible(false);
        //        jPCreerLivre.setVisible(false);
        //
        //        jPSupprimerEditeur.setVisible(true);
        //        jPAfficherEditeur.setVisible(false);
        //        jPModifierEditeur.setVisible(false);
        //        jPCreerEditeur.setVisible(false);
        //
        //        jPSupprimerAuteur.setVisible(false);
        //        jPAfficherAuteur.setVisible(false);
        //        jPModifierAuteur.setVisible(false);
        //        jPCreerAuteur.setVisible(false);
        //
        //        jPSupprimerCommande.setVisible(false);
        //        jPAfficherCommande.setVisible(false);
        //        jPModifierCommande.setVisible(false);
        //        jPCreerCommande.setVisible(false);
        //
        //        jPSupprimerClient.setVisible(false);
        //        jPAfficherClient.setVisible(false);
        //        jPModifierClient.setVisible(false);
        //        jPCreerClient.setVisible(false);
        int rowSel = jTableEditeurs.getSelectedRow();
        if (rowSel == -1) {
            JOptionPane.showMessageDialog(rootPane, "Veuillez sélectionner une ligne", "Sélection", JOptionPane.INFORMATION_MESSAGE);
        } else {
            int n;
            n = JOptionPane.showConfirmDialog(rootPane, "ATTENTION vous allez supprimer une commande.\nCette action est irrémediable\nEtes-vous sûr ?", "Suppression", JOptionPane.OK_CANCEL_OPTION);
            if (n == 0) {

                Editeur ediSel = (Editeur) jTableEditeurs.getValueAt(rowSel, 0);
                try {
                    ediSel.getStatus().setId("699");
                } catch (DataExceptions ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
                Xchange ech = new Xchange();
                ech.connect();
                ediSel.save(ech, ediSel.genValue());
                ech.close();
            }
        }
//            JFEditeurMod editMod = new JFEditeurMod();
//            editMod.setTitle("Suppression");
//            editMod.setEditeur(EdiSel);
//            editMod.setButton("sup");
//            editMod.setJFE(this);
//            editMod.textFill();
//            editMod.selectStat();
//            editMod.setVisible(true);

    }//GEN-LAST:event_jbSupprimerEditeurActionPerformed

    private void jbCreeEditeurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCreeEditeurActionPerformed
        //        jpLivres.setVisible(false);
        //        jpAuteurs.setVisible(false);
        //        jpEditeurs.setVisible(false);
        //        jpEvents.setVisible(false);
        //        jpDivers.setVisible(false);
        //        jpCommandes.setVisible(false);
        //        jpClients.setVisible(false);
        //        jpAvis.setVisible(false);
        //
        //        jPSupprimerLivre.setVisible(false);
        //        jPAfficherLivre.setVisible(false);
        //        jPModifierLivre.setVisible(false);
        //        jPCreerLivre.setVisible(false);
        //
        //        jPSupprimerEditeur.setVisible(false);
        //        jPAfficherEditeur.setVisible(false);
        //        jPModifierEditeur.setVisible(false);
        //        jPCreerEditeur.setVisible(true);
        //
        //        jPSupprimerAuteur.setVisible(false);
        //        jPAfficherAuteur.setVisible(false);
        //        jPModifierAuteur.setVisible(false);
        //        jPCreerAuteur.setVisible(false);
        //
        //        jPSupprimerCommande.setVisible(false);
        //        jPAfficherCommande.setVisible(false);
        //        jPModifierCommande.setVisible(false);
        //        jPCreerCommande.setVisible(false);
        //
        //        jPSupprimerClient.setVisible(false);
        //        jPAfficherClient.setVisible(false);
        //        jPModifierClient.setVisible(false);
        //        jPCreerClient.setVisible(false);

        JFEditeurMod editMod = new JFEditeurMod();
        editMod.setTitle("Nouvel Editeur");
        editMod.setJFE(this);
        editMod.setButton("new");
        editMod.setVisible(true);

    }//GEN-LAST:event_jbCreeEditeurActionPerformed

    private void jbAfficheAuteurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAfficheAuteurActionPerformed
        //        jpLivres.setVisible(false);
        jpAuteurs.setVisible(true);
        //        jpEditeurs.setVisible(false);
        //        jpEvents.setVisible(false);
        //        jpDivers.setVisible(false);
        //        jpCommandes.setVisible(false);
        //        jpClients.setVisible(false);
        //        jpAvis.setVisible(false);

        //        jPSupprimerLivre.setVisible(false);
        //        jPAfficherLivre.setVisible(false);
        //        jPModifierLivre.setVisible(false);
        //        jPCreerLivre.setVisible(false);
        //        jPSupprimerEditeur.setVisible(false);
        //        jPAfficherEditeur.setVisible(false);
        //        jPModifierEditeur.setVisible(false);
        //        jPCreerEditeur.setVisible(false);
        //
        //        jPSupprimerAuteur.setVisible(false);
        //        jPAfficherAuteur.setVisible(true);
        //        jPModifierAuteur.setVisible(false);
        //        jPCreerAuteur.setVisible(false);
        //
        //        jPSupprimerCommande.setVisible(false);
        //        jPAfficherCommande.setVisible(false);
        //        jPModifierCommande.setVisible(false);
        //        jPCreerCommande.setVisible(false);
        //
        //        jPSupprimerClient.setVisible(false);
        //        jPAfficherClient.setVisible(false);
        //        jPModifierClient.setVisible(false);
        //        jPCreerClient.setVisible(false);
        if (jTableAuteurs.isRowSelected(jTableAuteurs.getSelectedRow())) {
            Auteur a = (Auteur) jTableAuteurs.getValueAt(jTableAuteurs.getSelectedRow(), 0);

            DisplayAuteurJFrame dajf = new DisplayAuteurJFrame();
            dajf.setVisible(true);
            dajf.setLocationRelativeTo(null);
            dajf.lastNamejTextField.setText(a.getLastName());
            dajf.firstNamejTextField.setText((a.getFirstName()));
            dajf.biojTextArea.setText((a.getBio()));
            dajf.notesjTextArea.setText(a.getNotes());

            if (a.getPicture() != null) {
                ImageIcon icon = new ImageIcon(a.getPicture());
                Image img = icon.getImage().getScaledInstance(dajf.imagejLabel.getWidth(), dajf.imagejLabel.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon iconResize = new ImageIcon(img);
                dajf.imagejLabel.setIcon(iconResize);
            }

            for (int i = 0; i < dajf.StatusjComboBox.getItemCount(); i++) {
                Statut status = (Statut) dajf.StatusjComboBox.getItemAt(i);
                if (status.getId().equals(a.getStatus().getId())) {
                    dajf.StatusjComboBox.setSelectedIndex(i);
                }
            }

        } else {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner un auteur");
        }

    }//GEN-LAST:event_jbAfficheAuteurActionPerformed

    private void jbModifierAuteurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbModifierAuteurActionPerformed
        //        jpLivres.setVisible(false);
        jpAuteurs.setVisible(true);
        //        jpEditeurs.setVisible(false);
        //        jpEvents.setVisible(false);
        //        jpDivers.setVisible(false);
        //        jpCommandes.setVisible(false);
        //        jpClients.setVisible(false);
        //        jpAvis.setVisible(false);

        //        jPSupprimerLivre.setVisible(false);
        //        jPAfficherLivre.setVisible(false);
        //        jPModifierLivre.setVisible(false);
        //        jPCreerLivre.setVisible(false);
        //        jPSupprimerEditeur.setVisible(false);
        //        jPAfficherEditeur.setVisible(false);
        //        jPModifierEditeur.setVisible(false);
        //        jPCreerEditeur.setVisible(false);
        //
        //        jPSupprimerAuteur.setVisible(false);
        //        jPAfficherAuteur.setVisible(false);
        //        jPModifierAuteur.setVisible(true);
        //        jPCreerAuteur.setVisible(false);
        //
        //        jPSupprimerCommande.setVisible(false);
        //        jPAfficherCommande.setVisible(false);
        //        jPModifierCommande.setVisible(false);
        //        jPCreerCommande.setVisible(false);
        //
        //        jPSupprimerClient.setVisible(false);
        //        jPAfficherClient.setVisible(false);
        //        jPModifierClient.setVisible(false);
        //        jPCreerClient.setVisible(false);
        if (jTableAuteurs.isRowSelected(jTableAuteurs.getSelectedRow())) {
            Auteur a = (Auteur) jTableAuteurs.getValueAt(jTableAuteurs.getSelectedRow(), 0);

            EditAuteurJFrame eajf = new EditAuteurJFrame();
            eajf.setMotherJFrame(this);
            eajf.setVisible(true);
            eajf.setLocationRelativeTo(null);
            eajf.IDjTextField.setText(a.getID());
            eajf.lastNamejTextField.setText(a.getLastName());
            eajf.firstNamejTextField.setText((a.getFirstName()));
            eajf.biojTextArea.setText((a.getBio()));
            eajf.notesjTextArea.setText(a.getNotes());

            for (int i = 0; i < eajf.StatusjComboBox.getItemCount(); i++) {
                Statut status = (Statut) eajf.StatusjComboBox.getItemAt(i);
                if (status.getId().equals(a.getStatus().getId())) {
                    eajf.StatusjComboBox.setSelectedIndex(i);
                }
            }

        } else {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner un auteur");
        }
    }//GEN-LAST:event_jbModifierAuteurActionPerformed

    private void jbSupprimerAuteurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSupprimerAuteurActionPerformed
        //        jpLivres.setVisible(false);
        jpAuteurs.setVisible(true);
        //        jpEditeurs.setVisible(false);
        //        jpEvents.setVisible(false);
        //        jpDivers.setVisible(false);
        //        jpCommandes.setVisible(false);
        //        jpClients.setVisible(false);
        //        jpAvis.setVisible(false);

        //        jPSupprimerLivre.setVisible(false);
        //        jPAfficherLivre.setVisible(false);
        //        jPModifierLivre.setVisible(false);
        //        jPCreerLivre.setVisible(false);
        //        jPSupprimerEditeur.setVisible(false);
        //        jPAfficherEditeur.setVisible(false);
        //        jPModifierEditeur.setVisible(false);
        //        jPCreerEditeur.setVisible(false);
        //
        //        jPSupprimerAuteur.setVisible(true);
        //        jPAfficherAuteur.setVisible(false);
        //        jPModifierAuteur.setVisible(false);
        //        jPCreerAuteur.setVisible(false);
        //
        //        jPSupprimerCommande.setVisible(false);
        //        jPAfficherCommande.setVisible(false);
        //        jPModifierCommande.setVisible(false);
        //        jPCreerCommande.setVisible(false);
        //
        //        jPSupprimerClient.setVisible(false);
        //        jPAfficherClient.setVisible(false);
        //        jPModifierClient.setVisible(false);
        //        jPCreerClient.setVisible(false);
        if (jTableAuteurs.isRowSelected(jTableAuteurs.getSelectedRow())) {
            Auteur a = (Auteur) jTableAuteurs.getValueAt(jTableAuteurs.getSelectedRow(), 0);
            int n = JOptionPane.showConfirmDialog(null, "Vous allez supprimer définitivement cet auteur", "Supprimer", JOptionPane.OK_CANCEL_OPTION);

            if (n == 0) {
                Xchange xch = new Xchange();
                xch.connect();

                PreparedStatement ps;

                try {
                    String query = "UPDATE AUTEUR "
                            + "SET STATUSID = ? "
                            + "WHERE AUTHORID = ?";
                    ps = xch.getConnexion().prepareStatement(query);
                    ps.setString(1, "599");
                    ps.setString(2, a.getID());

                    if (ps.executeUpdate() != 0) {
                        JOptionPane.showMessageDialog(null, "Auteur supprimé");
                        refreshTable();
                        xch.close();
                    } else {
                        JOptionPane.showMessageDialog(null, "Erreur");
                        xch.close();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner un auteur");
        }
    }//GEN-LAST:event_jbSupprimerAuteurActionPerformed

    private void jbCreerAuteurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCreerAuteurActionPerformed
        //        jpLivres.setVisible(false);
        jpAuteurs.setVisible(true);
        //        jpEditeurs.setVisible(false);
        //        jpEvents.setVisible(false);
        //        jpDivers.setVisible(false);
        //        jpCommandes.setVisible(false);
        //        jpClients.setVisible(false);
        //        jpAvis.setVisible(false);

        //        jPSupprimerLivre.setVisible(false);
        //        jPAfficherLivre.setVisible(false);
        //        jPModifierLivre.setVisible(false);
        //        jPCreerLivre.setVisible(false);
        //        jPSupprimerEditeur.setVisible(false);
        //        jPAfficherEditeur.setVisible(false);
        //        jPModifierEditeur.setVisible(false);
        //        jPCreerEditeur.setVisible(false);
        //
        //        jPSupprimerAuteur.setVisible(false);
        //        jPAfficherAuteur.setVisible(false);
        //        jPModifierAuteur.setVisible(false);
        //        jPCreerAuteur.setVisible(true);
        //
        //        jPSupprimerCommande.setVisible(false);
        //        jPAfficherCommande.setVisible(false);
        //        jPModifierCommande.setVisible(false);
        //        jPCreerCommande.setVisible(false);
        //
        //        jPSupprimerClient.setVisible(false);
        //        jPAfficherClient.setVisible(false);
        //        jPModifierClient.setVisible(false);
        //        jPCreerClient.setVisible(false);
        AddAuteurJFrame aajf = new AddAuteurJFrame();
        aajf.setVisible(true);
        aajf.setLocationRelativeTo(null);
        aajf.setMotherJFrame(this);

    }//GEN-LAST:event_jbCreerAuteurActionPerformed

    private void browsejButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browsejButtonActionPerformed
        // TODO add your handling code here:
        JFileChooser filec = new JFileChooser();
        filec.setCurrentDirectory(new File(System.getProperty("user.home")));

        FileNameExtensionFilter fileFilter = new FileNameExtensionFilter(".jpg .png .gif", "jpg", "png", "gif");
        filec.addChoosableFileFilter(fileFilter);

        int fileState = filec.showSaveDialog(null);

        if (fileState == JFileChooser.APPROVE_OPTION) {
            File selectedFile = filec.getSelectedFile();
            String path = selectedFile.getAbsolutePath();
            imagePth = path;
            //            picturejLabel.setIcon(new ImageIcon(path));
            imagejLabel.setIcon(resizePic(path));
        } else if (fileState == JFileChooser.CANCEL_OPTION) {
            System.out.println("");
        }
    }//GEN-LAST:event_browsejButtonActionPerformed

    private void addjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addjButtonActionPerformed
        // TODO add your handling code here:
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Pattern p01 = Pattern.compile("[- 'À-ȳ-a-zA-Z0-9]{1,150}");
        Matcher m01 = p01.matcher(namejTextField.getText());

        try {
            if (df.parse(date1jTextField.getText()) != null) {
                date1jTextField.getText();
            } else {
                JOptionPane.showMessageDialog(null, "Cette date n'est pas valide");
            }
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        try {
            if (df.parse(date2jTextField.getText()) != null) {
                date2jTextField.getText();
            } else {
                JOptionPane.showMessageDialog(null, "Cette date n'est pas valide");
            }
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        try {
            Float.parseFloat(discountjTextField.getText());
            discountjTextField.getText();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        if (m01.matches()) {

            Xchange xch = new Xchange();
            xch.connect();

            PreparedStatement ps;

            try {
                String query = "INSERT INTO EVENEMENT "
                        + "( STATUSID, EVENTNAME, EVENTSTARTINGDATE, EVENTENDINGDATE, EVENTDISCOUNT, EVENTDESCR, EVENTPICTURE, EVENTNOTES )"
                        + " VALUES "
                        + "( ?, ?, ?, ?, ?, ?, ?, ?)";
                ps = xch.getConnexion().prepareStatement(query);
                Statut status = (Statut) StatusjComboBox.getSelectedItem();
                ps.setString(1, status.getId().toString());
                ps.setString(2, namejTextField.getText());
                ps.setString(3, date1jTextField.getText());
                ps.setString(4, date2jTextField.getText());
                ps.setString(5, String.valueOf(discountjTextField.getText()));
                ps.setString(6, descrjTextArea.getText());
                if (imagePth == null) {
                    ps.setNull(7, java.sql.Types.BLOB);
                } else {
                    InputStream img = new FileInputStream(new File(imagePth));
                    ps.setBlob(7, img);
                }
                ps.setString(8, notesjTextArea.getText());

                if (ps.executeUpdate() != 0) {
                    JOptionPane.showMessageDialog(null, "Évènement ajouté");
                    refreshTableEvents();
                    xch.close();

                } else {
                    JOptionPane.showMessageDialog(null, "Erreur");
                    xch.close();
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Champs vides ou caractères non valides", "Erreur de saisie", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_addjButtonActionPerformed

    private void editjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editjButtonActionPerformed
        // TODO add your handling code here:
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Pattern p01 = Pattern.compile("[- 'À-ȳ-a-zA-Z0-9]{1,150}");
        Matcher m01 = p01.matcher(namejTextField.getText());

        try {
            if (df.parse(date1jTextField.getText()) != null) {
                date1jTextField.getText();
            } else {
                JOptionPane.showMessageDialog(null, "Cette date n'est pas valide");
            }
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        try {
            if (df.parse(date2jTextField.getText()) != null) {
                date2jTextField.getText();
            } else {
                JOptionPane.showMessageDialog(null, "Cette date n'est pas valide");
            }
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        try {
            Float.parseFloat(discountjTextField.getText());
            discountjTextField.getText();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        if (m01.matches()) {

            Xchange xch = new Xchange();
            xch.connect();

            PreparedStatement ps;

            try {
                String query = "UPDATE EVENEMENT "
                        + "SET STATUSID = ?, EVENTNAME = ?, EVENTSTARTINGDATE = ?, EVENTENDINGDATE = ?, "
                        + "EVENTDISCOUNT = ?, EVENTDESCR = ?, EVENTPICTURE = ?, EVENTNOTES = ? "
                        + "WHERE EVENTID = ? ";

                ps = xch.getConnexion().prepareStatement(query);
                Statut status = (Statut) StatusjComboBox.getSelectedItem();
                ps.setString(1, status.getId().toString());
                ps.setString(2, namejTextField.getText());
                ps.setString(3, date1jTextField.getText());
                ps.setString(4, date2jTextField.getText());
                ps.setString(5, String.valueOf(discountjTextField.getText()));
                ps.setString(6, descrjTextArea.getText());
                if (imagePth == null) {
                    ps.setNull(7, java.sql.Types.BLOB);
                } else {
                    InputStream img = new FileInputStream(new File(imagePth));
                    ps.setBlob(7, img);
                }
                ps.setString(8, notesjTextArea.getText());
                ps.setString(9, IDjLabel.getText());

                if (ps.executeUpdate() != 0) {
                    JOptionPane.showMessageDialog(null, "Évènement modifié");
                    refreshTableEvents();
                    xch.close();

                } else {
                    JOptionPane.showMessageDialog(null, "Erreur");
                    xch.close();
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Champs vides ou caractères non valides", "Erreur de saisie", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_editjButtonActionPerformed

    private void deletejButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletejButtonActionPerformed
        // TODO add your handling code here:
        if (EventjTable.isRowSelected(EventjTable.getSelectedRow())) {
            Event e = (Event) EventjTable.getValueAt(EventjTable.getSelectedRow(), 0);
            int n = JOptionPane.showConfirmDialog(null, "Vous allez supprimer définitivement cet évènement", "Supprimer", JOptionPane.OK_CANCEL_OPTION);

            if (n == 0) {
                Xchange xch = new Xchange();
                xch.connect();

                PreparedStatement ps;

                try {
                    String query = "UPDATE EVENEMENT "
                            + "SET STATUSID = ? "
                            + "WHERE EVENTID = ?";
                    ps = xch.getConnexion().prepareStatement(query);
                    ps.setString(1, "299");
                    ps.setString(2, e.getEventID());

                    if (ps.executeUpdate() != 0) {
                        JOptionPane.showMessageDialog(null, "Évènement supprimé");
                        refreshTableEvents();
                        xch.close();
                    } else {
                        JOptionPane.showMessageDialog(null, "Erreur");
                        xch.close();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner un évènement");
        }

    }//GEN-LAST:event_deletejButtonActionPerformed

    private void canceljButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_canceljButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_canceljButtonActionPerformed

    private void EventjTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EventjTableMouseClicked
        // TODO add your handling code here:
        if (EventjTable.isRowSelected(EventjTable.getSelectedRow())) {
            Event e = (Event) EventjTable.getValueAt(EventjTable.getSelectedRow(), 0);

            IDjLabel.setText(e.getEventID());
            namejTextField.setText(e.getName());
            date1jTextField.setText(e.getStarterDate());
            date2jTextField.setText(e.getEndingDate());
            discountjTextField.setText(String.valueOf(e.getDiscount()));
            descrjTextArea.setText(e.getDescription());
            notesjTextArea.setText(e.getNote());

            if (e.getImage() != null) {
                ImageIcon icon = new ImageIcon(e.getImage());
                Image img = icon.getImage().getScaledInstance(imagejLabel.getWidth(), imagejLabel.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon iconResize = new ImageIcon(img);
                imagejLabel.setIcon(iconResize);
            } else {
                imagejLabel.setIcon(null);
            }

            for (int i = 0; i < StatusjComboBox.getItemCount(); i++) {
                Statut status = (Statut) StatusjComboBox.getItemAt(i);
                if (status.getId().equals(e.getStatus().getId())) {
                    StatusjComboBox.setSelectedIndex(i);
                }
            }
        }
    }//GEN-LAST:event_EventjTableMouseClicked

    private void jbCreerMCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCreerMCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbCreerMCActionPerformed

    private void jbSupprimerMCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSupprimerMCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbSupprimerMCActionPerformed

    private void jbModifierMotCléActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbModifierMotCléActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbModifierMotCléActionPerformed

    private void jbAfficherLivreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAfficherLivreActionPerformed
        //        jpLivres.setVisible(false);
        //        jpAuteurs.setVisible(false);
        //        jpEditeurs.setVisible(false);
        //        jpEvents.setVisible(false);
        //        jpDivers.setVisible(false);
        //        jpCommandes.setVisible(false);
        //        jpClients.setVisible(false);
        //        jpAvis.setVisible(false);
        //
        //        jPSupprimerLivre.setVisible(false);
        //        jPAfficherLivre.setVisible(false);
        //        jPModifierLivre.setVisible(false);
        //        jPCreerLivre.setVisible(false);
        //
        //        jPSupprimerEditeur.setVisible(false);
        //        jPAfficherEditeur.setVisible(true);
        //        jPModifierEditeur.setVisible(false);
        //        jPCreerEditeur.setVisible(false);
        //
        //        jPSupprimerAuteur.setVisible(false);
        //        jPAfficherAuteur.setVisible(false);
        //        jPModifierAuteur.setVisible(false);
        //        jPCreerAuteur.setVisible(false);
        //
        //        jPSupprimerCommande.setVisible(false);
        //        jPAfficherCommande.setVisible(false);
        //        jPModifierCommande.setVisible(false);
        //        jPCreerCommande.setVisible(false);
        //
        //        jPSupprimerClient.setVisible(false);
        //        jPAfficherClient.setVisible(false);
        //        jPModifierClient.setVisible(false);
        //        jPCreerClient.setVisible(false);
        int rowSel = jTableLivres.getSelectedRow();
        if (rowSel == -1) {
            JOptionPane.showMessageDialog(rootPane, "Veuillez sélectionner une ligne", "Sélection", JOptionPane.INFORMATION_MESSAGE);
        } else {
            Livre LivreSel = (Livre) jTableLivres.getValueAt(rowSel, 0);
            JFLivreAff editMod = new JFLivreAff();
            editMod.setLivre(LivreSel);
            editMod.setTitle("Affichage");
            //editMod.setButton("aff");
            editMod.setJFL(this);
            editMod.textFill();
            editMod.setVisible(true);

        }
    }//GEN-LAST:event_jbAfficherLivreActionPerformed

    private void jbModifierLivreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbModifierLivreActionPerformed
        //        jpLivres.setVisible(false);
        //        jpAuteurs.setVisible(false);
        //        jpEditeurs.setVisible(false);
        //        jpEvents.setVisible(false);
        //        jpDivers.setVisible(false);
        //        jpCommandes.setVisible(false);
        //        jpClients.setVisible(false);
        //        jpAvis.setVisible(false);
        //
        //        jPSupprimerLivre.setVisible(false);
        //        jPAfficherLivre.setVisible(false);
        //        jPModifierLivre.setVisible(false);
        //        jPCreerLivre.setVisible(false);
        //
        //        jPSupprimerEditeur.setVisible(false);
        //        jPAfficherEditeur.setVisible(false);
        //        jPModifierEditeur.setVisible(true);
        //        jPCreerEditeur.setVisible(false);
        //
        //        jPSupprimerAuteur.setVisible(false);
        //        jPAfficherAuteur.setVisible(false);
        //        jPModifierAuteur.setVisible(false);
        //        jPCreerAuteur.setVisible(false);
        //
        //        jPSupprimerCommande.setVisible(false);
        //        jPAfficherCommande.setVisible(false);
        //        jPModifierCommande.setVisible(false);
        //        jPCreerCommande.setVisible(false);
        //
        //        jPSupprimerClient.setVisible(false);
        //        jPAfficherClient.setVisible(false);
        //        jPModifierClient.setVisible(false);
        //        jPCreerClient.setVisible(false);
        int rowSel = jTableLivres.getSelectedRow();
        if (rowSel == -1) {
            JOptionPane.showMessageDialog(rootPane, "Veuillez sélectionner une ligne", "Sélection", JOptionPane.INFORMATION_MESSAGE);
        } else {
            Livre LivreSel = (Livre) jTableLivres.getValueAt(rowSel, 0);
            JFLivreMod editMod = new JFLivreMod();
            editMod.setTitle("Modification");
            editMod.setLivre(LivreSel);
            editMod.setButton("mod");
            editMod.setJFL(this);
            editMod.textFill();
            editMod.setVisible(true);
            editMod.enregOff();
        }

    }//GEN-LAST:event_jbModifierLivreActionPerformed

    private void jbSupprimerLivreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSupprimerLivreActionPerformed
        //        jpLivres.setVisible(false);
        //        jpAuteurs.setVisible(false);
        //        jpEditeurs.setVisible(false);
        //        jpEvents.setVisible(false);
        //        jpDivers.setVisible(false);
        //        jpCommandes.setVisible(false);
        //        jpClients.setVisible(false);
        //        jpAvis.setVisible(false);
        //
        //        jPSupprimerLivre.setVisible(false);
        //        jPAfficherLivre.setVisible(false);
        //        jPModifierLivre.setVisible(false);
        //        jPCreerLivre.setVisible(false);
        //
        //        jPSupprimerEditeur.setVisible(true);
        //        jPAfficherEditeur.setVisible(false);
        //        jPModifierEditeur.setVisible(false);
        //        jPCreerEditeur.setVisible(false);
        //
        //        jPSupprimerAuteur.setVisible(false);
        //        jPAfficherAuteur.setVisible(false);
        //        jPModifierAuteur.setVisible(false);
        //        jPCreerAuteur.setVisible(false);
        //
        //        jPSupprimerCommande.setVisible(false);
        //        jPAfficherCommande.setVisible(false);
        //        jPModifierCommande.setVisible(false);
        //        jPCreerCommande.setVisible(false);
        //
        //        jPSupprimerClient.setVisible(false);
        //        jPAfficherClient.setVisible(false);
        //        jPModifierClient.setVisible(false);
        //        jPCreerClient.setVisible(false);
        int n;
        Xchange ech = new Xchange();
        ech.connect();
        int rowSel = jTableLivres.getSelectedRow();
        if (rowSel == -1) {
            JOptionPane.showMessageDialog(rootPane, "Veuillez sélectionner une ligne", "Sélection", JOptionPane.INFORMATION_MESSAGE);
        } else {
            Livre LivreSel = (Livre) jTableLivres.getValueAt(rowSel, 0);

            n = JOptionPane.showConfirmDialog(rootPane, "ATTENTION vous allez supprimer un editeur.\nCette action est irrémediable\nEtes-vous sûr ?", "Suppression", JOptionPane.OK_CANCEL_OPTION);
            if (n == 0) {
                Statut st = new Statut();
                try {
                    st.setId("1010");
                } catch (DataExceptions ex) {
                    JOptionPane.showMessageDialog(null, "Création statut " + ex.getErrorCode() + " / " + ex.getMessage(), "Livre", JOptionPane.ERROR_MESSAGE);
                }
                LivreSel.setStatut(st);
                LivreSel.save(ech, LivreSel.genValue(ech));

            }
            ech.close();
            //            JFLivreMod editMod = new JFLivreMod();
            //            editMod.setTitle("Suppression");
            //            editMod.setLivre(LivreSel);
            //            editMod.setButton("sup");
            //            editMod.setJFL(this);
            //            editMod.textFill();
            //            editMod.selectStat();
            //            editMod.setVisible(true);
            refreshLivre();
        }
    }//GEN-LAST:event_jbSupprimerLivreActionPerformed

    private void jbCreeLivreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCreeLivreActionPerformed
        //        jpLivres.setVisible(false);
        //        jpAuteurs.setVisible(false);
        //        jpEditeurs.setVisible(false);
        //        jpEvents.setVisible(false);
        //        jpDivers.setVisible(false);
        //        jpCommandes.setVisible(false);
        //        jpClients.setVisible(false);
        //        jpAvis.setVisible(false);
        //
        //        jPSupprimerLivre.setVisible(false);
        //        jPAfficherLivre.setVisible(false);
        //        jPModifierLivre.setVisible(false);
        //        jPCreerLivre.setVisible(false);
        //
        //        jPSupprimerEditeur.setVisible(false);
        //        jPAfficherEditeur.setVisible(false);
        //        jPModifierEditeur.setVisible(false);
        //        jPCreerEditeur.setVisible(true);
        //
        //        jPSupprimerAuteur.setVisible(false);
        //        jPAfficherAuteur.setVisible(false);
        //        jPModifierAuteur.setVisible(false);
        //        jPCreerAuteur.setVisible(false);
        //
        //        jPSupprimerCommande.setVisible(false);
        //        jPAfficherCommande.setVisible(false);
        //        jPModifierCommande.setVisible(false);
        //        jPCreerCommande.setVisible(false);
        //
        //        jPSupprimerClient.setVisible(false);
        //        jPAfficherClient.setVisible(false);
        //        jPModifierClient.setVisible(false);
        //        jPCreerClient.setVisible(false);

        JFLivreAj editMod = new JFLivreAj();
        editMod.setTitle("Nouveau Livre");
        editMod.setJFL(this);
        editMod.setButton("new");
        editMod.setVisible(true);

    }//GEN-LAST:event_jbCreeLivreActionPerformed

    private void jbNouveauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNouveauActionPerformed

        namejTextField.setText("");
        date1jTextField.setText("");
        date2jTextField.setText("");
        discountjTextField.setText("");
        descrjTextArea.setText("");
        notesjTextArea.setText("");
    }//GEN-LAST:event_jbNouveauActionPerformed

    private void jbAfficherClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAfficherClientActionPerformed
        //        jpLivres.setVisible(false);
        //        jpAuteurs.setVisible(false);
        //        jpEditeurs.setVisible(false);
        //        jpEvents.setVisible(false);
        //        jpDivers.setVisible(false);
        //        jpCommandes.setVisible(false);
        //System.out.println();
        jpClients.setVisible(true);
        //        jpAvis.setVisible(false);

        //        jPSupprimerLivre.setVisible(false);
        //        jPAfficherLivre.setVisible(false);
        //        jPModifierLivre.setVisible(false);
        //        jPCreerLivre.setVisible(false);
        //
        //        jPSupprimerEditeur.setVisible(false);
        //        jPAfficherEditeur.setVisible(false);
        //        jPModifierEditeur.setVisible(false);
        //        jPCreerEditeur.setVisible(false);
        //
        //        jPSupprimerAuteur.setVisible(false);
        //        jPAfficherAuteur.setVisible(false);
        //        jPModifierAuteur.setVisible(false);
        //        jPCreerAuteur.setVisible(false);
        //
        //        jPSupprimerCommande.setVisible(false);
        //        jPAfficherCommande.setVisible(false);
        //        jPModifierCommande.setVisible(false);
        //        jPCreerCommande.setVisible(false);
        //
        //        jPSupprimerClient.setVisible(false);
        //        jPAfficherClient.setVisible(true);
        //        jPModifierClient.setVisible(false);
        //        jPCreerClient.setVisible(false);
        int rowSel = jTableClients.getSelectedRow();
        if (rowSel == -1) {
            JOptionPane.showMessageDialog(rootPane, "Pour choisir un client, veuillez selectionner la ligne concernée dans la liste des clients", "Sélection", JOptionPane.INFORMATION_MESSAGE);
        } else {

            cliSelect = (Client) jTableClients.getValueAt(rowSel, 0);
            JFGClient.setClient(cliSelect);
            JFGClient.getjTabbedPane1().setSelectedIndex(0);
            JFGClient.setVisible(true);
            JFGClient.textFill();
            JFGClient.setTitle("Affichage");
            JFGClient.setJFCli(this);

        }
    }//GEN-LAST:event_jbAfficherClientActionPerformed

    private void jbModifieClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbModifieClientActionPerformed
        //        jpLivres.setVisible(false);
        //        jpAuteurs.setVisible(false);
        //        jpEditeurs.setVisible(false);
        //        jpEvents.setVisible(false);
        //        jpDivers.setVisible(false);
        //        jpCommandes.setVisible(false);
        //
        //        jpAvis.setVisible(false);
        jpClients.setVisible(true);
        //        jPSupprimerLivre.setVisible(false);
        //        jPAfficherLivre.setVisible(false);
        //        jPModifierLivre.setVisible(false);
        //        jPCreerLivre.setVisible(false);
        //
        //        jPSupprimerEditeur.setVisible(false);
        //        jPAfficherEditeur.setVisible(false);
        //        jPModifierEditeur.setVisible(false);
        //        jPCreerEditeur.setVisible(false);
        //
        //        jPSupprimerAuteur.setVisible(false);
        //        jPAfficherAuteur.setVisible(false);
        //        jPModifierAuteur.setVisible(false);
        //        jPCreerAuteur.setVisible(false);
        //
        //        jPSupprimerCommande.setVisible(false);
        //        jPAfficherCommande.setVisible(false);
        //        jPModifierCommande.setVisible(false);
        //        jPCreerCommande.setVisible(false);
        //
        //        jPSupprimerClient.setVisible(false);
        //        jPAfficherClient.setVisible(false);
        //        jPModifierClient.setVisible(true);
        //        jPCreerClient.setVisible(false);

        int rowSel = jTableClients.getSelectedRow();
        if (rowSel == -1) {
            JOptionPane.showMessageDialog(rootPane, "Pour choisir un client, veuillez selectionner la ligne concernée dans la liste des clients", "Sélection", JOptionPane.INFORMATION_MESSAGE);
        } else {

            cliSelect = (Client) jTableClients.getValueAt(rowSel, 0);
            JFGClient.setClient(cliSelect);
            JFGClient.setVisible(true);
            JFGClient.getjTabbedPane1().setSelectedIndex(1);
            JFGClient.setTitle("Modification");
            JFGClient.setJFCli(this);
            JFGClient.textFill();

        }
    }//GEN-LAST:event_jbModifieClientActionPerformed

    private void jbSupprimerClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSupprimerClientActionPerformed
        //        jpLivres.setVisible(false);
        //        jpAuteurs.setVisible(false);
        //        jpEditeurs.setVisible(false);
        //        jpEvents.setVisible(false);
        //        jpDivers.setVisible(false);
        //        jpCommandes.setVisible(false);
        //
        //        jpAvis.setVisible(false);
        jpClients.setVisible(true);
        //        jPSupprimerLivre.setVisible(false);
        //        jPAfficherLivre.setVisible(false);
        //        jPModifierLivre.setVisible(false);
        //        jPCreerLivre.setVisible(false);
        //
        //        jPSupprimerEditeur.setVisible(false);
        //        jPAfficherEditeur.setVisible(false);
        //        jPModifierEditeur.setVisible(false);
        //        jPCreerEditeur.setVisible(false);
        //
        //        jPSupprimerAuteur.setVisible(false);
        //        jPAfficherAuteur.setVisible(false);
        //        jPModifierAuteur.setVisible(false);
        //        jPCreerAuteur.setVisible(false);
        //
        //        jPSupprimerCommande.setVisible(false);
        //        jPAfficherCommande.setVisible(false);
        //        jPModifierCommande.setVisible(false);
        //        jPCreerCommande.setVisible(false);
        //
        //        jPSupprimerClient.setVisible(true);
        //        jPAfficherClient.setVisible(false);
        //        jPModifierClient.setVisible(false);
        //        jPCreerClient.setVisible(false);

        int rowSel = jTableClients.getSelectedRow();
        if (rowSel == -1) {
            JOptionPane.showMessageDialog(rootPane, "Pour choisir un client, veuillez selectionner la ligne concernée dans la liste des clients", "Sélection", JOptionPane.INFORMATION_MESSAGE);
        } else {

            int n;

            n = JOptionPane.showConfirmDialog(rootPane, "ATTENTION vous allez supprimer un client.\nCette action est irrémediable\n'OK' pour valider la suppression ou 'ANNULER'.", "Suppression", JOptionPane.OK_CANCEL_OPTION);
            if (n == 0) {

                Client client = (Client) jTableClients.getValueAt(rowSel, 0);
                try {
                    client.getStatus().setId("799");
                } catch (DataExceptions ex) {
                    JOptionPane.showMessageDialog(null, "Modif Status: " + ex.getErrorCode() + " / " + ex.getMessage(), "Client", JOptionPane.ERROR_MESSAGE);
                }
                Xchange ech = new Xchange();
                ech.connect();
                client.save(ech, client.genValue());
                ech.connect();

                refreshClient();
            }

        }
    }//GEN-LAST:event_jbSupprimerClientActionPerformed

    private void jbCreerClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCreerClientActionPerformed
        //        jpLivres.setVisible(false);
        //        jpAuteurs.setVisible(false);
        //        jpEditeurs.setVisible(false);
        //        jpEvents.setVisible(false);
        //        jpDivers.setVisible(false);
        //        jpCommandes.setVisible(false);
        //
        //        jpAvis.setVisible(false);
        jpClients.setVisible(true);
        //        jPSupprimerLivre.setVisible(false);
        //        jPAfficherLivre.setVisible(false);
        //        jPModifierLivre.setVisible(false);
        //        jPCreerLivre.setVisible(false);
        //
        //        jPSupprimerEditeur.setVisible(false);
        //        jPAfficherEditeur.setVisible(false);
        //        jPModifierEditeur.setVisible(false);
        //        jPCreerEditeur.setVisible(false);
        //
        //        jPSupprimerAuteur.setVisible(false);
        //        jPAfficherAuteur.setVisible(false);
        //        jPModifierAuteur.setVisible(false);
        //        jPCreerAuteur.setVisible(false);
        //
        //        jPSupprimerCommande.setVisible(false);
        //        jPAfficherCommande.setVisible(false);
        //        jPModifierCommande.setVisible(false);
        //        jPCreerCommande.setVisible(false);
        //
        //        jPSupprimerClient.setVisible(false);
        //        jPAfficherClient.setVisible(false);
        //        jPModifierClient.setVisible(false);
        //        jPCreerClient.setVisible(true);

        JFGClient.setVisible(true);

        JFGClient.getjTabbedPane1().setSelectedIndex(2);
        JFGClient.setTitle("Création d'un nouveau client");
        JFGClient.setJFCli(this);

    }//GEN-LAST:event_jbCreerClientActionPerformed

    private void jbFermerJFClientsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbFermerJFClientsActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jbFermerJFClientsActionPerformed

    private void jbAfficherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAfficherActionPerformed

        int rowSel = jtAvis.getSelectedRow();
        if (rowSel == -1) {
            JOptionPane.showMessageDialog(rootPane, "Pour choisir un avis, veuillez selectionner la ligne concernée dans la liste des avis", "Sélection", JOptionPane.INFORMATION_MESSAGE);
        } else {
            Commentaire comment = (Commentaire) jtAvis.getValueAt(rowSel, 0);
            jtfAvisStatut.setText(comment.getStatut().toString());
            jtaDetailAvis.setText(comment.getNotes());
            this.comment = comment;
        }
    }//GEN-LAST:event_jbAfficherActionPerformed

    private void jbValiderAvisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbValiderAvisActionPerformed
        if (comment != null) {
            try {
                comment.getStatut().setId("320");
            } catch (DataExceptions ex) {
                Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
            }
            Xchange ech = new Xchange();
            ech.connect();
            comment.save(ech, comment.genValue());
            ech.close();
            if (jcbAvisSt.getSelectedItem() != null) {
                Statut st = (Statut) jcbAvisSt.getSelectedItem();
                refreshComment(st.getId());
            }
        }


    }//GEN-LAST:event_jbValiderAvisActionPerformed

    private void jcbAvisStActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbAvisStActionPerformed

        if (jcbAvisSt.getSelectedItem() != null) {
            Statut st = (Statut) jcbAvisSt.getSelectedItem();
            refreshComment(st.getId());

        }

    }//GEN-LAST:event_jcbAvisStActionPerformed

    private void jbRefuserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRefuserActionPerformed
        
                if (comment != null) {
            try {
                comment.getStatut().setId("340");
            } catch (DataExceptions ex) {
                Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
            }
            Xchange ech = new Xchange();
            ech.connect();
            comment.save(ech, comment.genValue());
            ech.close();
            if (jcbAvisSt.getSelectedItem() != null) {
                Statut st = (Statut) jcbAvisSt.getSelectedItem();
                refreshComment(st.getId());
            }
        }
    }//GEN-LAST:event_jbRefuserActionPerformed

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
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomePage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BrowsejButton1;
    private javax.swing.JTable EventjTable;
    private javax.swing.JLabel IDjLabel;
    private javax.swing.JComboBox StatusjComboBox;
    public javax.swing.JComboBox StatusjComboBox2;
    private javax.swing.JButton addjButton;
    private javax.swing.JButton browsejButton;
    private javax.swing.JButton canceljButton;
    private javax.swing.JButton canceljButton1;
    private javax.swing.JLabel date1jLabel;
    private javax.swing.JTextField date1jTextField;
    private javax.swing.JLabel date2jLabel;
    private javax.swing.JTextField date2jTextField;
    private javax.swing.JButton deletejButton;
    private javax.swing.JLabel descrjLabel;
    private javax.swing.JTextArea descrjTextArea;
    private javax.swing.JLabel discountjLabel;
    private javax.swing.JTextField discountjTextField;
    private javax.swing.JButton editjButton;
    private javax.swing.JButton editjButton2;
    private javax.swing.JLabel firstNamejLabel1;
    public javax.swing.JTextField firstNamejTextField1;
    private javax.swing.JLabel imagejLabel;
    public javax.swing.JLabel imagejLabel2;
    private javax.swing.JLabel jLabTitre;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JList jList1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelTitre;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollTableAuteurs;
    private javax.swing.JScrollPane jScrollTableClients;
    private javax.swing.JScrollPane jScrollTableCommandes;
    private javax.swing.JScrollPane jScrollTableEditeurs;
    private javax.swing.JScrollPane jScrollTableLivres;
    private javax.swing.JTabbedPane jTabbedPane1;
    public javax.swing.JTable jTableAuteurs;
    private javax.swing.JTable jTableClients;
    private javax.swing.JTable jTableCommande;
    private javax.swing.JTable jTableEditeurs;
    private javax.swing.JTable jTableLivres;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JButton jbAfficheAuteur;
    private javax.swing.JButton jbAfficher;
    private javax.swing.JButton jbAfficherClient;
    private javax.swing.JButton jbAfficherCommande;
    private javax.swing.JButton jbAfficherEditeur;
    private javax.swing.JButton jbAfficherLivre;
    private javax.swing.JButton jbAuteurs;
    private javax.swing.JButton jbAvis;
    private javax.swing.JButton jbClients;
    private javax.swing.JButton jbCommandes;
    private javax.swing.JButton jbCreeEditeur;
    private javax.swing.JButton jbCreeLivre;
    private javax.swing.JButton jbCreerAuteur;
    private javax.swing.JButton jbCreerClient;
    private javax.swing.JButton jbCreerCommande;
    private javax.swing.JButton jbCreerMC;
    private javax.swing.JButton jbDivers;
    private javax.swing.JButton jbEditeurs;
    private javax.swing.JButton jbEvents;
    private javax.swing.JButton jbFermer;
    private javax.swing.JButton jbFermerJFClients;
    private javax.swing.JButton jbLivres;
    private javax.swing.JButton jbModifieClient;
    private javax.swing.JButton jbModifierAuteur;
    private javax.swing.JButton jbModifierCommande;
    private javax.swing.JButton jbModifierEditeur;
    private javax.swing.JButton jbModifierLivre;
    private javax.swing.JButton jbModifierMotClé;
    private javax.swing.JButton jbNouveau;
    private javax.swing.JButton jbRefuser;
    private javax.swing.JButton jbSupprimerAuteur;
    private javax.swing.JButton jbSupprimerClient;
    private javax.swing.JButton jbSupprimerCommande;
    private javax.swing.JButton jbSupprimerEditeur;
    private javax.swing.JButton jbSupprimerLivre;
    private javax.swing.JButton jbSupprimerMC;
    private javax.swing.JButton jbValiderAvis;
    private javax.swing.JComboBox jcbAvisSt;
    private javax.swing.JLabel jlSelectionavis;
    private javax.swing.JLabel jlabSelectionnerAuteurs;
    private javax.swing.JLabel jlabSelectionnerClients;
    private javax.swing.JLabel jlabSelectionnerCommandes;
    private javax.swing.JLabel jlabSelectionnerEditeur;
    private javax.swing.JLabel jlabSelectionnerLivre;
    private javax.swing.JLabel jlabSelectionnerLivre1;
    private javax.swing.JPanel jpAuteurs;
    private javax.swing.JPanel jpAvis;
    private javax.swing.JPanel jpAvisAffichage;
    private javax.swing.JPanel jpAvisSelection;
    private javax.swing.JPanel jpClients;
    private javax.swing.JPanel jpCommandes;
    private javax.swing.JPanel jpDivers;
    private javax.swing.JPanel jpEditeurs;
    private javax.swing.JPanel jpEvents;
    private javax.swing.JPanel jpLivres;
    private javax.swing.JTable jtAvis;
    private javax.swing.JTextArea jtaDetailAvis;
    private javax.swing.JTextField jtfAvisStatut;
    private javax.swing.JLabel lastNamejLabel1;
    public javax.swing.JTextField lastNamejTextField1;
    private javax.swing.JLabel namejLabel;
    private javax.swing.JTextField namejTextField;
    private javax.swing.JLabel notesjLabel;
    private javax.swing.JLabel notesjLabel2;
    private javax.swing.JScrollPane notesjScrollPane1;
    private javax.swing.JTextArea notesjTextArea;
    public javax.swing.JTextArea notesjTextArea2;
    private javax.swing.JLabel picjLabel;
    private javax.swing.JLabel picturejLabel1;
    private javax.swing.JLabel statusjLabel;
    // End of variables declaration//GEN-END:variables

    private Icon resizePic(String path) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
