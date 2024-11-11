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

    public SQLConnector(String ip, String port, String database, String usr, String pw) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            DriverManager.setLoginTimeout(10);
            this.connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + database, usr, pw);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (this.connection != null)
            System.out.println("connected!");
    }

    /**
     * Method used for getting SQL-Statements from the connected Database
     *
     * @param statement The SQL Statement (unmodified)
     * @return an {@link SQLResult} for further use-cases
     * @throws SQLException
     */
    public SQLResult ask(String statement) throws SQLException {
        Statement st = this.connection.createStatement();
        ResultSet rs = st.executeQuery(statement);
        /*while(rs.next()) {
            for(int i = 1; i <= meta.getColumnCount(); i++) {
                System.out.println(rs.getString(i));
            }
        }*/
        return new SQLResult(rs);
    }

    /**
     * <p>Closes the main {@link Connection} from the Database</p>
     * <p>Automatically catches the {@link SQLException} for potential exceptions</p>
     */

    public void update(String sql) throws SQLException {
        Statement st = this.connection.createStatement();
        st.executeUpdate(sql);
    }
    public void close() {
        try {
            this.connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
