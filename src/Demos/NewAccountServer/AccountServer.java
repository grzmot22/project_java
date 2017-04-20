package Demos.NewAccountServer;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class AccountServer
{
   private ObjectOutputStream output; 
   private ObjectInputStream input; 
   private ServerSocket server; 
   private Socket connection; 
   private int counter = 1; 
   private AccountJDBC dB;

   
   public AccountServer()
   {      
	  try 
      {
         server = new ServerSocket(1024 ); 

         while(true) 
         {
            try 
            {
				System.out.println( "Waiting for connection\n" );
				connection = server.accept();    
				System.out.println( "Connection " + counter + " received from: "+connection.getInetAddress().getHostName() );
				output = new ObjectOutputStream( connection.getOutputStream() );
				output.flush(); 
				input = new ObjectInputStream( connection.getInputStream() );
				dB = new AccountJDBC();

				processConnection(); 
				dB.closeConnection();
            } 
            catch ( EOFException eofException ) 
            {
               System.out.println( "\nServer terminated connection" );
            } 
            finally 
            {
               closeConnection(); 
               counter++;
            } 
         } 
      }
      catch ( IOException ioException ) 
      {
         ioException.printStackTrace();
      } 
      
   }
   
   private void processConnection() throws IOException
   {
		String message = "";
		try 
		{
			message = ( String ) input.readObject(); 
			String[] st = message.split("\\s");
			if (st.length > 0)
			{

				if(st[0].equals("all")) 
					sendData(dB.getData());
			
				if(st[0].equals("add")) {
					int bal = Integer.parseInt(st[2]);
					int cLim = Integer.parseInt(st[3]);
					Account acc = new Account(st[1],bal);
					acc.setCreditLimit(cLim);
					dB.insertData(acc);
				}
				if(st[0].equals("usr")) 
					dB = new AccountJDBC(st[1],st[2]);
			}
         } 
         catch ( ClassNotFoundException classNotFoundException ) 
         {
            System.out.println( "\nUnknown object type received" );
         } 
   } 

   
   private void closeConnection() 
   {
      try 
      {
         output.close(); 
         input.close(); 
         connection.close(); 
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
         output.writeObject( message );
         output.flush(); 
         System.out.println( message );
      } 
      catch ( IOException ioException ) 
      {
         System.out.println( "\nError writing object" );
      } 
   } 

   
   public static void main( String args[] )
   {
      AccountServer application = new AccountServer(); 
   } 
} 

