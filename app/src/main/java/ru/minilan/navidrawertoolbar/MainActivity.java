package ru.minilan.navidrawertoolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView textView;
    private TextView textViewDefaultTown;
    private Toolbar toolbar;
    private ImageView cloudDownload;
    private ImageView calendarTool;
    private FloatingActionButton fab;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private SharedPreferences sharedPreferences;
    static final String KEYTOWN = "KEYTOWN";
    static final String KEYSHOWPRESSURE = "KEYSHOWPRESSURE";
    static final String SETTINGS = "SETTINGS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewFinder();
        setListeners();
        toolbarAndNavigationDrawerInit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSharedPreferences();
    }

    private void loadSharedPreferences() {
        sharedPreferences = getSharedPreferences(SETTINGS, MODE_PRIVATE);
        textViewDefaultTown.setText(sharedPreferences.getString(KEYTOWN, getResources().getString(R.string.deftown)));
    }

    private void viewFinder() {
        toolbar = findViewById(R.id.toolbar);
        textView = findViewById(R.id.textView);
        textViewDefaultTown = findViewById(R.id.textViewDefaultTown);
        cloudDownload = findViewById(R.id.cloudDownload);
        calendarTool = findViewById(R.id.calendarTool);
        fab = findViewById(R.id.fab);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

    }

    private void setListeners() {
        calendarTool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("Calendar selected");
            }
        });

        cloudDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("CloudDownload selected");
                Intent intent = new Intent(MainActivity.this, SimpleBrowser.class);
                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void toolbarAndNavigationDrawerInit() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_Search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String query = getResources().getString(R.string.searching, s);
                textView.setText(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
//                String query = getResources().getString(R.string.searching, s);
//                textView.setText(query);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            textView.setText("Menu settings pressed");
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_help) {
            textView.setText("Menu help pressed");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_towns) {
            textView.setText("Town selector selected");
        } else if (id == R.id.nav_gallery) {
            textView.setText("Gallery selected");
        } else if (id == R.id.nav_slideshow) {
            textView.setText("Slideshow selected");
        } else if (id == R.id.nav_tools) {
            textView.setText("Tools selected");
        } else if (id == R.id.nav_share) {
            textView.setText("Share selected");
        } else if (id == R.id.nav_send) {
            textView.setText("Send selected");
        } else if (id == R.id.nav_feedback) {
            textView.setText("Feedback selected");
        } else if (id == R.id.nav_about) {
            textView.setText("About selected");
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
