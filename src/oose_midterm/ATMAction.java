package oose_midterm;

public interface ATMAction {

    void checkable(Account accDetail);

    void withDrawable(Account accDetail, int currency);

    void depositeable(Account accDetail, double cash);

    void transferable(Account source, String id, double cash);
}
