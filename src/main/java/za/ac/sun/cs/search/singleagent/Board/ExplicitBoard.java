package za.ac.sun.cs.search.singleagent.Board;

import java.util.Arrays;
import java.util.LinkedList;


public class ExplicitBoard extends Board {
    private ExplicitBoard parent;
    private int costFromStart;
    private int estimatedCost;
    private int cost;

    /**
     * Initialize the internal state of the board and calculate the size of it. This constructor initializes the goal
     * state for us.
     *
     * @param initialState An array of the initial tile configuration, as read from left to right and top to bottom.
     */
    public ExplicitBoard(short[] initialState) {
        super(initialState);
        this.parent = null;
    }

    /**
     * Initialize the internal state of the board and calculate the size of it. This constructor initializes the goal
     * state for us.
     *
     * @param initialState An array of the initial tile configuration, as read from left to right and top to bottom.
     */
    public ExplicitBoard(short[] initialState, short[] goalState) {
        super(initialState, goalState);
        this.parent = null;
    }

    /**
     * Count the number of tiles not in their final position (excluding the blank tile).
     *
     * An admissible, inconsistent heuristic.
     * @return The number of misplaced tiles
     */
    public int getHeuristicCostEstimateRaw() {
        int cost = 0;

        int idx = 0;
        int N = (int) Math.sqrt(goalState.length);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                /* Don't count the blank tile. */
                if (this.getAt(i, j) == 0) {
                    idx++;
                    continue;
                }
                if (this.getAt(i, j) != goalState[idx++]) {
                    cost += 1;
                }

            }
        }

        return cost;
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
        this.estimatedCost = getHeuristicCostEstimateRaw();
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

            neighbor.setCostFromStart(this.costFromStart + 1);
            neighbors.add(neighbor);
        }

        return neighbors;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.currentState);
    }
}



