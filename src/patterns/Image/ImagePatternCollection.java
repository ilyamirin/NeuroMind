package patterns.Image;

import patterns.PatternIterator;
import java.util.Collection;
import java.util.Iterator;
import patterns.Pattern;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class ImagePatternCollection implements Collection<Pattern> {

    private String root;

    private boolean removeFromFilesystem = false;

    private ArrayList<File> files;  

    public ImagePatternCollection() {
        files = new ArrayList<File>();
    }

    public ImagePatternCollection(String path) {
        File patternDirectory = new File(path);
        if(patternDirectory.exists()) {
            this.root = path;
            File[] patternDirs =
                    patternDirectory.listFiles(new ImagePatternDirFilter());
            files = new ArrayList<File>();
            for (int i = 0; i < patternDirs.length; i++) {
                File[] jPats = patternDirs[i].listFiles(new ImagePatternFilter());
                for (int j = 0; j < jPats.length; j++)
                    files.add(jPats[j]);
            }//for 1
            Collections.shuffle(files);
        }//if
    }//GetJPEGPatterns

    public int size() {
        return files.size();
    }

    public boolean isEmpty() {
        return files.isEmpty();
    }

    public boolean contains(Object o) {
        if(o.getClass() != Pattern.class) return false;
        Pattern pattern = (Pattern) o;
        for(int i = 0; i < size(); i++)
            if(ImagePattern.grubIdFromFile(files.get(i)).equals(pattern.getId()))
                if(get(i).equals(pattern)) return true;            
        return false;
    }//contains
    
    public Iterator<Pattern> iterator() {
        return new PatternIterator(this, 0);
    }
    
    public Object[] toArray() {
        Pattern[] result = new Pattern[size()];
        for (int i = 0; i < result.length; i++)
            result[i] = get(i);
        return result;
    }

    //not tested
    public <T> T[] toArray(T[] ts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    //not tested
    public boolean add(Pattern e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    //TODO: подумать о выносе поиска паттерна в отдельную функцию
    public boolean remove(Object o) {
        //TODO: оптимизировать
        if(o.getClass() != Pattern.class) return false;
        Pattern pattern = (Pattern) o;
        if(!contains(pattern)) {
            return false;
        } else {
            for(int i = 0; i < size(); i++)
                if(ImagePattern.grubIdFromFile(files.get(i)).equals(pattern.getId())) {
                    if(removeFromFilesystem) files.get(i).delete();
                    files.remove(i);
                }
            return true;
        }
    }
    
    public boolean containsAll(Collection<?> clctn) {
        if(clctn.equals(this)) return true;
        if(clctn.size() < 1) return true;        
        boolean result = true;
        for (Iterator it = clctn.iterator(); it.hasNext();)             
            result |= contains((Pattern) it.next());        
        return result;
    }

    //not tested
    public boolean addAll(Collection<? extends Pattern> clctn) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    //not tested
    public boolean removeAll(Collection<?> clctn) {
        //TODO: оптимизировать
        try {
            for (Iterator<? extends Pattern> it =
                    (Iterator<? extends Pattern>) clctn.iterator(); it.hasNext();)
                remove(it.next());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //not tested
    public boolean retainAll(Collection<?> clctn) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    //not tested
    public void clear() {
        //TODO: дописать
        files.clear();
    }
    
    public Pattern get(int i) {
        if (files.size() == 0) return null;
        return new ImagePattern(files.get(i));
    }


}
