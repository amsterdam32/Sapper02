package com.home.game;

import java.awt.image.BufferedImage;

public class ImagePuzzle implements ImagePuzzleInterface{

    BufferedImage innerImage;
    BufferedImage[][] arrImage;
    int scale;

    public ImagePuzzle(){
        this(null,0);
    }
    public ImagePuzzle(BufferedImage image, int scale){
        innerImage = image;
        this.scale = scale;
    }

    @Override
    public void setImagePuzzle(BufferedImage image) {
        innerImage = image;
    }

    @Override
    public BufferedImage[][] getImagePuzzle() {
        arrImage = new BufferedImage[scale][scale];
        int width = innerImage.getWidth()/scale;
        int height = innerImage.getHeight()/scale;
        for (int i = 0; i < scale; i++) {
            for (int j = 0; j < scale; j++) {
                arrImage[i][j] = innerImage.getSubimage(j*width,i*height, width,height);
            }
        }
        return arrImage;
    }

    @Override
    public void setScaleImage(int scale) {
        this.scale = scale;
    }
}
