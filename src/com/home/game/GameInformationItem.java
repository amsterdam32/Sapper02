package com.home.game;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 * The <code>GameInformationItem</code> class
 * that describe information item of game field,
 * that can showing some of regularly updating data.
 */
public class GameInformationItem extends JPanel {
    /**
     * Value for storage some information.
     */
    private int value;
    /**
     * Label for value
     */
    private String itemLabel;
    /**
     * Value width of bases item
     */
    private int baseWidth;
    /**
     * Value height of bases item
     */
    private int baseHeight;
    /**
     * Value diameter of circle where shows value
     */
    private int circleDiameter;

    /**
     * Object of font for value
     */
    private Font fontValue = new Font(Font.SANS_SERIF, Font.BOLD, 24);
    /**
     * Object of font for label
     */
    private Font fontLabel = new Font(Font.SANS_SERIF, Font.BOLD, 24);
    /**
     * Object of color for value
     */
    private Color valueColor = new Color(255,255,255);
    /**
     * Object of color for label
     */
    private Color labelColor = new Color(85,155,25);

    public GameInformationItem(int baseWidth, int baseHeight, int circleDiameter, int value, String itemLabel){
        this.value = value;
        this.itemLabel = itemLabel;
        this.baseWidth = baseWidth;
        this.baseHeight = baseHeight;
        this.circleDiameter = circleDiameter;
        this.setPreferredSize(new Dimension(baseWidth, baseHeight));

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.WHITE);
        drawBackgroundCircle(g2);
        drawItemValue(g2);
        drawItemLabel(g2);
    }

    /**
     * Method for painting circle where placed informational the value.
     * @param g2 Common Graphics2D object for painting information item
     */
    private void drawBackgroundCircle(Graphics2D g2){
        int xCircle = (baseWidth - circleDiameter)/2;
        g2.setColor(new Color(125,175,35));
        g2.fillOval(xCircle,2, circleDiameter + 1, circleDiameter + 1);

        g2.setColor(new Color(80,80,80, 180));
        g2.setStroke(new BasicStroke(2));
        g2.drawOval(xCircle,2, circleDiameter + 1, circleDiameter + 1);
    }

    /**
     * Method for painting informational the value.
     * @param g2 Common Graphics2D object for painting information item
     */
    private void drawItemValue(Graphics2D g2){
        g2.setFont(this.fontValue);

        String itemValue = String.valueOf(value);
        FontRenderContext contextItem = g2.getFontRenderContext();
        Rectangle2D boundsValue = fontValue.getStringBounds(itemValue, contextItem);
        double xItem = (baseWidth - boundsValue.getWidth())/2 + 1;
        double yItem = (circleDiameter - boundsValue.getY())/2 - 1;

        g2.setColor(valueColor);
        g2.drawString(itemValue, (int)xItem,(int)yItem);
    }

    /**
     * Method for painting the label of item under informational value.
     * @param g2 Common Graphics2D object for painting information item
     */
    private void drawItemLabel(Graphics2D g2){
        g2.setFont(this.fontLabel);

        FontRenderContext contextLabel = g2.getFontRenderContext();
        Rectangle2D boundsLabel = fontLabel.getStringBounds(itemLabel, contextLabel);
        double xLabel = (baseWidth-boundsLabel.getWidth())/2;
        double yLabel = circleDiameter + (- boundsLabel.getY()) + 1;

        g2.setColor(labelColor);
        g2.drawString(itemLabel, (int)xLabel,(int)yLabel);
    }


    /**
     * Method for updating value, that showing in item.
     * @param currentValue new integer value
     */
    public void updateValue(int currentValue){
        value = currentValue;
        repaint();
    }

    /**
     * Setter for setting font of the value
     * @param fontValue Font object
     */
    public void setFontValue(Font fontValue) {
        this.fontValue = fontValue;
    }
    /**
     * Setter for setting font of the label
     * @param fontLabel Font object
     */
    public void setFontLabel(Font fontLabel) {
        this.fontLabel = fontLabel;
    }
    /**
     * Setter for setting color of the value
     * @param valueColor Color object
     */
    public void setValueColor(Color valueColor) {
        this.valueColor = valueColor;
    }
    /**
     * Setter for setting color of the label
     * @param labelColor Color object
     */
    public void setLabelColor(Color labelColor) {
        this.labelColor = labelColor;
    }
}
