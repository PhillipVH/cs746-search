package za.ac.sun.cs.search.singleagent.Domain.Grid;

import za.ac.sun.cs.search.singleagent.Heuristic.Heuristic;
import za.ac.sun.cs.search.singleagent.Domain.Board.Direction;

import java.util.Arrays;
import java.util.LinkedList;

public class ExplicitGrid extends Grid {
    private ExplicitGrid parent;
    private int costFromStart;
    private int estimatedCost;
    private int cost;
    private Heuristic heuristic;

    /**
     * Initialize the internal state of the board and calculate the size of it. This
     * constructor initializes the goal state for us.
     *
     * @param initialState An array of the initial tile configuration, as read from
     *                     left to right and top to bottom.
     */
    public ExplicitGrid(boolean[][] initialState, short[] playerPosition, short[] goalPosition, Heuristic heuristic) {
        super(initialState, playerPosition, goalPosition);
        this.parent = null;
        this.heuristic = heuristic;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof ExplicitGrid)) {
            return false;
        }

        return Arrays.equals(this.getPlayerPosition(), ((ExplicitGrid) o).getPlayerPosition());
    }

    public ExplicitGrid getParent() {
        return parent;
    }

    public void setParent(ExplicitGrid parent) {
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
    public LinkedList<ExplicitGrid> getNeighbors() {
        /* Start by getting the moves. */
        Direction[] legalMoves = getLegalMoves();
        LinkedList<ExplicitGrid> neighbors = new LinkedList<>();

        /* Take each move, apply it to the board, and add it to the list. */
        for (Direction move : legalMoves) {
            ExplicitGrid neighbor = (ExplicitGrid) this.makeMove(move);

            /* This board is the parent of the neighbor. */
            neighbor.setParent(this);

            /*
             * It cost one move to get to this neighbor, so set the cost from the start
             * appropriately.
             */
            neighbor.setCostFromStart(this.costFromStart + 1);
            neighbors.add(neighbor);
        }

        return neighbors;
    }

    public ExplicitGrid makeMove(Direction move) {
        short[] player = Arrays.copyOf(this.getPlayerPosition(), this.getPlayerPosition().length);
        switch (move) {
        case UP:
            player[0] = (short) (player[0] - 1);
            return new ExplicitGrid(this.getGrid(), player, this.getGoalState(), this.heuristic);
        case DOWN:
            player[0] = (short) (player[0] + 1);
            return new ExplicitGrid(this.getGrid(), player, this.getGoalState(), this.heuristic);
        case LEFT:
            player[1] = (short) (player[1] - 1);
            return new ExplicitGrid(this.getGrid(), player, this.getGoalState(), this.heuristic);
        case RIGHT:
            player[1] = (short) (player[1] + 1);
            return new ExplicitGrid(this.getGrid(), player, this.getGoalState(), this.heuristic);
        default:
            return null;
        }
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
        return Arrays.hashCode(this.getGrid());
    }
}
