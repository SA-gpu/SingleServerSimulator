package sample;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class QueuingSystem {

    private static final int MAX_FLOORS = 5;
    private LinkedList<QueueEvent>[] queues;

    QueuingSystem() {
        this.queues = new LinkedList[MAX_FLOORS];
        for (int count = 0; count < queues.length; count++) {
            queues[count] = new LinkedList<>();
        }
    }

    QueueEvent dequeue(int floor) {
        try {
            return queues[floor].removeFirst();
        }
        catch (NoSuchElementException e) {
            return null;
        }
    }

    void enqueue(int floor, QueueEvent event) {
        queues[floor].add(event);
    }

    int length(int floor) {
        return queues[floor].size();
    }
}
