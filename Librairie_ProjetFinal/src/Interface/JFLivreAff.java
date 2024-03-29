package Interface;


import ObjetsSimples.Auteur;
import ObjetsSimples.Editeur;
import ObjetsSimples.EditeurCollection;
import ObjetsSimples.Livre;
import ObjetsSimples.Statut;
import com.sun.corba.se.impl.resolver.SplitLocalResolverImpl;
import exceptions.DataExceptions;
import java.awt.Point;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import xchange.Xchange;

/**
 *
 * @author cda201
 */
public class JFLivreAff extends javax.swing.JFrame {

    private Livre livre;
    private HomePage JFL;
    private String button;

    /**
     * Creates new form JFEditeurMod
     */
    public JFLivreAff() {
        initComponents();
        this.livre = new Livre();
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

 
    
  public static String concat(String s1, String space, String s2){
	return s1 + space + s2;
}

    public void textFill() {
        jtfStatut.setText(livre.getStatut().getStatusname());
        jtfTitre.setText(livre.getTitle());
        jtfSoustitre.setText(livre.getSubTitle());
        jtfSerie.setText(livre.getSerie());
        jtfAuteur.setText(concat(livre.getAuthor().getFirstName()," ",livre.getAuthor().getLastName()));
        jtfEditeur.setText(livre.getPublisherColl().getPublisher().getName());
        jtfCollection.setText(livre.getPublisherColl().getPublisherCollName());
        jtfNombredepage.setText(livre.getPaging());
        jtfFormat.setText(livre.getFormat().getFormatname());
        jtfThème.setText(livre.getTheme().getName());
        jtfSousthème.setText(livre.getSoustheme().getName());
        jtfDatedeSortie.setText(livre.getReleaseDate());
        jtfExemplairesdisponibles.setText(livre.getAvailableStock());
        jtfPrix.setText(livre.getPrice());
     
        
        
        if(jtfSoustitre.getText().isEmpty()){
            jlSoustitre.setVisible(false);
            jtfSoustitre.setVisible(false);
        }
        else {jlSoustitre.setVisible(true);
            jtfSoustitre.setVisible(true);}
        
        if(jtfSerie.getText().isEmpty()){
            jlSerie.setVisible(false);
            jtfSerie.setVisible(false);
        }
        else {jlSerie.setVisible(true);
            jtfSerie.setVisible(true);}
    
        
        jtaSynopsis.append(livre.getSynopsis());
        jtaSynopsis.setWrapStyleWord(true);
        jtaSynopsis.setLineWrap(true);

       

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
        jtfTitre = new javax.swing.JTextField();
        jlSoustitre = new javax.swing.JLabel();
        jlAuteur = new javax.swing.JLabel();
        jtfAuteur = new javax.swing.JTextField();
        jlEditeur = new javax.swing.JLabel();
        jtfEditeur = new javax.swing.JTextField();
        jbFermer = new javax.swing.JButton();
        jtfFormat = new javax.swing.JTextField();
        jtfNombredepage = new javax.swing.JTextField();
        jtfDatedeSortie = new javax.swing.JTextField();
        jtfExemplairesdisponibles = new javax.swing.JTextField();
        jtfPrix = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtaSynopsis = new javax.swing.JTextArea();
        jlCollection = new javax.swing.JLabel();
        jlNombresdepages = new javax.swing.JLabel();
        jlDatedesortie = new javax.swing.JLabel();
        jlExemplairesdisponibles = new javax.swing.JLabel();
        jlPrix = new javax.swing.JLabel();
        jlSynopsis = new javax.swing.JLabel();
        jtfStatut = new javax.swing.JTextField();
        jtfSerie = new javax.swing.JTextField();
        jlSerie = new javax.swing.JLabel();
        jtfSousthème = new javax.swing.JTextField();
        jlThème = new javax.swing.JLabel();
        jtfThème = new javax.swing.JTextField();
        jlSousthème = new javax.swing.JLabel();
        jtfCollection = new javax.swing.JTextField();
        jlFormat = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtfSoustitre = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jlActif.setText("Statut");

        jlTitre.setText("Titre");

        jtfTitre.setEditable(false);
        jtfTitre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfTitreActionPerformed(evt);
            }
        });

        jlSoustitre.setText("Sous-titre");

        jlAuteur.setText(" Auteur");

        jtfAuteur.setEditable(false);
        jtfAuteur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfAuteurActionPerformed(evt);
            }
        });

        jlEditeur.setText("Editeur");

        jtfEditeur.setEditable(false);
        jtfEditeur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfEditeurActionPerformed(evt);
            }
        });

        jbFermer.setText("Fermer");
        jbFermer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbFermerActionPerformed(evt);
            }
        });

        jtfFormat.setEditable(false);
        jtfFormat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfFormatActionPerformed(evt);
            }
        });

        jtfNombredepage.setEditable(false);
        jtfNombredepage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfNombredepageActionPerformed(evt);
            }
        });

        jtfDatedeSortie.setEditable(false);

        jtfExemplairesdisponibles.setEditable(false);

        jtfPrix.setEditable(false);
        jtfPrix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfPrixActionPerformed(evt);
            }
        });

        jScrollPane2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane2.setVerifyInputWhenFocusTarget(false);

        jtaSynopsis.setEditable(false);
        jtaSynopsis.setColumns(20);
        jtaSynopsis.setRows(5);
        jtaSynopsis.setCaretPosition(0);
        jScrollPane2.setViewportView(jtaSynopsis);

        jlCollection.setText("Collection");

        jlNombresdepages.setText("Nombres de page");

        jlDatedesortie.setText("Date de Sortie");

        jlExemplairesdisponibles.setText("Exemplaires disponibles");

        jlPrix.setText("Prix");

        jlSynopsis.setText("Synopsis");

        jtfStatut.setEditable(false);
        jtfStatut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfStatutActionPerformed(evt);
            }
        });

        jtfSerie.setEditable(false);
        jtfSerie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfSerieActionPerformed(evt);
            }
        });

        jlSerie.setText("Serie");

        jtfSousthème.setEditable(false);
        jtfSousthème.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfSousthèmeActionPerformed(evt);
            }
        });

        jlThème.setText("Thème");

        jtfThème.setEditable(false);
        jtfThème.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfThèmeActionPerformed(evt);
            }
        });

        jlSousthème.setText("Sous-thème");

        jtfCollection.setEditable(false);
        jtfCollection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfCollectionActionPerformed(evt);
            }
        });

        jlFormat.setText("Format");

        jScrollPane1.setBorder(null);

        jtfSoustitre.setEditable(false);
        jScrollPane1.setViewportView(jtfSoustitre);
        jtfSoustitre.getAccessibleContext().setAccessibleParent(jScrollPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlCollection)
                            .addComponent(jlEditeur)
                            .addComponent(jlSerie)
                            .addComponent(jlPrix)
                            .addComponent(jlAuteur)
                            .addComponent(jlSoustitre)
                            .addComponent(jlTitre)
                            .addComponent(jlFormat)
                            .addComponent(jlNombresdepages)
                            .addComponent(jlDatedesortie)
                            .addComponent(jlExemplairesdisponibles)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlSousthème)
                            .addComponent(jlThème)
                            .addComponent(jlActif))))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jtfSousthème, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfSerie, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                    .addComponent(jtfPrix, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                    .addComponent(jtfExemplairesdisponibles, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfFormat, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfEditeur, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfStatut)
                    .addComponent(jtfCollection, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfDatedeSortie, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                    .addComponent(jtfAuteur, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                    .addComponent(jtfNombredepage, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                    .addComponent(jtfTitre, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfThème))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlSynopsis))
                .addGap(43, 43, 43))
            .addGroup(layout.createSequentialGroup()
                .addGap(457, 457, 457)
                .addComponent(jbFermer)
                .addContainerGap(298, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfStatut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlActif))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlThème)
                    .addComponent(jtfThème, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlSynopsis))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtfSousthème, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlSousthème))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtfTitre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlTitre))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jlSoustitre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtfSerie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlSerie))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtfAuteur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlAuteur))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtfEditeur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlEditeur))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlCollection)
                            .addComponent(jtfCollection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtfFormat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlFormat))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 198, Short.MAX_VALUE)
                        .addComponent(jbFermer))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        setBounds(0, 0, 838, 841);
    }// </editor-fold>//GEN-END:initComponents

    private void jtfTitreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfTitreActionPerformed


    }//GEN-LAST:event_jtfTitreActionPerformed

    private void jbFermerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbFermerActionPerformed
        this.dispose();
    }//GEN-LAST:event_jbFermerActionPerformed

    private void jtfEditeurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfEditeurActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfEditeurActionPerformed

    private void jtfPrixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfPrixActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfPrixActionPerformed

    private void jtfAuteurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfAuteurActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfAuteurActionPerformed

    private void jtfStatutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfStatutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfStatutActionPerformed

    private void jtfFormatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfFormatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfFormatActionPerformed

    private void jtfSerieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfSerieActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfSerieActionPerformed

    private void jtfSousthèmeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfSousthèmeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfSousthèmeActionPerformed

    private void jtfThèmeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfThèmeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfThèmeActionPerformed

    private void jtfNombredepageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfNombredepageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfNombredepageActionPerformed

    private void jtfCollectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfCollectionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfCollectionActionPerformed

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
            java.util.logging.Logger.getLogger(JFLivreAff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFLivreAff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFLivreAff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFLivreAff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                new JFLivreAff().setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbFermer;
    private javax.swing.JLabel jlActif;
    private javax.swing.JLabel jlAuteur;
    private javax.swing.JLabel jlCollection;
    private javax.swing.JLabel jlDatedesortie;
    private javax.swing.JLabel jlEditeur;
    private javax.swing.JLabel jlExemplairesdisponibles;
    private javax.swing.JLabel jlFormat;
    private javax.swing.JLabel jlNombresdepages;
    private javax.swing.JLabel jlPrix;
    private javax.swing.JLabel jlSerie;
    private javax.swing.JLabel jlSousthème;
    private javax.swing.JLabel jlSoustitre;
    private javax.swing.JLabel jlSynopsis;
    private javax.swing.JLabel jlThème;
    private javax.swing.JLabel jlTitre;
    private javax.swing.JTextArea jtaSynopsis;
    private javax.swing.JTextField jtfAuteur;
    private javax.swing.JTextField jtfCollection;
    private javax.swing.JTextField jtfDatedeSortie;
    private javax.swing.JTextField jtfEditeur;
    private javax.swing.JTextField jtfExemplairesdisponibles;
    private javax.swing.JTextField jtfFormat;
    private javax.swing.JTextField jtfNombredepage;
    private javax.swing.JTextField jtfPrix;
    private javax.swing.JTextField jtfSerie;
    private javax.swing.JTextField jtfSousthème;
    private javax.swing.JTextField jtfSoustitre;
    private javax.swing.JTextField jtfStatut;
    private javax.swing.JTextField jtfThème;
    private javax.swing.JTextField jtfTitre;
    // End of variables declaration//GEN-END:variables
}
