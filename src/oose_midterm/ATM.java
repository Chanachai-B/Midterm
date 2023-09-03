package oose_midterm;

import java.util.ArrayList;
import java.util.Scanner;

public class ATM implements ATMAction {

    private final String atmName;
    private ArrayList<Account> user = new ArrayList<>();
    private Manager admin;
    private int numOfLogins = 0;
    private double rate;

    public ATM(Bank detail) {
        this.atmName = detail.getBankName();
        this.user = detail.getUser();
        this.admin = detail.getAdmin();
        this.rate = detail.getRate();
    }

    @Override
    public void checkable(Account accDetail) {
        System.out.println("*********************************");
        System.out.println("Username : " + accDetail.getUserName());
        System.out.println("Account name : " + accDetail.getFullName());
        System.out.println("Account Balance " + accDetail.getCashBalance() + " bath or " + accDetail.getCashBalance() / this.getRate() + " BTC");
    }

    @Override
    public void withDrawable(Account accDetail, int currency) {
        Scanner userInput = new Scanner(System.in);
        System.out.println("*********************************");
        System.out.println("Username : " + accDetail.getUserName());
        System.out.println("Account name : " + accDetail.getFullName());
        if (currency == 1) {
            System.out.println("Account can withdraw money " + accDetail.getCashBalance() + " bath");
            try {
                System.out.print("Enter the amount you want to withdraw : ");
                double cash = userInput.nextDouble();
                if (cash > accDetail.getCashBalance() || cash < 0) {
                    System.err.println("Unable to withdraw money");
                    System.out.println("Account Balance " + accDetail.getCashBalance() + " bath");
                } else {
                    accDetail.setCashBalance(accDetail.getCashBalance() - cash);
                    System.out.println("Account Balance " + accDetail.getCashBalance() + " bath");
                }
                System.out.println("Account withdrawal " + cash + " bath");
            } catch (java.util.InputMismatchException e) {
                System.err.println("something went wrong!!!");
                this.showServiceMenu(accDetail);
            }
        } else {
            double btc = accDetail.getCashBalance() / this.getRate();
            System.out.println("Account can withdraw money " + btc + " BTC");
            try {
                System.out.print("Enter the amount you want to withdraw : ");
                double cash = userInput.nextDouble();
                if (cash > btc || cash < 0) {
                    System.err.println("Unable to withdraw money");
                    System.out.println("Account Balance " + btc + " BTC");
                } else {
                    accDetail.setCashBalance(accDetail.getCashBalance() - cash * this.getRate());
                    System.out.println("Account Balance " + cash + " BTC");
                }
                System.out.println("Account withdrawal " + cash + " BTC");
            } catch (java.util.InputMismatchException e) {
                System.err.println("something went wrong!!!");
                this.showServiceMenu(accDetail);
            }
        }
    }

    @Override
    public void depositeable(Account accDetail, double cash) {
        System.out.println("*********************************");
        System.out.println("Username : " + accDetail.getUserName());
        System.out.println("Account name : " + accDetail.getFullName());
        System.out.println("Account depositeable " + cash + " bath");
        double balance = cash + accDetail.getCashBalance();
        if (balance > 1000000 || cash < 0) {
            System.err.println("Unable to deposit money");
            System.out.println("Account Balance " + accDetail.getCashBalance() + " bath");
        } else {
            accDetail.setCashBalance(balance);
            System.out.println("Account Balance " + accDetail.getCashBalance() + " bath");
        }
    }

