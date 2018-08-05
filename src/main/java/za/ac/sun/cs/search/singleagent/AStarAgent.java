package za.ac.sun.cs.search.singleagent;

import java.util.*;

public class AStarAgent implements Agent {
    private short[] initialState;
    private short[] goalState;

    private ExplicitBoard startBoard;
    private ExplicitBoard goalBoard = new ExplicitBoard(new short[]{0, 1, 2, 3, 4, 5, 6, 7, 8});

    public AStarAgent(short[] configuration) {
        this.initialState = configuration;
        this.startBoard = new ExplicitBoard(this.initialState);
        this.goalState = new short[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
    }

    /**
     * Solve the given N-puzzle using A* on an explicit tree.
     *
     * @return An array of {@link Direction}s that when applied to the
     * initial state result in the goal state.
     */
    @Override
    public Direction[] solve() {

        /* Start by creating the frontiers. */
        PriorityList openList = new PriorityList();
        LinkedList closedList = new LinkedList();

        /* Set up our initial node. */
        this.startBoard.setCostFromStart(0);
        this.startBoard.setEstimatedCost(getHeuristicCostEstimate(goalBoard));
        this.startBoard.setParent(null);

        openList.add(startBoard);

        /* While there is still world to explore, explore it! */
        while (!openList.isEmpty()) {
            ExplicitBoard board = (ExplicitBoard) openList.removeFirst();

            /* If this board is terminal we have found our goal.
             * Reconstruct and return the path we have taken.
             */
            if (board.isTerminal()) {
                return constructPath(board);
            }

            /* Get a list of all neighbors. */
            LinkedList<ExplicitBoard> neighbors = board.getNeighbors();

            for (ExplicitBoard neighbor : neighbors) {
                boolean isOpen = openList.contains(neighbor);
                boolean isClosed = closedList.contains(neighbor);

                int costFromStart = neighbor.getCostFromStart() + board.getCost();

                if ((!isOpen && !isClosed) || costFromStart < neighbor.getCostFromStart()) {
                    neighbor.setParent(board);
                    neighbor.setCostFromStart(costFromStart);
                    neighbor.setEstimatedCost(getHeuristicCostEstimate(goalBoard));

                    if (isClosed) {
                        closedList.remove(neighbor);
                    }

                    if (!isOpen) {
                        openList.add(neighbor);
                    }
                }

                closedList.add(neighbor);
            }

        }

        return null; // TODO In future this should return Optional.empty().
    }

    private Direction[] constructPath(ExplicitBoard board) {
        LinkedList<ExplicitBoard> path = new LinkedList<>();
        System.out.println(board);

        while (board.getParent() != null) {
            path.addFirst(board);
            board = board.getParent();
            System.out.println(board.toString());
        }


        return null;
    }

    /* Misplaced Tiles Heuristic */

    private Integer getHeuristicCostEstimate(Board board) {
        int cost = 0;

        int idx = 0;
        int N = board.getSize();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board.getAt(i, j) != goalState[idx++]) {
                    cost += 1;
                }
            }
        }
        return cost;
    }

}

class PriorityList extends LinkedList {

    public void add(Comparable object) {
        for (int i = 0; i < size(); i++) {
            if (object.compareTo(get(i)) <= 0) {
                add(i, object);
                return;
            }
        }
        addLast(object);
    }
}
