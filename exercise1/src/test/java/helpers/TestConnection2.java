package helpers;

import injection.PerThreadI;

public class TestConnection2 implements ConnectionI, PerThreadI<TestConnection2> {
    private static int nextId = 0;
    private static final ThreadLocal<TestConnection2> connection =
            new ThreadLocal<>();


    private final int threadID;

    @Override
    public TestConnection2 get() {
        return connection.get();
    }

    @Override
    public void set() {
        connection.set(new TestConnection2());
    }

    public TestConnection2() {
        this.threadID = nextId;
        nextId = nextId++;
    }

    @Override
    public String Who() {
        return "TestConnection2 - " + threadID;
    }
}
