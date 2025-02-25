package com.bms.config;

import java.io.IOException;
import java.util.Properties;

public class EmailConfig {
    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(EmailConfig.class.getClassLoader().getResourceAsStream("email.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load email configuration", e);
        }
    }

    public static String getSmtpHost() {
        return properties.getProperty("SMTP_HOST");
    }

    public static String getSmtpPort() {
        return properties.getProperty("SMTP_PORT");
    }

    public static String getUsername() {
        return properties.getProperty("USERNAME");
    }

    public static String getPassword() {
        return properties.getProperty("PASSWORD");
    }
}
