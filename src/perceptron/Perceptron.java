package perceptron;

import com.thoughtworks.xstream.XStream;
//import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import patterns.Pattern;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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

    public void teach(Pattern pattern) {
        int d = 0;
        int[] t = recognize(pattern.getInputs());
        if(!Arrays.equals(t, pattern.getInputs()))
            for (int i = 0; i < neurons.length; i++) {
                d = pattern.getOutputs()[i] - t[i];
                neurons[i].changeWeights(v, d, pattern.getInputs());
            }//for
    }//teach

    public int test(List<Pattern> patterns) {
        int result = 0;
        for (Iterator<Pattern> it = patterns.iterator(); it.hasNext();) {
            Pattern pattern = it.next();
            if(!Arrays.equals(recognize(pattern.getInputs()), pattern.getOutputs()))
                result += 1;            
        }//for
        return result;
    }//test

    @Override
    public String toString() {
        XStream xstream = new XStream();
        xstream.autodetectAnnotations(true);
        return xstream.toXML(this);
    }
}
