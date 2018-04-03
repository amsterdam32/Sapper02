package com.home.game;

import java.awt.image.BufferedImage;

/**
 * The <code>TilesOfImage<code> is a common interface,
 * that sets methods for convert the image in sets subimages
 * with opportunity resize it.
 */
public interface TilesOfImage {
    /**
     * Return array of images.
     */
    public BufferedImage[][] getTilesOfImg();

    /**
     * Set a new image.
     */
    public void setImagePuzzle(BufferedImage image);

    /**
     * Set the scale image of relative cell field.
     */
    public void setScaleImage(int scale);
}
