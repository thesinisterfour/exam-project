package dk.easv.be;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CityTest {

    @Test
    public void testGettersAndSetters() {
        City city = new City(90210, "Beverly Hills");
        city.setZipcode(90211);
        city.setCityName("Los Angeles");

        Assertions.assertEquals(90211, city.getZipcode());
        Assertions.assertEquals("Los Angeles", city.getCityName());
    }

    @Test
    public void testConstructor() {
        City city = new City(90210, "Beverly Hills");

        Assertions.assertEquals(90210, city.getZipcode());
        Assertions.assertEquals("Beverly Hills", city.getCityName());
    }
}
