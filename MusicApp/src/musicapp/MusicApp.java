/**
 * CSCI1130 Assignment 4 MusicApp
 * Aim: Get acquainted with the JDK + NetBeans programming environment
 *      Learn the structure and format of a Java program by example
 * 
 * Remark: Key in class names, variable names, method names, etc. AS IS
 *         You should type also ALL the comment lines (text in gray)
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
 *   http://www.cuhk.edu.hk/policy/academichonesty
 * Faculty of Engineering Guidelines to Academic Honesty:
 *   http://www.erg.cuhk.edu.hk/erg/AcademicHonesty
 * 
 * Student Name: Cheng Wing Lam
 * Student ID  : 1155125313
 * Date        : 2/11/2021
 */


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musicapp;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class MusicApp {

    /**
     * @param songname
     * @param args the command line arguments
     */
    public static int input=0;
    public static void checkinput(String songname) {
        Scanner songpath;
        songpath = new Scanner(System.in);
        System.out.print("Song file: ");
        songname = songpath.next();

        while (input<3) {
            if (input == 2) {
                System.exit(0);
            }
            if (songname.endsWith(".txt")) {
                readfile(songname);
                break;
            }
            System.out.println("Something wrong!");
            System.out.print("Song file: ");
            songname = songpath.next();
        }
    }

    public static void readfile(String songname) {
        String original = songname;
        System.out.print("Reading song file " + songname);
        String data = "";
        System.out.println(data);
        try {
            File song = new File(songname);
            Scanner readfile;
            readfile = new Scanner(song);
            while (readfile.hasNextLine()) {
                data += readfile.nextLine();
                data += "\n";
            }
            readfile.close();
        } catch (FileNotFoundException e) {
            System.out.println("Something wrong!");
            checkinput(original);
        }
        //System.out.println(data);
        output(data, songname);
    }

    public static void output(String data, String songname) {
        try {
            try (FileWriter output = new FileWriter("melody_" + songname)) {
                String[] part = data.split(" = 4");
                //System.out.println("part.length =" + part.length);
                if (part.length == 2) {
                    output.write(part[0]);
                    System.out.print(part[0]);
                    output.write(" time Signature = 4\n");
                    System.out.println(" time Signature = 4");
                    int s = 4;
                    String substring1 = part[1].substring(1);
                    String[] melody = substring1.split("\n");
                    int mlen = melody.length;
                    //System.out.println("mlen =" + mlen);
                    for (int i = 0; i < mlen; i++) {
                        output.write(melody(melody[i], 4));
                    }

                } else if (part.length <= 1) {
                    String[] spart = data.split("\\*");
                    output.write(spart[0]);
                    System.out.print(spart[0]);
                    int l = spart.length;
                    //System.out.println("Spart.lengthkkkkk =" + spart.length);

                    for (int loop = 1; loop < l; loop++) {
                        //System.out.println("Spart.length =" + spart[loop] + "\n");
                        int time_signature = Character.getNumericValue(spart[loop].charAt(0));
                        String substring2 = spart[loop].substring(1);
                        output.write("#Time Signature = " + time_signature);
                        System.out.println("#Time Signature = " + time_signature);
                        output.write("\n");
                        String[] melody = substring2.split("\n");
                        int mlen = melody.length;
                        //System.out.println("mlen =" + mlen);
                        for (int i = 1; i < mlen; i++) {
                            output.write(melody(melody[i], time_signature));
                        }
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("Cannot output melody file!");
            //Logger.getLogger(MusicApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String melody(String melody, int ts) {
        int len = melody.length();
        String out = "";
        //System.out.println("ts = " + ts);
        int sum = 0;
        for (int i = 0; i < len; i++) {
            //System.out.println(melody);
            //System.out.println("i = " + i);

            if (i % 2 == 0) {
                out += pitch(melody.charAt(i));
                //System.out.println(i);
            } else {
                if (melody.charAt(i) == '1') {
                    out += " ";
                    sum++;
                    //System.out.println("Rsum = " + sum);
                } else if (melody.charAt(i) == '2') {
                    out += "- ";
                    sum += 2;
                    // System.out.println("Rsum = " + sum);
                } else if (melody.charAt(i) == '3') {
                    out += "-- ";
                    sum += 3;
                    //System.out.println("Rsum = " + sum);
                } else if (melody.charAt(i) == '4') {
                    out += "--- ";
                    sum += 4;
                    //System.out.println("Rsum = " + sum);
                } else if (melody.charAt(i) == '5') {
                    out += "---- ";
                    sum += 5;
                    //System.out.println("Rsum = " + sum);
                } else if (melody.charAt(i) == '6') {
                    out += "----- ";
                    sum += 6;
                    //System.out.println("Rsum = " + sum);
                }
                if (sum > 0 && sum % ts == 0) {
                    out += "| ";
                    //System.out.println("sum = " + sum);
                }
            }
        }
        out += "\n";
        System.out.print(out);
        return out;
    }

    public static String pitch(char p) {
        switch (p) {
            case 'd' -> {
                return "Do";
            }
            case 'r' -> {
                return "Re";
            }
            case 'm' -> {
                return "Me";
            }
            case 'f' -> {
                return "Fa";
            }
            case 's' -> {
                return "So";
            }
            case 'l' -> {
                return "La";
            }
            case 't' -> {
                return "Ti";
            }
            case 'o' -> {
                return "Off";
            }
            default -> {
            }
        }
        return "";
    }

    public static void main(String[] args) {
        // TODO code application logic here
        String songname = null;
        checkinput(songname);
    }
}
