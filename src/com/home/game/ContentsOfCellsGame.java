package com.home.game;

import java.util.*;
/**
 * The <code>ContentsOfCellsGame<code> is a class that realisation methods
 * installing of bombs and getting information about their placing.
 * @see ContentsOfCells
 */
public class ContentsOfCellsGame implements ContentsOfCells {
    /**
     * Container for store integer data that reflection information model game field
     * where every symbol interprets content of game field cell.
     */
    private int[][] dataArr;
    /**
     * value contain quantity rows in the game field
     */
    private int rowInField;
    /**
     * value contain quantity columns in the game field
     */
    private int colInField;
    /**
     * value contain common quantity of the game field cells
     */
    private int squareOfField;
    /**
     * Container for store coordinates of placed bombs
     */
    private Set<Integer> sequenceBombs = new HashSet<Integer>();

    public ContentsOfCellsGame(int rowInField, int colInField){
        this.rowInField = rowInField;
        this.colInField = colInField;
        dataArr = new int[rowInField][colInField];
        squareOfField = rowInField * colInField;
    }

    /**
     * The method fillings sequence of random coordinates
     * that representing bombs on game field.
     * @param numberBombs transmitting quantity all bombs
     */
    @Override
    public void setBombs(int numberBombs) {
        Date seed = new Date();
        Random number = new Random(seed.getTime());
        sequenceBombs = new HashSet<Integer>();
        while (sequenceBombs.size() < numberBombs) {
            sequenceBombs.add(number.nextInt(squareOfField));
        }
    }

    /**
     * {@inheritDoc}
     */
    public void calculateContentsOfCells(){

        for (Integer item : sequenceBombs) {

            //Calculating position of the bomb relative game field.
            int x = item % colInField;
            int y = item / colInField;

            //Bombs will be presented negative value.
            dataArr[y][x] = -9;

            /* Calculating state of field cells that placed
               in the central part of field relative cell having bomb.
               0 0 0 0
               0 # # 0
               0 # # 0
               0 0 0 0
             */
            if (x != 0 && x != colInField - 1 &&
                    y != 0 && y != rowInField - 1) {
                dataArr[y - 1][x - 1]++;
                dataArr[y - 1][x]++;
                dataArr[y - 1][x + 1]++;
                dataArr[y][x - 1]++;
                dataArr[y][x + 1]++;
                dataArr[y + 1][x - 1]++;
                dataArr[y + 1][x]++;
                dataArr[y + 1][x + 1]++;
            }
            /*
               Calculating state of field cells that placed
               in the top part of field relative cell having bomb.
               0 # # 0
               0 0 0 0
               0 0 0 0
               0 0 0 0
             */
            else if (y == 0 && x != 0 && x != colInField - 1) {
                dataArr[y][x - 1]++;
                dataArr[y][x + 1]++;
                dataArr[y + 1][x - 1]++;
                dataArr[y + 1][x]++;
                dataArr[y + 1][x + 1]++;

            }
            /* Calculating state of field cells that placed
               in the bottom part of field relative cell having bomb.
               0 0 0 0
               0 0 0 0
               0 0 0 0
               0 # # 0
            */
            else if (y == rowInField - 1 && x != 0 && x != colInField - 1) {
                dataArr[y][x - 1]++;
                dataArr[y][x + 1]++;
                dataArr[y - 1][x - 1]++;
                dataArr[y - 1][x]++;
                dataArr[y - 1][x + 1]++;
            }
            /* Calculating state of field cells that placed
               in the left part of field relative cell having bomb.
               0 0 0 0
               # 0 0 0
               # 0 0 0
               0 0 0 0
             */
            else if (y != 0 && y != rowInField - 1 && x == 0) {
                dataArr[y - 1][x]++;
                dataArr[y + 1][x]++;
                dataArr[y - 1][x + 1]++;
                dataArr[y][x + 1]++;
                dataArr[y + 1][x + 1]++;
            }
            /* Calculating state of field cells that placed
               in the right part of field relative cell having bomb.
               0 0 0 0
               0 0 0 #
               0 0 0 #
               0 0 0 0
             */
            else if (y != 0 && y != rowInField - 1 && x == colInField - 1) {
                dataArr[y - 1][x]++;
                dataArr[y + 1][x]++;
                dataArr[y - 1][x - 1]++;
                dataArr[y][x - 1]++;
                dataArr[y + 1][x - 1]++;
            }
            /* Calculating state of field cell that placed
               in the top-left corner of field relative cell having bomb.
               # 0 0 0
               0 0 0 0
               0 0 0 0
               0 0 0 0
            */
            else if (x == 0 && y == 0) {
                dataArr[y][x + 1]++;
                dataArr[y + 1][x]++;
                dataArr[y + 1][x + 1]++;
            }
            /* Calculating state of field cell that placed
               in the top-right corner of field relative cell having bomb.
               0 0 0 #
               0 0 0 0
               0 0 0 0
               0 0 0 0
            */
            else if (x == colInField - 1 && y == 0) {
                dataArr[y][x - 1]++;
                dataArr[y + 1][x]++;
                dataArr[y + 1][x - 1]++;
            }
            /* Calculating state of field cell that placed
               in the bottom-left corner of field relative cell having bomb.
               0 0 0 0
               0 0 0 0
               0 0 0 0
               # 0 0 0
            */
            else if (x == 0 && y == rowInField - 1) {
                dataArr[y][x + 1]++;
                dataArr[y - 1][x]++;
                dataArr[y - 1][x + 1]++;
            }
            /* Calculating state of field cell that placed
               in the bottom-right corner of field relative cell having bomb.
               0 0 0 0
               0 0 0 0
               0 0 0 0
               0 0 0 #
            */
            else if (x == colInField - 1 && y == rowInField - 1) {
                dataArr[y][x - 1]++;
                dataArr[y - 1][x]++;
                dataArr[y - 1][x - 1]++;
            }
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int[][] getContentsOfCells() {
        return dataArr;
    }

}
