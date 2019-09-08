package org.aoichaan0513.a_core.API;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class IConfig {

    private final String fileName;
    private final File file;
    private final FileConfiguration config;

    public IConfig(String fileName, File file, FileConfiguration config) {
        this.fileName = fileName;
        this.file = file;
        this.config = config;
    }

    public String getFileName() {
        return fileName;
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
