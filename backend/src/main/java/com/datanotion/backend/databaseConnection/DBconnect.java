package com.datanotion.backend.databaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DBconnect {

    @Value("${DB_HOST}")
    private String host;
    @Value("${DB_PORT}")
    private String port;
    @Value("${DB_DATABASE}")
    private String database;
    @Value("${DB_USERNAME}")
    private String username;
    @Value("${DB_PASSWORD}")
    private String password;

    public Connection getDBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://" + host + ":" + port + "/" + database;
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
