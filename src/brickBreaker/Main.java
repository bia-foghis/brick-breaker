package brickBreaker;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame obj = new JFrame();
        Game game = new Game();

        obj.setBounds(10,10,714,635);
        obj.setTitle("Breakout Ball");
        //obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(game);
    }
}