package com.home.game;

import java.awt.*;

/**
 * The <code>GameFieldWithoutImages</code> class
 * that implements common abstract class <code>GameField</code>
 * and presents the simple graphical version
 * without images for any objects game field.
 * @see com.home.game.GameField
 */
public class GameFieldWithoutImages extends GameField {

    public GameFieldWithoutImages(int[][] contentsOfCells, boolean[][] cellsSurfaceState, int[][] dataOfMarks, int cellSize) {
        super(contentsOfCells, cellsSurfaceState, dataOfMarks, cellSize);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 36);
        g2.setFont(font);

        drawUndergroundLayer(g2);
        drawSurfaceLayer(g2);
        drawMarkOnSurface(g2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void drawUndergroundLayer(Graphics2D g2){

        for (int i = 0; i < cellsCoordinate.length; i++) {
            for (int j = 0; j < cellsCoordinate[i].length; j++) {
                //draw ground
                g2.setColor(new Color(200,128,50));
                g2.fillRect((int)cellsCoordinate[i][j].getX(), (int)cellsCoordinate[i][j].getY(),
                        (int)cellsCoordinate[i][j].getWidth(), (int)cellsCoordinate[i][j].getHeight());

                //draw grid
                g2.setColor(Color.BLACK);
                g2.draw(cellsCoordinate[i][j]);

                //draw bombs and write numbers

                int xNumberCoordinate = (int) cellsCoordinate[i][j].getX() + cellSize / 2 - 8;
                int yNumberCoordinate = (int) cellsCoordinate[i][j].getY() + cellSize / 2 + 14;

                if (contentsOfCells[i][j] < 0) {
                    g2.setColor(Color.RED);
                    g2.drawString("*", xNumberCoordinate+2, yNumberCoordinate+6);
                }

                else if (contentsOfCells[i][j] == 0) ;
                else if(contentsOfCells[i][j] == 1){
                    g2.setColor(new Color(86,167,241));
                    g2.drawString(contentsOfCells[i][j] + "", xNumberCoordinate, yNumberCoordinate);
                }
                else if(contentsOfCells[i][j] == 2){
                    g2.setColor(new Color(105,193,5));
                    g2.drawString(contentsOfCells[i][j] + "", xNumberCoordinate, yNumberCoordinate);
                }
                else if(contentsOfCells[i][j] == 3){
                    g2.setColor(Color.RED);
                    g2.drawString(contentsOfCells[i][j] + "", xNumberCoordinate, yNumberCoordinate);
                }
                else if(contentsOfCells[i][j] == 4){
                    g2.setColor(new Color(200,154,200));
                    g2.drawString(contentsOfCells[i][j] + "", xNumberCoordinate, yNumberCoordinate);
                }
                else if(contentsOfCells[i][j] == 5){
                    g2.setColor(new Color(200,45,121));
                    g2.drawString(contentsOfCells[i][j] + "", xNumberCoordinate, yNumberCoordinate);
                }
                else {
                    g2.setColor(new Color(180,195,200));
                    g2.drawString(contentsOfCells[i][j] + "", xNumberCoordinate, yNumberCoordinate);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void drawSurfaceLayer(Graphics2D g2){
        for (int i = 0; i < cellsCoordinate.length; i++) {
            for (int j = 0; j < cellsCoordinate[i].length; j++) {
                if (null !=  cellsCoordinate[i][j] &&
                        cellsSurfaceState[i][j] == State.CLOSED.getValue()) {
                    //Drawing grass
                    if (i == brightCellRow && j == brightCellCol) {
                        g2.setColor(new Color(170,220,35));
                        g2.fillRect((int)cellsCoordinate[i][j].getX(), (int)cellsCoordinate[i][j].getY(),
                                (int)cellsCoordinate[i][j].getWidth(), (int)cellsCoordinate[i][j].getHeight());
                    } else {
                        g2.setColor(Color.RED);
                        g2.setColor(new Color(125,175,35));
                        g2.fillRect((int)cellsCoordinate[i][j].getX(), (int)cellsCoordinate[i][j].getY(),
                                (int)cellsCoordinate[i][j].getWidth(), (int)cellsCoordinate[i][j].getHeight());
                    }

                    //draw grid
                    g2.setColor(Color.BLACK);
                    g2.draw(cellsCoordinate[i][j]);

                    //drawing pseudo depth for cells
                    g2.setColor(new Color(110,65,45));
                    g2.setStroke(new BasicStroke(4));
                    g2.drawLine((int) cellsCoordinate[i][j].getX()+2,
                            (int)(cellsCoordinate[i][j].getY()+2+ cellsCoordinate[i][j].getHeight()),
                            (int)(cellsCoordinate[i][j].getX()+2+ cellsCoordinate[i][j].getWidth()),
                            (int)(cellsCoordinate[i][j].getY()+2+ cellsCoordinate[i][j].getHeight()));
                    g2.drawLine((int)(cellsCoordinate[i][j].getX()+2+ cellsCoordinate[i][j].getWidth()),
                            (int) cellsCoordinate[i][j].getY()+2,
                            (int)(cellsCoordinate[i][j].getX()+2+ cellsCoordinate[i][j].getWidth()),
                            (int)(cellsCoordinate[i][j].getY()+ cellsCoordinate[i][j].getHeight()));
                    g2.setStroke(new BasicStroke(1));
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void drawMarkOnSurface(Graphics2D g2){
        for (int i = 0; i < cellsCoordinate.length; i++) {
            for (int j = 0; j < cellsCoordinate[i].length; j++) {

                int xMarkCoordinate = (int) cellsCoordinate[i][j].getX() + cellSize / 2 - 10;
                int yMarkCoordinate = (int) cellsCoordinate[i][j].getY() + cellSize / 2 + 14;

                if (marksData[i][j] == Mark.FLAG.getValue()) {
                    g2.setColor(Color.DARK_GRAY);
                    g2.drawString("V", xMarkCoordinate, yMarkCoordinate);
                }
                else if(marksData[i][j] == Mark.QUESTION.getValue() && cellsSurfaceState[i][j] == State.CLOSED.getValue()) {
                    g2.setColor(Color.WHITE);
                    g2.drawString("?", xMarkCoordinate, yMarkCoordinate);
                }
            }
        }
    }

}
