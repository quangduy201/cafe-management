package com.cafe.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Settings {
    public static final String CONFIG_FILE = "settings/config.properties";
    public static final String DATABASE_FILE = "settings/db.properties";
    public static final String MAIL_FILE = "settings/mail.properties";

    public static void initialize() {
        try {
            Files.createDirectories(Paths.get(Resource.getResourcePath("settings", false)));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        Resource.copyResource(DATABASE_FILE, DATABASE_FILE);
        Resource.copyResource(MAIL_FILE, MAIL_FILE);
        Resource.copyResource(CONFIG_FILE, CONFIG_FILE);
        Theme.applyTheme("light");
    }
}
