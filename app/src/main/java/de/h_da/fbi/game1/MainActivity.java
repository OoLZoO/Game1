package de.h_da.fbi.game1;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import de.h_da.fbi.game1.fragment.AccountFragment;
import de.h_da.fbi.game1.fragment.GameFragment;
import de.h_da.fbi.game1.fragment.HistoryFragment;
import de.h_da.fbi.game1.fragment.SettingsFragment;
import de.h_da.fbi.game1.fragment.StatisticsFragment;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;                      // contains max. 2 Layouts
    private ListView navList;
    android.support.v4.app.FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    public static String loggedUser;
    public static Integer loggedUserID;
    public static String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // SQLite
        language = "de";
        loggedUser = "Unknown";
        loggedUserID = -1;
        //deleteDatabase("MobileDatenbankenPraktikum");

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        navList = (ListView) findViewById(R.id.navlist);
        navList.setOnItemClickListener(this);

        ArrayList<String> navArray = new ArrayList<String>();
        navArray.add("Home");
        navArray.add("Verlauf");
        navArray.add("Statistiken");
        navArray.add("Einstellungen");
        navArray.add("Accounteinstellungen");
        navList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, navArray);
        navList.setAdapter(adapter);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.opendrawer, R.string.closedrawer);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);               // Sync with ActionBar

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        fragmentManager = getSupportFragmentManager();

        loadSection(0);
    }

    private void loadSection(int i) { // Default Home
        navList.setItemChecked(i, true);

        switch (i) {
            case 0:
                GameFragment GameFragment = new GameFragment();
                fragmentTransaction = fragmentManager.beginTransaction(); // v4
                fragmentTransaction.replace(R.id.fragmentholder, GameFragment);
                fragmentTransaction.commit();
                break;
            case 1:
                HistoryFragment HistoryFragment = new HistoryFragment();
                fragmentTransaction = fragmentManager.beginTransaction(); // v4
                fragmentTransaction.replace(R.id.fragmentholder, HistoryFragment);
                fragmentTransaction.commit();
                break;
            case 2:
                StatisticsFragment StatisticsFragment = new StatisticsFragment();
                fragmentTransaction = fragmentManager.beginTransaction(); // v4
                fragmentTransaction.replace(R.id.fragmentholder, StatisticsFragment);
                fragmentTransaction.commit();
                break;
            case 3:
                SettingsFragment SettingsFragment = new SettingsFragment();
                fragmentTransaction = fragmentManager.beginTransaction(); // v4
                fragmentTransaction.replace(R.id.fragmentholder, SettingsFragment);
                fragmentTransaction.commit();
                break;
            case 4:
                AccountFragment AccountFragment = new AccountFragment();
                fragmentTransaction = fragmentManager.beginTransaction(); // v4
                fragmentTransaction.replace(R.id.fragmentholder, AccountFragment);
                fragmentTransaction.commit();
                break;
        }
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(navList)) {
                drawerLayout.closeDrawer(navList);
            } else {
                drawerLayout.openDrawer(navList);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        loadSection(position);

        drawerLayout.closeDrawer(navList);          // Close drawer after click
    }
}
