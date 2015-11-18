package de.h_da.fbi.game1.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import de.h_da.fbi.game1.MainActivity;
import de.h_da.fbi.game1.R;
import de.h_da.fbi.game1.adapter.TaskAdapter;
import de.h_da.fbi.game1.dao.DatabaseCRUD;
import de.h_da.fbi.game1.dao.Db4oCRUD;
import de.h_da.fbi.game1.model.Task;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends ListFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        if (!(MainActivity.loggedUser.equals("Unknown"))) {
            ArrayList<Task> arrayOfAufgaben = new ArrayList<>();
            Db4oCRUD.selectFromAufgabe(getActivity(), arrayOfAufgaben);
            System.out.println(arrayOfAufgaben.size());
            ListAdapter adapter = new TaskAdapter(getActivity(), arrayOfAufgaben);
            setListAdapter(adapter);
        }
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(getActivity(), getListView().getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
    }

    public void onStart() {
        super.onStart();
        TextView informationTV = (TextView) getView().findViewById(R.id.informationttv);
        if (MainActivity.loggedUser.equals("Unknown")) {
            informationTV.setText("Bitte loggen Sie sich ein.");
            informationTV.setTextColor(Color.RED);
        } else {
            informationTV.setText("");
            informationTV.setTextColor(Color.BLACK);
        }
    }
}
