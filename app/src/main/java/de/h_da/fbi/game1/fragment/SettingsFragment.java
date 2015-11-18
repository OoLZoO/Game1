package de.h_da.fbi.game1.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import de.h_da.fbi.game1.MainActivity;
import de.h_da.fbi.game1.R;
import de.h_da.fbi.game1.dao.DatabaseCRUD;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {


    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_settings, container, false);
        Button myButton = (Button) myView.findViewById(R.id.buttonDeleteDatabase);
        myButton.setOnClickListener(deleteDatabase);

        myButton = (Button) myView.findViewById(R.id.buttonDeleteTableAufgaben);
        myButton.setOnClickListener(deleteTableAufgaben);

        myButton = (Button) myView.findViewById(R.id.buttonDeleteSpecificAufgaben);
        myButton.setOnClickListener(deleteSpecificAufgaben);

        return myView;
    }

    View.OnClickListener deleteTableAufgaben = new View.OnClickListener() {
        public void onClick(View v) {
            DatabaseCRUD.dropTableAufgabe(getActivity());
        }
    };

    View.OnClickListener deleteDatabase = new View.OnClickListener() {
        public void onClick(View v) {
            DatabaseCRUD.dropDatabase(getActivity());
        }
    };

    View.OnClickListener deleteSpecificAufgaben = new View.OnClickListener() {
        public void onClick(View v) {
            DatabaseCRUD.deleteFromAufgaben(getActivity());
        }
    };

    public void onStart() {
        super.onStart();
        Button myButton = (Button) getView().findViewById(R.id.buttonDeleteSpecificAufgaben);
        myButton.setText("Aufgaben von " + MainActivity.loggedUser + " löschen");
        if (MainActivity.loggedUser.equals("Unknown")) {
            myButton.setEnabled(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
