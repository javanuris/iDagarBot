package logic;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;


/**
 * Created by User on 01.07.2017.
 */
public class TelegramAttendanceBot extends TelegramLongPollingBot {

    public void onUpdateReceived(Update update) {

        if (update.getMessage().hasLocation()) {
            System.out.println("Ectm");
            GeoDetermine geoDetermine = new GeoDetermine();
            geoDetermine.personLocation(update);
            geoDetermine.action();

            try {
                sendMessage(geoDetermine.action());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        } else if (update.getMessage().getText().equals(Commands.HELP)) {
            try {
                sendMessage(allCommands(update));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (update.getMessage().getText().equals(Commands.REGISTER)) {
            RegistraionPerson registraionPerson = new RegistraionPerson();
            registraionPerson.personRegister(update);
            try {
                sendMessage(registraionPerson.action());
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
        String commands = Commands.REGISTER + "-Регистрация \n" + Commands.MY_STORY + " - История поскщяемости \n" + Commands.END + "-Завершить действие";
        return sendMessage.setText(commands);
    }
}