package logic;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;


/**
 * Created by User on 01.07.2017.
 */
public class TelegramAttendanceBot extends TelegramLongPollingBot {
    KeyBoardChoose keyBoardChoose = new KeyBoardChoose();

    public void onUpdateReceived(Update update) {

        System.out.println(update.getMessage().getText());
        if (update.getMessage().hasLocation()) {
            GeoDetermine geoDetermine = new GeoDetermine();
            geoDetermine.personLocation(update);
            geoDetermine.action();

            if (!geoDetermine.isInPlace()) {

                KeyBoardChoose keyBoardChoose = new KeyBoardChoose();
                keyBoardChoose.personKeyboard(update);

                try {
                    sendMessage(keyBoardChoose.action());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    sendMessage(geoDetermine.action());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

        } else if (update.getMessage().getText().equals(Constant.NAME_COM + Constant.ABANDODED)) {
            keyBoardChoose.setReasonUpdate(update, Constant.ABANDODED);
        } else if (update.getMessage().getText().equals(Constant.NAME_COM + Constant.FELL_ILL)) {
            keyBoardChoose.setReasonUpdate(update, Constant.FELL_ILL);
        } else if (update.getMessage().getText().equals(Constant.NAME_COM + Constant.I_COMMING)) {
            keyBoardChoose.setReasonUpdate(update, Constant.I_COMMING);
        } else if (update.getMessage().getText().equals(Constant.NAME_COM + Constant.OVERSLEEP)) {
            keyBoardChoose.setReasonUpdate(update, Constant.OVERSLEEP);
        } else if (update.getMessage().getText().equals(Constant.NAME_COM + Constant.NOT_COMMING)) {
            keyBoardChoose.setReasonUpdate(update, Constant.NOT_COMMING);
        } else if (update.getMessage().getText().equals(Constant.HELP)) {
            try {
                sendMessage(allCommands(update));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (update.getMessage().getText().equals(Constant.SCHEDULE)) {
            try {
                ShowSchedule showSchedule = new ShowSchedule();
                SendMessage sendMessage = showSchedule.personSchedule(update);
                sendMessage(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (update.getMessage().getText().equals(Constant.REGISTER)) {
            RegistraionPerson registraionPerson = new RegistraionPerson();
            registraionPerson.personRegister(update);
            try {
                sendMessage(registraionPerson.action());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (update.getMessage().getText().equals(Constant.MY_STORY)) {
            AttendanceStory attendanceStory = new AttendanceStory();
            attendanceStory.personAtandance(update);
            try {
                sendMessage(attendanceStory.action());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId()).setText("Привет!✋ Давай по делу, без флуда... \nНачни с /help \uD83D\uDE09");
            try {
                sendMessage(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public String getBotUsername() {
        return null;
    }

    public String getBotToken() {
        return "431328313:AAFBcu-gshvdqRGrw7Xn_lbz00i-HClHyS4";
    }


    private SendMessage allCommands(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        String commands = Constant.REGISTER + "-Регистрация \n" + Constant.MY_STORY + " - История посещаемости \n" + Constant.SCHEDULE + "- Распорядок дня \n" + Constant.END + "-Завершить действие \n"
                + " \uD83D\uDCE1 Для того что бы зафиксировать прибытие на место, отправь данные геолокации";
        return sendMessage.setText(commands);
    }
}