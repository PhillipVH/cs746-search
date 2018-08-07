package za.ac.sun.cs.search.singleagent;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class IDAStarAgent implements Agent {
    private short[] initialState;
    private short[] goalState;
    private ImplicitBoard board;

    public IDAStarAgent(short[] configuration) {
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
        int bound = getHeuristicCostEstimate(board);
        boolean found = false;

        while (!found) {
            int i = search(board, path, 0, bound);
            if (i == -1) {
                found = true;
            }
            bound = i;
        }

        return path.toArray(new Direction[path.size()]);
    }

    /* Search utility function */

    public int search(ImplicitBoard board, Stack<Direction> path, int g, int bound) {
        int f = g + getHeuristicCostEstimate(board);
        if (f > bound) {
            return f;
        }
        if (board.isTerminal()) {
            return -1;
        }

        int min = (int) Double.POSITIVE_INFINITY;
        Direction previousMove = board.getPrevious();
        for (Direction move : board.getLegalMoves()) {
            if (board.getPrevious() != null && move == board.reverseMove(board.getPrevious())) {
                continue;
            }
            board.makeMove(move);
            board.setPreviousMove(move);
            path.add(move);
            int t = search(board, path, g + 1, bound);
            if (t == -1) {
                return -1;
            }
            if (t < min) {
                min = t;
            }
            path.pop();
            board.undoMove(move);
            board.setPreviousMove(previousMove);
        }

        return min;
    }

    /* Misplaced Tiles Heuristic */

    private Integer getHeuristicCostEstimate(Board board) {
        int cost = 0;

        int idx = 0;
        int N = (int) Math.sqrt(goalState.length);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                /* Don't count the blank tile. */
                if (board.getAt(i, j) == 0) {
                    idx++;
                    continue;
                }
                if (board.getAt(i, j) != goalState[idx++]) {
                    cost += 1;
                }
            }
        }

        return cost;
    }

    public ImplicitBoard getBoard() {
        return board;
    }
}