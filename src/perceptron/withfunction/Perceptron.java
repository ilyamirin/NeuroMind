package perceptron.withfunction;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import patterns.IGetPatternObject;
import patterns.IPattern;
import patterns.IPatternStore;
import perceptron.withfunction.functions.ITransferFunction;

public class Perceptron {

    private Neuron[] neurons;

    private double v = 0.001;

    @XStreamOmitField
    XStream xstream = new XStream();

    public double[] recognize(double[] inputs) {
        double[] result = new double[this.neurons.length];
        for (int i = 0; i < this.neurons.length; i++)
            result[i] = this.neurons[i].activate(inputs);
        return result;
    }//recognize

    public void teach(IPattern pattern) {
        double d = 0.0;
        double[] t = recognize(pattern.getInputs());
        if(!Arrays.equals(t, pattern.getInputs()))
            for (int i = 0; i < neurons.length; i++) {
                d = pattern.getOutputs()[i] - t[i];
                neurons[i].changeWeights(v, d, pattern.getInputs());
            }//for
    }//teach

    public void teach(List<IPattern> patterns) {
        for (Iterator<IPattern> it = patterns.iterator(); it.hasNext();)
            teach(it.next());
    }//teachList

    public void teach(IPatternStore store) {
        IPattern pattern;
        long i = 0;
        while((pattern = store.getPatternById(i)) != null) {
            this.teach(pattern);
            i++;
        }//while
    }//teachList

    public int test(IPattern pattern) {
        int result = 0;
        if(!Arrays.equals(recognize(pattern.getInputs()), pattern.getOutputs()))
            result ++;
        return result;
    }//test

    public int test(List<IPattern> patterns) {
        int result = 0;
        for (Iterator<IPattern> it = patterns.iterator(); it.hasNext();)
            result += test(it.next());
        return result;
    }//test

    public int test(IGetPatternObject getPatternObject, int times) {
        int result = 0;
        while(times > 0) {
            result += test(getPatternObject.getPattern());
            times--;
        }
        return result;
    }//test

    public Perceptron(int inputs, int neurons, ITransferFunction function) {
        this.neurons = new Neuron[neurons];
        for (int i = 0; i < neurons; i++)
            this.neurons[i] = new Neuron(inputs, function);
        xstream.processAnnotations(this.getClass());
        xstream.processAnnotations(Neuron.class);
    }//constructor

    @Override
    public String toString() {
        return xstream.toXML(this);
    }

}
