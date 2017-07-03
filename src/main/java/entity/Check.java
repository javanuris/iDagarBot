package entity;

/**
 * Created by User on 01.07.2017.
 */
public class Check extends BaseEntity {

    private int checkDate;
    private int status;
    private String reason;
    private Person person;

    public Check() {
    }

    public int getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(int checkDate) {
        this.checkDate = checkDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
