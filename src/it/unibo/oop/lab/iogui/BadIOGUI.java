package it.unibo.oop.lab.iogui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.commons.io.FileUtils;

/**
 * This class is a simple application that writes a random number on a file.
 * 
 * This application does not exploit the model-view-controller pattern, and as
 * such is just to be used to learn the basics, not as a template for your
 * applications.
 */
public class BadIOGUI {

    private static final String TITLE = "A very simple GUI application";
    private static final String PATH = System.getProperty("user.home") + System.getProperty("file.separator")
            + BadIOGUI.class.getSimpleName() + ".txt";
    private static final int PROPORTION = 5;
    private final Random rng = new Random();
    private final JFrame frame = new JFrame(TITLE);

    /**
     * Creates a frame with a panel in it.
     */
    public BadIOGUI() {
        final JPanel canvas = new JPanel();
        canvas.setLayout(new BorderLayout());
        final JButton write = new JButton("Write on file");
        final JButton readButton = new JButton("Read");
        final JPanel innerCenteredPanel = new JPanel();
        final BoxLayout boxLayout = new BoxLayout(innerCenteredPanel, BoxLayout.X_AXIS);
        innerCenteredPanel.setLayout(boxLayout);

        innerCenteredPanel.add(write);
        innerCenteredPanel.add(readButton);
        /*
         * Adds the centered panel to the main content panel
         */
        canvas.add(innerCenteredPanel, BorderLayout.CENTER);

        frame.setContentPane(canvas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /*
         * Handlers
         */
        write.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                /*
                 * This would be VERY BAD in a real application.
                 * 
                 * This makes the Event Dispatch Thread (EDT) work on an I/O operation. I/O
                 * operations may take a long time, during which your UI becomes completely
                 * unresponsive.
                 */
                try (PrintStream ps = new PrintStream(PATH)) {
                    ps.print(rng.nextInt());
                } catch (FileNotFoundException e1) {
                    JOptionPane.showMessageDialog(frame, e1, "Error", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                }
            }
        });
        readButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                final File fileToRead = new File(PATH);
                try {
                    final String stringifiedFile = FileUtils.readFileToString(fileToRead, StandardCharsets.UTF_8);
                    System.out.println(stringifiedFile);
                } catch (IOException e2) {
                    JOptionPane.showMessageDialog(frame, e2, "File error", JOptionPane.ERROR_MESSAGE);
                    e2.printStackTrace();
                }
            }
        });
    }

    /**
     * Displays the current JFrame.
     */
    private void display() {
        /*
         * Make the frame one fifth the resolution of the screen. This very method is
         * enough for a single screen setup. In case of multiple monitors, the primary
         * is selected. In order to deal coherently with multimonitor setups, other
         * facilities exist (see the Java documentation about this issue). It is MUCH
         * better than manually specify the size of a window in pixel: it takes into
         * account the current resolution.
         */
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();
        frame.setSize(sw / PROPORTION, sh / PROPORTION);
        /*
         * Instead of appearing at (0,0), upper left corner of the screen, this flag
         * makes the OS window manager take care of the default positioning on screen.
         * Results may vary, but it is generally the best choice.
         */
        frame.setLocationByPlatform(true);
        /*
         * Resize the frame to the minimum size
         */
        frame.pack();
        /*
         * OK, ready to pull the frame onscreen
         */
        frame.setVisible(true);
    }

    /**
     * @param args
     *                 ignored
     */
    public static void main(final String... args) {
        new BadIOGUI().display();
    }
}
