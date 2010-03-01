package perceptron.withfunction.functions;

public class Sigmoid implements ITransferFunction{
    
    public double transfer(double sum) {
        return ( 1 / (1 + Math.pow(Math.E, -sum)) ) ;
    }

}
