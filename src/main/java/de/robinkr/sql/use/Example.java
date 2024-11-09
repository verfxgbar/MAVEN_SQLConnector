package de.robinkr.sql.use;


import de.robinkr.sql.SQLConnector;
import de.robinkr.sql.SQLResult;

import java.sql.SQLException;
import java.util.Arrays;

public class Example {

    public static void main(String[] args) {

        String your_ip = "db_ip", dbName = "your_db_name", dbUserName = "your_login_name", dbPassword = "your_login_password";
        int db_port = 3306;
        String finalConnectionString = "jdbc:mysql://" + your_ip + ":" + db_port + "/" + dbName;

        SQLConnector con = new SQLConnector(finalConnectionString, dbUserName, dbPassword);

        try {
            SQLResult sq = con.execute("your sql code");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
