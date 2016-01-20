package itp341.luu.jonathan.finalprojectluujonathan.Controllers;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import itp341.luu.jonathan.finalprojectluujonathan.Models.Block_Singleton;
import itp341.luu.jonathan.finalprojectluujonathan.R;

public class MainActivity extends Activity {
    /*Application Requirements:
    Three activites and intents: GameActivity, MainActivity, NextLevelActivity, ScoresActivity, SettingsActivity
    Images and Icons: Icon for the application is under drawables/icon, many images for buttons/gameplay
    Data persistence: SharedPreferences for objects under the Settings Button, JSON to save scores
    Adapter/ListView: Used in the Scores section - See ScoresActivity and CustomAdapter
    Technology Item #1: Services - Used a service to run music in the background throughout the whole app. See BackgroundMusicPlayerService
    Technology Item #2: Notifications - Pop up appears that leads to ScoreActivity if a new high score is achieved
     */


    Button settingsButton, scoresButton, newGameButton, loadGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hiding the Status Bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_main);

        //Turn on sound based on settings
        SharedPreferences prefs = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        boolean muteOn = prefs.getBoolean("PREFERENCE_MUTE", false);

        if (!muteOn) {
            Intent musicIntent = new Intent(this, BackgroundMusicPlayerService.class);
            startService(musicIntent);
        }

        //Initializing Buttons
        settingsButton = (Button) findViewById(R.id.settingsButton);
        scoresButton = (Button) findViewById(R.id.scoresButton);
        newGameButton = (Button) findViewById(R.id.newGameButton);
        loadGameButton = (Button) findViewById(R.id.loadGameButton);

        //Setting Button Listeners
        ButtonListener BL = new ButtonListener();
        settingsButton.setOnClickListener(BL);
        newGameButton.setOnClickListener(BL);
        scoresButton.setOnClickListener(BL);
    }

    //Stop music when the application is closed
    @Override
    protected void onDestroy(){
        super.onDestroy();
        stopService(new Intent(this, BackgroundMusicPlayerService.class));
    }

    //Launch new activities based on which Button is pressed
    public class ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            //Settings Button clicked
            if (v.getId() == R.id.settingsButton) {
                Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivityForResult(settingsIntent, 0);
            }
            //New Game Button is clicked - Reset score and level
            else if (v.getId() == R.id.newGameButton){
                Intent gameIntent = new Intent(getApplicationContext(), GameActivity.class);
                Block_Singleton.get(getApplicationContext()).setCurrentLevel(1);
                Block_Singleton.get(getApplicationContext()).setTotalScore(0);
                startActivityForResult(gameIntent, 1);
            }
            //Scores Button is clicked
            else if (v.getId() == R.id.scoresButton){
                Intent scoreIntent = new Intent(getApplicationContext(), ScoresActivity.class);
                startActivityForResult(scoreIntent, 2);
            }
        }
    }

}
