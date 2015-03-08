package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Queue<T> {

//    private List<T> queue = new CopyOnWriteArrayList<T>();
    private List<T> queue = new ArrayList<T>();


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
//                    e.printStackTrace();
//                    System.out.println("queue.wait() InterruptedException");
                }
            }
            return queue.remove(0);
        }
    }

    public boolean isEmpty() {
//        synchronized (queue) {
            return queue.isEmpty();
//        }
    }
}
