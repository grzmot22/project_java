package Demos;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.io.*;
import java.sql.*;

/**
 * An example server that understands simple commands to fetch data from the database
 * 
 * @author Ingo Frommholz
 */
public class Server
{
    // the server socket
    private ServerSocket serverSocket;

    // the client socket
    private Socket clientSocket;

    // the output stream
    private PrintWriter output = null;

    // the input stream
    private BufferedReader input = null;

    // MySQL database stuff
    static final String DRIVER = "com.mysql.jdbc.Driver"; // JDBC driver
    static final String DATABASE = "test"; // the database name
//    static final String HOST = "10.1.63.200"; // the database host IP
    static final String HOST = "localhost"; // the database host IP
    static final String DATABASE_URL = 
        "jdbc:mysql://" + HOST + "/" + DATABASE;
    Connection con = null;

    public static final String END_MESSAGE = "__END__"; // the string denoting the end of a message

    /**
     * Constructor for objects of class Server
     */
    public Server(int port)
    {
        try {
            Class.forName(DRIVER);
            serverSocket = new ServerSocket(port);

            while(true) {
                try {
                    clientSocket = serverSocket.accept(); // waits until a client connects

                    System.out.println(clientSocket.getLocalAddress());

                    // gets the streams
                    output = new PrintWriter(clientSocket.getOutputStream());

                    // a BufferedReader provides a readLine() method, which is very convenient for us
                    input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    while(true) {
                        String command = input.readLine();
                        System.out.println("Got '" + command + "'");

                        if (command.equals("close")) {
                            // close the streams and the connections
                            display("Goodbye!");
                            try {
                                // close database connection if there
                                if (con != null) con.close();
                                con = null;
                            }
                            catch (SQLException sqlex) {
                                sqlex.printStackTrace();
                            }
                            clientSocket.close();
                            break;
                        }
                        else if (command.equals("hello")) {
                            // say hello
                            display("Hello client!");
                        }
                        else if (command.equals("connect")) {
                            // the next 2 lines should be username and password
                            display("Please enter your username!");
                            String username = input.readLine();
                            display("Please enter your password!");
                            String password = input.readLine();
                            try {
                                con = DriverManager.getConnection(DATABASE_URL,username,password);
                                display("Connected.");
                            }
                            catch (SQLException sqlex) {
                                display("Could not connect: " + sqlex);
                                sqlex.printStackTrace();
                            }
                        }
                        else if (command.equals("showauthors")) {
                            if (con == null) display("Not connected");
                            else {
                                try {
                                    Statement statement = con.createStatement();
                                    ResultSet resultSet =
                                        statement.executeQuery("select FirstName, LastName from Authors");

                                    // process query results
                                    ResultSetMetaData metaData = resultSet.getMetaData();
                                    int numberOfColumns = metaData.getColumnCount();     
                                    String results = "Authors:\n";

                                    // print the column headers
                                    for (int i = 1; i <= numberOfColumns; i++)
                                    {
                                        results += metaData.getColumnName(i) + '\t' ; 
                                    }
                                    results += '\n';

                                    // extract the results
                                    while (resultSet.next()) 
                                    {
                                        for (int i = 1; i <= numberOfColumns; i++)
                                        {
                                            results += resultSet.getObject(i).toString()  + '\t';
                                        }
                                        results += '\n';
                                    }
                                    display(results);
                                }
                                catch (SQLException s) {
                                    display("There was a problem: " + s);
                                    s.printStackTrace();
                                }
                            }
                        }
                        else {
                            display("Sorry I don't understand you.");
                        }
                    }
                }
                catch (SocketException s) {
                    System.out.println("Connection reset.");
                }
            }
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }
        catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }

    }

    /*
     * Send a string to the client, including the end message
     */
    private void display(String s) {
        output.println(s);
        output.println(END_MESSAGE);
        output.flush();
    }

    public static void main(String[] args) {
        int port = 20405;
        System.out.println("Starting server on port " + port + ".");
        Server myServer = new Server(port);
    }
}
