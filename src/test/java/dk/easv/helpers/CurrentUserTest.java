package dk.easv.helpers;

import dk.easv.be.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CurrentUserTest {

    @Test
    public void testGetInstance() {
        CurrentUser instance1 = CurrentUser.getInstance();
        CurrentUser instance2 = CurrentUser.getInstance();

        Assertions.assertSame(instance1, instance2);
    }

    @Test
    public void testGettersAndSetters() {
        CurrentUser user = CurrentUser.getInstance();

        user.setId(1);
        Assertions.assertEquals(1, user.getId());

        user.setName("John");
        Assertions.assertEquals("John", user.getName());

        Role role = Role.ADMIN;
        user.setRole(role);
        Assertions.assertSame(role, user.getRole());
    }
}
