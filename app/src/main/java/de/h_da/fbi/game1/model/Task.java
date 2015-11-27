package de.h_da.fbi.game1.model;

/**
 * Created by Zhenhao on 01.11.2015.
 */
public class Task {
    public String Name;
    public Integer Score;
    public Integer SkippedEx;
    public Integer WrongEx;
    public Integer solution;

    public Task(String Name, Integer Score, Integer SkippedEx, Integer WrongEx, Integer Solution) {
        this.Name = Name; // Gleichung
        this.Score = Score; // AnzahlVersuche
        this.SkippedEx = SkippedEx; // Richtig
        this.WrongEx = WrongEx; // Fehlversuche
        this.solution = Solution; // Lösung
    }
}
