package de.robinkr.sql;

import java.util.HashMap;
import java.util.Random;

public class OfflineConnection {

    private static HashMap<String, HashMap<String, Table>> databaseToTable;

    static {
        databaseToTable = new HashMap<>();
        databaseToTable.put("Database1", createRandomTables());
        databaseToTable.put("Database2", createRandomTables());
        databaseToTable.put("Database3", createRandomTables());
    }

    public static String[] getTableNames(String databaseName) {
        return databaseToTable.get(databaseName).keySet().toArray(new String[0]);
    }

    public static String[] getDatabases() {
        return databaseToTable.keySet().toArray(new String[0]);
    }

    public String[][] getTableContent(String databaseName, String tableName) {
        return databaseToTable.get(databaseName).get(tableName).getData();
    }

    private static HashMap<String, Table> createRandomTables() {
        Table table = new Table();
        HashMap<String, Table> map = new HashMap<>();
        map.put("Table" + new Random().nextInt(999), table);

        return map;
    }
    public static class Table {
        private String[] columns;
        private String[][] data;

        private static final int ROW_AMOUNT = 15;

        public Table() {
            this.columns = createColumns();
            this.data = createData();
        }

        public String[] getColumns() {
            return this.columns;
        }

        public String[][] getData() {
            return this.data;
        }

        // 3 Columns
        private String[] createColumns() {
            return new String[]{
                    "Column1" + new Random().nextInt(99999), "Column2" + new Random().nextInt(99999), "Column3" + new Random().nextInt(99999),
            };
        }
        private String[][] createData() {
            int amountRows = new Random().nextInt(ROW_AMOUNT);
            String[][] data = new String[amountRows][columns.length];
            for (int i = 0; i < amountRows; i++) {
                for (int j = 0; j < columns.length; j++) {
                    data[i][j] = "Value" + new Random().nextInt(99999);
                }
            }

            return data;
        }
    }
}


