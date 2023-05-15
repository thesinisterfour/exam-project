package dk.easv.helpers;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConfigTest {

    @Test
    void getUrl() {
        Path expected = Path.of("src/main/resources/dk/easv/config.cfg");
        assertEquals(expected, Config.CONFIG_PATH.getUrl());
    }
}