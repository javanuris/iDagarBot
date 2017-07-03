package dao.mysql;

import connection.ConnectionDB;
import entity.BaseEntity;
import entity.Check;
import entity.Person;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by User on 01.07.2017.
 */
public class MySqlCheckDao {
    private static final String FIND_BY_ID = "SELECT * FROM check_statuses WHERE id = ?";
    private static final String FIND_BY_USER = "SELECT * FROM check_statuses WHERE student_id = ?";
    private static final String INSERT = "INSERT INTO check_statuses VALUES(id,?,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE check_statuses SET reason = ? WHERE id = ?";

    public BaseEntity insert(Check item) {
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            try (PreparedStatement statement = connectionDB.getConnection().prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
                statement(statement, item).executeUpdate();
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    resultSet.next();
                    item.setId(resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                connectionDB.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return item;
    }

    public void update(Check item)  {
        ConnectionDB connectionDB = new ConnectionDB();

        try {
            try (PreparedStatement statement = connectionDB.getConnection().prepareStatement(UPDATE)) {
                statement.setString(1, item.getReason());
                statement.setInt(2, item.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
             e.printStackTrace();

        } finally {
            try {
                connectionDB.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


    public List<Check> getCustomers(Person person) {
        ConnectionDB connectionDB = new ConnectionDB();
        List<Check> list = new ArrayList<>();
        Check check = null;
        try {
            try (PreparedStatement statement = connectionDB.getConnection().prepareStatement(FIND_BY_USER)) {
              statement.setInt(1, person.getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        check = itemCheck(check, resultSet);
                        list.add(check);
                    }
                }
            }
        } catch (SQLException e) {
               e.printStackTrace();
        }
        return list;
    }


    private PreparedStatement statement(PreparedStatement statement, Check item) throws SQLException {
        statement.setInt(1, item.getCheckDate());
        statement.setInt(2, item.getStatus());
        statement.setString(3, item.getReason());
        statement.setInt(4, item.getPerson().getId());
        statement.setDate(5, new Date(Calendar.getInstance().getTime().getTime()));
        statement.setDate(6, new Date(Calendar.getInstance().getTime().getTime()));



        return statement;
    }

    private Check itemCheck(Check check, ResultSet resultSet) throws SQLException {
        check = new Check();
        check.setId(resultSet.getInt(1));
        check.setCheckDate(resultSet.getInt(2));
        check.setStatus(resultSet.getInt(3));
        check.setReason(resultSet.getString(4));

        return check;
    }
}
