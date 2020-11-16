package it.unibo.oop.lab.advanced;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.function.Consumer;

/**
 * Controller for {@link DrawNumberView}.
 */
public final class DrawNumberApp implements DrawNumberViewObserver {

    /**
     * Name of the directory where all the log files are found.
     */
    public static final String APP_DIRECTORY = System.getProperty("user.home") + File.separator + ".drawnumberapp"
            + File.separator;

    /**
     * Message shown when a game is restarted.
     */
    public static final String NEW_GAME = ": a new game starts!";

    private final DrawNumber model;
    private final List<DrawNumberView> views;
    private int min;
    private int max;
    private int attempts;

    /**
     * @param configFile
     *                       config file for reading the minimum/maximum and number
     *                       of attempts
     */
    public DrawNumberApp(final String configFile) {
        this.readConfiguration(configFile);
        this.model = new DrawNumberImpl(this.min, this.max, this.attempts);
        this.views = List.of(new DrawNumberViewImpl(), new StdOutDrawNumberView(), new FileDrawNumberView());
        this.initializeViews();
    }

    /**
     * Initialize the {@link #views} list.
     */
    private void initializeViews() {
        for (final DrawNumberView view : this.views) {
            view.setObserver(this);
            view.start();
        }
    }

    /**
     * Read the configuration.
     * 
     * @param configFile
     *                       configuration file
     */
    private void readConfiguration(final String configFile) {
        final URL url = ClassLoader.getSystemResource(configFile);
        try (FileReader fileReader = new FileReader(url.getPath());
                BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            final String minLine = bufferedReader.readLine();
            final String maxLine = bufferedReader.readLine();
            final String attemptsLine = bufferedReader.readLine();

            if (this.stringIsNullOrEmpty(minLine) || this.stringIsNullOrEmpty(maxLine)
                    || this.stringIsNullOrEmpty(attemptsLine)) {
                this.broadcastErrorToViews("Invalid configuration file");
                return;
            }

            this.min = Integer.parseInt(minLine.split(": ")[1]);
            this.max = Integer.parseInt(maxLine.split(": ")[1]);
            this.attempts = Integer.parseInt(attemptsLine.split(": ")[1]);
        } catch (IOException | NumberFormatException e) {
            this.broadcastErrorToViews(e.getMessage());
        }

    }

    /**
     * Checks if {@code source} is null or empty.
     * 
     * @param source
     *                   string to check
     * @return true if is null or empty, false otherwise
     */
    private boolean stringIsNullOrEmpty(final String source) {
        return source == null || source.equals("");
    }

    /**
     * Broadcast an error to every view.
     * 
     * @param errorMessage
     *                         error message to broadcast
     */
    private void broadcastErrorToViews(final String errorMessage) {
        this.broadcastToViews(new Consumer<DrawNumberView>() {
            @Override
            public void accept(final DrawNumberView view) {
                view.displayError(errorMessage);
            }
        });
    }

    /**
     * Broadcast a function to all the views.
     * 
     * @param functionForBroadcasting
     *                                    function used for broadcasting
     */
    private void broadcastToViews(final Consumer<DrawNumberView> functionForBroadcasting) {
        for (final DrawNumberView view : this.views) {
            functionForBroadcasting.accept(view);
        }
    }

    @Override
    public void newAttempt(final int n) {
        try {
            final DrawResult result = this.model.attempt(n);
            this.broadcastToViews(new Consumer<DrawNumberView>() {
                @Override
                public void accept(final DrawNumberView view) {
                    view.result(result);
                }
            });
        } catch (final IllegalArgumentException e) {
            this.broadcastToViews(new Consumer<DrawNumberView>() {
                @Override
                public void accept(final DrawNumberView view) {
                    view.numberIncorrect();
                }
            });
        } catch (final AttemptsLimitReachedException e) {
            this.broadcastToViews(new Consumer<DrawNumberView>() {
                @Override
                public void accept(final DrawNumberView view) {
                    view.limitsReached();
                }
            });
        }
    }

    @Override
    public void resetGame() {
        this.model.reset();
    }

    @Override
    public void quit() {
        System.exit(0);
    }

    /**
     * @param args
     *                 ignored
     */
    public static void main(final String... args) {
        new DrawNumberApp("config.yml");
    }

}
