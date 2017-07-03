package logic;

import dao.mysql.MySqlScheduleDao;
import entity.Schedule;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import java.util.List;

/**
 * Created by User on 02.07.2017.
 */
public class ShowSchedule {
    public SendMessage personSchedule(Update update) {
        String text;
        String list = "";
        SendMessage sendMessage = new SendMessage();
        MySqlScheduleDao mySqlScheduleDao = new MySqlScheduleDao();
        List<Schedule> showSchedules = mySqlScheduleDao.getAllSchedule();

        for (Schedule schedule : showSchedules) {
            text = schedule.getTitle() + " // " + schedule.getRoom() + " // " + schedule.getTime() + "\n";
            list += text;
        }

        sendMessage.setText(list).setChatId(update.getMessage().getChatId());
        return sendMessage;
    }
}
