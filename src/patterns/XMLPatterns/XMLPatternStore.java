package patterns.XMLPatterns;

import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import patterns.IGetPatternObject;
import patterns.IPatternStore;
import patterns.IPattern;

public class XMLPatternStore implements IPatternStore {

    private XStream xstream;
    
    private ObjectOutputStream out;
    private ObjectInputStream in;
    
    private String filename;

    public XMLPatternStore() {
        xstream = new XStream();
        xstream.processAnnotations(XMLPattern.class);
    }

    public XMLPatternStore(String filename) {
        xstream = new XStream(); 
        xstream.processAnnotations(XMLPattern.class);
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    private boolean initOut() {        
        try {
            FileOutputStream stream = new FileOutputStream(new File(filename));
            out = xstream.createObjectOutputStream(stream);
            return true;
        } catch (IOException iOException) {
            return false;
        }//try catch
    }//constructor

    private boolean initIn() {        
        try {
            FileInputStream stream = new FileInputStream(new File(filename));
            in = xstream.createObjectInputStream(stream);
            //TODO: проверить работоспособность с незакрытым in
            return true;
        } catch (IOException iOException) {
            return false;
        }//try catch
    }//constructor

    public void savePatterns(IGetPatternObject gpo, int times) {
        if(initOut()) {
            try {
                while (times > 0) {
                    out.writeObject(gpo.getPattern());
                    out.flush();
                    times--;
                }//while
                out.close();
            } catch (IOException iOException) {
            }
        }//if
    }//savePatterns

    public List<IPattern> getPatterns() {
        ArrayList<IPattern> patterns = new ArrayList<IPattern>();
        if(initIn())
            try {
                while (true) 
                    patterns.add((IPattern) in.readObject());
            } catch (Exception e) {            
            }
        //in.close();
        return patterns;
    }//getPatterns

    public IPattern getPatternById(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void savePattern(IPattern pattern) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