    @Override
    public void transferable(Account source, String id, double cash) {
        for (int i = 0; i < this.user.size(); i++) {
            if (id.equals(this.user.get(i).getIdCardNumber())) {
                double sourceBalance = source.getCashBalance() - cash;
                double destinationBalance = this.user.get(i).getCashBalance() + cash;
                if (source.getIdCardNumber().equals(this.user.get(i).getIdCardNumber())) {
                    System.err.println("Unable to transfer money to the destination account.");
                    System.err.println("Because the destination and source accounts are the same account.");
                    this.showServiceMenu(source);
                } else if (cash <= source.getCashBalance() && destinationBalance < 1000000) {
                    System.out.println("Username : " + source.getUserName());
                    System.out.println("From account name : " + source.getFullName());
                    System.out.println("To account name : " + this.user.get(i).getFullName());
                    System.out.println("Amount : " + cash);
                    source.setCashBalance(sourceBalance);
                    this.user.get(i).setCashBalance(destinationBalance);
                    this.showServiceMenu(source);
                } else if (cash > source.getCashBalance()) {
                    System.err.println("Unable to transfer money to the destination account.");
                    System.err.println("due to insufficient balance.");
                    this.showServiceMenu(source);
                } else if (destinationBalance > 1000000) {
                    System.err.println("Unable to transfer money to the destination account.");
                    System.err.println("because the balance amount of the destination account exceeds the limit.");
                    this.showServiceMenu(source);
                }
            }
        }
        System.err.println("Unable to transfer money to the destination account.");
        System.err.println("because The beneficiary's account information could not be found..");
    }

    public void login() {
        System.out.println("*********************************");
        System.out.println("Please login");
        Scanner userInput = new Scanner(System.in);
        System.out.println("ATM " + this.atmName);
        System.out.print("Account ID : ");
        String userName = userInput.next();
        if (userName.equals(this.admin.getUserName())) {
            System.out.print("Account Password : ");
            String userPassword = userInput.next();
            if (userPassword.equals(this.admin.getPassword())) {
                this.numOfLogins = 0;
                this.showAdminMenu();
            } else {
                System.err.println("Unable to login due to wrong password.");
                this.numOfLogins++;
                if (this.numOfLogins >= 3) {
                    System.err.println("You have entered the wrong password more than 3 times. Log out.");
                    System.exit(0);
                } else {
                    this.numOfLogins = 0;
                    this.login();
                }
            }
        }

        for (int i = 0; i < this.user.size(); i++) {
            if (userName.equals(this.user.get(i).getUserName())) {
                System.out.print("Account Password : ");
                String userPassword = userInput.next();
                if (userPassword.equals(this.user.get(i).getPassword())) {
                    System.out.println("logged in successfully");
                    this.numOfLogins = 0;
                    this.showServiceMenu(this.user.get(i));
                    break;
                } else {
                    System.err.println("Unable to login due to wrong password.");
                    this.numOfLogins++;
                    if (this.numOfLogins >= 3) {
                        System.err.println("You have entered the wrong password more than 3 times. Log out.");
                        System.exit(0);
                    } else {
                        this.login();
                    }
                }
            }
        }
        System.err.println("This account id was not found in the system.");
        this.login();
    }

    public void showAdminMenu() {
        while (true) {
            System.out.println("*********************************");
            Scanner userInput = new Scanner(System.in);
            System.out.println("ATM " + this.atmName);
            System.out.println("Admin account : " + admin.getUserName());
            System.out.println("Menu Service");
            System.out.println("1. Add Account");
            System.out.println("2. Show all account");
            System.out.println("3. Show BTC Rate");
            System.out.println("4. Exit");
            System.out.print("Choose : ");
            String choice = userInput.next();
            switch (choice) {
                case "1" -> {
                    Bank bank = new Bank(this);
                    bank.addAccount();
                }
                case "2" -> {
                    System.out.println("*********************************");
                    System.out.println("All Account in " + this.atmName);
                    int i;
                    for (i = 0; i < this.user.size(); ++i) {
                        System.out.println((i + 1) + " : " + this.user.get(i).getUserName() + " : " + this.user.get(i).getFullName());
                    }
                    System.out.println((i + 1) + " : Back to admin menu Service");
                    System.out.print("Select a number to view results or go back : ");
                    choice = userInput.next();
                    try {
                        this.showDetail(Integer.parseInt(choice) - 1);
                    } catch (NumberFormatException e) {
                        System.err.println("Please enter numerical data.");
                    }
                }
                case "3" -> {
                    this.showBTCRate();
                }
                case "4" -> {
                    this.login();
                }
                default ->
                    System.err.println("something went wrong!!!");
            }
        }
    }

