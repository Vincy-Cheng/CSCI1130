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

import javax.swing.JOptionPane;

/**
 *
 * @author user
 */
public class SnackTime {

    /**
     * @param args the command line arguments
     */
    private static int[] coinsInCents;
    private static String[] snackNames;
    public static int len;
    public static int remained = 2000;

    /**
     * * student's work to add extra fields here as needed **
     */
    /**
     * Show a menu of choices and get user's input, given method
     *
     * @return a String of user input: "1","2",…,"5", null means Cancel/Close
     */
    public SnackTime() {

    }

    public static String showSnackMenu(String[] name, double[] price) {
        /**
         * * student's work to display snack menu items **
         */
        return JOptionPane.showInputDialog("Buy Snack Input your choice\n" + "1. " + String.format("[%.2f] ", price[0]) + name[0] + "\n2. " + String.format("[%.2f] ", price[1]) + name[1] + "\n3. " + String.format("[%.2f] ", price[2]) + name[2] + "\n4. " + String.format("[%.2f] ", price[3]) + name[3] + "\n5. Cancel", "<type[1-5]here>");
    }

    public static int showConfirmMsg(String[] name, int snackMenuChoice) {
        return JOptionPane.showConfirmDialog(null, "Insert $" + String.format("%d ", remained / 100) + " to buy " + name[snackMenuChoice - 1], "Confirm", JOptionPane.YES_NO_OPTION);
    }

    @SuppressWarnings("empty-statement")
    public static void paymentleft(int snackMenuChoice, double[] snackprice, int[] coinsInCents) {
        remained -= (int) (snackprice[snackMenuChoice - 1] * 100);
        int money = remained;
        String msg;
        if (remained > 0) {
            msg = "$" + String.format("[%.2f] ", snackprice[snackMenuChoice - 1]) + "paid\n";
            msg += "Coins returned";
        } else {
            msg = "Not enough money.";
        }
        System.out.println(remained);
        int[] count = new int[]{0, 0, 0, 0, 0};;
        while (remained > 0) {
            if (remained >= 1000) {
                count[0] += 1;
                remained -= 1000;
            } else if (remained >= 500) {
                count[1] += 1;
                remained -= 500;
            } else if (remained >= 200) {
                count[2] += 1;
                remained -= 200;
            } else if (remained >= 100) {
                count[3] += 1;
                remained = remained - 100;
            } else if (remained >= 50) {
                count[4] += 1;
                remained -= 50;
            }
        }

        if (count[0] > 0) {
            msg = msg + "\n$10 x " + count[0];
        }
        if (count[1] > 0) {
            msg = msg + "\n$5 x " + count[1];
        }
        if (count[2] > 0) {
            msg = msg + "\n$2 x " + count[2];
        }
        if (count[3] > 0) {
            msg = msg + "\n$1 x " + count[3];
        }
        if (count[4] > 0) {
            msg = msg + "\n$0.5 x " + count[4];
        }
        JOptionPane.showMessageDialog(null, msg);
        remained = money;
        snackMenuChoice = SnackTimeHelper.getChoiceFromSnackMenu(snackNames, snackprice);
        inputcheck(snackMenuChoice, snackprice, coinsInCents);
    }

    public static void inputcheck(int snackMenuChoice, double[] snackprice, int[] coinsInCents) {
        while (true) {
            if (snackMenuChoice == 5) {
                System.out.println("User closed or cancelled dialog box");
                break;
            } else if (snackMenuChoice == 1) {
                System.out.println("User picked 1");
                break;
            } else if (snackMenuChoice == 2) {
                System.out.println("User picked 2");
                break;
            } else if (snackMenuChoice == 3) {
                System.out.println("User picked 3");
                break;
            } else if (snackMenuChoice == 4) {
                System.out.println("User picked 4");
                break;
            } else if (snackMenuChoice == -1) {
                JOptionPane.showMessageDialog(null, "Hope to serve you again");
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Input");
                snackMenuChoice = SnackTimeHelper.getChoiceFromSnackMenu(snackNames, snackprice);
            }
        }
        if (snackMenuChoice == 1 || snackMenuChoice == 2 || snackMenuChoice == 3 || snackMenuChoice == 4) {
            int confirm = showConfirmMsg(snackNames, snackMenuChoice);
            if (confirm == JOptionPane.YES_OPTION) {
                paymentleft(snackMenuChoice, snackprice, coinsInCents);
            } else {
                snackMenuChoice = SnackTimeHelper.getChoiceFromSnackMenu(snackNames, snackprice);
                inputcheck(snackMenuChoice, snackprice, coinsInCents);
            }
        }
    }

    public static void main(String[] args) {
        // TODO code application logic here
        // initialize coins, snack names & prices
        coinsInCents = new int[]{1000, 500, 200, 100, 50};
        snackNames = new String[]{"KitKat", "Oreo", "Marshmallow", "Cupcake"};
        len = snackNames.length;
        double snackprice[] = new double[len];
        for (int i = 0; i < 4; i++) {
            Snack snack = new Snack(snackNames[i]);
            snackprice[i] = snack.getPrice();
        }
        /**
         * * student's work here to initialize values, call
         * getChoiceFromSnackMenu()of the SnackTimeHelper class, where
         * Number-Format Exceptions are handled for you. Modify the following
         * sample code fragment to start processing user requests in a loop **
         */
        int snackMenuChoice = SnackTimeHelper.getChoiceFromSnackMenu(snackNames, snackprice);
        inputcheck(snackMenuChoice, snackprice, coinsInCents);

        /* ... */
    }

}
