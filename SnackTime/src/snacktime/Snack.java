/**
 * CSCI1130 Assignment 3 SnackTime
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
 * Date        : 23/10/2021
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snacktime;

/**
 *
 * @author user
 */
public class Snack {

    public String name;
    public double price;

    public Snack(String name) {
        this.name = name;
        price = genRandomPrice();
        printMessage();
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    private void printMessage() {
        System.out.printf("%s is sold at $%.2f.\n", name, price);
    }

    static double genRandomPrice() {
        double p = (double) Math.floor(Math.random() * (15 - 5 + 1) + 5);
        int point = (int) Math.floor(Math.random() * (1 - 0 + 1) + 0);
        if (point == 1) {
            p += 0.5;
        }
        return p;
    }
}
