package com.home.game;

/**
 * The <code>StateSurfaceOfCellsGame<code> is a class, that describes methods
 * for setting and getting states the surface of game field cells.
 * Also setting of some marks on surface of game field cells
 * and getting information about finding of bombs.
 * @see StateSurfaceOfCells
 */
public class StateSurfaceOfCellsGame implements StateSurfaceOfCells {
    /**
     * The container for storing the states of open cells of a field.
     */
    private boolean[][] cellsSurfaceState;
    /**
     * The container for storing the marks on surface of a field cells.
     */
    private int[][] dataOfMarks;
    /**
     * The container for storing the data about contents of cells.
     */
    private int[][] contentsOfCells;
    /**
     * The container for storing column coordinate where placed cell
     * that have alternative state of the surface.
     */
    private int brightCellCol = -1;
    /**
     * The container for storing row coordinate where placed cell
     * that have alternative state of the surface.
     */
    private int brightCellRow = -1;
    /**
     * This is the flag of the presence of a bomb in this cell.
     */
    private boolean bomb = false;

    /**
     * A containers for storing information about checked of field cells in a up direction.
     */
    private boolean[][] surfaceCheckedUp;
    /**
     * A containers for storing information about checked of field cells in a downup direction.
     */
    private boolean[][] surfaceCheckedDown;
    /**
     * A containers for storing information about checked of field cells in a left direction.
     */
    private boolean[][] surfaceCheckedLeft;
    /**
     * A containers for storing information about checked of field cells in a right direction.
     */
    private boolean[][] surfaceCheckedRight;

    public StateSurfaceOfCellsGame(int rowsInField, int colsInField, int[][] contentsOfCells){
        this.contentsOfCells = contentsOfCells;
        cellsSurfaceState = new boolean[rowsInField][colsInField];
        dataOfMarks = new int[rowsInField][colsInField];
        surfaceCheckedUp = new boolean[rowsInField][colsInField];
        surfaceCheckedDown = new boolean[rowsInField][colsInField];
        surfaceCheckedLeft = new boolean[rowsInField][colsInField];
        surfaceCheckedRight = new boolean[rowsInField][colsInField];
    }

    /**
     * The method of recursive researching field.
     * The method research of cell over current cell and checks some conditions.
     * @param row the place of cell in column
     * @param col The place of cell in row
     */
    private void checkUp(int row, int col) {
        if (row>0&& dataOfMarks[row-1][col]!=Mark.FLAG.getValue()) {
            surfaceCheckedUp[row][col] = State.OPENED.getValue();
            if (contentsOfCells[row-1][col] == 0) {
                checkUp(row-1, col);
                if(!surfaceCheckedRight[row-1][col])checkRight(row-1,col);
                if(!surfaceCheckedLeft[row-1][col])checkLeft(row-1,col);
            }
            this.cellsSurfaceState[row-1][col] = State.OPENED.getValue();
        }
    }

    /**
     * The method of recursive researching field.
     * The method research of cell bellow current cell and checks some conditions.
     * @param row the place of cell in column
     * @param col The place of cell in row
     */
    private void checkDown(int row, int col){
        if(row< contentsOfCells.length-1&& dataOfMarks[row+1][col]!=Mark.FLAG.getValue()){
            surfaceCheckedDown[row][col] = State.OPENED.getValue();
            if(contentsOfCells[row+1][col]==0){
                checkDown(row+1,col);
                if(!surfaceCheckedRight[row+1][col])checkRight(row+1,col);
                if(!surfaceCheckedLeft[row+1][col])checkLeft(row+1,col);
            }
            this.cellsSurfaceState[row+1][col] = State.OPENED.getValue();
        }
    }
    /**
     * The method of recursive researching field.
     * The method research of left cell from current cell and checks some conditions.
     * @param row the place of cell in column
     * @param col The place of cell in row
     */
    private void checkLeft(int row, int col){
        if(col>0&& dataOfMarks[row][col-1]!=Mark.FLAG.getValue()){
            surfaceCheckedLeft[row][col] = State.OPENED.getValue();
            if(contentsOfCells[row][col-1]==0){
                if(!surfaceCheckedUp[row][col-1])checkUp(row,col-1);
                checkLeft(row,col-1);
                if(!surfaceCheckedDown[row][col-1])checkDown(row,col-1);
            }
            this.cellsSurfaceState[row][col-1] = State.OPENED.getValue();
        }
    }

