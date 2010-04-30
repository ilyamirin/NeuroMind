package convolutioner;

import java.util.Iterator;
import patterns.Pattern;

public class ColvolutionPatternIterator implements Iterator<Pattern> {

    private Image store;

    private int pointer = 0;

    private int kwidth = 50;
    private int kheight = 50;

    public ColvolutionPatternIterator() {
        this.store = null;
    }

    public ColvolutionPatternIterator(Image store) {
        this.store = store;
    }

    public boolean hasNext() {
        return pointer < (store.size() - 2) ? true : false ;
    }

    public Pattern next() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
