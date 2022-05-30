package controller;

import view.ChessGameFrame;

public class Time extends Thread {

    /**
     * run () 方法就是子线程要执行的代码
     */
    private int i=10;
    public void run(ChessGameFrame mainFrame) {
        while(true) {
            i=100;
            for (; i >= 0; i--) {
                try {
                    Thread.sleep(1000);// 线程睡一秒钟
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mainFrame.setTimeText(i);
            }
            if (i == -1) {
                mainFrame.getChessBoard().swapColor();
            }
        }
    }
    public void Ini (){
        i=100;
    }

}
