package Test;

import javax.swing.*;
import java.sql.*;

/**
 * Created by Grzmot22 on 2017-04-25.
 */
public class ReservationJDBC {

    // MySQL database stuff
    private static final String DRIVER = "com.mysql.jdbc.Driver"; // JDBC driver
    private static final String DATABASE = "hotel"; // the database name
    //    static final String HOST = "10.1.63.200"; // the database host IP
    private static final String HOST = "localhost"; // the database host IP
    private static final String DATABASE_URL =
            "jdbc:mysql://" + HOST + "/" + DATABASE;
    private String password = null;
    private String user = null;

    protected Connection con = null;
    protected Statement statement;
    protected ResultSet resultSet;

    public ReservationJDBC()
    {
        this("","");
    }

    public ReservationJDBC(String userName, String passWd)
    {

        user = JOptionPane.showInputDialog("Please enter your user name");
        password = JOptionPane.showInputDialog("Please enter your password");

        setConnection();
    }

    protected void setConnection() {
        // connect to database
        try {
            // load the driver class
            Class.forName(DRIVER);
            // establish con to database
            con = DriverManager.getConnection(DATABASE_URL, "root", "");
//            con = DriverManager.getConnection(DATABASE_URL, user, password);
        } catch (SQLException | ClassNotFoundException sqlException) {
            sqlException.printStackTrace();
        } // end catch
        // end catch

    }
    protected void closeConnection() {
        // ensure resultSet, statement and con are closed
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

            if(con != null)
            {
                con.close();
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
