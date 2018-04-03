package com.home.game;
/**
 * The <code>StateSurfaceOfCells<code> is a common interface,
 * that define methods for setting and getting states the surface of cells.
 * Also setting of some marks on surface of cells
 * and getting information about finding of bombs.
 */

public interface StateSurfaceOfCells {
    /**
     * The method is used to open a field cell in the transmitted coordinates.
     * @param row new value of row where placed cell
     * @param col new value of col where placed cell
     */
    void openCell(int row, int col);

    /**
     * Return quantity closed cells in the field.
     */
    int getQuantityCloseCells();

    /**
     * The method is used for updating data about cell on game field with alternative state of surface.
     * @param row new value of row where placed cell
     * @param col new value of col where placed cell
     */
    void setBrightCell(int row, int col);

    /**
     * The method is used for getting value of column where placed cell with alternative state.
     */
    int getBrightCellCol();
    /**
     * The method is used for getting value of row where placed cell with alternative state.
     */
    int getBrightCellRow();

    /**
     * The method is used for setting mark on surface selected cell.
     * @param row new value of row where placed cell
     * @param col new value of column where placed cell
     */
    void setMark(int row, int col);

    /**
     * Return data array contains information about marks that was sets on surface of cells.
     */
    int[][] getDataOfMarks();

    /**
     * Return current value of certain marks that was set on the field.
     */
    int getQuantityFlagMarks();

    /**
     * Return state of the flag, that indicates existence bomb.
     */
    boolean isBomb();

    /**
     * Sets states of the flag.
     */
    void setBomb();

    /**
     * Take off the state of the flag.
     */
    void takeOffBomb();

    /**
     * Return data array about state the surface of cells.
     */
    boolean[][] getCellsSurfaceState();

}
