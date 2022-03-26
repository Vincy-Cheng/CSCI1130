package photokiosk;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Portable Pixel Map (RGB Color image file in ASCII text) Java application
 * employs 2D array and file I/O
 *
 * @author Michael FUNG, YPChui
 *
 * I declare that the assignment here submitted is original except for source
 * material explicitly acknowledged, and that the same or closely related
 * material has not been previously submitted for another course. I also
 * acknowledge that I am aware of University policy and regulations on honesty
 * in academic work, and of the disciplinary guidelines and procedures
 * applicable to breaches of such policy and regulations, as contained in the
 * website.
 *
 * University Guideline on Academic Honesty:
 * http://www.cuhk.edu.hk/policy/academichonesty/
 *
 * Student Name : Cheng Wing Lam 
 * Student ID : 1155125313 
 * Class/Section: B 
 * Date :14/11/2021
 */
public class PPM {
    // instance fields

    private String imageName;
    private int width, height;
    private int maxValue;
    private Color[][] image;

    // Default constructor for creating an blank PPM image of 2 x 3
    // provided for reference, NEED NOT be modified
    public PPM() {
        imageName = "Simple";
        width = 2;
        height = 3;
        maxValue = 255;
        image = new Color[height][width];
        image[0][0] = new Color(0, 128, 255);

        int r = image[0][0].getRed();
        int g = image[0][0].getGreen();
        int b = image[0][0].getBlue();

        image[0][1] = new Color(r, g + 127, b - 128);
        image[1][0] = new Color(128, g, 128);
        image[1][1] = new Color(255, 0, 255);
        image[2][0] = new Color(255, 255, 255);
        image[2][1] = new Color(0, 0, 0);
    }

    // Constructor for creating an "orange" PPM image of width x height
    // All pixels shall carry Color(255, 165, 0) in RGB
    public PPM(String name, int w, int h) {
        // fill in code here  
        imageName = name;
        width = w;
        height = h;
        maxValue = 255;
        image = new Color[height][width];
        //	orange's RGB (255,165,0)
        try {   // turn this to all orange
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    image[i][j] = new Color(255, 165, 0);
                }
            }
        } catch (Exception e) {
            //catch error
        }
    }
    // Constructor for reading a PPM image file

    public PPM(String filename) {
        this.imageName = filename;
        read(filename);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Color[][] getImage() {
        return image;
    }

    // Show image on screen
    // given and NEED NOT be modified
    public void showImage() {
        if (getHeight() <= 0 || getWidth() <= 0 || image == null) {
            JOptionPane.showConfirmDialog(null, "Width x Height = " + getWidth() + "x" + getHeight(), imageName + " corrupted!", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE, null);
            return;
        }

        BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(); col++) {
                img.setRGB(col, row, image[row][col].getRGB());
            }
        }

        JOptionPane.showConfirmDialog(null, "Width x Height = " + getWidth() + "x" + getHeight(), imageName, JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon(img));
    }

    public int checkrange(int i) {
        int c = i;
        if (c > maxValue) {
            c = maxValue;
        } else if (c < 0) {
            c = 0;
        }
        return c;
    }

    /* student's work here to define extra methods to handle color space conversion, 
     grayscale and saturate */
    public PPM grayscale() {
        try {
            PPM gray = new PPM();// create a new image
            gray.imageName = "grayscale.ppm";
            gray.width = width;
            gray.height = height;
            gray.maxValue = maxValue;
            gray.image = new Color[height][width];
            int r, g, b, y, u, v;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    r = image[i][j].getRed();
                    g = image[i][j].getGreen();
                    b = image[i][j].getBlue();

                    y = (int) (0.257 * r + 0.504 * g + 0.098 * b + 16);
                    u = (int) (-0.148 * r - 0.291 * g + 0.439 * b + 128);
                    v = (int) (0.439 * r - 0.368 * g - 0.071 * b + 128);
                    y = checkrange(y);
                    //make r,g,b all equal to y value(luminance component)
                    gray.image[i][j] = new Color(y, y, y);
                }
            }
            gray.write("grayscale.ppm");
            return gray;
        } catch (Exception e) {
            return null; // error
        }
    }

    public PPM saturate() {
        try {
            PPM sat = new PPM();// create a new image
            sat.imageName = "saturate.ppm";
            sat.width = width;
            sat.height = height;
            sat.maxValue = maxValue;
            sat.image = new Color[height][width];
            int r, g, b, y, u, v;
            int s = 2;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {

                    r = image[i][j].getRed();
                    g = image[i][j].getGreen();
                    b = image[i][j].getBlue();

                    y = (int) (0.257 * r + 0.504 * g + 0.098 * b + 16);
                    u = (int) (-0.148 * r - 0.291 * g + 0.439 * b + 128);
                    v = (int) (0.439 * r - 0.368 * g - 0.071 * b + 128);

                    u = (u - 128) * s + 128;
                    v = (v - 128) * s + 128;

                    r = (int) (1.164 * (y - 16) + 1.596 * (v - 128));
                    g = (int) (1.164 * (y - 16) - 0.813 * (v - 128) - 0.391 * (u - 128));
                    b = (int) (1.164 * (y - 16) + 2.018 * (u - 128));

                    r = checkrange(r);
                    g = checkrange(g);
                    b = checkrange(b);
                    sat.image[i][j] = new Color(r, g, b);
                }
            }
            sat.write("saturate.ppm");
            return sat;
        } catch (Exception e) {
            return null; // error
        }

    }

    public boolean read(String filename) {
        try {
            File f = new File(filename);
            Scanner reader = new Scanner(f);
            String header = reader.nextLine();
            if (header == null || header.length() < 2 || header.charAt(0) != 'P' || header.charAt(1) != '3') {
                throw new Exception("Wrong PPM header!");
            }

            do { // skip lines (if any) start with '#'
                header = reader.nextLine();
            } while (header.charAt(0) == '#');

            Scanner readStr = new Scanner(header);  // get w, h from last line scanned instead of file
            width = readStr.nextInt();
            height = readStr.nextInt();
//      width = reader.nextInt();
//      height = reader.nextInt();
            maxValue = reader.nextInt();  // get the rest from file      

            System.out.println("Reading PPM image of size " + width + "x" + height);
            // Write your code here
            int r, g, b;
            image = new Color[height][width];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    r = reader.nextInt(); // read the R
                    g = reader.nextInt(); // read the G
                    b = reader.nextInt(); // read the B
                    image[i][j] = new Color(r, g, b); // save color
                }
            }
        } catch (Exception e) {
            System.err.println(e);
            image = null;
            width = -1;
            height = -1;
            return false;
        }
        return true;
    }

    public void write(String filename) {
        PrintStream ps;
        try {
            ps = new PrintStream(filename);
            // fill in code here  
            ps.println("P3\n" + width + " " + height + "\n" + maxValue);
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    ps.printf("%d ", image[i][j].getRed());
                    ps.printf("%d ", image[i][j].getGreen());
                    ps.printf("%d ", image[i][j].getBlue());
                }
                ps.println();
            }

        } catch (Exception e) {
            System.err.println(e);
        }
    }

}
