package com.cafe.utils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Resource {
    public static String getAbsolutePath(String relativePath) {
        URL resource = Resource.class.getResource("/" + relativePath);
        if (resource == null) {
            System.out.println("File not found: " + relativePath);
            return null;
        }
        String absolutePath = resource.getPath();
        if (absolutePath.startsWith("file:/")) {
            return absolutePath.substring("file:/".length());
        }
        return resource.getPath().substring(1);
    }

    public static String getPathOutsideJAR() {
        String jarPath = Resource.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        return new File(jarPath).getParent();
    }

    public static String getPathOutsideJAR(String relativePath) {
        return getPathOutsideJAR() + File.separator + relativePath;
    }

    public static String getResourcePath(String relativePath, boolean insideJAR) {
        String resourcePath;
        if (insideJAR)
            resourcePath = getResourcePathInsideJAR();
        else
            resourcePath = getResourcePathOutsideJAR();
        return resourcePath + File.separator + relativePath;
    }

    public static String getResourcePath(boolean insideJAR) {
        return getResourcePath("", insideJAR);
    }

    public static String getResourcePathInsideJAR() {
        URL resourceUrl = Resource.class.getResource("/");
        if (resourceUrl == null) {
            System.out.println("Resource path not found");
            return null;
        }
        return resourceUrl.getPath();
    }

    public static String getResourcePathInsideJAR(String relativePath) {
        return getResourcePathInsideJAR() + File.separator + relativePath;
    }

    public static String getResourcePathOutsideJAR() {
        return getPathOutsideJAR() + File.separator + "resources";
    }

    public static String getResourcePathOutsideJAR(String relativePath) {
        return getResourcePathOutsideJAR() + File.separator + relativePath;
    }

    public static ImageIcon loadImageIcon(String relativePath, boolean insideJAR) {
        String path = getResourcePath(relativePath, insideJAR);
        File file = new File(path);
        if (file.exists()) {
            return new ImageIcon(path);
        }
        return null;
    }

    public static ImageIcon loadImageIcon(String relativePath) {
        return loadImageIcon(relativePath, true);
    }

    public static InputStream loadInputStream(String relativePath, boolean insideJAR) {
        File file = new File(getResourcePath(insideJAR), relativePath);
        if (file.exists()) {
            try {
                return Files.newInputStream(file.toPath());
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }
        return null;
    }

    public static InputStream loadInputStream(String relativePath) {
        return loadInputStream(relativePath, true);
    }

    public static Properties loadProperties(String relativePath, boolean insideJAR) {
        InputStream inputStream = loadInputStream(relativePath, insideJAR);
        Properties properties = new Properties();
        try {
            if (inputStream != null) {
                properties.load(inputStream);
                inputStream.close();
            }
            return properties;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Properties loadProperties(String relativePath) {
        return loadProperties(relativePath, false);
    }

    public static void copyResource(String sourceRelativePath, String destinationRelativePath) {
        try {
            Path database = Path.of(Resource.getResourcePath(destinationRelativePath, false));
            if (Files.notExists(database)) {
                InputStream inputStream = Resource.loadInputStream(sourceRelativePath, true);
                if (inputStream != null) {
                    Files.copy(inputStream, database);
                    inputStream.close();
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
