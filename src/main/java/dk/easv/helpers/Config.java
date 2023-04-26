package dk.easv.helpers;

import java.nio.file.Path;

public enum Config {
    CONFIG_PATH("src/main/resources/dk/easv/config.cfg");

    private final Path url;

    Config(String envUrl) {
        this.url = Path.of(envUrl);
    }

    public Path getUrl() {
        return url;
    }
}
