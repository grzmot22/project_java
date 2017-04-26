package Test.gui.controler;

import Test.Hotel;
import Test.Login;
import Test.gui.view.ReservationClient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Grzmot22 on 2017-04-25.
 */
public class ReservationClientController {
    private ReservationClient mFrame;
    private JTextField userIDTextField;
    private JTextField reservationIDTextField;
    private JTextField roomNoTextField;
    private JTextField startDateTextField;
    private JTextField endDateTextField;
    private JTextField paidStatusTextField;
    private JButton createBookingButton;
    private JButton bookedRoomsButton;
    private JButton freeRoomsButton;
    private JButton checkMyBookingButton;
    private JLabel userIdLtl;
    private JTextArea area;
    private Login login;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String message = "";
    private String server;
    private Socket client;
    protected boolean adminMode;


    public ReservationClientController() {
        initComponents();
        initListeners();
    }

    public ReservationClientController showMainFrameWindow(boolean adminMode,int userId) {
        this.adminMode = adminMode;
        String sUserId = String.valueOf(userId);

        if (adminMode){
            initComponents();
            mFrame.setTitle("Administrator panel Reservation System");
            userIdLtl.setText("User ID: " + sUserId);
            mFrame.setVisible(true);

//            runClient();
        }else {
            userIDTextField.setText(sUserId);
            userIdLtl.setText("User ID: " + sUserId);
            this.adminMode = false;

            mFrame.setVisible(true);
//            runClient();

        }

        return null;
    }

    private void initComponents() {
        mFrame = new ReservationClient();

        userIDTextField = mFrame.getUserIDTextField();
        endDateTextField = mFrame.getEndDateTextField();
        paidStatusTextField = mFrame.getPaidStatusTextField();
        reservationIDTextField = mFrame.getReservationIDTextField();
        roomNoTextField = mFrame.getRoomNoTextField();
        startDateTextField = mFrame.getStartDateTextField();

        bookedRoomsButton = mFrame.getBookedRoomsButton();
        createBookingButton = mFrame.getCreateBookingButton();
        freeRoomsButton = mFrame.getFreeRoomsButton();
        checkMyBookingButton = mFrame.getCheckMyBookingButton();

        userIDTextField.setEditable(adminMode);
        freeRoomsButton.setVisible(adminMode);
        bookedRoomsButton.setVisible(adminMode);
        area = mFrame.getArea();
        userIdLtl = mFrame.getUserIdLtl();


    }

    private void initListeners() {

        checkMyBookingButton.addActionListener(new checkMyBookingButtonListener());
        freeRoomsButton.addActionListener(new freeRoomsButtonLister());
        createBookingButton.addActionListener(new createBookingButtonListener());
        bookedRoomsButton.addActionListener(new bookedRoomsButtonListener());

    }
    private class checkMyBookingButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            message = reservationIDTextField.getText();

//            Hotel hotel = new Hotel(message,bal);
//            acc.setCreditLimit(2*bal);

//            message = acc.toString();
            sendData("createBooking "+message);
        }
    }

    private class freeRoomsButtonLister implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class createBookingButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class bookedRoomsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
    public void runClient()
    {
        while(!message.equals("EXIT"))
        {
            try
            {
                connectToServer();
                getStreams();
                processConnection();
            }
            catch ( EOFException eofException )
            {
                area.append( "\nClient terminated con" );
            }
            catch ( IOException ioException )
            {
                ioException.printStackTrace();
            }
        }
    }

    public void sendLogin(String message){
        try
        {
            output.writeObject(message );
            output.flush();
            area.append( message );
        }
        catch ( IOException ioException )
        {
            area.append( "\nError writing object" );
        }
    }

    private void connectToServer() throws IOException
    {
        area.append( "Attempting con\n" );

        client = new Socket( InetAddress.getByName( server ), 1024 );

        area.append( "Connected to: " + client.getInetAddress().getHostName() );
    }


    private void getStreams() throws IOException
    {

        output = new ObjectOutputStream( client.getOutputStream() );
        output.flush();

        input = new ObjectInputStream( client.getInputStream() );

        area.append( "\nGot I/O streams\n" );
    }


    private void processConnection() throws IOException
    {
        try
        {
            message = ( String ) input.readObject();
            area.append( "\n" + message );
        }
        catch ( ClassNotFoundException classNotFoundException )
        {
            area.append( "\nUnknown object type received" );
        }
    }


    private void closeConnection()
    {
        area.append( "\nClosing connection" );
        try
        {
            output.close();
            input.close();
            client.close();
        }
        catch ( IOException ioException )
        {
            ioException.printStackTrace();
        }
    }


    private void sendData( String message )
    {
        try
        {
            output.writeObject(message );
            output.flush();
            area.append( message );
        }
        catch ( IOException ioException )
        {
            area.append( "\nError writing object" );
        }

    }

//    getInfo.addActionListener(new ActionListener() {
//        public void actionPerformed(ActionEvent e) {
//            message = name.getText();
//            int bal = Integer.parseInt(balance.getText());
//
//            Account acc = new Account(message,bal);
//            acc.setCreditLimit(2*bal);
//
//            message = acc.toString();
//            sendData("createBooking "+message);
//
//        }
//    });
//        corrInfo.addActionListener(new ActionListener() {
//        public void actionPerformed(ActionEvent e) {
//            sendData("all");
//            area.append(message);
//        }
//    });
//        exitButton.addActionListener(new ActionListener() {
//        public void actionPerformed(ActionEvent e) {
//            closeConnection();
//            message = "EXIT";
//            //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        }
//    });
}



