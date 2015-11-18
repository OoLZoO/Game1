package de.h_da.fbi.game1.fragment;


import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

import de.h_da.fbi.game1.MainActivity;
import de.h_da.fbi.game1.R;
import de.h_da.fbi.game1.dao.DatabaseCRUD;
import de.h_da.fbi.game1.dao.Db4oCRUD;
import de.h_da.fbi.game1.model.User;
import de.h_da.fbi.game1.util.Message;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment implements View.OnClickListener {
    Button myButton;
    SQLiteDatabase db;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = null;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_account, container, false);
        myButton = (Button) myView.findViewById(R.id.buttonReg);
        myButton.setOnClickListener(register);

        myButton = (Button) myView.findViewById(R.id.buttonLogin);
        myButton.setOnClickListener(login);

        myButton = (Button) myView.findViewById(R.id.buttonLogout);
        myButton.setOnClickListener(logout);

        return myView;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!MainActivity.loggedUser.equals("Unknown")) {
            loginSuccess();
        } else {
            Button BT = (Button) getView().findViewById(R.id.buttonLogout);
            BT.setEnabled(false);
        }
    }

    private void loginSuccess() {
        EditText ET = (EditText) getView().findViewById(R.id.EditTextNameLogin);
        ET.setText(MainActivity.loggedUser);
        ET.setEnabled(false);
        Button BT = (Button) getView().findViewById(R.id.buttonLogin);
        BT.setEnabled(false);
        ET = (EditText) getView().findViewById(R.id.EditTextNameReg);
        ET.setEnabled(false);
        BT = (Button) getView().findViewById(R.id.buttonReg);
        BT.setEnabled(false);
        BT = (Button) getView().findViewById(R.id.buttonLogout);
        BT.setEnabled(true);
    }

    private void logoutSuccess() {
        EditText ET = (EditText) getView().findViewById(R.id.EditTextNameLogin);
        ET.setEnabled(true);
        ET.setText("");
        Button BT = (Button) getView().findViewById(R.id.buttonLogin);
        BT.setEnabled(true);
        ET = (EditText) getView().findViewById(R.id.EditTextNameReg);
        ET.setEnabled(true);
        BT = (Button) getView().findViewById(R.id.buttonReg);
        BT.setEnabled(true);
        BT = (Button) getView().findViewById(R.id.buttonLogout);
        BT.setEnabled(false);
    }

    View.OnClickListener register = new View.OnClickListener() {
        public void onClick(View v) {

            EditText username = (EditText) getView().findViewById(R.id.EditTextNameReg);
            String user = username.getText().toString();
            //DatabaseCRUD.insertIntoPerson(getActivity(), user);
            new Db4oCRUD().insertIntoPerson(getActivity(), user);
        }
    };

    View.OnClickListener login = new View.OnClickListener() {
        public void onClick(View v) {
            EditText username = (EditText) getView().findViewById(R.id.EditTextNameLogin);
            String user = username.getText().toString();
            if (new Db4oCRUD().selectFromPerson(getActivity(), user)) {
                loginSuccess();
            }
        }
    };

    View.OnClickListener logout = new View.OnClickListener() {
        public void onClick(View v) {
            MainActivity.loggedUser = "Unknown";
            logoutSuccess();
            Message.message(getActivity(), "Logout successfull");
        }
    };

    @Override
    public void onClick(View v) {

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
