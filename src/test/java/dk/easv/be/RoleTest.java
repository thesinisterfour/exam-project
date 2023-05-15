package dk.easv.be;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoleTest {

    @Test
    void getId() {
        assertEquals(1, Role.SALESPERSON.getId());
        assertEquals(2, Role.ADMIN.getId());
        assertEquals(3, Role.TECHNICIAN.getId());
        assertEquals(4, Role.PROJECTMANAGER.getId());
    }
}