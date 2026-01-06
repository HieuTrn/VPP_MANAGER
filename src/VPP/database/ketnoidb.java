package VPP.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class ketnoidb {

    public static Connection getConnection() {
        try {
            String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;"
                       + "databaseName=MainDB;"
                       + "integratedSecurity=true;"
                       + "trustServerCertificate=true;";
            return DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}