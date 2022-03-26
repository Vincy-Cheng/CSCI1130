/**
 * CSCI1130 Assignment 2 FutureValue
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
 * Date        : 29/9/2021
 */


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package futurevalue;
import java.util.Scanner;
/**
 *
 * @author wincs
 */
public final class FutureValue {
    public Scanner keyboard = new Scanner(System.in);
    public double principal ;
    public double rate ;
    public int time;
    public int period;
    public int month;
    
    public FutureValue(){
        inputPrincipal();
        inputRate();
        inputTimeSpan();
        inputCompoundPeriod();
        month = period;
        period = 12/period;
        aftertime();
        principaldouble();

    }
    
    public void inputPrincipal(){
        Boolean check = true;
        while(check){
            System.out.print("Input Principal [$10000.00 - $109700.00]:");
            principal = keyboard.nextDouble();
            if(principal >= 10000.0 && principal <= 109700.00)
                check = false;
            else
                System.out.println("Invalid Principal, please enter again.");
        }
    }
    
    public void inputRate(){
        Boolean check = true;
        while(check){
            System.out.print("Input Annual Interest Rate [1.0% - 10.0%]:");
            rate = keyboard.nextDouble()/100;
            if(rate >= 0.01 && rate <= 0.1)
                check = false;
            else
                System.out.println("Invalid Annual Interest Rate, please enter again.");
        }
    }
    
    public void inputTimeSpan(){
        Boolean check = true;
        while(check){
            System.out.print("Input Timespan [2 - 10 years]: ");
            time = keyboard.nextInt();
            if(time >= 2 && time <= 10)
                check = false;
            else
                System.out.println("Invalid Timespan, please enter again.");
        }
    }
    
    public void inputCompoundPeriod(){
        Boolean check = true;
        while(check){
            System.out.print("Input Compounding Period [2, 3 or 6 months]:");
            period = keyboard.nextInt();
            if(period == 2 ||  period == 3||  period == 6)
                check = false;
            else
                System.out.println("Invalid Compounding Period, please enter again.");
        }
    }
    
    public void aftertime(){
        double after;
        // 2 months --> 1 years 6 times
        // 3 months --> 1 years 4 times
        // 6 months --> 1 years 2 times
        after =principal* Math.pow((1+(rate/period)), period);
        System.out.printf("Future Value after 1 year: %.2f\n",after);
        after =principal* Math.pow((1+(rate/period)), 2*period);
        System.out.printf("Future Value after 2 years: %.2f\n",after);
        if(time ==3){
            after =principal* Math.pow((1+(rate/period)), 3*period);
            System.out.printf("Future Value after 3 years: %.2f",after);

        }
        else if(time >3){
            System.out.println("...");
            after =principal* Math.pow((1+(rate/period)), time*period);
            System.out.printf("Future Value after %d years: %.2f\n", time,after);
        }
        //after = Math.pow(principal* (1+(rate/period)), (time*period));//;
    }
    
    public double principaldouble(){
        double twice = Math.log(2)/(period*Math.log(1+(rate/period)));
        //System.out.println("double = " + twice);        
        if ((twice %1)!=0){
            if (month == 2){
                if(twice %1 <=0.167){
                    System.out.printf("Time to double asset: %d years 2 months \n",(int)twice);
                }
                else if(twice %1 >0.167 && twice %1 <= 0.333){
                    System.out.printf("Time to double asset: %d years 4 months \n",(int)twice);
                }
                else if(twice %1 >0.333 && twice %1 <= 0.5){
                    System.out.printf("Time to double asset: %d years 6 months \n",(int)twice);
                }
                else if(twice %1 >0.5 && twice %1 <= 0.667){
                    System.out.printf("Time to double asset: %d years 8 months \n",(int)twice);
                }
                else if(twice %1 >0.667){
                    System.out.printf("Time to double asset: %d years \n",(int)twice+1);
                }
            }
            else if (month == 3){
                if(twice %1 <=0.25){
                    System.out.printf("Time to double asset: %d years 3 months \n",(int)twice);
                }
                else if(twice %1 >0.25 && twice %1 <= 0.5){
                    System.out.printf("Time to double asset: %d years 6 months \n",(int)twice);
                }
                else if(twice %1 >0.5 && twice %1 <= 0.75){
                    System.out.printf("Time to double asset: %d years 9 months \n",(int)twice);
                }
                else if(twice %1 >0.75){
                    System.out.printf("Time to double asset: %d years \n",(int)twice+1);
                }
            }
            else if(month == 6){
                if(twice %1 <=0.5){
                    System.out.printf("Time to double asset: %d years 6 months \n",(int)twice);
                }
                else if(twice %1 >0.5){
                    System.out.printf("Time to double asset: %d years \n",(int)twice+1);
                }
            }
        }
        return twice;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        FutureValue current = new FutureValue();
    }   
}
