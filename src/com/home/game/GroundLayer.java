package com.home.game;

public class GroundLayer implements UpLayer {
    private boolean[][] innerDataArr;
    private int[][] innerMarkArr;
    private int[][] dataField;
    private int[] brightRect = {-1,-1};
    private int height;
    private int width;
    private boolean bomb = false;

    private boolean[][] fieldCheckedUp;
    private boolean[][] fieldCheckedDown;
    private boolean[][] fieldCheckedLeft;
    private boolean[][] fieldCheckedRight;

    public GroundLayer(int[][] dataField){
        this(10,10, dataField);
    }
    public GroundLayer(int height, int width, int[][]dataField){
        this.height = height;
        this.width = width;
        this.dataField = dataField;
        innerDataArr = new boolean[height][width];
        innerMarkArr = new int[height][width];
        fieldCheckedUp = new boolean[height][width];
        fieldCheckedDown = new boolean[height][width];
        fieldCheckedLeft = new boolean[height][width];
        fieldCheckedRight = new boolean[height][width];
    }

    private void checkUp(int y, int x) {
        if (y>0&&innerMarkArr[y-1][x]!=1) {
            fieldCheckedUp[y][x] = true;
            if (dataField[y-1][x] == 0) {
                checkUp(y-1, x);
                if(!fieldCheckedRight[y-1][x])checkRight(y-1,x);
                if(!fieldCheckedLeft[y-1][x])checkLeft(y-1,x);
            }
            this.innerDataArr[y-1][x] = true;
        }
    }
    private void checkDown(int y, int x){
        //checkDown
        if(y<dataField.length-1&&innerMarkArr[y+1][x]!=1){
            fieldCheckedDown[y][x] = true;
            if(dataField[y+1][x]==0){
                checkDown(y+1,x);
                if(!fieldCheckedRight[y+1][x])checkRight(y+1,x);
                if(!fieldCheckedLeft[y+1][x])checkLeft(y+1,x);
            }
            this.innerDataArr[y+1][x] = true;
        }
    }
    private void checkLeft(int y, int x){
        //checkLeft
        if(x>0&&innerMarkArr[y][x-1]!=1){
            fieldCheckedLeft[y][x] = true;
            if(dataField[y][x-1]==0){
                if(!fieldCheckedUp[y][x-1])checkUp(y,x-1);
                checkLeft(y,x-1);
                if(!fieldCheckedDown[y][x-1])checkDown(y,x-1);
            }
            this.innerDataArr[y][x-1] = true;
        }
    }
    private void checkRight(int y, int x){
        //checkRight
        if(x<dataField[y].length-1&&innerMarkArr[y][x+1]!=1){
            fieldCheckedRight[y][x] = true;
            if(dataField[y][x+1]==0){
                checkRight(y,x+1);
                if(!fieldCheckedUp[y][x+1])checkUp(y,x+1);
                if(!fieldCheckedDown[y][x+1])checkDown(y,x+1);
            }
            this.innerDataArr[y][x+1] = true;
        }

    }
    @Override
    public void openCell(int y, int x) {
        if(innerMarkArr[y][x]!=1) {
            if (dataField[y][x] < 0) {
                setBomb();
            }

            if (dataField[y][x] == 0) {
                checkUp(y, x);
                checkDown(y, x);
                checkRight(y, x);
                checkLeft(y, x);
            }

            this.innerDataArr[y][x] = true;

            for (int i = 0; i < innerDataArr.length; i++) {
                for (int j = 0; j < innerDataArr[i].length; j++) {
                    if (fieldCheckedUp[i][j]||fieldCheckedDown[i][j]||
                            fieldCheckedRight[i][j]||fieldCheckedLeft[i][j]) {
                        innerDataArr[i][j] = true;
                    }
                }
            }

        for (int i = 0; i < dataField.length; i++) {
            for (int j = 0; j < dataField[i].length; j++) {
                //обход центра
                if(dataField[i][j]==0&&i>0&&j>0&&
                        i<dataField.length-1&&
                        j<dataField[i].length-1&&
                        /*(fieldCheckedUp[i][j]||fieldCheckedDown[i][j]||
                         fieldCheckedRight[i][j]||fieldCheckedLeft[i][j])*/
                        innerDataArr[i][j]) {
                    if(innerMarkArr[i + 1][j + 1]!=1) this.innerDataArr[i + 1][j + 1] = true;
                    if(innerMarkArr[i - 1][j - 1]!=1) this.innerDataArr[i - 1][j - 1] = true;
                    if(innerMarkArr[i - 1][j + 1]!=1) this.innerDataArr[i - 1][j + 1] = true;
                    if(innerMarkArr[i + 1][j - 1]!=1) this.innerDataArr[i + 1][j - 1] = true;

                    //диагональ вниз-вправо
                    if(dataField[i+1][j+1]==0&&
                            (dataField[i][j+1]>0/*||innerMarkArr[i][j+1]==1*/) &&
                            (dataField[i+1][j]>0/*||innerMarkArr[i+1][j]==1*/)&&
                            !innerDataArr[i+1][j+1]/*&&
                            (!fieldCheckedUp[i+1][j+1]   || !fieldCheckedDown[i+1][j+1] ||
                             !fieldCheckedLeft[i+1][j+1] || !fieldCheckedRight[i+1][j+1])*/
                            ){
                        openCell(i+1,j+1);
                    }
                    if(dataField[i+1][j+1]==0&&innerMarkArr[i][j+1]==1&&innerMarkArr[i+1][j]==1&&
                            !innerDataArr[i+1][j+1]){
                        openCell(i+1,j+1);
                    }
                    //диагональ вверх-влево
                    if(dataField[i-1][j-1]==0&&
                            (dataField[i][j-1]>0) &&
                            (dataField[i-1][j]>0) &&
                            !innerDataArr[i-1][j-1]/*&&
                            (!fieldCheckedUp[i-1][j-1]   || !fieldCheckedDown[i-1][j-1] ||
                             !fieldCheckedLeft[i-1][j-1] || !fieldCheckedRight[i-1][j-1])*/) {
                        openCell(i-1,j-1);
                    }
                    if(dataField[i-1][j-1]==0&&innerMarkArr[i][j-1]==1&&innerMarkArr[i-1][j]==1&&
                            !innerDataArr[i-1][j-1]){
                        openCell(i-1,j-1);
                    }

                    //диагональ вверх-вправо
                    if(dataField[i-1][j+1]==0&&
                            (dataField[i][j+1]>0) &&
                            (dataField[i-1][j]>0) &&
                            !innerDataArr[i-1][j+1]/*&&
                            (!fieldCheckedUp[i-1][j+1]   || !fieldCheckedDown[i-1][j+1] ||
                             !fieldCheckedLeft[i-1][j+1] || !fieldCheckedRight[i-1][j+1])*/) {
                        openCell(i-1,j+1);
                    }
                    if(dataField[i-1][j+1]==0&&innerMarkArr[i][j+1]==1&&innerMarkArr[i-1][j]==1&&
                            !innerDataArr[i-1][j+1]){
                        openCell(i-1,j+1);
                    }

                    //диагональ вниз-влево
                    if(dataField[i+1][j-1]==0&&
                            (dataField[i][j-1]>0||innerMarkArr[i][j-1]==1) &&
                            (dataField[i+1][j]>0||innerMarkArr[i+1][j]==1) &&
                            !innerDataArr[i+1][j-1]/*&&
                            (!fieldCheckedUp[i+1][j-1]   || !fieldCheckedDown[i+1][j-1] ||
                             !fieldCheckedLeft[i+1][j-1] || !fieldCheckedRight[i+1][j-1])*/) {
                        openCell(i+1,j-1);
                    }
                    if(dataField[i+1][j-1]==0&&innerMarkArr[i][j-1]==1&&innerMarkArr[i+1][j]==1&&
                            !innerDataArr[i+1][j-1]){
                        openCell(i+1,j-1);
                    }
                }
                //обход верха
                else if(dataField[i][j]==0&&i==0&&j>0&&
                        j<dataField[i].length-1&&
                        (fieldCheckedUp[i][j]||fieldCheckedDown[i][j]||
                         fieldCheckedRight[i][j]||fieldCheckedLeft[i][j])){
                    if(innerMarkArr[i + 1][j + 1]!=1) this.innerDataArr[i + 1][j + 1] = true;
                    if(innerMarkArr[i + 1][j - 1]!=1) this.innerDataArr[i + 1][j - 1] = true;

                    if(dataField[i+1][j+1]==0&&dataField[i][j+1]>0&&dataField[i+1][j]>0&&
                            (!fieldCheckedUp[i+1][j+1]   || !fieldCheckedDown[i+1][j+1] ||
                             !fieldCheckedLeft[i+1][j+1] || !fieldCheckedRight[i+1][j+1])) {
                        openCell(i+1,j+1);
                    }
                    if(dataField[i+1][j-1]==0&&dataField[i][j-1]>0&&dataField[i+1][j]>0&&
                            (!fieldCheckedUp[i+1][j-1]   || !fieldCheckedDown[i+1][j-1] ||
                             !fieldCheckedLeft[i+1][j-1] || !fieldCheckedRight[i+1][j-1])) {
                        openCell(i+1,j-1);
                    }
                }
                //обход низа
                else if(dataField[i][j]==0&&i==dataField.length-1&&
                        j>0&&j<dataField[i].length-1&&
                        (fieldCheckedUp[i][j]||fieldCheckedDown[i][j]||
                         fieldCheckedRight[i][j]||fieldCheckedLeft[i][j])){
                    if(innerMarkArr[i - 1][j - 1]!=1) this.innerDataArr[i - 1][j - 1] = true;
                    if(innerMarkArr[i - 1][j + 1]!=1) this.innerDataArr[i - 1][j + 1] = true;

                    if(dataField[i-1][j-1]==0&&dataField[i][j-1]>0&&dataField[i-1][j]>0&&
                            (!fieldCheckedUp[i-1][j-1]   || !fieldCheckedDown[i-1][j-1] ||
                             !fieldCheckedLeft[i-1][j-1] || !fieldCheckedRight[i-1][j-1])) {
                        openCell(i-1,j-1);
                    }

                    if(dataField[i-1][j+1]==0&&dataField[i][j+1]>0&&dataField[i-1][j]>0&&
                            (!fieldCheckedUp[i-1][j+1]   || !fieldCheckedDown[i-1][j+1] ||
                             !fieldCheckedLeft[i-1][j+1] || !fieldCheckedRight[i-1][j+1])) {
                        openCell(i-1,j+1);
                    }
                }
                //обход левой границы
                else if(dataField[i][j]==0&&i>0&&j==0&&
                        i<dataField.length-1&&
                        (fieldCheckedUp[i][j]||fieldCheckedDown[i][j]||
                         fieldCheckedRight[i][j]||fieldCheckedLeft[i][j])) {
                    if(innerMarkArr[i + 1][j + 1]!=1) this.innerDataArr[i + 1][j + 1] = true;
                    if(innerMarkArr[i - 1][j + 1]!=1) this.innerDataArr[i - 1][j + 1] = true;

                    if (dataField[i + 1][j + 1] == 0 && dataField[i][j + 1] > 0 && dataField[i + 1][j] > 0 &&
                            (!fieldCheckedUp[i + 1][j + 1] || !fieldCheckedDown[i + 1][j + 1] ||
                             !fieldCheckedLeft[i + 1][j + 1] || !fieldCheckedRight[i + 1][j + 1])) {
                        openCell(i + 1, j + 1);
                    }
                    if (dataField[i - 1][j + 1] == 0 && dataField[i][j + 1] > 0 && dataField[i - 1][j] > 0 &&
                            (!fieldCheckedUp[i - 1][j + 1] || !fieldCheckedDown[i - 1][j + 1] ||
                             !fieldCheckedLeft[i - 1][j + 1] || !fieldCheckedRight[i - 1][j + 1])) {
                        openCell(i - 1, j + 1);
                    }
                }

                //обход правой границы
                else if(dataField[i][j]==0&&i>0&&
                        i<dataField.length-1&&
                        j==dataField[i].length-1&&
                        (fieldCheckedUp[i][j]||fieldCheckedDown[i][j]||
                         fieldCheckedRight[i][j]||fieldCheckedLeft[i][j])) {
                    if(innerMarkArr[i - 1][j - 1]!=1) this.innerDataArr[i - 1][j - 1] = true;
                    if(innerMarkArr[i + 1][j - 1]!=1) this.innerDataArr[i + 1][j - 1] = true;
                    if (dataField[i - 1][j - 1] == 0 && dataField[i][j - 1] > 0 && dataField[i - 1][j] > 0 &&
                            (!fieldCheckedUp[i - 1][j - 1] || !fieldCheckedDown[i - 1][j - 1] ||
                             !fieldCheckedLeft[i - 1][j - 1] || !fieldCheckedRight[i - 1][j - 1])) {
                        openCell(i - 1, j - 1);
                    }
                    if (dataField[i + 1][j - 1] == 0 && dataField[i][j - 1] > 0 && dataField[i + 1][j] > 0 &&
                            (!fieldCheckedUp[i + 1][j - 1] || !fieldCheckedDown[i + 1][j - 1] ||
                             !fieldCheckedLeft[i + 1][j - 1] || !fieldCheckedRight[i + 1][j - 1])) {
                        openCell(i + 1, j - 1);
                    }
                }
                //проверка левого верхнего угла
                else if(dataField[i][j]==0&&i==0&&j==0&&
                        (fieldCheckedUp[i][j]||fieldCheckedDown[i][j]||
                         fieldCheckedRight[i][j]||fieldCheckedLeft[i][j])) {
                    if(innerMarkArr[i + 1][j + 1]!=1) this.innerDataArr[i + 1][j + 1] = true;

                    if (dataField[i + 1][j + 1] == 0 && dataField[i][j + 1] > 0 && dataField[i + 1][j] > 0 &&
                            (!fieldCheckedUp[i + 1][j + 1] || !fieldCheckedDown[i + 1][j + 1] ||
                             !fieldCheckedLeft[i + 1][j + 1] || !fieldCheckedRight[i + 1][j + 1])) {
                        openCell(i + 1, j + 1);
                    }
                }
                //проверка правого верхнего угла
                else if(dataField[i][j]==0&&i==0&&
                        j==dataField[i].length-1&&
                        (fieldCheckedUp[i][j]||fieldCheckedDown[i][j]||
                         fieldCheckedRight[i][j]||fieldCheckedLeft[i][j])) {
                    if(innerMarkArr[i + 1][j - 1]!=1) this.innerDataArr[i + 1][j - 1] = true;

                    if (dataField[i + 1][j - 1] == 0 && dataField[i][j - 1] > 0 && dataField[i + 1][j] > 0 &&
                            (!fieldCheckedUp[i + 1][j - 1] || !fieldCheckedDown[i + 1][j - 1] ||
                             !fieldCheckedLeft[i + 1][j - 1] || !fieldCheckedRight[i + 1][j - 1])) {
                        openCell(i + 1, j - 1);
                    }
                }
                //проверка левого нижнего угла
                else if(dataField[i][j]==0&&i==dataField.length-1&&j==0&&
                        (fieldCheckedUp[i][j]||fieldCheckedDown[i][j]||
                         fieldCheckedRight[i][j]||fieldCheckedLeft[i][j])) {
                    if(innerMarkArr[i - 1][j + 1]!=1) this.innerDataArr[i - 1][j + 1] = true;

                    if (dataField[i - 1][j + 1] == 0 && dataField[i][j + 1] > 0 && dataField[i - 1][j] > 0 &&
                            (!fieldCheckedUp[i - 1][j + 1] || !fieldCheckedDown[i - 1][j + 1] ||
                             !fieldCheckedLeft[i - 1][j + 1] || !fieldCheckedRight[i - 1][j + 1])) {
                        openCell(i - 1, j + 1);
                    }
                }
                //проверка правого нижнего угла
                else if(dataField[i][j]==0&&i==dataField.length-1&&
                        j==dataField[i].length-1&&
                        (fieldCheckedUp[i][j]||fieldCheckedDown[i][j]||
                         fieldCheckedRight[i][j]||fieldCheckedLeft[i][j])) {
                    if(innerMarkArr[i - 1][j - 1]!=1) this.innerDataArr[i - 1][j - 1] = true;

                    if (dataField[i - 1][j - 1] == 0 && dataField[i][j - 1] > 0 && dataField[i - 1][j] > 0 &&
                            (!fieldCheckedUp[i - 1][j - 1] || !fieldCheckedDown[i - 1][j - 1] ||
                             !fieldCheckedLeft[i - 1][j - 1] || !fieldCheckedRight[i - 1][j - 1])) {
                        openCell(i - 1, j - 1);
                    }
                }
            }

        }
        }

    }

    @Override
    public boolean[][] getDataLayer() {
        return innerDataArr;
    }

    @Override
    public void setMark(int y, int x) {
        if(innerDataArr[y][x]==false&&innerMarkArr[y][x]==0) innerMarkArr[y][x] = 1;
        else if(innerDataArr[y][x]==false&&innerMarkArr[y][x]==1) innerMarkArr[y][x] = 2;
        else innerMarkArr[y][x] = 0;
    }

    @Override
    public int[] getBrightCell() {
        return brightRect;
    }

    @Override
    public void setBrightCell(int y, int x) {
        this.brightRect[0] = y;
        this.brightRect[1] = x;
    }

    @Override
    public int[][] getMarkInfo() {
        return innerMarkArr;
    }

    @Override
    public boolean isBomb() {
        return bomb;
    }

    @Override
    public void takeOffBomb() {
        bomb = false;
    }

    @Override
    public void setBomb() {
        bomb = true;
    }
}
