package oose_midterm;

import java.util.ArrayList;
import java.util.Scanner;

public class Bank {

    private String bankName;
    private ArrayList<Account> user = new ArrayList<>();
    private Manager admin;

    public Bank(String name) {
        this.bankName = name;
        System.out.println("*********************************");
        System.out.println("Welcome to " + this.bankName + " Management System");
        this.addAdmin();
    }

    public Bank(ATM atm) {
        this.bankName = atm.getAtmName();
        this.user = atm.getUser();
        this.admin = atm.getAdmin();
    }

    private void addAdmin() {
        System.out.println("*********************************");
        System.out.println("First, set up an admin account.");
        System.out.println("Enter the details of admin account");
        ArrayList<Object> list = this.addDetail(true);
        while (true) {
            if (list != null) {
                this.admin = new Manager((String) list.get(0), (String) list.get(1), (String) list.get(2), (String) list.get(3), (String) list.get(4), (String) list.get(5));
                System.out.println("successfully added information");
                break;
            }
            list = this.addDetail(true);
        }

        ATM atm = new ATM(this);
        atm.login();
    }

    public void addAccount() {
        Scanner userInput = new Scanner(System.in);

        while (true) {
            System.out.println("*********************************");
            System.out.println("Menu Add user account.");
            System.out.print("Enter amount of all account = ");
            try {
                int num = userInput.nextInt();
                if (num > 0 && num + this.user.size() <= 5) {
                    System.out.println("Enter the details of each account.");
                    for (int i = 1; i <= num; i++) {
                        System.out.println("*********************************");
                        System.out.println("No." + i);
                        ArrayList<Object> list = addDetail(false);
                        if (list == null) {
                            i--;
                            continue;
                        }
                        this.user.add(new Account((String) list.get(0), (String) list.get(1), (String) list.get(2), (String) list.get(3), (String) list.get(4), (String) list.get(5), (Integer) list.get(6)));
                        System.out.println("successfully added information");
                    }
                    ATM atm = new ATM(this);
                    atm.showAdminMenu();
                } else {
                    System.err.println("\nThe number of accounts must be less than 5 accounts.");
                    System.out.println("Press \"1\" for try again.");
                    System.out.println("Press \"2\" for Back to admin menu Service.");
                    System.out.println("Press any key for exit the program.");
                    System.out.print("Choose : ");
                    String choice = userInput.next();
                    switch (choice) {
                        case "1" ->
                            this.addAccount();
                        case "2" -> {
                            ATM atm = new ATM(this);
                            atm.showAdminMenu();
                        }
                        default -> {
                            System.err.println("Exit program.....");
                            userInput.close();
                            System.exit(0);
                        }
                    }
                }
            } catch (java.util.InputMismatchException e) {
                System.err.println("something went wrong!!!");
                this.addAccount();
            }
        }

    }

    private ArrayList<Object> addDetail(boolean isAdmin) {
        Scanner userInput = new Scanner(System.in);
        ArrayList<Object> list = new ArrayList<>();
        ArrayList<String> nameUser = new ArrayList<>();
        ArrayList<String> idUser = new ArrayList<>();
        for (int i = 0; i < this.user.size(); i++) {
            nameUser.add(this.user.get(i).getUserName());
            idUser.add(this.user.get(i).getIdCardNumber());
        }

        System.out.print("Enter Username : ");
        String userName = userInput.next();
        if (userName.length() > 10) {
            System.err.println("The number of Username must be not exceed 10 characters.");
            return null;
        } else if (nameUser.contains(userName)) {
            System.err.println("duplicate account username.");
            return null;
        } else if (this.admin != null && this.admin.getUserName().equals(userName)) {
            System.err.println("duplicate account admin.");
            return null;
        }
        list.add(userName);

        System.out.print("Enter Password : ");
        String pass = userInput.next();
        if (pass.length() != 4) {
            System.err.println("The number of passwords must be 4 digits.");
            return null;
        }
        list.add(pass);

        System.out.print("Enter First Name : ");
        String fName = userInput.next();
        if (fName.length() > 50) {
            System.err.println("The number of Admin first names must not be more than 50 characters.");
            return null;
        }
        list.add(fName);

        System.out.print("Enter Last Name : ");
        String lName = userInput.next();
        if (lName.length() > 50) {
            System.err.println("The number of Admin last names must not be more than 50 characters.");
            return null;
        }
        list.add(lName);

        System.out.print("Select gender (1 for Men, 2 for Women) : ");
        String sGender = userInput.next();
        String gender;
        switch (sGender) {
            case "1" -> {
                gender = "Men";
            }
            case "2" ->
                gender = "Women";

            default -> {
                System.err.println("Select \"1\" or \"2\" only!!!");
                return null;
            }
        }
        list.add(gender);

        System.out.print("Enter ID Card Number : ");
        String id = userInput.next();
        if (id.length() != 13) {
            System.err.println("The number of ID Card numbers must be equal to 13 digits.");
            return null;
        } else if (idUser.contains(id)) {
            System.err.println("duplicate account id card number.");
            return null;
        }
        list.add(id);

        if (isAdmin == false) {
            System.out.print("Balance : ");
            String balance = userInput.next();
            try {
                int balanceAmount = Integer.parseInt(balance);
                if (balanceAmount > 1000000) {
                    System.out.println("The balance must be less than 1,000,000 baht.");
                    return null;
                } else if (balanceAmount <= 0) {
                    System.out.println("The balance must be greater than 0 baht.");
                    return null;
                } else {
                    list.add(balanceAmount);
                }
            } catch (NumberFormatException e) {
                System.out.println("something went wrong!!!");
                return null;
            }
        }
        return list;
    }

    public String getBankName() {
        return this.bankName;
    }

    public ArrayList<Account> getUser() {
        return this.user;
    }

    public Manager getAdmin() {
        return this.admin;
    }
}
