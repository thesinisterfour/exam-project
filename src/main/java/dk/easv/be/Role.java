package dk.easv.be;

public enum Role {
    SALESPERSON(1),
    ADMIN(2),
    TECHNICIAN(3),
    PROJECTMANAGER(4);

    private final int id;

    Role(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
