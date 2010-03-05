package perceptron.absract;

import java.util.List;

public abstract class AbstractNeuron<T extends Number> {

    public List<T> weights[];

    public abstract <T> T activate(List<T> inputs);

}
