package perceptron.standart;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import patterns.IGetPatternObject;
import patterns.IPattern;
import patterns.IPatternStore;
import perceptron.standart.functions.ITransferFunction;
import perceptron.standart.functions.Sigmoid;

@XStreamAlias("perceptron")
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

    //TODO: реализовать функцию распознавания в двоичном или целочисленном ыормате

    //TODO: вынести стартегию обучения во внешний класс
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
    }//teachPatternStore

    public void teach(IGetPatternObject getPatternObject, int times) {
        for (int i = 0; i < times; i++)
            teach(getPatternObject.getPattern());
    }//testFromgetPatternObject

    public double test(IPattern pattern) {
        double result = 0.0;
        double[] recognize = this.recognize(pattern.getInputs());        
        for (int i = 0; i < pattern.getOutputs().length; i++) 
            result += Math.abs(pattern.getOutputs()[i] - recognize[i]);        
        return result;
    }//test

    public double test(List<IPattern> patterns) {
        double result = 0;
        for (Iterator<IPattern> it = patterns.iterator(); it.hasNext();)
            result += test(it.next());        
        return result;
    }//test

    public double test(IGetPatternObject getPatternObject, int times) {
        double result = 0;
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