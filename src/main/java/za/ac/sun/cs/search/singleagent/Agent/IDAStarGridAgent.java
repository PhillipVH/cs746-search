package za.ac.sun.cs.search.singleagent.Agent;

import za.ac.sun.cs.search.singleagent.Board.Direction;
import za.ac.sun.cs.search.singleagent.Grid.ImplicitGrid;
import za.ac.sun.cs.search.singleagent.Heuristic.Heuristic;

import java.util.Stack;

public class IDAStarGridAgent implements Agent {

    private ImplicitGrid grid;
    private boolean[][] initialState;
    private Heuristic heuristic;

    public IDAStarGridAgent(boolean[][] configuration, short[] playerPosition, short[] goalPosition,
            Heuristic heuristic) {
        this.initialState = configuration;
        this.grid = new ImplicitGrid(initialState, playerPosition, goalPosition);
        this.heuristic = heuristic;

    }

    /**
     * Solve the given N-puzzle using IDA* on an implicit tree.
     *
     * @return An array of {@link Direction}s that when applied to the initial state
     *         result in the goal state.
     */
    @Override
    public Direction[] solve() {
        Stack<Direction> path = new Stack<>();
        int bound = heuristic.getHeuristicCostEstimate(grid);
        boolean found = false;

        while (!found) {
            int i = search(grid, path, 0, bound);
            if (i == -1) {
                found = true;
            }
            bound = i;
        }

        return path.toArray(new Direction[0]);
    }

    /* Search utility function */

    public int search(ImplicitGrid grid, Stack<Direction> path, int g, int bound) {
        int f = g + heuristic.getHeuristicCostEstimate(grid);
        if (f > bound) {
            return f;
        }
        if (grid.isTerminal()) {
            return -1;
        }

        int min = (int) Double.POSITIVE_INFINITY;
        Direction previousMove = grid.getPrevious();
        for (Direction move : grid.getLegalMoves()) {
            if (grid.getPrevious() != null && move == grid.reverseMove(grid.getPrevious())) {
                continue;
            }
            grid.makeMove(move);
            grid.setPreviousMove(move);

            path.add(move);
            int t = search(grid, path, g + 1, bound);
            if (t == -1) {
                return -1;
            }
            if (t < min) {
                min = t;
            }
            path.pop();
            grid.undoMove(move);
            grid.setPreviousMove(previousMove);

        }

        return min;
    }
}