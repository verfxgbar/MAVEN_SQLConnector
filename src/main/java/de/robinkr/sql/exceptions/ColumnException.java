package de.robinkr.sql.exceptions;

public class ColumnException extends Exception {
    public ColumnException() {
        super("ColumnException", new Throwable("Table failed or System is setup correctly!"));
    }

    public ColumnException(String message) {
        super(message);
    }

    public ColumnException(Throwable cause) {
        super(cause);
    }

    public ColumnException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>Throws its own exception</p>
     * <p>Only used so you dont have to try catch anything :)</p>
     */
    public static void own() {
        try {
            throw new ColumnException();
        } catch (ColumnException e) {
            e.printStackTrace();

        }
    }
}
