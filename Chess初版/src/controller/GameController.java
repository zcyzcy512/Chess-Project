package controller;

import model.*;
import view.Chessboard;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameController {
    private Chessboard chessboard;
    private static List<String>strings;

    public static List<String> getStrings(){
        return strings;
    }

    public Chessboard getChessboard() {
        return chessboard;
    }

    public GameController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public List<String> loadGameFromFile(String path) {
        try {
            //判断后缀是否为txt
            if (path.charAt(path.length()-1)=='t'&&path.charAt(path.length()-2)=='x'&&path.charAt(path.length()-3)=='t'){
                List<String>chessData=Files.readAllLines(Paths.get(path));

                if (chessData.size()-1!=8&&(Objects.equals(chessData.get(chessData.size()-1),"W")||Objects.equals(chessData.get(chessData.size()-1),"B"))){
                    List<String>error=new ArrayList<>();
                    String errorType="1";
                    error.add(errorType);
                    return error;//1.棋盘错误
                }
                if (chessData.size()<9||chessData.get(8).length()!=1||(chessData.get(8).charAt(0)!='W'&&chessData.get(8).charAt(0)!='B')){
                    List<String>error=new ArrayList<>();
                    String errorType="3";
                    error.add(errorType);
                    return error;//3.缺少行棋方
                }

                for (int i=0;i<8;i++){
                    if (chessData.get(i).length()!=8){
                        List<String>error=new ArrayList<>();
                        String errorType="1";
                        error.add(errorType);
                        return error;//1.棋盘错误

                    }
                    for (int j=0;j<8;j++){
                        char a=chessData.get(i).charAt(j);
                        switch (a){
                            case 'p':
                            case '_':
                            case 'K':
                            case 'k':
                            case 'Q':
                            case 'q':
                            case 'B':
                            case 'b':
                            case 'N':
                            case 'n':
                            case 'R':
                            case 'r':
                            case 'P':
                                break;
                            default:
                                List<String>error=new ArrayList<>();
                                String errorType="2";
                                error.add(errorType);
                                return error;//2.棋子并非六种之一
                        }
                    }
                }
                List<String>error=new ArrayList<>();
                String errorType;//正常
                if (chessData.get(8).charAt(0)!='W'&&chessData.get(8).charAt(0)!='B'){
                    errorType = "2";
                    error.add(errorType);
                }else {
                    errorType = "0";
                    error.add(errorType);
                    chessboard.initiateEmptyChessboard();
                    for (int i=0;i<8;i++){
                        for (int j=0;j<8;j++){
                            chessboard.getChessComponents()[i][j].repaint();
                        }
                    }
                    chessboard.loadGame(chessData);
                    strings=chessData;
                    return chessData;
                }
                return error;//2.棋子并非黑白棋子
            }else {
                //4.文件格式错误
                List<String>error=new ArrayList<>();
                String errorType="4";
                error.add(errorType);
                return error;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void saveGameFromFile(String title) {
        String path="D:\\JavaClass\\Main\\save";
        char[][] chars=new char[8][8];
        for (int i=0;i<8;i++) {
            for (int j = 0; j < 8; j++) {
                //判断棋子类型
                if (chessboard.getChessComponents()[i][j] instanceof RookChessComponent) {
                    //判断棋子颜色
                    if (chessboard.getChessComponents()[i][j].getChessColor() == ChessColor.WHITE) {
                        chars[chessboard.getChessComponents()[i][j].getChessboardPoint().getX()][chessboard.getChessComponents()[i][j].getChessboardPoint().getY()] = 'r';
                    } else {
                        chars[chessboard.getChessComponents()[i][j].getChessboardPoint().getX()][chessboard.getChessComponents()[i][j].getChessboardPoint().getY()] = 'R';
                    }
                }
                if (chessboard.getChessComponents()[i][j] instanceof BishopChessComponent) {
                    if (chessboard.getChessComponents()[i][j].getChessColor() == ChessColor.WHITE) {
                        chars[chessboard.getChessComponents()[i][j].getChessboardPoint().getX()][chessboard.getChessComponents()[i][j].getChessboardPoint().getY()] = 'b';
                    } else {
                        chars[chessboard.getChessComponents()[i][j].getChessboardPoint().getX()][chessboard.getChessComponents()[i][j].getChessboardPoint().getY()] = 'B';
                    }
                }
                if (chessboard.getChessComponents()[i][j] instanceof KnightChessComponent) {
                    if (chessboard.getChessComponents()[i][j].getChessColor() == ChessColor.WHITE) {
                        chars[chessboard.getChessComponents()[i][j].getChessboardPoint().getX()][chessboard.getChessComponents()[i][j].getChessboardPoint().getY()] = 'n';
                    } else {
                        chars[chessboard.getChessComponents()[i][j].getChessboardPoint().getX()][chessboard.getChessComponents()[i][j].getChessboardPoint().getY()] = 'N';
                    }
                }
                if (chessboard.getChessComponents()[i][j] instanceof PawnChessComponent) {
                    if (chessboard.getChessComponents()[i][j].getChessColor() == ChessColor.WHITE) {
                        chars[chessboard.getChessComponents()[i][j].getChessboardPoint().getX()][chessboard.getChessComponents()[i][j].getChessboardPoint().getY()] = 'p';
                    } else {
                        chars[chessboard.getChessComponents()[i][j].getChessboardPoint().getX()][chessboard.getChessComponents()[i][j].getChessboardPoint().getY()] = 'P';
                    }
                }
                if (chessboard.getChessComponents()[i][j] instanceof QueenChessComponent) {
                    if (chessboard.getChessComponents()[i][j].getChessColor() == ChessColor.WHITE) {
                        chars[chessboard.getChessComponents()[i][j].getChessboardPoint().getX()][chessboard.getChessComponents()[i][j].getChessboardPoint().getY()] = 'q';
                    } else {
                        chars[chessboard.getChessComponents()[i][j].getChessboardPoint().getX()][chessboard.getChessComponents()[i][j].getChessboardPoint().getY()] = 'Q';
                    }
                }
                if (chessboard.getChessComponents()[i][j] instanceof KingChessComponent) {
                    if (chessboard.getChessComponents()[i][j].getChessColor() == ChessColor.WHITE) {
                        chars[chessboard.getChessComponents()[i][j].getChessboardPoint().getX()][chessboard.getChessComponents()[i][j].getChessboardPoint().getY()] = 'k';
                    } else {
                        chars[chessboard.getChessComponents()[i][j].getChessboardPoint().getX()][chessboard.getChessComponents()[i][j].getChessboardPoint().getY()] = 'K';
                    }
                }
                if (chessboard.getChessComponents()[i][j] instanceof EmptySlotComponent) {
                    chars[chessboard.getChessComponents()[i][j].getChessboardPoint().getX()][chessboard.getChessComponents()[i][j].getChessboardPoint().getY()] = '_';
                }
            }
        }
            char player;
            if (chessboard.getCurrentColor()==ChessColor.WHITE){
                player='W';
            }else {
                player='B';
            }
            try {
                // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw
                /* 写入Txt文件 */
                File file = new File(path);// 相对路径，如果没有则要建立一个新的output。txt文件
                if(!file.exists()){
                    file.mkdirs();
                }
                String save=path+"\\"+title+".txt";
                file = new File(save);// 相对路径，如果没有则要建立一个新的output。txt文件
                file.createNewFile(); // 创建新文件
                FileWriter fileWriter=new FileWriter(save);
                for (int i=0;i<8;i++) {
                    for (int j = 0; j < 8; j++) {
                        fileWriter.append(chars[i][j]);
                    }
                    fileWriter.append('\n');
                }
                fileWriter.append(player);
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
//————————————————
//            版权声明：本文为CSDN博主「persistenceヾ(◍°∇°◍)ﾉ」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//            原文链接：https://blog.csdn.net/persistencegoing/article/details/88640737
    }
}
