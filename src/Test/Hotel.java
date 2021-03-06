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
    private Statement statement = null;
    private ResultSet resultSet = null;
    private boolean reservationIdAlreadyExist;
    private Date startDate = null;
    private Date endDate = null;
    private int paidStatus;
    private String roomType = null;
    private boolean roomAlreadyBooked;

    public Hotel(int userId,  int reservationId, int roomNo) {

        this.userId = userId;
        this.reservationId = reservationId;
        this.roomNo = roomNo;

    }
    public Hotel() {
        roomNo = 100;
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
            resultSet = statement.executeQuery("SELECT * FROM `reservations` WHERE reservation_id = '" + reservationId + "' ORDER BY reservation_id");
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

        setConnection();

        try {

            statement = con.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM `reservations` ORDER BY reservation_id");
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
    private void createBooking(int userId, String roomType, Date startDate, Date endDate, int paidStatus) {

        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paidStatus = paidStatus;
        this.roomType = roomType;

        setConnection();
        createReservation(userId, roomType);
    }
    private void createBooking(String roomType, Date startDate, Date endDate, int paidStatus) {

        this.startDate = startDate;
        this.endDate = endDate;
        this.paidStatus = paidStatus;
        this.roomType = roomType;

        setConnection();
        createReservation(this.userId, roomType);
    }
    private void createBooking(int roomNo, Date startDate, Date endDate, int paidStatus) {

        this.startDate = startDate;
        this.endDate = endDate;
        this.paidStatus = paidStatus;
        this.roomType = roomType;
        this.roomNo = roomNo;

        setConnection();
        createReservation(this.userId, roomType);

    }
    private void createReservation(int userId, String roomType){

        try {
//            reservationIdAlreadyExist = false;
            reservationExist();
            statement = con.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM `reservations` ORDER BY reservation_id");
            if (reservationId == 0) {
                reservationId = 1;
                while (resultSet.next()) {

                    int currentReservationId = resultSet.getInt("reservation_id");

                    if (currentReservationId > 0) {
                        if (reservationId == currentReservationId) {
                            reservationId = currentReservationId + 1;
                        }
                    }
                }
            }
            if (reservationExist()){
                reservationIdAlreadyExist = true;
                System.out.println("Reservation already exist");

                } else if (!reservationExist() && !reservationIdAlreadyExist){

                    if(!roomAlreadyBooked){

                        if(roomNo != 100){
                            bookRoom(roomType, roomNo);
                        } else {
                            bookRoom(roomType);
                        }

                    }

                    if (roomNo != 100 && this.roomType != null && !reservationIdAlreadyExist){
                        reservationIdAlreadyExist = true;
                        statement.executeUpdate("INSERT INTO `reservations` (`reservation_id`, `room_no`, `user_id`, `start_date`,`end_date`,`paid_status`) " +
                                "VALUES ('"+reservationId+"', '"+roomNo+"', '"+userId+"', '"+startDate+"', '"+endDate+"', '"+paidStatus+"')");
                        System.out.println("Reservation has been created \n Reservation ID: "+reservationId+"\n Room No: "+roomNo+"\n UserId: "+userId
                                +"\n Start date : "+startDate+"\n End date: "+endDate +"\n Payment status: "+paidStatus);
                    } else if (!reservationIdAlreadyExist){
                        System.out.println("All " + roomType + " rooms booked");
                    }

                }

        } catch (SQLException e) {

            e.printStackTrace();

        }
    }



    private String getBookedRooms() {

        setConnection();
        StringBuilder rooms = new StringBuilder();
        try {

            statement = con.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM `rooms` WHERE booked = 1 ORDER BY room_no");

            ResultSetMetaData metaData = resultSet.getMetaData();
            int numOfCol = metaData.getColumnCount();

            while (resultSet.next()) {

                for (int i = 1; i < numOfCol; i++) {

                    rooms.append(resultSet.getObject(i).toString()).append('\t');

                }
                rooms.append('\n');
            }
//            System.out.println(rooms.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms.toString();
    }

    private String getFreeRooms() {

        setConnection();
        StringBuilder rooms = new StringBuilder();
        try {

            statement = con.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM `rooms` WHERE status = 'free' ORDER BY room_no");

            ResultSetMetaData metaData = resultSet.getMetaData();
            int numOfCol = metaData.getColumnCount();

            while (resultSet.next()) {

                for (int i = 1; i < numOfCol; i++) {

                    rooms.append(resultSet.getObject(i).toString()).append('\t');

                }
                rooms.append('\n');
            }
//            System.out.println(rooms.toString());

            return rooms.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms.toString();

    }

    private void bookRoom(String roomType) {
        try {
            roomAlreadyBooked = false;
            statement = con.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM `rooms` WHERE type ='"+roomType+"' AND booked = 0 ORDER BY room_no");
            roomNo = 100;

            while (resultSet.next()){

                int currentRoomNo = resultSet.getInt("room_no");

                if (currentRoomNo > 100){
                    if (roomNo != currentRoomNo){
                        roomNo = currentRoomNo;

                        statement.executeUpdate("UPDATE `rooms` SET `booked` = '1', `reservation_id` = '"+reservationId+"' WHERE room_no = '"+roomNo+"'");
                        System.out.println("Room has been booked \n  Room No: "+roomNo+"\n Room type: "+roomType+"\n reservation ID: "+reservationId);

                    }else if (roomNo == currentRoomNo && roomNo != 100){

                        statement.executeUpdate("UPDATE `rooms` SET `booked` = '1', `reservation_id` = '"+reservationId+"' WHERE room_no = '"+roomNo+"'");
                        System.out.println("Room has been booked \n  Room No: "+roomNo+"\n Room type: "+roomType+"\n reservation ID: "+reservationId);

                    }

                }else if (roomNo == 100) {
                    System.out.println("All " + roomType + " rooms booked");
                }
            }


        } catch (SQLException e) {

            e.printStackTrace();

        }
    }

    private void bookRoom(String roomType, int roomNo) {
        try {
            roomAlreadyBooked = false;
            statement = con.createStatement();
            if (roomType == null){

                resultSet = statement.executeQuery("SELECT * FROM `rooms` WHERE  booked = 0 ORDER BY room_no");

            } else {

                resultSet = statement.executeQuery("SELECT * FROM `rooms` WHERE type ='"+roomType+"' AND booked = 0 ORDER BY room_no");

            }


            while (resultSet.next()){

                int currentRoomNo = resultSet.getInt("room_no");

                if (currentRoomNo == roomNo){

                    System.out.println("Room " + roomNo + " is free");

                    resultSet = statement.executeQuery("SELECT * FROM `rooms` WHERE  room_no = "+roomNo+" ORDER BY room_no");
                    resultSet.next();
                    this.roomType = resultSet.getObject("type").toString();
                    roomType = this.roomType;
                    this.roomNo = roomNo;

                    roomAlreadyBooked = true;

                    statement.executeUpdate("UPDATE `rooms` SET `booked` = '1', `reservation_id` = '"+reservationId+"' WHERE `rooms`.`room_no` = '"+roomNo+"'");
                    System.out.println("Room has been booked \n  Room No: "+roomNo+"\n Room type: "+roomType+"\n reservation ID: "+reservationId);

                }
            }

            if (roomType == null){

                resultSet = statement.executeQuery("SELECT * FROM `rooms` WHERE  booked = 1 ORDER BY room_no");

            } else {

                resultSet = statement.executeQuery("SELECT * FROM `rooms` WHERE type ='"+roomType+"' AND booked = 1 ORDER BY room_no");
            }
                while (resultSet.next()){

                    int currentRoomNo = resultSet.getInt("room_no");

                    if (currentRoomNo == roomNo){
                        roomAlreadyBooked = true;
                        System.out.println("Room "+ roomNo +" already booked");
                        System.out.println("Try book different");
                        Scanner in = new Scanner(System.in);
                        try {

                            roomNo = Integer.parseInt(in.next());
                            while (roomNo == 100 || roomNo < 100 || roomNo > 410 || (110 < roomNo && roomNo < 200)
                                    || (210 < roomNo && roomNo < 300) || (310 < roomNo && roomNo < 400)){
                                System.out.println("Enter room No between 101 and 409");
                                roomNo = Integer.parseInt(in.next());
                            }
                            bookRoom(this.roomType,roomNo);
                        } catch (Exception e){

                            e.printStackTrace();
                            System.out.println("Enter room No between 101 and 409");

                        }
                        createReservation(this.userId,this.roomType);
                    }
                }
        } catch (SQLException e) {

            e.printStackTrace();

        }
    }

    private void reservationStatus(int reservationId) {

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
    public void closeConnection()
    {
        // ensure resultSet, statement and connection are closed
        try
        {
            if(statement != null)
            {
                statement.close();
            }

            if(resultSet != null)
            {
                resultSet.close();
            }

            if(con != null)
            {
                con.close();
            }

        } // end try
        catch (Exception exception)
        {
            exception.printStackTrace();
        } // end catch

    }
    public static void main(String[] args) {

        Hotel test = new Hotel(100001,405);
//        test.checkBooking();)
        test.createBooking(305, Date.valueOf("2017-04-30"),Date.valueOf("2017-05-19"),1);
//        test.bookRoom("single");
//        System.out.println(test.reservationExist());
//        test.getFreeRooms();
//        test.getBookedRooms();


    }
}
