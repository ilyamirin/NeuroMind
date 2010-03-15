package patterns;

import java.util.Arrays;

public class Pattern {

    private Long id;

    private double[] inputs;

    private double[] outputs;

    public Pattern() {
    }

    public Pattern(double[] inputs, double[] outputs) {
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if(this.getClass() != o.getClass()) return false;
        Pattern pattern = (Pattern) o;
        if(!this.getId().equals(pattern.getId())) return false;
        if(!Arrays.equals(this.getInputs(), pattern.getInputs())) return false;
        if(!Arrays.equals(this.getOutputs(), pattern.getOutputs())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 89 * hash + Arrays.hashCode(this.inputs);
        hash = 89 * hash + Arrays.hashCode(this.outputs);
        return hash;
    }

}
