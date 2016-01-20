package itp341.luu.jonathan.finalprojectluujonathan.Models;

import org.json.JSONException;
import org.json.JSONObject;

public class Score {
    private static final String JSON_NAME = "NAME";
    private static final String JSON_SCORE = "SCORE";

    String name;
    Integer scoreValue;

    public Score(){
    }

    public Score(String n, Integer sV){
        name = n;
        scoreValue = sV;
    }

    public Score(JSONObject json) throws JSONException{
        name = json.getString(JSON_NAME);
        scoreValue = json.getInt(JSON_SCORE);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(Integer scoreValue) {
        this.scoreValue = scoreValue;
    }

    public JSONObject toJSON() throws JSONException{
        JSONObject json = new JSONObject();
        json.put(JSON_NAME, name);
        json.put(JSON_SCORE, scoreValue);

        return json;
    }
}
