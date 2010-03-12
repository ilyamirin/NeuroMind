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

public class JPEGPatternStore<T extends Pattern> implements List<Pattern> {

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

    public JPEGPatternStore() {
        files = new ArrayList<File>();
    }

    public JPEGPatternStore(String path) {
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

    private void savePattern(Pattern pattern) {
        //TODO: сделать сохранения паттерна в файл
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int size() {
        return files.size();
    }

    public boolean isEmpty() {
        return files.isEmpty();
    }

    public boolean contains(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Iterator<Pattern> iterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object[] toArray() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public <T> T[] toArray(T[] ts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean add(Pattern e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
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
        files.remove(i);
        return pattern;
    }

    public int indexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ListIterator<Pattern> listIterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ListIterator<Pattern> listIterator(int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Pattern> subList(int i, int i1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
