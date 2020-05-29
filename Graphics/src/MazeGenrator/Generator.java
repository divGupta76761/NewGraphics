package MazeGenrator;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Generator extends JFrame {
    //Maze Generator by depth first algorithm
    Remover remover;
    int frameSize = 600;
    boolean[][][]walls;
    int col;
    int row;
    int res = 20;
    boolean[][]visited;
    Generator(){

        //Frame
        setSize(frameSize+20,frameSize+20);
        setDefaultCloseOperation(3);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(3);
        setVisible(true);
    }
    public static void main(String[]args){
        Generator generator = new Generator();
        generator.Start(generator);
    }
    void Start(Generator g){

        //Array
        col = frameSize/res;
        row = frameSize/res;
        Random r = new Random();
        visited = new boolean[row][col];
        remover = new Remover(r.nextInt(row-1),r.nextInt(col-1),col,row);
        visited = remover.Change(visited);
        walls = new boolean[row][col][4];
        for(int i=0;i<walls.length;i++){
            for(int j=0;j<walls[i].length;j++){
                for(int k=0;k<walls[i][j].length;k++){
                    walls[i][j][k] = true;
                }
            }
        }
        g.repaint();
    }
    @Override
    public void paint(Graphics g){
        g.clearRect(0,0,getWidth(),getHeight());
        g.translate(10,10);
        for(int i=0;i<walls.length;i++){
            for(int j=0;j<walls[i].length;j++){
                for(int k=0;k<walls[i][j].length;k++){
                    if(visited[i][j]){
                        g.setColor(Color.RED);
                    }
                    int offsetX = 2;
                    int offsetY = 2;
                    int x = (i*res);
                    int y = (j*res);
                    if(k==0&&walls[i][j][k]){
                        g.drawLine(x+offsetX,y+offsetY,(x+res)-offsetX,y+offsetY);
                    }else if(k==1&&walls[i][j][k]){
                        g.drawLine((x+res)-offsetX,y+offsetY,(x+res)-offsetX,y+res-offsetY);
                    }else if(k==2&&walls[i][j][k]){
                        g.drawLine((x+res)-offsetX,y+res-offsetY,x+offsetX,y+res-offsetY);
                    }else if(k==3&&walls[i][j][k]){
                        g.drawLine(x+offsetX,y+res-offsetY,x+offsetX,y+offsetY);
                    }
                    g.setColor(Color.BLACK);

                }
            }
        }
    }

}
