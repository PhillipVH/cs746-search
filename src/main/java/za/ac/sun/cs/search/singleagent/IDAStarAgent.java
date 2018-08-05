package za.ac.sun.cs.search.singleagent;

import java.util.LinkedList;
import java.util.Queue;

public class IDAStarAgent implements Agent {
    private short[] initialState;
    private short[] goalState;

    public IDAStarAgent(short[] configuration) {
        this.initialState = configuration;
    }


    /**
     * Solve the given N-puzzle using IDA* on an implicit tree.
     *
     * @return An array of {@link Direction}s that when applied to the
     * initial state result in the goal state.
     */
    @Override
    public Direction[] solve() {
        Queue<Direction> path = new LinkedList<Direction>();
        ImplicitBoard board = new ImplicitBoard(initialState);
        int bound = getHeuristicCostEstimate(board);
        boolean found = false;
        
        goalState = board.getGoalState();

        while (!found) {
            int i = search(board, path, 0, bound);
            if (i == -1) {found = true;}
            bound = i;
        }        

        return path.toArray(new Direction[path.size()]);
    }

    /* Search utility function */

    public int search(ImplicitBoard board, Queue<Direction> path, int g, int bound) {        
        int f = g + getHeuristicCostEstimate(board);

        if (f > bound) {return f;}
        if (board.isTerminal()) {return -1;}

        int min = (int) Double.NEGATIVE_INFINITY;

        for (Direction move: board.getLegalMoves()) {
            ImplicitBoard temp = board.makeMove(move);
            path.add(move);
            int t = search(temp, path, g + 1, bound);
            if (t == -1) {return -1;}
            if (t < min) {min = t;}
            path.remove();
        }

        return min;
    }

    /* Misplaced Tiles Heuristic */

    private Integer getHeuristicCostEstimate(Board board) {
        int cost = 0;

        int idx = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.getAt(i, j) != goalState[idx++]) {
                    cost += 1;
                }
            }
        }

        return cost;
    }
}