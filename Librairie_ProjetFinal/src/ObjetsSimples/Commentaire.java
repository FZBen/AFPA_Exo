/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjetsSimples;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;
import xchange.Xchange;

/**
 *
 * @author cda201
 */
public class Commentaire {

    private String ID;
    private Livre livre;
    private LigneCmd ligne;
    private Client client;
    private Statut statut;
    private Employe employe;
    private String notes;
    private String eval;
    private String date;
    private String yesCount;
    private String noCount;
    private String ip;
    private String comment;
    private String genre = "commentaire";
    private int type = 21;

    public Commentaire() {
        this.livre = new Livre();
        this.ligne = new LigneCmd();
        this.client = new Client();
        this.employe = new Employe();
        this.statut = new Statut();

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public LigneCmd getLigne() {
        return ligne;
    }

    public void setLigne(LigneCmd ligne) {
        this.ligne = ligne;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getEval() {
        return eval;
    }

    public void setEval(String eval) {
        this.eval = eval;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getYesCount() {
        return yesCount;
    }

    public void setYesCount(String yesCount) {
        this.yesCount = yesCount;
    }

    public String getNoCount() {
        return noCount;
    }

    public void setNoCount(String noCount) {
        this.noCount = noCount;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void save(Xchange echange, String value) {
        Statement stat = null;

        try {
            stat = echange.getConnexion().createStatement();
            String query = "";
            if (this.ID == null) {
                query = "INSERT INTO COMMENTAIRES "
                        + "(BOOKISBN13, LIG_LINEID, CUSTOMERID,STATUSID, EMPLOGINID, COMMENTNOTES, COMMENTEVAL, COMMENTDATE, COMMENTYESCOUNT, COMMENTNOCOUNT, COMMENTIPADDRESS, STATCOMMENTDATE, STATCOMMENTCOMMENT) VALUES ";
                query += value;

            } else {
                query = "UPDATE COMMENTAIRES "
                        + value + " WHERE COMMENTID = " + this.ID;
            }

            System.out.println(query);
            stat.executeUpdate(query);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ecriture Table : " + ex.getErrorCode() + " / " + ex.getMessage(), "Commentaire", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Fermeture statement : " + ex.getErrorCode() + " / " + ex.getMessage(), "Table Commentaire", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    /*
     La méthode genValue sert à générer une partie de la requête necessaire pour la sauvegarde
    
     2 options sont disponibles
     1- l'objet vient d'être crée et n'existe pas dans SQL , l'addrID est vide donc on crée un nouvel enregistrement
     2- l'objet est déjà dans SQL , l'addrID n'est pas vide donc on met à jour l'enregistrement
     */

    public String genValue() {
        String str = "";
        if (this.ID == null) {
            str = "('";
            //str += ID + ", ";

            str += livre.getIsbn13() + "', ";

            str += ligne.getID() + ", ";

            str += client.getId() + ", ";
            str += statut.getId() + ", ";
            str += employe.getLoginID() + ", ";
            str += "'" + apostrophe(notes) + "', ";
            str += "" + eval + ", ";
            if (date == null) {
                str += date + ", ";
            } else {
                str += "'" + date + "', ";
            }
            str += "" + yesCount + ", ";
            str += "" + noCount + ", ";
            str += "'" + ip + "', ";
            str += ",";
            str += "'" + apostrophe(comment) + "')";

        } else {
            str += "SET BOOKISBN13 = '" + livre.getIsbn13() + "', ";
            str += " LIG_LINEID = " + ligne.getID() + ", ";
            str += " CUSTOMERID = " + client.getId() + ", ";
            str += " STATUSID = " + statut.getId() + ", ";
            str += " EMPLOGINID = " + employe.getLoginID() + ", ";
            str += " COMMENTNOTES = '" + apostrophe(notes) + "', ";
            str += " COMMENTEVAL = " + eval + ", ";
            if (date != null) {
                str += " COMMENTDATE = '" + date + "', ";
            }
            str += " COMMENTYESCOUNT = " + yesCount + ", ";
            str += " COMMENTNOCOUNT = " + noCount + ", ";
            str += " COMMENTIPADDRESS = '" + ip + "',";
            str += " STATCOMMENTCOMMENT = '" + apostrophe(comment) + "'";

        }
        return str;
    }

    public String apostrophe(String apostrophe) {
        String str = "";
        if (apostrophe != null) {
            str = apostrophe.replace("'", "''");
        }

        return str;

    }

    public Vector getVector() {
        Vector v = new Vector();
        v.add(this);
        v.add(livre.getTitle());
        v.add(client.getCustomerfirstname());
        v.add(client.getCustomerlastname());

        //v.add(statut.getStatusname());
        return v;
    }

    @Override
    public String toString() {
        return eval;
    }

}
