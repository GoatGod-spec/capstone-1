package com.pluralsight;

import java.time.LocalDate;
import java.util.Scanner;

public class ConsoleHelper {

    private static Scanner scanner = new Scanner(System.in);

    public static String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
    public static int promptForInt(String prompt){
        System.out.print(prompt);
        int result = scanner.nextInt();
        scanner.nextLine();
        return result;
    }
    public static float promptForFloat(String prompt){
        System.out.print(prompt);
        float result = scanner.nextFloat();
        scanner.nextLine();
        return result;
    }
    public static long promptForLong(String prompt){
        System.out.print(prompt);
        long result = scanner.nextLong();
        scanner.nextLine();
        return result;
    }
    public static LocalDate promptForDate(String prompt){
        while(true){
            try{
                System.out.print(prompt + ":");
                String dateAsString = scanner.nextLine();
                return LocalDate.parse(dateAsString);
            }
            catch(Exception ex){
                System.out.println("INVALID ENTRY! Please enter a valid date (YYYY-MM-DD");
            }
        }
    }
    public static double promptForDouble(String prompt){
        System.out.print(prompt);
        double result = scanner.nextDouble();
        scanner.nextLine();
        return result;
    }

}



