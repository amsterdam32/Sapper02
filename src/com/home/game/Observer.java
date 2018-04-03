package com.home.game;

/**
 * Interface of observer pattern. A part for observer.
 */
public interface Observer {
    /**
     * Method used for updating data.
     */
    public void updatePaint();

    /**
     * Method used for updating animation.
     */
    public void updateAnimate();
}
