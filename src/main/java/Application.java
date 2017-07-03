import logic.TelegramAttendanceBot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;



public class Application {
    public static void main(String[] args) {

        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();

        try{
            botsApi.registerBot(new TelegramAttendanceBot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }


    }
}
