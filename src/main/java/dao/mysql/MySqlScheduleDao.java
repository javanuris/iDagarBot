package dao.mysql;

import connection.ConnectionDB;
import entity.Schedule;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 02.07.2017.
 */
public class MySqlScheduleDao {
    private static final String SELECT_ALL = "SELECT * FROM schedules";

    public List<Schedule> getAllSchedule() {
        List<Schedule> list = new ArrayList<>();
        Schedule schedule = null;
        try {
            ConnectionDB connectionDB = new ConnectionDB();

            try (PreparedStatement statement = connectionDB.getConnection().prepareStatement(SELECT_ALL)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        schedule = item(schedule, resultSet);
                        list.add(schedule);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Schedule item(Schedule schedule, ResultSet resultSet) throws SQLException {
        schedule = new Schedule();
        schedule.setId(resultSet.getInt(1));
        schedule.setTitle(resultSet.getString(2));
        schedule.setTime(resultSet.getString(3));
        schedule.setRoom(resultSet.getString(4));
        return schedule;
    }
}
