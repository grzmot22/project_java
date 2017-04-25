package Test.gui.view;

import javax.swing.*;

/**
 * Created by Grzmot22 on 2017-04-25.
 */
public class CreateAccount extends JFrame{

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private JTextField loginTextField;
    private JPasswordField passwordPasswordField;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField cityTextField;
    private JTextField postcodeTextField;
    private JTextField emailTextField;
    private JTextField phoneTextField;
    private JPanel mainPanel;
    private JButton createButton;

    public CreateAccount() {


        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        SwingUtilities.updateComponentTreeUI(mainPanel);
        pack();
        setSize(WIDTH, HEIGHT);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public JButton getCreateButton() {
        return createButton;
    }

    public JPasswordField getPasswordPasswordField() {
        return passwordPasswordField;
    }

    public JTextField getCityTextField() {
        return cityTextField;
    }

    public JTextField getEmailTextField() {
        return emailTextField;
    }

    public JTextField getFirstNameTextField() {
        return firstNameTextField;
    }

    public JTextField getLastNameTextField() {
        return lastNameTextField;
    }

    public JTextField getLoginTextField() {
        return loginTextField;
    }

    public JTextField getPhoneTextField() {
        return phoneTextField;
    }

    public JTextField getPostcodeTextField() {
        return postcodeTextField;
    }
}
