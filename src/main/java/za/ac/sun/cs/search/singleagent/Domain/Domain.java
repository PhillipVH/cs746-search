package za.ac.sun.cs.search.singleagent.Domain;

public interface Domain {
    short getAt(int i, int j);

    int getSize();

    short[] getGoalState();

    default short[] getPlayerPosition() {
        return null;
    }
}