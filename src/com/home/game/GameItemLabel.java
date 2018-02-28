package com.home.game;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class GameItemLabel extends JPanel {
    public int value;
    public String label;
    public GameItemLabel(int width, int height, int value, String label){
        this.value = value;
        this.label = label;
        this.setPreferredSize(new Dimension(width, height));
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        Font fontTimer = new Font(Font.SANS_SERIF, Font.BOLD, 24);
        g2.setFont(fontTimer);
        int xCircle = (getWidth()-50)/2;

        g2.setColor(new Color(105,193,5));
        g2.fillOval(xCircle,2,51,51);

        g2.setColor(new Color(80,80,80, 180));
        g2.setStroke(new BasicStroke(2));
        g2.drawOval(xCircle,2,51,51);

        g2.setColor(Color.WHITE);
        String currentTimeString = String.valueOf(value);
        FontRenderContext context = g2.getFontRenderContext();
        Rectangle2D bounds = fontTimer.getStringBounds(currentTimeString, context);
        double xt = (getWidth()-bounds.getWidth())/2;
        g2.drawString(currentTimeString, (int)xt+1,36);

        FontRenderContext contextLabel = g2.getFontRenderContext();
        Rectangle2D boundsLabel = fontTimer.getStringBounds(label, context);
        double xl = (getWidth()-boundsLabel.getWidth())/2;
        g2.setColor(new Color(85,154,26));
        g2.drawString(label, (int)xl,76);

    }
    public void updateValue(int currentValue){
        value = currentValue;
        repaint();
    }
}
