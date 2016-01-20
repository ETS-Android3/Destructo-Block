package itp341.luu.jonathan.finalprojectluujonathan.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import itp341.luu.jonathan.finalprojectluujonathan.R;

public class CustomAdapter extends ArrayAdapter<Score> {
    TextView name, score;

    public CustomAdapter (Context c, ArrayList<Score> scores){
        super(c, 0, scores);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Score currScore = ScoreSingleton.get(getContext()).getScore(position);
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_entry, parent, false);

        name = (TextView) convertView.findViewById(R.id.nameName);
        score = (TextView) convertView.findViewById(R.id.scoreName);

        name.setText(currScore.getName().toString());
        score.setText(Integer.toString(currScore.getScoreValue()));

        return convertView;
    }
}