package com.bms.config;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {
    private static DatabaseConfig instance;
    private Connection connection;

    private DatabaseConfig() throws IOException,ClassNotFoundException {
    	Properties properties = new Properties();
		properties.load(DatabaseConfig.class.getClassLoader().getResourceAsStream("db.properties"));
		
		final String URL = properties.getProperty("URL");
		final String USERNAME = properties.getProperty("USERNAME");
		final String PASSWORD = properties.getProperty("PASSWORD");
		
		
		try {
		    Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
			System.out.println("Database connection established..");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
    }

    public static DatabaseConfig getInstance() {
        if (instance == null) {
            synchronized (DatabaseConfig.class) {
                if (instance == null) {
                    try {
						instance = new DatabaseConfig();
					} catch (IOException | ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
