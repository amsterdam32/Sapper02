package com.home.game;

import java.io.IOException;
/**
 * The <code>FieldController</code> class that describe game controller
 */
public class FieldController {
    /**
     * Object stores model of game field.,
     */
    private FieldModel fieldModel;
    /**
     * Object stores view of game field.,
     */
    private FieldView fieldView;

    FieldController(int height, int width, int numbersBombs){
        initializeModelAndView(height, width, numbersBombs);
    }

    /**
     * Method starts new game
     * @param rowsInField quantity rows in game field
     * @param colsInField quantity columns in game field
     * @param quantityBombs quantity bombs on game field
     */
    public void newGame(int rowsInField, int colsInField, int quantityBombs){
        FieldView oldFieldView = fieldView;
        oldFieldView.mainFrame.dispose();
        initializeModelAndView(rowsInField, colsInField, quantityBombs);
    }

    /**
     * Method sets initial parameters for model and view.
     * @param rowsInField quantity rows in game field
     * @param colsInField quantity columns in game field
     * @param quantityBombs quantity bombs on game field
     */
    private void initializeModelAndView(int rowsInField, int colsInField, int quantityBombs){
        fieldModel = new FieldModel(rowsInField,colsInField,quantityBombs);
        fieldModel.setSurfaceImgScale(15);
        fieldModel.setUndergroundIgmScale(15);
        fieldView = new FieldView(this, fieldModel);
        setGameFieldType();
        fieldView.createView();
    }

    /**
     * The method is used to open a field cell in the transmitted coordinates.
     * @param row - value of row where placed cell
     * @param col - value of col where placed cell
     */
    public void openCell(int row, int col){
        fieldModel.openCell(row,col);
    }

    /**
     * The method is used for setting mark on surface selected cell.
     * @param row - value of row where placed cell
     * @param col - value of col where placed cell
     */
    public void setMark(int row, int col){
        fieldModel.setMark(row,col);
    }

    /**
     * Method checking event of the win.
     */
    public void checkWin(){
        if(fieldModel.checkGameStateWin()) {
            fieldModel.stopTime();
            fieldModel.setGameIsEnd(true);
            int winTime = fieldModel.getCurrentTime()/10;
            fieldView.winGame(winTime);
        }
    }

    /**
     * Method checking event of the detonation
     */
    public void checkWasDetonated(){
        if(fieldModel.checkBombFound()) {
            fieldModel.setBombFoundState(false);
            fieldView.endGame();
        }
    }

    /**
     * Method sets coordinate for cell on game field with alternative state of surface.
     * @param row - value of row where placed cell
     * @param col - value of col where placed cell
     */
    public void setBrightCell(int row, int col){
        fieldModel.setBrightCell(row,col);
    }


    /**
     * Method exit from game
     */
    public void exitGame(){
        System.exit(0);
    }

    /**
     * Method checks opportunity upload all necessary images
     * and generates appropriate view.
     */
    private void setGameFieldType(){
        try {
            fieldModel.loadImg();
        }
        catch (IOException e){
            fieldView.alarmLostImages();
        }
        if(fieldModel.getLoadImagesState()){
            fieldView.setGameFieldType(new GameFieldWithImages(
                    fieldModel.getContentsOfCells(),
                    fieldModel.getCellsSurfaceState(),
                    fieldModel.getDataOfMarks(),
                    fieldModel.getCellSize()));
        }
        else{
            fieldView.setGameFieldType(new GameFieldWithoutImages(
                    fieldModel.getContentsOfCells(),
                    fieldModel.getCellsSurfaceState(),
                    fieldModel.getDataOfMarks(),
                    fieldModel.getCellSize()));
        }
    }
}
