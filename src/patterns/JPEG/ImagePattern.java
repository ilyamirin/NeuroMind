package patterns.JPEG;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import patterns.Pattern;

//TODO: инкапсулировать в этот класс сегментацию
//TODO: посоветоваться с димоном по поводу механизма наследования
//TODO: заменить массив pixels на super.inputs
public class ImagePattern extends Pattern {

    private int w, h;   

    public ImagePattern() {
        this.w = 0;
        this.h = 0;
    }

    public ImagePattern(File file) throws IOException {
        load(file);
    }//constructor
    
    private void getPixelsFromImg(BufferedImage bimg) throws IOException {
        this.w = bimg.getWidth();
        this.h = bimg.getHeight();
        super.setInputs(new double[w * h]);
        for(int i = 0; i < w; i++)
            for(int j = 0; j < h; j++)
                super.getInputs()[i + w * j] = bimg.getRGB(i, j);
    }//getPixelsFromJPEG

    public void load(File file) throws IOException {
        //loading id from filname
        String fileName = file.getName();
        java.util.regex.Pattern regex =
                java.util.regex.Pattern.compile("^\\d+");
        Matcher matcher = regex.matcher(fileName);
        if(matcher.find()) 
            try {
                super.setId(Long.parseLong(matcher.group()));
            } catch (NumberFormatException numberFormatException) {
                throw new IOException("Pattern or dir name is incorrect.");
            }//try catch

        //loading outputs form parent directory name
        File parent = file.getParentFile();
        if(!parent.isDirectory())
            throw new IOException(parent.getAbsolutePath()+" is not a directory!");
        fileName = parent.getName();
        super.setOutputs(new double[fileName.length()]);
        for(int i = 0; i < fileName.length(); i++)
            super.setOutput(Integer.parseInt(fileName.substring(i, i+1)), i);

        //loading pixels from file
        FileInputStream in = new FileInputStream(file);
        JPEGImageDecoder jdecoder = JPEGCodec.createJPEGDecoder(in);
        BufferedImage bimg = jdecoder.decodeAsBufferedImage();

        getPixelsFromImg(bimg);
    }//saveToFile

    private BufferedImage putPixelsToImg() {
        BufferedImage bimg = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
        for(int i = 0; i < w; i++)
            for(int j = 0; j < h; j++)
                bimg.setRGB(i, j, (int) super.getInputs()[i + w * j]);
        return bimg;
    }//putPixelsToImg

    public void save(File file) throws IOException {
        BufferedImage bimg = putPixelsToImg();

        FileOutputStream fout = new FileOutputStream(file);

        JPEGImageEncoder jencoder = JPEGCodec.createJPEGEncoder(fout);
        JPEGEncodeParam enParam = jencoder.getDefaultJPEGEncodeParam(bimg);

        enParam.setQuality(1.0F, true);
        jencoder.setJPEGEncodeParam(enParam);
        jencoder.encode(bimg);

        fout.close();
    }//savePixelsToJPEG

    public int getPixel(int x, int y) {
        return (int) super.getInputs()[x + y * x];
    }//getPixel

    public int getH() {
        return h;
    } 

    public int getW() {
        return w;
    }

    //-------------------------------------------
/*
    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public double[] getInputs() {
        return super.getInputs();
    }

    @Override
    public double[] getOutputs() {
        return super.getOutputs();
    }
*/

}
