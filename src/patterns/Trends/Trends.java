package patterns.Trends;

import java.util.Random;
import patterns.IPatternStore;
import patterns.Pattern;

public class Trends implements IPatternStore {

    private Random random = new Random();

    private int length;

    private double probability = 0.25;

    public Trends(int length) {
        this.length = length;
    }

    private void generateUp(double[] inputs) {
        for(int i = 0; i < length; i++)
            inputs[i] = random.nextInt(10+(i/10));
    }

    private void generateDown(double[] inputs) {
        for(int i = 0; i < length; i++)
            inputs[i] = random.nextInt(20-(i/10));
    }

    private void generateHill(double[] inputs) {
        int breakePoint = length / 2;
        for(int i = 0; i < breakePoint; i++)
            inputs[i] = random.nextInt(10+(i/10));
        for(int i = breakePoint; i < length; i++)
            inputs[i] = random.nextInt(20-(i/10));
    }

    private void generateSome(double[] inputs) {
        for(int i = 0; i < length; i++){
            inputs[i] = 10 + random.nextInt(10);
        }//for
    }

    private Pattern generate() {
        double[] inputs = new double[length];
        double[] outputs = new double[3];
        
        if(Math.random() < probability) {
            generateHill(inputs);
            outputs[0] = 0;
            outputs[1] = 0;
            outputs[2] = 1;
        } else if(Math.random() < probability) {
            generateDown(inputs);
            outputs[0] = 0;
            outputs[1] = 1;
            outputs[2] = 0;
        } else if(Math.random() < probability) {
            generateUp(inputs);
            outputs[0] = 1;
            outputs[1] = 0;
            outputs[2] = 0;
        } else {
            generateSome(inputs);
            outputs[0] = 0;
            outputs[1] = 0;
            outputs[2] = 0;
        }//if else
        
        return new Pattern(inputs, outputs);

    }//ganerate

    public int getCount() {
        return Integer.MAX_VALUE;
    }

    public void savePattern(Pattern pattern) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Pattern getPattern(int id) {
        return generate();
    }

    public Pattern removePattern(int id) {
        return generate();
    }
}