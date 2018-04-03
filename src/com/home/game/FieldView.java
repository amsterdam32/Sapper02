package com.home.game;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

/**
 * The <code>FieldView</code> class that describe graphics show of game field,
 */
public class FieldView<T extends GameField> implements Observer, ActionListener {
    /**
     * Object stores model of game field.,
     */
    FieldModel fieldModel;
    /**
     * Object stores controller of game field.,
     */
    FieldController controller;

    /**
     * Main frame for application
     */
    JFrame mainFrame;
    /**
     * Information item that shows time game.
     */
    GameInformationItem timePanel;
    /**
     * Information item that shows quantity not marked bombs..
     */
    GameInformationItem minePanel;
    /**
     * Sittings frame for application.
     */
    JFrame settingsFrame;
    /**
     * Object what contains graphical shows of game field.
     */
    T gameField;
    /**
     * Panel for representation and setup options of level difficulty game.
     */
    JPanel levelOfDifficultly;

    JButton settingsOk;
    JButton settingsCancel;
    ButtonGroup settingsSelector;
    JRadioButton easyButton;
    JRadioButton normalButton;
    JRadioButton hardButton;
    JRadioButton personalButton;
    /**
     * Information field about height game field that sets in personal settings.
     */
    JLabel heightFieldLabel;
    /**
     * Information field about width game field that sets in personal settings.
     */
    JLabel widthFieldLabel;
    /**
     * Information field about quantity bombs in game field that sets in personal settings.
     */
    JLabel numberBombsFieldLabel;
    /**
     * Field for setup height game field that sets in personal settings.
     */
    JTextField heightField;
    /**
     * Field for setup width game field that sets in personal settings.
     */
    JTextField widthField;
    /**
     * Field for setup quantity bombs in game field that sets in personal settings.
     */
    JTextField numberBombsField;

    /**
     * Value of the width for counting gap between mainframe and game field.
     */
    final int WIDTH_ADD = 6+50;
    /**
     * Value of the height for counting gap between mainframe and game field.
     */
    final int HEIGHT_ADD = 52+125;


    JMenuBar menuBar;
    JMenu menuGame;
    JMenu menuInfo;
    JMenuItem startGame;
    JMenuItem exitGame;
    JMenuItem settingsGame;
    JMenuItem aboutGame;

    public FieldView(FieldController ci, FieldModel fm){
        fieldModel = fm;
        controller = ci;
        fieldModel.registerObserver(this);
    }

    /**
     * Method sets type of game field with images or not.
     * @param gameFieldType
     */
    public void setGameFieldType(T gameFieldType){
        this.gameField = gameFieldType;
    }

