package za.ac.sun.cs.search.singleagent.Agent;

import za.ac.sun.cs.search.singleagent.Board.Direction;
import za.ac.sun.cs.search.singleagent.Board.ExplicitBoard;

import java.util.*;

public class BidirectionalAStarAgent implements Agent {
    private short[] initialState;
    private short[] goalState;

    private ExplicitBoard startBoard;
    private ExplicitBoard goalBoard = new ExplicitBoard(new short[]{0, 1, 2, 3, 4, 5, 6, 7, 8});

    public BidirectionalAStarAgent(short[] configuration) {
        this.initialState = Arrays.copyOf(configuration, configuration.length);
        this.startBoard = new ExplicitBoard(this.initialState);
        this.goalState = new short[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
    }

    private Comparator<ExplicitBoard> explicitBoardComparator = (theBoard, otherBoard) -> {
        int v = theBoard.getCost() - otherBoard.getCost();

        return Integer.compare(v, 0);
    };


    /**
     * Solve the given N-puzzle using A* on an explicit tree.
     *
     * @return An array of {@link Direction}s that when applied to the
     * initial state result in the goal state.
     */
    @Override
    public Direction[] solve() {

        /* Start by creating the frontiers. */
        PriorityQueue<ExplicitBoard> openSet = new PriorityQueue<>(explicitBoardComparator);
        HashSet<ExplicitBoard> closedSet = new HashSet<>();

        /* Set up our initial node. */
        this.startBoard.setCostFromStart(0);

        this.startBoard.setParent(null);

        openSet.add(startBoard);

        /* While there is still world to explore, explore it! */
        while (!openSet.isEmpty()) {

            /* Get the next board in the open set that has the lowest f-cost. */
            ExplicitBoard board = openSet.poll();

            /* Add the current node to the closed set. */
            closedSet.add(board);

            /* If this node is terminal, we have found the solution. */
            if (board.isTerminal()) {
                return constructPath(board);
            }

            /* Get all the neighbors. */
            LinkedList<ExplicitBoard> neighbors = board.getNeighbors();

            /* Put this board into the closed set. */
            openSet.remove(board);
            closedSet.add(board);

            for (ExplicitBoard neighbor : neighbors) {
                /* Capture some information about the node. */
                boolean isOpen = openSet.contains(neighbor);
                boolean isClosed = closedSet.contains(neighbor);

                /* Ignore nodes in the closed list. */
                if (isClosed) {
                    continue;
                }

                /* If node is not in open set, add it. */
                if (!isOpen) {
                    openSet.add(neighbor);
                }

                /* If the node is in the open set, see if this path is a better one. */
                if (isOpen) {
                    /* If the cost to get to this neighbor through this square is lower, update. */
                    if (board.getCostFromStart() + neighbor.getCostFromStart() < (board.getCostFromStart() + 1)) {
                        neighbor.setCostFromStart(board.getCostFromStart() + 1);

                        assert neighbor.getCost() <= board.getCost();
                        neighbor.setParent(board);
                    }
                }

                assert neighbor.getCostFromStart() > 0;
            }
        }
        return null; // TODO In future this should return Optional.empty().
    }

    private Direction[] constructPath(ExplicitBoard board) {
        LinkedList<ExplicitBoard> path = new LinkedList<>();
        System.out.println(board);

        int i = 0;
        while (board.getParent() != null) {
            i++;
            path.addFirst(board);
            board = board.getParent();
            System.out.println(board.toString());
        }
        System.out.println("Move :" + i);


        return null;
    }


}
