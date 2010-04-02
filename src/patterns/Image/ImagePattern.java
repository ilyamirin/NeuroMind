package patterns.Image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import patterns.Pattern;
import javax.imageio.ImageIO;

public class ImagePattern extends Pattern {

    private File file;

    //TODO: удалить лишнюю ссылку???
    private BufferedImage bimg;

    private int width;
    private int height;

    private int[] grubRGB(int rgb) {
        int[] res = new int[3];
        res[0] = (rgb & 0x00ff0000) >> 16;
        res[1] =  (rgb & 0x0000ff00) >> 8;
        res[2] =  (rgb & 0x000000ff);
        return res;
    }

    private void pushInputs() {
        super.setInputs(new double[width * height * 3]);
        for(int i = 0; i < width; i++) 
            for(int j = 0; j < height; j++) {
                int[] rgb = grubRGB(bimg.getRGB(i, j));
                for(int k = 0; k < 3; k++) 
                    super.setInput(rgb[k], i + j * width + k);
            }//for
    }//pushRGB

    private void pushOutputs() throws IOException {
        File parent = file.getParentFile();
        if(!parent.isDirectory())
            throw new IOException(parent.getAbsolutePath()+" is not a directory!");
        String filename = parent.getName();
        super.setOutputs(new double[filename.length()]);
        try {
            for (int i = 0; i < filename.length(); i++)
                super.setOutput(Integer.parseInt(filename.substring(i, i + 1)), i);
        } catch (NumberFormatException numberFormatException) {
            throw numberFormatException;
        }//try catch
    }//pushOutputs

    public static Long grubIdFromFile(File file) {        
        String fileName = file.getName();
        java.util.regex.Pattern regex =
                java.util.regex.Pattern.compile("^\\d+");
        Matcher matcher = regex.matcher(fileName);
        return matcher.find() ? Long.parseLong(matcher.group()) : null;
    }

    private void pushId() {
        super.setId(ImagePattern.grubIdFromFile(file));
    }

    public ImagePattern(File file) {
        try {
            bimg = ImageIO.read(file);
            this.file = file;
            this.width = bimg.getWidth();
            this.height = bimg.getHeight();
            pushId();
            pushInputs();
            pushOutputs();            
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }//constructor

    @Override
    public double[] getInputs() {
        return super.getInputs();
    }//getInputs

    public double[] getInputs(int x, int y, int w, int h) {
        double[] res = new double[w * h * 3];
        w = x + w;
        h = y + h;
        for(int i = x; i < w; i++)
            for(int j = y; j < h; j++)
                for(int rgb = 0; rgb < 3; rgb++)
                    super.getInput(i + j * width + rgb);
        return res;
    }//getInputs

    @Override
    public double[] getOutputs() {
        return super.getOutputs();
    }//getInputs


    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

}
