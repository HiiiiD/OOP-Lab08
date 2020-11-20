package it.unibo.oop.lab.mvcio2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import it.unibo.oop.lab.mvcio.Controller;
import it.unibo.oop.lab.mvcio.SimpleGUI;

/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUIWithFileChooser {

    private final Controller controller;

    private final JPanel mainContentPane;

    /**
     * Passing a {@link SimpleGUI} to build the main layout of the "app".
     * 
     * @param gui
     *                {@link SimpleGUI} to use
     */
    public SimpleGUIWithFileChooser(final SimpleGUI gui) {

        final SimpleGUI simpleGUI = Objects.requireNonNull(gui);
        // Get the controller from the GUI
        this.controller = simpleGUI.getController();
        // Get the current mainContentPane from the simple gui
        this.mainContentPane = simpleGUI.getMainContentPane();
        /*
         * Create the north panel for browsing files
         */
        final JPanel browsePanel = new JPanel();
        /*
         * Create a textfield
         */
        final JTextField browseTextField = new JTextField(this.controller.getCurrentFilePath());
        /*
         * Make it unmodifiable
         */
        browseTextField.setEditable(false);
        final JButton browseFilesButton = new JButton("Browse...");
        browsePanel.setLayout(new BorderLayout());
        browsePanel.add(browseTextField, BorderLayout.CENTER);
        browsePanel.add(browseFilesButton, BorderLayout.LINE_END);

        /*
         * Open a {@link JFileChooser} on click
         */
        browseFilesButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                /**
                 * Create a file chooser with the default file set as the
                 * {@link Controller.getCurrentFile()}
                 */
                final JFileChooser fileChooser = new JFileChooser(SimpleGUIWithFileChooser.this.controller.getCurrentFile());
                final int dialogResult = fileChooser.showSaveDialog(browsePanel);
                // If the user clicked "OK"
                if (dialogResult == JFileChooser.APPROVE_OPTION) {
                    // Set the new current file to the selected file
                    SimpleGUIWithFileChooser.this.controller.setFile(fileChooser.getSelectedFile());
                    browseTextField.setText(SimpleGUIWithFileChooser.this.controller.getCurrentFilePath());
                } else if (dialogResult != JFileChooser.CANCEL_OPTION) {
                    JOptionPane.showMessageDialog(browsePanel, "Error occurred");
                    System.out.println(dialogResult);
                }
            }
        });

        this.mainContentPane.add(browsePanel, BorderLayout.NORTH);

        // Make the main content panel refresh to add the new components
        this.mainContentPane.revalidate();
    }

    /**
     * Get the current controller.
     * 
     * @return the current controlelr
     */
    public Controller getController() {
        return this.controller;
    }

    /**
     * Get the main content pane.
     * 
     * @return the main content pane
     */
    public JPanel getMainContentPane() {
        return this.mainContentPane;
    }

    /*
     * 
     * 1) Add a JTextField and a button "Browse..." on the upper part of the
     * graphical interface. Suggestion: use a second JPanel with a second
     * BorderLayout, put the panel in the North of the main panel, put the text
     * field in the center of the new panel and put the button in the line_end of
     * the new panel.
     * 
     * 2) The JTextField should be non modifiable. And, should display the current
     * selected file.
     * 
     * 3) On press, the button should open a JFileChooser. The program should use
     * the method showSaveDialog() to display the file chooser, and if the result is
     * equal to JFileChooser.APPROVE_OPTION the program should set as new file in
     * the Controller the file chosen. If CANCEL_OPTION is returned, then the
     * program should do nothing. Otherwise, a message dialog should be shown
     * telling the user that an error has occurred (use
     * JOptionPane.showMessageDialog()).
     * 
     * 4) When in the controller a new File is set, also the graphical interface
     * must reflect such change. Suggestion: do not force the controller to update
     * the UI: in this example the UI knows when should be updated, so try to keep
     * things separated.
     */

    /**
     * Build a simple GUI with a file chooser with the MVC pattern.
     */
    public SimpleGUIWithFileChooser() {
        this(new SimpleGUI());
    }

    public static void main(final String[] args) {
        new SimpleGUIWithFileChooser();
    }

}
