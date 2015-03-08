package entity;

public interface InterfaceManager<T> {

    public void addElement(T element);

    public void removeElement(T element);

    public int getSize();

    public boolean isEmpty();
}
