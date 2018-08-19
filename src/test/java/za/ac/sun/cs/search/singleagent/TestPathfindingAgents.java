package za.ac.sun.cs.search.singleagent;

import org.junit.Before;

public class TestPathfindingAgents {

    short[] playerPos;
    short[] goalPos;
    boolean[][] configuration;

    @Before
    public void setupConfig() {

        playerPos = new short[]{0, 20};
        goalPos = new short[]{39, 20};

        configuration = new boolean[40][40];

        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 40; j++) {
                configuration[i][j] = true;
            }
        }

        /* The horizontal wall. */
        int obstacle1xStart = 10;
        int obstacle1xEnd = 12;

        int obstacle1yStart = 2;
        int obstacle1yEnd = 30;

        /* The vertical wall. */
        int obstacle2xStart = 0;
        int obstacle2xEnd = 10;

        int obstacle2yStart = 27;
        int obstacle2yEnd = 30;

        /* Install the walls, so to speak. */
        for (int i = obstacle1xStart; i < obstacle1xEnd; i++) {
            for (int j = obstacle1yStart; j < obstacle1yEnd; j++) {
                configuration[i][j] = false;
            }
        }

        for (int i = obstacle2xStart; i < obstacle2xEnd; i++) {
            for (int j = obstacle2yStart; j < obstacle2yEnd; j++) {
                configuration[i][j] = false;
            }
        }


    }
}
