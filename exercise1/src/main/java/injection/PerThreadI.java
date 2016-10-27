package injection;

public interface PerThreadI<T> {
    T get ();
    void set();
}
