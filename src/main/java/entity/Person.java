package entity;

/**
 * Created by User on 01.07.2017.
 */
public class Person extends BaseEntity {
    private int telegramId;
    private String firstName;
    private String lastName;
    private int checkDate;
    private int status;

    public Person() {
    }

    public int getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(int telegramId) {
        this.telegramId = telegramId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
}
