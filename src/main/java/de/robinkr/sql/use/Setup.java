package de.robinkr.sql.use;


import de.robinkr.sql.SQLConnector;
import de.robinkr.sql.SQLResult;

import java.sql.SQLException;

public class Setup {

    public static void main(String[] args) {

        String dbName = "db_name", dbUserName = "db_user", dbPassword = "your_password";
        String connectionString = "jdbc:mysql://ip:3306/your_database_name";

        SQLConnector con = new SQLConnector(connectionString, dbUserName, dbPassword);

        try {
            SQLResult sq = con.t("your sql-code");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
