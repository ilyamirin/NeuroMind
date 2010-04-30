package convolutioner;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;

public class Image {

    private int depth = 24;    

    private BufferedImage bimg;

    private boolean[][] points;

    public void loadImage(String fileName) throws IOException {
        bimg = ImageIO.read(new File(fileName));
    }//saveToFile

    public void saveImage(String fileName) throws IOException {
        ImageIO.write(bimg, "jpg", new File(fileName));
    }//savePixelsToJPEG

    public void paint() {
        int d = 20;
        Graphics2D graphics = bimg.createGraphics();        
        for(int i = 0; i < points.length; i++)
            for(int j = 0; j < points[0].length; j++)
                if(points[i][j] == true)
                    graphics.drawOval(i-(d/2), j-(d/2), d, d);
    }//paint

    public int getRGBDifference(int x1, int y1, int x2, int y2) {
        int rgb1 = bimg.getRGB(x1, y1);
        int rgb2 = bimg.getRGB(x2, y2);

        int red1 = (rgb1 & 0x00ff0000) >> 16;
        int green1 = (rgb1 & 0x0000ff00) >> 8;
        int blue1 = (rgb1 & 0x000000ff);

        int red2 = (rgb2 & 0x00ff0000) >> 16;
        int green2 = (rgb2 & 0x0000ff00) >> 8;
        int blue2 = (rgb2 & 0x000000ff);

        return (int) Math.sqrt( (red1 - red2)*(red1 - red2) + (green1 - green2)
                * (green1 - green2) + (blue1 - blue2) * (blue1 - blue2) );
    }//getDifference

    public int calcDepth(int x, int y) {
        int diff = 0;
        for(int i = -1; i <= 1; i++)
            for(int j = -1; j <= 1; j++)
                diff += getRGBDifference(x, y, x+i, y+j);
        diff /= 8;
        return diff;
    }

    public void getInputs() {
        Kernel kernel = new Kernel(3, 3, new float[] { 1f / 9f, 1f / 9f, 1f / 9f,
            1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f });
        BufferedImageOp op = new ConvolveOp(kernel);
        bimg = op.filter(bimg, null);        

        //TODO: проверить значение декремента
        int w = bimg.getWidth()-4;
        int h = bimg.getHeight()-4;
        points = new boolean[w][h];

        int size = 0;
        for(int i = 2; i < w; i++)
            for(int j = 2; j < h; j++) 
                if(calcDepth(i, j) > depth) {
                    points[i][j] = true;
                    size++;
                }            

        System.out.println("найдено " + size + " точек");
        
    }//seekPoints()
    

}
