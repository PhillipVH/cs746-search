package za.ac.sun.cs.search.singleagent.Board;

import za.ac.sun.cs.search.singleagent.Heuristic.Heuristic;

import java.util.Arrays;
import java.util.LinkedList;


public class ExplicitBoard extends Board {
    private ExplicitBoard parent;
    private int costFromStart;
    private int estimatedCost;
    private int cost;
    private Heuristic heuristic;

    /**
     * Initialize the internal state of the board and calculate the size of it. This constructor initializes the goal
     * state for us.
     *
     * @param initialState An array of the initial tile configuration, as read from left to right and top to bottom.
     */
    public ExplicitBoard(short[] initialState, Heuristic heuristic) {
        super(initialState);
        this.parent = null;
        this.heuristic = heuristic;
    }

    /**
     * Initialize the internal state of the board and calculate the size of it. This constructor initializes the goal
     * state for us.
     *
     * @param initialState An array of the initial tile configuration, as read from left to right and top to bottom.
     */
    public ExplicitBoard(short[] initialState, short[] goalState, Heuristic heuristic) {
        super(initialState, goalState);
        this.parent = null;
        this.heuristic = heuristic;
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
        this.estimatedCost = this.heuristic.getHeuristicCostEstimate(this);
        this.cost = this.costFromStart + estimatedCost;
    }

    public int getEstimatedCost() {
        return this.estimatedCost;
    }

    public int getCost() {
        return this.cost;
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
            ExplicitBoard neighbor = (ExplicitBoard) this.makeMove(move);

            /* This board is the parent of the neighbor. */
            neighbor.setParent(this);

            /* It cost one move to get to this neighbor, so set the cost from the start appropriately. */
            neighbor.setCostFromStart(this.costFromStart + 1);
            neighbors.add(neighbor);
        }

        return neighbors;
    }

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
        ExplicitBoard temp = new ExplicitBoard(Arrays.copyOf(this.getCurrentState(), this.getCurrentState().length), this.goalState, heuristic);
        temp.putAt(toRow, toCol, (short) 0);
        temp.putAt(fromRow, fromCol, (short) tempTile);

        return temp;
    }

    public void visualizePath(Direction[] path) throws Exception {
        System.out.println("Initial Board:");
        System.out.println(this);
        for (Direction move : path) {
            System.out.println("Move: " + move);
            this.makeMove(move);
            System.out.println(this);
            Thread.sleep(500);

        }
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.currentState);
    }
}



