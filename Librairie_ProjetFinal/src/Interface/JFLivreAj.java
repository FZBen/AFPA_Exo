package Interface;


import ObjetsSimples.Auteur;
import ObjetsSimples.Editeur;
import ObjetsSimples.EditeurCollection;
import ObjetsSimples.Format;
import ObjetsSimples.Grille;
import ObjetsSimples.Livre;
import ObjetsSimples.LivreVolume;
import ObjetsSimples.Statut;
import ObjetsSimples.Theme;
import com.sun.corba.se.impl.resolver.SplitLocalResolverImpl;
import exceptions.DataExceptions;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import xchange.Xchange;

/**
 *
 * @author cda201
 */
public class JFLivreAj extends javax.swing.JFrame {

    private Livre livre;
    private LivreVolume vol;
    private HomePage JFL;
    private String button;

    /**
     * Creates new form JFEditeurMod
     */
    public JFLivreAj() {
        initComponents();
        this.livre = new Livre();
        this.vol = new LivreVolume();
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }

    public HomePage getJFL() {
        return JFL;
    }

    public void setJFL(HomePage JFL) {
        this.JFL = JFL;
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public LivreVolume getVol() {
        return vol;
    }

    public void setVol(LivreVolume vol) {
        this.vol = vol;
    }
    

    public void enregOff() {
        jbEnregistrer.setVisible(true);
    }
    
  

    public void textFill() {
//        jtfTitre.setText(livre.getTitle());
//        jtfSerie.setText(livre.getSerie());
//        jtfSoustitre.setText(livre.getSubTitle());
//        jtfDatedeSortie.setText(livre.getReleaseDate());
//        jtfNombredepage.setText(livre.getPaging());
//        jtfExemplairesdisponibles.setText(livre.getAvailableStock());
//        jtfPrix.setText(livre.getPrice());
//     
//        jtaNotes.append(livre.getNotes());
//        jtaNotes.setWrapStyleWord(true);
//        jtaNotes.setLineWrap(true);
//    
//        
//        jtaSynopsis.append(livre.getSynopsis());
//        jtaSynopsis.setWrapStyleWord(true);
//        jtaSynopsis.setLineWrap(true);

        for (int i = 0; i < jcbStatus.getItemCount(); i++) {
            Statut st = (Statut) jcbStatus.getItemAt(i);
            if (st.getId().equals(livre.getStatut().getId())) {
                jcbStatus.setSelectedIndex(i);
            }
        }
        
        for (int i = 0; i < jcbAuteur.getItemCount(); i++) {
            Auteur at = (Auteur) jcbAuteur.getItemAt(i);
            if (at.getID().equals(livre.getAuthor().getID())) {
                jcbAuteur.setSelectedIndex(i);
            }
        }
        
        for (int i = 0; i < jcbEditeur.getItemCount(); i++) {
            Editeur ed = (Editeur) jcbEditeur.getItemAt(i);
            if (ed.getID().equals(livre.getPublisherColl().getPublisher().getID())) {
                jcbEditeur.setSelectedIndex(i);
            }
            
        }
        
        for (int i = 0; i < jcbTheme.getItemCount(); i++) {
            Theme th = (Theme) jcbTheme.getItemAt(i);
            if (th.getID().equals(livre.getTheme().getID())) {
                jcbTheme.setSelectedIndex(i);
            }

    }
        
        for (int i = 0; i < jcbSousthème.getItemCount(); i++) {
            Theme th = (Theme) jcbSousthème.getItemAt(i);
            if (th.getID().equals(livre.getSoustheme().getID())) {
                jcbSousthème.setSelectedIndex(i);
            }

    }
        
        for (int i = 0; i < jcbFormat.getItemCount(); i++) {
            Format fmrt = (Format) jcbFormat.getItemAt(i);
            if (fmrt.getId().equals(livre.getFormat().getId())) {
                jcbFormat.setSelectedIndex(i);
            }

    }
        
     
    }

    public void selectStat() {
        jcbStatus.setSelectedIndex(jcbStatus.getItemCount() - 1);
    }
    
     public void selectAut() {
        jcbAuteur.setSelectedIndex(jcbAuteur.getItemCount() - 1);
    }
     public void selectEd() {
        jcbEditeur.setSelectedIndex(jcbEditeur.getItemCount() - 1);
    }
     
     public void selectTh() {
        jcbTheme.setSelectedIndex(jcbTheme.getItemCount() - 1);
    }
     
     public void selectssTh() {
        jcbSousthème.setSelectedIndex(jcbSousthème.getItemCount() - 1);
    }
     
     public void selectFr() {
        jcbFormat.setSelectedIndex(jcbFormat.getItemCount() - 1);
    }

    private DefaultComboBoxModel initStatus() {
        Vector v = new Vector();

        Xchange ech = new Xchange();
        Statement stat = null;
        String query = "SELECT STATUSID, STATUSNAME,STATUS_DESCR FROM STATUS "
                + "WHERE STATUSID BETWEEN 400 AND 499";
        ResultSet rs = null;

        try {

            ech.connect();
            stat = ech.getConnexion().createStatement();
            rs = stat.executeQuery(query);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Création connexion : " + ex.getErrorCode() + " / " + ex.getMessage(), "EditeurMod", JOptionPane.ERROR_MESSAGE);;
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
            JOptionPane.showMessageDialog(null, "Lecture données : " + ex.getErrorCode() + " / " + ex.getMessage(), "EditeurMod", JOptionPane.ERROR_MESSAGE);
        } catch (DataExceptions ex) {
            JOptionPane.showMessageDialog(null, "Lecture données : " + ex.getErrorCode() + " / " + ex.getMessage(), "EditeurMod", JOptionPane.ERROR_MESSAGE);
        } finally {
            ech.close();
        }

        return new DefaultComboBoxModel(v);
    }
    private DefaultComboBoxModel initAuteur() {
        Vector v = new Vector();

        Xchange ech = new Xchange();
        Statement stat = null;
        String query = "SELECT AUTHORID, AUTHORFIRSTNAME, AUTHORLASTNAME FROM AUTEUR ";
        ResultSet rs = null;

        try {

            ech.connect();
            stat = ech.getConnexion().createStatement();
            rs = stat.executeQuery(query);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Création connexion : " + ex.getErrorCode() + " / " + ex.getMessage(), "EditeurMod", JOptionPane.ERROR_MESSAGE);;
        }
        try {
            while (rs.next()) {
                Auteur at = new Auteur();
                at.setID(rs.getString("AUTHORID"));
                at.setFirstName(rs.getString("AUTHORFIRSTNAME"));
                at.setLastName(rs.getString("AUTHORLASTNAME"));

               v.add(at);

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lecture données : " + ex.getErrorCode() + " / " + ex.getMessage(), "EditeurMod", JOptionPane.ERROR_MESSAGE);
        } catch (DataExceptions ex) {
            JOptionPane.showMessageDialog(null, "Lecture données : " + ex.getErrorCode() + " / " + ex.getMessage(), "EditeurMod", JOptionPane.ERROR_MESSAGE);
        } finally {
            ech.close();
        }

        return new DefaultComboBoxModel(v);
    }
    private DefaultComboBoxModel initEditeur() {
        Vector v = new Vector();

        Xchange ech = new Xchange();
        Statement stat = null;
        String query = "SELECT PUBLISHERID, EDITEUR_NOM FROM EDITEUR ";
        ResultSet rs = null;

        try {

            ech.connect();
            stat = ech.getConnexion().createStatement();
            rs = stat.executeQuery(query);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Création connexion : " + ex.getErrorCode() + " / " + ex.getMessage(), "EditeurMod", JOptionPane.ERROR_MESSAGE);;
        }
        try {
            while (rs.next()) {
                Editeur ed = new Editeur();
                ed.setID(rs.getString("PUBLISHERID"));
                ed.setName(rs.getString("EDITEUR_NOM"));

               v.add(ed);

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lecture données : " + ex.getErrorCode() + " / " + ex.getMessage(), "EditeurMod", JOptionPane.ERROR_MESSAGE);
        } catch (DataExceptions ex) {
            JOptionPane.showMessageDialog(null, "Lecture données : " + ex.getErrorCode() + " / " + ex.getMessage(), "EditeurMod", JOptionPane.ERROR_MESSAGE);
        } finally {
            ech.close();
        }

        return new DefaultComboBoxModel(v);
    }
    
   private DefaultComboBoxModel initTheme() {
        Vector v = new Vector();

        Xchange ech = new Xchange();
        Statement stat = null;
        String query = "SELECT THEMEID, THEMENAME FROM THEME ";
        ResultSet rs = null;

        try {

            ech.connect();
            stat = ech.getConnexion().createStatement();
            rs = stat.executeQuery(query);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Création connexion : " + ex.getErrorCode() + " / " + ex.getMessage(), "EditeurMod", JOptionPane.ERROR_MESSAGE);;
        }
        try {
            while (rs.next()) {
                Theme th = new Theme();
                th.setID(rs.getString("THEMEID"));
                th.setName(rs.getString("THEMENAME"));

               v.add(th);

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lecture données : " + ex.getErrorCode() + " / " + ex.getMessage(), "EditeurMod", JOptionPane.ERROR_MESSAGE);
        } catch (DataExceptions ex) {
            JOptionPane.showMessageDialog(null, "Lecture données : " + ex.getErrorCode() + " / " + ex.getMessage(), "EditeurMod", JOptionPane.ERROR_MESSAGE);
        } finally {
            ech.close();
        }

        return new DefaultComboBoxModel(v);
    }
   
   private DefaultComboBoxModel initsousTheme(String id) {
        Vector v = new Vector();

        Xchange ech = new Xchange();
        Statement stat = null;
        String query = "SELECT ss.THEMEID, ss.SOUS_THEMEID, THEMENAME FROM THEME th JOIN INCLURE ss on th.THEMEID = ss.SOUS_THEMEID WHERE ss.THEMEID =" + id;
        ResultSet rs = null;

        try {

            ech.connect();
            stat = ech.getConnexion().createStatement();
            rs = stat.executeQuery(query);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Création connexion : " + ex.getErrorCode() + " / " + ex.getMessage(), "EditeurMod", JOptionPane.ERROR_MESSAGE);;
        }
        try {
            while (rs.next()) {
                Theme st = new Theme();
                st.setID(rs.getString("SOUS_THEMEID"));
                st.setName(rs.getString("THEMENAME"));

               v.add(st);

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lecture données : " + ex.getErrorCode() + " / " + ex.getMessage(), "EditeurMod", JOptionPane.ERROR_MESSAGE);
        } catch (DataExceptions ex) {
            JOptionPane.showMessageDialog(null, "Lecture données : " + ex.getErrorCode() + " / " + ex.getMessage(), "EditeurMod", JOptionPane.ERROR_MESSAGE);
        } finally {
            ech.close();
        }

        return new DefaultComboBoxModel(v);
    }
    
   private DefaultComboBoxModel initFormat() {
        Vector v = new Vector();

        Xchange ech = new Xchange();
        Statement stat = null;
        String query = "SELECT FORMATID, FORMATNAME FROM FORMAT ";
        ResultSet rs = null;

        try {

            ech.connect();
            stat = ech.getConnexion().createStatement();
            rs = stat.executeQuery(query);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Création connexion : " + ex.getErrorCode() + " / " + ex.getMessage(), "EditeurMod", JOptionPane.ERROR_MESSAGE);;
        }
        try {
            while (rs.next()) {
                Format fmrt = new Format();
                fmrt.setId(rs.getString("FORMATID"));
                fmrt.setFormatname(rs.getString("FORMATNAME"));

               v.add(fmrt);

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lecture données : " + ex.getErrorCode() + " / " + ex.getMessage(), "EditeurMod", JOptionPane.ERROR_MESSAGE);
        } catch (DataExceptions ex) {
            JOptionPane.showMessageDialog(null, "Lecture données : " + ex.getErrorCode() + " / " + ex.getMessage(), "EditeurMod", JOptionPane.ERROR_MESSAGE);
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

        jlActif = new javax.swing.JLabel();
        jlTitre = new javax.swing.JLabel();
        jtfSerie = new javax.swing.JTextField();
        jlSoustitre = new javax.swing.JLabel();
        jtfSoustitre = new javax.swing.JTextField();
        jlAuteur = new javax.swing.JLabel();
        jlEditeur = new javax.swing.JLabel();
        jcbStatus = new javax.swing.JComboBox();
        jbEnregistrer = new javax.swing.JButton();
        jbFermer = new javax.swing.JButton();
        jtfNombredepage = new javax.swing.JTextField();
        jtfDatedeSortie = new javax.swing.JTextField();
        jtfExemplairesdisponibles = new javax.swing.JTextField();
        jtfPrix = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaNotes = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtaSynopsis = new javax.swing.JTextArea();
        jlNombresdepages = new javax.swing.JLabel();
        jlDatedesortie = new javax.swing.JLabel();
        jlExemplairesdisponibles = new javax.swing.JLabel();
        jlPrix = new javax.swing.JLabel();
        jlSynopsis = new javax.swing.JLabel();
        jlNotes = new javax.swing.JLabel();
        jcbAuteur = new javax.swing.JComboBox();
        jcbEditeur = new javax.swing.JComboBox();
        jlSerie = new javax.swing.JLabel();
        jtfISBN13 = new javax.swing.JTextField();
        jcbSousthème = new javax.swing.JComboBox();
        jcbTheme = new javax.swing.JComboBox();
        jlThème = new javax.swing.JLabel();
        jlSousthème = new javax.swing.JLabel();
        jcbFormat = new javax.swing.JComboBox();
        jlFormat = new javax.swing.JLabel();
        jtfTitre = new javax.swing.JTextField();
        jtfISBN10 = new javax.swing.JTextField();
        jlISBN10 = new javax.swing.JLabel();
        jlISBN13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jlActif.setText("Statut");

        jlTitre.setText("Titre");

        jtfSerie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfSerieActionPerformed(evt);
            }
        });

        jlSoustitre.setText("Sous-titre");

        jlAuteur.setText(" Auteur");

        jlEditeur.setText("Editeur");

        jcbStatus.setModel(initStatus());
        jcbStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbStatusActionPerformed(evt);
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

        jtfPrix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfPrixActionPerformed(evt);
            }
        });

        jtaNotes.setColumns(20);
        jtaNotes.setRows(5);
        jScrollPane1.setViewportView(jtaNotes);

        jtaSynopsis.setColumns(20);
        jtaSynopsis.setRows(5);
        jScrollPane2.setViewportView(jtaSynopsis);

        jlNombresdepages.setText("Nombres de page");

        jlDatedesortie.setText("Date de Sortie");

        jlExemplairesdisponibles.setText("Exemplaires disponibles");

        jlPrix.setText("Prix");

        jlSynopsis.setText("Synopsis");

        jlNotes.setText("Notes");

        jcbAuteur.setModel(initAuteur());
        jcbAuteur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbAuteurActionPerformed(evt);
            }
        });

        jcbEditeur.setModel(initEditeur());
        jcbEditeur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbEditeurActionPerformed(evt);
            }
        });

        jlSerie.setText("Serie");

        jtfISBN13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfISBN13ActionPerformed(evt);
            }
        });

        jcbSousthème.setModel(initTheme());
        jcbSousthème.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbSousthèmeActionPerformed(evt);
            }
        });

        jcbTheme.setModel(initTheme());
        jcbTheme.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbThemeItemStateChanged(evt);
            }
        });
        jcbTheme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbThemeActionPerformed(evt);
            }
        });

        jlThème.setText("Thème");

        jlSousthème.setText("Sous-thème");

        jcbFormat.setModel(initFormat());
        jcbFormat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbFormatActionPerformed(evt);
            }
        });

        jlFormat.setText("Format");

        jtfTitre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfTitreActionPerformed(evt);
            }
        });

        jtfISBN10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfISBN10ActionPerformed(evt);
            }
        });

        jlISBN10.setText("ISBN10");

        jlISBN13.setText("ISBN13");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlAuteur)
                            .addComponent(jlPrix)
                            .addComponent(jlActif)
                            .addComponent(jlExemplairesdisponibles)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jlThème)
                                .addComponent(jlEditeur))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jlTitre)
                                    .addComponent(jlSousthème)
                                    .addComponent(jlSoustitre)
                                    .addComponent(jlSerie)
                                    .addComponent(jlNombresdepages)
                                    .addComponent(jlDatedesortie)))
                            .addComponent(jlFormat)
                            .addComponent(jlISBN10)
                            .addComponent(jlISBN13))
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jtfPrix)
                                    .addComponent(jtfExemplairesdisponibles)
                                    .addComponent(jtfDatedeSortie)
                                    .addComponent(jtfNombredepage)
                                    .addComponent(jcbStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jcbAuteur, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jcbEditeur, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jtfSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jtfSoustitre, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jcbSousthème, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jcbTheme, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jtfISBN13, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jcbFormat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jtfISBN10, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfTitre, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(83, 83, 83)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlSynopsis)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                                .addComponent(jScrollPane1))
                            .addComponent(jlNotes)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbEnregistrer)
                        .addGap(336, 336, 336)
                        .addComponent(jbFermer)))
                .addContainerGap(246, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlSynopsis)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jlNotes)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbEnregistrer)
                            .addComponent(jbFermer))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 115, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtfISBN13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlISBN13))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtfISBN10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlISBN10))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcbAuteur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlAuteur))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcbEditeur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlEditeur))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcbTheme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlThème))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcbSousthème, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlSousthème))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlTitre)
                            .addComponent(jtfTitre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtfSoustitre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlSoustitre))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtfSerie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlSerie))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtfNombredepage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlNombresdepages))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtfDatedeSortie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlDatedesortie))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtfExemplairesdisponibles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlExemplairesdisponibles))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtfPrix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlPrix))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlActif))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcbFormat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlFormat))
                        .addGap(191, 191, 191))))
        );

        setBounds(0, 0, 975, 931);
    }// </editor-fold>//GEN-END:initComponents

    private void jtfSerieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfSerieActionPerformed


    }//GEN-LAST:event_jtfSerieActionPerformed

    private void jbFermerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbFermerActionPerformed
        this.dispose();
    }//GEN-LAST:event_jbFermerActionPerformed

    private void jbEnregistrerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEnregistrerActionPerformed
        //mise à jour de l'objet avec controle d'erreur grâce à error
            if (this.button.equals("new")) {
            int error = 0;
            //editeur.setEditDate();
            
  
            
            try {
                //System.out.println(jtfTitre.getText());
                livre.setTitle(jtfTitre.getText());
            } catch (DataExceptions ex) {
                JOptionPane.showMessageDialog(null, "Ecriture Titre : " + ex.getErrorCode() + " / " + ex.getMessage(), "LivreMod", JOptionPane.ERROR_MESSAGE);
                error += 1;
            }
            try {
                //System.out.println(jtfAdresse.getText());
                livre.setSubTitle(jtfSoustitre.getText());
            } catch (DataExceptions ex) {
                JOptionPane.showMessageDialog(null, "Ecriture Sous-titre : " + ex.getErrorCode() + " / " + ex.getMessage(), "LivreMod", JOptionPane.ERROR_MESSAGE);
                error += 1;
            }
            
            try {
                //System.out.println(jtfAdresse.getText());
                livre.setSerie(jtfSerie.getText());
//                if(jtfSerie.getText().equals(livre.getBookVol().getBookvolname())){
//                int quant = livre.getBookVol().getBookvolquantity();
//                quant++;
//                livre.getBookVol().setBookvolname(jtfSerie.getText());
//                livre.getBookVol().setBookvolquantity(quant);
//                }
//                
//                else{
//                    vol.setBookvolname(jtfSerie.getText());
//                    livre.setBookVol(vol);}
//                    
                
            } catch (DataExceptions ex) {
                JOptionPane.showMessageDialog(null, "Ecriture Sous-titre : " + ex.getErrorCode() + " / " + ex.getMessage(), "LivreMod", JOptionPane.ERROR_MESSAGE);
                error += 1;
            }
       
       
            
            try {
                //System.out.println(jtaNotes.getText());
                livre.setPaging(jtfNombredepage.getText());
            } catch (DataExceptions ex) {
                JOptionPane.showMessageDialog(null, "Ecriture Nombres de pages : " + ex.getErrorCode() + " / " + ex.getMessage(), "LivreMod", JOptionPane.ERROR_MESSAGE);
                error += 1;
            }
            livre.setReleaseDate(jtfDatedeSortie.getText());
            
            try {
                //System.out.println(jtaNotes.getText());
                livre.setAvailableStock(jtfExemplairesdisponibles.getText());
            } catch (DataExceptions ex) {
                JOptionPane.showMessageDialog(null, "Ecriture Nombres de pages : " + ex.getErrorCode() + " / " + ex.getMessage(), "LivreMod", JOptionPane.ERROR_MESSAGE);
                error += 1;
            }
             
            try {
                //System.out.println(jtaNotes.getText());
                livre.setPrice(jtfPrix.getText());
            } catch (DataExceptions ex) {
                JOptionPane.showMessageDialog(null, "Ecriture Nombres de pages : " + ex.getErrorCode() + " / " + ex.getMessage(), "LivreMod", JOptionPane.ERROR_MESSAGE);
                error += 1;
            }
            
            try {
                //System.out.println(jtaNotes.getText());
                livre.setIsbn13(jtfISBN13.getText());
            } catch (DataExceptions ex) {
                JOptionPane.showMessageDialog(null, "Ecriture Nombres de pages : " + ex.getErrorCode() + " / " + ex.getMessage(), "LivreMod", JOptionPane.ERROR_MESSAGE);
                error += 1;
            }
            
            try {
                //System.out.println(jtaNotes.getText());
                livre.setIsbn10(jtfISBN10.getText());
            } catch (DataExceptions ex) {
                JOptionPane.showMessageDialog(null, "Ecriture Nombres de pages : " + ex.getErrorCode() + " / " + ex.getMessage(), "LivreMod", JOptionPane.ERROR_MESSAGE);
                error += 1;
            }
            
            

            Statut st = (Statut) jcbStatus.getSelectedItem();
            livre.setStatut(st);
            
            Auteur aut = (Auteur) jcbAuteur.getSelectedItem();
            livre.setAuthor(aut);
            
           EditeurCollection ec = new EditeurCollection();
           ec.setPublisher((Editeur)jcbEditeur.getSelectedItem());
           ec.setPublisherCollID(((Editeur)jcbEditeur.getSelectedItem()).getID());
           livre.setPublisherColl(ec);
           
           Grille grd = new Grille();
           grd.setEditeur((Editeur)jcbEditeur.getSelectedItem());
                try {
                    grd.setGridID("3");
                } catch (DataExceptions ex) {
                    Logger.getLogger(JFLivreAj.class.getName()).log(Level.SEVERE, null, ex);
                }
           livre.setGrid(grd);
          
            
            Theme th = (Theme) jcbTheme.getSelectedItem();
            livre.setTheme(th);
            
            Theme sth = (Theme) jcbSousthème.getSelectedItem();
            livre.setSoustheme(sth);
            
            Format fmrt = (Format) jcbFormat.getSelectedItem();
            livre.setFormat(fmrt);
          
          

//si pas d'erreur ( error = 0) je sauvegarde
            int n = 0;
            if (error == 0) {
                if (livre.getStatut().getId().equals("499")) {
                    n = JOptionPane.showConfirmDialog(rootPane, "ATTENTION vous allez supprimer un editeur.\nCette action est irrémediable\nEtes-vous sûr ?", "Suppression", JOptionPane.OK_CANCEL_OPTION);
                }
                if (n == 0) {
                    Xchange ech = new Xchange();
                    ech.connect();
//                    vol.save(ech, vol.genValue());
                    livre.save(ech, livre.genValue(ech));
                    
                    ech.close();

                }

            }
        }


