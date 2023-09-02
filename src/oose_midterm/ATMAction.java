package oose_midterm;

public interface ATMAction {

    void checkable(Account accDetail);

    void withDrawable(Account accDetail, int cash);

    void depositeable(Account accDetail, int cash);

    void transferable(Account source, String id, int cash);
}
