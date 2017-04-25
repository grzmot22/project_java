package Test.gui.view;

import javax.swing.*;

/**
 * Created by Grzmot22 on 2017-04-25.
 */
public class MainFrame extends JFrame{
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private JPanel mainPanel;
    private JButton loginBtn;
    private JButton createBtn;
    private JLabel loginLbl;
    private JLabel passwordLbl;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JLabel welLbl;
    private JLabel infoLbl;


    public MainFrame() {
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
        setSize(WIDTH, HEIGHT);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);
        pack();
    }

    public JButton getLoginBtn() {
        return loginBtn;
    }

    public JButton getCreateBtn() {
        return createBtn;
    }

    public JLabel getInfoLbl() {
        return infoLbl;
    }

    public JLabel getPasswordLbl() {
        return passwordLbl;
    }

    public JLabel getWelLbl() {
        return welLbl;
    }

    public JLabel getLoginLbl() {
        return loginLbl;
    }

    public JTextField getLoginField() {
        return loginField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
    }
}
