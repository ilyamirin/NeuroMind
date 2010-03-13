package patterns.JPEG;

import java.util.ListIterator;
import patterns.Pattern;

public class JPEGPatternIterator implements ListIterator<Pattern> {

    private JPEGPatternList store;

    private int pointer = 0;

    public JPEGPatternIterator() {
        this.store = null;
    }

    public JPEGPatternIterator(JPEGPatternList store) {
        this.store = store;
    }

    public boolean hasNext() {
        if(pointer < (store.size() - 2)) {
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
        if(pointer >= store.size()) pointer = store.size() - 1;
    }

    public boolean hasPrevious() {
        if(pointer > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Pattern previous() {
        return store.get(pointer - 1);
    }

    public int nextIndex() {
        return (pointer + 1);
    }

    public int previousIndex() {
        return (pointer - 1);
    }

    public void set(Pattern e) {
        store.set(pointer, e);
    }

    public void add(Pattern e) {
        store.add(pointer, e);
    }

}
