package view;


import controller.ClickController;
import controller.Time;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */
    private static final int CHESSBOARD_SIZE = 8;

    private final ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];


    private ChessColor currentColor = ChessColor.WHITE;               //改了老师的样例，本来是黑先走
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;
    private ChessGameFrame frame;            //自己加的,用于改frame里的当前玩家
    private Time time;
    private int count =0;  //吃过路兵

    public void setCount(int count){
        this.count=count;
    }

    public void setTime(Time time){
        this.time=time;
    }
    public boolean isCount2(){
        if(count==2){
            return true;
        }
        return false;
    }  //吃过路兵

    public ChessGameFrame getFrame() {
        return frame;
    }

    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);

        initiateEmptyChessboard();
        initializeChess();

    }

    public void setCurrentColor(ChessColor currentColor) {
        this.currentColor = currentColor;

    }

    public void initializeChess() {

        // FIXME: Initialize chessboard for testing only.
        //初始放置车
        //黑车
        initRookOnBoard(0, 0, ChessColor.BLACK);//（0，0）
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);//（0，7）
        //白车
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);//（7，0）
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);//（7，7）

        //初始放置兵
        for (int i = 0; i < 8; i++) {
            initPawnOnBoard(1, i, ChessColor.BLACK);//第二行
            initPawnOnBoard(CHESSBOARD_SIZE - 2, i, ChessColor.WHITE);//第七行
        }

        //初始放置象
        initBishopOnBoard(0, 2, ChessColor.BLACK);
        initBishopOnBoard(0, 5, ChessColor.BLACK);
        initBishopOnBoard(7, 2, ChessColor.WHITE);
        initBishopOnBoard(7, 5, ChessColor.WHITE);

        //初始放置王
        initKingOnBoard(0, 4, ChessColor.BLACK);
        initKingOnBoard(7, 4, ChessColor.WHITE);

        //初始放置后
        initQueenOnBoard(0, 3, ChessColor.BLACK);
        initQueenOnBoard(7, 3, ChessColor.WHITE);

        //初始放置马
        initKnightOnBoard(0, 1, ChessColor.BLACK);
        initKnightOnBoard(7, 1, ChessColor.WHITE);
        initKnightOnBoard(0, 6, ChessColor.BLACK);
        initKnightOnBoard(7, 6, ChessColor.WHITE);
    }

    //为重置棋盘，不知道会有什么影响,错过，但没记住错误的走法
    public void removeChess() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int k = 0; k < chessComponents[i].length; k++) {
                chessComponents[i][k].setVisible(false);
            }
        }
         clickController.ini();
    }

    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public ChessColor getCurrentColor(){
        return currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();

        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
        chessComponent.repaint();
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.

        if(chess1 instanceof PawnChessComponent){
            int store=-1;
            if(chess1.getChessColor()==ChessColor.WHITE&&chess2.getChessboardPoint().getX()==0){
                store=frame.PawnUp();
            }
            if(chess1.getChessColor()==ChessColor.BLACK&&chess2.getChessboardPoint().getX()==7){
                store=frame.PawnUp();
            }
            switch(store){
                case 0:
                    remove(chess1);
                    chess1=new QueenChessComponent(chess1.getChessboardPoint(),chess1.getLocation(),chess1.getChessColor(),clickController, CHESS_SIZE);
                    add(chess1);
                    break;
                case 1:
                    remove(chess1);
                    chess1=new BishopChessComponent(chess1.getChessboardPoint(),chess1.getLocation(),chess1.getChessColor(),clickController, CHESS_SIZE);
                    add(chess1);
                    break;
                case 2:
                    remove(chess1);
                    chess1=new RookChessComponent(chess1.getChessboardPoint(),chess1.getLocation(),chess1.getChessColor(),clickController, CHESS_SIZE);
                    add(chess1);
                    break;
                case 3:
                    remove(chess1);
                    chess1=new KnightChessComponent(chess1.getChessboardPoint(),chess1.getLocation(),chess1.getChessColor(),clickController, CHESS_SIZE);
                    add(chess1);
                    break;

            }
        }   //兵升变

        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;

        chess1.repaint();
        chess2.repaint();

        count++;
        time.Ini();
    }   //这里有兵升变

    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE));
            }
        }
    }

    public void setFrame(ChessGameFrame frame) {
        this.frame = frame;
    }

    public void swapColor() {
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
        frame.ChangePlayer(currentColor);
    }

    private void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initPawnOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessComponent.setChessboard(this);
    }    //里有吃过路兵

    private void initBishopOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKingOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initQueenOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKnightOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }


    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public void loadGame(List<String> chessData) {
        chessData.forEach(System.out::println);
        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                char a=chessData.get(i).charAt(j);
                if (a=='r'){
                    initRookOnBoard(i,j,ChessColor.WHITE);
                }
                if (a=='R'){
                    initRookOnBoard(i,j,ChessColor.BLACK);
                }
                if (a=='p'){
                    initPawnOnBoard(i,j,ChessColor.WHITE);
                }
                if (a=='P'){
                    initPawnOnBoard(i,j,ChessColor.BLACK);
                }
                if (a=='b'){
                    initBishopOnBoard(i,j,ChessColor.WHITE);
                }
                if (a=='B') {
                    initBishopOnBoard(i, j, ChessColor.BLACK);
                }
                if (a=='n'){
                    initKnightOnBoard(i,j,ChessColor.WHITE);
                }
                if (a=='N'){
                    initKnightOnBoard(i,j,ChessColor.BLACK);
                }
                if (a=='q'){
                    initQueenOnBoard(i,j,ChessColor.WHITE);
                }
                if (a=='Q'){
                    initQueenOnBoard(i,j,ChessColor.BLACK);
                }
                if (a=='k'){
                    initKingOnBoard(i,j,ChessColor.WHITE);
                }
                if (a=='K'){
                    initKingOnBoard(i,j,ChessColor.BLACK);
                }
            }
        }
        if (chessData.get(8).charAt(0)=='W'){
            currentColor=ChessColor.WHITE;
            frame.ChangePlayer(currentColor);

        }
        if (chessData.get(8).charAt(0)=='B'){
            currentColor=ChessColor.BLACK;
            frame.ChangePlayer(currentColor);
        }
    }

    public void eatSidePawn(ChessComponent chess){
        remove(chess);
        add(chess=new EmptySlotComponent(chess.getChessboardPoint(),chess.getLocation(),clickController, CHESS_SIZE));
        int row2 = chess.getChessboardPoint().getX(), col2 = chess.getChessboardPoint().getY();
        chessComponents[row2][col2]=chess;
        chess.repaint();
    }

   

}
