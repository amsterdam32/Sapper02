package com.home.game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * The <code>FieldModel</code> class that describe information model of game field,
 * and includes all information about game field state
 * and various methods for manipulating this.
 */
public class FieldModel implements Observable, ActionListener {
    /**
     * Container for each observers that registers.
     */
    private ArrayList<Observer> observers;
    /**
     * Value of rows in the game field.
     */
    private int rowsInField;
    /**
     * Value of columns in the game field.
     */
    private int colsInField;
    /**
     * Value of quantity bombs on the game field.
     */
    private int quantityBombs;
    /**
     * Size side of square what represents of the game field cell.
     */
    private final int CELL_SIZE = 40;

    /**
     * The flag indicates success upload all images.
     */
    private boolean fieldWithImages = true;

    /**
     * Object store data about contents of cells
     */
    private ContentsOfCells contentsOfCells;
    /**
     * Object store information about states surface of cells
     */
    private StateSurfaceOfCells stateSurfaceOfCells;
    /**
     * Object for handling for animations explosion.
     */
    private Animate animateExplosion;
    /**
     * Object for handling for image underground layer of game field.
     */
    private TilesOfImage tilesOfUndergroundImg;
    /**
     * Object for handling for image surface of game field.
     */
    private TilesOfImage tilesOfSurfaceImg;
    /**
     * Object for handling for alternatives image surface of game field.
     */
    private TilesOfImage tilesOfLightSurfaceImg;

    /**
     * Flag for indicate event about explosion.
     */
    private boolean wasExplosion = false;
    /**
     * Flag for indicate the bomb was found.
     */
    private boolean findBomb = false;

    /**
     * Container for image mark of flag.
     */
    private BufferedImage flagImg = null;
    /**
     * Container for image mark of question.
     */
    private BufferedImage questionImg = null;
    /**
     * Container for image of bomb.
     */
    private BufferedImage bombImg = null;
    /**
     * Container for image surface of game field.
     */
    private BufferedImage surfaceImg = null;
    /**
     * Container for alternative a image surface of game field.
     */
    private BufferedImage lightSurfaceImg = null;
    /**
     * Container for image underground layer of game field.
     */
    private BufferedImage undergroundImg = null;
    /**
     * value of scale for underground image
     */
    private int undergroundImgScale;
    /**
     * value of scale for surface image
     */
    private int surfaceImgScale;

    /**
     * Timer for counting time between frames with animation explosion.
     */
    private Timer explosionTimer;
    /**
     * Value delay between frames with animation explosion.
     */
    private int explosionFrameDelay = 63;
    /**
     * Timer for counting game time.
     */
    private Timer clockTimer;
    /**
     * Value step game time.
     */
    private final int CLOCK_TIMER_DELAY = 100;
    /**
     * Current value game time.
     */
    private int currentTime;

    /**
     * Flag for indicate event about starts game.
     */
    private boolean gameIsStart = false;
    /**
     * Flag for indicate event about ends game.
     */
    private boolean gameIsEnd = false;

    public FieldModel(int rowsInField, int colsInField, int quantityBombs) {
        this.colsInField = colsInField;
        this.rowsInField = rowsInField;
        this.quantityBombs = quantityBombs;

        contentsOfCells = new ContentsOfCellsGame(rowsInField, colsInField);
        contentsOfCells.setBombs(quantityBombs);
        contentsOfCells.calculateContentsOfCells();

        stateSurfaceOfCells = new StateSurfaceOfCellsGame(rowsInField, colsInField, contentsOfCells.getContentsOfCells());
        observers = new ArrayList();
        startClock();
    }

    /**
     * Method for upload images for all objects game.
     * @throws IOException
     */
    public void loadImg() throws IOException {
            try {
                URL imgExplosionPath = ClassLoader.getSystemResource("img/explosionSprite512_512.png");
                animateExplosion = new AnimateExplosion(ImageIO.read(imgExplosionPath), 4, 4);

                URL imgBombPath = ClassLoader.getSystemResource("img/bomb40_40.png");
                bombImg = ImageIO.read(imgBombPath);

                URL imgFlagPath = ClassLoader.getSystemResource("img/flag40_40.png");
                flagImg = ImageIO.read(imgFlagPath);

                URL imgQuestionPath = ClassLoader.getSystemResource("img/questionMark40_40.png");
                questionImg = ImageIO.read(imgQuestionPath);

                URL imgUndergroundPath = ClassLoader.getSystemResource("img/ground600_600.jpg");
                undergroundImg = ImageIO.read(imgUndergroundPath);

                URL imgSurfacePath = ClassLoader.getSystemResource("img/grass600_600.jpg");
                surfaceImg = ImageIO.read(imgSurfacePath);

                URL imgLightSurfacePath = ClassLoader.getSystemResource("img/grassLight600_600.jpg");
                lightSurfaceImg = ImageIO.read(imgLightSurfacePath);

                tilesOfSurfaceImg = new TilesOfImageObjectsGame(surfaceImg, rowsInField, colsInField, getSurfaceImgScale());
                tilesOfLightSurfaceImg = new TilesOfImageObjectsGame(lightSurfaceImg, rowsInField, colsInField, getSurfaceImgScale());
                tilesOfUndergroundImg = new TilesOfImageObjectsGame(undergroundImg, rowsInField, colsInField, getUndergroundImgScale());
            }
            catch (IOException e) {
                fieldWithImages = false;
                throw new IOException();
            }
    }

