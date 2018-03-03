package com.home.game;

import javax.swing.*;
import java.awt.*;

public class FieldController {
    private FieldModel fieldModel;
    private FieldView fieldView;

    FieldController(){
        fieldModel = new FieldModel();
        fieldModel.setGroundImageScale(20);
        fieldModel.setUnderGroundImageScale(20);
        fieldModel.loadImg();
        fieldView = new FieldView(this, fieldModel);
        fieldView.createView();

    }
    public void openCell(int y, int x){
        fieldModel.openCell(y,x);
    }

    public void setMark(int y, int x){
        fieldModel.setMark(y,x);
    }
    public void checkWin(){
        if(fieldModel.checkGameStateWin()) {
            fieldModel.stopTime();
            fieldModel.setGameIsEnd(true);
            int winTime = fieldModel.getCurrentTime()/10;
            fieldView.winGame(winTime);
        }
    }
    public void checkWasDetonated(){
        if(fieldModel.checkBombFound()) {
            fieldModel.setBombFoundState(false);
            fieldView.endGame();
        }
    }
    public void setBrightCell(int y, int x){
        fieldModel.setBrightCell(y,x);
    }
    public void newGame(){
        newGame(9,9,10);
    }

    public void newGame(int height, int width, int numbersBombs){
        FieldView oldView = fieldView;
        oldView.mainFrame.dispose();
        fieldModel = new FieldModel(height,width,numbersBombs);
        fieldModel.setGroundImageScale(20);
        fieldModel.setUnderGroundImageScale(20);
        fieldModel.loadImg();
        fieldView = new FieldView(this, fieldModel);
        fieldView.createView();
    }

    public void exitGame(){
        System.exit(0);
        //fieldModel.exitGame();
    }
}
