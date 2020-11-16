package it.unibo.oop.lab.mvc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUI {

    private final JFrame frame = new JFrame();

    private final Controller controller = new IOController();

    /*
     * Once the Controller is done, implement this class in such a way that:
     * 
     * 1) I has a main method that starts the graphical application
     * 
     * 2) In its constructor, sets up the whole view
     * 
     * 3) The graphical interface consists of a JTextField in the upper part of the
     * frame, a JTextArea in the center and two buttons below it: "Print", and
     * "Show history". SUGGESTION: Use a JPanel with BorderLayout
     * 
     * 4) By default, if the graphical interface is closed the program must exit
     * (call setDefaultCloseOperation)
     * 
     * 5) The behavior of the program is that, if "Print" is pressed, the controller
     * is asked to show the string contained in the text field on standard output.
     * If "show history" is pressed instead, the GUI must show all the prints that
     * have been done to this moment in the text area.
     * 
     */

    /**
     * builds a new {@link SimpleGUI}.
     */
    public SimpleGUI() {

        /*
         * Make the frame half the resolution of the screen. This very method is enough
         * for a single screen setup. In case of multiple monitors, the primary is
         * selected.
         * 
         * In order to deal coherently with multimonitor setups, other facilities exist
         * (see the Java documentation about this issue). It is MUCH better than
         * manually specify the size of a window in pixel: it takes into account the
         * current resolution.
         */
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();
        this.frame.setSize(sw / 2, sh / 2);

        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BorderLayout());

        final JTextField textField = new JTextField();
        final JTextArea textArea = new JTextArea();
        /*
         * Create a jpanel for the bottom layout
         */
        final JPanel bottomPanel = new JPanel();
        /*
         * Use a grid layout to divide the space equally One row and two columns to
         * divide width in half
         */
        final int rows = 1;
        final int columns = 2;
        bottomPanel.setLayout(new GridLayout(rows, columns));
        final JButton printButton = new JButton("Print");
        final JButton showHistoryButton = new JButton("Show history");
        bottomPanel.add(printButton);
        bottomPanel.add(showHistoryButton);

        mainContentPanel.add(textField, BorderLayout.NORTH);
        mainContentPanel.add(textArea, BorderLayout.CENTER);
        mainContentPanel.add(bottomPanel, BorderLayout.SOUTH);

        /*
         * Click handlers
         */
        printButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                SimpleGUI.this.controller.setNextString(textField.getText());
                SimpleGUI.this.controller.printCurrentString();
            }
        });

        showHistoryButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                final List<String> history = SimpleGUI.this.controller.getHistory();
                textArea.setText(history.toString());
            }
        });

        this.frame.setContentPane(mainContentPanel);

        /*
         * Instead of appearing at (0,0), upper left corner of the screen, this flag
         * makes the OS window manager take care of the default positioning on screen.
         * Results may vary, but it is generally the best choice.
         */
        this.frame.setLocationByPlatform(true);

        this.frame.setVisible(true);
    }

    public static void main(final String[] args) {
        new SimpleGUI();
    }

}
