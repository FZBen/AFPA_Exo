package ObjetsSimples;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import exceptions.DataExceptions;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;
import xchange.Xchange;

public class Auteur {

    private String ID;
    private Statut status;
    private String lastName;
    private String firstName;
    private String bio;
    private byte[] picture;
    private String notes;
    private int type = 2;
    private String genre = "auteur";

    public Auteur() {
        this.status = new Statut();
    }

    
 

    public String getID() {
        return ID;
    }

    public void setID(String ID) throws DataExceptions {
//        Pattern p = Pattern.compile("[0-9]*");
//        Matcher m = p.matcher(ID);
//        if (m.matches()) {
            this.ID = ID;
//        } else {
//            throw new DataExceptions(2001, "L'ID ne doit contenir que des chiffres et sans espace", "ID");
//        }
    }

    public Statut getStatus() {
        return status;
    }

    public void setStatus(Statut status) {
        this.status = status;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws DataExceptions {
        Pattern p = Pattern.compile("[- 'À-ȳ-a-zA-Z]{1,150}");
        Matcher m = p.matcher(lastName);
        if (m.matches()) {
            this.lastName = lastName;
        } else {
            throw new DataExceptions(2002, "Le nom ne doit avoir que des lettres", "Nom");
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws DataExceptions {
        Pattern p = Pattern.compile("[- 'À-ȳ-a-zA-Z]{1,150}");
        Matcher m = p.matcher(firstName);
        if (m.matches()) {
            this.firstName = firstName;
        } else {
            throw new DataExceptions(2003, "Le prénom ne doit avoir que des lettres", "Prénom");
        }
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) throws DataExceptions {
//        if (bio.length() > 1024) {
            this.bio = bio;
//        } else {
//            throw new DataExceptions(2004, "La biographie ne doit pas dépasser 1024 caractères", "Biographie");
//        }
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) throws DataExceptions {
//        if (notes.length() > 500) {
            this.notes = notes;
//        } else {
//            throw new DataExceptions(2004, "Les commentaires ne doivent pas dépasser 500 caractères", "Commentaires");
//        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return lastName;
    }

    public void save(Xchange echange, String value) {
        Statement stat = null;

        try {
            stat = echange.getConnexion().createStatement();
            String query = "";
            if (this.ID == null) {
                query = "INSERT INTO AUTEUR "
                        + "(STATUSID, AUTHORLASTNAME, AUTHORFIRSTNAME, AUTHORBIO,"
                        + " AUTHORPICTURE, AUTHORNOTES) VALUES ";
                query += value;

            } else {
                query = "UPDATE AUTHOR "
                        + value + " WHERE AUTHORID = " + this.ID;
            }

            System.out.println(query);
            stat.executeUpdate(query);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ecriture Table : " + ex.getErrorCode() + " / " + ex.getMessage(), "Auteur", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Fermeture statement : " + ex.getErrorCode() + " / " + ex.getMessage(), "Table Auteur", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public String genValue() {
        String str = "";
        if (this.ID == null) {
            str = "(";
            //str += ID + ", ";
            str += status.getId() + ", ";
            str += "'" + apostrophe(lastName) + "', ";
            str += "'" + firstName + "', ";
            str += "'" + apostrophe(bio) + "', ";
            str += "" + picture + ", ";
            str += "'" + apostrophe(notes) + "')";

        } else {
            str += "SET STATUSID = " + status.getId() + ", ";
            str += " AUTHORLASTNAME = '" + apostrophe(lastName) + "', ";
            str += " AUTHORFIRSTNAME = '" + firstName + "', ";
            str += " AUTHORBIO = '" + apostrophe(bio) + "', ";
            str += " AUTHORPICTURE = " + picture + ", ";
            str += " AUTHORNOTES = '" + apostrophe(notes) + "'";

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
    
    public Vector getVector(){
        Vector v = new Vector();
        v.add(this);
        v.add(firstName);
        v.add(status.getStatusname());
        return v;
        
    }
    
    

}
