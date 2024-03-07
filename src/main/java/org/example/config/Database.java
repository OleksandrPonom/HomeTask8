package org.example.config;


import org.example.utils.Utils;
import org.flywaydb.core.Flyway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database{

    private static final Database INSTANCE = new Database();
    private Connection connection;

    private Database(){
        try {
            String dbUrl = new Utils().getString(Utils.DB_URL);
            String dbUser = new Utils().getString(Utils.DB_USER);
            String dbPass = new Utils().getString(Utils.DB_PASS);
            this.connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            flywayMigration(dbUrl, dbUser, dbPass);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Database getInstance(){
        return INSTANCE;
    }


    public int executeUpdate(String sql){
        try(Statement st = connection.createStatement()){
           return st.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
		}
    }

    public Connection getConnection(){
        return connection;
    }

    public void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void flywayMigration(String dbUrl, String dbUser, String dbPass){
        Flyway flyway = Flyway.configure().dataSource(dbUrl, dbUser, dbPass).load();
        flyway.migrate();
    }
}