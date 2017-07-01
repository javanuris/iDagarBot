package logic;

import dao.mysql.MySqlPersonDao;
import entity.BaseEntity;
import entity.Person;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

/**
 * Created by User on 01.07.2017.
 */
public class RegistraionPerson {
    private long chatId;
    private boolean registerStatus = false;

    public void personRegister(Update update){
        Person person = new Person();
        MySqlPersonDao mySqlPersonDao = new MySqlPersonDao();
        BaseEntity telegramId = mySqlPersonDao.findByTelegramId(Math.toIntExact(update.getMessage().getChat().getId()));
        if(telegramId!=null){
            chatId = update.getMessage().getChatId();
            registerStatus = false;
        }else{
            System.out.println("Update");
            chatId = update.getMessage().getChatId();

        person.setTelegramId(Math.toIntExact(update.getMessage().getChat().getId()));
        person.setFirstName(update.getMessage().getChat().getFirstName());
        person.setLastName(update.getMessage().getChat().getLastName());
        person.setCheckDate(update.getMessage().getDate());

        mySqlPersonDao.insert(person);
            registerStatus = true;
    }
    }


    public SendMessage action(){
        SendMessage sendMessage = new SendMessage();
        if (registerStatus) {
            return sendMessage.setChatId(chatId).setText(" Регистрация прошла успешна! \uD83D\uDCA1");
        }else{
            return sendMessage.setChatId(chatId).setText(" Ты уже зарегстрировался ранее!\n Точно не помню когда..!\n Но было дело! \uD83D\uDC7D");
        }
    }
}
