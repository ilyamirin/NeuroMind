package perceptron.standart.functions;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("sigmoid")
public class Sigmoid implements ITransferFunction {

    @XStreamAsAttribute
    private double a;

    public Sigmoid() {
        this.a = 1.0;
    }

    public Sigmoid(double a) {
        this.a = a;
    }

    public double transfer(double sum) {
        return ( 1 / (1 + Math.pow(Math.E, -a * sum)) ) ;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    } 

}
