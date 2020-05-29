package NumberIdentifier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class Identifier extends JPanel   {
    public String name = "WeightsAndBiases.txt";
    File dataFile = null;
    NeuralNetwork network;
    static int imageSize = 40;
    static JFrame f;
    BufferedImage mainImage;
    static int frameSize = 400;
    boolean drawing = false;
    boolean clearing = false;
    Identifier(){
        setLayout(null);
        dataFile = new File(name);
    }
    public static void main(String[]args){
        Identifier identifier = new Identifier();
        f = new JFrame();
        f.getContentPane().add(identifier);
        f.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        f.setSize( frameSize,frameSize );
        f.setLocationRelativeTo(null);
        f.setResizable(false);
        f.setVisible(true);
        identifier.Start();
    }
    public void Enter(){
        float[]inputs = new float[imageSize*imageSize];
        for(int i=0,index = 0;i<mainImage.getWidth();i++){
            for(int j=0;j<mainImage.getHeight();j++){
                inputs[index] = Squish(mainImage.getRGB(i,j));
                //System.out.println(inputs[index]);
                index++;

            }
        }
        float[]outputs = network.FeedForward(inputs);
        int number = 0;
        float n = Float.MIN_VALUE;
        for(int i=0;i<outputs.length;i++){
            if(n<outputs[i]){
                n = outputs[i];
                number = i;
            }
        }
        System.out.println(number);
        network.Mutuate(0.4f,0.1f,0.05f);
        Save();
    }
    void Start(){
        int[]layers = {(imageSize*imageSize),100,90,30,100,10};
        network = new NeuralNetwork(layers);
        if(dataFile.exists()){
            ToSave data = Load();
            network.weights = data.weights;
            network.biases = data.biases;
        }
        mainImage = new BufferedImage(imageSize,imageSize,BufferedImage.TYPE_INT_RGB);
        for(int i=0;i<mainImage.getWidth();i++){
            for(int j=0;j<mainImage.getHeight();j++){
                mainImage.setRGB(i,j,Color.BLACK.getRGB());
            }
        }
        f.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==10){
                    Enter();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        f.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton()==1) {
                    drawing = true;
                    clearing = false;
                }else if(e.getButton()==3){
                    clearing = true;
                    drawing = false;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.getButton()==1) {
                    drawing = false;
                }else if(e.getButton()==3){
                    clearing = false;
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        repaint();
        while (true){
            Update();
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    void Update(){
        if(drawing){
            Point e = MouseInfo.getPointerInfo().getLocation();
            SwingUtilities.convertPointFromScreen(e,f);
            int x = (int) e.getX();
            int y = (int) e.getY();
            x /= (frameSize / imageSize);
            y /= (frameSize / imageSize);
            y -= 3;
            x -= 1;
            if(x>0&&y>0&&x<mainImage.getWidth()&&y<mainImage.getHeight()) {
                mainImage.setRGB(x, y, Color.WHITE.getRGB());
            }
            repaint();
        }
        if(clearing){
            Point e = MouseInfo.getPointerInfo().getLocation();
            SwingUtilities.convertPointFromScreen(e,f);
            int x = (int) e.getX();
            int y = (int) e.getY();
            x /= (frameSize / imageSize);
            y /= (frameSize / imageSize);
            y -= 3;
            x -= 1;
            if(x>0&&y>0&&x<mainImage.getWidth()&&y<mainImage.getHeight()) {
                mainImage.setRGB(x, y, Color.BLACK.getRGB());
            }
            repaint();
        }
    }
    @Override
    public void paintComponent(Graphics g){
        g.clearRect(0,0,getWidth(),getHeight());
        g.drawImage(mainImage,0,0,frameSize,frameSize,null);
        g.setColor(Color.BLACK);
    }
    float Squish(float x){
        double e= Math.E;
        float value = (float) (1/(1+Math.pow(e,-x)));
        return value;
    }
    public void Save(){
        ToSave toSave = new ToSave(network.biases,network.weights);
        try{
            FileOutputStream stream = new FileOutputStream(name);
            ObjectOutputStream out = new ObjectOutputStream(stream);
            out.writeObject(toSave);

            out.close();
            stream.close();
        }catch (IOException e){ }
    }
    public ToSave Load(){
        ToSave data = null;
        try {
            FileInputStream stream = new FileInputStream(name);
            ObjectInputStream in = new ObjectInputStream(stream);
            data = (ToSave) in.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }
}
