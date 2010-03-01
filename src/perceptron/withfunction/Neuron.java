package perceptron.withfunction;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import perceptron.withfunction.functions.ITransferFunction;

@XStreamAlias("neuron")
public class Neuron {
    
    private double[] weights;

    @XStreamAsAttribute
    private double threshold;

    private ITransferFunction function;

    public double activate(double[] values) {
        double sum = 0;
        for (int i = 0; i < weights.length; i++)
            sum += weights[i] * values[i];
        return function.transfer(sum);
    }//activate

    public void changeWeights(double v, double d, double[] x) {
        for (int i = 0; i < weights.length; i++)
            weights[i] += v * d * x[i];            
    }//changeWeights

    public Neuron(int weights, ITransferFunction function) {
        this.weights = new double[weights];
        for (int i = 0; i < this.weights.length; i++)
            this.weights[i] = Math.random();
        this.threshold = Math.random() * weights;
        this.function = function;        
    }//constructor

    public ITransferFunction getFunction() {
        return function;
    }

    public void setFunction(ITransferFunction function) {
        this.function = function;
    }
    
}
