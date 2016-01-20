package itp341.luu.jonathan.finalprojectluujonathan.Controllers;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import itp341.luu.jonathan.finalprojectluujonathan.Models.Score;
import itp341.luu.jonathan.finalprojectluujonathan.Models.ScoreSingleton;
import itp341.luu.jonathan.finalprojectluujonathan.R;

public class NextLevelActivity extends Activity{

    int totalScoreLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide action bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getActionBar();
        actionBar.hide();

        setContentView(R.layout.next_level_activity);

        //Load views from layout
        TextView winLoseView = (TextView) findViewById(R.id.winLoseText);
        TextView scoreNextView = (TextView) findViewById(R.id.scoreNextLevel);
        TextView blocksDestroyedView = (TextView) findViewById(R.id.blocksDestroyed);
        Button nextLevelButton = (Button) findViewById(R.id.nextLevelButton);
        Button endGameButton = (Button) findViewById(R.id.endGameButton);

        //Depending on the intent, show a winning screen or losing screen
        Intent i = getIntent();
        Boolean winLose = i.getBooleanExtra("WinLose", false);
        if (winLose)
            winLoseView.setText("Congratulations, you have progressed to the next level.");
        else
            winLoseView.setText("Sorry, you did not make it to the next level");

        //Display text on screen
        totalScoreLevel = i.getIntExtra("Score", -1);
        scoreNextView.setText("Current Score: " + Integer.toString(totalScoreLevel));
        blocksDestroyedView.setText(i.getStringExtra("BlocksDestroyed"));

        //If the player lost, do not display "Next Level" Button
        if (!winLose)
            nextLevelButton.setEnabled(false);

        //Set Result to let GameActivity know to start a new level
        nextLevelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

        //If the game is ended, store the score in JSON and update ListView
        endGameButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
                String currName = prefs.getString("PREFERENCE_NAME", "NoName Jim");

                Score tempScore = new Score(currName, totalScoreLevel);
                ScoreSingleton.get(NextLevelActivity.this).addScore(tempScore);
                Intent scoreIntent = new Intent(getApplicationContext(), ScoresActivity.class);
                startActivityForResult(scoreIntent, 2);
                finish();
            }
        });
    }
}
