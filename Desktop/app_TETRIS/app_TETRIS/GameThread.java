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
                this.mino = nextMino;//次のミノの用意
                this.nextMino = new Mino();
                app.updateMino(this.mino); // Appの中のMinoを更新、入力受付更新
            } else {
                ga.initField();
                ga.fieldAddMino(mino);
            }
            app.repaint();// 画面を最新の状態に更新
            System.out.println("NextMino");
            ga.drawNextMino(nextMino);

            try {
                Thread.sleep(1000);//1秒間スリープ
            } catch (InterruptedException ex) {//スレッドが一時停止中に他のスレッドから割り込まれた場合
                Logger.getLogger(GameThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}