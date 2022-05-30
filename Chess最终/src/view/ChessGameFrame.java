package view;

import controller.GameController;
import controller.Time;
import model.ChessColor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Objects;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGHT;


    public final int CHESSBOARD_SIZE;
    private  GameController gameController;
    private  Chessboard chessboard;   //原来没有,为了获取chessboard的玩家
    private   JTextField Player;    //后加的
    private JTextField time;
    private Time thread;

    public void setTime(Time thread){
        this.thread=thread;
    }

    public Chessboard getChessBoard(){
        return chessboard;
    }

    private JPanel panel1=new JPanel();
    private JPanel panel2=new JPanel();

    public ChessGameFrame(int width, int height) throws Exception {
        setTitle("Welcome to the Chess Game"); //设置标题
        this.WIDTH = width;
        this.HEIGHT = height;
        this.CHESSBOARD_SIZE = HEIGHT * 4 / 5;


        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        panel1.setSize(WIDTH,HEIGHT);
        panel1.setLayout(null);
        getContentPane().add(panel1);

        ImageIcon bg =new ImageIcon("images/BackGround.jpg");
        JLabel label = new JLabel(bg);
        label.setSize(WIDTH,HEIGHT);
        panel1.add(label);

        panel2.setSize(WIDTH,HEIGHT);
        panel2.setLayout(null);

        addChessboard(panel2);
        addResetButton(panel2);
        addLoadButton(panel2);
        addCurrentPlayer(panel2);
        addSaveButton(panel2);
        addStartButton(panel1);
        addBackGround();
        addTimeClock(panel2);


        /*addChessboard();
        //addLabel();
        addResetButton();
        addLoadButton();
        addCurrentPlayer();
        addSaveButton();*/


    }

    private void addBackGround(){
        ImageIcon bg = new ImageIcon("images/BackGround.jpg");
        JLabel label = new JLabel(bg);
        label.setBounds(0,0,this.WIDTH,this.HEIGHT);
        label.setSize(WIDTH,HEIGHT);
        this.getLayeredPane().add(label);
        JPanel jpanel = (JPanel) this.getContentPane();
        jpanel.setOpaque(false);
        this.getLayeredPane().add(label,Integer.MIN_VALUE);
        panel2.add(label);
    }

    public void addStartButton(JPanel panel){
        JButton button=new JButton("Start Game");
        button.setFont(new Font("Rockwell",Font.BOLD,25));
        button.setBounds(WIDTH*3/8,HEIGHT/2,WIDTH/4,HEIGHT/10);
        panel.add(button);

        button.addActionListener((e -> {
            thread.Ini();
            remove(panel1);
            getContentPane().add(panel2);
            repaint();
        }));
    }

    private void addUndoButton(JPanel panel){
        JButton button = new JButton("Undo");
        button.setLocation(HEIGHT,HEIGHT/10+360);
        button.setSize(200,60);
        button.setFont(new Font("Rockwell",Font.BOLD,29));
        panel.add(button);
        button.addActionListener(e -> {
            System.out.println("Click undo");
        });
    }

    public void addCurrentPlayer(JPanel panel){
        if(chessboard.getCurrentColor()== ChessColor.WHITE){
            Player =new JTextField("");
            Player.setBackground (Color.WHITE);
        }
        else{
            Player =new JTextField("");
            Player.setBackground (Color.BLACK);
        }
        Player.setEditable(false);
        Player.setLocation(HEIGHT+50, HEIGHT / 10);Player.setSize(70, 70);
        Player.setFont(new Font("Rockwell", Font.BOLD, 20)); Player.setBorder(new EmptyBorder(0,0,0,0));
        panel.add(Player);
    }

    public void ChangePlayer(ChessColor color){
        if(color==ChessColor.WHITE){
            //Player.setText("White");
            Player.setBackground (Color.WHITE);
        }
        else{
            //Player.setText("Black");
            Player.setBackground (Color.BLACK);
        }
    }

    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard(JPanel panel) {
        chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);     //改过，原来是Chessboard chessboard=new
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10);
        panel.add(chessboard);

        chessboard.setFrame(this);   //自己加的
    }

    /**
     * 在游戏面板中添加标签
     */
    /*private void addLabel() {
        JLabel statusLabel;
        if(chessboard.getCurrentColor()== ChessColor.WHITE){
            statusLabel = new JLabel("White");
        }
        else{
            statusLabel = new JLabel("Black");
        }
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }*/

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addResetButton(JPanel panel) {
        JButton button = new JButton("reset");
        button.addActionListener((e) ->  restChessBoard());//JOptionPane.showMessageDialog(this, "Hello, world!"));
        button.setLocation(HEIGHT, HEIGHT / 10+120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        panel.add(button);

    }

    private void addLoadButton(JPanel panel) {
        JButton button = new JButton("Load");
        button.setLocation(HEIGHT, HEIGHT / 10+240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        panel.add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            String path="";

            JFrame fileChooser=new FileChooser();
            ((FileChooser)fileChooser).actionPerformed(e);
            path=((FileChooser)fileChooser).getFileName();
            if (Objects.equals(gameController.loadGameFromFile(path).get(0),"1")){
                JOptionPane.showMessageDialog(this,"错误编码：101");
            }
            if (Objects.equals(gameController.loadGameFromFile(path).get(0),"2")){
                JOptionPane.showMessageDialog(this,"错误编码：102");
            }
            if (Objects.equals(gameController.loadGameFromFile(path).get(0),"3")){
                JOptionPane.showMessageDialog(this,"错误编码：103");
            }
            if (Objects.equals(gameController.loadGameFromFile(path).get(0),"4")){
                JOptionPane.showMessageDialog(this,"错误编码：104");
            }
            thread.Ini();
        });
    }


    private void addSaveButton(JPanel panel){
        JButton button=new JButton("Save");
        button.setLocation(HEIGHT,HEIGHT/10+360);
        button.setSize(200,60);
        button.setFont(new Font("Rockwell",Font.BOLD,20));
        panel.add(button);

        button.addActionListener(e -> {
            System.out.println("Click save");
            String name=JOptionPane.showInputDialog(this,"Input Name here");
            gameController.saveGameFromFile(name);
        });
    }

    //为重置棋盘，设置看不见被重置的棋子，出现过问题
    public void restChessBoard(){
        chessboard.setCurrentColor(ChessColor.WHITE);
        ChangePlayer(ChessColor.WHITE);
        chessboard.removeChess();
        chessboard.initiateEmptyChessboard();
        chessboard.initializeChess();

        thread.Ini();
    }

    public void addPopUpWindow(ChessColor color){

        int store;
        if(color==ChessColor.WHITE){
            store=JOptionPane.showConfirmDialog(null, "White win", "White win", JOptionPane.OK_CANCEL_OPTION);
        }
        else{
            store=JOptionPane.showConfirmDialog(null, "Black win", "Black win", JOptionPane.OK_CANCEL_OPTION);
        }
        if(store==JOptionPane.OK_OPTION){
            restChessBoard();
        }
        else{
            this.setVisible(false);//窗口不可见

            this.dispose();//窗口销毁

            System.exit(0);
        }

    }    //吃王

    public int PawnUp(){
        Object[] possibleValues = { "Queen", "Bishop", "Rook","Knight" };
        int selectedValue;
        do {
            selectedValue = JOptionPane.showOptionDialog(null, "Choose one", "兵升变",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, possibleValues, possibleValues[0]);
        }while(selectedValue==-1);
        switch(selectedValue){
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            default:JOptionPane.showMessageDialog(null, "alert", "alert", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }   //兵升变

    public void addTimeClock(JPanel panel){
        time=new JTextField("");
        time.setEditable(false);
        time.setLocation(HEIGHT+70,HEIGHT/10+460);
        time.setVisible(true);
        time.setFont(new Font("Rockwell", Font.BOLD, 20));
        time.setSize(50, 50);
        time.setBorder(new EmptyBorder(0,0,0,0));
        time.setBackground (new Color(255,255,0));
        time.setHorizontalAlignment(JTextField.CENTER);
        panel.add(time);
    }       //无法设置背景透明

    public void setTimeText(int t){
        time.setText(t+"");
    }

    //通过JFileChooser选取存档
    class FileChooser extends JFrame{
        String fileName="";

        public void actionPerformed(ActionEvent event){
            JFileChooser jFileChooser=new JFileChooser("D:\\JavaClass\\Main\\save");
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            jFileChooser.showDialog(new JLabel(),"选择");
            File file=jFileChooser.getSelectedFile();
            fileName=file.getPath();
        }
        public String getFileName(){
            System.out.println(fileName);
            return fileName;
        }
    }

}
