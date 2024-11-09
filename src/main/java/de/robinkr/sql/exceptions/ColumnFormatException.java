package de.robinkr.sql.exceptions;

public class ColumnFormatException extends Exception {

    public ColumnFormatException() {
        super("ColumnFormatException", new Throwable("Table failed or System is setup correctly!"));
    }

    public ColumnFormatException(String message) {
        super(message);
    }

    public ColumnFormatException(Throwable cause) {
        super(cause);
    }

    public ColumnFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>Throws its own exception</p>
     * <p>Only used so you dont have to try catch anything :)</p>
     */
    public static void own() {
        try {
            throw new ColumnFormatException();
        } catch (ColumnFormatException e) {
            e.printStackTrace();
        }
    }
}
