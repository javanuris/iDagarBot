package logic;

import dao.mysql.MySqlCheckDao;
import dao.mysql.MySqlPersonDao;
import entity.Check;
import entity.Person;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 01.07.2017.
 */
public class KeyBoardChoose {
    InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
   private long chatId;

    public void personKeyboard(Update update){
        chatId = update.getMessage().getChatId();

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText(Constant.ABANDODED);
        button1.setSwitchInlineQueryCurrentChat(Constant.ABANDODED);

        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText(Constant.FELL_ILL);
        button2.setSwitchInlineQueryCurrentChat(Constant.FELL_ILL);

        InlineKeyboardButton button3 = new InlineKeyboardButton();
        button3.setText(Constant.OVERSLEEP);
        button3.setSwitchInlineQueryCurrentChat(Constant.OVERSLEEP);

        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        rowsInline.add(buttons);

        InlineKeyboardButton button4 = new InlineKeyboardButton();
        button4.setText(Constant.I_COMMING);
        button4.setSwitchInlineQueryCurrentChat(Constant.I_COMMING);

        InlineKeyboardButton button5 = new InlineKeyboardButton();
        button5.setText(Constant.NOT_COMMING);
        button5.setSwitchInlineQueryCurrentChat(Constant.NOT_COMMING);

        List<InlineKeyboardButton> buttons2 = new ArrayList<>();
        buttons2.add(button4);
        buttons2.add(button5);

        rowsInline.add(buttons2);
        markupInline.setKeyboard(rowsInline);
    }

    public SendMessage action(){

        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyMarkup(markupInline);
        sendMessage.setChatId(chatId).setText("Не пришел... Назови причину? \uD83D\uDE10");
        return sendMessage;
    }

    public void setReasonUpdate(Update update, String str){
        MySqlCheckDao mySqlCheckDao = new MySqlCheckDao();
        MySqlPersonDao mySqlPersonDao = new MySqlPersonDao();
        Person person = (Person) mySqlPersonDao.findByTelegramId(Math.toIntExact(update.getMessage().getChat().getId()));
        Check check = new Check();
        check.setPerson(person);
        check.setReason(str);
        check.setCheckDate(update.getMessage().getDate());
        check.setStatus(1);
        mySqlCheckDao.insert(check);
    }
}
