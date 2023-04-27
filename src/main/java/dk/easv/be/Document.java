package dk.easv.be;

import java.util.Date;

public class Document {

    private int documentID;
    private String documentName;
    private String documentURL;
    private Date creationDate;
    private Date lastView;
    private String documentDescription;

    public Document(int documentID, String documentName, String documentURL, Date creationDate, String documentDescription){
        this.documentID = documentID;
        this.documentName = documentName;
        this.documentURL = documentURL;
        this.creationDate = creationDate;
        this.documentDescription = documentDescription;
    }

    public Document(int documentID, String documentName, String documentURL, Date creationDate, Date lastView, String documentDescription){
        this.documentID = documentID;
        this.documentName = documentName;
        this.documentURL = documentURL;
        this.creationDate = creationDate;
        this.lastView = lastView;
        this.documentDescription = documentDescription;
    }

    public int getDocumentID(){
        return documentID;
    }

    public String getDocumentName(){
        return documentName;
    }

    public String getDocumentURL(){
        return documentURL;
    }

    public Date getCreationDate(){
        return creationDate;
    }

    public Date getLastView() {
        return lastView;
    }

    public String getDocumentDescription() {
        return documentDescription;
    }

    public void setDocumentID(int documentID) {
        this.documentID = documentID;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public void setDocumentURL(String documentURL) {
        this.documentURL = documentURL;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setLastView(Date lastView) {
        this.lastView = lastView;
    }

    public void setDocumentDescription(String documentDescription) {
        this.documentDescription = documentDescription;
    }
}
