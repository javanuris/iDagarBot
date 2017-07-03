package logic;

import dao.mysql.MySqlCheckDao;
import dao.mysql.MySqlPersonDao;
import entity.Check;
import entity.Person;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class AttendanceStory {

    private  List<Check> checks;
    private long chatId;

    String str = "";

    public void personAtandance(Update update) {
        MySqlPersonDao mySqlPersonDao = new MySqlPersonDao();
        Person person = (Person) mySqlPersonDao.findByTelegramId(Math.toIntExact(update.getMessage().getChat().getId()));
        chatId = Math.toIntExact(update.getMessage().getChatId());
        MySqlCheckDao mySqlCheckDao = new MySqlCheckDao();
        checks = mySqlCheckDao.getCustomers(person);
    }

    public SendMessage action(){
        SendMessage sendMessage = new SendMessage();

        for(Check check : checks){
            String text;
            if(check.getStatus()==1) {
                text = "Дата и время : " + dateFormat(check.getCheckDate()) + " Status: Отсутсвовал ➖"  + "\n";
            }else{
                text = "Дата и время  : " + dateFormat(check.getCheckDate()) + " Status: Присутсвовал ➕" + "\n";
            }
                str += text;
        }

        return sendMessage.setChatId(chatId).setText(str);

    }

    private String dateFormat(long unix){
        long unixSeconds = unix;
        Date date = new Date(unixSeconds*1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); // the format of your date
        String formattedDate = sdf.format(date);
        return formattedDate;
    }
}
