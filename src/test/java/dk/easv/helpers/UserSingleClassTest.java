package dk.easv.helpers;

import dk.easv.be.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserSingleClassTest {

    @Test
    public void testGetInstance() {
        UserSingleClass instance1 = UserSingleClass.getInstance();
        UserSingleClass instance2 = UserSingleClass.getInstance();

        Assertions.assertSame(instance1, instance2);
    }

    @Test
    public void testGettersAndSetters() {
        UserSingleClass user = UserSingleClass.getInstance();

        user.setId(1);
        Assertions.assertEquals(1, user.getId());

        user.setName("John");
        Assertions.assertEquals("John", user.getName());

        Role role = Role.ADMIN;
        user.setRole(role);
        Assertions.assertSame(role, user.getRole());
    }
}
