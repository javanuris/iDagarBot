package entity;

/**
 * Created by User on 02.07.2017.
 */
public class Schedule extends BaseEntity{
    private String title;
    private String room;
    private String time;

    public Schedule() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
