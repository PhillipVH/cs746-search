package za.ac.sun.cs.search.singleagent;

import java.util.*;

public class AStarAgent implements Agent {
    private short[] initialState;
    private short[] goalState;

    public AStarAgent(short[] configuration) {
        this.initialState = configuration;
    }

    public LinkedList<Board> findPath(Board startBoard) {

        LinkedList<Board> closedList = new LinkedList<>();
        PriorityQueue<Board>  openSet = new PriorityQueue<>();

        startBoard.setCostFromStart(0);
    }


    /**
     * Solve the given N-puzzle using A* on an explicit tree.
     *
     * @return An array of {@link Direction}s that when applied to the
     * initial state result in the goal state.
     */
    @Override
    public Direction[] solve() {

        ExplicitBoard start = new ExplicitBoard(initialState);

        TreeSet<ExplicitBoard> closedSet = new TreeSet<>();

        TreeSet<ExplicitBoard> openSet = new TreeSet<>();
        openSet.add(start);

        HashMap<ExplicitBoard, ExplicitBoard> cameFrom = new HashMap<>();

        HashMap<ExplicitBoard, Integer> gScore = new HashMap<>();

        gScore.put(start, 0);

        HashMap<ExplicitBoard, Integer> fScore = new HashMap<>();

        fScore.put(start, getHeuristicCostEstimate(start));

        while (!openSet.isEmpty()) {
            // TODO Integrate with fScore
            ExplicitBoard current = openSet.first();

            if (current.isTerminal()) {
                LinkedList<ExplicitBoard> solution = reconstructPath(cameFrom, current);

                for (ExplicitBoard board : solution) {
                    System.out.println(board.toString());
                }
                return null;
            }

            openSet.remove(current);
            closedSet.add(current);

            Direction[] legalMoves = current.getLegalMoves();

            for (Direction move : legalMoves) {
                ExplicitBoard neighbor = current.makeMove(move);

                if (closedSet.contains(neighbor)) {
                    continue;
                }

                int tentative_gScore = gScore.get(current) + 1; // distanceBetween(current, neighbor)

                if (!openSet.contains(neighbor)) {
                    openSet.add(neighbor);
                } else if (tentative_gScore >= gScore.get(neighbor)) {
                    continue;
                }

                cameFrom.put(neighbor, current);
                gScore.put(neighbor, tentative_gScore);
                fScore.put(neighbor, gScore.get(neighbor) + getHeuristicCostEstimate(neighbor));
            }

        }

        return null;
    }

    private LinkedList<ExplicitBoard> reconstructPath(HashMap<ExplicitBoard, ExplicitBoard> cameFrom, ExplicitBoard current) {
        LinkedList<ExplicitBoard> totalPath = new LinkedList<>();
        totalPath.add(current);

        while (cameFrom.keySet().contains(current)) {
            current = cameFrom.get(current);
            totalPath.add(current);
        }

        return totalPath;

    }

    private Integer getHeuristicCostEstimate(Board board) {
        int cost = 0;

        int idx = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.getAt(i, j) != goalState[idx++]) {
                    cost += 1;
                }
            }
        }

        return cost;
    }

}