    /**
     * The method of recursive researching field.
     * The method research of right cell from current cell and checks some conditions.
     * @param row the place of cell in column
     * @param col The place of cell in row
     */
    private void checkRight(int row, int col){
        if(col< contentsOfCells[row].length-1&& dataOfMarks[row][col+1]!=Mark.FLAG.getValue()){
            surfaceCheckedRight[row][col] = State.OPENED.getValue();
            if(contentsOfCells[row][col+1]==0){
                checkRight(row,col+1);
                if(!surfaceCheckedUp[row][col+1])checkUp(row,col+1);
                if(!surfaceCheckedDown[row][col+1])checkDown(row,col+1);
            }
            this.cellsSurfaceState[row][col+1] = State.OPENED.getValue();
        }

    }

    /**
     * Part of main recursive method,
     * that research more complicated sites of the game field.
     */
    private void researchDiagonalCells(){
        for (int i = 0; i < contentsOfCells.length; i++) {
            for (int j = 0; j < contentsOfCells[i].length; j++) {

            //Research of the center part of the game field.
                if (contentsOfCells[i][j] == 0 && i > 0 && j > 0 &&
                        i < contentsOfCells.length - 1 &&
                        j < contentsOfCells[i].length - 1 &&
                        cellsSurfaceState[i][j] == State.OPENED.getValue()) {

                    /*Begin new research for next cell
                    if it is in down and in right from current cell.*/
                    if (contentsOfCells[i + 1][j + 1] == 0 &&
                            (contentsOfCells[i][j + 1] > 0 || dataOfMarks[i][j + 1] == Mark.FLAG.getValue()) &&
                            (contentsOfCells[i + 1][j] > 0 || dataOfMarks[i + 1][j] == Mark.FLAG.getValue()) &&
                            cellsSurfaceState[i + 1][j + 1] == State.CLOSED.getValue()) {
                        openCell(i + 1, j + 1);
                    }

                    /*Begin new research for next cell
                    if it is in top and in left from current cell.*/
                    if (contentsOfCells[i - 1][j - 1] == 0 &&
                            (contentsOfCells[i][j - 1] > 0 || dataOfMarks[i][j - 1] == Mark.FLAG.getValue()) &&
                            (contentsOfCells[i - 1][j] > 0 || dataOfMarks[i - 1][j] == Mark.FLAG.getValue()) &&
                            cellsSurfaceState[i - 1][j - 1] == State.CLOSED.getValue()) {
                        openCell(i - 1, j - 1);
                    }

                    /*Begin new research for next cell
                    if it is in top and in right from current cell.*/
                    if (contentsOfCells[i - 1][j + 1] == 0 &&
                            (contentsOfCells[i][j + 1] > 0 || dataOfMarks[i][j + 1] == Mark.FLAG.getValue()) &&
                            (contentsOfCells[i - 1][j] > 0 || dataOfMarks[i - 1][j] == Mark.FLAG.getValue()) &&
                            cellsSurfaceState[i - 1][j + 1] == State.CLOSED.getValue()) {
                        openCell(i - 1, j + 1);
                    }

                    /*Begin new research for next cell
                    if it is in down and in left from current cell.*/
                    if (contentsOfCells[i + 1][j - 1] == 0 &&
                            (contentsOfCells[i][j - 1] > 0 || dataOfMarks[i][j - 1] == Mark.FLAG.getValue()) &&
                            (contentsOfCells[i + 1][j] > 0 || dataOfMarks[i + 1][j] == Mark.FLAG.getValue()) &&
                            cellsSurfaceState[i + 1][j - 1] == State.CLOSED.getValue()) {
                        openCell(i + 1, j - 1);
                    }

                    if (dataOfMarks[i + 1][j + 1] != Mark.FLAG.getValue()) this.cellsSurfaceState[i + 1][j + 1] = State.OPENED.getValue();
                    if (dataOfMarks[i - 1][j - 1] != Mark.FLAG.getValue()) this.cellsSurfaceState[i - 1][j - 1] = State.OPENED.getValue();
                    if (dataOfMarks[i - 1][j + 1] != Mark.FLAG.getValue()) this.cellsSurfaceState[i - 1][j + 1] = State.OPENED.getValue();
                    if (dataOfMarks[i + 1][j - 1] != Mark.FLAG.getValue()) this.cellsSurfaceState[i + 1][j - 1] = State.OPENED.getValue();

                }

            //Research of the top edge of the game field.
                else if (contentsOfCells[i][j] == 0 && i == 0 && j > 0 &&
                        j < contentsOfCells[i].length - 1 &&
                        cellsSurfaceState[i][j] == State.OPENED.getValue()) {

                    // if cell is down and to the right empty and closed
                    // and cell on the right contains a positive number
                    // and cell down contains a positive number
                    // then to enter down and to the right cell and begin of research
                    if (contentsOfCells[i + 1][j + 1] == 0 &&
                            contentsOfCells[i][j + 1] > 0 && contentsOfCells[i + 1][j] > 0 &&
                            cellsSurfaceState[i + 1][j + 1] == State.CLOSED.getValue()) {
                        openCell(i + 1, j + 1);
                    }
                    // if cell is down and to the left empty and closed
                    // and cell on the left contains a positive number
                    // and cell down contains a positive number
                    // then to enter down and to the left cell and begin of research
                    if (contentsOfCells[i + 1][j - 1] == 0 &&
                            contentsOfCells[i][j - 1] > 0 && contentsOfCells[i + 1][j] > 0 &&
                            cellsSurfaceState[i + 1][j - 1] == State.CLOSED.getValue()) {
                        openCell(i + 1, j - 1);
                    }

                    //open diagonals cells, if they don't have flag
                    if (dataOfMarks[i + 1][j + 1] != Mark.FLAG.getValue()) this.cellsSurfaceState[i + 1][j + 1] = State.OPENED.getValue();
                    if (dataOfMarks[i + 1][j - 1] != Mark.FLAG.getValue()) this.cellsSurfaceState[i + 1][j - 1] = State.OPENED.getValue();

                }

            //Research of the bottom edge of the game field.
                else if (contentsOfCells[i][j] == 0 && i == contentsOfCells.length - 1 &&
                        j > 0 && j < contentsOfCells[i].length - 1 &&
                        cellsSurfaceState[i][j] == State.OPENED.getValue()) {

                    // if cell is top and to the left empty and closed
                    // and cell on the left contains a positive number
                    // and cell on the top contains a positive number
                    // then to enter top and to the left cell and begin of research
                    if (contentsOfCells[i - 1][j - 1] == 0 &&
                            contentsOfCells[i][j - 1] > 0 && contentsOfCells[i - 1][j] > 0 &&
                            cellsSurfaceState[i - 1][j - 1] == State.CLOSED.getValue()) {
                        openCell(i - 1, j - 1);
                    }

                    // if cell is top and to the right empty and closed
                    // and cell on the right contains a positive number
                    // and cell on the top contains a positive number
                    // then to enter top and to the right cell and begin of research
                    if (contentsOfCells[i - 1][j + 1] == 0 &&
                            contentsOfCells[i][j + 1] > 0 && contentsOfCells[i - 1][j] > 0 &&
                            cellsSurfaceState[i - 1][j + 1] == State.CLOSED.getValue()) {
                        openCell(i - 1, j + 1);
                    }

                    //open diagonals cells, if they don't have flag
                    if (dataOfMarks[i - 1][j - 1] != Mark.FLAG.getValue()) this.cellsSurfaceState[i - 1][j - 1] = State.OPENED.getValue();
                    if (dataOfMarks[i - 1][j + 1] != Mark.FLAG.getValue()) this.cellsSurfaceState[i - 1][j + 1] = State.OPENED.getValue();
                }

            //Research of the left edge of the game field.
                else if (contentsOfCells[i][j] == 0 && i > 0 && j == 0 &&
                        i < contentsOfCells.length - 1 &&
                        cellsSurfaceState[i][j] == State.OPENED.getValue()) {

                    // if cell is on the right and on the top empty and closed
                    // and cell on the right contains a positive number
                    // and cell on the top contains a positive number
                    // then to enter right and to the top cell and begin of research
                    if (contentsOfCells[i + 1][j + 1] == 0 &&
                            contentsOfCells[i][j + 1] > 0 && contentsOfCells[i + 1][j] > 0 &&
                            cellsSurfaceState[i + 1][j + 1] == State.CLOSED.getValue()) {
                        openCell(i + 1, j + 1);
                    }
                    // if cell is on the right and on the down empty and closed
                    // and cell on the right contains a positive number
                    // and cell on the down contains a positive number
                    // then to enter right and to the down cell and begin of research
                    if (contentsOfCells[i - 1][j + 1] == 0 &&
                            contentsOfCells[i][j + 1] > 0 && contentsOfCells[i - 1][j] > 0 &&
                            cellsSurfaceState[i - 1][j + 1] == State.CLOSED.getValue()) {
                        openCell(i - 1, j + 1);
                    }

                    //open diagonals cells, if they don't have flag
                    if (dataOfMarks[i + 1][j + 1] != Mark.FLAG.getValue()) this.cellsSurfaceState[i + 1][j + 1] = State.OPENED.getValue();
                    if (dataOfMarks[i - 1][j + 1] != Mark.FLAG.getValue()) this.cellsSurfaceState[i - 1][j + 1] = State.OPENED.getValue();

                }

            //Research of the right edge of the game field.
                else if (contentsOfCells[i][j] == 0 && i > 0 &&
                        i < contentsOfCells.length - 1 &&
                        j == contentsOfCells[i].length - 1 &&
                        cellsSurfaceState[i][j] == State.OPENED.getValue()) {

                    // if cell is on the left and on the down empty and closed
                    // and cell on the left contains a positive number
                    // and cell on the down contains a positive number
                    // then to enter left and to the down cell and begin of research
                    if (contentsOfCells[i - 1][j - 1] == 0 &&
                            contentsOfCells[i][j - 1] > 0 && contentsOfCells[i - 1][j] > 0 &&
                            cellsSurfaceState[i - 1][j - 1] == State.CLOSED.getValue()) {
                        openCell(i - 1, j - 1);
                    }

                    // if cell is on the left and on the top empty and closed
                    // and cell on the left contains a positive number
                    // and cell on the top contains a positive number
                    // then to enter left and to the top cell and begin of research
                    if (contentsOfCells[i + 1][j - 1] == 0 &&
                            contentsOfCells[i][j - 1] > 0 && contentsOfCells[i + 1][j] > 0 &&
                            cellsSurfaceState[i + 1][j - 1] == State.CLOSED.getValue()) {
                        openCell(i + 1, j - 1);
                    }

                    //open diagonals cells, if they don't have flag
                    if (dataOfMarks[i - 1][j - 1] != Mark.FLAG.getValue()) this.cellsSurfaceState[i - 1][j - 1] = State.OPENED.getValue();
                    if (dataOfMarks[i + 1][j - 1] != Mark.FLAG.getValue()) this.cellsSurfaceState[i + 1][j - 1] = State.OPENED.getValue();

                }

            //Research of the top left corner cell
                else if (contentsOfCells[i][j] == 0 && i == 0 && j == 0 &&
                        cellsSurfaceState[i][j] == State.OPENED.getValue()) {

                    // if cell is down and to the right empty and closed
                    // and cell on the right contains a positive number
                    // and cell down contains a positive number
                    // then to enter down and to the right cell and begin of research
                    if (contentsOfCells[i + 1][j + 1] == 0 &&
                            contentsOfCells[i][j + 1] > 0 && contentsOfCells[i + 1][j] > 0 &&
                            cellsSurfaceState[i + 1][j + 1] == State.CLOSED.getValue()) {
                        openCell(i + 1, j + 1);
                    }

                    //open diagonals cell, if they don't have flag
                    if (dataOfMarks[i + 1][j + 1] != Mark.FLAG.getValue()) this.cellsSurfaceState[i + 1][j + 1] = State.OPENED.getValue();
                }

            //Research of the top right corner cell
                else if (contentsOfCells[i][j] == 0 && i == 0 &&
                        j == contentsOfCells[i].length - 1 &&
                        cellsSurfaceState[i][j] == State.OPENED.getValue()) {

                    // if cell is down and to the left empty and closed
                    // and cell on the left contains a positive number
                    // and cell down contains a positive number
                    // then to enter down and to the left cell and begin of research
                    if (contentsOfCells[i + 1][j - 1] == 0 &&
                            contentsOfCells[i][j - 1] > 0 && contentsOfCells[i + 1][j] > 0 &&
                            cellsSurfaceState[i + 1][j - 1] == State.CLOSED.getValue()) {
                        openCell(i + 1, j - 1);
                    }

                    //open diagonals cell, if they don't have flag
                    if (dataOfMarks[i + 1][j - 1] != Mark.FLAG.getValue()) this.cellsSurfaceState[i + 1][j - 1] = State.OPENED.getValue();
                }

            //Research of the down left corner cell
                else if (contentsOfCells[i][j] == 0 && i == contentsOfCells.length - 1 && j == 0 &&
                        cellsSurfaceState[i][j] == State.OPENED.getValue()) {

                    // if cell is top and to the right empty and closed
                    // and cell on the right contains a positive number
                    // and cell on the top contains a positive number
                    // then to enter top and to the right cell and begin of research
                    if (contentsOfCells[i - 1][j + 1] == 0 &&
                            contentsOfCells[i][j + 1] > 0 && contentsOfCells[i - 1][j] > 0 &&
                            cellsSurfaceState[i - 1][j + 1] == State.CLOSED.getValue()) {
                        openCell(i - 1, j + 1);
                    }

                    //open diagonals cell, if they don't have flag
                    if (dataOfMarks[i - 1][j + 1] != Mark.FLAG.getValue()) this.cellsSurfaceState[i - 1][j + 1] = State.OPENED.getValue();

                }

            //Research of the down right corner cell
                else if (contentsOfCells[i][j] == 0 && i == contentsOfCells.length - 1 &&
                        j == contentsOfCells[i].length - 1 &&
                        cellsSurfaceState[i][j] == State.OPENED.getValue()) {

                    // if cell is top and to the left empty and closed
                    // and cell on the left contains a positive number
                    // and cell on the top contains a positive number
                    // then to enter top and to the left cell and begin of research
                    if (contentsOfCells[i - 1][j - 1] == 0 &&
                            contentsOfCells[i][j - 1] > 0 && contentsOfCells[i - 1][j] > 0 &&
                            cellsSurfaceState[i - 1][j - 1] == State.CLOSED.getValue()) {
                        openCell(i - 1, j - 1);
                    }

                    //open diagonals cell, if they don't have flag
                    if (dataOfMarks[i - 1][j - 1] != Mark.FLAG.getValue()) this.cellsSurfaceState[i - 1][j - 1] = State.OPENED.getValue();
                }
            }
        }
    }

