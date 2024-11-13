package de.robinkr.sql;


import de.robinkr.sql.exceptions.ColumnException;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class SQLResult {

    private SQLConnector connector;
    private String sql;
    private ResultSet rs;
    private HashMap<String, ArrayList<String>> columns = new HashMap<>();
    //private HashMap<Integer, ArrayList<String>> rows = new HashMap<>();
    private HashMap<Integer, String[]> rows = new HashMap<>();

    public SQLResult(String sql, SQLConnector connector, ResultSet rs) throws SQLException {
        this.rs = rs;
        this.connector = connector;
        this.sql = sql;
        ResultSetMetaData rsmd = rs.getMetaData();
        for (int i = 0; i < rsmd.getColumnCount(); i++) {
            this.columns.computeIfAbsent(rsmd.getColumnLabel(i + 1), k -> new ArrayList<>());
        }
        /*int rowCounter = 0;
        System.out.println(rs.getString(1));
        ArrayList<String> tempRow = new ArrayList<>();
        while (rs.next()) {
            tempRow = new ArrayList<>();
            tempRow.add(rs.getString(rowCounter + 1));
            System.out.println(rs.getString(rowCounter + 1));
            //this.rows.get(rs.getRow()).add(rs.getString(i + 1));
            rowCounter++;
        }*/
        ArrayList<ArrayList<String>> rowHolding = new ArrayList<>();
        while (rs.next()) {
            ArrayList<String> tempList = new ArrayList<>();
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                tempList.add(rs.getString(i + 1));
            }
            rowHolding.add(tempList);
        }
        ArrayList<String[]> arrList = doubleArrayListToArray(rowHolding);
        for (int i = 0; i < arrList.size(); i++) {
            this.rows.put(i, arrList.get(i));
        }

        System.out.println(this.rows.size());

        getColumns();
    }

    private ArrayList<String[]> doubleArrayListToArray(ArrayList<ArrayList<String>> list) {
        ArrayList<String[]> arrList = new ArrayList<>();
        for (ArrayList<String> row : list) {
            String[] arr = new String[row.size()];
            for (int i = 0; i < row.size(); i++) {
                arr[i] = row.get(i);
            }
            arrList.add(arr);
        }

        return arrList;
    }


    /**
     * <p>Internal Method used for correct format of Method.</p>
     *
     * <b>Throws {@link ColumnException} if SQL-Table is setup wrong</b>
     */
    private void getColumns() {
        this.rows.keySet().forEach(rowNumer -> {
            String[] row = this.rows.get(rowNumer);
            for (int i = 0; i < columns.keySet().size(); i++) {
                if (columns.keySet().toArray()[i] == null || row[i] == null)
                    ColumnException.own();

                String columnName = (String) columns.keySet().toArray()[i];
                columns.get(columnName).add(row[i]);
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


    @Deprecated
    /**
     * Gets collected Rows from
     *
     * @return a HashMap with the Row Number as Key and the Value as a String Array
     */
    public HashMap<Integer, String[]> getRowsOLD() throws SQLException {
        String[] defaultRowFormat = new String[this.rows.values().stream().findFirst().get().length];
        for (int i = 0; i < this.rows.values().stream().findFirst().get().length; i++) {
            defaultRowFormat[i] = rows.values().toArray()[i].toString();
        }
        HashMap<Integer, String[]> returned = new HashMap<>();

        int columnCount = this.rs.getMetaData().getColumnCount();
        String[] columns = new String[columnCount];
        for (int i = 0; i < columnCount; i++)
            columns[i] = this.rs.getMetaData().getColumnLabel(i + 1);
        for (int i = 0; i < defaultRowFormat.length; i++) {
            String row = defaultRowFormat[i];
            String formatted = row.substring(1, row.length() - 1);
            String[] formattedArr = new String[formatted.split(", ").length];
            for (int i1 = 0; i1 < formatted.split(", ").length; i1++)
                formattedArr[i1] = formatted.split(", ")[i1];
            returned.put(i, formattedArr);
        }

        return returned;
    }

    public HashMap<Integer, String[]> getRows() {
        return this.rows;
    }
    /**
     * Closes the {@link java.sql.ResultSet} and their related Streams
     *
     * @throws SQLException
     */

    /**
     * @return the original {@link ResultSet}
     */
    public ResultSet getResultSet() {
        return this.rs;
    }

    public void close() throws SQLException {
        this.rs.close();
    }
}
