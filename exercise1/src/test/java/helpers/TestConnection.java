package helpers;

import injection.PerThreadI;

import java.util.concurrent.atomic.AtomicInteger;

public class TestConnection implements ConnectionI, PerThreadI<TestConnection> {

    private static final AtomicInteger nextId = new AtomicInteger(0);

    // Thread local variable containing each thread's ID
    private static final ThreadLocal<Integer> threadId =
            new ThreadLocal<Integer>() {
                @Override protected Integer initialValue() {
                    return nextId.getAndIncrement();
                }
            };

    // Returns the current thread's unique ID, assigning it if necessary
    public static int getThreadID() {
        return threadId.get();
    }

    private static final ThreadLocal<TestConnection> connection =
            new ThreadLocal<>();

    @Override
    public TestConnection get() {
        return connection.get();
    }

    @Override
    public void set() {
        connection.set(new TestConnection());
    }

    @Override
    public String Who() {
        return "TestConnection - " + getThreadID();
    }

}
