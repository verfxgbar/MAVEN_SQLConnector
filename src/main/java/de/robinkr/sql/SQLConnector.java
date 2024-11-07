package de.robinkr.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLConnector {

    public Connection connection;

    public SQLConnector(String url, String usr, String pw) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            DriverManager.setLoginTimeout(10);
            this.connection = DriverManager.getConnection(url, usr, pw);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (this.connection != null)
            System.out.println("connected!");
    }

    public SQLResult t(String s) throws SQLException {
        Statement st = this.connection.createStatement();
        ResultSet rs = st.executeQuery(s);
        /*while(rs.next()) {
            for(int i = 1; i <= meta.getColumnCount(); i++) {
                System.out.println(rs.getString(i));
            }
        }*/
        return new SQLResult(rs);
    }

    public void close() {
        try {
            this.connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
