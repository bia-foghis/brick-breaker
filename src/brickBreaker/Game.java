package brickBreaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0;
    private int totalBricks = 21;
    private Timer timer;
    private int delay = 8;
    private int playerX = 310;
    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballDirX = -3;
    private int ballDirY = -4;
    private MapGenerator map;

    public Game() {
        map = new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay,this);
        timer.start();
    }

    public void paint(Graphics g) {
        // background
        g.setColor(Color.black);
        g.fillRect(0,0,700,600);

        // map
        map.draw((Graphics2D) g);

        // borders
        g.setColor(new Color(89,73,166));
        g.fillRect(0,0,5,600);
        g.fillRect(0,0,700,5);
        g.fillRect(695,0,5,600);

        // scores
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString(""+score, 590, 30);

        // paddle
        g.setColor(new Color(89,73,166));;
        g.fillRect(playerX,575,100,8);

        // ball
        g.setColor(new Color(255,224,18));
        g.fillOval(ballPosX,ballPosY,20,20);

        if(totalBricks <= 0){
            play = false;
            ballDirX = 0;
            ballDirY = 0;
            g.setColor(new Color(203,10,10));
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("You won!",260,300);
            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press Enter to Restart",230,350);
        }

        if(ballPosY > 570) {
            play = false;
            ballDirX = 0;
            ballDirY = 0;
            g.setColor(new Color(203,10,10));
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Game Over! Score: "+score,190,300);
            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press Enter to Restart",230,350);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play){
            if(new Rectangle(ballPosX,ballPosY,20,20).intersects(new Rectangle(playerX,550,100,8))) {
                ballDirY =- ballDirY;
            }

            A: for(int i = 0; i < map.map.length; i++){
                for(int j = 0; j < map.map[0].length; j++){
                    if(map.map[i][j] > 0){
                        int brickX = j*map.brickWidth + 80;
                        int brickY = i* map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
                        Rectangle brickRect = rect;

                        if(ballRect.intersects(brickRect)){
                            map.setBrickValue(0,i,j);
                            totalBricks--;
                            score += 5;

                            if(ballPosX  + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width){
                                ballDirX =  -ballDirX;
                            } else {
                                ballDirY = -ballDirY;
                            }

                            break A;
                        }
                    }
                }
            }

            ballPosX += ballDirX;
            ballPosY += ballDirY;
            if(ballPosX < 0){
                ballDirX = -ballDirX;
            }
            if(ballPosY < 0){
                ballDirY = -ballDirY;
            }
            if(ballPosX > 697){
                ballDirX = -ballDirX;
            }
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(playerX <= 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(!play){
                play = true;
                ballPosX = 120;
                ballPosY = 350;
                ballDirX = -1;
                ballDirY = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3,7);

                repaint();
            }
        }
    }

    public void moveRight() {
        play = true;
        playerX += 20;
    }

    public void moveLeft() {
        play = true;
        playerX -= 20;
    }
}
