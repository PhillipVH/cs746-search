package za.ac.sun.cs.search.singleagent;

import za.ac.sun.cs.search.singleagent.Agent.AStarGridAgent;
import za.ac.sun.cs.search.singleagent.Board.Direction;
import za.ac.sun.cs.search.singleagent.Grid.ExplicitGrid;
import za.ac.sun.cs.search.singleagent.Heuristic.EucledianHeuristic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GridRepl {
    public static void main(String[] args) {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        /* Read in the size of the grid, NxN. */
        System.out.println("Enter grid size (N): ");
        int N = 0;
        try {
            N = Integer.parseInt(input.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* Initialize the grid configuration. */
        boolean configuration[][] = new boolean[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                configuration[i][j] = true;
            }
        }

        /* Place the starting and ending position. */
        System.out.println("Enter player x-position: ");
        int playerX = 0;
        try {
            playerX = Integer.parseInt(input.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Enter player y-position: ");
        int playerY = 0;
        try {
            playerY = Integer.parseInt(input.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Enter goal x-position: ");
        int goalX = 0;
        try {
            goalX = Integer.parseInt(input.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Enter goal y-position: ");
        int goalY = 0;
        try {
            goalY = Integer.parseInt(input.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* Read in the obstacles */
        while (true) {
            System.out.println("Enter x-start of obstacle (or skip to continue): ");
            int obstacleX = 0;
            int obstacleXEnd = 0;
            try {
                obstacleX = Integer.parseInt(input.readLine());
                obstacleXEnd = Integer.parseInt(input.readLine());
            } catch (IOException | NumberFormatException e) {
                break;
            }

            System.out.println("Enter y-start of obstacle (or skip to continue): ");
            int obstacleY = 0;
            int obstacleYEnd = 0;
            try {
                obstacleY = Integer.parseInt(input.readLine());
                obstacleYEnd = Integer.parseInt(input.readLine());

            } catch (IOException | NumberFormatException e) {
                break;
            }

            for (int i = obstacleX; i < obstacleXEnd; i++) {
                for (int j = obstacleY; j < obstacleYEnd; j++) {
                    configuration[i][j] = false;
                }
            }
        }


        /* Create the grid and display it. */
        short[] playerPos = new short[]{(short) playerX, (short) playerY};
        short[] goalPos = new short[]{(short) goalX, (short) goalY};

        short[] originalPlayerPos = Arrays.copyOf(playerPos, playerPos.length);

        /* Create the agent and get the solution. */
        AStarGridAgent aStarGridAgent = new AStarGridAgent(Arrays.copyOf(configuration, configuration.length), playerPos, goalPos, new EucledianHeuristic());

        Direction[] solution = aStarGridAgent.solve();

        /* Paint the grid with the solution. */
        List<short[]> playerPositions = new ArrayList<>();
        playerPositions.add(Arrays.copyOf(playerPos, playerPos.length));

        for (Direction move : solution) {
            switch (move) {
                case UP:
                    playerPos[0] = (short) (playerPos[0] - 1);
                    playerPositions.add(Arrays.copyOf(playerPos, playerPos.length));
                    break;
                case DOWN:
                    playerPos[0] = (short) (playerPos[0] + 1);
                    playerPositions.add(Arrays.copyOf(playerPos, playerPos.length));
                    break;
                case LEFT:
                    playerPos[1] = (short) (playerPos[1] - 1);
                    playerPositions.add(Arrays.copyOf(playerPos, playerPos.length));
                    break;
                case RIGHT:
                    playerPos[1] = (short) (playerPos[1] + 1);
                    playerPositions.add(Arrays.copyOf(playerPos, playerPos.length));
                    break;
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                boolean isPlayerPos = (originalPlayerPos[0] == i) && (originalPlayerPos[1] == j);
                boolean isGoalPos = (goalPos[0] == i) && (goalPos[1] == j);
                boolean isWalkable = configuration[i][j];
                boolean isSolutionStep = false;

                for (short[] solutionStep : playerPositions) {
                    if (solutionStep[1] == j)
                        if (solutionStep[0] == i) {
                            isSolutionStep = true;
                            break;
                        }
                }

                if (isPlayerPos) {
                    System.out.print("S\t");
                    continue;
                }

                if (isGoalPos) {
                    System.out.print("G\t");
                    continue;
                }

                if (isSolutionStep) {
                    System.out.print("x\t");
                    continue;
                }

                if (isWalkable) {
                    System.out.print("_\t");
                    continue;
                } else {
                    System.out.print("O\t");
                    continue;
                }

            }
            System.out.println();
        }
    }
}
