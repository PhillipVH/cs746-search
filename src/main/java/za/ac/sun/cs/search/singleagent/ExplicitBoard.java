package za.ac.sun.cs.search.singleagent;

import java.util.LinkedList;


public class ExplicitBoard extends Board {
    protected ExplicitBoard parent;
    /**
     * Initialize the internal state of the board and calculate the size of it.
     *
     * @param initialState An array of the initial tile configuration, as read from left to right and top to bottom.
     */
    public ExplicitBoard(short[] initialState) {
        super(initialState);
        this.parent = null;
    }

    @Override
    public ExplicitBoard makeMove(Direction move) {
        return null;
    }

    public ExplicitBoard getParent() {
        return parent;
    }

    public void setParent(ExplicitBoard parent) {
        this.parent = parent;
    }

    @Override
    public LinkedList<Board> getNeighbors() {return null;}
}
