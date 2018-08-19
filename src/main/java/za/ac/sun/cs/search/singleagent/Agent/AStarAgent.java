package za.ac.sun.cs.search.singleagent.Agent;

import za.ac.sun.cs.search.singleagent.Domain.Board.Direction;
import za.ac.sun.cs.search.singleagent.Domain.Board.ExplicitBoard;
import za.ac.sun.cs.search.singleagent.Heuristic.Heuristic;

import java.util.*;

import static za.ac.sun.cs.search.singleagent.Domain.Board.Direction.*;

public class AStarAgent implements Agent {
    private short[] initialState;

    private ExplicitBoard startBoard;

    /* A counter to keep track of the number of nodes explored. */
    private int exploredNodes = 0;

    public AStarAgent(short[] configuration, Heuristic heuristic) {
        this.initialState = Arrays.copyOf(configuration, configuration.length);
        this.startBoard = new ExplicitBoard(this.initialState, heuristic);
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

            exploredNodes += neighbors.size();

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

    /**
     * Reconstruct the path from the source vertex to the target vertex.
     *
     * @param board The terminal node, as determined by A*.
     * @return An array of moves that when applied on the starting board, leads to the
     * terminal board.
     */
    private Direction[] constructPath(ExplicitBoard board) {
        List<Direction> solution = new LinkedList<>();

        System.out.println(board);

        while (board.getParent() != null) {

            /* Grab a reference to the next state. */
            ExplicitBoard nextBoard = board.getParent();
            System.out.println(nextBoard);

            /* Find the move responsible for the transition. */
            Direction move = testMove(board, nextBoard);

            /* Add the solution to the move. */
            solution.add(move);

            /* One step up in the chain. */
            board = nextBoard;
        }

        /* Reverse the linked list so we get the solution from the front to the back. */
        Collections.reverse(solution);

        return solution.toArray(new Direction[solution.size()]);
    }

    /**
     * Determine which move caused the new board to be generated from the original.
     *
     * @param original  The original board
     * @param nextBoard The board after the move has been applied
     * @return The move responsible for the transformation.
     */
    private Direction testMove(ExplicitBoard original, ExplicitBoard nextBoard) {

        Direction[] possibleMoves = new Direction[] {UP, LEFT, DOWN, RIGHT};

        for (Direction move : possibleMoves) {
            try {
                if (nextBoard.makeMove(move).equals(original)) {
                    return move;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                /* Not this move. */
            }
        }
        return null;

    }

    /**
     * @return The number of nodes explored by this agent during the search.
     */
    public int getExploredNodes() {
        return this.exploredNodes;
    }


}
