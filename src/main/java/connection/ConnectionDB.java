package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by User on 01.07.2017.
 */
public class ConnectionDB {

    private String user= "root";

    private String password = "root";

    private String driver= "com.mysql.jdbc.Driver";

    private String url = "jdbc:mysql://localhost:3306/telegrambotadmin?autoReconnect=true&useSSL=false";

    private Connection connection;

    public ConnectionDB()  {
        init();
    }

    private void init(){
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
           connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Connection getConnection() {
        return connection;
    }

}
