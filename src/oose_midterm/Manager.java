package oose_midterm;

public class Manager extends Person {

    private String userName;
    private String password;

    public Manager(String userName, String password, String firstName, String lastName, String gender, String idCardNumber) {
        super.setIdCardNumber(idCardNumber);
        super.setFirstName(firstName);
        super.setLastName(lastName);
        super.setGender(gender);

        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
