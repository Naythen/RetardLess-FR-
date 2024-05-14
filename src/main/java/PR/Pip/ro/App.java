package PR.Pip.ro;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) throws InterruptedException, IOException {
        boolean x=true;
        int displayMinutes=0;
        long starttime=System.currentTimeMillis();
        System.out.println("Timer:");
        while(x)
        {
            String s ="D:\\JavaProjects\\untitled1\\Request\\req";
            Request d=new Request();
            TimeUnit.SECONDS.sleep(1);
            long timepassed=System.currentTimeMillis()-starttime;
            long secondspassed=timepassed/1000;
            if(secondspassed==60)
            {
                secondspassed=0;
                starttime=System.currentTimeMillis();
                s=s.concat(Integer.toString(displayMinutes));
                s=s.concat(".json");
                File file = new File(s);
                d.request(file);
            }
            if((secondspassed%60)==0)
                displayMinutes++;

            System.out.println(displayMinutes+"::"+secondspassed);
        }

    }

}
