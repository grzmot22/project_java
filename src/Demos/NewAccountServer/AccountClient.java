package Demos.NewAccountServer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class AccountClient extends JFrame
{
    private Container con;
    private JButton getInfo, corrInfo, exitButton;
    private JLabel balLabel, nameLabel;
     
    private JTextField name, balance;
    
    private JTextArea area;
    private JPanel panel1, panel2;

	private ObjectOutputStream output; 
	private ObjectInputStream input; 
	private String message = ""; 
	private String server; 
	private Socket client; 

    /**
     * Constructor for objects of class JFrame4
     */
    public AccountClient() {
        super("Account Client");
        con = getContentPane();
        panel1 = new JPanel();
        panel2 = new JPanel();        
        balLabel = new JLabel("Openning Balance:");
        nameLabel = new JLabel("Your name:");
        
        getInfo = new JButton("Add");
        corrInfo = new JButton("Retrieve");  
		exitButton = new JButton("Exit");
        name = new JTextField(20);
        balance = new JTextField(10);
         
        area = new JTextArea(); 
		//add( new JScrollPane( area ), BorderLayout.CENTER );

        GridLayout frameGrid = new GridLayout(2,1);
        GridLayout topGrid = new GridLayout(5,2);
        GridLayout bottomGrid = new GridLayout(1,1);        
        con.setLayout(frameGrid);
        panel1.setLayout(topGrid);
        panel2.setLayout(bottomGrid);        
        panel1.add(nameLabel);
        panel1.add(name);
        panel1.add(balLabel);
        panel1.add(balance);
       
        panel1.add(getInfo);   
        panel1.add(corrInfo);
		panel1.add(exitButton);
        panel2.add(area);  
		panel2.add(new JScrollPane(area));//,bottomGrid);
        con.add(panel1);
        con.add(panel2);  

        getInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                message = name.getText();
				int bal = Integer.parseInt(balance.getText());

				Account acc = new Account(message,bal);
				acc.setCreditLimit(2*bal);

				message = acc.toString();
				sendData("add "+message);

            }
        });
        corrInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				sendData("all");
				area.append(message);				
            }
        });
		exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				closeConnection();
				message = "EXIT";
				//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
        pack();
        setLocation(200,180);
        setSize(400, 300);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
				area.append( "\nClient terminated connection" );
			} 
			catch ( IOException ioException ) 
			{
				ioException.printStackTrace();
			} 
		}
   } 

   
   private void connectToServer() throws IOException
   {      
      area.append( "Attempting connection\n" );

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


    public static void main(String [] args) {
        AccountClient myCl = new AccountClient();
		myCl.runClient();
		//myCl.closeConnection(); 
    }
}
