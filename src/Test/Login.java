package Test;

import java.sql.*;

/**
 * Created by Grzmot22 on 2017-04-20.
 */
public class Login {
    private String login;
    private String password;
    private int userId;
    private boolean adminUser;
    private boolean loginAlreadyExist;

    // MySQL database stuff
    private static final String DRIVER = "com.mysql.jdbc.Driver"; // JDBC driver
    private static final String DATABASE = "hotel"; // the database name
    //    static final String HOST = "10.1.63.200"; // the database host IP
    private static final String HOST = "localhost"; // the database host IP
    private static final String DATABASE_URL =
            "jdbc:mysql://" + HOST + "/" + DATABASE;
    private Connection con = null;

    public Login (String login, String password) {
        this.login = login;
        this.password = password;
        userId = 999999;
    }
    /**
     This method check user's permission
     */
    private boolean checkUser(){
        try {
            setConnection();
            Statement st = con.createStatement();

            ResultSet log = st.executeQuery("SELECT `Login`, `user_id`,`admin` FROM `user` WHERE Login = '"+login+"'  LIMIT 1");
            log.next();

            if (login.matches(log.getString("Login"))){

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

    /**
     * Method check if login exist in the DB
     * @return
     */
    private boolean loginExist(){

        try {

            setConnection();
            Statement st;

            st = con.createStatement();
            ResultSet log = st.executeQuery("SELECT * FROM `user` ");
            String checkLogin = "";

            while (!checkLogin.matches(login)){

                log.next();
                checkLogin = log.getString("Login");

        }
            return checkLogin.matches(login);

    } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Method check login and password and login user
     */
    private void signIn(){
        try {
            setConnection();
            Statement st = con.createStatement();


            if (loginExist()){

                ResultSet log = st.executeQuery("SELECT * FROM `user` WHERE Login = '"+login+"' LIMIT 1");
                log.next();

                if (login.matches(log.getString("Login"))) {

                    if (password.matches(log.getString("password"))){
                        System.out.println("Success");
                        checkUser();
                    } else {
                        System.out.println("Failed");
                        System.out.println("Login or password are not correct");
                    }
                }
            } else {
                System.out.println("Login or password are not correct");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method creates the user in the DB
     * @param login
     * @param password
     */
    private void createUser(String login, String password){

        setConnection();
        Statement st;
        this.login = login;
        this.password = password;
        try {
            loginAlreadyExist = false;
            loginExist();
            st = con.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM `user`");
            userId = 100001;

            while (resultSet.next()){
                int currentUserId = resultSet.getInt("user_id");
                if(currentUserId >= 0 && currentUserId <= 10){
                    currentUserId = userId;
                }else if (currentUserId > 100000){
                    if (userId == currentUserId){
                        userId = currentUserId + 1;
                    }
                }
            }

            if (loginExist()){
                loginAlreadyExist = true;
                System.out.println("Login already exist");

            } else if (!loginExist() && !loginAlreadyExist){

                st.executeUpdate("INSERT INTO `user` (`Login`, `password`, `user_id`, `admin`) VALUES ('"+login+"', '"+password+"', '"+userId+"', '0')");
                System.out.println("Account has been created \n Login: "+login+"\n Pass: "+password+"\nUserId: "+userId);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }



    }
    private void setConnection()
    {
        // connect to database
        try
        {
            // load the driver class
            Class.forName(DRIVER);
            // establish connection to database
            con =  DriverManager.getConnection(DATABASE_URL, "root", "");
        }
        catch (SQLException | ClassNotFoundException sqlException)
        {
            sqlException.printStackTrace();
        } // end catch
        // end catch

    }
    public static void main(String[] args) {

       Login test = new Login("admin","admin");

//        test.signIn();
//        test.checkUser();
//        System.out.println(test.loginExist());
//        test.createUser("client12","qwerty123");
    }
}
