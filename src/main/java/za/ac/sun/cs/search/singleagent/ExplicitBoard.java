package za.ac.sun.cs.search.singleagent;

import java.util.Arrays;
import java.util.LinkedList;


public class ExplicitBoard extends Board {
    private ExplicitBoard parent;
    private int costFromStart;
    private int estimatedCost;
    private int cost;

    /**
     * Initialize the internal state of the board and calculate the size of it.
     *
     * @param initialState An array of the initial tile configuration, as read from left to right and top to bottom.
     */
    public ExplicitBoard(short[] initialState) {
        super(initialState);
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
            ExplicitBoard neighbor = this.makeMove(move);
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
        ExplicitBoard temp = new ExplicitBoard(Arrays.copyOf(this.getCurrentState(), this.getCurrentState().length));
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



