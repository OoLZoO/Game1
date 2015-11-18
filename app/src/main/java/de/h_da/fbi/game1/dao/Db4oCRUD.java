package de.h_da.fbi.game1.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

import java.util.ArrayList;

import de.h_da.fbi.game1.MainActivity;
import de.h_da.fbi.game1.R;
import de.h_da.fbi.game1.model.Task;
import de.h_da.fbi.game1.model.User;
import de.h_da.fbi.game1.util.Message;

/**
 * Created by Zhenhao on 18.11.2015.
 */
public class Db4oCRUD {
    public static void insertIntoAufgabe(FragmentActivity fragmentActivity, CharSequence Gleichung, Integer AnzahlVersuche, Integer Richtig, Integer Fehlversuche, Integer Loesung, Integer PersonID) {

    }

    public static void insertIntoPerson(FragmentActivity fragmentActivity, String user) {
        User u = new User (user);
        ObjectContainer db = Db4oEmbedded.openFile("mdb.db");
        db.store (u);
        System.out.println ("Stored: "+ u);
        db.close();
    }

    public static boolean selectFromPerson(FragmentActivity fragmentActivity, String user) {
        boolean sqlSuccess = false;
        SQLiteDatabase db = null;
        try {
            db = fragmentActivity.openOrCreateDatabase(SQLString.DATABASE_NAME, android.content.Context.MODE_PRIVATE, null);
            db.execSQL(SQLString.CREATE_TABLE_PERSON);

            // Check if exists
            Cursor c = db.rawQuery(SQLString.SELECT_NAME_FROM_PERSON(user), null);
            if (c.moveToFirst()) {
                do {
                    MainActivity.loggedUser = c.getString(0);
                    sqlSuccess = true;
                    // get ID
                    Cursor d = db.rawQuery("SELECT _id FROM Person WHERE Name = '" + user + "' ", null);
                    d.moveToFirst();
                    MainActivity.loggedUserID = d.getInt(0);
                    d.close();

                } while (c.moveToNext());
            } else {
                Message.message(fragmentActivity, "User does not exists");
            }
            c.close();
        } catch (Exception e) {
            Message.message(fragmentActivity, "Error: " + e.getMessage());
        } finally {
            db.close();
        }
        return sqlSuccess;
    }

