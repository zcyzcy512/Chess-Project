package view;


//线程类
public class TestThread extends Thread {
    private ChessGameFrame chessGameFrame;
    public void run() {
        while(true){
            try {
                sleep(1000);
                //这里可以写你自己要运行的逻辑代码
                System.out.println("一秒钟运行一次");
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
}
//————————————————
     //   版权声明：本文为CSDN博主「马鹏森」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
     //   原文链接：https://blog.csdn.net/weixin_43135178/article/details/111594857