package za.ac.sun.cs.search.singleagent.Domain.Board;

public class ImplicitBoard extends Board {
    protected Direction previousMove;

    /**
     * Initialize the internal state of the board and calculate the size of it.
     *
     * @param initialState An array of the initial tile configuration, as read from
     *                     left to right and top to bottom.
     */
    public ImplicitBoard(short[] initialState) {
        super(initialState);
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
        short[] emptyPosition = this.getEmptyTilePosition();
        switch (move) {
        case UP:
            swapTiles(emptyPosition[0], emptyPosition[1], emptyPosition[0] - 1, emptyPosition[1]);
            break;
        case DOWN:
            swapTiles(emptyPosition[0], emptyPosition[1], emptyPosition[0] + 1, emptyPosition[1]);
            break;
        case LEFT:
            swapTiles(emptyPosition[0], emptyPosition[1], emptyPosition[0], emptyPosition[1] - 1);
            break;
        case RIGHT:
            swapTiles(emptyPosition[0], emptyPosition[1], emptyPosition[0], emptyPosition[1] + 1);
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

    public void swapTiles(int fromRow, int fromCol, int toRow, int toCol) {
        int tempTile = this.getAt(toRow, toCol);
        this.putAt(toRow, toCol, (short) 0);
        this.putAt(fromRow, fromCol, (short) tempTile);

        return;
    }

    public Direction getPrevious() {
        return previousMove;
    }

    public void setPreviousMove(Direction move) {
        this.previousMove = move;
    }

    public void visualizePath(Direction[] path) throws Exception {
        System.out.println("Initial Board:");
        System.out.println(this);
        for (Direction move : path) {
            System.out.println("Move: " + move);
            this.makeMove(move);
            System.out.println(this);
            Thread.sleep(500);

        }
    }
}
