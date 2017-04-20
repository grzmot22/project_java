package Demos;

import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.*;
import java.util.Scanner;

/**
 * The client
 * 
 * @author Ingo Frommholz 
 */
public class Client
{
    private Socket clientSocket;
    private PrintWriter output;
    private BufferedReader input;
    
    
    
    /**
     * Constructor for objects of class Client
     */
    public Client(String host, int port)
    {
        try{
            // connect to server
            clientSocket = new Socket(InetAddress.getByName(host), port);
        
            // get streams
            output = new PrintWriter(clientSocket.getOutputStream());
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
            Scanner keyboard = new Scanner(System.in);
            String keyboardInput;
            do {
                keyboardInput = keyboard.next();
                // send input to server
                output.println(keyboardInput); // you must use println()!
                output.flush();
            
                // Retrieve and print messages from server (until we read the token "_END_").
                String message;
                while (!(message = input.readLine()).equals(Server.END_MESSAGE)) {
                    System.out.println(message);
                }
            }
            while (!keyboardInput.equals("close")); 
            clientSocket.close();
        }
        catch (UnknownHostException uhe) {
            uhe.printStackTrace();
        }
        catch (IOException io) {
            io.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        String host = args[0];
//        int port = Integer.parseInt(args[1]);
        String host = "localhost";
        int port = 20405;
        new Client(host,port);
    }
}
