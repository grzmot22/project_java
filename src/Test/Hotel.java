package Test;

import java.sql.*;
import java.sql.Date;
import java.util.*;

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
    private ResultSet resultSet;
    private boolean reservationIdAlreadyExist;
    private Date startDate;
    private Date endDate;
    private int paidStatus;

    public Hotel(int userId,  int reservationId, int roomNo) {

        this.userId = userId;
        this.reservationId = reservationId;
        this.roomNo = roomNo;

    }

    public Hotel(int userId, int roomNo) {
        this.userId = userId;
        this.roomNo = roomNo;
    }

    public Hotel (int userId,  int reservationId, int roomNo, Date startDate, Date endDate, int paidStatus) {

        this.userId = userId;
        this.reservationId = reservationId;
        this.roomNo = roomNo;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paidStatus = paidStatus;

    }

    public Hotel (int userId, int roomNo, Date startDate, Date endDate, int paidStatus) {

        this.userId = userId;
        this.roomNo = roomNo;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paidStatus = paidStatus;

    }


    private String checkBooking() {

        setConnection();
        StringBuilder book = new StringBuilder();
        try {

            statement = con.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM `reservations` WHERE reservation_id = '" + reservationId + "'");
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

    private boolean reservationExist(){

        try {

            setConnection();

            statement = con.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM `reservations` ");
            int checkReservationId = -1;
            boolean status = checkReservationId == reservationId;

            while (!(checkReservationId == reservationId) ){

                resultSet.next();
                checkReservationId = resultSet.getInt("reservation_id");

            }
            return status;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    private void createBooking(int userId, Date startDate, Date endDate, int paidStatus) {

        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paidStatus = paidStatus;

        setConnection();
        createReservation(userId);
    }
    private void createBooking(Date startDate, Date endDate, int paidStatus) {

        this.startDate = startDate;
        this.endDate = endDate;
        this.paidStatus = paidStatus;

        setConnection();
        createReservation(this.userId);
    }
    private void createReservation(int userId){

        try {
            reservationIdAlreadyExist = false;
            reservationExist();
            statement = con.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM `reservations`");
            reservationId = 1;

            while (resultSet.next()){

                int currentReservationId = resultSet.getInt("reservation_id");

                if (currentReservationId > 0){
                    if (reservationId == currentReservationId){
                        reservationId = currentReservationId + 1;
                    }
                }
            }

            if (reservationExist()){
                reservationIdAlreadyExist = true;
                System.out.println("Login already exist");

            } else if (!reservationExist() && !reservationIdAlreadyExist){

                statement.executeUpdate("INSERT INTO `reservations` (`reservation_id`, `room_no`, `user_id`, `start_date`,`end_date`,`paid_status`) " +
                        "VALUES ('"+reservationId+"', '"+roomNo+"', '"+userId+"', '"+startDate+"', '"+endDate+"', '"+paidStatus+"')");
                System.out.println("Account has been created \n Login: "+reservationId+"\n Room No: "+roomNo+"\n UserId: "+userId
                        +"\n Start date : "+startDate+"\n End date: "+endDate +"\n Payment status: "+paidStatus);

            }

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

        Hotel test = new Hotel(100001,204);
//        test.checkBooking();)
        test.createBooking(1, Date.valueOf("2017-04-30"),Date.valueOf("2017-05-19"),1);
//        System.out.println(test.reservationExist());

    }
}
