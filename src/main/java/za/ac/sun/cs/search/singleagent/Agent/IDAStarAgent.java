package za.ac.sun.cs.search.singleagent.Agent;

import za.ac.sun.cs.search.singleagent.Board.Board;
import za.ac.sun.cs.search.singleagent.Board.Direction;
import za.ac.sun.cs.search.singleagent.Board.Heuristic;
import za.ac.sun.cs.search.singleagent.Board.ImplicitBoard;

import java.util.Stack;

public class IDAStarAgent implements Agent {
    private short[] initialState;
    private short[] goalState;
    private ImplicitBoard board;
    private Heuristic heuristic;

    public IDAStarAgent(short[] configuration, Heuristic heuristic) {
        this.initialState = configuration;
        this.board = new ImplicitBoard(initialState);
        this.goalState = this.board.getGoalState();
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
        int bound = heuristic.getHeuristicCostEstimate(board);
        boolean found = false;

        while (!found) {
            int i = search(board, path, 0, bound);
            if (i == -1) {found = true;}
            bound = i;
        }

        return path.toArray(new Direction[0]);
    }

    /* Search utility function */

    public int search(ImplicitBoard board, Stack<Direction> path, int g, int bound) {
        int f = g + heuristic.getHeuristicCostEstimate(board);
        if (f > bound) {return f;}
        if (board.isTerminal()) {return -1;}

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
            if (t == -1) {return -1;}
            if (t < min) {min = t;}
            path.pop();
            board.undoMove(move);
            board.setPreviousMove(previousMove);

        }

        return min;
    }
}