package za.ac.sun.cs.search.singleagent.Grid;

import za.ac.sun.cs.search.singleagent.Board.Direction;

public class ImplicitGrid extends Grid {
    protected Direction previousMove;

    /**
     * Initialize the internal state of the grid and calculate the size of it. This
     * constructor initializes the goal state for us.
     *
     * @param initialState   An array of the initial grid configuration, as read
     *                       from left to right and top to bottom.
     * 
     * @param playerPosition Position of the player.
     * 
     * @param goalPosition   Position of the goal.
     * 
     * @param Heuristic      Heuristic function which will be used to evaluate
     *                       costs.
     * 
     */
    public ImplicitGrid(boolean[][] initialState, short[] playerPosition, short[] goalPosition) {
        super(initialState, playerPosition, goalPosition);
        this.previousMove = null;
    }

    public Direction reverseMove(Direction move) {
        switch (move) {
        case UP:
            return Direction.DOWN;
        case DOWN:
            return Direction.UP;
        case LEFT:
            return Direction.RIGHT;
        case RIGHT:
            return Direction.LEFT;
        default:
            return null;
        }
    }

    public void makeMove(Direction move) {
        short[] player = this.getPlayerPosition();
        switch (move) {
        case UP:
            this.setPlayerPostion(player[0] - 1, player[1]);
            break;
        case DOWN:
            this.setPlayerPostion(player[0] + 1, player[1]);
            break;
        case LEFT:
            this.setPlayerPostion(player[0], player[1] - 1);
            break;
        case RIGHT:
            this.setPlayerPostion(player[0], player[1] + 1);
            break;
        default:
            break;
        }
    }

    public void undoMove(Direction move) {
        switch (move) {
        case UP:
            makeMove(Direction.DOWN);
            break;
        case DOWN:
            makeMove(Direction.UP);
            break;
        case LEFT:
            makeMove(Direction.RIGHT);
            break;
        case RIGHT:
            makeMove(Direction.LEFT);
            break;
        default:
            return;
        }
    }

    public Direction getPrevious() {
        return previousMove;
    }

    public void setPreviousMove(Direction move) {
        this.previousMove = move;
    }

    public void visualizePath(Direction[] path) throws Exception {
        System.out.println("Initial Grid:");
        System.out.println(this);
        for (Direction move : path) {
            System.out.println("Move: " + move);
            this.makeMove(move);
            System.out.println(this);
            Thread.sleep(500);

        }
    }
}