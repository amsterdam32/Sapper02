package com.home.game;

/**
 * Some constants that indicate type of mark
 * (such as FLAG/QUESTION/WITHOUT MARK) on cell surface
 */
public enum Mark {
    /**
     * This named constant for empty mark on cell surface, that equal 0.
     */
    WITHOUT {
        public int getValue() {
            return 0;
        }
    },
    /**
     * This named constant for mark of the FLAG on cell surface, that equal 1.
     */
    FLAG {
        public int getValue() {
            return 1;
        }
    },
    /**
     * This named constant. for mark of the QUESTION on cell surface, that equal 2.
     */
    QUESTION {
        public int getValue() {
            return 2;
        }
    };

    /**
     * Method return numeric equivalent for named of constant.
     */
    public abstract int getValue();
}
