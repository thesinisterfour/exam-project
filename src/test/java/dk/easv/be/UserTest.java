package dk.easv.be;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserTest {

    @Test
    public void testConstructorWithAllParameters(){
        User user = new User(1, "John", "Doe", Role.ADMIN, "johndoe", "password");
        assertEquals(1, user.getUserID());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals(Role.ADMIN, user.getRole());
        assertEquals("johndoe", user.getUsername());
        assertEquals("password", user.getPassword());
    }

    @Test
    public void testConstructorWithThreeParameters(){
        User user = new User("John", "Doe", Role.ADMIN);
        assertEquals(0, user.getUserID());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals(Role.ADMIN, user.getRole());
        assertNull(user.getUsername());
        assertNull(user.getPassword());
    }

    @Test
    public void testSetUserID(){
        User user = new User(1, "John", "Doe", Role.ADMIN, "johndoe", "password");
        user.setUserID(2);
        assertEquals(2, user.getUserID());
    }

    @Test
    public void testSetFirstName(){
        User user = new User(1, "John", "Doe", Role.ADMIN, "johndoe", "password");
        user.setFirstName("Jane");
        assertEquals("Jane", user.getFirstName());
    }

    @Test
    public void testSetLastName(){
        User user = new User(1, "John", "Doe", Role.ADMIN, "johndoe", "password");
        user.setLastName("Doe2");
        assertEquals("Doe2", user.getLastName());
    }

    @Test
    public void testSetRole(){
        User user = new User(1, "John", "Doe", Role.ADMIN, "johndoe", "password");
        user.setRole(Role.SALESPERSON);
        assertEquals(Role.SALESPERSON, user.getRole());
    }

    @Test
    public void testSetUsername(){
        User user = new User(1, "John", "Doe", Role.ADMIN, "johndoe", "password");
        user.setUsername("janedoe");
        assertEquals("janedoe", user.getUsername());
    }

    @Test
    public void testSetPassword(){
        User user = new User(1, "John", "Doe", Role.ADMIN, "johndoe", "password");
        user.setPassword("newPassword");
        assertEquals("newPassword", user.getPassword());
    }
}
