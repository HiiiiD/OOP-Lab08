package it.unibo.oop.lab.mvc;

import java.util.ArrayList;
import java.util.List;

public class IOController implements Controller {

    private final List<String> printedStrings = new ArrayList<>();

    private String nextString;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNextString(final String content) {
        this.nextString = content;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNextString() {
        return this.nextString;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getHistory() {
        /*
         * Return a copy of the field
         */
        return new ArrayList<String>(this.printedStrings);
    }

    /**
     * {@inheritDoc} Prints the string to the standard output
     */
    @Override
    public void printCurrentString() {
        if (this.nextString == null) {
            throw new IllegalStateException("The next string cannot be null");
        }
        this.printedStrings.add(this.nextString);
        System.out.println(this.nextString);
    }

}
