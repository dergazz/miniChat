package entity;

public abstract class QueueManager<T> extends Thread {

    private Queue<T> queue = new Queue<T>();

    public void addToQueue(T element) {
        queue.add(element);
    }

    public abstract void actionOnElement(T element);

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            T element = queue.remove();
            actionOnElement(element);
        }
    }
}