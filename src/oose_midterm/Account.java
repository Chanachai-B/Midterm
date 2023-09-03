package oose_midterm;

public class Account extends Person {

    private String userName;
    private String password;
    private double cashBalance;

    public Account(String userName, String password, String firstName, String lastName, String gender, String idCardNumber, double cash) {
        super.setIdCardNumber(idCardNumber);
        super.setFirstName(firstName);
        super.setLastName(lastName);
        super.setGender(gender);

        this.userName = userName;
        this.password = password;
        this.cashBalance = cash;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }

    public double getCashBalance() {
        return this.cashBalance;
    }

    public void setCashBalance(double cash) {
        this.cashBalance = cash;
    }

}
