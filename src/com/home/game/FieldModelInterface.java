package com.home.game;

import java.awt.image.BufferedImage;

public interface FieldModelInterface {
    public int getHeight();
    public int getWidth();
    public int getNumberBombs();
    public int getCellSize();
    public BufferedImage getBombsImg();
    public BufferedImage getFlagImg();
    public BufferedImage getQuestionImg();

    public int[][] getInfoBottomLayer();
    public boolean[][] getInfoUpLayer();

    public void setMark(int y, int x);
    public int[][] getMarkInfo();

    public void openCell(int y, int x);
    public void setBrightCell(int y, int x);
    public int[] getBrightCell();

    public void startExplosion(int y, int x);
    public int[] getExplosionCoordinates();
    public BufferedImage nextFrame ();
    public boolean getExplosionState();
    public boolean checkBombFound();
    public void setBombFoundState(boolean state);
    public int getCurrentTime();
    public boolean getGameState();

    public void registerObserver(FieldObserver o);
    public void removeObserver(FieldObserver o);
    public void notifyObservers();

}
