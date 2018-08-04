package za.ac.sun.cs.search.singleagent;

import java.util.*;

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
        ArrayList<Direction> path = new ArrayList<Direction>();
        ImplicitBoard board = new ImplicitBoard(initialState);
        int bound = getHeuristicCostEstimate(board);


        goalState = board.getGoalState();

        

        return null;
    }

    /* Search utility function */

    public int search(ArrayList<Direction> path, int costToNode, int bound) {
        return 0;
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