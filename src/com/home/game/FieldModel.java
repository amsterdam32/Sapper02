package com.home.game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FieldModel implements FieldModelInterface, ActionListener {
    private int height;
    private int width;
    private int numberBombs;
    private final int CELLSIZE = 40;

    private InfoBombsField infoField;
    private UpLayer groundLayer;
    private AnimateLayer explosionLayer;

    private ArrayList<FieldObserver> fieldObservers;

    private boolean isExplosion = false;
    private boolean findBomb = false;
    private BufferedImage frame = null;

    private BufferedImage flagImg = null;
    private BufferedImage questionImg = null;
    private BufferedImage bombImg = null;

    private Timer explosionTimer;
    private Timer clockTimer;
    private int currentTime;

    private boolean gameIsStart = false;

    public FieldModel(){
        this(20,20,40);
    }

    public FieldModel(int height, int width, int numberBombs) {
        this.width = width;
        this.height = height;
        this.numberBombs = numberBombs;
        infoField = new DataBombsField(height, width);
        infoField.setBombs(numberBombs);
        groundLayer = new GroundLayer(height,width, infoField.getInfoFields());
        fieldObservers = new ArrayList<FieldObserver>();
        try {
            explosionLayer = new AnimateExplosionLayer(ImageIO.read(new File("pic/explosionSprite512_512.png")));
            bombImg = ImageIO.read(new File("pic/bomb40_40.png"));
            flagImg = ImageIO.read(new File("pic/flag40_40.png"));
            questionImg = ImageIO.read(new File("pic/questionMark40_40.png"));
        } catch (IOException e) {
            explosionLayer = null;
        }
        startClock();
        //notifyObservers();
    }

    @Override
    public int[][] getInfoBottomLayer() {
        return infoField.getInfoFields();
    }

    @Override
    public boolean[][] getInfoUpLayer() {
        return groundLayer.getDataLayer();
    }

    @Override
    public void setMark(int y, int x) {
        groundLayer.setMark(y,x);
        notifyObservers();
    }

    @Override
    public int[][] getMarkInfo() {
        return groundLayer.getMarkInfo();
    }

    @Override
    public void openCell(int y, int x) {
        gameIsStart = true;
        if (!groundLayer.getDataLayer()[y][x]&&groundLayer.getMarkInfo()[y][x]!=1) {
            groundLayer.openCell(y,x);
            if(groundLayer.isBomb()) {
                startExplosion(x, y);
                groundLayer.takeOffBomb();
            }
        }
        notifyObservers();
    }

    private void startClock(){
        if(clockTimer==null){
            currentTime=0;
            clockTimer = new Timer(100, this);
            clockTimer.start();
        }

    }

    @Override
    public void setBrightCell(int y, int x) {
        groundLayer.setBrightCell(y, x);
    }

    @Override
    public int[] getBrightCell() {
        return groundLayer.getBrightCell();
    }

    @Override
    public int getHeight() {
        return height;
    }
    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getNumberBombs() {
        return numberBombs;
    }

    @Override
    public int getCellSize() {
        return CELLSIZE;
    }

    @Override
    public BufferedImage getBombsImg() {
        return bombImg;
    }

    @Override
    public BufferedImage getFlagImg() {
        return flagImg;
    }

    @Override
    public BufferedImage getQuestionImg() {
        return questionImg;
    }

    @Override
    public void registerObserver(FieldObserver o) {
        fieldObservers.add(o);
    }

    @Override
    public void removeObserver(FieldObserver o) {
        int index = fieldObservers.indexOf(o);
        if(index>=0) fieldObservers.remove(index);
    }

    @Override
    public void notifyObservers() {
        for(FieldObserver item : fieldObservers){
            item.update();
        }
    }

    @Override
    public void startExplosion(int x, int y) {

        try {
            explosionLayer = new AnimateExplosionLayer(ImageIO.read(new File("pic/explosionSprite512_512.png")));
            explosionLayer.setExplosionState(true);
            explosionLayer.setExplosionCoordinates(x,y, CELLSIZE);
            if(explosionTimer !=null&& explosionTimer.isRunning()) explosionTimer.stop();
            explosionTimer = new Timer(60, this);
            explosionTimer.start();

        } catch (IOException e) {
            explosionLayer = null;
        }

    }

    @Override
    public int[] getExplosionCoordinates() {
        return explosionLayer.getExplosionCoordinates();
    }

    @Override
    public boolean getExplosionState() {
        return explosionLayer.isExplosion();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(explosionTimer)) {
            if(explosionLayer.isExplosion()) {
                explosionLayer.nextFrame();
            }
            else {
                explosionTimer.stop();
                setBombFoundState(true);
            }
        }
        else if(e.getSource().equals(clockTimer)&&getGameState()) {
            if(currentTime<9999) currentTime++;
        }
        notifyObservers();
    }

    @Override
    public int getCurrentTime() {
        return currentTime;
    }

    @Override
    public BufferedImage nextFrame() {
        return explosionLayer.nextFrame();
    }

    @Override
    public boolean checkBombFound() {
        return findBomb;
    }

    @Override
    public void setBombFoundState(boolean state) {
        findBomb = state;
    }

    @Override
    public boolean getGameState() {
        return gameIsStart;
    }
}
