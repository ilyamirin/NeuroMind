package perceptron.simple;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import java.util.Random;

@XStreamAlias("neuron")
public class Neuron {
    
    private int[] weights;

    @XStreamAsAttribute
    private int threshold;

    public Neuron(int weights) {
        Random random = new Random();
        this.weights = new int[weights];
        for (int i = 0; i < this.weights.length; i++) 
            this.weights[i] = random.nextInt(10);
        this.threshold = random.nextInt(5 * weights);
    }//constructor

    public int activate(int[] values) {
        int sum = 0;
        for (int i = 0; i < weights.length; i++)
            sum += weights[i] * values[i];
        if(sum > threshold) {
            return 1;
        } else {
            return 0;
        }//elseif
    }//activate

    public void changeWeights(int v, int d, int[] x) {
        for (int i = 0; i < weights.length; i++)
            weights[i] += v * d * x[i];            
    }//changeWeights
    
}
