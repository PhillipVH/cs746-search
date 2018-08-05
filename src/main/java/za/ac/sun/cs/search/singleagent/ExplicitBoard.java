package za.ac.sun.cs.search.singleagent;

import java.util.Arrays;
import java.util.LinkedList;


public class ExplicitBoard extends Board {
    private ExplicitBoard parent;
    private int costFromStart;
    private int estimatedCost;

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
        short[] emptyPosition = this.getEmptyTilePosition();

        switch (move) {
            case UP:
                return swapTiles(emptyPosition[0], emptyPosition[1], emptyPosition[0] - 1, emptyPosition[1]);
            case DOWN:
                return swapTiles(emptyPosition[0], emptyPosition[1], emptyPosition[0] + 1, emptyPosition[1]);
            case LEFT:
                return swapTiles(emptyPosition[0], emptyPosition[1], emptyPosition[0], emptyPosition[1] - 1);
            case RIGHT:
                return swapTiles(emptyPosition[0], emptyPosition[1], emptyPosition[0], emptyPosition[1] + 1);
            default:
                return null;
        }
    }

    public ExplicitBoard swapTiles(int fromRow, int fromCol, int toRow, int toCol) {
        int tempTile = this.getAt(toRow, toCol);
        ExplicitBoard temp = new ExplicitBoard(Arrays.copyOf(this.getCurrentState(), this.getCurrentState().length));
        temp.putAt(toRow, toCol, (short) 0);
        temp.putAt(fromRow, fromCol, (short) tempTile);

        return temp;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof ExplicitBoard)) {
            return false;
        }

        return Arrays.equals(this.getCurrentState(), ((ExplicitBoard) o).getCurrentState());
    }

    public ExplicitBoard getParent() {
        return parent;
    }

    public void setParent(ExplicitBoard parent) {
        this.parent = parent;
    }

    public int getCostFromStart() {
        return costFromStart;
    }

    public void setCostFromStart(int cost) {
        this.costFromStart = cost;
    }

    public void setEstimatedCost(int cost) {
        this.estimatedCost = cost;

    }

    public int getEstimatedCost() {
        return this.estimatedCost;
    }

    public int getCost() {
        return costFromStart + estimatedCost;
    }

    /**
     * Return all the board states we can reach from this node.
     *
     * @return All the reachable states from this node.
     */
    public LinkedList<ExplicitBoard> getNeighbors() {
        /* Start by getting the moves. */
        Direction[] legalMoves = getLegalMoves();
        LinkedList<ExplicitBoard> neighbors = new LinkedList<>();

        /* Take each move, apply it to the board, and add it to the list. */
        for (Direction move : legalMoves) {
            ExplicitBoard neighbor = this.makeMove(move);
            neighbors.add(neighbor);
        }

        return neighbors;
    }
}



