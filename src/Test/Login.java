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
    private ReservationJDBC jdbc = new ReservationJDBC();

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
            jdbc.setConnection();

            jdbc.statement = jdbc.con.createStatement();

            jdbc.resultSet = jdbc.statement.executeQuery("SELECT `Login`, `user_id`,`admin` FROM `user` WHERE Login = '"+login+"'  LIMIT 1");
            jdbc.resultSet.next();

            if (login.matches(jdbc.resultSet.getString("Login"))){

                if(Integer.parseInt(jdbc.resultSet.getString("admin")) == 1){

                    System.out.println("You\'re admin");
                    return adminUser = true;

                } else if (Integer.parseInt(jdbc.resultSet.getString("admin")) == 0){

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

            jdbc.setConnection();

            jdbc.statement = jdbc.con.createStatement();
            jdbc.resultSet = jdbc.statement.executeQuery("SELECT * FROM `user` ");
            String checkLogin = "";

            while (!checkLogin.matches(login)){

                jdbc.resultSet.next();
                checkLogin = jdbc.resultSet.getString("Login");

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
            jdbc.setConnection();

            jdbc.statement = jdbc.con.createStatement();


            if (loginExist()){

                jdbc.resultSet = jdbc.statement.executeQuery("SELECT * FROM `user` WHERE Login = '"+login+"' LIMIT 1");
                jdbc.resultSet.next();

                if (login.matches(jdbc.resultSet.getString("Login"))) {

                    if (password.matches(jdbc.resultSet.getString("password"))){
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

        jdbc.setConnection();

        this.login = login;
        this.password = password;
        try {
            loginAlreadyExist = false;
            loginExist();
            jdbc.statement = jdbc.con.createStatement();
            jdbc.resultSet = jdbc.statement.executeQuery("SELECT * FROM `user`");
            userId = 100001;

            while (jdbc.resultSet.next()){
                int currentUserId = jdbc.resultSet.getInt("user_id");
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

                jdbc.statement.executeUpdate("INSERT INTO `user` (`Login`, `password`, `user_id`, `admin`) VALUES ('"+login+"', '"+password+"', '"+userId+"', '0')");
                System.out.println("Account has been created \n Login: "+login+"\n Pass: "+password+"\nUserId: "+userId);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }



    }

    public static void main(String[] args) {

       Login test = new Login("admin","admin");

//        test.signIn();
//        test.checkUser();
//        System.out.println(test.loginExist());
//        test.createUser("client12","qwerty123");
    }
}
