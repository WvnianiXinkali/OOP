// TabooTest.java
// Taboo class tests -- nothing provided.

import java.util.*;

import junit.framework.TestCase;

public class TabooTest extends TestCase {
    public void testTabooNormal (){
        List<String> rules = new ArrayList<String>();
        rules.add("a");
        rules.add("c");
        rules.add("a");
        rules.add("b");
        Taboo<String> taboo = new Taboo<String>(rules);
        Set<String> shouldreturn = new HashSet<String>();
        shouldreturn.add("c");
        shouldreturn.add("b");
        assertTrue(Arrays.deepEquals(shouldreturn.toArray(), taboo.noFollow("a").toArray()));
        assertTrue(Arrays.deepEquals(Collections.emptySet().toArray(), taboo.noFollow("x").toArray()));
    }
    public void testTabooNull (){
        List<String> rules = new ArrayList<String>();
        rules.add("a");
        rules.add("b");
        rules.add(null);
        rules.add("c");
        rules.add("b");
        rules.add("d");
        Taboo<String> taboo = new Taboo<String>(rules);
        Set<String> shouldreturn = new HashSet<String>();
        shouldreturn.add("d");
        assertTrue(Arrays.deepEquals(shouldreturn.toArray(), taboo.noFollow("b").toArray()));
    }

    public void testTabooReduce (){
        List<String> rules = new ArrayList<String>();
        rules.add("a");
        rules.add("c");
        rules.add("a");
        rules.add("b");
        Taboo<String> taboo = new Taboo<String>(rules);
        List<String> shouldreturn = new ArrayList<String>();
        shouldreturn.add("a");
        shouldreturn.add("x");
        shouldreturn.add("c");
        List<String> reduce = new ArrayList<String>();
        reduce.add("a");
        reduce.add("c");
        reduce.add("b");
        reduce.add("x");
        reduce.add("c");
        reduce.add("a");
        taboo.reduce(reduce);
        assertTrue(Arrays.deepEquals(reduce.toArray(), shouldreturn.toArray()));
    }
}
