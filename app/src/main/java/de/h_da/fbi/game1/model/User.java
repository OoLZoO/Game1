package de.h_da.fbi.game1.model;


import java.util.LinkedList;
import java.util.List;

/**
 * Created by Zhenhao on 18.11.2015.
 */
public class User {
    public static Integer usercnt = 0;
    public String username;
    public List<Task> aufgabenListe;
    public Integer userID;

    public User (String username){
        this.username = username;
        this.userID = usercnt++;
        aufgabenListe = new LinkedList<Task>();
    }

    public void Assign (Task task) {
        aufgabenListe.add (task);
    }
}
