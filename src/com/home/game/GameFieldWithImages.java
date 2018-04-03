package com.home.game;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The <code>GameFieldWithImages</code> class that implements
 * common abstract class <code>GameField</code> and presenting
 * implementation with images for underground and surface layers
 * and also use images for bomb, all types marks and animation explosion.
 * @see com.home.game.GameField
 */
public class GameFieldWithImages extends GameField {

    /**
     * The flag reflect state of explosion event on game field.
     */
    private boolean explosionIsAction = false;

    /**
     * Container for storage coordinates of cell where occurred a explosion.
     */
    private int[] explosionCoordinate;

    /**
     * Container for storage image of marks FLAG.
     */
    private BufferedImage flagImg = null;
    /**
     * Container for storage image marks QUESTION.
     */
    private BufferedImage questionImg = null;
    /**
     * Container for storage image of BOMB.
     */
    private BufferedImage bombImg = null;

    /**
     * Containers for storage image of a surface game field.
     */
    private BufferedImage[][] surfaceImgPuzzle = null;
    /**
     * Containers for storage image of an alternative surface game field.
     */
    private BufferedImage[][] surfaceLightImgPuzzle = null;
    /**
     * Containers for storage image of a underground layer.
     */
    private BufferedImage[][] underGroundImgPuzzle = null;

    /**
     * Container for storage frame of explosion.
     */
    private BufferedImage explosionFrameImg = null;

