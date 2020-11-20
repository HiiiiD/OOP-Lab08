package it.unibo.oop.lab.advanced;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileDrawNumberView implements DrawNumberView {

    private static final String LOG_FILE_PATH = "log.log";

    private File currentLogFile;

    private DrawNumberViewObserver observer;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setObserver(final DrawNumberViewObserver observer) {
        this.observer = observer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        this.currentLogFile = new File(DrawNumberApp.APP_DIRECTORY + LOG_FILE_PATH);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void numberIncorrect() {
        this.writeToFile("Incorrect Number.. try again");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void result(final DrawResult res) {
        switch (res) {
        case YOURS_HIGH:
        case YOURS_LOW:
            this.writeToFile(res.getDescription());
            return;
        case YOU_WON:
            this.writeToFile(res.getDescription() + DrawNumberApp.NEW_GAME);
            break;
        default:
            throw new IllegalStateException("Unexpected result: " + res);
        }
        this.observer.resetGame();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void limitsReached() {
        this.writeToFile("You lost" + DrawNumberApp.NEW_GAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayError(final String message) {
        this.writeToFile(message);
    }

    /**
     * Write to file {@code message} using UTF-8 charset.
     * 
     * @param message
     *                    message to write
     */
    private void writeToFile(final String message) {
        try (FileWriter fileWriter = new FileWriter(this.currentLogFile, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write(message + "\n");
        } catch (final IOException e) {
            /*
             * If an IOException occurs, print to the stdout stream
             */
            System.out.println(e.getMessage());
        }
    }

}
