package Test.gui.controler;

import Test.Login;
import Test.gui.view.CreateAccount;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Grzmot22 on 2017-04-25.
 */
public class CreateAccountController {

    private JTextField loginTextField;
    private JPasswordField passwordPasswordField;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField cityTextField;
    private JTextField postcodeTextField;
    private JTextField emailTextField;
    private JTextField phoneTextField;
    private JButton createButton;
    private CreateAccount mFrame;
    private Login login;

    public CreateAccountController() {
        initComponents();
        initListeners();
    }
    public void showMainFrameWindow() {

        mFrame.setVisible(true);
        loginTextField = mFrame.getLoginTextField();
        passwordPasswordField = mFrame.getPasswordPasswordField();
        firstNameTextField = mFrame.getFirstNameTextField();
        lastNameTextField = mFrame.getLastNameTextField();
        cityTextField = mFrame.getCityTextField();
        postcodeTextField = mFrame.getPostcodeTextField();
        emailTextField = mFrame.getEmailTextField();
        phoneTextField = mFrame.getPhoneTextField();

    }


    private void initComponents() {
        mFrame = new CreateAccount();


    }
    private void initListeners() {
        createButton.addActionListener(new createButtonListener());
    }

    private class createButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            try {

                String create = login.createUser(loginTextField.getText(),passwordPasswordField.getPassword().toString(),
                        firstNameTextField.getText(),lastNameTextField.getText(),cityTextField.getText(),
                        postcodeTextField.getText(),emailTextField.getText(),phoneTextField.getText());

                if (create.matches("Account has been created")){

                    JOptionPane.showMessageDialog(null,"Account has been created. Now you can login.");

                }else if (create.matches("Login already exist")){

                    JOptionPane.showMessageDialog(null,"Login already exist. Try again.");

                }
            } catch (Exception ex){
                ex.getMessage();
            }

        }
    }
}
