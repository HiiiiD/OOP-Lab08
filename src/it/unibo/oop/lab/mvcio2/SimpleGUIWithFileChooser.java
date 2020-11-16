package it.unibo.oop.lab.mvcio2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import it.unibo.oop.lab.mvcio.Controller;

/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUIWithFileChooser {

    private final JFrame frame = new JFrame();

    private final Controller controller = new Controller();

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
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();
        frame.setSize(sw / 2, sh / 2);
        /*
         * Instead of appearing at (0,0), upper left corner of the screen, this flag
         * makes the OS window manager take care of the default positioning on screen.
         * Results may vary, but it is generally the best choice.
         */
        frame.setLocationByPlatform(true);

        /**
         * Create the main content pane
         */
        final JPanel mainContentPane = new JPanel();
        final BorderLayout mainContentPaneLayout = new BorderLayout();
        mainContentPane.setLayout(mainContentPaneLayout);

        final JTextArea textArea = new JTextArea();
        mainContentPane.add(textArea, BorderLayout.CENTER);
        final JButton saveButton = new JButton("Save");
        mainContentPane.add(saveButton, BorderLayout.SOUTH);

        /*
         * Create the north panel for browsing files 
         */
        final JPanel browsePanel = new JPanel();
        /**
         * Create a textfield that is non-modifiable
         */
        final JTextField browseTextField = new JTextField(controller.getCurrentFilePath());
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
                 * Create a file chooser with the default file set as the {@link Controller.getCurrentFile()}
                 */
                final JFileChooser fileChooser = new JFileChooser(controller.getCurrentFile());
                final int dialogResult = fileChooser.showSaveDialog(browsePanel);
                if (dialogResult == JFileChooser.APPROVE_OPTION) {
                    controller.setFile(fileChooser.getSelectedFile());
                    browseTextField.setText(controller.getCurrentFilePath());
                } else if (dialogResult != JFileChooser.CANCEL_OPTION) {
                    JOptionPane.showMessageDialog(browsePanel, "Error occurred");
                    System.out.println(dialogResult);
                }
            }
        });

        mainContentPane.add(browsePanel, BorderLayout.NORTH);

        /*
         * Save button handler
         */
        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    controller.saveContent(textArea.getText());
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(frame, e1, "File error", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                }
            }

        });

        frame.setContentPane(mainContentPane);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
    }

    public static void main(final String[] args) {
        new SimpleGUIWithFileChooser();
    }

}
