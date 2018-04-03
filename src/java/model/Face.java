package model;

import java.io.Serializable;
import java.text.NumberFormat;

/**
 *
 * @author Marshall Ehlinger
 */
public class Face implements Serializable {

    private int faceId;
    private String name;
    private int rating;
    private String eyebrowRow;
    private String eyeRow;
    private String mouthRow;
    private String quote;

    public Face() {
    }

    public Face(int faceId, String name, int rating, String eyebrowRow, String eyeRow, String mouthRow, String quote) {
        this.faceId = faceId;
        this.name = name;
        this.rating = rating;
        this.eyebrowRow = eyebrowRow;
        this.eyeRow = eyeRow;
        this.mouthRow = mouthRow;
        this.quote = quote;
    }

    public int getFaceId() {
        return faceId;
    }

    public void setFaceId(int faceId) {
        this.faceId = faceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getEyebrowRow() {
        return eyebrowRow;
    }

    public void setEyebrowRow(String eyebrowRow) {
        this.eyebrowRow = eyebrowRow;
    }

    public String getEyeRow() {
        return eyeRow;
    }

    public void setEyeRow(String eyeRow) {
        this.eyeRow = eyeRow;
    }

    public String getMouthRow() {
        return mouthRow;
    }

    public void setMouthRow(String mouthRow) {
        this.mouthRow = mouthRow;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    @Override
    public String toString() {
        return "User{" + "userId=" + faceId + ", email=" + name + ", bloodSugar=" + 
                rating + ", date=" + eyebrowRow + ", event=" + eyeRow + ", health=" + mouthRow + ", notes=" + quote + '}';
    }
    
    
    
    public String inHTMLRowFormat() {
        
        return "<tr><td>" + faceId + "</td>"
                + "<td>" + name + "</td>"
                + "<td>" + rating + " pts</td>"
                + "<td>" + 
                "<pre> _____ <br/>" +
                "/     \\<br/>" +
                "|" + eyebrowRow + "|<br/>" +
                "|" + eyeRow + "|<br/>" +
                "|" + mouthRow + "|<br/>" +
                "\\     /<br/>" +
                " ~~~~~ </pre>" 
                + "</td>"
                + "<td>" + quote + "</td></tr>\n";
                
    }

}
