package Test;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Created by Grzmot22 on 2017-04-23.
 */
public class Hotel {

    private int reservationId;
    private int roomNo;
    private int userId;
    private boolean reservationIdAlreadyExist;
    private Date startDate = null;
    private Date endDate = null;
    private int paidStatus;
    private String roomType = null;
    private boolean roomAlreadyBooked;
    private ReservationJDBC jdbc = new ReservationJDBC();

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


    public String checkBooking() {

        jdbc.setConnection();
        StringBuilder book = new StringBuilder();
        try {

            jdbc.statement = jdbc.con.createStatement();
            jdbc.resultSet = jdbc.statement.executeQuery("SELECT * FROM `reservations` WHERE reservation_id = '" + reservationId + "' ORDER BY reservation_id");
                while (jdbc.resultSet.next()) {

                    ResultSetMetaData metaData = jdbc.resultSet.getMetaData();

                    int numOfCol = metaData.getColumnCount();
                    for (int i = 1; i < numOfCol; i++) {

                        book.append(jdbc.resultSet.getObject(i).toString()).append('\t');
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

        jdbc.setConnection();

        try {

            jdbc.statement = jdbc.con.createStatement();
            jdbc.resultSet = jdbc.statement.executeQuery("SELECT * FROM `reservations` ORDER BY reservation_id");
            int checkReservationId = -1;
            boolean status = checkReservationId == reservationId;

            while (!(checkReservationId == reservationId) ){

                jdbc.resultSet.next();
                checkReservationId = jdbc.resultSet.getInt("reservation_id");

            }
            return status;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void createBooking(int userId, String roomType, Date startDate, Date endDate, int paidStatus) {

        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paidStatus = paidStatus;
        this.roomType = roomType;

        jdbc.setConnection();
        createReservation(userId, roomType);
    }
    public void createBooking(String roomType, Date startDate, Date endDate, int paidStatus) {

        this.startDate = startDate;
        this.endDate = endDate;
        this.paidStatus = paidStatus;
        this.roomType = roomType;

        jdbc.setConnection();
        createReservation(this.userId, roomType);
    }
    public void createBooking(int roomNo, Date startDate, Date endDate, int paidStatus) {

        this.startDate = startDate;
        this.endDate = endDate;
        this.paidStatus = paidStatus;
        this.roomType = roomType;
        this.roomNo = roomNo;

        jdbc.setConnection();
        createReservation(this.userId, roomType);

    }
    private void createReservation(int userId, String roomType){

        try {
//            reservationIdAlreadyExist = false;
            reservationExist();
            jdbc.statement = jdbc.con.createStatement();
            jdbc.resultSet = jdbc.statement.executeQuery("SELECT * FROM `reservations` ORDER BY reservation_id");
            if (reservationId == 0) {
                reservationId = 1;
                while (jdbc.resultSet.next()) {

                    int currentReservationId = jdbc.resultSet.getInt("reservation_id");

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
                        jdbc.statement.executeUpdate("INSERT INTO `reservations` (`reservation_id`, `room_no`, `user_id`, `start_date`,`end_date`,`paid_status`) " +
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



    public String getBookedRooms() {

        jdbc.setConnection();
        StringBuilder rooms = new StringBuilder();
        try {

            jdbc.statement = jdbc.con.createStatement();
            jdbc.resultSet = jdbc.statement.executeQuery("SELECT * FROM `rooms` WHERE booked = 1 ORDER BY room_no");

            ResultSetMetaData metaData = jdbc.resultSet.getMetaData();
            int numOfCol = metaData.getColumnCount();

            while (jdbc.resultSet.next()) {

                for (int i = 1; i < numOfCol; i++) {

                    rooms.append(jdbc.resultSet.getObject(i).toString()).append('\t');

                }
                rooms.append('\n');
            }
//            System.out.println(rooms.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms.toString();
    }

    public String getFreeRooms() {

        jdbc.setConnection();
        StringBuilder rooms = new StringBuilder();
        try {

            jdbc.statement = jdbc.con.createStatement();
            jdbc.resultSet = jdbc.statement.executeQuery("SELECT * FROM `rooms` WHERE status = 'free' ORDER BY room_no");

            ResultSetMetaData metaData = jdbc.resultSet.getMetaData();
            int numOfCol = metaData.getColumnCount();

            while (jdbc.resultSet.next()) {

                for (int i = 1; i < numOfCol; i++) {

                    rooms.append(jdbc.resultSet.getObject(i).toString()).append('\t');

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

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    private void bookRoom(String roomType) {
        try {
            roomAlreadyBooked = false;
            jdbc.statement = jdbc.con.createStatement();
            jdbc.resultSet = jdbc.statement.executeQuery("SELECT * FROM `rooms` WHERE type ='"+roomType+"' AND booked = 0 ORDER BY room_no");
            roomNo = 100;

            while (jdbc.resultSet.next()){

                int currentRoomNo = jdbc.resultSet.getInt("room_no");

                if (currentRoomNo > 100){
                    if (roomNo != currentRoomNo){
                        roomNo = currentRoomNo;

                        jdbc.statement.executeUpdate("UPDATE `rooms` SET `booked` = '1', `reservation_id` = '"+reservationId+"' WHERE room_no = '"+roomNo+"'");
                        System.out.println("Room has been booked \n  Room No: "+roomNo+"\n Room type: "+roomType+"\n reservation ID: "+reservationId);

                    }else if (roomNo == currentRoomNo && roomNo != 100){

                        jdbc.statement.executeUpdate("UPDATE `rooms` SET `booked` = '1', `reservation_id` = '"+reservationId+"' WHERE room_no = '"+roomNo+"'");
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
            jdbc.statement = jdbc.con.createStatement();
            if (roomType == null){

                jdbc.resultSet = jdbc.statement.executeQuery("SELECT * FROM `rooms` WHERE  booked = 0 ORDER BY room_no");

            } else {

                jdbc.resultSet = jdbc.statement.executeQuery("SELECT * FROM `rooms` WHERE type ='"+roomType+"' AND booked = 0 ORDER BY room_no");

            }


            while (jdbc.resultSet.next()){

                int currentRoomNo = jdbc.resultSet.getInt("room_no");

                if (currentRoomNo == roomNo){

                    System.out.println("Room " + roomNo + " is free");

                    jdbc.resultSet = jdbc.statement.executeQuery("SELECT * FROM `rooms` WHERE  room_no = "+roomNo+" ORDER BY room_no");
                    jdbc.resultSet.next();
                    this.roomType = jdbc.resultSet.getObject("type").toString();
                    roomType = this.roomType;
                    this.roomNo = roomNo;

                    roomAlreadyBooked = true;

                    jdbc.statement.executeUpdate("UPDATE `rooms` SET `booked` = '1', `reservation_id` = '"+reservationId+"' WHERE `rooms`.`room_no` = '"+roomNo+"'");
                    System.out.println("Room has been booked \n  Room No: "+roomNo+"\n Room type: "+roomType+"\n reservation ID: "+reservationId);

                }
            }

            if (roomType == null){

                jdbc.resultSet = jdbc.statement.executeQuery("SELECT * FROM `rooms` WHERE  booked = 1 ORDER BY room_no");

            } else {

                jdbc.resultSet = jdbc.statement.executeQuery("SELECT * FROM `rooms` WHERE type ='"+roomType+"' AND booked = 1 ORDER BY room_no");
            }
                while (jdbc.resultSet.next()){

                    int currentRoomNo = jdbc.resultSet.getInt("room_no");

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
