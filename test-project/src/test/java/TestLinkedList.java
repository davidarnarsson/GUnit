/**
 * Created by davida on 20.1.2015.
 */


import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import edu.chl.linkedlist.LinkedList;
import org.junit.Before;
import org.junit.Test;


public class TestLinkedList {
    String a;
    String b;
    @Before
    public void setup() {
        b = null;
        a = "2";

        System.out.println("This is before");
    }


    @Test
    public void testRemoveAt() {
        LinkedList<String> ll = new LinkedList<>();
        ll.insert("derp");
        ll.insert("derp2");
        ll.removeAt(0);

        assertEquals("last should be first now", "derp2", ll.get(0));
        assertTrue(ll.size() == 1);
    }

    @Test
    public void testHerpderpNewTest() {

    }

    @Test
    public void testLinkedListInsert() {
        LinkedList<String> ll = new LinkedList<String>();

        String actual = "derp";
        ll.insert(actual);
        String d = ll.get(0);

        assertEquals("First inserted should equal first gotten", actual, d);
    }

    @Test
    public void testLinkedListInsertMultiple() {
        LinkedList<String> ll = new LinkedList<String>();

        String actual = "derp";

        for (int i = 0; i < 10; ++i) {
            ll.insert(String.format("%s%d", actual, i));
        }

        for (int i = 0; i <10; ++i) {
            assertEquals(ll.get(i), String.format("%s%d", actual, i));
        }
    }


    @Test
    public void testGetLinkedListNonExistent() {
        LinkedList<String> ll = new LinkedList<>();
        assertThat("Non-existant get should return null",null,is(equalTo(ll.get(1))));
    }

    @Test
    public void testLinkedListSizeEmpty() {
        LinkedList<String> ll = new LinkedList<String>();

        assertEquals("Empty should have size 0", ll.size(), 0);
    }

    @Test
    public void testLinkedListSizeNonEmpty() {
        LinkedList<String> ll = new LinkedList<String>();
        for (int i = 0; i < 10; ++i) {
            ll.insert("derp");
        }
        assertEquals("edu.david.linkedlist.LinkedList with 10 nodes should have size 10", ll.size(), 10);
    }

    @Test
    public void testLinkedListRemove() {
        LinkedList<String> ll = new LinkedList<String>();

        ll.insert("derp");
        assertThat("size before removal", ll.size(), is(1));
        ll.remove("derp");
        assertThat("size after removal", ll.size(), is(0));
    }
}
