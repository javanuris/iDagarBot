package logic;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;


/**
 * Created by User on 01.07.2017.
 */
public class GeoDetermine {
    private double latitude = 51.14814758300781;
    private double longitude = 71.36809539794922;
    private boolean inPlace = false;
    private long chatId;
    public void personLocation(Update update) {
        double personX = update.getMessage().getLocation().getLatitude();
        double personY = update.getMessage().getLocation().getLongitude();
        chatId = update.getMessage().getChatId();

        if (latitude == personX && longitude == personY) {
            inPlace = true;
        } else {
            inPlace = false;
        }
    }

    public SendMessage action(){
        SendMessage sendMessage = new SendMessage();
        if(isInPlace()){
           return sendMessage.setChatId(chatId).setText(" На месте... Готовься к занятиям! \uD83D\uDE01");
        }else{
            return sendMessage.setChatId(chatId).setText(" Не пришел... Твое оправдание? \uD83D\uDE10");

        }
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
