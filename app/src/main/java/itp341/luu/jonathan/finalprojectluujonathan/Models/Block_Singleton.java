package itp341.luu.jonathan.finalprojectluujonathan.Models;

import android.content.Context;
import java.util.ArrayList;


public class Block_Singleton {
    private Context mAppContext;
    private static Block_Singleton sBlock;
    private ArrayList<Block> allBlocks, previousAllBlocks;
    private Integer totalScore, currentLevel;

    private Block_Singleton(Context appContext) {
        mAppContext = appContext;
        allBlocks = new ArrayList<Block>();
        previousAllBlocks = new ArrayList<>();
        totalScore = 0;
        currentLevel = 1;
    }

    public static Block_Singleton get(Context c) {
        if (sBlock == null)
            sBlock = new Block_Singleton(c.getApplicationContext());

        return sBlock;
    }

    public ArrayList<Block> getAllBlocks(){
        return allBlocks;
    }

    public ArrayList<Block> getPreviousAllBlocks(){
        return previousAllBlocks;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Integer currentLevel) {
        this.currentLevel = currentLevel;
    }
}


