package za.ac.sun.cs.search.singleagent.Agent;

import za.ac.sun.cs.search.singleagent.Domain.Board.Direction;
import za.ac.sun.cs.search.singleagent.Domain.Grid.ExplicitGrid;
import za.ac.sun.cs.search.singleagent.Heuristic.Heuristic;

import java.util.*;

import static za.ac.sun.cs.search.singleagent.Domain.Board.Direction.*;

public class BidirectionalAStarGridAgent implements Agent {
    private boolean[][] initialState;

    /* Two boards, one for each of the forward and backward searches. */
    private ExplicitGrid startBoardForward;
    private ExplicitGrid startBoardBackward;

    private ExplicitGrid uPath = null;
    private boolean allowUnoptimalSolution;

    private short[] initialPlayerPosition;
    private short[] goalPosition;

    /* A counter to keep track of the number of nodes explored. */
    private int exploredNodes = 0;

    private Heuristic heuristic;

    public BidirectionalAStarGridAgent(boolean[][] configuration, short[] playerPosition, short[] goalPosition,
            boolean allowUnoptimalSolution, Heuristic heuristic) {
        this.initialState = Arrays.copyOf(configuration, configuration.length);
        this.allowUnoptimalSolution = allowUnoptimalSolution;
        this.goalPosition = goalPosition;
        this.initialPlayerPosition = playerPosition;

        /*
         * First we create a new board for the forward search to start at. We will use
         * the goal state generated by the single argument ctor to initialize the node
         * for the backwards search.
         */
        this.startBoardForward = new ExplicitGrid(this.initialState, this.initialPlayerPosition, this.goalPosition,
                heuristic);

        /*
         * The node for the backwards search now has the traditional goal state as it's
         * starting state, and the initial state as it's goal state.
         */
        this.startBoardBackward = new ExplicitGrid(this.initialState, this.goalPosition, this.initialPlayerPosition,
                heuristic);

        this.heuristic = heuristic;

    }

    private Comparator<ExplicitGrid> explicitBoardComparator = (theBoard, otherBoard) -> {
        int v = theBoard.getCost() - otherBoard.getCost();

        return Integer.compare(v, 0);
    };

