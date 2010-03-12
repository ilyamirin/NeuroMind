package patterns.JPEG;

import java.util.Iterator;
import patterns.Pattern;

public class JPEGPatternIterator implements Iterator<Pattern> {

    private JPEGPatternList store;

    private int pointer = 0;

    public JPEGPatternIterator() {
        this.store = null;
    }

    public JPEGPatternIterator(JPEGPatternList store) {
        this.store = store;
    }

    public boolean hasNext() {
        if(pointer < (store.size() - 1)) {
            return true;
        } else {
            return false;
        }
    }

    public Pattern next() {
        Pattern pattern = store.get(pointer);
        pointer++;
        return pattern;
    }

    public void remove() {
        Pattern pattern = store.remove(pointer);
    }

}