    public static void selectFromAufgabe(FragmentActivity fragmentActivity, ArrayList<Task> arrayOfAufgaben) {
        SQLiteDatabase db2 = null;
        try {
            db2 = fragmentActivity.openOrCreateDatabase(SQLString.DATABASE_NAME, android.content.Context.MODE_PRIVATE, null);
            db2.execSQL(SQLString.CREATE_TABLE_AUFGABE);
            db2.execSQL(SQLString.CREATE_TABLE_PERSON);

            Log.d("TEST", "INDRIN");
            Cursor c = db2.rawQuery(SQLString.SELECT_FROM_AUFGABE_BY_USERID(MainActivity.loggedUserID), null);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                String gleichung = c.getString(0);
                Integer anzahlversuche = c.getInt(1);
                Integer richtig = c.getInt(2);
                Integer fehlversuche = c.getInt(3);
                Integer loesung = c.getInt(4);
                arrayOfAufgaben.add(new Task(gleichung, richtig, anzahlversuche, fehlversuche, loesung));
                c.moveToNext();
            }

        } catch (Exception e) {
            Message.message(fragmentActivity, "Error: " + e.getMessage());
        } finally {
            db2.close();
        }
    }

    public static void deleteFromAufgaben(FragmentActivity fragmentActivity) {
        SQLiteDatabase db = null;
        try {
            fragmentActivity.deleteDatabase(SQLString.DATABASE_NAME);
            db = fragmentActivity.openOrCreateDatabase(SQLString.DATABASE_NAME, android.content.Context.MODE_PRIVATE, null);
            db.execSQL(SQLString.DELETE_FROM_AUFGABE_BY_UserID(MainActivity.loggedUserID));
            //db.delete("Aufgabe", "PersonID="+((MainActivity)getActivity()).loggedUserID, null);
            Message.message(fragmentActivity, "Aufgabeneinträge von " + MainActivity.loggedUser + " wurden erfolgreich gelöscht.");
        } catch (Exception e) {
            Message.message(fragmentActivity, "Error deleteSpecific: " + e.getMessage());
        } finally {
            db.close();
        }
    }

    public static void dropDatabase(FragmentActivity fragmentActivity) {
        SQLiteDatabase db = null;
        try {
            fragmentActivity.deleteDatabase(SQLString.DATABASE_NAME);
            db = fragmentActivity.openOrCreateDatabase(SQLString.DATABASE_NAME, android.content.Context.MODE_PRIVATE, null);
            db.execSQL(SQLString.CREATE_TABLE_PERSON);
            db.execSQL(SQLString.CREATE_TABLE_AUFGABE);
            MainActivity.loggedUser = "Unknown";
            MainActivity.loggedUserID = -1;
            Message.message(fragmentActivity, "Datenbank wurde erfolgreich gelöscht und neu angelegt.");
        } catch (Exception e) {
            Message.message(fragmentActivity, "Error: " + e.getMessage());
        } finally {
            db.close();
        }
    }

    public static void dropTableAufgabe(FragmentActivity fragmentActivity) {
        SQLiteDatabase db = null;
        try {
            fragmentActivity.deleteDatabase(SQLString.DATABASE_NAME);
            db = fragmentActivity.openOrCreateDatabase(SQLString.DATABASE_NAME, android.content.Context.MODE_PRIVATE, null);
            db.execSQL(SQLString.DROP_TABLE_AUFGABE);
            Message.message(fragmentActivity, "Tabelle 'Aufgabe' wurde erfolgreich gelöscht.");
        } catch (Exception e) {
            Message.message(fragmentActivity, "Error: " + e.getMessage());
        } finally {
            db.close();
        }
    }

    public static void retrieveStatistics(FragmentActivity fragmentActivity, TextView TV1, View view) {
        SQLiteDatabase db2 = fragmentActivity.openOrCreateDatabase(SQLString.DATABASE_NAME, android.content.Context.MODE_PRIVATE, null);
        db2.execSQL(SQLString.CREATE_TABLE_AUFGABE);
        db2.execSQL(SQLString.CREATE_TABLE_PERSON);

        try {
            Cursor c = db2.rawQuery(SQLString.SELECT_All_FROM_AUFGABE_BY_USERID(MainActivity.loggedUserID), null);
            c.moveToFirst();
            Integer bla = c.getInt(0);
            TV1.setText(bla.toString());
        } catch (Exception e) {
            Message.message(fragmentActivity, "Error: " + e.getMessage());
        }

        TV1 = (TextView) view.findViewById(R.id.textViewTotalRichtig);
        try {
            Cursor c = db2.rawQuery(SQLString.SELECT_CORRECT_FROM_AUFGABE_BY_USERID(MainActivity.loggedUserID), null);
            c.moveToFirst();
            Integer bla = c.getInt(0);
            TV1.setText(bla.toString());
        } catch (Exception e) {
            Message.message(fragmentActivity, "Error: " + e.getMessage());
        }

        TV1 = (TextView) view.findViewById(R.id.textViewErstversuch);
        try {
            Cursor c = db2.rawQuery(SQLString.SELECT_FIRSTTRY_FROM_AUFGABE_BY_USERID(MainActivity.loggedUserID), null);
            c.moveToFirst();
            Integer bla = c.getInt(0);
            TV1.setText(bla.toString());
        } catch (Exception e) {
            Message.message(fragmentActivity, "Error: " + e.getMessage());
        }

        TV1 = (TextView) view.findViewById(R.id.textViewFalscheLoesungen);
        try {
            Cursor c = db2.rawQuery(SQLString.SELECT_FAILS_FROM_AUFGABE_BY_USERID(MainActivity.loggedUserID), null);
            c.moveToFirst();
            Integer bla = c.getInt(0);
            TV1.setText(bla.toString());
        } catch (Exception e) {
            Message.message(fragmentActivity, "Error: " + e.getMessage());
        }
    }
}
