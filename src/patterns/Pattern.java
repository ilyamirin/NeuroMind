package patterns;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("pattern")
public class Pattern {

    private Long id;

    private int[] inputs;

    private int[] outputs;

    public Pattern() {
    }

    public Pattern(int[] inputs, int[] outputs) {
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
