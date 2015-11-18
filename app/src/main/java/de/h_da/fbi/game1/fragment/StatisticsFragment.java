package de.h_da.fbi.game1.fragment;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.h_da.fbi.game1.MainActivity;
import de.h_da.fbi.game1.R;
import de.h_da.fbi.game1.dao.DatabaseCRUD;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsFragment extends Fragment {

    public StatisticsFragment() {
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

    public void onStart() {
        super.onStart();
        TextView TV = (TextView) getView().findViewById(R.id.textViewStatInfoText);
        if (MainActivity.loggedUser.equals("Unknown")) {
            TV.setText("Bitte loggen Sie sich ein.");
            TV.setTextColor(Color.RED);
        } else {
            TV.setText("");
        }
        if (!(MainActivity.loggedUser.equals("Unknown"))) {
            TextView TV1 = (TextView) getView().findViewById(R.id.textViewTotalAufgaben);
            DatabaseCRUD.retrieveStatistics(getActivity(), TV1, getView());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics, container, false);
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
