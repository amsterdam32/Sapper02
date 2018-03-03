package com.home.game;

import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class GamePanelAbstract extends JPanel {
    int[][] innerBottomLayer;
    boolean[][] innerUpLayer;
    Rectangle2D[][] setRectangles;
    Rectangle2D[][] setUpRectangles;

    abstract public void updateInnerBottomLayer(int[][] innerBottomLayer);
    abstract public void updateInnerUpLayer(boolean[][] innerUpLayer, int[][]innerMarkInfo);

    abstract public void updateBrightCell(int[] brightCell);
    public abstract void getImgElements(BufferedImage bombImg, BufferedImage flagImg, BufferedImage questionImg,
                                        BufferedImage[][] groundImagePuzzle, BufferedImage[][] groundLightImagePuzzle,
                                        BufferedImage[][] underGroundImagePuzzle, int groundScale, int underGroudnScale);

    public Rectangle2D[][] getSetUpRectangles() {
        return setUpRectangles;
    }

    abstract public void updateAnimateLayer(int[] coordinate, boolean stateExplosion, BufferedImage explosionFrame);
}
