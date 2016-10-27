package injection;

public class NewInstanceBuildFunction<T> implements BuildFunction<T> {
    private final Class<? extends T> clz;

    public NewInstanceBuildFunction(Class<? extends T> clz) {
        this.clz = clz;
    }

    @Override
    public T build() {
        try {
            return clz.newInstance();
        } catch (Exception ex) {
        }
        return null;
    }
}
