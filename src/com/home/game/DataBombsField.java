package com.home.game;

import java.util.*;

public class DataBombsField implements InfoBombsField {
    private int[][] innerDataArr;
    private int height;
    private int width;
    private int fieldSquare;
    private Set<Integer> sequenceBombs = new HashSet<Integer>();

    public DataBombsField(){
        this(10,10);
    }
    public DataBombsField(int height, int width){
        this.height = height;
        this.width = width;
        innerDataArr = new int[height][width];
        fieldSquare = height*width;
    }
    @Override
    public void setBombs(int numberBombs) {
        Date seed = new Date();
        //Date seed = new Date(2018, 02, 14);
        Random number = new Random(seed.getTime());
        sequenceBombs = new HashSet<Integer>();
        while (sequenceBombs.size() < numberBombs) {
            sequenceBombs.add(number.nextInt(fieldSquare));
        }

        for (Integer item : sequenceBombs) {

            int x = item % width;
            int y = item / width;

            innerDataArr[y][x] = -9;
            /*
             * 0 0 0 0
             * 0 # # 0
             * 0 # # 0
             * 0 0 0 0
             * */
            if (x != 0 && x != width - 1 &&
                    y != 0 && y != height - 1) {
                innerDataArr[y - 1][x - 1]++;
                innerDataArr[y - 1][x]++;
                innerDataArr[y - 1][x + 1]++;
                innerDataArr[y][x - 1]++;
                innerDataArr[y][x + 1]++;
                innerDataArr[y + 1][x - 1]++;
                innerDataArr[y + 1][x]++;
                innerDataArr[y + 1][x + 1]++;
            }
            /*
             * 0 # # 0
             * 0 0 0 0
             * 0 0 0 0
             * 0 0 0 0
             * */
            else if (y == 0 && x != 0 && x != width - 1) {
                innerDataArr[y][x - 1]++;
                innerDataArr[y][x + 1]++;
                innerDataArr[y + 1][x - 1]++;
                innerDataArr[y + 1][x]++;
                innerDataArr[y + 1][x + 1]++;

            }
            /*
             * 0 0 0 0
             * 0 0 0 0
             * 0 0 0 0
             * 0 # # 0
             * */
            else if (y == height - 1 && x != 0 && x != width - 1) {
                innerDataArr[y][x - 1]++;
                innerDataArr[y][x + 1]++;
                innerDataArr[y - 1][x - 1]++;
                innerDataArr[y - 1][x]++;
                innerDataArr[y - 1][x + 1]++;
            }
            /*
             * 0 0 0 0
             * # 0 0 0
             * # 0 0 0
             * 0 0 0 0
             * */
            else if (y != 0 && y != height - 1 && x == 0) {
                innerDataArr[y - 1][x]++;
                innerDataArr[y + 1][x]++;
                innerDataArr[y - 1][x + 1]++;
                innerDataArr[y][x + 1]++;
                innerDataArr[y + 1][x + 1]++;
            }
            /*
             * 0 0 0 0
             * 0 0 0 #
             * 0 0 0 #
             * 0 0 0 0
             * */
            else if (y != 0 && y != height - 1 && x == width - 1) {
                innerDataArr[y - 1][x]++;
                innerDataArr[y + 1][x]++;
                innerDataArr[y - 1][x - 1]++;
                innerDataArr[y][x - 1]++;
                innerDataArr[y + 1][x - 1]++;
            }
            /*
             * # 0 0 0
             * 0 0 0 0
             * 0 0 0 0
             * 0 0 0 0
             * */
            else if (x == 0 && y == 0) {
                innerDataArr[y][x + 1]++;
                innerDataArr[y + 1][x]++;
                innerDataArr[y + 1][x + 1]++;
            }
            /*
             * 0 0 0 #
             * 0 0 0 0
             * 0 0 0 0
             * 0 0 0 0
             * */
            else if (x == width - 1 && y == 0) {
                innerDataArr[y][x - 1]++;
                innerDataArr[y + 1][x]++;
                innerDataArr[y + 1][x - 1]++;
            }
            /*
             * 0 0 0 0
             * 0 0 0 0
             * 0 0 0 0
             * # 0 0 0
             * */
            else if (x == 0 && y == height - 1) {
                innerDataArr[y][x + 1]++;
                innerDataArr[y - 1][x]++;
                innerDataArr[y - 1][x + 1]++;
            }
            /*
             * 0 0 0 0
             * 0 0 0 0
             * 0 0 0 0
             * 0 0 0 #
             * */
            else if (x == width - 1 && y == height - 1) {
                innerDataArr[y][x - 1]++;
                innerDataArr[y - 1][x]++;
                innerDataArr[y - 1][x - 1]++;
            }
        }

    }

    @Override
    public int[][] getInfoFields() {
        return innerDataArr;
    }

}