    /**
     * The main method of recursive researching field.
     * The method research of field from current cell.
     * @param row the place of cell in column
     * @param col The place of cell in row
     */
    @Override
    public void openCell(int row, int col) {
        if(dataOfMarks[row][col]!=Mark.FLAG.getValue()) {
            this.cellsSurfaceState[row][col] = State.OPENED.getValue();

            //If open cell contain bomb.
            if (contentsOfCells[row][col] < 0) {
                setBomb();
            }

            //If open cell is empty
            else if (contentsOfCells[row][col] == 0) {
                checkUp(row, col);
                checkDown(row, col);
                checkRight(row, col);
                checkLeft(row, col);
            }

            /*Collect information about the researched(open) cells
              from all the supplementing recursive methods in common array. */
            for (int i = 0; i < cellsSurfaceState.length; i++) {
                for (int j = 0; j < cellsSurfaceState[i].length; j++) {
                    if(surfaceCheckedUp[i][j]|| surfaceCheckedDown[i][j]||
                            surfaceCheckedRight[i][j]|| surfaceCheckedLeft[i][j]){
                        cellsSurfaceState[i][j]= State.OPENED.getValue();
                    }
                }
            }
            researchDiagonalCells();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getQuantityCloseCells() {
        int result = 0;
        for (int i = 0; i < cellsSurfaceState.length; i++) {
            for (int j = 0; j < cellsSurfaceState[i].length; j++) {
                if(cellsSurfaceState[i][j] == State.CLOSED.getValue()) result++;
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getBrightCellCol() {
        return brightCellCol;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int getBrightCellRow() {
        return brightCellRow;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBrightCell(int row, int col) {
        this.brightCellRow = row;
        this.brightCellCol = col;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMark(int row, int col) {
        if(cellsSurfaceState[row][col] == State.CLOSED.getValue() && dataOfMarks[row][col]==Mark.WITHOUT.getValue()){
            dataOfMarks[row][col] = Mark.FLAG.getValue();
        }
        else if(cellsSurfaceState[row][col] == State.CLOSED.getValue() && dataOfMarks[row][col]==Mark.FLAG.getValue()){
            dataOfMarks[row][col] = Mark.QUESTION.getValue();
        }
        else dataOfMarks[row][col] = Mark.WITHOUT.getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int[][] getDataOfMarks() {
        return dataOfMarks;
    }

    /**
     * Return current value of flag marks that was set on the field.
     */
    @Override
    public int getQuantityFlagMarks() {
        int result=0;
        for (int i = 0; i < dataOfMarks.length; i++) {
            for (int j = 0; j < dataOfMarks[i].length; j++) {
                if(dataOfMarks[i][j]==1) result++;
            }
        }
        return result;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBomb() {
        return bomb;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBomb() {
        bomb = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void takeOffBomb() {
        bomb = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean[][] getCellsSurfaceState() {
        return cellsSurfaceState;
    }

}
