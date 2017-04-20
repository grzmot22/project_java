package Test;

import java.sql.*;

/**
 * Created by Grzmot22 on 2017-04-20.
 */
public class Login {
    private String login;
    private String password;
    private int userId;
    private boolean adminUser = false;

    // MySQL database stuff
    static final String DRIVER = "com.mysql.jdbc.Driver"; // JDBC driver
    static final String DATABASE = "hotel"; // the database name
    //    static final String HOST = "10.1.63.200"; // the database host IP
    static final String HOST = "localhost"; // the database host IP
    static final String DATABASE_URL =
            "jdbc:mysql://" + HOST + "/" + DATABASE;
    public Connection con = null;

    public Login (String login, String password) {
        this.login = login;
        this.password = password;
        userId = 999999;
    }

    private boolean checkUser(){
        try {
            setConnection();
            Statement st = con.createStatement();

            ResultSet log = st.executeQuery("SELECT `Login`, `user_id`,`admin` FROM `user` WHERE Login = '"+login+"'  LIMIT 1");
            log.next();

            if (login.matches(log.getString("login"))){

                System.out.println(log.getString("login"));

                if(Integer.parseInt(log.getString("admin")) == 1){

                    System.out.println("You\'re admin");
                    return adminUser = true;

                } else if (Integer.parseInt(log.getString("admin")) == 0){

                    System.out.println("You\'re normal user");
                    return adminUser = false;

                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("You\'re normal user");
        return adminUser = false;
    }
    public void setConnection()
    {
        // connect to database
        try
        {
            // load the driver class
            Class.forName(DRIVER);
            // establish connection to database
            con =  DriverManager.getConnection(DATABASE_URL, "root", "");
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
    public static void main(String[] args) {

       Login test = new Login("empl1","1234");

        test.checkUser();
    }
}
