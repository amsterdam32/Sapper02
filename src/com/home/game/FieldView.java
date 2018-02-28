package com.home.game;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class FieldView implements FieldObserver, ActionListener {
    FieldModelInterface fieldModel;
    FieldController controller;

    JFrame mainFrame;
    JPanel baseForGameField;
    GameItemLabel timePanel;
    GameItemLabel minePanel;
    JFrame settingsFrame;
    JPanel levelOfDifficultly;

    JButton settingsOk;
    JButton settingsCancel;
    ButtonGroup settingsSelector;
    JRadioButton easyButton;
    JRadioButton normalButton;
    JRadioButton hardButton;
    JRadioButton personalButton;
    JLabel heightFieldLabel;
    JLabel widthFieldLabel;
    JLabel numberBombsFieldLabel;
    JTextField heightField;
    JTextField widthField;
    JTextField numberBombsField;

    final int WIDTH_ADD = 6+100;
    final int HEIGHT_ADD = 52+150;
    GamePanelAbstract gamePanel;

    JMenuBar menuBar;
    JMenu menuGame;
    JMenu menuInfo;
    JMenuItem startGame;
    JMenuItem exitGame;
    JMenuItem settingsGame;
    JMenuItem aboutGame;

    public FieldView(FieldController ci, FieldModelInterface fm){
        fieldModel = fm;
        controller = ci;
        fieldModel.registerObserver(this);
    }

    public void createView(){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                mainFrame = new JFrame("Sapper");
                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                int gameWidth = fieldModel.getWidth()*fieldModel.getCellSize();
                int gameHeight = fieldModel.getHeight()*fieldModel.getCellSize();
                int fWidth = gameWidth+WIDTH_ADD;
                int fHeight = gameHeight+HEIGHT_ADD;
                mainFrame.setSize(new Dimension(fWidth,fHeight));
                mainFrame.setLocationRelativeTo(null);
                mainFrame.setResizable(false);
                mainFrame.setVisible(true);

                menuBar = new JMenuBar();
                menuGame = new JMenu(" Game ");
                menuGame.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        menuGame.setOpaque(true);
                        menuGame.setBackground(new Color(210,210,210));
                    };

                    @Override
                    public void mouseExited(MouseEvent e) {
                        menuGame.setOpaque(false);
                        menuGame.setBackground(Color.GRAY);
                    };
                });

                startGame = new JMenuItem("New game");
                settingsGame = new JMenuItem("Settings");
                exitGame = new JMenuItem("Exit");

                menuGame.add(startGame);
                startGame.addActionListener((e)->controller.newGame());
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
                aboutGame.addActionListener((e)->JOptionPane.showMessageDialog(mainFrame,"Приложение не предназначенно для\n" +
                        "коммерческого использования и \n" +
                        "создано с целью демонстрации\n" +
                        "навыков работы на языке Java.\n" +
                        "Приложение разработанно по аналоги\n" +
                        "со всеми любимой игрой сапер, реализующее\n" +
                        "большую часть её функционала.\n\n" +
                        "Разработчик:                            Початрев В.В\n" +
                        "email:             v.postman2014@yandex.com\n\n"));
                menuInfo.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        menuInfo.setOpaque(true);
                        menuInfo.setBackground(new Color(210,210,210));
                    };

                    @Override
                    public void mouseExited(MouseEvent e) {
                        menuInfo.setOpaque(false);
                        menuInfo.setBackground(Color.GRAY);
                    };
                });
                menuInfo.add(aboutGame);
                menuBar.add(menuInfo);


                gamePanel = new GamePanel(fieldModel.getInfoBottomLayer(),
                                          fieldModel.getInfoUpLayer(),
                                          fieldModel.getMarkInfo(),
                                          fieldModel.getBrightCell(),
                                          fieldModel.getCellSize());
                gamePanel.getImgElements(fieldModel.getBombsImg(),
                                         fieldModel.getFlagImg(),
                                         fieldModel.getQuestionImg());
                gamePanel.setPreferredSize(new Dimension(gameWidth+1,gameHeight+1));
                SpringLayout springLayout = new SpringLayout();
                mainFrame.setLayout(springLayout);
                springLayout.putConstraint(SpringLayout.WEST, gamePanel,
                            50,
                            SpringLayout.WEST, mainFrame);
                springLayout.putConstraint(SpringLayout.NORTH, gamePanel,
                        50,
                        SpringLayout.NORTH, mainFrame);
                mainFrame.add(gamePanel);

                timePanel = new GameItemLabel(80,80,0, "Time");
                springLayout.putConstraint(SpringLayout.WEST, timePanel,
                        0,
                        SpringLayout.WEST, gamePanel);
                springLayout.putConstraint(SpringLayout.NORTH, timePanel,
                        10,
                        SpringLayout.SOUTH, gamePanel);
                mainFrame.add(timePanel);

                minePanel = new GameItemLabel(80,80, fieldModel.getNumberBombs(),"Mine");
                springLayout.putConstraint(SpringLayout.EAST, minePanel,
                        0,
                        SpringLayout.EAST, gamePanel);
                springLayout.putConstraint(SpringLayout.NORTH, minePanel,
                        10,
                        SpringLayout.SOUTH, gamePanel);
                mainFrame.add(minePanel);


                gamePanel.addMouseMotionListener(new MouseMotionAdapter() {
                    @Override
                    public void mouseMoved(MouseEvent e) {
                        Rectangle2D[][] setUpRectangle = gamePanel.getSetUpRectangles();
                            Point point = e.getPoint();
                            for (int i = 0; i < setUpRectangle.length; i++) {
                                for (int j = 0; j < setUpRectangle[i].length; j++) {
                                    if(null!=setUpRectangle[i][j]&&setUpRectangle[i][j].contains(point)) {
                                        controller.setBrightCell(i,j);
                                    }
                                    else gamePanel.repaint();
                                }
                            }
                    }
                });
                gamePanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseExited(MouseEvent e) {
                        super.mouseExited(e);
                        controller.setBrightCell(-1,-1);
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        super.mousePressed(e);
                        Rectangle2D[][] setUpRectangle = gamePanel.getSetUpRectangles();
                        Point point = e.getPoint();
                        for (int i = 0; i < setUpRectangle.length; i++) {
                            for (int j = 0; j < setUpRectangle[i].length; j++) {
                                if(null!=setUpRectangle[i][j]&&setUpRectangle[i][j].contains(point)) {
                                    if(e.getButton()==1) controller.openCell(i,j);
                                    else if (e.getButton()==3) controller.setMark(i,j);
                                }
                            }
                        }

                    }
                });
            }
        });

    }

    public void createSettingsFrame(){
        settingsFrame = new JFrame("Settings");
        settingsFrame.setSize(new Dimension(355,280));
        settingsFrame.setLocationRelativeTo(mainFrame);
        settingsFrame.setResizable(false);
        settingsFrame.setVisible(true);

        settingsFrame.setLayout(null);

        levelOfDifficultly = new JPanel();
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
        easyButton.addActionListener((e->{
            heightFieldLabel.setEnabled(false);
            heightField.setEditable(false);
            widthFieldLabel.setEnabled(false);
            widthField.setEditable(false);
            numberBombsFieldLabel.setEnabled(false);
            numberBombsField.setEditable(false);
        }));

        settingsSelector.add(normalButton);
        normalButton.addActionListener((e->{
            heightFieldLabel.setEnabled(false);
            heightField.setEditable(false);
            widthFieldLabel.setEnabled(false);
            widthField.setEditable(false);
            numberBombsFieldLabel.setEnabled(false);
            numberBombsField.setEditable(false);
        }));

        settingsSelector.add(hardButton);
        hardButton.addActionListener((e->{
            heightFieldLabel.setEnabled(false);
            heightField.setEditable(false);
            widthFieldLabel.setEnabled(false);
            widthField.setEditable(false);
            numberBombsFieldLabel.setEnabled(false);
            numberBombsField.setEditable(false);
        }));

        settingsSelector.add(personalButton);
        personalButton.addActionListener((e->{
            heightFieldLabel.setEnabled(true);
            heightField.setEditable(true);
            widthFieldLabel.setEnabled(true);
            widthField.setEditable(true);
            numberBombsFieldLabel.setEnabled(true);
            numberBombsField.setEditable(true);
        }));

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

    public void endGame(){
        int result = JOptionPane.showConfirmDialog(
                null,
                "You found bomb!\nWould you like to continue?",
                "You lost",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.NO_OPTION) controller.newGame();

    }

    @Override
    public void update() {
            gamePanel.updateInnerUpLayer(fieldModel.getInfoUpLayer(), fieldModel.getMarkInfo());
            gamePanel.updateBrightCell(fieldModel.getBrightCell());
            if (fieldModel.getExplosionState()) {
                gamePanel.updateAnimateLayer(fieldModel.getExplosionCoordinates(),
                        fieldModel.getExplosionState(),
                        fieldModel.nextFrame());
            }
            controller.checkWasDetonated();
            timePanel.updateValue(fieldModel.getCurrentTime()/10);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


}
