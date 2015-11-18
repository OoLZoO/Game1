package de.h_da.fbi.game1.dao;

// Database Strings
public class SQLString {

    public static String DATABASE_NAME = "MobileDatenbankenPraktikum";

    public static String CREATE_TABLE_PERSON =
            "CREATE TABLE IF NOT EXISTS Person " + "(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "Name TEXT" +
                    ");"
            ;

    public static String CREATE_TABLE_AUFGABE =
            "CREATE TABLE IF NOT EXISTS Aufgabe (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "Gleichung TEXT, " +
                    "AnzahlVersuche INTEGER, " +
                    "Richtig INTEGER, " +
                    "Fehlversuche INTEGER, " +
                    "Loesung INTEGER, " +
                    "PersonID INTEGER, " +
                    "FOREIGN KEY(PersonID) REFERENCES Person(_id)" +
                    ");"
            ;

    public static String INSERT_INTO_AUFGABE(CharSequence Gleichung, Integer AnzahlVersuche, Integer Richtig, Integer Fehlversuche, Integer Loesung, Integer PersonID) {
        return "INSERT INTO Aufgabe (" +
                "Gleichung, " +
                "AnzahlVersuche, " +
                "Richtig, " +
                "Fehlversuche, " +
                "Loesung, " +
                "PersonID" +
                ") VALUES(" +
                "'" + Gleichung + "', " +
                AnzahlVersuche + ", " +
                Richtig + ", " +
                Fehlversuche + ", " +
                Loesung + ", " +
                PersonID +
                ");"
                ;
    }


    public static String SELECT_FROM_AUFGABE_BY_USERID(Integer userId) {
        return "SELECT Gleichung, AnzahlVersuche, Richtig, Fehlversuche, Loesung " +
                "FROM Aufgabe " +
                "WHERE PersonID=" + userId + ";"
                ;
    }

    public static String SELECT_NAME_FROM_PERSON(String username) {
        return "SELECT Name " +
                "FROM Person " +
                "WHERE Name = '" + username + "'"
                ;
    }

    public static String INSERT_INTO_PERSON(String username) {
        return "INSERT OR IGNORE INTO Person (" +
                "Name" +
                " )VALUES(" +
                "'" + username + "')"
                ;
    }

    public static String DROP_TABLE_AUFGABE =
            "DROP TABLE IF EXISTS 'Aufgabe'";


    public static String DELETE_FROM_AUFGABE_BY_UserID(Integer userId) {
        return "DELETE FROM Aufgabe " +
                "WHERE PersonID='" + userId + "';"
                ;
    }

    public static String SELECT_All_FROM_AUFGABE_BY_USERID(Integer userId) {
        return "SELECT count(Gleichung) " +
                "FROM Aufgabe " +
                "WHERE PersonID=" + userId + ";"
                ;
    }

    public static String SELECT_CORRECT_FROM_AUFGABE_BY_USERID(Integer userId) {
        return "SELECT count(Gleichung) " +
                "FROM Aufgabe " +
                "WHERE PersonID=" + userId +
                " AND Richtig=1;";
    }

    public static String SELECT_FIRSTTRY_FROM_AUFGABE_BY_USERID(Integer userId) {
        return "SELECT count(Gleichung) " +
                "FROM Aufgabe " +
                "WHERE PersonID=" + userId +
                " AND AnzahlVersuche=1;";
    }

    public static String SELECT_FAILS_FROM_AUFGABE_BY_USERID(Integer userId) {
        return "SELECT sum(Fehlversuche) " +
                "FROM Aufgabe " +
                "WHERE PersonID=" + userId + ";";
    }
}
