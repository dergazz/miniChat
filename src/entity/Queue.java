package entity;

import java.util.ArrayList;
import java.util.List;

public class Queue<T> {
    List<T> queue = new ArrayList<T>();


    public boolean add(T element) {
        synchronized (queue) {
            queue.add(element);
            queue.notify();
            return true;
        }
    }

    public int getSize(){
        return queue.size();
    }

    public T remove() {
        synchronized (queue) {
            while (isEmpty()) {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    System.out.println("queue.wait() exception");
                }
            }
            return queue.remove(0);
        }
    }

    public boolean isEmpty() {
        synchronized (queue) {
            return queue.isEmpty();
        }
    }
}
