package dk.easv;

import java.nio.file.Path;

public enum Helpers {
    CONFIG_PATH("src/main/resources/dk/easv/config.cfg");

    private final Path url;

    Helpers(String envUrl) {
        this.url = Path.of(envUrl);
    }

    public Path getUrl() {
        return url;
    }
}
