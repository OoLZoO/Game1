package de.h_da.fbi.game1.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import de.h_da.fbi.game1.MainActivity;
import de.h_da.fbi.game1.R;
import de.h_da.fbi.game1.dao.DatabaseCRUD;
import de.h_da.fbi.game1.dao.Db4oCRUD;
import de.h_da.fbi.game1.util.Message;


public class GameFragment extends Fragment implements View.OnClickListener {
    Double result;
    Integer tryCount;
    Integer failCount;
    Button myButton;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_game, container, false);
        myButton = (Button) myView.findViewById(R.id.button);
        myButton.setOnClickListener(enterButtonClicked);

        myButton = (Button) myView.findViewById(R.id.buttonUeberspringen);
        myButton.setOnClickListener(ueberspringenButtonClicked);


        return myView;
    }

    View.OnClickListener ueberspringenButtonClicked = new View.OnClickListener() {
        public void onClick(View v) {
            if (!(MainActivity.loggedUser.equals("Unknown"))) {
                TextView gleichungTextView = (TextView) getView().findViewById(R.id.textViewGleichung);
                DatabaseCRUD.insertIntoAufgabe(
                        getActivity(), gleichungTextView.getText(), tryCount, 0, failCount, result.intValue(), MainActivity.loggedUserID);
                tryCount = 0;
                failCount = 0;
            }
            createEquation();
            Message.message(getActivity(), "Aufgabe wurde übersprungen.");
        }
    };

    View.OnClickListener enterButtonClicked = new View.OnClickListener() {
        public void onClick(View v) {
            EditText ET = (EditText) getView().findViewById(R.id.editTextSolution);
            TextView gleichungTextView = (TextView) getView().findViewById(R.id.textViewGleichung);
            if (ET.getText().length() != 0) {
                if (Double.valueOf(ET.getText().toString()).equals(result)) {
                    Message.message(getActivity(), "Richtige Lösung!");
                    tryCount++;
                    if (!(MainActivity.loggedUser.equals("Unknown"))) {
                        new Db4oCRUD().insertIntoAufgabe(getActivity(), gleichungTextView.getText().toString(), tryCount, 1, failCount, result.intValue(), MainActivity.loggedUserID);
                        tryCount = 0;
                        failCount = 0;
                    }
                    createEquation();

                } else {
                    Message.message(getActivity(), "Falsche Lösung!");
                    if (!(MainActivity.loggedUser.equals("Unknown"))) {
                        tryCount++;
                        failCount++;
                    }
                }
                ET.setText("");
            }
        }
    };

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onStart() {
        super.onStart();

        tryCount = 0;
        failCount = 0;

        TextView TV = (TextView) getView().findViewById(R.id.textViewLoginStatus);
        if (MainActivity.loggedUser.equals("Unknown")) {
            TV.setText("Sie sind nicht eingeloggt.");
            TV.setTextColor(Color.parseColor("#FF5050"));
        } else {
            TV.setText("Sie sind eingeloggt als: " + MainActivity.loggedUser);
        }

        createEquation();

    }

    protected void createEquation() {
        TextView loesung = (TextView) getView().findViewById(R.id.textViewLoesung);

        TextView gleichungTextView = (TextView) getView().findViewById(R.id.textViewGleichung);

        Random r = new Random();
        ArrayList<Integer> numbers = new ArrayList<>();
        ArrayList<Character> signs = new ArrayList<>();

        Integer anzahlZahlen = r.nextInt(3) + 2;
        Integer numberSign;

        for (int i = 0; i < anzahlZahlen; i++) {
            numbers.add(i, r.nextInt(10) + 1);
        }

        gleichungTextView.setText(numbers.get(0).toString());

        for (int i = 1; i < numbers.size(); i++) {
            numberSign = r.nextInt(3);
            if (numberSign.equals(0)) {
                gleichungTextView.append("+" + numbers.get(i).toString());
                signs.add(i - 1, '+');
            } else if (numberSign.equals(1)) {
                gleichungTextView.append("-" + numbers.get(i).toString());
                signs.add(i - 1, '-');
            } else if (numberSign.equals(2)) {
                gleichungTextView.append("*" + numbers.get(i).toString());
                signs.add(i - 1, '*');
            } else if (numberSign.equals(3)) {
                gleichungTextView.append("/" + numbers.get(i).toString());
                signs.add(i - 1, '/');
            }
        }

        result = calculate(numbers, signs).doubleValue();
        loesung.setText(result.toString());

    }

    protected Integer calculate(ArrayList<Integer> zahlenListe, ArrayList<Character> operatorListe) {
        Integer d1 = 0;
        Integer d2 = 0;
        Integer index = 0;
        Character currentOperator = '+';

        while (index < zahlenListe.size()) {
            d2 = zahlenListe.get(index);

            while (index < operatorListe.size() && (operatorListe.get(index).equals('*') || operatorListe.get(index).equals('/'))) {
                if (operatorListe.get(index).equals('*')) {
                    d2 *= zahlenListe.get(index + 1);
                } else {
                    d2 /= zahlenListe.get(index + 1);
                }
                index++;    //test2
            }
            if (currentOperator.equals('+')) {
                d1 += d2;
            } else {
                d1 -= d2;
            }

            if (index.equals(operatorListe.size())) {
                break;
            }
            currentOperator = operatorListe.get(index);
            index++;
        }

        return d1;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public GameFragment() {
        // Required empty public constructor
    }


}
