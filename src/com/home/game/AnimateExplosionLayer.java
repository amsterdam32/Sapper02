package com.home.game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AnimateExplosionLayer implements AnimateLayer {
    private BufferedImage explosionSprite = null;
    private BufferedImage frame = null;
    private boolean explosion = false;
    private int[] coordinates = new int[4];

    private int frameY = 0;
    private int frameX = 0;

    public AnimateExplosionLayer(BufferedImage explosionSprite) {
        this.explosionSprite = explosionSprite;
    }

    @Override
    public void setExplosionState(boolean state) {
        explosion = state;
    }

    @Override
    public void setExplosionCoordinates(int x, int y, int cellSize){
        coordinates[0] = x*cellSize + cellSize/2 -66;
        coordinates[1] = y*cellSize + cellSize/2-66;
        coordinates[2] = x*cellSize + cellSize/2+66;
        coordinates[3] = y*cellSize + cellSize/2+66;
    }

    @Override
    public int[] getExplosionCoordinates() {
        return coordinates;
    }

    @Override
    public boolean isExplosion() {
        return explosion;
    }

    @Override
    public BufferedImage nextFrame (){
        frame = explosionSprite.getSubimage(frameX*128, frameY*128, 128, 128);
        frameX++;
        if(frameX==4){
            frameY++;
            frameX=0;
        }

        if(frameY==4){
            frameY = 0;
            frameX = 0;
            explosion = false;
        }
        return frame;
    }


}
