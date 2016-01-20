package itp341.luu.jonathan.finalprojectluujonathan.Models;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

public class Block extends ImageView {
    //Block Types: Red, Blue, Green, Yellow, Gray

    String blockType;
    Integer gridLocation;
    Boolean currentlyClicked;

    public Block(Context context, String BT, Integer GL) {
        super(context);
        blockType = BT;
        gridLocation = GL;
        currentlyClicked = false;
    }

    public String getBlockType() {
        return blockType;
    }

    public void setBlockType(String blockType) {
        this.blockType = blockType;
    }

    public Integer getGridLocation() {
        return gridLocation;
    }

    public void setGridLocation(Integer gridLocation) {
        this.gridLocation = gridLocation;
    }

    public Boolean getCurrentlyClicked() {
        return currentlyClicked;
    }

    public void setCurrentlyClicked(Boolean currentlyClicked) {
        this.currentlyClicked = currentlyClicked;
    }
}
