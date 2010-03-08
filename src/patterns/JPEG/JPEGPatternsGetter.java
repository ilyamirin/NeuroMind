package patterns.JPEG;

import patterns.Pattern;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import patterns.IGetPatternObject;

public class JPEGPatternsGetter implements IGetPatternObject {

    private ArrayList<File> jpegFiles;

    public static double[] intToDouble(int[] in) {
        double[] result = new double[in.length];
        for (int i = 0; i < result.length; i++)
            result[i] = in[i];
        return result;
    }

    public static int[] getOutputsFromFilename(File file) throws IOException {
        if(!file.isDirectory())
            throw new IOException(file.getAbsolutePath()+" is not a directory!");
        String filename = file.getName();
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

    public JPEGPatternsGetter() {
        jpegFiles = new ArrayList<File>();
    }

    public JPEGPatternsGetter(String path) {
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
            System.out.println(jpegFiles.size()+" patterns in.");
        }//if
    }//GetJPEGPatterns

    public int getMaxPatterns() {
        return jpegFiles.size();
    }

    public Pattern getPattern() {
        if (jpegFiles.size() == 0) return null;

        try {            
            File file = jpegFiles.remove((new Random()).nextInt(jpegFiles.size()));

            Pattern pattern = new Pattern();
            pattern.setOutputs(intToDouble(getOutputsFromFilename(file)));
            pattern.setInputs(intToDouble(getPixelsFromJPEG(file)));

            return pattern;
        } catch (IOException iOException) {
            return null;
        }//try catch
    }//getPattern

}
