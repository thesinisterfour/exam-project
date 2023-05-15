package dk.easv.be;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CardTest {

    @Test
    public void testGettersAndSetters() {
        Card card = new Card("John Doe", "john.doe@example.com", "user");
        card.setUserName("Jane Doe");
        card.setUserRole("admin");
        card.setCustomerName("Acme Corp");
        card.setCustomerAddress("123 Main St");
        card.setCustomerZipCode("90210");
        card.setCustomerEmail("jane.doe@example.com");

        Assertions.assertEquals("Jane Doe", card.getUserName());
        Assertions.assertEquals("admin", card.getUserRole());
        Assertions.assertEquals("Acme Corp", card.getCustomerName());
        Assertions.assertEquals("123 Main St", card.getCustomerAddress());
        Assertions.assertEquals("90210", card.getCustomerZipCode());
        Assertions.assertEquals("jane.doe@example.com", card.getCustomerEmail());
    }

    @Test
    public void testConstructorWithUser() {
        Card card = new Card("John Doe", "john.doe@example.com", "user");

        Assertions.assertEquals("John Doe", card.getUserName());
        Assertions.assertEquals("john.doe@example.com", card.getCustomerEmail());
        Assertions.assertEquals("user", card.getUserRole());
    }

    @Test
    public void testConstructorWithCustomer() {
        Card card = new Card("Acme Corp", "123 Main St", 90210, "jane.doe@example.com");

        Assertions.assertEquals("Acme Corp", card.getCustomerName());
        Assertions.assertEquals("123 Main St", card.getCustomerAddress());
        Assertions.assertEquals("90210", card.getCustomerZipCode());
        Assertions.assertEquals("jane.doe@example.com", card.getCustomerEmail());
    }
}