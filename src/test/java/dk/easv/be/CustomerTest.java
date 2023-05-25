package dk.easv.be;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerTest {

    @Test
    void testGettersAndSetters() {
        Customer customer = new Customer(1, "John Doe", "john.doe@example.com", "123 Main St");
        assertEquals(1, customer.getCustomerID());
        assertEquals("John Doe", customer.getCustomerName());
        assertEquals("john.doe@example.com", customer.getCustomerEmail());
        assertEquals("123 Main St", customer.getCustomerAddress());
        assertEquals(0, customer.getZipCode());

        customer.setCustomerID(2);
        customer.setCustomerName("Jane Smith");
        customer.setCustomerEmail("jane.smith@example.com");
        customer.setCustomerAddress("456 High St");
        customer.setZipCode(1000);

        assertEquals(2, customer.getCustomerID());
        assertEquals("Jane Smith", customer.getCustomerName());
        assertEquals("jane.smith@example.com", customer.getCustomerEmail());
        assertEquals("456 High St", customer.getCustomerAddress());
        assertEquals(1000, customer.getZipCode());
    }

    @Test
    void testConstructorWithCity() {
        City city = new City(6700, "Esbjerg");
        Customer customer = new Customer(1, "John Doe", "john.doe@example.com", "123 Main St", city);
        assertEquals(6700, customer.getZipCode());
    }

    @Test
    void testConstructorWithZipCode() {
        Customer customer = new Customer("Jane Smith", "jane.smith@example.com", "456 High St", 1000);
        assertEquals("Jane Smith", customer.getCustomerName());
        assertEquals("jane.smith@example.com", customer.getCustomerEmail());
        assertEquals("456 High St", customer.getCustomerAddress());
        assertEquals(1000, customer.getZipCode());
    }

    @Test
    void testConstructorWithIdAndZipCode() {
        Customer customer = new Customer(1, "Jane Smith", "jane.smith@example.com", "456 High St", 1000);
        assertEquals(1, customer.getCustomerID());
        assertEquals("Jane Smith", customer.getCustomerName());
        assertEquals("jane.smith@example.com", customer.getCustomerEmail());
        assertEquals("456 High St", customer.getCustomerAddress());
        assertEquals(1000, customer.getZipCode());
    }

    @Test
    void testToString() {
        Customer customer = new Customer(1, "John Doe", "john.doe@example.com", "123 Main St");
        assertEquals("ID = 1 Name = John Doe", customer.toString());
    }



}
