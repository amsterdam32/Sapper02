package com.home.game;

import java.awt.image.BufferedImage;

/**
 * The <code>TilesOfImageObjectsGame<code> is a class that realise methods
 * for convert seamless image in array of subimages
 * where every array element match cell of game field.
 * @see TilesOfImage
 */
public class TilesOfImageObjectsGame implements TilesOfImage {

    /**
     * It's container of seamless image
     */
    private BufferedImage image;
    /**
     * It's container for sliced subimages.
     */
    private BufferedImage[][] tilesOfImages;
    /**
     * Common quantity rows of game field.
     */
    private int rowsField;
    /**
     * Common quantity columns of game field.
     */
    private int colsField;
    /**
     * The scale subimage relative cell of game field.
     */
    private int scale;

    public TilesOfImageObjectsGame(BufferedImage image, int rowsField, int colsField, int scale){
        this.image = image;
        this.rowsField = rowsField;
        this.colsField = colsField;
        this.scale = scale;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setImagePuzzle(BufferedImage image) {
        this.image = image;
    }

    /**
     * Method convert seamless image in array of subimages
     * where every array element match cell of game field.
     * Method allow resize scale subimage relative original image.
     * @return array sliced subimages
     */
    @Override
    public BufferedImage[][] getTilesOfImg() {
        tilesOfImages = new BufferedImage[rowsField][colsField];
        int widthImageCell = image.getWidth()/scale;
        int heightImageCell = image.getHeight()/scale;
        for (int i = 0, y=0; i < rowsField; i++) {
            for (int j = 0, x=0; j < colsField; j++) {
                tilesOfImages[i][j] = image.getSubimage(x,y, widthImageCell,heightImageCell);

                if(x >= image.getWidth() - widthImageCell) x=0;
                else x += widthImageCell;
            }
            if(y >= image.getHeight() - heightImageCell) y=0;
            else y += heightImageCell;
        }
        return tilesOfImages;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setScaleImage(int scale) {
        this.scale = scale;
    }
}
