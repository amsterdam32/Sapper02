package com.home.game;

/**
 * Some constants that indicate state of cell surface
 */
public enum State {
    /**
     * This named constant that indicate closed state of cell surface that equal false.
     */
    CLOSED {
        public boolean getValue() {
            return false;
        }
    },
    /**
     * This named constant that indicate opened state of cell surface that equal true.
     */
    OPENED {
        public boolean getValue() {
            return true;
        }
    };

    /**
     * Method return numeric equivalent for named of constant.
     */
    public abstract boolean getValue();
}
