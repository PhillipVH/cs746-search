package za.ac.sun.cs.search.singleagent.Agent;

import za.ac.sun.cs.search.singleagent.Board.Direction;
import za.ac.sun.cs.search.singleagent.Board.ExplicitBoard;
import za.ac.sun.cs.search.singleagent.Heuristic.Heuristic;

import java.util.*;
import java.util.logging.Logger;

import static za.ac.sun.cs.search.singleagent.Board.Direction.*;

public class BidirectionalAStarAgent implements Agent {
    private short[] initialState;

    /* Two boards, one for each of the forward and backward searches. */
    private ExplicitBoard startBoardForward;
    private ExplicitBoard startBoardBackward;

    private ExplicitBoard uPath = null;
    private boolean allowUnoptimalSolution;

    /* A counter to keep track of the number of nodes explored. */
    int exploredNodes = 0;

    private Heuristic heuristic;

    public BidirectionalAStarAgent(short[] configuration, boolean allowUnoptimalSolution, Heuristic heuristic) {
        this.initialState = Arrays.copyOf(configuration, configuration.length);
        this.allowUnoptimalSolution = allowUnoptimalSolution;

        /* First we create a new board for the forward search to start at.
         * We will use the goal state generated by the single argument ctor
         * to initialize the node for the backwards search.
         */
        this.startBoardForward = new ExplicitBoard(this.initialState, heuristic);

        short[] initialStateBackward = Arrays.copyOf(this.startBoardForward.getGoalState(), this.startBoardForward.getGoalState().length);

        /* The node for the backwards search now has the traditional goal state
         * as it's starting state, and the initial state as it's goal state.
         */
        this.startBoardBackward = new ExplicitBoard(initialStateBackward, initialState, heuristic);

        this.heuristic = heuristic;

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

        /* Start by creating the frontiers. Both the forward and backwards searches get their own open and closed sets.*/
        PriorityQueue<ExplicitBoard> openSetForward = new PriorityQueue<>(explicitBoardComparator);
        HashSet<ExplicitBoard> closedSetForward = new HashSet<>();

        PriorityQueue<ExplicitBoard> openSetBackward = new PriorityQueue<>(explicitBoardComparator);
        HashSet<ExplicitBoard> closedSetBackward = new HashSet<>();

        /* Set up our initial nodes. */
        this.startBoardForward.setCostFromStart(0);
        this.startBoardBackward.setCostFromStart(0);

        this.startBoardForward.setParent(null);
        this.startBoardBackward.setParent(null);

        openSetForward.add(startBoardForward);
        openSetBackward.add(startBoardBackward);

        /* The searches get a change to run in round robin fashion. */
        PriorityQueue<ExplicitBoard> openSetRef = openSetForward;
        HashSet<ExplicitBoard> closedSetRef = closedSetForward;

        boolean forward = true;

        /* While there is still world to explore, explore it! */
        while (!openSetForward.isEmpty()) {

            /* Get the next board in the open set that has the lowest f-cost. */
            ExplicitBoard board = openSetRef.poll();

            /* Check if this node is in both open sets. */
            if (forward && openSetBackward.contains(board)) {
                /* Merge the two paths and record the length. If we are in this block,
                 * we are currently in the forward search. The parents of `board` will
                 * eventually lead us back to the scrambled initial configuration.
                 */

                ExplicitBoard boardBackward = null;

                for (ExplicitBoard boardBackwardCandidate : openSetBackward) {
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
                /* Merge the two paths and record the length. If we are in this block,
                 * we are currently in the forward search. The parents of `board` will
                 * eventually lead us back to the scrambled initial configuration.
                 */

                ExplicitBoard boardForward = null;

                for (ExplicitBoard boardForwardCandidate : openSetForward) {
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
            LinkedList<ExplicitBoard> neighbors = board.getNeighbors();

            exploredNodes += neighbors.size();

            /* Put this board into the closed set. */
            openSetRef.remove(board);
            closedSetRef.add(board);

            for (ExplicitBoard neighbor : neighbors) {
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
     * Takes two partial paths (forward from source to target, backward from target to source) and returns a single
     * (forward) path.
     *
     * @param forwardBoard  The partial path from the source to :sithe target
     * @param backwardBoard The partial path from the target to the source
     * @param forward       Determines whether this call was made from a backward or forward search
     * @return A path from source to target
     */
    private List<ExplicitBoard> mergePaths(ExplicitBoard forwardBoard, ExplicitBoard backwardBoard, boolean forward) {

        List<ExplicitBoard> sourceToTarget = new ArrayList<>();

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

            /* Add the partial path from the target to the source.
             * We are skipping the first instance of backwardBoard, since it's already part of the path.
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
        ExplicitBoard solutionBoard = new ExplicitBoard(sourceToTarget.get(0).getCurrentState(), heuristic);
        solutionBoard.setCostFromStart(sourceToTarget.size());

        ExplicitBoard solutionBoardRef = solutionBoard;

        /* Step through each element in the list and set up the parental chain. */
        for (ExplicitBoard board : sourceToTarget.subList(1, sourceToTarget.size() - 1)) {
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

        Direction[] possibleMoves = new Direction[]{UP, LEFT, DOWN, RIGHT};

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
