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
        setSize(300, 600);
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
        for (int y = 0; y < ga.getFieldHight(); y++) {
            for (int x = 0; x < ga.getFieldWidth(); x++) {
                if (ga.getField()[y][x] == 1) {
                    g.fillRect(x * 30, y * 30, 30, 30);
                } else {
                    g.drawRect(x * 30, y * 30, 30, 30);
                }
            }
        }
        g.setColor(Color.RED);
        for (int y = 0; y < mino.getMinoSize(); y++) {
            for (int x = 0; x < mino.getMinoSize(); x++) {
                if (mino.getMino()[mino.getMinoAngle()][y][x] == 1) {
                    g.fillRect((mino.getMinoX() + x) * 30, (mino.getMinoY() + y) * 30, 30, 30);
                }
            }
        }
    }
}