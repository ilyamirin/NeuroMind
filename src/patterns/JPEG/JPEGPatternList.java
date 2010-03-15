package patterns.JPEG;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import patterns.Pattern;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;

public class JPEGPatternList implements List<Pattern> {

    private ArrayList<File> files;

    //TODO: вынести в либу следующте пять методов

    private static Long getIdFromFile(File file) {
        String fileName = file.getName();
        java.util.regex.Pattern regex =
                java.util.regex.Pattern.compile("^\\d+");
        Matcher matcher = regex.matcher(fileName);
        if(matcher.find()) return Long.parseLong(matcher.group());
        return null;
    }

    private static double[] intToDouble(int[] in) {
        double[] result = new double[in.length];
        for (int i = 0; i < result.length; i++)
            result[i] = in[i];
        return result;
    }

    private static int[] getOutputsFromFile(File file) throws IOException {
        File parent = file.getParentFile();
        if(!parent.isDirectory())
            throw new IOException(parent.getAbsolutePath()+" is not a directory!");
        String filename = parent.getName();
        int[] result = new int[filename.length()];
        for(int i = 0; i < filename.length(); i++)
            result[i] = Integer.parseInt(filename.substring(i, i+1));
        return result;
    }//getOutputsFromFilename

   private static int[] getPixelsFromJPEG(File file)
            throws IOException {
        FileInputStream out = new FileInputStream(file);

        JPEGImageDecoder jdecoder = JPEGCodec.createJPEGDecoder(out);

        BufferedImage img = jdecoder.decodeAsBufferedImage();
        out.close();

        int[] result = new int[img.getWidth() * img.getHeight()];
        for(int i = 0; i < img.getHeight(); i++)
            for(int j = 0; j < img.getWidth(); j++)
                result[i * img.getWidth() + j] = img.getRGB(j, i);

        return result;
    }//getPixelsFromJPEG

    public JPEGPatternList() {
        files = new ArrayList<File>();
    }

    public JPEGPatternList(String path) {
        File patternDirectory = new File(path);
        if(patternDirectory.exists()) {
            File[] patternDirs =
                    patternDirectory.listFiles(new JPEGPatternDirFilter());
            files = new ArrayList<File>();
            for (int i = 0; i < patternDirs.length; i++) {
                File[] jPats = patternDirs[i].listFiles(new JPEGPatternFilter());
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
            if(getIdFromFile(files.get(i)).equals(pattern.getId()))
                if(get(i).equals(pattern)) return true;
        //for(int i = 0; i < size(); i++)
            
        return false;
    }

    //test pointer
    public Iterator<Pattern> iterator() {
        return new JPEGPatternIterator(this, 0);
    }

    public Object[] toArray() {
        Pattern[] result = new Pattern[size()];
        for (int i = 0; i < result.length; i++)
            result[i] = get(i);
        return result;
    }

    public <T> T[] toArray(T[] ts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean add(Pattern e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean remove(Object o) {
        Pattern pattern = (Pattern) o;
        if(!contains(pattern)) {
            return false;
        } else {
            remove(indexOf(pattern));
            return true;
        }
    }

    public boolean containsAll(Collection<?> clctn) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean addAll(Collection<? extends Pattern> clctn) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean addAll(int i, Collection<? extends Pattern> clctn) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean removeAll(Collection<?> clctn) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean retainAll(Collection<?> clctn) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void clear() {
        files.clear();
    }

    public Pattern get(int i) {
        if (files.size() == 0) return null;

        try {
            File file = files.get(i);

            Pattern pattern = new Pattern();

            pattern.setId(getIdFromFile(file));
            pattern.setOutputs(intToDouble(getOutputsFromFile(file)));
            pattern.setInputs(intToDouble(getPixelsFromJPEG(file)));

            return pattern;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }//try catch
    }

    public Pattern set(int i, Pattern e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void add(int i, Pattern e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Pattern remove(int i) {
        Pattern pattern = get(i);
        //TODO: написать удаление файла из ФС, возможно с флагом
        files.remove(i);
        return pattern;
    }

    public int indexOf(Object o) {
        Pattern pattern = (Pattern) o;
        for (int i = 0; i < size(); i++)
            if(get(i).equals(pattern)) return i;        
        return -1;
    }

    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ListIterator<Pattern> listIterator() {
        return new JPEGPatternIterator(this, 0);
    }

    public ListIterator<Pattern> listIterator(int i) {
        return new JPEGPatternIterator(this, i);
    }

    public List<Pattern> subList(int i, int i1) {
        if((i >= size()) && (i < 0)) return null;
        if((i1 >= size()) && (i1 < 0)) return null;
        List<Pattern> result = new ArrayList<Pattern>();
        for(int k = i; k < i1; k++)
            result.add(get(k));
        return result;
    }

}
