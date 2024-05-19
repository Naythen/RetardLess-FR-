package ro.tuiasi.ac;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static ro.tuiasi.ac.Request.timedrequest;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) throws InterruptedException, IOException {
        timedrequest(100);

    }

}
