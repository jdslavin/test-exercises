package injection;

import helpers.ConnectionI;
import helpers.TestConnection;
import helpers.TestConnection2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class InstanceFactoryTest {
    private final BuildFunction<ConnectionI> mockedBuildFunction = mock(BuildFunction.class);
    private final BuildFunction<ConnectionI> mockedBuildOverrideFunction = mock(BuildFunction.class);
    private final InstanceFactory<ConnectionI> if1 = new InstanceFactory<>(mockedBuildFunction);

    @Before
    public void init() {
        when(mockedBuildFunction.build()).thenReturn(new TestConnection());
        when(mockedBuildOverrideFunction.build()).thenReturn(new TestConnection2());
    }

    @After
    public void done() {
        if1.close();
    }

    @Test
    public void make_returns_an_object_returned_by_the_build_function() throws Exception {
        ConnectionI c_first = if1.make();

        assertEquals("TestConnection - 0", c_first.Who());
    }

    @Test
    public void make_can_be_overridden_in_as_a_resetable() {
        ConnectionI c_first = if1.make();
        Resettable r1 = if1.override(mockedBuildOverrideFunction);
        ConnectionI c_second = if1.make();
        r1.close();
        assertEquals("TestConnection - 0", c_first.Who());
        assertEquals("TestConnection2 - 0", c_second.Who());
    }

    @Test
    public void close_removes_the_overridebuilder() throws Exception {
        ConnectionI c_first = if1.make();
        Resettable r1 = if1.override(mockedBuildOverrideFunction);
        r1.close();
        ConnectionI c_second = if1.make();
        assertEquals("TestConnection - 0", c_first.Who());
        assertEquals("TestConnection - 0", c_second.Who());
    }

}