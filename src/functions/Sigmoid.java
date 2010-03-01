package functions;

public class Sigmoid implements ITransferFunction{

    public int transfer(int sum) {
        return (int) ( 1 / (1 + Math.pow(Math.E, -sum)) * 100);
    }

}
