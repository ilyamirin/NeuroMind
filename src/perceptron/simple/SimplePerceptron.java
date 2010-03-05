package perceptron.simple;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import patterns.IPattern;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import patterns.IGetPatternObject;
import patterns.IPatternStore;

//Старый перцептрон
@XStreamAlias("simplePerceptron")
public class SimplePerceptron {
    
    private SimpleNeuron[] neurons;

    @XStreamAsAttribute
    private int v = 1;

    @XStreamOmitField
    XStream xstream = new XStream();

    public SimplePerceptron() {
    }

    public SimplePerceptron(int inputs, int neurons) {
        this.neurons = new SimpleNeuron[neurons];
        for (int i = 0; i < neurons; i++)
            this.neurons[i] = new SimpleNeuron(inputs);
        
        xstream.processAnnotations(this.getClass());
        xstream.processAnnotations(this.neurons[0].getClass());
    }//constructor

    public int[] recognize(int[] input) {
        int[] outputs = new int[neurons.length];
        for (int i = 0; i < neurons.length; i++) 
            outputs[i] = neurons[i].activate(input);
        return outputs;
    }//recognize

    public void teach(IPattern pattern) {
        int d = 0;
        int[] t = recognize(doubleToInt(pattern.getInputs()));
        if(!Arrays.equals(t, doubleToInt(pattern.getInputs())))
            for (int i = 0; i < neurons.length; i++) {
                d = doubleToInt(pattern.getOutputs())[i] - t[i];
                neurons[i].changeWeights(v, d, doubleToInt(pattern.getInputs()));
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

    public void teach(IGetPatternObject getPatternObject, int times) {
        for (int i = 0; i < times; i++)
            teach(getPatternObject.getPattern());
    }//testFromgetPatternObject

    public int test(IPattern pattern) {
        int result = 0;
        int[] recognize = recognize(doubleToInt(pattern.getInputs()));
        for (int i = 0; i < recognize.length; i++)
            result += Math.abs(recognize[i] - pattern.getOutputs()[i]);
        /*
        if(!Arrays.equals(recognize(doubleToInt(pattern.getInputs())),
                doubleToInt(pattern.getOutputs())))
            result++;
        */
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
        return xstream.toXML(this);
    }

    public boolean save(String filename) {
        try {
            FileOutputStream stream = new FileOutputStream(new File(filename));
            ObjectOutputStream out = xstream.createObjectOutputStream(stream);
            out.writeInt(v);
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
            this.v = in.readInt();
            for (int i = 0; i < neurons.length; i++)
                neurons[i] = (SimpleNeuron) in.readObject();
            in.close();
            return true;
        } catch (Exception e) {
            return false;
        }//try catch
    }//load

    public static int[] doubleToInt(double[] in) {
        int[] result = new int[in.length];
        for (int i = 0; i < in.length; i++)
            result[i] = (int) in[i];
        return result;
    }
}
