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
 * Helper class to encapsulate input parsing, exception handling and invalid
 * check, etc.
 *
 * @author ypchui
 */
class SnackTimeHelper {

    /*
      * Get the user input String from Snack Menu
      * @return an integer of user input: 1, 2, 3, 4, -1 means Cancel/Close
     */
    public static int getChoiceFromSnackMenu(String[] name, double[] price) {
        String choice;
        choice = SnackTime.showSnackMenu(name, price);
        if (choice == null) {
            return -1; // "Cancel" is clicked
        }
        return parseChoices(choice);
    }

    /*
    * Parse the user input String, handle exception and get the user input as an integer
    * @param choice the user input String
    * @return an integer of user input
     */
    private static int parseChoices(String choice) {
        int task;
        try { // exception handling for Invalid Number Format
            task = Integer.parseInt(choice);
        } catch (NumberFormatException e) {
            task = -1;
        }
        return task;
    }
}
