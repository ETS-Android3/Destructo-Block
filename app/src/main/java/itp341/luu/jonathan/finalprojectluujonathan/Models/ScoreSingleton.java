package itp341.luu.jonathan.finalprojectluujonathan.Models;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import itp341.luu.jonathan.finalprojectluujonathan.Controllers.JSONSerializer;
import itp341.luu.jonathan.finalprojectluujonathan.Controllers.ScoresActivity;
import itp341.luu.jonathan.finalprojectluujonathan.R;

public class ScoreSingleton {
    private static final String FILENAME = "scores.json";

    private Context mAppContext;
    private ArrayList<Score> allScores;
    private static ScoreSingleton sScore;
    private JSONSerializer mSerializer;
    private Integer topScore;

    private ScoreSingleton(Context appContext) {
        mAppContext = appContext;
        mSerializer = new JSONSerializer(mAppContext, FILENAME);

        try{
            allScores = mSerializer.loadScores();
            topScore = allScores.get(allScores.size()-1).getScoreValue();
        }catch (Exception e) {
            allScores = new ArrayList<Score>();
            topScore = 0;
        }
    }

    public static ScoreSingleton get(Context c) {
        if (sScore == null)
            sScore = new ScoreSingleton(c.getApplicationContext());

        return sScore;
    }

    public void addScore(Score s){

        allScores.add(s);
        Collections.sort(allScores, new Comparator<Score>() {
            @Override
            public int compare(Score lhs, Score rhs) {
                return rhs.getScoreValue() - lhs.getScoreValue();
            }
        });

        //Code for notifications
        if ((s.getScoreValue() > topScore)){
            NotificationManager NM = (NotificationManager) mAppContext.getSystemService(Context.NOTIFICATION_SERVICE);
            Intent intent = new Intent(mAppContext, ScoresActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(mAppContext, (int) System.currentTimeMillis(), intent, 0);

            Notification n = new Notification.Builder(mAppContext).setContentTitle("New Top Score: " + s.getScoreValue().toString()).setSmallIcon(R.drawable.icon).setContentIntent(pIntent)
                    .setAutoCancel(true).build();

            NM.notify(0, n);
            topScore = s.getScoreValue();
        }

        try {
            mSerializer.saveScores(allScores);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeScore(Score s){
        allScores.remove(s);
    }

    public Score getScore(int position){
        return allScores.get(position);
    }

    public ArrayList<Score> getAllScores(){
        return allScores;
    }

    public void resetAllScores(){
        allScores.clear();

        try {
            mSerializer.saveScores(allScores);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
