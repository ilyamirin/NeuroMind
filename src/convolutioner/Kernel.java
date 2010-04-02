package convolutioner;

import patterns.Image.ImagePattern;
import perceptron.Neuron;
import perceptron.functions.ITransferFunction;

//TODO: протестировать
public class Kernel extends Neuron {

    private int width;

    private int height;

    public Kernel(int width, int height, ITransferFunction function) {
        super(width * height * 3, function);
        this.width = width;
        this.height = height;
    }
    
    public double[] activate(ImagePattern ip) {
        int w = ip.getWidth()-width;
        int h = ip.getHeight()-height;

        int resultLength = (w + 1) * (h + 1);
        double[] result = new double[resultLength];

        for(int i = 0; i < w; i++)
            for(int j = 0; j < h; j++)
                result[i + j * w] = 
                        super.activate(ip.getInputs(i, j, width, height));
        return result;
    }//activate

}
