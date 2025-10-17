package com.pluralsight;

import javax.swing.text.html.HTMLDocument;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

        private LocalDate date;
        private LocalTime time;
        private String item;
        private String vendor;
        private double amount;


        public Transaction(LocalDate date, LocalTime time, String item, String vendor, double amount) {
            this.date = date;
            this.time = time;
            this.item = item;
            this.vendor = vendor;
            this.amount = amount;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public LocalTime getTime() {
            return time;
        }

        public void setTime(LocalTime time) {
            this.time = time;
        }

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public String getVendor() {
            return vendor;
        }

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }


        public String toString() {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return String.format("%s | %s | %s | %s | %.2f", date.format(dateFormatter), time.format(timeFormatter), item, vendor, amount);
        }
        private static String getTableHeader(){
            return String.format("%s | %s | %s | %s | %s | %s", "Date", "Time", "Item", "Vendor", "Amount");
        }

    }
