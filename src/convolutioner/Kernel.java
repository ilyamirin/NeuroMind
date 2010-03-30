package convolutioner;

import perceptron.Neuron;
import perceptron.functions.ITransferFunction;

//TODO: протестировать
public class Kernel extends Neuron {

    private int width;

    private int height;

    public Kernel(int width, int height, ITransferFunction function) {
        super(width * height, function);
        this.width = width;
        this.height = height;
    }

    public double[] makeInputs(double[][] inputs, int x1, int y1) {        
        double[] result = new double[super.getWeights().length];
        int p = 0;
        int x2 = x1 + width;
        int y2 = y1 + height;
        for(int i = x1; i < x2; i++)
            for(int j = y1; j < y2; j++) {
                result[p] = inputs[i][j];
                p++;
            }
        return result;
    }
    
    public double[] activate(double[][] inputs) {
        int w = inputs.length-width;
        int h = inputs[0].length-height;

        int resultLength = (w + 1) * (h + 1);
        double[] result = new double[resultLength];

        for(int i = 0; i < w; i++)
            for(int j = 0; j < h; j++)
                result[i + j * w] = super.activate(makeInputs(inputs, i, j));
        return result;
    }

}
