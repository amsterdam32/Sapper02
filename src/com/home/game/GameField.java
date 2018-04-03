package com.home.game;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * The <code>GameField</code> abstract class that define
 * common properties game field
 */
public abstract class GameField extends JPanel {
    /**
     * The value contain size of cell game field
     */
    protected int cellSize;

    /**
     * Container for storage data about content of cells
     */
    protected int[][] contentsOfCells;
    /**
     * Container for storage data about state of cells surface
     */
    protected boolean[][] cellsSurfaceState;
    /**
     * Container for storage data about marks on cells surface
     */
    protected int[][] marksData;

    /**
     * Container for storage data about coordinate of cells on game field
     */
    protected Rectangle2D[][] cellsCoordinate;

    /**
     * Value of column where placed cell with alternative state of surface
     */
    protected int brightCellCol = -1;
    /**
     * Value of row where placed cell with alternative state of surface
     */
    protected int brightCellRow = -1;

    /**
     * Common constructor of abstract class, that sets initial data for class.
     * @param contentsOfCells Container that represent data about content of cells
     * @param cellsSurfaceState Container that represent data about state of cells surface
     * @param dataOfMarks Container that represent data about marks on cell surface
     * @param cellSize Value contain size of cell game field
     */
    public GameField(int[][] contentsOfCells, boolean[][] cellsSurfaceState, int[][] dataOfMarks, int cellSize) {
        this.contentsOfCells = contentsOfCells;
        this.cellsSurfaceState = cellsSurfaceState;
        this.marksData = dataOfMarks;
        this.cellSize = cellSize;
        createRectangles();
    }

    /**
     * Method defines order and  way painting underground layer of game
     * field in the transferred graphics2D object.
     * @param g2 Common Graphics2D object for painting on game field.
     */
    abstract void drawUndergroundLayer(Graphics2D g2);

    /**
     * Method defines order and  way painting layer of ground
     * on game field in the transferred graphics2D object.
     * @param g2 Common Graphics2D object for painting on game field.
     */
    abstract void drawSurfaceLayer(Graphics2D g2);

    /**
     * Method defines  order and  way painting the marks on surface cell
     * of game field in the transferred graphics2D object.
     * @param g2 Common Graphics2D object for painting on game field.
     */
    abstract void drawMarkOnSurface(Graphics2D g2);

    /**
     * Method gets data about changes cell surface of game field.
     * @param surfaceState Container that represent new data about state of cells surface
     * @param marksData Container that represent new data about marks on cell surface
     */
    public void updateDataSurfaceLayer(boolean[][] surfaceState, int[][] marksData){
        this.cellsSurfaceState = surfaceState;
        this.marksData = marksData;
        repaint();
    }

    /**
     * Method updating data about cell on game field with alternative state of surface.
     * @param brightCellRow new value of row where placed cell
     * @param brightCellCol new value of column where placed cell
     */
    public void updateBrightCell(int brightCellRow, int brightCellCol){
        this.brightCellRow = brightCellRow;
        this.brightCellCol = brightCellCol;
        repaint();
    }

    /**
     * Method to fill container that store data about calculated coordinates
     * all cells on game field.
     */
    public void createRectangles(){
        int rowsInField = contentsOfCells.length;
        int colsInField = contentsOfCells[0].length;
        cellsCoordinate = new Rectangle2D[rowsInField][colsInField];
        for (int i = 0, row = 0; i < rowsInField*cellSize; i += cellSize, row++) {
            for (int j = 0, col = 0; j < colsInField*cellSize; j += cellSize, col++) {
                cellsCoordinate[row][col] = new Rectangle2D.Double(j, i, cellSize, cellSize);
            }
        }
    }

    /**
     * Method return container with data about coordinates all cells on game field.
     */
    public Rectangle2D[][] getCellsCoordinate(){
        return cellsCoordinate;
    }
}
