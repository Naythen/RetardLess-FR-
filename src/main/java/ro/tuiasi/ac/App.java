
package ro.tuiasi.ac;
import java.io.IOException;
import static ro.tuiasi.ac.Request.timedrequest;

/**
 *
 */
public class App {
    /**
     * @param args
     * @throws InterruptedException
     * @throws IOException
     */
    public static void main(final String[] args)
            throws InterruptedException, IOException {
        timedrequest(100);

    }

}