    /**
     * Main method for graphically shows and settings view of the application.
     */
    public void createView(){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                int gameWidth = fieldModel.getColsInField()*fieldModel.getCellSize();
                int gameHeight = fieldModel.getRowsInField()*fieldModel.getCellSize();

                createMainFrame(gameHeight, gameWidth);
                createMenuBar();
                getImgsFieldModel();
                gameField.setPreferredSize(new Dimension(gameWidth,gameHeight));

                SpringLayout springLayout = new SpringLayout();
                positioningGameField(springLayout);
                positioningTimePanel(springLayout);
                positioningMinePanel(springLayout);

                addGameFieldMouseMovingHandler();
                addGameFieldMouseExitingHandler();
                addGameFieldMousePressingHandler();
            }
        });
    }

    /**
     * Method creates and settings parameters for main frame of application.
     * @param gameHeight Height of game field.
     * @param gameWidth Width of game field.
     */
    private void createMainFrame(int gameHeight, int gameWidth){
        mainFrame = new JFrame("Sapper");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int fWidth = gameWidth+WIDTH_ADD;
        int fHeight = gameHeight+HEIGHT_ADD;
        mainFrame.setSize(new Dimension(fWidth,fHeight));
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
    }

    /**
     * Method creates and settings parameters for menu bar of application.
     */
    private void createMenuBar(){
        menuBar = new JMenuBar();
        menuGame = new JMenu(" Game ");
        addMenuBarMouseEnteredHandler(menuGame);
        addMenuBarMouseExitingHandler(menuGame);

        startGame = new JMenuItem("New game");
        settingsGame = new JMenuItem("Settings");
        exitGame = new JMenuItem("Exit");

        menuGame.add(startGame);
        startGame.addActionListener((e)->controller.newGame(fieldModel.getRowsInField(), fieldModel.getColsInField(), fieldModel.getQuantityBombs()));
        menuGame.addSeparator();
        menuGame.add(settingsGame);
        settingsGame.addActionListener((e)->createSettingsFrame());
        menuGame.addSeparator();
        menuGame.add(exitGame);
        exitGame.addActionListener((e)->controller.exitGame());
        menuBar.add(menuGame);
        mainFrame.setJMenuBar(menuBar);

        menuInfo = new JMenu(" Info ");
        aboutGame = new JMenuItem(" About game");
        aboutGame.addActionListener((e)->JOptionPane.showMessageDialog(mainFrame,
                "Приложение разработано по\n" +
                        "аналоги с многим знакомой игрой\n" +
                        "сапер, и реализует большую\n" +
                        "часть её функционала.\n" +
                        "Создано с целью демонстрации\n" +
                        "навыков работы на языке Java.\n\n" +
                        "Разработчик:                 Початрев В.В      \n" +
                        "email:  v.postman2014@yandex.com\n\n"));
        addMenuBarMouseEnteredHandler(menuInfo);
        addMenuBarMouseExitingHandler(menuInfo);
        menuInfo.add(aboutGame);
        menuBar.add(menuInfo);
    }

    /**
     * Method to set behaviour item menu when mouse entered on it.
     * @param menuItem menu item JMenu
     */
    private void addMenuBarMouseEnteredHandler(JMenu menuItem){
        menuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                menuItem.setOpaque(true);
                menuItem.setBackground(new Color(210,210,210));
            };
        });
    }

    /**
     * Method to set behaviour item menu when mouse exiting from it.
     * @param menuItem menu item JMenu
     */
    private void addMenuBarMouseExitingHandler(JMenu menuItem){
        menuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                menuItem.setOpaque(false);
                menuItem.setBackground(Color.GRAY);
            };
        });
    }

    /**
     * Method create frame with settings for select parameters new game.
     */
    private void createSettingsFrame(){
        settingsFrame = new JFrame("Settings");
        settingsFrame.setSize(new Dimension(355,280));
        settingsFrame.setLocationRelativeTo(mainFrame);
        settingsFrame.setResizable(false);
        settingsFrame.setVisible(true);
        settingsFrame.setLayout(null);

        levelOfDifficultly = new JPanel();
        settingLevelOfDifficultlyPanel();
        settingsFrame.add(levelOfDifficultly);

        settingsOk = new JButton("Ok");
        settingsOk.addActionListener((e)-> {
            if(personalButton.isSelected()){
                int height = 0;
                int width = 0;
                int numberBombs = 0;
                try {
                    height = Integer.parseInt(heightField.getText());
                    width = Integer.parseInt(widthField.getText());
                    numberBombs = Integer.parseInt(numberBombsField.getText());
                    if(height<9||height>21||width<9||width>30||numberBombs<10||numberBombs>668) throw new NumberFormatException();
                    if(numberBombs>height*width) throw new IndexOutOfBoundsException();
                    settingsFrame.dispose();
                    controller.newGame(height, width, numberBombs);
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(settingsFrame, "You inputted incorrect the number format!");
                }
                catch (IndexOutOfBoundsException e2){
                    JOptionPane.showMessageDialog(settingsFrame, "You inputted incorrect number bombs!");
                }
            }
            else {
                settingsFrame.dispose();
                if (easyButton.isSelected()) controller.newGame(9, 9, 10);
                else if (normalButton.isSelected()) controller.newGame(16, 16, 40);
                else if (hardButton.isSelected()) controller.newGame(16, 30, 99);
            }
        });

        settingsCancel = new JButton("Cancel");
        settingsCancel.addActionListener((e)->settingsFrame.dispose());
        settingsFrame.add(settingsOk);
        settingsFrame.add(settingsCancel);

        settingsCancel.setBounds(203,220,75,25);
        settingsOk.setBounds(287,220,50,25);
    }

    /**
     * Method sets and placed elements on panel
     * that show parameters for select of difficulty game.
     */
    private void settingLevelOfDifficultlyPanel(){
        levelOfDifficultly.setLayout(null);
        Border etched = BorderFactory.createEtchedBorder();
        Border titled = BorderFactory.createTitledBorder(etched, "Level of difficultly");
        levelOfDifficultly.setBorder(titled);

        settingsSelector = new ButtonGroup();
        easyButton = new JRadioButton("Easy", true);
        normalButton = new JRadioButton("Normal");
        hardButton = new JRadioButton("Hard");
        personalButton = new JRadioButton("Personal");

        settingsSelector.add(easyButton);
        activatePersonalOptions(easyButton, false);

        settingsSelector.add(normalButton);
        activatePersonalOptions(normalButton, false);

        settingsSelector.add(hardButton);
        activatePersonalOptions(hardButton, false);

        settingsSelector.add(personalButton);
        activatePersonalOptions(personalButton, true);

        heightFieldLabel = new JLabel("Height (9-21)");
        heightFieldLabel.setEnabled(false);
        heightField = new JTextField("9");
        heightField.setEditable(false);

        widthFieldLabel = new JLabel("Width (9-30)");
        widthFieldLabel.setEnabled(false);
        widthField = new JTextField("9");
        widthField.setEditable(false);

        numberBombsFieldLabel = new JLabel("Number bombs (10-668)");
        numberBombsFieldLabel.setEnabled(false);
        numberBombsField = new JTextField("10");
        numberBombsField.setEditable(false);

        easyButton.setBounds(20,20,100,25);
        levelOfDifficultly.add(easyButton);

        normalButton.setBounds(20,70,100,25);
        levelOfDifficultly.add(normalButton);

        hardButton.setBounds(20,120,100,25);
        levelOfDifficultly.add(hardButton);

        personalButton.setBounds(123,20,100,25);
        levelOfDifficultly.add(personalButton);

        heightFieldLabel.setBounds(145,50, 75,25);
        levelOfDifficultly.add(heightFieldLabel);
        heightField.setBounds(290,50, 30,25);
        levelOfDifficultly.add(heightField);

        widthFieldLabel.setBounds(145,85, 75,25);
        levelOfDifficultly.add(widthFieldLabel);
        widthField.setBounds(290,85,30,25);
        levelOfDifficultly.add(widthField);

        numberBombsFieldLabel.setBounds(145,120, 140,25);
        levelOfDifficultly.add(numberBombsFieldLabel);
        numberBombsField.setBounds(290,120,30,25);
        levelOfDifficultly.add(numberBombsField);

        levelOfDifficultly.setBounds(10,10,330,180);
    }

    /**
     * Method sets state of personal options if selected button
     * @param button listened button
     * @param state stats personal options
     */
    private void activatePersonalOptions(JRadioButton button, boolean state){
        button.addActionListener((e->{
            heightFieldLabel.setEnabled(state);
            heightField.setEditable(state);
            widthFieldLabel.setEnabled(state);
            widthField.setEditable(state);
            numberBombsFieldLabel.setEnabled(state);
            numberBombsField.setEditable(state);
        }));
    }

    /**
     * Method gets images for games objects if  images available.
     */
    private void getImgsFieldModel(){
        if(fieldModel.getLoadImagesState()){
            ((GameFieldWithImages)gameField).setImgElements(
                    fieldModel.getBombsImg(),
                    fieldModel.getFlagImg(),
                    fieldModel.getQuestionImg(),
                    fieldModel.getTilesOfSurfaceImg(),
                    fieldModel.getTilesOfLightSurfaceImg(),
                    fieldModel.getTilesOfUndergroundImg());
        }
    }

    /**
     * Method places game field relative main frame of applications.
     * @param springLayout
     */
    private void positioningGameField(SpringLayout springLayout){
        mainFrame.setLayout(springLayout);
        springLayout.putConstraint(SpringLayout.WEST, gameField,
                25,
                SpringLayout.WEST, mainFrame);
        springLayout.putConstraint(SpringLayout.NORTH, gameField,
                25,
                SpringLayout.NORTH, mainFrame);
        mainFrame.add(gameField);
    }
    /**
     * Method places game item that show time relative main frame of applications.
     * @param springLayout
     */
    private void positioningTimePanel(SpringLayout springLayout){
        timePanel = new GameInformationItem(80,80,50,0, "Time");
        springLayout.putConstraint(SpringLayout.WEST, timePanel,
                0,
                SpringLayout.WEST, gameField);
        springLayout.putConstraint(SpringLayout.NORTH, timePanel,
                10,
                SpringLayout.SOUTH, gameField);
        mainFrame.add(timePanel);
    }
    /**
     * Method places game item that show quantity bombs without marks relative main frame of applications.
     * @param springLayout
     */
    private void positioningMinePanel(SpringLayout springLayout){
        minePanel = new GameInformationItem(80,80, 50,fieldModel.getQuantityBombs(),"Mine");
        springLayout.putConstraint(SpringLayout.EAST, minePanel,
                0,
                SpringLayout.EAST, gameField);
        springLayout.putConstraint(SpringLayout.NORTH, minePanel,
                10,
                SpringLayout.SOUTH, gameField);
        mainFrame.add(minePanel);
    }

    /**
     * Method is handler event moving mouse on game field.
     */
    private void addGameFieldMouseMovingHandler(){
        gameField.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Rectangle2D[][] setUpRectangle = gameField.getCellsCoordinate();
                Point point = e.getPoint();
                for (int i = 0; i < setUpRectangle.length; i++) {
                    for (int j = 0; j < setUpRectangle[i].length; j++) {
                        if(null!=setUpRectangle[i][j]&&setUpRectangle[i][j].contains(point)) {
                            controller.setBrightCell(i,j);
                            gameField.updateBrightCell(fieldModel.getBrightCellRow(), fieldModel.getBrightCellCol());
                        }
                    }
                }
            }
        });
    }
    /**
     * Method is handler event exiting mouse out bounds game field.
     */
    private void addGameFieldMouseExitingHandler(){
        gameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                controller.setBrightCell(-1,-1);
                gameField.updateBrightCell(fieldModel.getBrightCellRow(), fieldModel.getBrightCellCol());
                gameField.repaint();
            }
        });
    }

    /**
     * Method is handler event pressing mouse on cell of game field.
     */
    private void addGameFieldMousePressingHandler(){
        gameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                Rectangle2D[][] setUpRectangle = gameField.getCellsCoordinate();
                Point point = e.getPoint();
                for (int row = 0; row < setUpRectangle.length; row++) {
                    for (int col = 0; col < setUpRectangle[row].length; col++) {
                        if(null!=setUpRectangle[row][col]&&setUpRectangle[row][col].contains(point)) {
                            if(e.getButton()==1) controller.openCell(row,col);
                            else if (e.getButton()==3) controller.setMark(row,col);
                        }
                    }
                }
            }
        });
    }

    /**
     * Method creates window with warning about impossibility load some images.
     */
    public void alarmLostImages(){
        int result = JOptionPane.showConfirmDialog(
                null,
                "Aplication can't upload all images,\n check up correctness of settings and paths.\n" +
                        " Application will be start without images.",
                "Warning",
                JOptionPane.DEFAULT_OPTION,2);
    }

    /**
     * Method creates window with message about win.
     * @param winGame
     */
    public void winGame(int winGame){
        int result = JOptionPane.showConfirmDialog(
                null,
                "Congratulations you win!\nTime: " + winGame+" seconds.\nWould you like exit?",
                "You win",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.NO_OPTION) controller.newGame(fieldModel.getRowsInField(), fieldModel.getColsInField(), fieldModel.getQuantityBombs());
        else if (result == JOptionPane.YES_OPTION) controller.exitGame();
    }

    /**
     * Method creates window with message about defeat.
     */
    public void endGame(){
        int result = JOptionPane.showConfirmDialog(
                null,
                "You found bomb!\nWould you like to continue this game?",
                "You lost",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.NO_OPTION) controller.newGame(fieldModel.getRowsInField(), fieldModel.getColsInField(), fieldModel.getQuantityBombs());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePaint() {
            gameField.updateDataSurfaceLayer(fieldModel.getCellsSurfaceState(), fieldModel.getDataOfMarks());
            minePanel.updateValue(fieldModel.getQuantityMarkedBombs());
            controller.checkWasDetonated();
            timePanel.updateValue(fieldModel.getCurrentTime()/10);
            controller.checkWin();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateAnimate() {
        if (fieldModel.getExplosionState() &&
            GameFieldWithImages.class.isAssignableFrom(gameField.getClass())){
            ((GameFieldWithImages)gameField).updateAnimateLayer(
                    fieldModel.getExplosionCoordinates(),
                    fieldModel.getExplosionState(),
                    fieldModel.nextFrame());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {}
}
