package MazeGenrator;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class Tester extends JPanel {
    int x=300;
    Tester(){
        setLayout(null);
    }
    public static void main(String[]args){
        Tester tester = new Tester();
        JFrame f = new JFrame( "Sample2 Test" );
        f.getContentPane().add(tester);
        f.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        f.setSize( 600, 600 );
        f.setLocationRelativeTo(null);
        f.setResizable(false);
        f.setVisible(true);
        tester.Start();
    }
    void Start(){
        while (true){
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Update();
        }
    }
    void Update(){
        x--;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawRect(x, 100, 50, 50);
    }
}