    public GameFieldWithImages(int[][] contentsOfCells, boolean[][] cellsSurfaceState, int[][] dataOfMarks, int cellSize) {
        super(contentsOfCells, cellsSurfaceState, dataOfMarks, cellSize);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        drawUndergroundLayer(g2);
        drawSurfaceLayer(g2);
        drawMarkOnSurface(g2);
        if(explosionIsAction) drawExplosion(g2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawUndergroundLayer(Graphics2D g2){
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 36);
        g2.setFont(font);

        for (int i = 0; i < cellsCoordinate.length; i++) {
            for (int j = 0; j < cellsCoordinate[i].length; j++) {
                //draw ground
                g2.setColor(new Color(200,128,18));
                g2.drawImage(underGroundImgPuzzle[i][j],
                        (int) cellsCoordinate[i][j].getX(), (int) cellsCoordinate[i][j].getY(),
                        cellSize,cellSize,
                        this);

                //draw grid
                g2.setColor(Color.BLACK);
                g2.draw(cellsCoordinate[i][j]);

                //draw bombs and write numbers
                int xNumberCoordinate = (int) cellsCoordinate[i][j].getX() + cellSize / 2 - 8;
                int yNumberCoordinate = (int) cellsCoordinate[i][j].getY() + cellSize / 2 + 14;

                if (contentsOfCells[i][j] < 0) {
                    if(null!=bombImg) {
                        g2.drawImage(bombImg, (int) cellsCoordinate[i][j].getX()+1,
                                (int) cellsCoordinate[i][j].getY()+4,
                                (int) cellsCoordinate[i][j].getWidth()-4,
                                (int) cellsCoordinate[i][j].getHeight()-4,
                                this);
                    }
                    else {
                        g2.setColor(Color.RED);
                        g2.drawString("*", xNumberCoordinate+2, yNumberCoordinate+6);
                    }
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
    public void drawSurfaceLayer(Graphics2D g2){
        for (int row = 0; row < cellsCoordinate.length; row++) {
            for (int col = 0; col < cellsCoordinate[row].length; col++) {
                if (null != cellsCoordinate[row][col] &&
                        cellsSurfaceState[row][col] == State.CLOSED.getValue()) {
                    //Drawing grass
                    if (row == brightCellRow && col == brightCellCol) {
                        g2.drawImage(surfaceLightImgPuzzle[row][col],
                                (int) cellsCoordinate[row][col].getX(), (int) cellsCoordinate[row][col].getY(),
                                cellSize, cellSize,
                                this);
                    } else {
                        g2.drawImage(surfaceImgPuzzle[row][col],
                                (int) cellsCoordinate[row][col].getX(), (int) cellsCoordinate[row][col].getY(),
                                cellSize, cellSize,
                                this);
                    }

                    //drawing pseudo depth for cells
                    g2.setColor(new Color(110,65,45));
                    g2.setStroke(new BasicStroke(4));
                    g2.drawLine((int) cellsCoordinate[row][col].getX()+2,
                            (int)(cellsCoordinate[row][col].getY()+2+ cellsCoordinate[row][col].getHeight()),
                            (int)(cellsCoordinate[row][col].getX()+2+ cellsCoordinate[row][col].getWidth()),
                            (int)(cellsCoordinate[row][col].getY()+2+ cellsCoordinate[row][col].getHeight()));
                    g2.drawLine((int)(cellsCoordinate[row][col].getX()+2+ cellsCoordinate[row][col].getWidth()),
                            (int) cellsCoordinate[row][col].getY()+2,
                            (int)(cellsCoordinate[row][col].getX()+2+ cellsCoordinate[row][col].getWidth()),
                            (int)(cellsCoordinate[row][col].getY()+ cellsCoordinate[row][col].getHeight()));
                }
            }
        }
    }

    /**
     * Method that responsible for drawing one frame explosion in some coordinates on game field
     * @param g2 Common Graphics2D object for painting on game field.
     */
    private void drawExplosion(Graphics2D g2){
        int leftCornerExplosionX = explosionCoordinate[0];
        int leftCornerExplosionY = explosionCoordinate[1];
        int rightCornerExplosionX = explosionCoordinate[2];
        int rightCornerExplosionY = explosionCoordinate[3];

        g2.drawImage(explosionFrameImg, leftCornerExplosionX,leftCornerExplosionY,
                rightCornerExplosionX, rightCornerExplosionY,
                0, 0, 128, 128,
                getParent());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawMarkOnSurface(Graphics2D g2){
        for (int i = 0; i < cellsCoordinate.length; i++) {
            for (int j = 0; j < cellsCoordinate[i].length; j++) {

                int x1 = (int) cellsCoordinate[i][j].getX() + cellSize / 2 - 8;
                int y1 = (int) cellsCoordinate[i][j].getY() + cellSize / 2 + 14;

                if (marksData[i][j] == Mark.FLAG.getValue()) {
                    if (null != flagImg) {
                        g2.drawImage(flagImg, (int) cellsCoordinate[i][j].getX() + 1,
                                (int) cellsCoordinate[i][j].getY() + 4,
                                (int) cellsCoordinate[i][j].getWidth() - 4,
                                (int) cellsCoordinate[i][j].getHeight() - 4,
                                this);
                    }
                }
                else if(marksData[i][j] == Mark.QUESTION.getValue() && cellsSurfaceState[i][j] == State.CLOSED.getValue()) {
                    if (null != questionImg) {
                        g2.drawImage(questionImg, (int) cellsCoordinate[i][j].getX() + 1,
                                (int) cellsCoordinate[i][j].getY() + 4,
                                (int) cellsCoordinate[i][j].getWidth() - 4,
                                (int) cellsCoordinate[i][j].getHeight() - 4,
                                this);
                    }
                }
            }
        }
    }

    /**
     * Method updating data for realisation next frame of animation explosion.
     * @param coordinates coordinates relative game field where occurre animation
     * @param explosionIsAction state indicate that event occurre or no.
     * @param explosionFrame new frame with images this event
     */
    public void updateAnimateLayer(int[] coordinates, boolean explosionIsAction, BufferedImage explosionFrame){
        explosionCoordinate = coordinates;
        this.explosionIsAction = explosionIsAction;
        explosionFrameImg = explosionFrame;
        repaint();
    }

    /**
     * Method sets images for all elements game field.
     * @param bombImg image of bomb
     * @param flagImg image of flag mark
     * @param questionImg image of question mark
     * @param surfaceImagePuzzle image of surface game field
     * @param groundLightImagePuzzle image of surface game field in alternative state
     * @param underGroundImagePuzzle image of game field without layer of surface
     */
    public void setImgElements(BufferedImage bombImg, BufferedImage flagImg, BufferedImage questionImg,
                               BufferedImage[][] surfaceImagePuzzle, BufferedImage[][] groundLightImagePuzzle,
                               BufferedImage[][] underGroundImagePuzzle) {
        this.bombImg = bombImg;
        this.flagImg = flagImg;
        this.questionImg = questionImg;
        this.surfaceImgPuzzle = surfaceImagePuzzle;
        this.surfaceLightImgPuzzle = groundLightImagePuzzle;
        this.underGroundImgPuzzle = underGroundImagePuzzle;
    }
}
