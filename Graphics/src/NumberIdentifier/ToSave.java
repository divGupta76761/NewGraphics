package NumberIdentifier;
import java.io.Serializable;
public class ToSave implements Serializable{
    public float[][]biases;
    public float[][][]weights;
    public ToSave(float[][]b,float[][][]w){
        this.biases = b;
        this.weights = w;
    }
}
