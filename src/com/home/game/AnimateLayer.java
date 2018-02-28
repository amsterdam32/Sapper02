package com.home.game;

import java.awt.image.BufferedImage;

public interface AnimateLayer {
    public int[] getExplosionCoordinates();
    public void setExplosionCoordinates(int x, int y, int cellSize);
    public BufferedImage nextFrame ();
    public boolean isExplosion();
    public void setExplosionState(boolean state);
}
