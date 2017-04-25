package Test.gui.view;

import javax.swing.*;

/**
 * Created by Grzmot22 on 2017-04-23.
 */
public class ReservationClient extends JFrame{

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private JTextField userIDTextField;
    private JTextField reservationIDTextField;
    private JTextField roomNoTextField;
    private JTextField startDateTextField;
    private JTextField endDateTextField;
    private JButton createBookingButton;
    private JButton bookedRoomsButton;
    private JButton freeRoomsButton;
    private JButton checkMyBookingButton;
    private JTextField paidStatusTextField;
    private JTextArea area;
    private JPanel mainPanel;
    private JLabel userIdLtl;

    /**
     * Constructor for objects of class JFrame4
     */
    public ReservationClient() {


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

    public JButton getBookedRoomsButton() {
        return bookedRoomsButton;
    }

    public JButton getCheckMyBookingButton() {
        return checkMyBookingButton;
    }

    public JButton getCreateBookingButton() {
        return createBookingButton;
    }

    public JButton getFreeRoomsButton() {
        return freeRoomsButton;
    }

    public JTextField getEndDateTextField() {
        return endDateTextField;
    }

    public JTextField getPaidStatusTextField() {
        return paidStatusTextField;
    }

    public JTextField getReservationIDTextField() {
        return reservationIDTextField;
    }

    public JTextField getRoomNoTextField() {
        return roomNoTextField;
    }

    public JTextField getStartDateTextField() {
        return startDateTextField;
    }

    public JTextField getUserIDTextField() {
        return userIDTextField;
    }

    public JLabel getUserIdLtl() {
        return userIdLtl;
    }

    public void setPaidStatusTextField(JTextField paidStatusTextField) {
        this.paidStatusTextField = paidStatusTextField;
    }

    public void setArea(JTextArea area) {
        this.area = area;
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
    }

    public JTextArea getArea() {
        return area;
    }

    public static void main(String [] args) {
        ReservationClient myCl = new ReservationClient();
//        myCl.runClient();
            //myCl.closeConnection();
    }
}
