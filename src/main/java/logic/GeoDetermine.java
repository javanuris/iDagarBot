package logic;

import dao.mysql.MySqlCheckDao;
import dao.mysql.MySqlPersonDao;
import entity.Check;
import entity.Person;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;


/**
 * Created by User on 01.07.2017.
 */
public class GeoDetermine {
      /*  51.1199951171875
            71.46634674072266*/

    private double latitude = 51.14872741699219;
    private double longitude = 71.36585998535156;
    private boolean inPlace = false;
    private long chatId;
    private boolean isRegister = false;

    public void personLocation(Update update) {
        double personX = update.getMessage().getLocation().getLatitude();
        double personY = update.getMessage().getLocation().getLongitude();
        System.out.println(personX);
        System.out.println(personY);
        chatId = update.getMessage().getChatId();
        Check check = new Check();
        MySqlPersonDao mySqlPersonDao = new MySqlPersonDao();
        MySqlCheckDao mySqlCheckDao = new MySqlCheckDao();

        Person telegramId = (Person) mySqlPersonDao.findByTelegramId(Math.toIntExact(update.getMessage().getChat().getId()));

        if (telegramId == null) {
            isRegister =false;
        } else {
            isRegister = true;
            if(latitude == personX && longitude == personY) {
                check.setPerson(telegramId);
                check.setCheckDate(update.getMessage().getDate());
                check.setStatus(2);
                mySqlCheckDao.insert(check);
                inPlace = true;
            } else {
                inPlace = false;
            }
        }
    }

    public SendMessage action() {

        SendMessage sendMessage = new SendMessage();
        if (!isRegister()){
            return sendMessage.setChatId(chatId).setText(" Пожалуйста зарегайся! \uD83D\uDE01");
        }
        if (isInPlace()) {
            return sendMessage.setChatId(chatId).setText(" На месте... Готовься к занятиям! \uD83D\uDE01");
        }
        return null;
    }


    public boolean isRegister() {
        return isRegister;
    }

    public void setRegister(boolean register) {
        isRegister = register;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isInPlace() {
        return inPlace;
    }

    public void setInPlace(boolean inPlace) {
        this.inPlace = inPlace;
    }
}
