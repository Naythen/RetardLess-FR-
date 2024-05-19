package PR.Pip.ro;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static PR.Pip.ro.Request.timedrequest;

/**
 * 
 *
 */
public class App 
{
    public static void main(String[] args) throws InterruptedException, IOException {
    timedrequest(5);
    }

}
