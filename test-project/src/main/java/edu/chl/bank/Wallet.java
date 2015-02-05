package edu.chl.bank;


public class Wallet {
    private double amount;

    private String accountNr;

    public double deposit(double amt) {
        amount += amt;

        return amount;
    }

    public double withdraw(double amt) {
        amt = Math.min(amount, amt);
        amount -= amt;
        return amt;
    }

    public double getAmount() {
        return amount;
    }
}