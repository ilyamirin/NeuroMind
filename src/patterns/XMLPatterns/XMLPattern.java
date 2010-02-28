package patterns.XMLPatterns;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import patterns.IPattern;

@XStreamAlias("pattern")
public class XMLPattern implements IPattern {

    private int[] inputs;

    private int[] outputs;

    public XMLPattern() {
    }

    public XMLPattern(int[] inputs, int[] outputs) {
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public int[] getInputs() {
        return inputs;
    }

    public void setInputs(int[] inputs) {
        this.inputs = inputs;
    }

    public int[] getOutputs() {
        return outputs;
    }

    public void setOutputs(int[] outputs) {
        this.outputs = outputs;
    }

}
