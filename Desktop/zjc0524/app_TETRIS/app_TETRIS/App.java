package app_TETRIS;

import javax.swing.JFrame;
import javax.swing.InputMap;
import javax.swing.ActionMap;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.Color;
import java.util.Scanner;

public class App extends JFrame {
    private GameArea ga;
    private Mino mino;
    private Mino nextMino;

    public App() {
        this.mino = new Mino();
        this.ga = new GameArea();
        this.nextMino = new Mino();
        new GameThread(mino, ga, nextMino, this).start();
        initControls();
       
        setTitle("Tetris");
        setSize(500, 690); // 增加?度以?示下一个ミノ
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Tetris");
        System.out.print("Please enter your name:");

        Scanner sc = new Scanner(System.in);
        String name = sc.next();

        int l = name.length();
        if (0 < l && l <= 16) {
            System.out.println("Welcome " + name + "!");
            GameArea player = new GameArea();
            player.setName(name);
        } else {
            System.out.println("Guest");
            GameArea player = new GameArea();
            player.setName("Guest");
        }

        System.out.println("Press Enter to start!!");
        while ((System.in.read()) != '\n');

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new App().setVisible(true);
            }
        });
        sc.close();
    }

    public void updateMino(Mino mino) {
        this.mino = mino;
        initControls();
    }

    public void updateNextMino(Mino nextMino) {
        this.nextMino = nextMino;
    }

    private void initControls() {
        InputMap im = this.getRootPane().getInputMap();
        ActionMap am = this.getRootPane().getActionMap();

        im.put(KeyStroke.getKeyStroke("RIGHT"), "right");
        am.put("right", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!ga.isCollison(mino, mino.getMinoX() + 1, mino.getMinoY(), mino.getMinoAngle())) {
                    ga.moveRight(mino);
                    repaint();
                }
            }
        });

        im.put(KeyStroke.getKeyStroke("LEFT"), "left");
        am.put("left", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!ga.isCollison(mino, mino.getMinoX() - 1, mino.getMinoY(), mino.getMinoAngle())) {
                    ga.moveLeft(mino);
                    repaint();
                }
            }
        });

        im.put(KeyStroke.getKeyStroke("DOWN"), "down");
        am.put("down", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!ga.isCollison(mino, mino.getMinoX(), mino.getMinoY() + 1, mino.getMinoAngle())) {
                    ga.moveDown(mino);
                    repaint();
                }
            }
        });

        im.put(KeyStroke.getKeyStroke("UP"), "up");
        am.put("up", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!ga.isCollison(mino, mino.getMinoX(), mino.getMinoY(), (mino.getMinoAngle() + 1) % mino.getMinoAngleSize())) {
                    ga.rotation(mino);
                    repaint();
                }
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);

    g.drawString("Score: " + ga.getScore(), (ga.getFieldWidth() + 1) * 1, (ga.getFieldHight() + 3) * 28);

    
        // ?制游?区域
        for (int y = 0; y < ga.getFieldHight(); y++) {
            for (int x = 0; x < ga.getFieldWidth(); x++) {
                if (ga.getField()[y][x] == 1) {
                    g.setColor(ga.getFieldColors()[y][x]); // 使用存?的?色
                    g.fillRect(x * 30, y * 30, 30, 30);
                } else {
                    g.setColor(Color.BLACK); // ?置?黑色
                    g.drawRect(x * 30, y * 30, 30, 30);
                }
            }
        }
        // ?制当前ミノ
        g.setColor(mino.getColor());
        
        for (int y = 0; y < mino.getMinoSize(); y++) {
            for (int x = 0; x < mino.getMinoSize(); x++) {
                if (mino.getMino()[mino.getMinoAngle()][y][x] == 1) {
                    g.fillRect((mino.getMinoX() + x) * 30, (mino.getMinoY() + y) * 30, 30, 30);
                }
            }
        }
        // ?制下一个ミノ
        drawNextMino(g, nextMino);
    }
    

    private void drawNextMino(Graphics g, Mino nextMino) {
        int offsetX = 380; // ?整?个?以向右移?
        int offsetY = 60; // ?整?个?以向下移?

        g.setColor(Color.BLACK);
        g.drawString("Next Mino:", offsetX, offsetY - 10); // ??位置

        g.setColor(nextMino.getColor());
        for (int y = 0; y < nextMino.getMinoSize(); y++) {
            for (int x = 0; x < nextMino.getMinoSize(); x++) {
                if (nextMino.getMino()[0][y][x] == 1) {
                    g.fillRect(offsetX + x * 30, offsetY + y * 30, 30, 30);
                }
            }
        }
    }
}