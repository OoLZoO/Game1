package de.h_da.fbi.game1.dao;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Predicate;

import java.util.ArrayList;

import de.h_da.fbi.game1.MainActivity;
import de.h_da.fbi.game1.R;
import de.h_da.fbi.game1.model.Task;
import de.h_da.fbi.game1.model.User;
import de.h_da.fbi.game1.util.Message;

/**
 * Created by Zhenhao on 18.11.2015.
 */
public class Db4oCRUD extends Activity {

    public void insertIntoAufgabe(FragmentActivity fragmentActivity, String Gleichung, Integer AnzahlVersuche, Integer Richtig, Integer Fehlversuche, Integer Loesung, final Integer PersonID) {
        EmbeddedConfiguration myConf = Db4oEmbedded.newConfiguration();
        myConf.common().objectClass(User.class).cascadeOnUpdate(true);
        myConf.common().objectClass(Task.class).cascadeOnUpdate(true);
        ObjectContainer db = Db4oEmbedded.openFile(myConf, "/data/data/" + fragmentActivity.getPackageName() + "/database");

        Task u = new Task(Gleichung, AnzahlVersuche, Richtig, Fehlversuche, Loesung);

        ObjectSet<User> os = db.query(
                new Predicate<User>(){
                    public boolean match (User u){
                        return u.username.equals(MainActivity.loggedUser);
                    }
                });
        User z = null;
        for (User b : os){
            z = b;
            z.Assign(u);
        }

        //unidirectional from user to task
        db.store(z);
        db.close();
    }

    public void insertIntoPerson(FragmentActivity fragmentActivity, String user) {
        User u = new User (user);
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "/data/data/" + fragmentActivity.getPackageName() + "/database");
        db.store(u);

        db.close();
    }

    public boolean selectFromPerson(FragmentActivity fragmentActivity, final String user) {
        boolean db4oSuccess = false;

        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "/data/data/" + fragmentActivity.getPackageName() + "/database");
        ObjectSet<User> os = db.query(
                new Predicate<User>(){
                    public boolean match (User u){
                        return u.username.equals(user);
                    }
                });
        User z = null;
        for (User b : os){
            z = b;
        }
        db.close();
        if (user != null) {
            MainActivity.loggedUser = z.username;
            MainActivity.loggedUserID = z.userID;
            db4oSuccess = true;
        } else {
            db4oSuccess = false;
        }
        return db4oSuccess;
    }

    public static void selectFromAufgabe(FragmentActivity fragmentActivity, ArrayList<Task> arrayOfAufgaben) {
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "/data/data/" + fragmentActivity.getPackageName() + "/database");
        ObjectSet<User> os = db.query(
                new Predicate<User>(){
                    public boolean match (User u){
                        return u.username.equals(MainActivity.loggedUser);
                    }
                });
        User z = null;
        for (User b : os){
            z = b;
        }
        db.close();
        for (int i=0; i<z.aufgabenListe.size(); i++){
            arrayOfAufgaben.add(z.aufgabenListe.get(i));
        }
    }

    public static void deleteFromAufgaben(FragmentActivity fragmentActivity) {
        // NOT IMPLEMENTED YET
    }

    public static void dropDatabase(FragmentActivity fragmentActivity) {
        // NOT IMPLEMENTED YET
    }

    public static void dropTableAufgabe(FragmentActivity fragmentActivity) {
        // NOT IMPLEMENTED YET
    }

    public static void retrieveStatistics(FragmentActivity fragmentActivity, TextView TV1, View view) {
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "/data/data/" + fragmentActivity.getPackageName() + "/database");
        ObjectSet<User> os = db.query(
                new Predicate<User>(){
                    public boolean match (User u){
                        return u.username.equals(MainActivity.loggedUser);
                    }
                });
        User z = null;
        for (User b : os){
            z = b;
        }
        db.close();

        TV1 = (TextView) view.findViewById(R.id.textViewTotalAufgaben);
        TV1.setText(String.valueOf(z.aufgabenListe.size()));

        int totalRichtig = 0;
        int falscheLoesungen = 0;
        int erstversuche = 0;


        for (int i=0; i<z.aufgabenListe.size(); i++){
            if (z.aufgabenListe.get(i).Score.equals(1)){
                totalRichtig++;
            }
            if (z.aufgabenListe.get(i).WrongEx != 0){
                falscheLoesungen+=z.aufgabenListe.get(i).WrongEx;
            }
            if (z.aufgabenListe.get(i).WrongEx == 0 && z.aufgabenListe.get(i).Score.equals(1)){
                erstversuche++;
            }
        }

        TV1 = (TextView) view.findViewById(R.id.textViewTotalRichtig);
        TV1.setText(String.valueOf(totalRichtig));

        TV1 = (TextView) view.findViewById(R.id.textViewFalscheLoesungen);
        TV1.setText(String.valueOf(falscheLoesungen));

        TV1 = (TextView) view.findViewById(R.id.textViewErstversuch);
        TV1.setText(String.valueOf(erstversuche));
    }
}
