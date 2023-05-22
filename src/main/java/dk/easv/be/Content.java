package dk.easv.be;

import javafx.scene.image.Image;

public class Content {
    private int id;
    private Image image;
    private String text;
    private int index;
    private int documentId;

    public Content(int documentId, int id, int index, String text) {
        this.documentId = documentId;
        this.id = id;
        this.text = text;
        this.index = index;
    }

    public Content(int documentId, int id, int index) {
        this.documentId = documentId;
        this.id = id;
        this.index = index;
    }

    public Content(int documentId, int index, String text) {
        this.documentId = documentId;
        this.text = text;
        this.index = index;
    }

    public Content(int documentId, int index, Image image) {
        this.documentId = documentId;
        this.image = image;
        this.index = index;
    }

    public Content(int id, Image image) {
        this.id = id;
        this.image = image;
    }

    public Content(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }
}
