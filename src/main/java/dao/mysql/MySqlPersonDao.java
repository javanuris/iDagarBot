package dao.mysql;


import connection.ConnectionDB;
import entity.BaseEntity;
import entity.Person;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by User on 01.07.2017.
 */
public class MySqlPersonDao {

    private static final String FIND_BY_ID = "SELECT * FROM person_info WHERE person_id = ?";
    private static final String FIND_BY_TELEGRAM_ID = "SELECT * FROM person_info WHERE telegram_id = ?";
    private static final String INSERT = "INSERT INTO person_info VALUES(person_id,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE person_info SET telegram_id = ? ,first_name = ?,last_name = ?,check_date = ?, status = ? WHERE person_id = ?";

    public BaseEntity insert(Person item) {
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

    public BaseEntity findById(int id) {
        Person person = null;
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            try (PreparedStatement statement = connectionDB.getConnection().prepareStatement(FIND_BY_ID)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        person = itemAuthor(person, resultSet);
                    }
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
        return person;
    }

    public void update(Person item) {
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            try (PreparedStatement statement = connectionDB.getConnection().prepareStatement(UPDATE)) {
                statement(statement, item);
                statement.setInt(6, item.getId());
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

    public BaseEntity findByTelegramId(int id) {
        ConnectionDB connectionDB = new ConnectionDB();

        Person person = null;
        try {
            try (PreparedStatement statement = connectionDB.getConnection().prepareStatement(FIND_BY_TELEGRAM_ID)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        person = itemAuthor(person, resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connectionDB.getConnection().close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return person;
    }

    private PreparedStatement statement(PreparedStatement statement, Person item) throws SQLException {
        statement.setInt(1, item.getTelegramId());
        statement.setString(2, item.getFirstName());
        statement.setString(3, item.getLastName());
        statement.setInt(4, item.getCheckDate());
        statement.setInt(5, item.getStatus());
        return statement;
    }

    private Person itemAuthor(Person person, ResultSet resultSet) throws SQLException {
        person = new Person();
        person.setId(resultSet.getInt(1));
        person.setTelegramId(resultSet.getInt(2));
        person.setFirstName(resultSet.getString(3));
        person.setLastName(resultSet.getString(4));
        person.setCheckDate(resultSet.getInt(5));
        person.setStatus(resultSet.getInt(6));
        return person;
    }
}
