package perceptron.standart.functions;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;


@XStreamAlias("threshold")
public class Threshold implements ITransferFunction {

    @XStreamAsAttribute
    private double threshold;

    public Threshold() {
        this.threshold = 0.5;
    }

    public Threshold(double threshold) {
        this.threshold = threshold;
    }

    public double transfer(double sum) {
        if(sum > threshold) {
            return 1.0;
        } else {
            return 0.0;
        }//else if
    }//transfer

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

}
