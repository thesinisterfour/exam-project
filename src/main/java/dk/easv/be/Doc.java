package dk.easv.be;

import java.time.LocalDate;

public class Doc {

    private int id;
    private String name;
    private LocalDate creationDate;
    private LocalDate lastView;
    private String description;

    public Doc(int id, String name, LocalDate creationDate, String description) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.description = description;
    }

    public Doc(int id, String name, LocalDate creationDate, LocalDate lastView, String description) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.lastView = lastView;
        this.description = description;
    }

    public Doc(String name, LocalDate creationDate) {
        this.name = name;
        this.creationDate = creationDate;
    }

    public Doc(String name, LocalDate creationDate, String description) {
        this.name = name;
        this.creationDate = creationDate;
        this.description = description;
    }

    public Doc(String name) {
        this.name = name;
    }

    public Doc(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getLastView() {
        return lastView;
    }

    public void setLastView(LocalDate lastView) {
        this.lastView = lastView;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Doc{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creationDate=" + creationDate +
                ", lastView=" + lastView +
                ", description='" + description + '\'' +
                '}';
    }
}
