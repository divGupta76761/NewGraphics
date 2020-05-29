package NumberIdentifier;

import java.awt.event.ActionListener;
import java.util.Random;
import java.util.RandomAccess;

public class NeuralNetwork {
    private int[] layers;
    private float[][] neurons;
    public float[][] biases;
    public float[][][] weights;
    public NeuralNetwork(int[] layers)
    {
        this.layers = new int[layers.length];
        for (int i = 0; i < layers.length; i++)
        {
            this.layers[i] = layers[i];
        }
        InitNeurons();
        InitBiases();
        InitWeights();
    }
    void InitNeurons(){
        neurons = new float[layers.length][];
        for(int i=0;i<layers.length;i++){
            neurons[i] = new float[layers[i]];
        }
        for(int i=0;i<neurons.length;i++){
            for(int j=0;j<neurons[i].length;j++){
                //System.out.println(neurons[i][j]);
            }
        }
    }
    void  InitBiases(){
        biases = new float[layers.length-1][];
        for(int i=1;i<layers.length;i++){
            biases[i-1] = new float[layers[i]];
        }
        for(int i=0;i<biases.length;i++){
            for(int j=0;j<biases[i].length;j++){
                biases[i][j] =Random(-3f,3f);

            }
        }
    }
    void InitWeights(){
        weights = new float[layers.length-1][][];
        for(int i=0;i<layers.length-1;i++){
            weights[i] = new float[layers[i+1]][layers[i]];
        }
        for(int i=0;i<weights.length;i++){
            for(int j=0;j<weights[i].length;j++){
                for(int k=0;k<weights[i][j].length;k++){
                    weights[i][j][k] = Random(-1.8f,1.8f);
                }
            }
        }
    }
    float Random(float min,float max){
        float r = (float) (min + Math.random() * (max - min));
        return r;
    }
    public float[] FeedForward(float[] inputs)
    {
        for (int i = 0; i < inputs.length; i++)
        {
            neurons[0][i] = inputs[i];
        }
        for(int i=1;i<layers.length;i++){
            for(int j=0;j<layers[i];j++){
                float value = 0f;
                float bias = biases[i-1][j];
                for(int k=0;k<layers[i-1];k++){
                    float weight = weights[i-1][j][k];

                    float neuronValue = neurons[i-1][k];
                    float toAdd = (weight*neuronValue);
                    value+=toAdd;
                }
                neurons[i][j] = Activation(value+bias);
            }
        }
        return neurons[neurons.length-1];
    }
    public float Activation(float value)
    {
        return (float)Math.tanh(value);
    }
    public void Mutuate(float chance,float biasValue,float weightValue){
        for(int i=0;i<weights.length;i++){
            for(int j=0;j<weights[i].length;j++){
                for(int k=0;k<weights[i][j].length;k++){
                    if(Random(0f,1f)>chance){
                        weights[i][j][k] +=Random(-weightValue,weightValue);
                    }
                }
            }
        }
        for(int i=0;i<biases.length;i++){
            for(int j=0;j<biases[i].length;j++){
                if(Random(0f,1f)>chance){
                    biases[i][j]+= Random(-biasValue,biasValue);
                }
            }
        }
    }
}
