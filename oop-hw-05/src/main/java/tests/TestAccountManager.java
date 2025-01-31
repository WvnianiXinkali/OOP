package tests;

import junit.framework.TestCase;
import task1.AccountManager;

public class TestAccountManager extends TestCase {
    private AccountManager acc;
    protected void setUp() {
        acc = new AccountManager();
    }

    public void testAddittion(){
        acc.addToMap("gio", "gio");
        assertTrue(acc.containsMap("gio"));
        assertTrue(acc.checkPass("gio","gio"));
        assertTrue(!acc.checkPass("gio","merabi"));
    }
    public void testDefault(){
        assertTrue(acc.containsMap("Patrick"));
        assertTrue(acc.containsMap("Molly"));
        assertTrue(acc.checkPass("Patrick","1234"));
        assertTrue(acc.checkPass("Molly","FloPup"));
    }
}
