package MazeGenrator;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SalesMan extends JPanel {
    int totalCities = 15;
    Vector2[]cities;
    int[] best;
    float recorded;
    static int frameSize = 600;
    Order order;
    int count = 0;

    long factorial(long n){
        if (n == 0)
            return 1;
        else
            return(n * factorial(n-1));
    }
    SalesMan(){
        order = new Order(totalCities);
        cities = new Vector2[totalCities];
        best = null;
        setLayout(null);
    }
    public static void main(String[]args){
        SalesMan man = new SalesMan();
        JFrame f = new JFrame();
        f.getContentPane().add(man);
        f.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        f.setSize( frameSize+50,frameSize+50 );
        f.setLocationRelativeTo(null);
        f.setResizable(false);
        f.setVisible(true);
        man.Start();

    }
    void Start(){
        Random r = new Random();
        for(int i=0;i<cities.length;i++){
            cities[i] = new Vector2(r.nextInt(600),r.nextInt(600));
            order.order[i] = i;
        }
        recorded = calcDis();
        repaint();
        while (!order.finished){
            Update();
            count++;
            try {
                TimeUnit.MILLISECONDS.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    void Update(){

        /*Random r = new Random();
        int i= r.nextInt(totalCities);
        int j= r.nextInt(totalCities);
        Vector2 temp = cities[i];
        cities[i] = cities[j];
        cities[j] = temp;*/

            order.ChangeOrder();
            float d = calcDis();
            if (recorded > d) {
                recorded = d;
                best = order.order.clone();
                System.out.println(recorded);
            }
            repaint();
        
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.clearRect(0,0,getWidth(),getHeight());
        Graphics2D g2 = (Graphics2D)g;
        g2.setFont(new Font("Arial", Font.BOLD,20));
        g2.drawString(Integer.toString(count),getWidth()/2,getHeight()-20);
        if(!order.finished) {
            int[] xPoints = new int[totalCities];
            int[] yPoints = new int[totalCities];
            for (int i = 0; i < order.order.length; i++) {
                g2.setColor(Color.BLACK);

                g2.fillOval(cities[order.order[i]].x, cities[order.order[i]].y, 10, 10);
                xPoints[i] = cities[order.order[i]].x + 5;
                yPoints[i] = cities[order.order[i]].y + 5;
            }
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(1));
            g2.drawPolyline(xPoints, yPoints, totalCities);
        }
        if(order.finished) {
            g2.drawString(Integer.toString(count),getWidth()/2,getHeight()-20);
            for (int i = 0; i < order.order.length; i++) {
                g2.setColor(Color.BLACK);

                g2.fillOval(cities[order.order[i]].x, cities[order.order[i]].y, 10, 10);
            }
            /*int[] bestXpoints = new int[best.length];
            int[] bestYpoints = new int[best.length];
            for (int l = 0; l < best.length; l++) {
                bestXpoints[l] = best[l].x + 5;
                bestYpoints[l] = best[l].y + 5;
            }
            g2.setColor(Color.BLUE);
            g2.setStroke(new BasicStroke(3));
            g2.drawPolyline(bestXpoints, bestYpoints, best.length);*/
            int []bestXpoints = new int[best.length];
            int []bestYpoints = new int[best.length];
            for(int i=0;i<best.length;i++){
                Vector2 city = cities[best[i]];
                bestXpoints[i] = city.x+5;
                bestYpoints[i] = city.y+5;
            }
            g2.setColor(Color.BLUE);
            g2.setStroke(new BasicStroke(3));
            g2.drawPolyline(bestXpoints, bestYpoints, best.length);
        }
    }

    float calcDis(){
        /*float sum = 0f;
        for(int i=0;i<totalCities-1;i++){
            float d = (float) Math.sqrt(Math.pow(cities[i].x-cities[i+1].x,2)+Math.pow(cities[i].y-cities[i+1].y,2));
            sum+=d;
        }
        return sum;*/
        float sum = 0f;
        for(int i=0;i<order.order.length-1;i++){
            float d = (float)Math.sqrt(Math.pow(cities[order.order[i]].x-cities[order.order[i+1]].x,2)+Math.pow(cities[order.order[i]].y-cities[order.order[i+1]].y,2));
            sum+=d;
        }
        return sum;
    }
}
