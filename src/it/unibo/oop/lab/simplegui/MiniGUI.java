package it.unibo.oop.lab.simplegui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class is a simple application that writes a random number on a file.
 * 
 * This application does not exploit the model-view-controller pattern, and as
 * such is just to be used to learn the basics, not as a template for your
 * applications.
 */
public class MiniGUI {

    private static final String TITLE = "A very simple GUI application";
    private static final int PROPORTION = 5;
    private final Random rng = new Random();
    private final JFrame frame = new JFrame(TITLE);

    /**
     * Create a simple application with a GUI with a button and a panel in the center of the window.
     */
    public MiniGUI() {
        final JPanel canvas = new JPanel();
        canvas.setLayout(new BorderLayout());
        /**
         * Button that prints a random number on the standard output
         */
        final JButton write = new JButton("Print a random number on standard output");
        /*
         * Panel with the button
         */
        final JPanel innerCenteredPanel = new JPanel();
        final BoxLayout centeredPanelBoxLayout = new BoxLayout(innerCenteredPanel, BoxLayout.X_AXIS);
        innerCenteredPanel.setLayout(centeredPanelBoxLayout);
        innerCenteredPanel.add(write);
        canvas.add(innerCenteredPanel, BorderLayout.CENTER);
        /*
         * Text field added to the north.
         */
        final JTextField resultTextField = new JTextField();
        canvas.add(resultTextField, BorderLayout.NORTH);

        frame.setContentPane(canvas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /*
         * Handlers
         */
        write.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final int newRandomInteger = rng.nextInt();
                System.out.println(rng.nextInt());
                resultTextField.setText(String.valueOf(newRandomInteger));
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
         * Resize to the minimum size
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
        new MiniGUI().display();
    }

}
