package de.robinkr.sql;

import de.robinkr.sql.exceptions.ColumnException;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;

public class SQLConnector {

    public Connection connection;

    public SQLConnector(String url, String usr, String pw) throws ConnectException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            DriverManager.setLoginTimeout(10);
            this.connection = DriverManager.getConnection(url, usr, pw);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (this.connection != null)
            System.out.println("connected!");
        else
            throw new ConnectException();
    }

    public SQLConnector(String ip, String port, String database, String usr, String pw) throws ConnectException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            DriverManager.setLoginTimeout(10);
            this.connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + database, usr, pw);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (this.connection != null)
            System.out.println("connected!");
        else
            throw new ConnectException();
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

    /**
     * Gets all accessable databases by running the <b>"SHOW DATABASES"</b> SQL-Command.
     *
     * @return {@link String String[]} all accessable databases by name
     * @throws ColumnException if Databases cannot be found
     */
    public String[] getAccessableDatabases() throws ColumnException {
        String[] arr;
        try {
            SQLResult rs = ask("SHOW DATABASES");

            HashMap<Integer, String[]> map = rs.getRows();
            arr = new String[map.size()];

            map.forEach((k, v) -> {
                arr[k] = v[0];
            });

        } catch (Exception e) {
            throw new ColumnException("Die Datenbankliste konnte nicht gefunden werden!");
        }

        return arr;
    }
    
    public String[] getAccessableTablesFromDatabase(String databaseName) throws SQLException {

        SQLResult rs = ask("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA='" + databaseName + "'");
        String[] accessableTables = new String[rs.getRows().size()];
        rs.getRows().forEach((rowNumber, row) -> {
            accessableTables[rowNumber] = row[0];
        });

        return accessableTables;
    }

    public String getUserName() {
        try {
            return this.connection.getMetaData().getUserName();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            this.connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
