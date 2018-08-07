package za.ac.sun.cs.search.singleagent;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class IDFSAgent implements Agent {
    private short[] initialState;
    private short[] goalState;
    private ImplicitBoard board;

    public IDFSAgent(short[] configuration) {
        this.initialState = configuration;
        this.board = new ImplicitBoard(initialState);
        this.goalState = this.board.getGoalState();

    }

    /**
     * Solve the given N-puzzle using IDA* on an implicit tree.
     *
     * @return An array of {@link Direction}s that when applied to the initial state
     *         result in the goal state.
     */
    @Override
    public Direction[] solve() {
        Stack<Direction> path = new Stack<Direction>();
        DFSResult result = new DFSResult();
        int depth = 4;

        while (!result.getFound()) {
            path = new Stack<Direction>();
            dfs(board, depth, path, result);
            if (result.getFound()) {
                result.setFound(true);
            } else if (!result.getRemaining()) {
                return null;
            }

            depth++;
        }

        return path.toArray(new Direction[path.size()]);
    }

    /* Search utility function */

    public void dfs(ImplicitBoard board, int depth, Stack<Direction> path, DFSResult result) {

        if (depth == 0) {
            if (board.isTerminal()) {
                result.setFound(true);
                return;
            } else {
                result.setFound(false);
                return;
            }
        } else if (depth > 0) {
            Direction previousMove = board.getPrevious();
            for (Direction move : board.getLegalMoves()) {
                if (board.getPrevious() != null && move == board.reverseMove(board.getPrevious())) {
                    continue;
                }
                path.add(move);
                //ImplicitBoard temp = new ImplicitBoard(Arrays.copyOf(board.getCurrentState(), board.getCurrentState().length));
                board.makeMove(move);
                board.setPreviousMove(move);
                dfs(board, depth - 1, path, result);
                if (result.getFound()) {
                    return;
                } else {
                    result.setRemaining(true);
                }

                board.undoMove(path.pop());
                board.setPreviousMove(previousMove);

            }

            result.setFound(false);
            return;
        }

    }

    public ImplicitBoard getBoard() {
        return board;
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
