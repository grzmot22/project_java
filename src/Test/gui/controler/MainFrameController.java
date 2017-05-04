package Test.gui.controler;

/**
 * Created by Grzmot22 on 2017-04-25.
 */
import Test.Login;
import Test.gui.view.MainFrame;
import Test.gui.view.ReservationClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainFrameController {

    private MainFrame mainFrame;
    private JButton loginBtn;
    private JButton createBtn;
    private JLabel loginLbl;
    private JLabel passwordLbl;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JLabel welLbl;
    private JLabel infoLbl;
    private Login login;
    private int userId;


    public MainFrameController() {
        initComponents();
        initListeners();
    }

    public void showMainFrameWindow() {
        mainFrame.setVisible(true);
    }


    private void initComponents() {
        mainFrame = new MainFrame();

        loginBtn = mainFrame.getLoginBtn();
        createBtn = mainFrame.getCreateBtn();
        loginField = mainFrame.getLoginField();
        passwordLbl = mainFrame.getPasswordLbl();
        passwordField = mainFrame.getPasswordField();
        infoLbl = mainFrame.getInfoLbl();

    }

    private void initListeners() {
        loginBtn.addActionListener(new loginBtnLister());
        createBtn.addActionListener(new CreateBtnLister());
    }

    private class loginBtnLister implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try{

                login = new Login(loginField.getText(),String.copyValueOf(passwordField.getPassword()));
                String log = login.signIn();
                if (log.matches("Success")){
                    infoLbl.setText(log.toString());
                    JOptionPane.showMessageDialog(null,"Welcome back "+loginField.getText()+"!","Reservation System",JOptionPane.INFORMATION_MESSAGE);
                    mainFrame.setVisible(false);
                    userId = login.getUserId();
                    boolean adminMode = login.checkUser();
                    if (adminMode){
                        JOptionPane.showMessageDialog(null,"Admin mode","Reservation System",JOptionPane.INFORMATION_MESSAGE);
                        ReservationClientController clientController = new ReservationClientController().showMainFrameWindow(adminMode,userId);
//                        clientController.showMainFrameWindow(true);
                    }  else if (!adminMode){
                        ReservationClientController clientController = new ReservationClientController().showMainFrameWindow(adminMode,userId);
                    }
                } else {
                    infoLbl.setText(log);
                }

            } catch (Exception ex){
                ex.getMessage();
            }

        }
    }

    private class CreateBtnLister implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new CreateAccountController().showMainFrameWindow();
        }
    }

    public static void main(String[] args) {
        MainFrameController mainFrameController = new MainFrameController();
        mainFrameController.showMainFrameWindow();
    }
}
