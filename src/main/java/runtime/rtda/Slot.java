package runtime.rtda;


public class Slot<T> {

    private T element;

    Slot(T element) {
        this.element = element;
    }

    public void set(T element) {
        this.element = element;
    }

    public T get() {
        return element;
    }

}
