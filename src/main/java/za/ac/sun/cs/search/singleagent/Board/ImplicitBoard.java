package za.ac.sun.cs.search.singleagent.Board;

public class ImplicitBoard extends Board {
    protected Direction previousMove;
    /**
     * Initialize the internal state of the board and calculate the size of it.
     *
     * @param initialState An array of the initial tile configuration, as read from left to right and top to bottom.
     */
    public ImplicitBoard(short[] initialState) {
        super(initialState);
        this.previousMove = null;
    }

    public ImplicitBoard swapTiles(int fromRow, int fromCol, int toRow, int toCol) {
        int tempTile = this.getAt(toRow, toCol);
        ImplicitBoard temp = new ImplicitBoard(this.getCurrentState());
        temp.putAt(toRow, toCol, (short) 0);
        temp.putAt(fromRow, fromCol, (short) tempTile);

        return temp;
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



    public Direction getPrevious() {
        return previousMove;
    }

    public void setPreviousMove(Direction move) {
        this.previousMove = move;
    }
}
