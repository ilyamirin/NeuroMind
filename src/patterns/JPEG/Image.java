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

//TODO: подумать о том, чтобы сделать этот класс колллекцией
//TODO: инкапсулировать в этот класс сегментацию
public class Image {

    private int w, h;
    
    private Integer[][] pixels;

    public Image() {
        this.w = 0;
        this.h = 0;
    }

    public Image(String filename) {
        try {
            loadFromFile(filename);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }//constructor
    
    private void getPixelsFromImg(BufferedImage bimg) throws IOException {
        this.w = bimg.getWidth();
        this.h = bimg.getHeight();        
        Integer[][] result = new Integer[w][h];
        for(int i = 0; i < w; i++)
            for(int j = 0; j < h; j++)
                pixels[i][j] = bimg.getRGB(i, j);
    }//getPixelsFromJPEG

    public void loadFromFile(String filename) throws IOException {
        FileInputStream in = new FileInputStream(new File(filename));
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

    public void savePixelsToJPEG(String filemane) throws IOException {
        BufferedImage bimg = putPixelsToImg();

        FileOutputStream fout = new FileOutputStream(new File(filemane));

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


}
