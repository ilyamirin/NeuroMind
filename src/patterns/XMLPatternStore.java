package patterns;

import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class XMLPatternStore implements IPatternStore {

    private XStream xstream;
    private ObjectOutputStream out;

    private String filename = "C:\\temp\\patterns.xml";

    private boolean init() {
        xstream = new XStream();
        try {
            FileOutputStream stream = new FileOutputStream(new File(filename));
            out = xstream.createObjectOutputStream(stream);
            return true;
        } catch (IOException iOException) {
            return false;
        }//try catch
    }//constructor

    public void savePatterns(IGetPatternObject gpo, int times) {
        if(init()) {
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
    }

    public Pattern getPatternById(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
