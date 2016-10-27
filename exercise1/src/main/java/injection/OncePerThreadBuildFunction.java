package injection;

public class OncePerThreadBuildFunction<T> implements BuildFunction<T> {
    public final PerThreadI<T> perThread;

    public OncePerThreadBuildFunction(PerThreadI<T> perThread) {
        this.perThread = perThread;
    }

    @Override
    public T build() {
         if (perThread.get() == null)
         {
             perThread.set();
         }
        return perThread.get();
    }
}
