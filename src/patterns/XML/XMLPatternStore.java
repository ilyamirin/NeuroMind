package patterns.XML;

import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import patterns.IPatternStore;
import patterns.Pattern;

public class XMLPatternStore implements IPatternStore {

    private XStream xstream;
    
    private ObjectOutputStream out;
    private ObjectInputStream in;
    
    private String filename;

    public XMLPatternStore() {
        xstream = new XStream();        
    }

    public XMLPatternStore(String filename) {
        xstream = new XStream();         
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

    public void savePatterns(List<Pattern> patterns) {
        if(initOut()) {
            try {
                for (Iterator<Pattern> it = patterns.iterator(); it.hasNext();) {
                    out.writeObject(it.next());
                    out.flush();
                }
                out.close();
            } catch (IOException iOException) {
            }
        }//if
    }//savePatterns

    public List<Pattern> getPatterns() {
        ArrayList<Pattern> patterns = new ArrayList<Pattern>();
        if(initIn())
            try {
                while (true) 
                    patterns.add((Pattern) in.readObject());
            } catch (Exception e) {            
            }
        //in.close();
        return patterns;
    }//getPatterns

    public Pattern getPatternById(int id) {
        if(initIn())
            try {
                Pattern pattern;
                while (true) {
                    pattern = (Pattern) in.readObject();
                    if(pattern.getId() == id) return pattern;
                }
            } catch (Exception e) {
                return null;
            }
        return null;
    }

    public void savePattern(Pattern pattern) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getCount() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Pattern getPattern(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Pattern removePattern(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
