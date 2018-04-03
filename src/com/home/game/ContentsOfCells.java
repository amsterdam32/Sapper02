package com.home.game;

/**
 * The <code>ContentsOfCells<code> is a interface
 * that sets common methods install bombs
 * and getting information about their placing.
 */
public interface ContentsOfCells {
    /**
     * The method of placing bombs on the game field.
     * @param numberBombs transmitting quantity all bombs
     */
    void setBombs(int numberBombs);

    /**
     * Method sets contents all cells on game field relative of added bombs.
     */
    void calculateContentsOfCells();

    /**
     * Return data array containing information about cells
     * that contain bombs and other cells of game field.
     * @return data array
     */
    int[][] getContentsOfCells();
}
