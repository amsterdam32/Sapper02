package com.home.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class GamePanel extends GamePanelAbstract {
    private int cellSize;
    private int[] brightRect ;

    private Timer timer;
    private boolean isExplosion = false;
    private int[] explosionCoordinate;
    private int[][]  markInfo;
    private BufferedImage flagImg = null;
    private BufferedImage questionImg = null;
    private BufferedImage bombImg = null;
    private BufferedImage[][] groundImgPuzzle = null;
    private BufferedImage[][] groundLightImgPuzzle = null;
    private BufferedImage[][] underGroundImgPuzzle = null;
    private int groundScale = 1;
    private int underGroundScale = 1;

    private BufferedImage explosionImg = null;


    public GamePanel(int[][] ibl, boolean[][] iul,int[][] imi, int[] br, int cs){
        innerBottomLayer = ibl;
        innerUpLayer = iul;
        markInfo = imi;
        cellSize = cs;
        createRectangles();
        brightRect = br;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        drawBottomLayer(g2);
        drawUpLayer(g2);
        if(isExplosion) drawExplosion(g2);
        drawMark(g2);

    }

    private void drawBottomLayer(Graphics2D g2){
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 36);
        g2.setFont(font);

        for (int i = 0; i < setRectangles.length; i++) {
            for (int j = 0; j < setRectangles[i].length; j++) {
                //draw ground
                g2.setColor(new Color(200,128,18));
                /*g2.fillRect((int)setRectangles[i][j].getX(), (int)setRectangles[i][j].getY(),
                        (int)setRectangles[i][j].getWidth(), (int)setRectangles[i][j].getHeight());*/
                g2.drawImage(underGroundImgPuzzle[i%underGroundScale][j%underGroundScale],
                        (int)setRectangles[i][j].getX(), (int)setRectangles[i][j].getY(),
                        cellSize,cellSize,
                        this);

                //draw grid
                g2.setColor(Color.BLACK);
                g2.draw(setRectangles[i][j]);

                //draw bombs and write numbers

                int x1 = (int) setRectangles[i][j].getX() + cellSize / 2 - 8;
                int y1 = (int) setRectangles[i][j].getY() + cellSize / 2 + 14;

                if (innerBottomLayer[i][j] < 0) {
                    if(null!=bombImg) {
                        g2.drawImage(bombImg, (int) setRectangles[i][j].getX()+1,
                                (int) setRectangles[i][j].getY()+4,
                                (int)setRectangles[i][j].getWidth()-4,
                                (int)setRectangles[i][j].getHeight()-4,
                                this);
                    }
                    else {
                        g2.setColor(Color.RED);
                        g2.drawString("*", x1+2, y1+6);
                    }
                }
                else if (innerBottomLayer[i][j] == 0) ;
                else if(innerBottomLayer[i][j] == 1){
                    g2.setColor(new Color(86,167,241));
                    g2.drawString(innerBottomLayer[i][j] + "", x1, y1);
                }
                else if(innerBottomLayer[i][j] == 2){
                    g2.setColor(new Color(105,193,5));
                    g2.drawString(innerBottomLayer[i][j] + "", x1, y1);
                }
                else if(innerBottomLayer[i][j] == 3){
                    g2.setColor(Color.RED);
                    g2.drawString(innerBottomLayer[i][j] + "", x1, y1);
                }
                else if(innerBottomLayer[i][j] == 4){
                    g2.setColor(new Color(200,154,200));
                    g2.drawString(innerBottomLayer[i][j] + "", x1, y1);
                }
                else if(innerBottomLayer[i][j] == 5){
                    g2.setColor(new Color(200,45,121));
                    g2.drawString(innerBottomLayer[i][j] + "", x1, y1);
                }
                else {
                    g2.setColor(new Color(180,195,200));
                    g2.drawString(innerBottomLayer[i][j] + "", x1, y1);
                }
            }
        }
    }

    private void drawUpLayer(Graphics2D g2){
        for (int i = 0; i < setUpRectangles.length; i++) {
            for (int j = 0; j < setUpRectangles[i].length; j++) {
                if (setUpRectangles[i][j] != null &&
                        !innerUpLayer[i][j]) {
                    if(i==brightRect[0]&&j==brightRect[1]) {
                        g2.drawImage(groundLightImgPuzzle[i%groundScale][j%groundScale],
                                (int)setRectangles[i][j].getX(), (int)setRectangles[i][j].getY(),
                                cellSize,cellSize,
                                this);
                        /*g2.setColor(new Color(170,220,35));
                        g2.fillRect((int)setUpRectangles[i][j].getX(), (int)setUpRectangles[i][j].getY(),
                                (int)setUpRectangles[i][j].getWidth(), (int)setUpRectangles[i][j].getHeight());*/

                        g2.setColor(new Color(110,65,45));
                        g2.setStroke(new BasicStroke(4));
                        g2.drawLine((int)setUpRectangles[i][j].getX()+2,
                                (int)(setUpRectangles[i][j].getY()+2+setUpRectangles[i][j].getHeight()),
                                (int)(setUpRectangles[i][j].getX()+2+setUpRectangles[i][j].getWidth()),
                                (int)(setUpRectangles[i][j].getY()+2+setUpRectangles[i][j].getHeight()));
                        g2.drawLine((int)(setUpRectangles[i][j].getX()+2+setUpRectangles[i][j].getWidth()),
                                (int) setUpRectangles[i][j].getY()+2,
                                (int)(setUpRectangles[i][j].getX()+2+setUpRectangles[i][j].getWidth()),
                                (int)(setUpRectangles[i][j].getY()+setUpRectangles[i][j].getHeight()));
                        g2.setStroke(new BasicStroke(1));

                    }
                    else {
                        //Рисуем траву
                        g2.drawImage(groundImgPuzzle[i%groundScale][j%groundScale],
                                (int)setRectangles[i][j].getX(), (int)setRectangles[i][j].getY(),
                                cellSize,cellSize,
                                this);
                        //g2.setColor(Color.RED);
                        /*g2.setColor(new Color(125,175,35));
                        g2.fillRect((int)setUpRectangles[i][j].getX(), (int)setUpRectangles[i][j].getY(),
                                (int)setUpRectangles[i][j].getWidth(), (int)setUpRectangles[i][j].getHeight());*/

                        g2.setColor(new Color(110,65,45));
                        g2.setStroke(new BasicStroke(4));
                        g2.drawLine((int)setUpRectangles[i][j].getX()+2,
                                (int)(setUpRectangles[i][j].getY()+2+setUpRectangles[i][j].getHeight()),
                                (int)(setUpRectangles[i][j].getX()+2+setUpRectangles[i][j].getWidth()),
                                (int)(setUpRectangles[i][j].getY()+2+setUpRectangles[i][j].getHeight()));
                        g2.drawLine((int)(setUpRectangles[i][j].getX()+2+setUpRectangles[i][j].getWidth()),
                                (int) setUpRectangles[i][j].getY()+2,
                                (int)(setUpRectangles[i][j].getX()+2+setUpRectangles[i][j].getWidth()),
                                (int)(setUpRectangles[i][j].getY()+setUpRectangles[i][j].getHeight()));
                        g2.setStroke(new BasicStroke(1));
                    }
                    //g2.fill(setUpRectangles[i][j]);

                } //else System.out.println("call repaint for null");
            }
        }
    }

    private void drawExplosion(Graphics2D g2){
        int leftCornerExplosionX = explosionCoordinate[0];
        int leftCornerExplosionY = explosionCoordinate[1];
        int rightCornerExplosionX = explosionCoordinate[2];
        int rightCornerExplosionY = explosionCoordinate[3];

        g2.drawImage(explosionImg, leftCornerExplosionX,leftCornerExplosionY,
                rightCornerExplosionX, rightCornerExplosionY,
                0, 0, 128, 128,
                getParent());
    }

    private void drawMark(Graphics2D g2){
        for (int i = 0; i < setRectangles.length; i++) {
            for (int j = 0; j < setRectangles[i].length; j++) {

                int x1 = (int) setRectangles[i][j].getX() + cellSize / 2 - 8;
                int y1 = (int) setRectangles[i][j].getY() + cellSize / 2 + 14;

                if (markInfo[i][j] == 1) {
                    if (null != flagImg) {
                        g2.drawImage(flagImg, (int) setRectangles[i][j].getX() + 1,
                                (int) setRectangles[i][j].getY() + 4,
                                (int) setRectangles[i][j].getWidth() - 4,
                                (int) setRectangles[i][j].getHeight() - 4,
                                this);
                    }
                }
                else if(markInfo[i][j] == 2&&!innerUpLayer[i][j]) {
                    if (null != questionImg) {
                        g2.drawImage(questionImg, (int) setRectangles[i][j].getX() + 1,
                                (int) setRectangles[i][j].getY() + 4,
                                (int) setRectangles[i][j].getWidth() - 4,
                                (int) setRectangles[i][j].getHeight() - 4,
                                this);
                    }
                }
            }
        }
    }

    @Override
    public void updateInnerBottomLayer(int[][] innerBottomLayer) {
        this.innerBottomLayer = innerBottomLayer;
    }
    @Override
    public void updateInnerUpLayer(boolean[][] innerUpLayer, int[][]innerMarkInfo) {
        this.innerUpLayer = innerUpLayer;
        this.markInfo = innerMarkInfo;
        repaint();
    }

    @Override
    public void updateBrightCell(int[] brightCell){
        brightRect[0] = brightCell[0];
        brightRect[1] = brightCell[1];
        repaint();
    }

    @Override
    public void updateAnimateLayer(int[] coordinate, boolean stateExplosion, BufferedImage explosionFrame) {
        explosionImg = explosionFrame;
        explosionCoordinate = coordinate;
        isExplosion = stateExplosion;
    }

    public void createRectangles(){
        int height = innerBottomLayer.length;
        int width = innerBottomLayer[0].length;
        setRectangles = new Rectangle2D[height][width];
        setUpRectangles = new Rectangle2D[height][width];
        for (int i = 0, y = 0; i < height*cellSize; i += cellSize, y++) {
            for (int j = 0, x = 0; j < width*cellSize; j += cellSize, x++) {
                setRectangles[y][x] = new Rectangle2D.Double(j, i, cellSize, cellSize);
                setUpRectangles[y][x] = new Rectangle2D.Double(j + 1, i + 1, cellSize - 1, cellSize - 1);
            }
        }

    }

    @Override
    public void getImgElements(BufferedImage bombImg, BufferedImage flagImg, BufferedImage questionImg,
                               BufferedImage[][] groundImagePuzzle, BufferedImage[][] groundLightImagePuzzle,
                               BufferedImage[][] underGroundImagePuzzle, int groundScale, int underGroundScale) {
        this.bombImg = bombImg;
        this.flagImg = flagImg;
        this.questionImg = questionImg;
        this.groundImgPuzzle = groundImagePuzzle;
        this.groundLightImgPuzzle = groundLightImagePuzzle;
        this.underGroundImgPuzzle = underGroundImagePuzzle;
        this.groundScale = groundScale;
        this.underGroundScale = groundScale;
    }
}
