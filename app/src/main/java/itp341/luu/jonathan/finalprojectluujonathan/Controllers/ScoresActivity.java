package itp341.luu.jonathan.finalprojectluujonathan.Controllers;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import itp341.luu.jonathan.finalprojectluujonathan.Models.CustomAdapter;
import itp341.luu.jonathan.finalprojectluujonathan.Models.Score;
import itp341.luu.jonathan.finalprojectluujonathan.Models.ScoreSingleton;
import itp341.luu.jonathan.finalprojectluujonathan.R;

public class ScoresActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scores_activity);

        //Attach adapter to ListView
        ArrayList<Score> allScores = ScoreSingleton.get(getApplicationContext()).getAllScores();
        CustomAdapter adapter = new CustomAdapter(this, allScores);

        ListView scoreList = (ListView) findViewById(R.id.scoreList);
        scoreList.setAdapter(adapter);

        //If return button is pressed, end the activity
        Button returnButton = (Button) findViewById(R.id.returnButtonScore);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