    /**
     * Solve the given Path Finder using Bidirectional A* on an explicit tree.
     *
     * @return An array of {@link Direction}s that when applied to the initial state
     *         result in the goal state.
     */
    @Override
    public Direction[] solve() {

        /*
         * Start by creating the frontiers. Both the forward and backwards searches get
         * their own open and closed sets.
         */
        PriorityQueue<ExplicitGrid> openSetForward = new PriorityQueue<>(explicitBoardComparator);
        HashSet<ExplicitGrid> closedSetForward = new HashSet<>();

        PriorityQueue<ExplicitGrid> openSetBackward = new PriorityQueue<>(explicitBoardComparator);
        HashSet<ExplicitGrid> closedSetBackward = new HashSet<>();

        /* Set up our initial nodes. */
        this.startBoardForward.setCostFromStart(0);
        this.startBoardBackward.setCostFromStart(0);

        this.startBoardForward.setParent(null);
        this.startBoardBackward.setParent(null);

        openSetForward.add(startBoardForward);
        openSetBackward.add(startBoardBackward);

        /* The searches get a change to run in round robin fashion. */
        PriorityQueue<ExplicitGrid> openSetRef = openSetForward;
        HashSet<ExplicitGrid> closedSetRef = closedSetForward;

        boolean forward = true;

        /* While there is still world to explore, explore it! */
        while (!openSetForward.isEmpty()) {

            /* Get the next board in the open set that has the lowest f-cost. */
            ExplicitGrid board = openSetRef.poll();

            /* Check if this node is in both open sets. */
            if (forward && openSetBackward.contains(board)) {
                /*
                 * Merge the two paths and record the length. If we are in this block, we are
                 * currently in the forward search. The parents of `board` will eventually lead
                 * us back to the scrambled initial configuration.
                 */

                ExplicitGrid boardBackward = null;

                for (ExplicitGrid boardBackwardCandidate : openSetBackward) {
                    boardBackward = boardBackwardCandidate;

                    /* We have found the matching board. */
                    if (boardBackward.equals(board)) {
                        break;
                    }
                }

                /* Trace the ancestry of the two boards and return a nicely patched solution. */
                mergePaths(board, boardBackward, true);

                /* If we allow suboptimal solutions, return now if possible. */
                if (this.uPath != null && this.allowUnoptimalSolution) {
                    return constructPath(this.uPath);
                }

            } else if (!forward && openSetForward.contains(board)) {
                /*
                 * Merge the two paths and record the length. If we are in this block, we are
                 * currently in the forward search. The parents of `board` will eventually lead
                 * us back to the scrambled initial configuration.
                 */

                ExplicitGrid boardForward = null;

                for (ExplicitGrid boardForwardCandidate : openSetForward) {
                    boardForward = boardForwardCandidate;

                    /* We have found the matching board. */
                    if (boardForward.equals(board)) {
                        break;
                    }
                }

                /* Trace the ancestry of the two boards and return a nicely patched solution. */
                mergePaths(boardForward, board, true);

                /* If we allow suboptimal solutions, return now if possible. */
                if (this.uPath != null && this.allowUnoptimalSolution) {
                    return constructPath(this.uPath);
                }
            }

            /* Add the current node to the closed set. */
            closedSetRef.add(board);

            /* If this node is terminal, we have found the solution. */
            if (board.isTerminal()) {
                System.out.println(exploredNodes);
                return constructPath(board);
            }

            /* Get all the neighbors. */
            LinkedList<ExplicitGrid> neighbors = board.getNeighbors();

            exploredNodes += neighbors.size();

            /* Put this board into the closed set. */
            openSetRef.remove(board);
            closedSetRef.add(board);

            for (ExplicitGrid neighbor : neighbors) {
                /* Capture some information about the node. */
                boolean isOpen = openSetRef.contains(neighbor);
                boolean isClosed = openSetRef.contains(neighbor);

                /* Ignore nodes in the closed list. */
                if (isClosed) {
                    continue;
                }

                /* If node is not in open set, add it. */
                if (!isOpen) {
                    openSetRef.add(neighbor);
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

            /* Alternate the searches. */
            if (forward) {
                openSetRef = openSetBackward;
                closedSetRef = closedSetBackward;
            } else {
                openSetRef = openSetForward;
                closedSetRef = closedSetForward;
            }

            forward = !forward;
        }
        return null; // TODO In future this should return Optional.empty().
    }

    /**
     * Takes two partial paths (forward from source to target, backward from target
     * to source) and returns a single (forward) path.
     *
     * @param forwardBoard  The partial path from the source to :sithe target
     * @param backwardBoard The partial path from the target to the source
     * @param forward       Determines whether this call was made from a backward or
     *                      forward search
     * @return A path from source to target
     */
    private List<ExplicitGrid> mergePaths(ExplicitGrid forwardBoard, ExplicitGrid backwardBoard, boolean forward) {

        List<ExplicitGrid> sourceToTarget = new ArrayList<>();

        /* Handle the case where this method is called from a forward search. */
        if (forward) {

            /* Add the partial path from source to target. */
            while (forwardBoard.getParent() != null) {
                sourceToTarget.add(forwardBoard);
                forwardBoard = forwardBoard.getParent();
            }

            sourceToTarget.add(forwardBoard); // TODO There is a nicer way to add this dangling child.

            /* Reverse the source to target path for sanity sake. */
            Collections.reverse(sourceToTarget);

            /*
             * Add the partial path from the target to the source. We are skipping the first
             * instance of backwardBoard, since it's already part of the path.
             */
            backwardBoard = backwardBoard.getParent();
            while (backwardBoard.getParent() != null) {
                sourceToTarget.add(backwardBoard);
                backwardBoard = backwardBoard.getParent();
            }

            sourceToTarget.add(backwardBoard); // TODO There is a nicer way to add this dangling child.

        } else {
            /* Handle the case where this method is called from a backward search. */
            System.out.println("Backwards");
        }

        /* Reverse the path to get the terminal node on top. */
        Collections.reverse(sourceToTarget);

        /* Create the new root board. */
        ExplicitGrid solutionBoard = new ExplicitGrid(sourceToTarget.get(0).getGrid(), this.initialPlayerPosition,
                this.goalPosition, heuristic);
        solutionBoard.setCostFromStart(sourceToTarget.size());

        ExplicitGrid solutionBoardRef = solutionBoard;

        /* Step through each element in the list and set up the parental chain. */
        for (ExplicitGrid board : sourceToTarget.subList(1, sourceToTarget.size() - 1)) {
            solutionBoardRef.setParent(board);
            solutionBoardRef = solutionBoardRef.getParent();
        }

        /* See if we've found a shorter path. */
        if (this.uPath == null) {
            /* This is the first path found. */
            this.uPath = solutionBoard;

            /* If we are allow suboptimal solutions, we can call constructPath. */
            if (this.allowUnoptimalSolution) {
                return null;
            }
        } else {
            /* This is not the first path found. */
            if (solutionBoard.getCostFromStart() < this.uPath.getCostFromStart()) {
                System.out.println("Found it!" + this.uPath.getCostFromStart());
            }
        }

        return sourceToTarget;
    }

    /**
     * Reconstruct the path from the source vertex to the target vertex.
     *
     * @param board The terminal node, as determined by A*.
     * @return An array of moves that when applied on the starting board, leads to
     *         the terminal board.
     */
    private Direction[] constructPath(ExplicitGrid board) {
        List<Direction> solution = new LinkedList<>();

        System.out.println(board);

        while (board.getParent() != null) {

            /* Grab a reference to the next state. */
            ExplicitGrid nextBoard = board.getParent();
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
    private Direction testMove(ExplicitGrid original, ExplicitGrid nextBoard) {

        Direction[] possibleMoves = new Direction[] { UP, LEFT, DOWN, RIGHT };

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
     * @return The number of nodes explored by this agent.
     */
    public int getExploredNodes() {
        return this.exploredNodes;
    }

}
