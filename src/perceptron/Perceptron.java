package perceptron;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import patterns.Pattern;
import perceptron.functions.ITransferFunction;
import perceptron.functions.Sigmoid;

public class Perceptron {

    private Neuron[] neurons;

    @XStreamAsAttribute
    private double v = 0.001;

    @XStreamOmitField
    XStream xstream = new XStream();

    public double[] recognize(double[] inputs) {
        double[] result = new double[this.neurons.length];
        for (int i = 0; i < this.neurons.length; i++)
            result[i] = this.neurons[i].activate(inputs);
        return result;
    }//recognize

    public double[] recognize(Pattern p) {
        return recognize(p.getInputs());
    }

    public double[] recognize(List<Double> list) {
        double[] inputs = new double[list.size()];
        for(int i = 0; list.size() > 0; i++) 
            inputs[i] = list.get(i);        
        return recognize(inputs);
    }
    
    public void teach(Pattern pattern) {
        double d = 0.0;
        double[] t = recognize(pattern.getInputs());
        if(!Arrays.equals(t, pattern.getInputs()))
            for (int i = 0; i < neurons.length; i++) {
                d = pattern.getOutputs()[i] - t[i];
                neurons[i].changeWeights(v, d, pattern.getInputs());
            }//for
    }//teach

    public void teach(Collection<Pattern> patterns) {
        for (Iterator<Pattern> it = patterns.iterator(); it.hasNext();)
            teach(it.next());
    }//teachList

    public int test(Pattern pattern) {
        double mistake = 0.0;
        double[] recognize = this.recognize(pattern.getInputs());        
        for (int i = 0; i < pattern.getOutputs().length; i++) 
            mistake += Math.abs(pattern.getOutputs()[i] - recognize[i]);
        if(mistake < (pattern.getOutputs().length * 0.05)){
            return 0;
        } else {
            return 1;
        }//else if
    }//test

    public int test(Collection<Pattern> patterns) {
        int result = 0;
        for (Iterator<Pattern> it = patterns.iterator(); it.hasNext();)
            result += test(it.next());        
        return result;
    }//test

    public Perceptron() {
    }
    
    public Perceptron(int inputs, int neurons, ITransferFunction function) {
        this.neurons = new Neuron[neurons];
        for (int i = 0; i < neurons; i++)
            this.neurons[i] = new Neuron(inputs, function);
        xstream.processAnnotations(this.getClass());
        xstream.processAnnotations(Neuron.class);
        xstream.processAnnotations(Sigmoid.class);
    }//constructor

    @Override
    public String toString() {
        return xstream.toXML(this);
    }

    public boolean save(String filename) {
        try {
            FileOutputStream stream = new FileOutputStream(new File(filename));
            ObjectOutputStream out = xstream.createObjectOutputStream(stream);
            out.writeDouble(v);
            for (int i = 0; i < neurons.length; i++)
                out.writeObject(neurons[i]);
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }//try catch
    }//save

    public boolean load(String filename) {
        try {
            FileInputStream stream = new FileInputStream(new File(filename));
            ObjectInputStream in = xstream.createObjectInputStream(stream);
            this.v = in.readDouble();
            for (int i = 0; i < neurons.length; i++)
                neurons[i] = (Neuron) in.readObject();
            in.close();
            return true;
        } catch (Exception e) {
            return false;
        }//try catch
    }//load

    public double getV() {
        return v;
    }

    public void setV(double v) {
        this.v = v;
    }

}
