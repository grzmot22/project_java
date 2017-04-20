package Demos.NewAccountServer; /**
*/
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;

public class  AccountJDBC  
{
    static final String DRIVER = "com.mysql.jdbc.Driver"; 
    static final String DATABASE = "bank"; // please re-name to your database
        static final String HOST = "localhost";//"10.1.63.200"; // the database host IP
        static final String DATABASE_URL = "jdbc:mysql://" + HOST + "/" + DATABASE;
            
    //static final String DATABASE_URL = "jdbc:mysql://localhost/";

    public Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    //String results = null;
    String password = null;
    String user = null;


    // launch the application
    public AccountJDBC()
    {
        this("","");
    }

    public AccountJDBC(String uN, String pWd)
    {
        
        user = JOptionPane.showInputDialog("Please enter your user name");
        password = JOptionPane.showInputDialog("Please enter your password");
        
        setConnection();
    }

    public void setConnection()
    {
        // connect to database 
        try 
        {
            // load the driver class
            Class.forName(DRIVER);
            // establish connection to database    
            connection =  DriverManager.getConnection(DATABASE_URL, user, password);
        }
        catch (SQLException sqlException)                                
        {                                                                  
            sqlException.printStackTrace();
        } // end catch                                                     
        catch (ClassNotFoundException classNotFound)                     
        {                                                                  
            classNotFound.printStackTrace();            
        } // end catch                             
    
    }

    public String getData()
    {
        String results="";
        try 
        {
            // create Statement for querying database
            statement = connection.createStatement();
             
            // query database                                        
            resultSet = statement.executeQuery("SELECT Name, Balance, CreditLimit FROM Account");
             
            // process query results
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();     
             
            while (resultSet.next()) 
            {
                for (int i=1;i<=numberOfColumns;i++)
                    results += resultSet.getObject(i).toString()+'\t';

                results += '\n';
            } // end while
        }  // end try
        catch (SQLException sqlException)                                
        {                                                                  
            sqlException.printStackTrace();
        } // end catch                                                     
     
        return results;
    }

    public void insertData(Account acc)
    {
        try 
        {
            // create Statement for querying database
            statement = connection.createStatement();            
            // query database                                        
            statement.executeUpdate("INSERT INTO Account (Name,Balance,CreditLimit) VALUES ('"+acc.getName()+"',"+acc.getBalance()+","+acc.getCreditLimit()+")");                       
        }  // end try
        catch (SQLException sqlException)                                
        {                                                                  
            sqlException.printStackTrace();
        } // end catch                                                       
    }
    public void closeConnection()
        {
         // ensure resultSet, statement and connection are closed                                                                    
            try                                                        
            {                                                          
                if(resultSet != null)
                {
                    resultSet.close();                                      
                }
                if(statement != null)
                {
                    statement.close();                                      
                }
                if(connection != null)
                {
                    connection.close();                                     
                }
            } // end try                                               
            catch (Exception exception)                              
            {                                                          
                exception.printStackTrace();                            
            } // end catch                                                
    }

    public static void main(String[] args) 
    {
        AccountJDBC accDB = new AccountJDBC();
        /*Vector vec = accDB.getData();
        

        for(int i=0; i<vec.size();i++) 
            System.out.println(((Account)vec.elementAt(i)).toString());
    */
        System.out.println(accDB.getData());
        accDB.insertData(new Account("Lim Neeson",10000));
        System.out.println(accDB.getData());
    /*
        vec = accDB.getData();
        for(int i=0; i<vec.size();i++) 
            System.out.println(((Account)vec.elementAt(i)).toString());
    */
        accDB.closeConnection();
    }
}
