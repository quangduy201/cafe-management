package com.cafe.utils;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;

import javax.swing.*;
import java.io.*;
import java.util.Properties;

public class Settings {
    public static final String CONFIG_FILE = "config.properties";
    public static FlatLaf theme;

    public static void saveTheme() {
        try (OutputStream outputStream = new FileOutputStream(Resource.getPathOutsideJAR(CONFIG_FILE))) {
            Properties properties = new Properties();
            properties.setProperty("theme", theme.isDark() ? "dark" : "light");
            properties.store(outputStream, "Theme Information");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String loadTheme() {
        String themeName;
        try (InputStream inputStream = new FileInputStream(Resource.getPathOutsideJAR(CONFIG_FILE))) {
            Properties properties = new Properties();
            properties.load(inputStream);
            themeName = properties.getProperty("theme");
        } catch (IOException e) {
            themeName = "light";
        }
        return themeName;
    }

    public static void applyTheme() {
        try {
            UIManager.setLookAndFeel(theme);
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
    }

    public static void applyTheme(String themeName) {
        theme = getTheme(themeName);
        applyTheme();
    }

    public static FlatLaf getTheme(String themeName) {
        if (themeName.equals("light"))
            return new FlatIntelliJLaf();
        if (themeName.equals("dark"))
            return new FlatDarculaLaf();
        return null;
    }
}
