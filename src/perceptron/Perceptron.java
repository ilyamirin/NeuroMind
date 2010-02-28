package perceptron;

import com.thoughtworks.xstream.XStream;
//import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import patterns.IPattern;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import patterns.IGetPatternObject;
import patterns.IPatternStore;

//@XStreamAlias("perceptron")
public class Perceptron {
    
    private Neuron[] neurons;

    //@XStreamAsAttribute
    private int v = 1;

    public Perceptron(int inputs, int neurons) {
        this.neurons = new Neuron[neurons];
        for (int i = 0; i < neurons; i++)
            this.neurons[i] = new Neuron(inputs);
    }//constructor

    public int[] recognize(int[] input) {
        int[] outputs = new int[neurons.length];
        for (int i = 0; i < neurons.length; i++) 
            outputs[i] = neurons[i].activate(input);
        return outputs;
    }//recognize

    public void teach(IPattern pattern) {
        int d = 0;
        int[] t = recognize(pattern.getInputs());
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
            result++;
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

    @Override
    public String toString() {
        XStream xstream = new XStream();
        xstream.autodetectAnnotations(true);
        return xstream.toXML(this);
    }
}
