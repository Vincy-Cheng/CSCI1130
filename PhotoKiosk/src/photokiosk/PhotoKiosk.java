package photokiosk;

/**
 * CSCI1130 Assignment 5 PhotoKiosk
 * PhotoKiosk
 * Java application employs 2D array and file I/O
 * @author YP Chui
 *
 * I declare that the assignment here submitted is original
 * except for source material explicitly acknowledged,
 * and that the same or closely related material has not been
 * previously submitted for another course.
 * I also acknowledge that I am aware of University policy and
 * regulations on honesty in academic work, and of the disciplinary
 * guidelines and procedures applicable to breaches of such
 * policy and regulations, as contained in the website.
 *
 * University Guideline on Academic Honesty:
 *   http://www.cuhk.edu.hk/policy/academichonesty/
 *
 * Student Name : Cheng Wing Lam
 * Student ID   : 1155125313
 * Class/Section: B
 * Date         : 14/11/2021
 */
public class PhotoKiosk {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    String filename;

    // Write your code here
    PPM imgDefault;
    imgDefault = new PPM();
    imgDefault.showImage();

    PPM imgBlank;
    imgBlank = new PPM("Orange", 40, 30);
    imgBlank.showImage();

    filename = "WRONG_FILENAME.ppm";
    PPM imgFileCorrupted;
    imgFileCorrupted = new PPM(filename);
    imgFileCorrupted.showImage();

    filename = "rgb_30x25.ppm";
    PPM imgFileSmall;
    imgFileSmall = new PPM(filename);
    imgFileSmall.showImage();

    filename = "peacock_256.ppm";
    PPM imgFile2;
    imgFile2 = new PPM(filename);
    imgFile2.showImage();
       
    PPM result = imgFile2.grayscale();
    result.showImage();
    result.write("grayscale.ppm");
    
    PPM result2 = imgFile2.saturate();
    result2.showImage();
    result2.write("saturate.ppm");
    
  }
}
