package Test;

import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.Objects;

/**
 * Created by Grzmot22 on 2017-04-23.
 */
public class Hotel {

    // MySQL database stuff
    private static final String DRIVER = "com.mysql.jdbc.Driver"; // JDBC driver
    private static final String DATABASE = "hotel"; // the database name
    //    static final String HOST = "10.1.63.200"; // the database host IP
    private static final String HOST = "localhost"; // the database host IP
    private static final String DATABASE_URL =
            "jdbc:mysql://" + HOST + "/" + DATABASE;
    private Connection con = null;

    private int reservationId;
    private int roomNo;
    private int userId;
    private Statement statement;

    public Hotel(int userId,  int reservationId,int roomNo) {
        this.userId = userId;
        this.reservationId = reservationId;
        this.roomNo = roomNo;
    }


    private String checkBooking() {

        setConnection();
        StringBuilder book = new StringBuilder();
        try {

            statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `reservations` WHERE reservation_id = '" + reservationId + "'");
                while (resultSet.next()) {

                    ResultSetMetaData metaData = resultSet.getMetaData();

                    int numOfCol = metaData.getColumnCount();
                    for (int i = 1; i < numOfCol; i++) {

                        book.append(resultSet.getObject(i).toString()).append('\t');
    //                    System.out.println(book.toString());
                    }

                }
                if (Objects.equals(book.toString(), "")){

                    return "reservation id not exist";

                }else {

                    return book.toString();

                }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book.toString();
    }

    private void createBooking() {

        setConnection();
        try {
            Statement st = con.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private String getBookedRooms() {
        return "test";
    }

    private void getFreeRooms() {
    }

    private void bookRoom() {
    }

    private void reservationStatus() {
    }

    private void setConnection() {
        // connect to database
        try {
            // load the driver class
            Class.forName(DRIVER);
            // establish connection to database
            con = DriverManager.getConnection(DATABASE_URL, "root", "");
        } catch (SQLException | ClassNotFoundException sqlException) {
            sqlException.printStackTrace();
        } // end catch
        // end catch

    }
    public static void main(String[] args) {

        Hotel test = new Hotel(100001,20,101);
        test.checkBooking();
    }
}
