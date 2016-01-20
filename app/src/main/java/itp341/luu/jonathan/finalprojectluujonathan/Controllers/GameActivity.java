package itp341.luu.jonathan.finalprojectluujonathan.Controllers;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

import itp341.luu.jonathan.finalprojectluujonathan.Models.Block;
import itp341.luu.jonathan.finalprojectluujonathan.Models.Block_Singleton;
import itp341.luu.jonathan.finalprojectluujonathan.R;

public class GameActivity extends Activity {

    Vector<LinearLayout> allColumn = new Vector<LinearLayout>();
    ArrayList<Block> allBlocks, previousAllBlocks;

    BlockListener BL;
    SoundPool sp;

    Integer nextScoreCount, totalScoreCount, currentLevel, neededNextScoreCount, soundID;
    TextView nextScoreCountView, totalScoreView;
    LinearLayout columnLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide action bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getActionBar();
        actionBar.hide();

        setContentView(R.layout.game_activity);

        //Sound effects for when blocks are destroyed
        AudioAttributes attrs = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        sp = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(attrs)
                .build();

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundID = sp.load(getApplicationContext(), R.raw.crackle, 1);

        //Loading in Views from Layout
        nextScoreCountView = (TextView) findViewById(R.id.nextLevelScore);
        totalScoreView = (TextView) findViewById(R.id.totalScore);
        TextView levelNumber = (TextView) findViewById(R.id.levelNumber);

        LinearLayout columnOne = (LinearLayout) findViewById(R.id.columnOne);
        LinearLayout columnTwo = (LinearLayout) findViewById(R.id.columnTwo);
        LinearLayout columnThree = (LinearLayout) findViewById(R.id.columnThree);
        LinearLayout columnFour = (LinearLayout) findViewById(R.id.columnFour);
        LinearLayout columnFive = (LinearLayout) findViewById(R.id.columnFive);
        LinearLayout columnSix = (LinearLayout) findViewById(R.id.columnSix);
        LinearLayout columnSeven = (LinearLayout) findViewById(R.id.columnSeven);
        columnLayout = (LinearLayout) findViewById(R.id.columnLayout);

        allColumn.add(columnOne);
        allColumn.add(columnTwo);
        allColumn.add(columnThree);
        allColumn.add(columnFour);
        allColumn.add(columnFive);
        allColumn.add(columnSix);
        allColumn.add(columnSeven);

        //Retrieving arrays from Singleton
        allBlocks = Block_Singleton.get(getApplicationContext()).getAllBlocks();
        previousAllBlocks = Block_Singleton.get(getApplicationContext()).getPreviousAllBlocks();
        BL = new BlockListener();

        //Initializing variables and TextViews
        nextScoreCount = 0;
        totalScoreCount = Block_Singleton.get(getApplicationContext()).getTotalScore();
        totalScoreView.setText("Score: " + totalScoreCount.toString());

        currentLevel = Block_Singleton.get(getApplicationContext()).getCurrentLevel();
        levelNumber.setText("Level " + currentLevel.toString());

        //Setting score levels
        if ((currentLevel % 2) == 1 && currentLevel < 6)
            neededNextScoreCount = 50;
        else
            neededNextScoreCount = 60;
        if (currentLevel >=7 && currentLevel<=8)
            neededNextScoreCount = 65;
        if (currentLevel >8)
            neededNextScoreCount = 70;

        nextScoreCountView.setText("Next Level: " + nextScoreCount.toString() + " of " + neededNextScoreCount.toString());

