package patterns.XMLPatterns;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import patterns.IPattern;

@XStreamAlias("pattern")
public class XMLPattern implements IPattern {

    private double[] inputs;

    private double[] outputs;

    public XMLPattern() {
    }

    public XMLPattern(double[] inputs, double[] outputs) {
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public double[] getInputs() {
        return inputs;
    }

    public void setInputs(double[] inputs) {
        this.inputs = inputs;
    }

    public double[] getOutputs() {
        return outputs;
    }

    public void setOutputs(double[] outputs) {
        this.outputs = outputs;
    }

}
