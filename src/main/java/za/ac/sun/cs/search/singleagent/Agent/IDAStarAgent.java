package za.ac.sun.cs.search.singleagent.Agent;

import za.ac.sun.cs.search.singleagent.Domain.Board.Direction;
import za.ac.sun.cs.search.singleagent.Domain.Board.ImplicitBoard;
import za.ac.sun.cs.search.singleagent.Heuristic.Heuristic;

import java.util.Stack;

public class IDAStarAgent implements Agent {
    private short[] initialState;
    private short[] goalState;
    private ImplicitBoard board;
    private Heuristic heuristic;
    private int exploredNodes;
    private int initalEstimate;

    public IDAStarAgent(short[] configuration, Heuristic heuristic) {
        this.initialState = configuration;
        this.board = new ImplicitBoard(initialState);
        this.goalState = this.board.getGoalState();
        this.heuristic = heuristic;
        this.exploredNodes = 0;

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
        int bound = heuristic.getHeuristicCostEstimate(board);
        initalEstimate = bound;
        boolean found = false;

        while (!found) {
            int i = search(board, path, 0, bound);
            if (i == -1) {
                found = true;
            }
            bound = i;
        }

        return path.toArray(new Direction[0]);
    }

    /* Search utility function */

    public int search(ImplicitBoard board, Stack<Direction> path, int g, int bound) {
        int f = g + heuristic.getHeuristicCostEstimate(board);
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
            exploredNodes++;
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

    public int getInitialEstimate() {
        return initalEstimate;
    }

    public int getExploredNodes() {
        return exploredNodes;
    }
}