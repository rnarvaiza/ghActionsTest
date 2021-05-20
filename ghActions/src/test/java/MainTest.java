import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MainTest {


    /********************************
     ******  @Mock Annotation  ******
     *******************************/

    @Test
    @Order(1)
    void whenNotUseMockAnnotation_thenCorrect() {
        List<String> mockList = Mockito.mock(ArrayList.class);

        mockList.add("one");
        Mockito.verify(mockList).add("one");
        assertEquals(0, mockList.size());

        Mockito.when(mockList.size()).thenReturn(100);
        assertEquals(100, mockList.size());
    }

    @Mock
    List<String> mockedList;

    @Test
    @Order(2)
    void whenUseMockAnnotation_thenMockIsInjected() {
        mockedList.add("one");
        Mockito.verify(mockedList).add("one");
        assertEquals(0, mockedList.size());

        Mockito.when(mockedList.size()).thenReturn(100);
        assertEquals(100, mockedList.size());
    }

    /********************************
     ******  @Spy Annotation  ******
     *******************************/

    @Test
    @Order(3)
    void whenNotUseSpyAnnotation_thenCorrect() {
        List<String> spyList = Mockito.spy(new ArrayList<>());

        spyList.add("one");
        spyList.add("two");

        Mockito.verify(spyList).add("one");
        Mockito.verify(spyList).add("two");

        assertEquals(2, spyList.size());

        Mockito.doReturn(100).when(spyList).size();
        assertEquals(100, spyList.size());
    }

    @Spy
    List<String> spiedList = new ArrayList<>();

    @Test
    @Order(4)
    public void whenUseSpyAnnotation_thenSpyIsInjectedCorrectly() {
        spiedList.add("one");
        spiedList.add("two");

        Mockito.verify(spiedList).add("one");
        Mockito.verify(spiedList).add("two");

        assertEquals(2, spiedList.size());

        Mockito.doReturn(100).when(spiedList).size();
        assertEquals(100, spiedList.size());
    }

    /********************************
     ******  @Captor Annotation  ******
     *******************************/

    @Test
    @Order(5)
    public void whenNotUseCaptorAnnotation_thenCaptureListParameters() {
        List<String> list = Mockito.mock(ArrayList.class);
        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);

        list.add("one");
        list.add("two");

        Mockito.verify(list, Mockito.times(2)).add(valueCaptor.capture());

        List<String> allValues = valueCaptor.getAllValues();

        assertTrue(allValues.contains("one"));
        assertTrue(allValues.contains("two"));

        assertEquals("two", valueCaptor.getValue());
    }

    @Mock
    private List<String> list;

    @Captor
    private ArgumentCaptor<String> valueCaptor;

    @Test
    @Order(6)
    public void whenUseCaptorAnnotation_thenCaptureListParameters() {
        list.add("one");
        list.add("two");

        Mockito.verify(list, Mockito.times(2)).add(valueCaptor.capture());

        List<String> allValues = valueCaptor.getAllValues();

        assertTrue(allValues.contains("one"));
        assertTrue(allValues.contains("two"));

        assertEquals("two", valueCaptor.getValue());
    }

}
