package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BishopChessComponent extends ChessComponent{
    private static Image BISHOP_WHITE;
    private static Image BISHOP_BLACK;
    private Image bishopImage;


    public void loadResource() throws IOException {
        if (BISHOP_WHITE == null) {
            BISHOP_WHITE = ImageIO.read(new File("./images/bishop-white.png"));
        }

        if (BISHOP_BLACK == null) {
            BISHOP_BLACK = ImageIO.read(new File("./images/bishop-black.png"));
        }
    }

    private void initiateBishopImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                bishopImage = BISHOP_WHITE;
            } else if (color == ChessColor.BLACK) {
                bishopImage = BISHOP_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public BishopChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateBishopImage(color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        int x = source.getX()-destination.getX();
        int y = source.getY()-destination.getY();
        double loop;
        if(x==0){
            loop=0;
        }
        else{
            loop = y/x;
        }
        if (loop == 1) {
            int absY=Math.max(source.getY(), destination.getY())-Math.min(source.getY(), destination.getY());
            int smallX=Math.min(source.getX(), destination.getX());
            int smallY=Math.min(source.getY(), destination.getY());
            for(int i=1;i<absY;i++){
                if (!(chessComponents[smallX+i][smallY+i] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        } else if (loop == -1) {
            int absY=Math.max(source.getY(), destination.getY())-Math.min(source.getY(), destination.getY());
            int smallX=Math.min(source.getX(), destination.getX());
            int largeY=Math.max(source.getY(), destination.getY());
            for(int i=1;i<absY;i++){
                if (!(chessComponents[smallX+i][largeY-i] instanceof EmptySlotComponent)) {
                    return false;
                }
            }

        } else { // Not on the k=1 or k=-1 line
            return false;
        }
        return true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(bishopImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}
