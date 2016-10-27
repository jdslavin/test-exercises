package helpers;

import injection.OncePerThreadBuildFunction;

public class FooThread extends Thread {
    private String who;
    private Thread t;
    private final OncePerThreadBuildFunction<TestConnection> buildFunction;

    public FooThread(OncePerThreadBuildFunction<TestConnection> buildFunction) {

        this.buildFunction = buildFunction;
    }

    @Override
    public void run() {
        synchronized(buildFunction) {
            TestConnection conn = buildFunction.build();
            this.who = conn.Who();
        }
    }

    public String getWho() {
        return this.who;
    }
}

