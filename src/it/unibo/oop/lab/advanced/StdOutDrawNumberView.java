package it.unibo.oop.lab.advanced;

public class StdOutDrawNumberView implements DrawNumberView {

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
        System.out.flush();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void numberIncorrect() {
        System.out.println("Incorrect Number.. try again");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void result(final DrawResult res) {
        switch (res) {
        case YOURS_HIGH:
        case YOURS_LOW:
            System.out.println(res.getDescription());
            return;
        case YOU_WON:
            System.out.println(res.getDescription() + DrawNumberApp.NEW_GAME);
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
        System.out.println("You lost" + DrawNumberApp.NEW_GAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayError(final String message) {
        System.out.println(message);
    }

}
