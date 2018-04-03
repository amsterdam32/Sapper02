package com.home.game;

import java.awt.image.BufferedImage;

/**
 * The <code>AnimateExplosion</code> class that implements
 * the animation of the explosion
 * @see Animate
 */
public class AnimateExplosion implements Animate {
    /**
     * A container for the image sprite.
     */
    private BufferedImage explosionSprite;
    /**
     * A container for current frame.
     */
    private BufferedImage frame = null;
    /**
     *  Flag illustrating the state of the getting frames with action process.
     */
    private boolean explosion = false;
    /**
     *  Array to store coordinates frame relative game field.
     */
    private int[] coordinates = new int[4];

    /**
     * A counter frames by height relative image sprite structure.
     */
    private int frameCountY = 0;
    /**
     * A counter frames by width relative image sprite structure.
     */
    private int frameCountX = 0;
    /**
     * Value contain quantity rows in the image sprite.
     */
    private int rowInSprite;
    /**
     * Value contain quantity columns in the image sprite.
     */
    private int colInSprite;
    /**
     * Value contain width frames from image sprite.
     */
    private int widthFrame;
    /**
     * Value contain height frames from image sprite.
     */
    private int heightFrame;

    /** Constructs a <code>AnimateExplosion</code> A constructor with setting
     * image sprite of explosion and some parameters this sprite
     * @param explosionSprite image sprite of explosion
     * @param rowInSprite quantity rows in the image sprite
     * @param colInSprite quantity width frames from image sprite
     */
    public AnimateExplosion(BufferedImage explosionSprite, int rowInSprite, int colInSprite) {
        this.explosionSprite = explosionSprite;
        this.rowInSprite = rowInSprite;
        this.colInSprite = colInSprite;
        calculateParametersFrame();
    }

    /**
     * Method defining width and height frame from image sprite.
     */
    private void calculateParametersFrame(){
        widthFrame = explosionSprite.getWidth()/colInSprite;
        heightFrame = explosionSprite.getHeight()/rowInSprite;
    }


    @Override
    public void setCoordinatesAction(int row, int col, int cellSize){
        coordinates[0] = col * cellSize + cellSize / 2 - widthFrame / 2;
        coordinates[1] = row * cellSize + cellSize / 2 - heightFrame / 2;
        coordinates[2] = col * cellSize + cellSize / 2 + widthFrame / 2;
        coordinates[3] = row * cellSize + cellSize / 2 + heightFrame / 2;
    }

    /**
     * Method return array having a lenght four number that contains
     * coordinates X1,Y1 (left corner) and X2,Y2 (right corner) for drawing subimage
     * @return array {X1,Y1,X2,Y2}
     */
    @Override
    public int[] getCoordinatesAction() {
        return coordinates;
    }

    /**
     * The method intended for sets the action flag state
     * and start/stop forming frames from image sprite.
     * @param state current state
     */
    @Override
    public void setStateAction(boolean state) {
        explosion = state;
    }

    /**
     * The method return true until occurring getting the new frames action.
     * @return true if has next frame
     */
    @Override
    public boolean isAction() {
        return explosion;
    }

    @Override
    public BufferedImage nextFrameAction(){
        frame = explosionSprite.getSubimage(frameCountX * widthFrame, frameCountY * heightFrame, widthFrame, heightFrame);
        frameCountX++;
        if(frameCountX == colInSprite){
            frameCountY++;
            frameCountX = 0;
        }
        if(frameCountY == rowInSprite){
            frameCountY = 0;
            frameCountX = 0;
            setStateAction(false);
        }
        return frame;
    }
}