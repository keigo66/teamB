package app_TETRIS;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameThread extends Thread {

    private GameArea ga;
    private Mino mino;
    private Mino nextMino;
    private App app;

    public GameThread(Mino mino, GameArea ga, Mino nextMino, App app) {
        this.mino = mino;
        this.ga = ga;
        this.nextMino = nextMino;
        this.app = app;
    }

    public GameThread(Mino mino, GameArea ga, App app) {
        this.mino = mino;
        this.ga = ga;
        this.nextMino = new Mino();
        this.app = app;
    }

    public void run() {
        while (true) {
            if (!ga.isCollison(mino, mino.getMinoX(), mino.getMinoY() + 1, mino.getMinoAngle())) {
                ga.moveDown(mino);
            }

            if (ga.isCollison(mino)) {
                if (mino.getMinoY() <= 1) {
                    System.out.println("GameOver");
                    System.out.println(ga.getName() + "  Your score: " + ga.getScore());
                    System.exit(0);
                }

                ga.bufferFieldAddMino(mino);
                ga.eraseLine();
                ga.initField();
                this.mino = nextMino;
                this.nextMino = new Mino();
                app.updateMino(this.mino); // 更新 App 中的 mino 引用并重新绑定键盘输入
            } else {
                ga.initField();
                ga.fieldAddMino(mino);
            }
            app.repaint();
            System.out.println("NextMino");
            ga.drawNextMino(nextMino);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}