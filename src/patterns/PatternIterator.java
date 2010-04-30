package patterns;

import java.util.Iterator;
import patterns.Image.ImagePatternCollection;

public class PatternIterator implements Iterator<Pattern> {

    private ImagePatternCollection store;

    private int pointer = 0;

    private int kwidth = 50;
    private int kheight = 50;

    public PatternIterator() {
        this.store = null;
    }

    public PatternIterator(ImagePatternCollection store, int pointer) {
        this.store = store;
        this.pointer = pointer;
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
        if((pointer < store.size()) && (pointer < store.size())) {
            store.remove(store.get(pointer));
            if(pointer >= store.size()) pointer = store.size() - 1;
        }
    }

}
