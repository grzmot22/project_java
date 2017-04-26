package Test;

import Demos.NewAccountServer.Account;
import Demos.NewAccountServer.AccountJDBC;
import Demos.NewAccountServer.AccountServer;
import Test.gui.Runner;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Grzmot22 on 2017-04-23.
 */
public class ReservationServer {

    private ServerSocket serverSocket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private Socket con;
    private int counter = 1;
    private Hotel hotel;
    private Login login;
    private ReservationJDBC jdbc;

    public ReservationServer(){

        try {
            serverSocket = new ServerSocket(1024);

            while (true) {
                try {
                    System.out.println("Waiting for con\n");
                    con = serverSocket.accept();
                    System.out.println("Connection " + counter + " received from: " + con.getInetAddress().getHostName());
                    outputStream = new ObjectOutputStream(con.getOutputStream());
                    outputStream.flush();
                    inputStream = new ObjectInputStream(con.getInputStream());

                    new Runner();

                    processConnection();
                    jdbc.closeConnection();
                } catch (EOFException eofException) {
                    System.out.println("\nServer terminated con");
                } finally {
                    closeConnection();
                    counter++;
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

//    private void

    private void processConnection() throws IOException {
        String message = "";
        try {
            message = (String) inputStream.readObject();
            String[] st = message.split("\\s");
            if (st.length > 0) {

                if (st[0].equals("createUser")){
                    String user = st[1];
                    String pass = st[2];
                    String log = login.createUser(user,pass);
                    sendData(log);
                }

                if ((st[0].equals("login"))){
                    String user = st[1];
                    String pass = st[2];
                    String log = login.signIn();
                    sendData(log);
                }

            }
        } catch (ClassNotFoundException classNotFoundException) {
            System.out.println("\nUnknown object type received");
        }

    }


    private void closeConnection() {
        try {
            outputStream.close();
            inputStream.close();
            con.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }


    private void sendData(String message) {
        try {
            outputStream.writeObject(message);
            outputStream.flush();
            System.out.println(message);
        } catch (IOException ioException) {
            System.out.println("\nError writing object");
        }
    }

    public static void main( String args[] )
    {
        ReservationServer server = new ReservationServer();
    }
}

