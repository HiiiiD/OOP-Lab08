package it.unibo.oop.lab.advanced;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

/**
 */
public final class DrawNumberApp implements DrawNumberViewObserver {

    private final DrawNumber model;
    private final DrawNumberView view;
    private int min;
    private int max;
    private int attempts;

    /**
     * @param configFile config file for reading the minimum/maximum and number of attempts
     */
    public DrawNumberApp(final String configFile) {
        readConfiguration(configFile);
        this.model = new DrawNumberImpl(this.min, this.max, this.attempts);
        this.view = new DrawNumberViewImpl();
        this.view.setObserver(this);
        this.view.start();
    }

    /**
     * Read the configuration.
     * @param configFile configuration file
     */
    private void readConfiguration(final String configFile) {
        final URL url = ClassLoader.getSystemResource(configFile);
        try (FileReader fileReader = new FileReader(url.getPath());
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            final String minLine = bufferedReader.readLine();
            final String maxLine = bufferedReader.readLine();
            final String attemptsLine = bufferedReader.readLine();

            if (stringIsNullOrEmpty(minLine) || stringIsNullOrEmpty(maxLine) || stringIsNullOrEmpty(attemptsLine)) {
                this.view.displayError("Invalid configuration file");
                return;
            }

            this.min = Integer.parseInt(minLine.split(": ")[1]);
            this.max = Integer.parseInt(maxLine.split(": ")[1]);
            this.attempts = Integer.parseInt(attemptsLine.split(": ")[1]);
        } catch (IOException e) {
            this.view.displayError(e.getMessage());
        } catch (NumberFormatException e) {
            this.view.displayError(e.getMessage());
        }

    }

    /**
     * Checks if {@code source} is null or empty.
     * @param source string to check
     * @return true if is null or empty, false otherwise
     */
    private boolean stringIsNullOrEmpty(final String source) {
        return source == null || source.equals("");
    }

    @Override
    public void newAttempt(final int n) {
        try {
            final DrawResult result = model.attempt(n);
            this.view.result(result);
        } catch (IllegalArgumentException e) {
            this.view.numberIncorrect();
        } catch (AttemptsLimitReachedException e) {
            view.limitsReached();
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
