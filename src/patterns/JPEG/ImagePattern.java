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
    
    private Integer[][] pixels;

    public ImagePattern() {
        this.w = 0;
        this.h = 0;
    }

    public ImagePattern(File file) {
        try {
            load(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }//constructor
    
    private void getPixelsFromImg(BufferedImage bimg) throws IOException {
        this.w = bimg.getWidth();
        this.h = bimg.getHeight();                
        for(int i = 0; i < w; i++)
            for(int j = 0; j < h; j++)
                pixels[i][j] = bimg.getRGB(i, j);
    }//getPixelsFromJPEG

    public void load(File file) throws IOException {
        //loading id from filname
        String fileName = file.getName();
        java.util.regex.Pattern regex =
                java.util.regex.Pattern.compile("^\\d+");
        Matcher matcher = regex.matcher(fileName);
        if(matcher.find()) {
            super.setId(Long.parseLong(matcher.group()));
        } else {
            throw new IOException("Pattern name " +fileName+ " is incorrect.");
        }

        //loading outputs form parent directory name
        File parent = file.getParentFile();
        if(!parent.isDirectory())
            throw new IOException(parent.getAbsolutePath()+" is not a directory!");
        fileName = parent.getName();        
        for(int i = 0; i < fileName.length(); i++)
            super.setOutput(Integer.parseInt(fileName.substring(i, i+1)), i);

        //loading pixels from file
        FileInputStream in = new FileInputStream(file);
        JPEGImageDecoder jdecoder = JPEGCodec.createJPEGDecoder(in);
        BufferedImage bimg = jdecoder.decodeAsBufferedImage();

        getPixelsFromImg(bimg);
    }//saveToFile

    private BufferedImage putPixelsToImg() {
        BufferedImage img = new BufferedImage(pixels.length, pixels[0].length,
                BufferedImage.TYPE_INT_RGB);
        for(int i = 0; i < pixels.length; i++)
            for(int j = 0; j < pixels[0].length; j++)
                img.setRGB(i, j, pixels[i][j]);
        return img;
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
        return pixels[x][y];
    }//getPixel

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public Integer[][] getPixels() {
        return pixels;
    }

    public void setPixels(Integer[][] pixels) {
        this.pixels = pixels;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    //-------------------------------------------

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


}