    private void showServiceMenu(Account accountDetail) {
        while (true) {
            System.out.println("*********************************");
            Scanner userInput = new Scanner(System.in);
            System.out.println("ATM " + this.atmName);
            System.out.println("Account ID : " + accountDetail.getUserName());
            System.out.println("Menu Service");
            System.out.println("1. Check account Balance.");
            System.out.println("2. Withdraw money.");
            System.out.println("3. deposit money.");
            System.out.println("4. Transfer money.");
            System.out.println("5. Show BTC Rate.");
            System.out.println("6. Log out.");
            System.out.print("Choose : ");
            String choice = userInput.next();
            switch (choice) {
                case "1" ->
                    this.checkable(accountDetail);
                case "2" -> {
                    System.out.println("*********************************");
                    System.out.println("Username : " + accountDetail.getUserName());
                    System.out.println("Account name : " + accountDetail.getFullName());
                    while (true) {
                        System.out.println("Choose the currency to withdraw :");
                        System.out.println("1. THB");
                        System.out.println("2. BTC");
                        System.out.println("3. Cancel");
                        System.out.print("Choose : ");
                        choice = userInput.next();
                        switch (choice) {
                            case "1" -> {
                                this.withDrawable(accountDetail, 1);
                                this.showServiceMenu(accountDetail);
                            }
                            case "2" -> {
                                this.withDrawable(accountDetail, 2);
                                this.showServiceMenu(accountDetail);
                            }
                            case "3" -> {
                                this.showServiceMenu(accountDetail);
                            }
                            default ->
                                System.out.println("Select \"1\", \"2\" or \"3\" again:");
                        }
                    }
                }
                case "3" -> {
                    System.out.println("*********************************");
                    System.out.println("Username : " + accountDetail.getUserName());
                    System.out.println("Account name : " + accountDetail.getFullName());
                    System.out.print("Enter the amount you wish to deposit : ");
                    try {
                        double cash = userInput.nextDouble();
                        this.depositeable(accountDetail, cash);
                    } catch (java.util.InputMismatchException e) {
                        System.err.println("something went wrong!!!");
                        this.showServiceMenu(accountDetail);
                    }
                }
                case "4" -> {
                    System.out.println("*********************************");
                    System.out.println("Username : " + accountDetail.getUserName());
                    System.out.println("Account name : " + accountDetail.getFullName());
                    System.out.print("Enter recipient account id : ");
                    String destination = userInput.next();
                    System.out.print("Enter the amount you want to transfer : ");
                    double cash = userInput.nextDouble();
                    this.transferable(accountDetail, destination, cash);
                }
                case "5" -> {
                    this.showBTCRate();
                }
                case "6" -> {
                    this.login();
                }
                default -> {
                    System.err.println("something went wrong!!!");
                    this.showServiceMenu(accountDetail);
                }
            }
        }
    }

    private void showBTCRate() {
        System.out.println("*********************************");
        System.out.println("BTC rate 1 BTC = " + this.getRate() + " bath.");
        System.out.println("*********************************");
    }

    private void showDetail(int index) {
        if (index < this.user.size()) {
            System.out.println("*********************************");
            System.out.println("Username : " + this.user.get(index).getUserName());
            System.out.println("Password : " + this.user.get(index).getPassword());
            System.out.println("Name     : " + this.user.get(index).getFullName());
            System.out.println("ID       : " + this.user.get(index).getIdCardNumber());
            System.out.println("Gender   : " + this.user.get(index).getGender());
            System.out.println("Balance  : " + this.user.get(index).getCashBalance());
        } else if (index == this.user.size()) {
            this.showAdminMenu();
        } else {
            System.out.println("*********************************");
            System.err.println("something went wrong!!!");
        }
    }

    public String getAtmName() {
        return this.atmName;
    }

    public ArrayList<Account> getUser() {
        return this.user;
    }

    public Manager getAdmin() {
        return this.admin;
    }

    public void setAdmin(Manager admin) {
        this.admin = admin;
    }

    public double getRate() {
        return this.rate;
    }
}