        generateBlocks();
    }

    //Create all blocks to display on a new game
    public void generateBlocks(){
        if (allBlocks.size() != 0) {
            allBlocks.clear();
            previousAllBlocks.clear();
        }
        for (int i=0; i<7; i++) {
            for (int j = 0; j < 12; j++) {
                Block tempBlock = createSingleBlock((j*7)+i);
                allColumn.get(i).addView(tempBlock);
            }
        }

        Collections.sort(allBlocks, new BlockCompare());
    }

    //Randomly generate a colored block
    private Block createSingleBlock(int blockNum){
        int max = 2;
        if (currentLevel > 2 && currentLevel < 5)
            max = 3;
        else if (currentLevel > 4)
            max = 4;

        int selectedNum = randInt(0, max);
        Block IV = null;

        switch (selectedNum){
            case 0:
                IV = new Block(getApplicationContext(), "Blue", blockNum);
                IV.setImageResource(R.drawable.blueblock);
                break;
            case 1:
                IV = new Block(getApplicationContext(), "Yellow", blockNum);
                IV.setImageResource(R.drawable.yellowblock);
                break;
            case 2:
                IV = new Block(getApplicationContext(), "Green", blockNum);
                IV.setImageResource(R.drawable.greenblock);
                break;
            case 3:
                IV = new Block(getApplicationContext(), "Red", blockNum);
                IV.setImageResource(R.drawable.redblock);
                break;
            case 4:
                IV = new Block(getApplicationContext(), "Gray", blockNum);
                IV.setImageResource(R.drawable.grayblock);
                break;
        }

        //Convert pixels to dp when changing the width of the block
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 34, getApplicationContext().getResources().getDisplayMetrics());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
        IV.setLayoutParams(lp);

        IV.setOnClickListener(BL);
        allBlocks.add(IV);

        return IV;
    }

    //RandInt and BlockCompare used as accessory functions
    public static int randInt(int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max+1);
    }

    //Comparator for Collections.sort
    public class BlockCompare implements Comparator<Block> {
        @Override
        public int compare(Block lhs, Block rhs) {
            return lhs.getGridLocation() - rhs.getGridLocation();
        }
    }

    //Do something when the block is clicked
    public class BlockListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Block tempBlock = null;

            //Find which block was clicked
            for (int i=0; i<allBlocks.size(); i++) {
                if (v.equals(allBlocks.get(i))) {
                    tempBlock = allBlocks.get(i);
                    break;
                }
            }

            try {
                //If it is the first click, highlight the blocks
                if (tempBlock.getCurrentlyClicked() == false) {
                    //Check if another item has been clicked already
                    if (previousAllBlocks.size() != 0) {
                        for (int i = 0; i < previousAllBlocks.size(); i++)
                            unselectBlocks(previousAllBlocks.get(i));
                    }

                    ArrayList<Block> selectedBlocks = parseBlocks(tempBlock);
                    previousAllBlocks = selectedBlocks;
                    if (selectedBlocks.size() > 1) {
                        for (int i = 0; i < selectedBlocks.size(); i++)
                            highlightBlocks(selectedBlocks.get(i));
                    }
                }
                //Else destroy them on second click
                else {
                    if (previousAllBlocks.size() > 1) {
                        nextScoreCount += previousAllBlocks.size();
                        totalScoreCount += previousAllBlocks.size() * previousAllBlocks.size();
                        for (int i = 0; i < previousAllBlocks.size(); i++)
                            destroyBlocks(previousAllBlocks.get(i));

                        sp.play(soundID, 1, 1, 1, 0, 1);
                        updateBlockPositions();
                        previousAllBlocks.clear();

                        nextScoreCountView.setText("Next Level: " + nextScoreCount.toString() + " of " + neededNextScoreCount.toString());
                        totalScoreView.setText("Score: " + totalScoreCount.toString());

                        //If there are no more moves, check to see if the player has won or not and start the NextLevelActivity
                        if (!checkRemainingMoves() && (nextScoreCount >= neededNextScoreCount)) {
                            Intent nextLevelIntent = new Intent(getApplicationContext(), NextLevelActivity.class);
                            nextLevelIntent.putExtra("WinLose", true);
                            nextLevelIntent.putExtra("Score", totalScoreCount);
                            nextLevelIntent.putExtra("BlocksDestroyed", "Blocks Destroyed: " + nextScoreCount.toString() + " of " + neededNextScoreCount.toString());
                            startActivityForResult(nextLevelIntent, 2);
                        } else if (!checkRemainingMoves()) {
                            Intent nextLevelIntent = new Intent(getApplicationContext(), NextLevelActivity.class);
                            nextLevelIntent.putExtra("WinLose", false);
                            nextLevelIntent.putExtra("Score", totalScoreCount);
                            nextLevelIntent.putExtra("BlocksDestroyed", "Blocks Destroyed: " + nextScoreCount.toString() + " of " + neededNextScoreCount.toString());
                            startActivityForResult(nextLevelIntent, 2);
                        }
                    }
                }
            }
            catch(Exception e){}
        }
    }

    //Change image resource of a block from normal to highlighted
    public void highlightBlocks(Block block){
        switch (block.getBlockType()){
            case "Blue":
                block.setImageResource(R.drawable.blue_clicked);
                break;
            case "Green":
                block.setImageResource(R.drawable.green_clicked);
                break;
            case "Yellow":
                block.setImageResource(R.drawable.yellow_clicked);
                break;
            case "Red":
                block.setImageResource(R.drawable.red_clicked);
                break;
            case "Gray":
                block.setImageResource(R.drawable.gray_clicked);
                break;
        }
    }

    //Remove blocks from game
    public void destroyBlocks(Block block){
        int currCol = (block.getGridLocation())%7;
        allColumn.get(currCol).removeView(block);
    }

    //Change image resource of a block from highlighted to normal
    public void unselectBlocks(Block block){
        switch (block.getBlockType()){
            case "Blue":
                block.setImageResource(R.drawable.blueblock);
                break;
            case "Green":
                block.setImageResource(R.drawable.greenblock);
                break;
            case "Yellow":
                block.setImageResource(R.drawable.yellowblock);
                break;
            case "Red":
                block.setImageResource(R.drawable.redblock);
                break;
            case "Gray":
                block.setImageResource(R.drawable.grayblock);
                break;
        }
        block.setCurrentlyClicked(false);
    }

    //Use BFS to find adjacent blocks
    public ArrayList<Block> parseBlocks(Block initialBlock){
        //Final list of adjacent blocks
        ArrayList<Block> selectedBlocks = new ArrayList<>();

        initialBlock.setCurrentlyClicked(true);
        String startingColor = initialBlock.getBlockType();
        selectedBlocks.add(initialBlock);

        LinkedList<Block> bfsList = new LinkedList<>();
        bfsList.add(initialBlock);

        while (!bfsList.isEmpty()) {
            int startingPosition = bfsList.getFirst().getGridLocation();
            //If the starting position is on the left edge
            if ((startingPosition % 7) == 0) {
                //If starting position is 0
                if (startingPosition == 0) {
                    //Add positions 1 and 7 to the queue if not visited already and match colors
                    checkSides(false, true, false, true, selectedBlocks, bfsList, startingColor, startingPosition);
                    bfsList.remove();
                    continue;
                }

                //If starting position is 77
                if (startingPosition == 77) {
                    //Add positions 70 and 78 to the queue if not visited already and match colors
                    checkSides(false, true, true, false, selectedBlocks, bfsList, startingColor, startingPosition);
                    bfsList.remove();
                    continue;
                }

                //Else if it is anywhere else on the left edge
                checkSides(false, true, true, true, selectedBlocks, bfsList, startingColor, startingPosition);
                bfsList.remove();
                continue;
            }

            //If the starting position is on the right edge
            if ((startingPosition % 7) == 6){
                if (startingPosition == 6){
                    checkSides(true, false, false, true, selectedBlocks, bfsList, startingColor, startingPosition);
                    bfsList.remove();
                    continue;
                }
                if (startingPosition == 83){
                    checkSides(true, false, true, false, selectedBlocks, bfsList, startingColor, startingPosition);
                    bfsList.remove();
                    continue;
                }

                //Else if it is anywhere else on the right edge
                checkSides(true, false, true, true, selectedBlocks, bfsList, startingColor, startingPosition);
                bfsList.remove();
                continue;
            }

            //If the starting position is on the top
            if (startingPosition < 7){
                checkSides(true, true, false, true, selectedBlocks, bfsList, startingColor, startingPosition);
                bfsList.remove();
                continue;
            }

            //If the starting position is on the bottom
            if (startingPosition > 76){
                checkSides(true, true, true, false, selectedBlocks, bfsList, startingColor, startingPosition);
                bfsList.remove();
                continue;
            }

            //General algorithm for center blocks
            checkSides(true, true, true, true, selectedBlocks, bfsList, startingColor, startingPosition);
            bfsList.remove();
        }

        //If only initial block is selected, do not highlight
        if (selectedBlocks.size() == 1)
            initialBlock.setCurrentlyClicked(false);

        return selectedBlocks;
    }

    //Given a current block, check if the sides are adjacent and add to queue
    public void checkSides(boolean left, boolean right, boolean up, boolean down, ArrayList<Block> selectedBlocks, LinkedList<Block> bfsList, String startingColor, int startingPosition){
        if (up == true) {
            if (!allBlocks.get(startingPosition - 7).getCurrentlyClicked() && (allBlocks.get(startingPosition - 7).getBlockType().equals(startingColor))) {
                allBlocks.get(startingPosition - 7).setCurrentlyClicked(true);
                bfsList.add(allBlocks.get(startingPosition - 7));
                selectedBlocks.add(allBlocks.get(startingPosition - 7));
            }
        }
        if (down == true) {
            if (!allBlocks.get(startingPosition + 7).getCurrentlyClicked() && (allBlocks.get(startingPosition + 7).getBlockType().equals(startingColor))) {
                allBlocks.get(startingPosition + 7).setCurrentlyClicked(true);
                bfsList.add(allBlocks.get(startingPosition + 7));
                selectedBlocks.add(allBlocks.get(startingPosition + 7));
            }
        }
        if (right == true) {
            if (!allBlocks.get(startingPosition + 1).getCurrentlyClicked() && (allBlocks.get(startingPosition + 1).getBlockType().equals(startingColor))) {
                allBlocks.get(startingPosition + 1).setCurrentlyClicked(true);
                bfsList.add(allBlocks.get(startingPosition + 1));
                selectedBlocks.add(allBlocks.get(startingPosition + 1));
            }
        }
        if (left == true) {
            if (!allBlocks.get(startingPosition - 1).getCurrentlyClicked() && (allBlocks.get(startingPosition - 1).getBlockType().equals(startingColor))) {
                allBlocks.get(startingPosition - 1).setCurrentlyClicked(true);
                bfsList.add(allBlocks.get(startingPosition - 1));
                selectedBlocks.add(allBlocks.get(startingPosition - 1));
            }
        }
    }

    //When blocks fall, update their grid location
    public void updateBlockPositions(){
        ArrayList<Block> updateRowList = (ArrayList<Block>) previousAllBlocks.clone();
        Collections.sort(updateRowList, new BlockCompare());

        while (!updateRowList.isEmpty()) {
            //Get the first element in the queue
            int currPosition = updateRowList.get(0).getGridLocation();

            //If the block is not in the first row or the block is not blank
            while (currPosition > 6){
                if( (allBlocks.get(currPosition-7).getBlockType().equals("Blank")) ) {
                    allBlocks.set(currPosition, new Block(getApplicationContext(), "Blank", currPosition));
                    break;
                }
                Block nextBlock = allBlocks.get(currPosition - 7);
                nextBlock.setGridLocation(currPosition);
                allBlocks.set(currPosition, nextBlock);
                currPosition = currPosition-7;
            }

            if (currPosition <= 6)
                allBlocks.set(currPosition, new Block(getApplicationContext(), "Blank", currPosition));

            updateRowList.remove(0);
        }
    }

    //Check to see if there are any remaining moves left, if not, end level
    public boolean checkRemainingMoves(){
        for (int i=allBlocks.size()-1; i>=0; i--){
            if (allBlocks.get(i).getBlockType().equals("Blank"))
                continue;

            //If block is currently not blank, check to see if it has any moves
            ArrayList<Block> tempBlockCheck = parseBlocks(allBlocks.get(i));
            for (Block b: tempBlockCheck)
                b.setCurrentlyClicked(false);
            if (tempBlockCheck.size() > 1) {
                return true;
            }
        }
        return false;
    }

    //When a new game is started, save old values
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            Block_Singleton.get(getApplicationContext()).setTotalScore(totalScoreCount);
            Block_Singleton.get(getApplicationContext()).setCurrentLevel(currentLevel+1);
            this.recreate();
        }
        else
            finish();
    }


    //Used for testing purposes - Display all block types in a grid format using Log
    public void listAllBlockTypes(){
        int count = 0;
        int blankCount = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (Block b: allBlocks){
            sb.append(b.getBlockType() + b.getGridLocation() + " ");

            if (count == 6){
                count =0;
                sb.append("\n");
                continue;
            }
            if (b.getBlockType().equals("Blank"))
                blankCount++;
            count++;
        }
        Log.d("Grid", sb.toString());
    }

}


