import junit.framework.TestCase;

import java.io.*;

public class TestBank extends TestCase {

    Bank bank10;
    Bank bank1;
    protected void setUp() throws Exception {
        bank1 = new Bank(1);
        bank10 = new Bank(10);
    }

    public void testMain1() throws IOException{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream customPrintStream = new PrintStream(outputStream);
        PrintStream originalPrintStream = System.out;
        System.setOut(customPrintStream);
        bank1.main(new String[] {"5k.txt", "1"});
        System.setOut(originalPrintStream);
        String capturedOutput = outputStream.toString();

        String[] output = capturedOutput.split("\n");

        for(int i = 0; i < output.length; i++){
            assertEquals("1000",output[i].split(" ")[1].split(":")[1]);
        }
    }
    public void testMain10() throws IOException{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream customPrintStream = new PrintStream(outputStream);
        PrintStream originalPrintStream = System.out;
        System.setOut(customPrintStream);
        bank10.main(new String[] {"5k.txt", "10"});
        System.setOut(originalPrintStream);
        String capturedOutput = outputStream.toString();

        String[] output = capturedOutput.split("\n");

        for(int i = 0; i < output.length; i++){
            assertEquals("1000",output[i].split(" ")[1].split(":")[1]);
        }
    }

    public void testMain10k() throws IOException{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream customPrintStream = new PrintStream(outputStream);
        PrintStream originalPrintStream = System.out;
        System.setOut(customPrintStream);
        bank10.main(new String[] {"100k.txt", "10"});
        System.setOut(originalPrintStream);
        String capturedOutput = outputStream.toString();

        String[] output = capturedOutput.split("\n");

        for(int i = 0; i < output.length; i++){
            assertEquals("1000",output[i].split(" ")[1].split(":")[1]);
        }
    }
    public void testTransaction(){
        Transaction transaction = new Transaction(10, 10, 1000);
        assertEquals("from:10 to:10 amt:1000", transaction.toString());
    }
}
