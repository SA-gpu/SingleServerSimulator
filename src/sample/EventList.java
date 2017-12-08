package sample;

import java.util.LinkedList;

public class EventList extends LinkedList {

    void enqueue(QueueEvent enqueueEvent) {
        add(priority(enqueueEvent), enqueueEvent);
    }

    QueueEvent dequeue() {
        return (QueueEvent) removeFirst();
    }

    private int priority(QueueEvent event) {

        for (int count = 0; count < size(); count++) {

            QueueEvent queueEvent = (QueueEvent) get(count);
            if (event.getTime() < queueEvent.getTime()) {
                return count;
            }
        }
        return size();
    }
}