    /**
     * Method indicates about successfulness or not for uploading images.
     * @return boolean value true - success, false - no
     */
    public boolean getLoadImagesState(){
        return fieldWithImages;
    }

    /**
     * Method return array data about contents of cells
     * @return - digitals from 1 to 8 indicate neighbourhood with bombs
     *         <br> - 0 is empty cells</br>
     *         <br> - all negative digital indicate on bomb</br>
     */
    public int[][] getContentsOfCells() {
        return contentsOfCells.getContentsOfCells();
    }

    /**
     * Method return array data with information about states surface of cells
     * @return       - true - opened
     *          <br> - false - closed</br>
     */
    public boolean[][] getCellsSurfaceState() {
        return stateSurfaceOfCells.getCellsSurfaceState();
    }

    /**
     * Method returns array of images surface for each cells of game field.
     */
    public BufferedImage[][] getTilesOfSurfaceImg() {
        return tilesOfSurfaceImg.getTilesOfImg();
    }

    /**
     * Method returns array of alternatives images surface for each cells of game field.
     */
    public BufferedImage[][] getTilesOfLightSurfaceImg() {
        return tilesOfLightSurfaceImg.getTilesOfImg();
    }

    /**
     * Method returns array of images underground layer of game field under each cells.
     */
    public BufferedImage[][] getTilesOfUndergroundImg() {
        return tilesOfUndergroundImg.getTilesOfImg();
    }

    /**
     * Method gets value of scale for surface image
     */
    public int getSurfaceImgScale() {
        return surfaceImgScale;
    }

    /**
     * Method sets value of scale for surface image
     * @param scale
     */
    public void setSurfaceImgScale(int scale) {
        this.surfaceImgScale = scale;
    }
    /**
     * Method gets value of scale for underground image
     */
    public int getUndergroundImgScale() {
        return undergroundImgScale;
    }
    /**
     * Method sets value of scale for underground image
     * @param scale
     */
    public void setUndergroundIgmScale(int scale) {
        this.undergroundImgScale = scale;
    }

    /**
     * The method is used for setting mark on surface selected cell.
     * @param row - new value of row where placed cell
     * @param col - new value of column where placed cell
     */
    public void setMark(int row, int col) {
        stateSurfaceOfCells.setMark(row,col);
        notifyObserversData();
    }

    /**
     * Method return data array contains information about marks
     * that was sets on surface of cells.
     * @return      - 0 equal cell without marks
     *         <br> - 1 equal mark of flag</br>
     *         <br> - 2 equal mark of question</br>
     */
    public int[][] getDataOfMarks() {
        return stateSurfaceOfCells.getDataOfMarks();
    }

    /**
     * Method returns quantity marks of flag that sets on game field
     * relative total value of bombs if it more > -99
     */
    public int getQuantityMarkedBombs() {
        if(getQuantityBombs() - stateSurfaceOfCells.getQuantityFlagMarks() > -100)
            return getQuantityBombs() - stateSurfaceOfCells.getQuantityFlagMarks();
        else return -99;
    }

