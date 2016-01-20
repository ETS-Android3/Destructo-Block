package itp341.luu.jonathan.finalprojectluujonathan.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import itp341.luu.jonathan.finalprojectluujonathan.Models.ScoreSingleton;
import itp341.luu.jonathan.finalprojectluujonathan.R;

public class SettingsActivity extends Activity{

    SharedPreferences.Editor prefEditor;
    TextView savedName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        //Get Views from Layout
        final RadioButton muteNo = (RadioButton) findViewById(R.id.muteNo);
        RadioButton muteYes = (RadioButton) findViewById(R.id.muteYes);
        RadioGroup muteRadio = (RadioGroup) findViewById(R.id.muteRadio);

        RadioListener RL = new RadioListener();
        muteRadio.setOnCheckedChangeListener(RL);

        //If Cancel button is pressed, end activity
        Button settingsCancelButton = (Button) findViewById(R.id.settingsCancelButton);
        settingsCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Using SharedPreferences to store Name and Mute settings
        savedName = (TextView) findViewById(R.id.nameTextSettings);
        SharedPreferences prefs = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        prefEditor = prefs.edit();

        String getName = prefs.getString("PREFERENCE_NAME", "NoName Jim");
        savedName.setText(getName);

        Boolean mute = prefs.getBoolean("PREFERENCE_MUTE", false);

        if (mute){
            muteYes.setChecked(true);
        }
        else
            muteNo.setChecked(true);

        //If save button is pressed, commit the SharedPreferences and exit activity
        Button settingsSaveButton = (Button) findViewById(R.id.settingsSaveButton);
        settingsSaveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String name = savedName.getText().toString();
                prefEditor.putString("PREFERENCE_NAME", name);

                if (muteNo.isChecked() == true){
                    prefEditor.putBoolean("PREFERENCE_MUTE", false);
                }
                else{
                    prefEditor.putBoolean("PREFERENCE_MUTE", true);
                }
                prefEditor.commit();
                finish();
            }
        });

        //If reset button is pressed, delete all scores from the Singleton and save to JSON
        Button resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScoreSingleton.get(getApplicationContext()).resetAllScores();
            }
        });
    }

    //If mute is on, stop music. If mute is off, start music again
    public class RadioListener implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.muteNo){
                startService(new Intent(SettingsActivity.this, BackgroundMusicPlayerService.class));
            }
            if (checkedId == R.id.muteYes){
                stopService(new Intent(SettingsActivity.this, BackgroundMusicPlayerService.class));
            }
        }
    }
}
