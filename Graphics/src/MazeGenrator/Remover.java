package MazeGenrator;

import java.util.ArrayList;
import java.util.List;

public class Remover {
    Vector2 pos;
    int mX;
    int mY;
    public Remover(int x,int y,int mx,int my){
        if(x==0){
            x++;
        } if(y==0){
            y++;
        }
        pos = new Vector2(x,y);
        this.mX = mx;
        this.mY = my;
    }
    public boolean[][] Change(boolean[][]visited){
         if(!visited[this.pos.x][this.pos.y]){
             visited[this.pos.x][this.pos.y] = true;
         }
         System.out.println(this.pos.x+" sds "+this.pos.y);
         Vector2[]n = neigh(this.pos.x,this.pos.y);

         int z=0;
         for(z=0;z<n.length;z++){
             if(n[z]!=null){
                 System.out.println(n[z].x+" "+n[z].y);
                 System.out.println("Index = "+z);
             } else{
                if(z!=2){
                    break;
                }
             }
         }
         Vector2[]neighBours = new Vector2[z];
         System.out.println(neighBours.length);
         for(int i=0;i<neighBours.length;i++){

         }
         return visited;
    }
    Vector2[]neigh(int x,int y){
        Vector2[]indexes = new Vector2[8];
        int index = 0;
        for(int i=-1;i<2;i++){
            for(int j=-1;j<2;j++){
                if(i==0||j==0){
                    //System.out.println(index);
                    int nx = x+i;
                    int ny = y+j;
                    if(nx>=0&&ny>=0&&nx<this.mX&&ny<this.mY) {
                        indexes[index] = new Vector2(nx, ny);
                        index++;
                    }
                }

            }
        }
        for(int i=0;i<indexes.length;i++){
            if(this.pos.x==indexes[i].x&&this.pos.y==indexes[i].y){
                indexes[i]=null;
                //System.out.println("Break");
                break;
            }
        }


        return indexes;
    }


}
