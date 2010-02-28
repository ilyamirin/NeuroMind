package overmind;

import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import perceptron.Perceptron;

public class NeuroMind {

    String name = "overmind";
    
    Perceptron perceptron;

    XStream xstream;

    public NeuroMind() {
        xstream = new XStream();
        //xstream.autodetectAnnotations(true);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Perceptron getPerceptron() {
        return perceptron;
    }

    public void setPerceptron(Perceptron perceptron) {
        this.perceptron = perceptron;
    }

    public void saveToFile(String filename) {
        if (filename == null) filename = this.name + ".xml";
        try {
            File file = new File(filename);
            ObjectOutputStream out =
                    xstream.createObjectOutputStream(new FileOutputStream(file));
            out.writeObject(perceptron);
            out.close();
        } catch (Exception e) {
        }//try catch
    }//saveToFile

    public void loadFromFile(String filename) {
        if (filename == null) filename = this.name + ".xml";        
        try {
            File file = new File(filename);
            ObjectInputStream in =
                    xstream.createObjectInputStream(new FileInputStream(file));
            perceptron = (Perceptron) in.readObject();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }//try catch
    }//loadFormFile
}
