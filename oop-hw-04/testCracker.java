import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class testCracker extends TestCase {
    Cracker cracker;
    protected void setUp() throws Exception {

    }

    public void testGeneration(){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream customPrintStream = new PrintStream(outputStream);
        PrintStream originalPrintStream = System.out;
        System.setOut(customPrintStream);
        Cracker.main(new String[] {"molly"});
        System.setOut(originalPrintStream);
        String capturedOutput = outputStream.toString();

        assertTrue("4181eecbd7a755d19fdf73887c54837cbecf63fd".equals(capturedOutput.replaceAll("\\s", "")));
    }

//    public void testCrack(){
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        PrintStream customPrintStream = new PrintStream(outputStream);
//        PrintStream originalPrintStream = System.out;
//        System.setOut(customPrintStream);
//        Cracker.main(new String[] {"4181eecbd7a755d19fdf73887c54837cbecf63fd", "5", "8"});
//        System.setOut(originalPrintStream);
//        String capturedOutput = outputStream.toString();
//
//        assertEquals("molly", capturedOutput.split("\n")[0].replaceAll("\\s", ""));
//        assertEquals("all done", capturedOutput.split("\n")[1].replaceAll("\\s", ""));
//    }

    public void testCrack1(){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream customPrintStream = new PrintStream(outputStream);
        PrintStream originalPrintStream = System.out;
        System.setOut(customPrintStream);
        Cracker.main(new String[] {"86f7e437faa5a7fce15d1ddcb9eaeaea377667b8", "2", "8"});
        System.setOut(originalPrintStream);
        String capturedOutput = outputStream.toString();

        assertEquals("a", capturedOutput.split("\n")[0].replaceAll("\\s", ""));
    }

    public void testCrack2(){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream customPrintStream = new PrintStream(outputStream);
        PrintStream originalPrintStream = System.out;
        System.setOut(customPrintStream);
        Cracker.main(new String[] {"adeb6f2a18fe33af368d91b09587b68e3abcb9a7", "2", "40"});
        System.setOut(originalPrintStream);
        String capturedOutput = outputStream.toString();

        assertEquals("fm", capturedOutput.split("\n")[0].replaceAll("\\s", ""));
    }

    public void testCrack3(){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream customPrintStream = new PrintStream(outputStream);
        PrintStream originalPrintStream = System.out;
        System.setOut(customPrintStream);
        Cracker.main(new String[] {"66b27417d37e024c46526c2f6d358a754fc552f3", "3", "5"});
        System.setOut(originalPrintStream);
        String capturedOutput = outputStream.toString();

        assertEquals("xyz", capturedOutput.split("\n")[0].replaceAll("\\s", ""));
    }

    public void testOther(){
        assertEquals("24a26f", Cracker.hexToString(Cracker.hexToArray("24a26f")));
    }
}
