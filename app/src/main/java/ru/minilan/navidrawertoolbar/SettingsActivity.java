package ru.minilan.navidrawertoolbar;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    private EditText town;
    private Switch switchPressure;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        town = findViewById(R.id.editTextDefaultTown);
        switchPressure = findViewById(R.id.switchPressure);
        sharedPreferences = getSharedPreferences(MainActivity.SETTINGS,MODE_PRIVATE);
        loadPreferences(sharedPreferences);
    }

    @Override
    protected void onPause() {
        super.onPause();
        savePreferences(sharedPreferences);
    }


    private void savePreferences(SharedPreferences sharedPreferences) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MainActivity.KEYTOWN, town.getText().toString());
        editor.putBoolean(MainActivity.KEYSHOWPRESSURE, switchPressure.isChecked());
        editor.apply();
    }

    private void loadPreferences(SharedPreferences sharedPreferences) {
        town.setText(sharedPreferences.getString(MainActivity.KEYTOWN, getResources().getString(R.string.deftown)));
        switchPressure.setChecked(sharedPreferences.getBoolean(MainActivity.KEYSHOWPRESSURE, false));
    }
}