//refresh de la table de la fenetre mère
        this.JFL.refreshLivre();
    }//GEN-LAST:event_jbEnregistrerActionPerformed

    private void jcbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbStatusActionPerformed

    private void jtfPrixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfPrixActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfPrixActionPerformed

    private void jcbAuteurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbAuteurActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbAuteurActionPerformed

    private void jcbEditeurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbEditeurActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbEditeurActionPerformed

    private void jtfISBN13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfISBN13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfISBN13ActionPerformed

    private void jcbSousthèmeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbSousthèmeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbSousthèmeActionPerformed

    private void jcbThemeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbThemeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbThemeActionPerformed

    private void jcbThemeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbThemeItemStateChanged
     Theme th = (Theme) jcbTheme.getSelectedItem();
     if(th != null){
         jcbSousthème.removeAllItems();
         jcbSousthème.setModel(initsousTheme(th.getID()));
     }
        
    }//GEN-LAST:event_jcbThemeItemStateChanged

    private void jcbFormatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbFormatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbFormatActionPerformed

    private void jtfTitreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfTitreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfTitreActionPerformed

    private void jtfISBN10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfISBN10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfISBN10ActionPerformed

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
            java.util.logging.Logger.getLogger(JFLivreAj.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFLivreAj.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFLivreAj.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFLivreAj.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFLivreAj().setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbEnregistrer;
    private javax.swing.JButton jbFermer;
    private javax.swing.JComboBox jcbAuteur;
    private javax.swing.JComboBox jcbEditeur;
    private javax.swing.JComboBox jcbFormat;
    private javax.swing.JComboBox jcbSousthème;
    private javax.swing.JComboBox jcbStatus;
    private javax.swing.JComboBox jcbTheme;
    private javax.swing.JLabel jlActif;
    private javax.swing.JLabel jlAuteur;
    private javax.swing.JLabel jlDatedesortie;
    private javax.swing.JLabel jlEditeur;
    private javax.swing.JLabel jlExemplairesdisponibles;
    private javax.swing.JLabel jlFormat;
    private javax.swing.JLabel jlISBN10;
    private javax.swing.JLabel jlISBN13;
    private javax.swing.JLabel jlNombresdepages;
    private javax.swing.JLabel jlNotes;
    private javax.swing.JLabel jlPrix;
    private javax.swing.JLabel jlSerie;
    private javax.swing.JLabel jlSousthème;
    private javax.swing.JLabel jlSoustitre;
    private javax.swing.JLabel jlSynopsis;
    private javax.swing.JLabel jlThème;
    private javax.swing.JLabel jlTitre;
    private javax.swing.JTextArea jtaNotes;
    private javax.swing.JTextArea jtaSynopsis;
    private javax.swing.JTextField jtfDatedeSortie;
    private javax.swing.JTextField jtfExemplairesdisponibles;
    private javax.swing.JTextField jtfISBN10;
    private javax.swing.JTextField jtfISBN13;
    private javax.swing.JTextField jtfNombredepage;
    private javax.swing.JTextField jtfPrix;
    private javax.swing.JTextField jtfSerie;
    private javax.swing.JTextField jtfSoustitre;
    private javax.swing.JTextField jtfTitre;
    // End of variables declaration//GEN-END:variables

    
}
