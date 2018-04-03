package com.home.game;

import java.awt.image.BufferedImage;

/**
 * The <code>Animate<code> is a common interface,
 * that define methods and states for getting subimages,
 * from sprite image and getting coordinates
 * for drawing this the subimage relative
 * calling point and self subimages size.
 */
public interface Animate {
    /**
     * @return  array that contains coordinates for drawing subimage
     */
    int[] getCoordinatesAction();

    /**
     * The method calculate new coordinates relative game field for painting queue images based on input params.
     * @param row value of row where placed cell that calling this event
     * @param col value of column where placed cell that calling this event
     * @param cellSize size the cell selected for game field
     */
    void setCoordinatesAction(int row, int col, int cellSize);

    /**
     *Sets state for inner flag, that may service sign achievement last subimage.
     * @param state current state
     */
    void setStateAction(boolean state);

    /**
     *Checks state the inner flag.
     * @return true or false state
     */
    boolean isAction();


    /**
     *Returns next subimage from sprite image
     * @return subimage
     */
    BufferedImage nextFrameAction();
}
