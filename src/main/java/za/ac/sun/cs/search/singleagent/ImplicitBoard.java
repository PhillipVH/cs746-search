package za.ac.sun.cs.search.singleagent;

public class Implicit extends Board {
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

    @Override
    public ImplicitBoard makeMove(Direction move) {
        short[] emptyPosition = this.getEmptyTilePosition();
        
        switch (move) {
            case Direction.UP:
                return swapTiles(emptyPosition[0], emptyPosition[1], emptyPosition[0] - 1, emptyPosition[1]);
            case Direction.DOWN:
                return swapTiles(emptyPosition[0], emptyPosition[1], emptyPosition[0] + 1, emptyPosition[1]);
            case Direction.LEFT:
                return swapTiles(emptyPosition[0], emptyPosition[1], emptyPosition[0], emptyPosition[1] - 1);
            case Direction.RIGHT:
                return swapTiles(emptyPosition[0], emptyPosition[1], emptyPosition[0], emptyPosition[1] + 1);
            default:
                return null;
        }
    }

    @Override
    public ImplicitBoard undoMove(Direction move) {

        switch (move) {
            case Direction.UP:
                return makeMove(Direction.DOWN);
            case Direction.DOWN:
                return makeMove(Direction.UP);
            case Direction.LEFT:
                return makeMove(Direciton.RIGHT);
            case Direction.RIGHT:
                return makeMove(Direction.LEFT);
            default:
                return null;
        }
    }

    public ImplicitBoard swapTiles(int fromRow, int fromCol, int toRow, int toCol) {
        int tempTile = this.getAt(toRow, toCol);
        this.putAt(toRow, toCol, 0);
        this.putAt(fromRow, fromCol, tempTile);

        return new ImplicitBoard(this.getCurrentState);
    }



    public Direction getPrevious() {
        return previousMove;
    }

    public void setPreviousMove(Direction move) {
        this.previousMove = move;
    }
}
