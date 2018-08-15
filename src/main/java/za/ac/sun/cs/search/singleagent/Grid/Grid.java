package za.ac.sun.cs.search.singleagent.Grid;

import za.ac.sun.cs.search.singleagent.Domain.Domain;
import za.ac.sun.cs.search.singleagent.Board.Direction;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Grid implements Domain {

    private boolean[][] grid;
    private short[] playerPosition;
    private short[] goalPosition;
    private int size;

    public Grid(boolean[][] initialState, short[] playerPosition, short[] goalPosition) {
        this.grid = initialState;
        this.playerPosition = playerPosition;
        this.goalPosition = goalPosition;
        this.size = initialState.length;
    }

    public short getAt(int i, int j) {
        return this.grid[i][j] ? (short) 1 : (short) 0;
    }

    public int getSize() {
        return this.size;
    }

    public short[] getGoalState() {
        return this.goalPosition;
    }

    public short[] getPlayerPosition() {
        return this.playerPosition;
    }

    public void setPlayerPostion(int i, int j) {
        this.playerPosition[0] = (short) i;
        this.playerPosition[1] = (short) j;
    }

    public boolean isTerminal() {
        return Arrays.equals(this.playerPosition, this.goalPosition);
    }

    @Override
    public String toString() {
        StringBuilder outputBuilder = new StringBuilder();

        for (int i = 0; i < this.size; i++) {
            outputBuilder.append('[');
            for (int j = 0; j < this.size; j++) {
                if (this.playerPosition[0] == i && this.playerPosition[1] == j) {
                    outputBuilder.append('P');
                } else if (this.goalPosition[0] == i && this.goalPosition[1] == j) {
                    outputBuilder.append('G');
                } else {
                    outputBuilder.append(getAt(i, j));
                }
                if (j != this.size - 1) {
                    outputBuilder.append(' ');
                }
            }
            outputBuilder.append(']');
            outputBuilder.append('\n');
        }

        return outputBuilder.toString();
    }

    public Direction[] getLegalMoves() {
        ArrayList<Direction> legalMoves = new ArrayList<Direction>();

        if (this.playerPosition[0] - 1 >= 0 && this.grid[this.playerPosition[0] - 1][this.playerPosition[1]] == true) {
            legalMoves.add(Direction.UP);
        }

        if (this.playerPosition[0] + 1 < this.size
                && this.grid[this.playerPosition[0] + 1][this.playerPosition[1]] == true) {
            legalMoves.add(Direction.DOWN);
        }

        if (this.playerPosition[1] - 1 >= 0 && this.grid[this.playerPosition[0]][this.playerPosition[1] - 1] == true) {
            legalMoves.add(Direction.LEFT);
        }

        if (this.playerPosition[1] + 1 < this.size
                && this.grid[this.playerPosition[0]][this.playerPosition[1] + 1] == true) {
            legalMoves.add(Direction.RIGHT);
        }

        return legalMoves.toArray(new Direction[0]);
    }
}
