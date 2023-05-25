package dk.easv.helpers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CustomerTypeTest {

    @Test
    public void testEnumValues() {
        Assertions.assertEquals(2, CustomerType.values().length);
        Assertions.assertSame(CustomerType.PUBLIC, CustomerType.valueOf("PUBLIC"));
        Assertions.assertSame(CustomerType.PRIVATE, CustomerType.valueOf("PRIVATE"));
    }
}