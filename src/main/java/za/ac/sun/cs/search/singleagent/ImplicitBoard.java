package za.ac.sun.cs.search.singleagent;

public class Implicit extends Board {
    protected Direction previousMove;
    /**
     * Initialize the internal state of the board and calculate the size of it.
     *
     * @param initialState An array of the initial tile configuration, as read from left to right and top to bottom.
     */
    public ImplicitBoard(short[] initialState) {
        super(initialState);
        this.previousMove = null;
    }

    @Override
    public ExplicitBoard makeMove(Direction move) {
        return null;
    }

    public ExplicitBoard getPrevious() {
        return previousMove;
    }

    public void setPreviousMove(Direction move) {
        this.previousMove = move;
    }
}
