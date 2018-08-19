package za.ac.sun.cs.search.singleagent.Heuristic;

import za.ac.sun.cs.search.singleagent.Domain.Domain;

/* Linear conflict heuristic, built on top of the manhattan distance heurisitic. */

public class LinearConflictHeuristic implements Heuristic {

    @Override
    public int getHeuristicCostEstimate(Domain domain) {
        int cost = 0;
        int idx = 0;
        int N = domain.getSize();
        short[] goalState = domain.getGoalState();
        cost += linearVerticalConflict(domain, goalState);
        cost += linearHorizontalConflict(domain, goalState);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                short tile = domain.getAt(i, j);
                /* Don't count the blank tile. */
                if (tile == 0) {
                    idx++;
                    continue;
                }
                if (tile != goalState[idx++]) {
                    cost += getDistance(goalState, i, j, tile);
                }
            }
        }

        return cost;
    }

    private int getDistance(short[] goalState, int row, int column, short tile) {
        int cost = 0;
        int n = goalState.length;
        int size = (int) Math.sqrt(n);

        for (int i = 0; i < n; i++) {
            if (goalState[i] == tile) {
                cost += Math.abs((i / size) - row);
                cost += Math.abs((i % size) - column);
                return cost;
            }
        }

        return 0;
    }

    private short[] getGoalTilePosition(short[] goalState, short tile, int size) {
        short[] pos = new short[2];

        for (int i = 0; i < goalState.length; i++) {
            if (goalState[i] == tile) {
                pos[0] = (short) (i / size);
                pos[1] = (short) (i % size);
                return pos;
            }
        }

        return null;
    }

    private int linearVerticalConflict(Domain domain, short[] goalState) {
        int cost = 0;
        int n = goalState.length;
        int size = (int) Math.sqrt(n);

        for (int row = 0; row < size; row++) {
            short max = -1;
            for (int column = 0; column < size; column++) {
                short tile = domain.getAt(row, column);

                if (tile != 0 && getGoalTilePosition(goalState, tile, size)[1] == column) {
                    if (tile > max) {
                        max = tile;
                    } else {
                        cost += 1;
                    }
                }
            }
        }

        return cost;
    }

    private int linearHorizontalConflict(Domain domain, short[] goalState) {
        int cost = 0;
        int n = goalState.length;
        int size = (int) Math.sqrt(n);

        for (int column = 0; column < size; column++) {
            short max = -1;
            for (int row = 0; row < size; row++) {
                short tile = domain.getAt(row, column);

                if (tile != 0 && getGoalTilePosition(goalState, tile, size)[0] == row) {
                    if (tile > max) {
                        max = tile;
                    } else {
                        cost += 1;
                    }
                }
            }
        }

        return cost;
    }

}