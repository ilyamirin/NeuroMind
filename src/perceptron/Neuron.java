package perceptron;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import perceptron.functions.ITransferFunction;

@XStreamAlias("neuron")
public class Neuron {
    
    private double[] weights;
        
    @XStreamAsAttribute
    private ITransferFunction function;

    @XStreamAsAttribute
    private IAdapter adapter;

    public double activate(double[] values) {
        double sum = 0;
        for (int i = 0; i < weights.length; i++)
            sum += weights[i] * values[i];
        if(adapter != null) 
            sum = adapter.adapt(sum);        
        return function.transfer(sum);
    }//activate

    public void changeWeights(double v, double d, double[] x) {
        for (int i = 0; i < weights.length; i++)
            weights[i] += v * d * x[i];            
    }//changeWeights

    public Neuron() {
    }

    public Neuron(int weights, ITransferFunction function) {
        this.weights = new double[weights];
        for (int i = 0; i < this.weights.length; i++)
            this.weights[i] = Math.random();        
        this.function = function;        
    }//constructor

    public Neuron(int weights, ITransferFunction function, IAdapter adapter) {
        this(weights, function);
        this.adapter = adapter;
    }//constructor

    public IAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(IAdapter adapter) {
        this.adapter = adapter;
    }

    public ITransferFunction getFunction() {
        return function;
    }

    public void setFunction(ITransferFunction function) {
        this.function = function;
    }

    public double[] getWeights() {
        return weights;
    }

    public void setWeights(double[] weights) {
        this.weights = weights;
    }

    @Override
    public String toString() {
        XStream xstream = new XStream();
        xstream.processAnnotations(this.getClass());
        xstream.processAnnotations(function.getClass());
        xstream.processAnnotations(adapter.getClass());
        return xstream.toXML(this);
    }
    
}
