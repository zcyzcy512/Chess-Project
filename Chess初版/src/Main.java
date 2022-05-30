import controller.Time;
import view.ChessGameFrame;
import view.Music;

public class Main{

    public static void main(String[] args) {

        //背景音乐启动
        Music audioPlayWave = new Music("bgm.wav");// 开音乐 音樂名
        audioPlayWave.start();
        @SuppressWarnings("unused")
        int musicOpenLab = 1;

        //SwingUtilities.invokeLater(() -> {
        ChessGameFrame mainFrame = null;
        try {
            mainFrame = new ChessGameFrame(900, 660);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mainFrame.setVisible(true);

        Time thread =new Time();
        mainFrame.getChessBoard().setTime(thread);
        mainFrame.setTime(thread);
        thread.start();
        thread.run(mainFrame);

           // });
        }


    }
