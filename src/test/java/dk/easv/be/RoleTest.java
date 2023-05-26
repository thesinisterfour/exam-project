package dk.easv.be;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RoleTest {

    @Test
    public void testValues() {
        Role[] expected = {Role.SALESPERSON,
                Role.ADMIN,
                Role.TECHNICIAN,
                Role.PROJECTMANAGER};
        Role[] actual = Role.values();

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void testToString(){
        String expected = "SALESPERSON";
        String actual = Role.SALESPERSON.toString();
        Assertions.assertEquals(expected, actual);

        expected = "ADMIN";
        actual = Role.ADMIN.toString();
        Assertions.assertEquals(expected, actual);

        expected = "TECHNICIAN";
        actual = Role.TECHNICIAN.toString();
        Assertions.assertEquals(expected, actual);

        expected = "PROJECTMANAGER";
        actual = Role.PROJECTMANAGER.toString();
        Assertions.assertEquals(expected, actual);
    }
}