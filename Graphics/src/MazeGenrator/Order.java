package MazeGenrator;

import java.util.Arrays;
import java.util.Random;

public class Order {
    public boolean finished = false;
    public int []order;
    public Order(int size){
        order = new int[size];
    }
    public void ChangeOrder(){
        /*Random r = new Random();
        int i = r.nextInt(order.length);
        int j = r.nextInt(order.length);
        int temp = order[i];
        order[i] = order[j];
        order[j] = temp;*/
        int largestX = -1;
        int largestY = -1;
        //STEP 1
        for(int i=0;i<order.length-1;i++){
            if(order[i]<order[i+1]){
                largestX = i;
            }
        }
        if(largestX==-1){
            finished = true;
            return;
        }
        for(int i=0;i<order.length;i++){
            if(order[i]>order[largestX]){
                largestY = i;
            }
        }
        int temp = order[largestX];
        order[largestX] = order[largestY];
        order[largestY] = temp;
        int endIndex = order.length;
        int startIndex = largestX+1;
        int[]m = Arrays.copyOfRange(order,startIndex,endIndex);
        m = reverse(m,m.length);
        for(int w=startIndex;w<order.length;w++){
            order[w] = m[w-startIndex];
        }
        /*System.out.println();
        for(int i=0;i<order.length;i++){
            System.out.print(order[i]);
        }*/
    }
    int[] reverse(int []a, int n)
    {
        int[] b = new int[n];
        int j = n;
        for (int i = 0; i < n; i++) {
            b[j - 1] = a[i];
            j = j - 1;
        }
        return b;
    }


}
