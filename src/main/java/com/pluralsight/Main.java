package com.pluralsight;

import java.io.BufferedWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class Main {

    public static ArrayList<Transaction> transactions = getTransactionsFromFile();

    public static ArrayList<Transaction> getTransactionsFromFile() {
        ArrayList<Transaction> result = new ArrayList<Transaction>();
        try {
            FileReader fileReader = new FileReader("transactions.csv");
            BufferedReader br = new BufferedReader(fileReader);

            String line;
            while((line = br.readLine()) !=null) {
                String[] parts = line.split("\\|");

                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy:MM:dd");
                if (parts.length == 5) ;
                {
                    LocalDate date = LocalDate.parse(parts[0].trim());
                    LocalTime time = LocalTime.parse(parts[1].trim(), timeFormatter);
                    String item = parts[2].trim();
                    String vendor = parts[3].trim();
                    double amount = Double.parseDouble(parts[4].trim());

                    result.add(new Transaction(date, time, item, vendor, amount));
                }
            }
        } catch (IOException e) {
            System.out.println("Something went wrong with reading the file");
        }
        return result;
    }


    public static void main(String[] args) {
        // John helped get me started with displays
        System.out.println("// ========================================\n" +
                "// \uD83D\uDCB0 WELCOME TO GOATED BANKING \uD83D\uDCB0\n" +
                "// Manage your money like a true GOAT \uD83D\uDC10\n" +
                "// ========================================\n");
        String mainMenu = """                
                Hello there, What would you like to do?
                D) Add Deposit
                P) Make Payment(Debit)
                L) Ledger
                X) Exit
                """;

        while (true) {
            System.out.println(mainMenu);

            String command = ConsoleHelper.promptForString("Enter your command: ");//Tanner helped with consi

            switch (command.toUpperCase()) {
                case "D":
                    addDeposit();
                    break;
                case "P":
                    makePayment();
                    break;
                case "L":
                    ledgerDisplay();
                case "X":
                    return;
                default:
                    System.out.println("INVALID COMMAND! Please select one of the follow commands.");
                    break;
            }
        }
    }

    public static void addDeposit() {
        System.out.println("Add Deposit");
        String itemDesc = ConsoleHelper.promptForString("Enter Item Description: ");
        String vendorInfo = ConsoleHelper.promptForString("Enter Vendor Information: ");
        double amount = ConsoleHelper.promptForDouble("Enter Amount (Numerals Only): "); //Henry help with prompt issue
        System.out.println("Deposit Success, Information added");
        Transaction newDeposit = new Transaction(LocalDate.now(), LocalTime.now(), itemDesc, vendorInfo, amount);
        transactions.add(newDeposit);
        System.out.println("New Balance: " + calculateBalance());

        LocalDate today = LocalDate.now();
        LocalTime current = LocalTime.now();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        String date = today.format(dateFormatter);
        String time = current.format(timeFormatter);

        try {
            FileWriter fileWriter = new FileWriter("transactions.csv", true);
            BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
            bufferWriter.newLine();
            bufferWriter.write("Deposit completed, Information submitted " + date + "|" + time + "|" + itemDesc + "|" + vendorInfo + "|" + amount);
        } catch (IOException e) {
            System.out.println("There was an error");
        }
    }
    //Ayah helped with deposit/payment balances
    private static double calculateBalance() {
        double balance = 0;
        for (Transaction t : transactions) {
            balance += t.getAmount();
        }
        return balance;
    }

    // similar steps to making a deposit, I simplified the step by copying some of the steps previous
    public static void makePayment() {
        System.out.println("Make a Payment");
        String itemDesc = ConsoleHelper.promptForString("Enter Description: ");
        String vendorInfo = ConsoleHelper.promptForString("Enter Vendor Information: ");
        double amount = ConsoleHelper.promptForDouble("Enter Amount: ");
        amount = -Math.abs(amount); //to make sure the payment is negative because it's taking away
        System.out.println("Payment Success, Information added");
        Transaction payment = new Transaction(LocalDate.now(), LocalTime.now(), itemDesc, vendorInfo, amount);
        transactions.add(payment);
        System.out.println("New Balance: " + calculateBalance());

        LocalDate today = LocalDate.now();
        LocalTime current = LocalTime.now();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        String date = today.format(dateFormatter);
        String time = current.format(timeFormatter);

        try {
            FileWriter fileWriter = new FileWriter("transactions.csv", true);
            BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
            bufferWriter.newLine();
            bufferWriter.write("Payment complete, Information submitted " + date + "|" + time + "|" + itemDesc + "|" + vendorInfo + "|" + amount);
        } catch (IOException e) {
            System.out.println("There was an error");
        }
    }

    public static void ledgerDisplay() {
        System.out.println(" 🐐 GOATED BANKING LEDGER 🐐");
        System.out.println("----------------------------");
        System.out.println("Track every move like a GOAT 💸");
        System.out.println("Accuracy. Transparency. Mastery");
        System.out.println("----------------------------");
        String ledgerDisplay = """
                A) All Entries Displayed
                D) View Deposits
                P) View Payments
                R) View Reports
                H) Return to Home Page
                """;
        while (true) {
            System.out.println(ledgerDisplay);

            String command = ConsoleHelper.promptForString("Enter your command: ");

            switch (command.toUpperCase()) {
                case "A":
                    displayEntries(transactions);
                    break;
                case "D":
                    displayDeposits(transactions);
                    break;
                case "P":
                    displayPayments(transactions);
                    break;
                case "R":
                    reportMenu();
                    break;
                case "H": ; //Help from Margret and Dalis
                    return;
                default:
                    System.out.println("INVALID COMMAND! Please select one of the follow commands.");
            }
        }
    }

    public static void displayEntries(ArrayList<Transaction> transactions) {
        System.out.println("List all entries");
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

    public static void displayDeposits(ArrayList<Transaction> transactions) {
        System.out.println("Here are all the listed deposits");
        for (Transaction t : transactions) {
            if (t.getAmount() > 0) {
                System.out.println(t);
            }
        }

    }

    public static void displayPayments(ArrayList<Transaction> transactions) {
        System.out.println("Here is the payment history");
        for (Transaction t : transactions) {
//            System.out.println(t.getAmount() < 0);
//            System.out.println(t);

            if (t.getAmount() < 0) {
                System.out.println(t);
            }
        }
    }

    public static void reportMenu() {
        System.out.println("=======================================================");
        System.out.println("❓ WHAT WOULD YOU LIKE TO KNOW ABOUT YOUR PAST? ❓");
        System.out.println("-------------------------------------------------------");
        System.out.println("Explore your records. Reveal your history. 🕰️");
        System.out.println("=======================================================");
        String reportMenu = """
                1) Month To Date
                2) Previous Month
                3) Year To Date
                4) Previous Year
                5) Vendor Search
                0) Back to Ledger page
                """;
        while (true) {
            System.out.println(reportMenu);
            String command = ConsoleHelper.promptForString("Enter your command: ");

            switch (command) {
                case "1":
                    showMonthToDate();
                    break;
                case "2":
                    showPreviousMonth();
                    break;
                case "3":
                    showYearToDate();
                    break;
                case "4":
                    showPreviousYear();
                    break;
                case "5":
                    searchByVendor();
                    break;
                case "0":
                    return; //Go back to Ledger menu
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void showMonthToDate() {
        System.out.println("Month to Date Report: ");
        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear = LocalDate.now().getYear();
        for (Transaction t : transactions) {
            LocalDate date = t.getDate();
            if (date.getMonthValue() == currentMonth && date.getYear() == currentYear) {
                System.out.println(t);
            }
        }
    }
    private static void showPreviousMonth() {
        System.out.println("Previous Month Report: ");
        int prevMonth = LocalDate.now().getMonthValue() -1;
        int currentYear = LocalDate.now().getYear();
        if (prevMonth == 0) {
            prevMonth = 12;
            currentYear -= 1;
        }
        for (Transaction t : transactions) {
            LocalDate date = t.getDate();
            if (date.getMonthValue() == prevMonth && date.getYear() == currentYear) {
                System.out.println(t);
            }
        }
    }
    private static void showYearToDate() {
        System.out.println("Year to Date Report: ");

        int currentYear = LocalDate.now().getYear();

        for (Transaction t : transactions) {
            LocalDate date = t.getDate();
            if (date.getYear() == currentYear) {
                System.out.println(t);
            }
        }
    }
    private static void showPreviousYear() {
        System.out.print("Previous Year Report: ");

        int prevYear = LocalDate.now().getYear() -1;

        for (Transaction t : transactions) {
            if (t.getDate().getYear() == prevYear) {
                System.out.println(t);
            }
        }
    }
    private static void searchByVendor() {
        System.out.println("Search by Vendors: ");

        String vendorSearch = ConsoleHelper.promptForString("Enter Vendor name: ");

        for (Transaction t : transactions) {
            if (t.getVendor().equalsIgnoreCase(vendorSearch)) {
                System.out.println(t);
            }
        }
    }
}
