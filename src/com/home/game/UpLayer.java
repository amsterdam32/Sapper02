package com.home.game;

public interface UpLayer {
    public void openCell(int y, int x);
    public void setBrightCell(int y, int x);
    public int[] getBrightCell();

    public void setMark(int y, int x);
    public int[][] getMarkInfo();

    public boolean[][] getDataLayer();
    public boolean isBomb();
    public void takeOffBomb();
    public void setBomb();
}