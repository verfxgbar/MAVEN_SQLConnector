package de.robinkr.sql;


import de.robinkr.sql.exceptions.ColumnFormatException;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class SQLResult {

    private ResultSet rs;
    private HashMap<String, ArrayList<String>> columns = new HashMap<>();
    private HashMap<Integer, ArrayList<String>> rows = new HashMap<>();

    public SQLResult(ResultSet rs) throws SQLException {
        this.rs = rs;
        ResultSetMetaData rsmd = rs.getMetaData();
        for (int i = 0; i < rsmd.getColumnCount(); i++) {
            this.columns.computeIfAbsent(rsmd.getColumnLabel(i + 1), k -> new ArrayList<>());
        }
        while (rs.next()) {
            this.rows.computeIfAbsent(rs.getRow(), k -> new ArrayList<>());
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                this.rows.get(rs.getRow()).add(rs.getString(i + 1));
            }
        }
        getColumns();
    }


    /**
     * <p>Internal Method used for correct format of Method.</p>
     *
     * <b>Throws {@link de.robinkr.sql.exceptions.ColumnFormatException} if SQL-Table is setup wrong</b>
     */
    private void getColumns() {
        this.rows.keySet().forEach(rowNumer -> {
            ArrayList<String> row = this.rows.get(rowNumer);
            for (int i = 0; i < columns.keySet().size(); i++) {
                if (columns.keySet().toArray()[i] == null || row.get(i) == null)
                    ColumnFormatException.own();

                String columnName = (String) columns.keySet().toArray()[i];
                columns.get(columnName).add(row.get(i));
            }
        });
    }

    /**
     * Logic for proper Usage of Class
     *
     * @return The Table as a 2D-Array of String
     */
    public String[] getColumnNames() {
        String[] toBeReturned = new String[this.columns.keySet().size()];
        for (int i = 0; i < this.columns.keySet().size(); i++) {
            toBeReturned[i] = this.columns.keySet().toArray()[i].toString();
        }
        return toBeReturned;
    }

    /**
     * Gets collected Rows from
     *
     * @return The Table as a 2D-Array of String
     */
    public String[] getRows() {
        String[] toBeReturned = new String[this.rows.values().stream().findFirst().get().size()];
        for (int i = 0; i < this.rows.values().stream().findFirst().get().size(); i++) {
            String concat = "";
            this.rows.forEach((k, v) -> {
                v.forEach(concat::concat);
            });
            toBeReturned[i] = rows.values().toArray()[i].toString();
        }
        return toBeReturned;
    }

    public void close() throws SQLException {
        this.rs.close();
    }
}
