package sample;

public class Elevator {

    private static final int CAPACITY = 5;
    private static final int EMPTY = -1;
    int currentFloor;
    int space;
    private int[] destinations;
    private boolean moving;

    private int direction;
    private double[] moveProbabilities = {0.2, 0.2, 0.2, 0.2, 0.2};

    Elevator() {
        destinations = new int[CAPACITY];
        for (int count = 0; count < destinations.length; count++) {
            destinations[count] = EMPTY;
        }
        space = CAPACITY;
        moving = false;
        currentFloor = 0;
        direction = 1;
    }

    boolean hasSpace() {
        return space > 0;
    }

    int getFloor() {
        return currentFloor;
    }

    void move() {
        moving = true;

        if (currentFloor == 4) {
            direction = -1;
        } else if (currentFloor == 0) {
            direction = 1;
        }
        currentFloor += direction;
    }

    void stop() {
        moving = false;
    }

    void load() {

        for (int count = 0; count < destinations.length; count++) {
            if (destinations[count] == EMPTY) {
                destinations[count] = selectFloor();
                break;
            }
        }
        space--;
    }

    private int selectFloor() {

        // Build transition matrix and generate probabilities
        double selection = Math.random();
        double cumulativeSum = 0;

        for (int count = 0; count < 5; count++) {
            if (selection <= (cumulativeSum + moveProbabilities[count])) {
                return count;
            }
            cumulativeSum += moveProbabilities[count];
        }
        return 0;
    }

    void unload() {

        for (int count = 0; count < destinations.length; count++) {
            if (currentFloor == destinations[count]) {
                destinations[count] = EMPTY;
                space++;
            }
        }
    }

    boolean inMotion() {
        return moving;
    }
}
