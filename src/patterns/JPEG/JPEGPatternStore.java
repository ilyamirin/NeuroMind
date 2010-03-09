package patterns.JPEG;

import java.util.List;
import patterns.Pattern;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import patterns.IPatternStore;

public class JPEGPatternStore implements IPatternStore {

    private ArrayList<File> jpegFiles;

    public static double[] intToDouble(int[] in) {
        double[] result = new double[in.length];
        for (int i = 0; i < result.length; i++)
            result[i] = in[i];
        return result;
    }

    public static int[] getOutputsFromFile(File file) throws IOException {
        File parent = file.getParentFile();
        if(!parent.isDirectory())
            throw new IOException(parent.getAbsolutePath()+" is not a directory!");
        String filename = parent.getName();
        int[] result = new int[filename.length()];
        for(int i = 0; i < filename.length(); i++)
            result[i] = Integer.parseInt(filename.substring(i, i+1));
        return result;
    }//getOutputsFromFilename

    public static int[] getPixelsFromJPEG(File file)
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
        jpegFiles = new ArrayList<File>();
    }

    public JPEGPatternStore(String path) {
        File patternDirectory = new File(path);
        if(patternDirectory.exists()) {
            File[] patternDirs =
                    patternDirectory.listFiles(new JPEGPatternDirFilter());
            jpegFiles = new ArrayList<File>();
            for (int i = 0; i < patternDirs.length; i++) {
                File[] jPats = patternDirs[i].listFiles(new JPEGPatternFilter());
                for (int j = 0; j < jPats.length; j++)
                    jpegFiles.add(jPats[j]);
            }//for 1
            Collections.shuffle(jpegFiles);
        }//if
    }//GetJPEGPatterns

    public int getMaxPatternsNum() {
        return jpegFiles.size();
    }

    public Pattern getPattern(int id) {
        if (jpegFiles.size() == 0) return null;

        try {            
            File file = jpegFiles.get(id);

            Pattern pattern = new Pattern();

            //System.out.println(file.getName());
            
            pattern.setOutputs(intToDouble(getOutputsFromFile(file)));
            pattern.setInputs(intToDouble(getPixelsFromJPEG(file)));

            return pattern;
            
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }//try catch
    }//getPattern

    public void savePattern(Pattern pattern) {
        //TODO: сделать сохранения паттерна в файл
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void savePatterns(List<Pattern> patterns) {
        for (Iterator<Pattern> it = patterns.iterator(); it.hasNext();)
            savePattern(it.next());
    }

    public List<Pattern> getPatterns() {
        List<Pattern> result = new ArrayList<Pattern>();
        for (int i = 0; i < jpegFiles.size(); i++)
             result.add(getPattern(i));
        return result;
    }

    public Pattern removePattern(int id) {
        Pattern pattern = getPattern(id);
        jpegFiles.remove(id);
        return pattern;
    }

}
