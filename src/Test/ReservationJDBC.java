package Test;

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

    protected Connection con = null;
    protected Statement statement;
    protected ResultSet resultSet;

    protected void setConnection() {
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
//    protected void closeConnection() {
//        // ensure resultSet, statement and connection are closed
//        try
//        {
//            if(statement != null)
//            {
//                statement.close();
//            }
//
//            if(resultSet != null)
//            {
//                resultSet.close();
//            }
//
//            if(con != null)
//            {
//                con.close();
//            }
//
//        } // end try
//        catch (Exception exception)
//        {
//            exception.printStackTrace();
//        } // end catch
//
//    }
}
