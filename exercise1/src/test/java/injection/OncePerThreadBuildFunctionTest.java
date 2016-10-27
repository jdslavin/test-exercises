package injection;

import helpers.FooThread;
import helpers.TestConnection;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OncePerThreadBuildFunctionTest {
    private final OncePerThreadBuildFunction<TestConnection> buildFunction
            = new OncePerThreadBuildFunction<>(new TestConnection());


    @Test
    public void build_always_returns_the_same_instance_on_a_single_thread() {
        TestConnection c_first = buildFunction.build();
        TestConnection c_second = buildFunction.build();
        assertEquals(c_second.Who(), c_first.Who());
    }

    @Test
    public void build_returns_a_new_instance_on_each_thread()
    {
        FooThread foo1 = new FooThread(buildFunction);
        FooThread foo2 = new FooThread(buildFunction);
        foo1.start();
        foo2.start();

        // wait for threads to end
        try {
            foo1.join();
            foo2.join();
        } catch( Exception e) {
            System.out.println("Interrupted");
        }

        String c_first = foo1.getWho();
        String c_second = foo2.getWho();
        assertEquals("TestConnection - 1", c_first);
        assertEquals("TestConnection - 2", c_second);

    }

}