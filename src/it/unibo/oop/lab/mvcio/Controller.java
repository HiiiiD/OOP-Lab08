package it.unibo.oop.lab.mvcio;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.apache.commons.io.FileUtils;

/**
 * 
 */
public class Controller {

    /*
     * This class must implement a simple controller responsible of I/O access. It
     * considers a single file at a time, and it is able to serialize objects in it.
     * 
     * Implement this class with:
     * 
     * 1) A method for setting a File as current file
     * 
     * 2) A method for getting the current File
     * 
     * 3) A method for getting the path (in form of String) of the current File
     * 
     * 4) A method that gets a String as input and saves its content on the current
     * file. This method may throw an IOException.
     * 
     * 5) By default, the current file is "output.txt" inside the user home folder.
     * A String representing the local user home folder can be accessed using
     * System.getProperty("user.home"). The separator symbol (/ on *nix, \ on
     * Windows) can be obtained as String through the method
     * System.getProperty("file.separator"). The combined use of those methods leads
     * to a software that runs correctly on every platform.
     */
    private String currentFilePath = System.getProperty("user.home") + File.separator + "output.txt";
    private File currentFile = new File(currentFilePath);

    /**
     * Set {@code newFile} as the new current file.
     * @param newFile new current file
     */
    public void setFile(final File newFile) {
        Objects.requireNonNull(newFile);
        this.currentFile = newFile;
        this.currentFilePath = newFile.getAbsolutePath();
    }

    /**
     * Set {@code newFilePath} as the new path for the current file.
     * @param newFilePath path of the new file
     */
    public void setFile(final String newFilePath) {
        Objects.requireNonNull(newFilePath);
        this.currentFile = new File(newFilePath);
        this.currentFilePath = newFilePath;
    }

    /**
     * Get the current file.
     * @return the current file
     */
    public File getCurrentFile() {
        return this.currentFile;
    }

    /**
     * Get the current file path.
     * @return the file path of the current file
     */
    public String getCurrentFilePath() {
        return this.currentFilePath;
    }
    /**
     * Save {@code content} to the current file.
     * @param content content to save on the current file
     * @throws IOException
     */
    public void saveContent(final String content) throws IOException {
        FileUtils.write(this.currentFile, content, StandardCharsets.UTF_8);
    }

}
