import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
 
//public class Tertris extends JFrame implements KeyListener{
    public class Tertris extends JFrame {
    private static final int game_x=20;
    private static final int game_y=10;
 
    JTextArea[][] text;
    int[][] data;
 
    public void intWindow(){
       
 
        //ゲームの画面を作る    
            this.setSize(600,850);
            this.setVisible(true);
            this.setLocationRelativeTo(null);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setResizable(false);
            this.setTitle("Tetorisu game_B group");
    }
 
    //初期化  
    public void initGamePanel(){
        JPanel game_main=new JPanel();
        game_main.setLayout(new GridLayout(game_x,game_y,1,1));
 
        for(int i=0;i<text.length;i++){
 
            for(int j=0;j<text[i].length;j++){
                text[i][j]=new JTextArea(game_x,game_y);
                text[i][j].setBackground(Color.WHITE);
              //  text[i][j].addKeyListener(this);
                if(j==0 || j==text[i].length-1 || i==text.length-1){
                    text[i][j].setBackground(Color.MAGENTA);
                    data[i][j]=1;
                }
                text[i][j].setEditable(false);
                game_main.add(text[i][j]);
            }
        }
        this.setLayout(new BorderLayout());
        //this.add(game_main,BorderLayout,CENTER);
    }
 
    public Tertris()
    {
        text=new JTextArea[game_x][game_y];
        data=new int[game_x][game_y];
        intWindow();
    }
 
    public static void main(String[] args)
    {
        Tertris tertris =new Tertris();
    }
}