package za.ac.sun.cs.search.singleagent.Board;

import java.lang.Thread;

import java.util.ArrayList;
import java.util.Arrays;


public abstract class Board implements Comparable<Board>, Domain {

    protected short[] currentState;
    protected short[] goalState;

    private int N;
    private int cost;

    /**
     * Initialize the internal state of the board and calculate the size of it.
     *
     * @param initialState An array of the initial tile configuration, as read from left to right and top to bottom.
     */
    public Board(short[] initialState) {
        /* We are working under the assumption that N-puzzle boards are always square. */
        N = (int) Math.sqrt(initialState.length);

        assert Math.pow(N, 2) == initialState.length;

        /* Initialize the current state. */
        currentState = new short[initialState.length];

        /* Initialize the goal state */
        goalState = new short[initialState.length];

        /* Load the initial state into this Board instance. */
        for (short i = 0; i < initialState.length; i++) {
            currentState[i] = initialState[i];

            /* Setup the goal state */
            goalState[i] = i;
        }
    }

    /**
     * Initializes the internal state of the board. Unlike the single argument ctor, this ctor expects
     * an explicit goal state. This is mostly useful for bidirectional searches, as the backward searches
     * don't have the classic goal state.
     *
     * @param initialState The starting state of the board.
     * @param goalState The (explicit) goal state.
     */
    public Board(short[] initialState, short[] goalState) {

        /* Do a bit of a sanity check on the lengths of the inputs. */
        assert initialState.length == goalState.length;

        /* We are working under the assumption that N-puzzle boards are always square. */
        N = (int) Math.sqrt(initialState.length);

        assert Math.pow(N, 2) == initialState.length;

        /* Initialize the current state. */
        currentState = new short[initialState.length];

        /* Load the initial state into this Board instance. */
        for (short i = 0; i < initialState.length; i++) {
            currentState[i] = initialState[i];
        }
        currentState = initialState;

        /* Copy in the goal state. */
        this.goalState = Arrays.copyOf(goalState, goalState.length);

    }

    public int compareTo(Board other) {
        int thisValue = this.getCost();
        int otherValue = other.getCost();

        int v = thisValue - otherValue;

        return Integer.compare(v, 0);
    }

    public short[] getEmptyTilePosition() {
        short[] pos = new short[2];

        for (short i = 0; i < currentState.length; i++) {
            if (currentState[i] == 0) {
                pos[0] = (short) (i / N);
                pos[1] = (short) (i % N);
                break;
            }
        }

        return pos;
    }

    public Direction[] getLegalMoves() {

        ArrayList<Direction> legalMoves = new ArrayList<Direction>();
        short[] emptyPosition = getEmptyTilePosition();

        if (emptyPosition[0] > 0) {
            legalMoves.add(Direction.UP);
        }
        if (emptyPosition[0] < (N - 1)) {
            legalMoves.add(Direction.DOWN);
        }
        if (emptyPosition[1] > 0) {
            legalMoves.add(Direction.LEFT);
        }
        if (emptyPosition[1] < (N - 1)) {
            legalMoves.add(Direction.RIGHT);
        }

        return legalMoves.toArray(new Direction[legalMoves.size()]);
    }


    public int getCost() {
        return cost;
    }

    public short[] getGoalState() {
        return goalState;
    }

    public short[] getCurrentState() {
        return currentState;
    }

    public int getSize() {
        return N;
    }

    public short getAt(int i, int j) {
        return currentState[i * N + j];
    }

    public void putAt(int i, int j, short value) {
        currentState[i * N + j] = value;
    }

    public boolean isTerminal() {
        return Arrays.equals(currentState, goalState);
    }

    @Override
    public String toString() {
        StringBuilder outputBuilder = new StringBuilder();

        for (int i = 0; i < N; i++) {
            outputBuilder.append('[');
            for (int j = 0; j < N; j++) {
                outputBuilder.append(getAt(i, j));
                if (j != N - 1) {
                    outputBuilder.append(' ');
                }
            }
            outputBuilder.append(']');
            outputBuilder.append('\n');
        }

        return outputBuilder.toString();
    }
}
