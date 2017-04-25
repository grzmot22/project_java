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
                    boolean adminMode = login.checkUser();
                    if (adminMode){
                        JOptionPane.showMessageDialog(null,"Admin mode","Reservation System",JOptionPane.INFORMATION_MESSAGE);
                        ReservationClientController clientController = new ReservationClientController().showMainFrameWindow(adminMode);
//                        clientController.showMainFrameWindow(true);
                    } else {
                        ReservationClientController clientController = new ReservationClientController().showMainFrameWindow(adminMode);
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
            try {
                String create = login.createUser(loginField.getText(),passwordField.getPassword().toString());
                if (create.matches("Account has been created")){
                    infoLbl.setText("Account has been created. Now you can login.");

                }else if (create.matches("Login already exist")){
                    infoLbl.setText("Login already exist. Try again.");
                }
            } catch (Exception ex){
                ex.getMessage();
            }
        }
    }

    public static void main(String[] args) {
        MainFrameController mainFrameController = new MainFrameController();
        mainFrameController.showMainFrameWindow();
    }
}
