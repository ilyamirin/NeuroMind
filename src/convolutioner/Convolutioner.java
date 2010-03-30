package convolutioner;

import java.util.Collection;
import java.util.Iterator;
import patterns.Pattern;
import perceptron.functions.ITransferFunction;

public class Convolutioner {

    //TODO: сделать несколько ядер?
    private Kernel kernel;

    public Convolutioner(int width, int height, ITransferFunction f) {
        kernel = new Kernel(width, height, f);
    }

    //TODO: реализовать
    public void teach(Pattern pattern) {

    }

    public void teach(Collection<Pattern> patterns) {
        for (Iterator<Pattern> it = patterns.iterator(); it.hasNext();)
            teach(it.next());
    }//teachList

    //TODO: проетестировать
    public double[] recognise(double[][] inputs) {
        return kernel.activate(inputs);
    }//recognise

}
