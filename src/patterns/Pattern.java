package patterns;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.IndexColumn;

@Entity
@Table(name="patterns")
public class Pattern implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CollectionOfElements
    @IndexColumn(name="inputs")
    private int[] inputs;

    @CollectionOfElements
    @IndexColumn(name="outputs")
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

    @Override
    public String toString() {
        String res = new String();
        for (int i = 0; i < inputs.length; i++)
            res += inputs[i] + " ";
        res += "| ";
        for (int i = 0; i < outputs.length; i++)
            res += outputs[i] + " ";
        return res;
    }

}
