package it.unibo.oop.lab.mvcio;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUI {

    private final JFrame frame = new JFrame();

    private final Controller controller = new Controller();

    private final JPanel mainContentPane = new JPanel();

    /*
     * Once the Controller is done, implement this class in such a way that:
     * 
     * 1) It has a main method that starts the graphical application
     * 
     * 2) In its constructor, sets up the whole view
     * 
     * 3) The graphical interface consists of a JTextArea with a button "Save" right
     * below (see "ex02.png" for the expected result). SUGGESTION: Use a JPanel with
     * BorderLayout
     * 
     * 4) By default, if the graphical interface is closed the program must exit
     * (call setDefaultCloseOperation)
     * 
     * 5) The program asks the controller to save the file if the button "Save" gets
     * pressed.
     * 
     * Use "ex02.png" (in the res directory) to verify the expected aspect.
     */

    /**
     * Builds a new {@link SimpleGUI}.
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
        /*
         * Instead of appearing at (0,0), upper left corner of the screen, this flag
         * makes the OS window manager take care of the default positioning on screen.
         * Results may vary, but it is generally the best choice.
         */
        this.frame.setLocationByPlatform(true);

        /**
         * Create the main content pane
         */
        final BorderLayout mainContentPaneLayout = new BorderLayout();
        this.mainContentPane.setLayout(mainContentPaneLayout);
        final JTextArea contentTextArea = new JTextArea();
        this.mainContentPane.add(contentTextArea, BorderLayout.CENTER);
        final JButton saveButton = new JButton("Save");
        this.mainContentPane.add(saveButton, BorderLayout.SOUTH);

        /*
         * Save button handler
         */
        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    SimpleGUI.this.controller.saveContent(contentTextArea.getText());
                } catch (final IOException e1) {
                    JOptionPane.showMessageDialog(SimpleGUI.this.frame, e1, "File error", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                }
            }

        });

        this.frame.setContentPane(this.mainContentPane);

        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set visible by default
        this.frame.setVisible(true);
    }

    /**
     * Get the current controller.
     * 
     * @return the current {@link Controller}
     */
    public Controller getController() {
        return this.controller;
    }

    /**
     * Get the main content pane.
     * 
     * @return the main content pane.
     */
    public JPanel getMainContentPane() {
        return this.mainContentPane;
    }

    public static void main(final String[] args) {
        new SimpleGUI();
    }

}
