package za.ac.sun.cs.search.singleagent.Agent;

import za.ac.sun.cs.search.singleagent.Domain.Board.Direction;
import za.ac.sun.cs.search.singleagent.Domain.Grid.ImplicitGrid;

import java.util.Stack;

public class IDFSGridAgent implements Agent {

    private ImplicitGrid grid;
    private boolean[][] initialState;
    private int exploredNodes;

    public IDFSGridAgent(boolean[][] configuration, short[] playerPosition, short[] goalPosition) {
        this.initialState = configuration;
        this.grid = new ImplicitGrid(initialState, playerPosition, goalPosition);
        this.exploredNodes = 0;
    }

    /**
     * Solve the given Path Finder using IDFS on an implicit tree.
     *
     * @return An array of {@link Direction}s that when applied to the initial state
     *         result in the goal state.
     */
    @Override
    public Direction[] solve() {
        Stack<Direction> path = new Stack<>();
        DFSResult result = new DFSResult();
        int depth = 4;

        while (!result.getFound()) {
            path = new Stack<>();
            dfs(grid, depth, path, result);
            if (result.getFound()) {
                result.setFound(true);
            } else if (!result.getRemaining()) {
                return null;
            }

            depth++;
        }

        return path.toArray(new Direction[0]);
    }

    /* Search utility function */

    public void dfs(ImplicitGrid grid, int depth, Stack<Direction> path, DFSResult result) {

        if (depth == 0) {
            if (grid.isTerminal()) {
                result.setFound(true);
                return;
            } else {
                result.setFound(false);
                return;
            }
        } else if (depth > 0) {
            Direction previousMove = grid.getPrevious();
            for (Direction move : grid.getLegalMoves()) {
                if (grid.getPrevious() != null && move == grid.reverseMove(grid.getPrevious())) {
                    continue;
                }
                path.add(move);
                grid.makeMove(move);
                exploredNodes++;
                grid.setPreviousMove(move);
                dfs(grid, depth - 1, path, result);
                if (result.getFound()) {
                    return;
                } else {
                    result.setRemaining(true);
                }

                grid.undoMove(path.pop());
                grid.setPreviousMove(previousMove);

            }

            result.setFound(false);
            return;
        }

    }

    public ImplicitGrid getBoard() {
        return grid;
    }

    public int getExploredNodes() {
        return exploredNodes;
    }

    private class DFSResult {
        private boolean found;
        private boolean remaining;

        DFSResult() {
            this.found = false;
            this.remaining = false;
        }

        public boolean getFound() {
            return found;
        }

        public boolean getRemaining() {
            return remaining;
        }

        public void setFound(boolean found) {
            this.found = found;
        }

        public void setRemaining(boolean remaining) {
            this.remaining = remaining;
        }
    }
}