    /**
     * Method return boolean value about winning.
     */
    public boolean checkGameStateWin() {
        if (stateSurfaceOfCells.getQuantityCloseCells() == getQuantityBombs() &&
                !wasExplosion && !gameIsEnd) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method sets state about end of game.
     * @param state
     */
    public void setGameIsEnd(boolean state) {
        gameIsEnd = state;
    }

    /**
     * The method is used to open a field cell in the transmitted coordinates and
     * reaction on possible event linked with this.
     * @param row - new value of row where placed cell
     * @param col - new value of col where placed cell
     */
    public void openCell(int row, int col) {
        gameIsStart = true;
        if (stateSurfaceOfCells.getCellsSurfaceState()[row][col] == State.CLOSED.getValue()&&
                stateSurfaceOfCells.getDataOfMarks()[row][col]!=Mark.FLAG.getValue()) {
            stateSurfaceOfCells.openCell(row,col);
            if(stateSurfaceOfCells.isBomb()) {
                wasExplosion = true;
                if(fieldWithImages)startExplosion(row, col);
                else setBombFoundState(true);
                stateSurfaceOfCells.takeOffBomb();
            }
        }
        notifyObserversData();
    }

    /**
     * Start game timer.
     */
    private void startClock(){
        if(clockTimer==null){
            currentTime=0;
            clockTimer = new Timer(CLOCK_TIMER_DELAY, this);
            clockTimer.start();
        }
    }

    /**
     * Method sets coordinate for cell on game field with alternative state of surface.
     * @param row - new value of row where placed cell
     * @param col - new value of col where placed cell
     */
    public void setBrightCell(int row, int col) {
        stateSurfaceOfCells.setBrightCell(row, col);
    }

    /**
     * Method gets value of column where placed cell with alternative state.
     */
    public int getBrightCellCol() {
        return stateSurfaceOfCells.getBrightCellCol();
    }

    /**
     * Method gets value of row where placed cell with alternative state.
     */
    public int getBrightCellRow() {
        return stateSurfaceOfCells.getBrightCellRow();
    }

    /**
     * Method gets value of columns in game field.
     */
    public int getColsInField() {
        return colsInField;
    }

    /**
     * Method gets value of rows in game field.
     */
    public int getRowsInField() {
        return rowsInField;
    }

    /**
     * Method gets value of quantity bombs on game field.
     */
    public int getQuantityBombs() {
        return quantityBombs;
    }

    /**
     * Method gets value of size of cell for game field.
     */
    public int getCellSize() {
        return CELL_SIZE;
    }

    /**
     * Method gets image bombs.
     */
    public BufferedImage getBombsImg() {
        return bombImg;
    }

    /**
     * Method gets image mark of flag.
     */
    public BufferedImage getFlagImg() {
        return flagImg;
    }

    /**
     * Method gets image mark of question.
     */
    public BufferedImage getQuestionImg() {
        return questionImg;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void removeObserver(Observer o) {
        int index = observers.indexOf(o);
        if(index>=0) observers.remove(index);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyObserversData() {
        for(Observer item : observers){
            item.updatePaint();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyObserversAnimate() {
        for(Observer item : observers){
            item.updateAnimate();
        }
    }

    /**
     * Method for  launch event that describes of animation explosion in the transmitted coordinates.
     * @param row - value of row where placed cell
     * @param col - value of col where placed cell
     */
    public void startExplosion(int row, int col) {
        animateExplosion.setStateAction(true);
        animateExplosion.setCoordinatesAction(row,col, CELL_SIZE);
        if(explosionTimer !=null&& explosionTimer.isRunning()) explosionTimer.stop();
        explosionTimer = new Timer(explosionFrameDelay, this);
        explosionTimer.start();
    }

    /**
     * Method return array having a lenght four number that contains
     * coordinates X1,Y1 (left corner) and X2,Y2 (right corner) for drawing subimage
     */
    public int[] getExplosionCoordinates() {
        return animateExplosion.getCoordinatesAction();
    }

    /**
     * The method return true until occurring getting the new frames action.
     * @return true if has next frame
     */
    public boolean getExplosionState() {
        return animateExplosion.isAction();
    }

    /**
     * Method used for handling events that timers generating.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(explosionTimer)) {
            if(!animateExplosion.isAction()) {
                explosionTimer.stop();
                setBombFoundState(true);
            }
            notifyObserversAnimate();
        }
        if(e.getSource().equals(clockTimer)&&getGameState()) {
            if(currentTime<9999) currentTime++;
            notifyObserversData();
        }

    }

    /**
     * Method gets current value game time.
     */
    public int getCurrentTime() {
        return currentTime;
    }

    /**
     * Method use for stopping game timer.
     */
    public void stopTime() {
       clockTimer.stop();
    }

    /**
     * Method returns image with next frame of animation explosion.
     */
    public BufferedImage nextFrame() {
        return animateExplosion.nextFrameAction();
    }

    /**
     * Method check flag what indicate the bomb was found.
     */
    public boolean checkBombFound() {
        return findBomb;
    }

    /**
     * Method sets state about finding bomb.
     */
    public void setBombFoundState(boolean state) {
        findBomb = state;
    }

    /**
     * Method gets pointer event about starts game.
     */
    public boolean getGameState() {
        return gameIsStart;
    }
}
