import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

public class WebWorker extends Thread {
    private String url;
    private int rowNumber;
    private WebFrame webFrame;
    public WebWorker(String url, int rowNumber, WebFrame webFrame){
        this.url = url;
        this.rowNumber = rowNumber;
        this.webFrame = webFrame;
    }

    private void download() {
        InputStream input = null;
        StringBuilder contents = null;
        String k = url;
        try {
            long start = System.currentTimeMillis();
            URL url = new URL(k);
            URLConnection connection = url.openConnection();

            // Set connect() to throw an IOException
            // if connection does not succeed in this many msecs.
            connection.setConnectTimeout(5000);

            connection.connect();
            input = connection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            char[] array = new char[1000];
            int len;
            contents = new StringBuilder(1000);
            while ((len = reader.read(array, 0, array.length)) > 0) {
                if(isInterrupted()) break;
                contents.append(array, 0, len);
                Thread.sleep(100);
            }
            long end = System.currentTimeMillis();
            // Successful download if we get here
           webFrame.updateScreenSuccesful(end - start, contents.length(), rowNumber);
        }
        // Otherwise control jumps to a catch...
        catch (MalformedURLException ignored) {
            webFrame.updateScreenMallformed(rowNumber);
        } catch (InterruptedException exception) {
            // YOUR CODE HERE
            // deal with interruption
            webFrame.updateScreenInterrupted(rowNumber);
        } catch (IOException ignored) {
            webFrame.updateScreenMallformed(rowNumber);
        }
        // "finally" clause, to close the input stream
        // in any case
        finally {
            try {
                if (input != null) input.close();
            } catch (IOException ignored) {
            }
        }
    }


    @Override
    public void run() {
        webFrame.changeRunningThreadCnt(true);
        download();
        webFrame.changeRunningThreadCnt(false);
        webFrame.postSemaphore();
        webFrame.postCountdown();
    }
}
