package com.home.game;

/**
 * Interface of observer pattern. A part for observable.
 */
public interface Observable {
    /**
     * Method used for registration observers.
     */
    void registerObserver(Observer o);
    /**
     * Method used for removing observers.
     */
    void removeObserver(Observer o);

    /**
     * Method used for notify observers about change data.
     */
    void notifyObserversData();

    /**
     * Method used for notify observers about change animation.
     */
    void notifyObserversAnimate();
}
