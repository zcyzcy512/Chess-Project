package model;

import controller.ClickController;
import view.Chessboard;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PawnChessComponent extends ChessComponent{
    private static Image PAWN_WHITE;
    private static Image PAWN_BLACK;
    private Image pawnImage;
    private int move =0;    //吃过路兵
    private Chessboard chessboard;

    public void setChessboard(Chessboard chessboard){
        this.chessboard=chessboard;
    }

    public int getMove (){
        return move;
    }     //吃过路兵

    public void loadResource() throws IOException {
        if (PAWN_WHITE == null) {
            PAWN_WHITE = ImageIO.read(new File("./images/pawn-white.png"));
        }

        if (PAWN_BLACK == null) {
            PAWN_BLACK = ImageIO.read(new File("./images/pawn-black.png"));
        }
    }

    private void initiatePawnImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                pawnImage = PAWN_WHITE;
            } else if (color == ChessColor.BLACK) {
                pawnImage = PAWN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public PawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiatePawnImage(color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();

        if(chessComponents[destination.getX()][destination.getY()].getChessColor()==ChessColor.NONE&&
                getChessColor()==ChessColor.WHITE&&source.getY()==destination.getY()){
            if(source.getX()==6&&destination.getX()==source.getX()-2){
                move++;
                chessboard.setCount(1);
                return true;
            }
            if(destination.getX()==source.getX()-1){
                move++;
                return true;
            }

        }
        if(chessComponents[destination.getX()][destination.getY()].getChessColor()==ChessColor.NONE&&
                getChessColor()==ChessColor.BLACK&&source.getY()==destination.getY()){
            if(source.getX()==1&&destination.getX()==source.getX()+2){
                chessboard.setCount(1);
                move++;
                return true;
            }
            if(destination.getX()==source.getX()+1){
                move++;
                return true;
            }
        }
        int x=destination.getX()-source.getX();
        int y=destination.getY()-source.getY();
        if(getChessColor()==ChessColor.WHITE&&chessComponents[destination.getX()][destination.getY()].getChessColor()==ChessColor.BLACK
                &&x==-1&&(y==1|y==-1)){
            move++;
            return true;
        }
        if(getChessColor()==ChessColor.BLACK&&chessComponents[destination.getX()][destination.getY()].getChessColor()==ChessColor.WHITE
                &&x==1&&(y==1|y==-1)){
            move++;
            return true;
        }

        if(getChessColor()==ChessColor.WHITE&&x==-1&&(y==1||y==-1)
                &&chessComponents[destination.getX()+1][destination.getY()] instanceof PawnChessComponent
                &&chessComponents[destination.getX()+1][destination.getY()].getMove()==1
                &&chessComponents[destination.getX()+1][destination.getY()].getChessColor()==ChessColor.BLACK){
            if(chessboard.isCount2()){
            chessboard.eatSidePawn(chessComponents[destination.getX()+1][destination.getY()]);
            move++;
            return true;}
        }   //吃过路兵

        if(getChessColor()==ChessColor.BLACK&&x==1&&(y==1||y==-1)
                &&chessComponents[destination.getX()-1][destination.getY()] instanceof PawnChessComponent
                &&chessComponents[destination.getX()-1][destination.getY()].getMove()==1
                &&chessComponents[destination.getX()-1][destination.getY()].getChessColor()==ChessColor.WHITE){
            if(chessboard.isCount2()){
                chessboard.eatSidePawn(chessComponents[destination.getX()-1][destination.getY()]);
            move++;
            return true;}
        }   //吃过路兵

        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(pawnImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }



}
