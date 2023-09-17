package com.cafe.utils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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

    public static ImageIcon loadImageIconIn(String relativePath) {
        URL resource = Resource.class.getResource("/" + relativePath);
        if (resource == null) {
            System.out.println("Image not found: " + relativePath);
            return null;
        }
        return new ImageIcon(resource);
    }

    public static InputStream loadInputStream(String relativePath) {
        InputStream inputStream = Resource.class.getResourceAsStream("/" + relativePath);
        if (inputStream == null) {
            System.out.println("File not found: " + relativePath);
            return null;
        }
        return inputStream;
    }

    public static Properties loadProperties(String relativePath) throws IOException {
        InputStream inputStream = loadInputStream(relativePath);
        Properties properties = new Properties();
        properties.load(inputStream);
        if (inputStream != null)
            inputStream.close();
        return properties;
    }

    public static String getPathOutsideJAR(String relativePath) {
        String jarPath = Resource.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String jarFolder = new File(jarPath).getParent();
        return jarFolder + File.separator + relativePath;
    }

    public static String getResourceOutsideJAR(String relativePath) {
        return getPathOutsideJAR("resources" + File.separator + relativePath);
    }
}
