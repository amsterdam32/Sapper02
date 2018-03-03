package com.home.game;

import java.awt.image.BufferedImage;

public interface ImagePuzzleInterface {
    public BufferedImage[][] getImagePuzzle();
    public void setImagePuzzle(BufferedImage image);
    public void setScaleImage(int scale);
}
