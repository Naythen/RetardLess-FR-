package ro.tuiasi.ac.Proiect_PIP;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
//import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
//        System.out.println( "Hello World!" );
//        System.out.println("Sanatate la neamuri!~!");
//        System.out.println("Salutari!");
//        System.out.println("Sanatate la neamurile noastre!~!");
//        
//        System.out.println("aici merge");
        Devazut d = new Devazut();
        
        try {
            Response response = d.client.newCall(d.request).execute();
            // Handle the response here
            ResponseBody responseBody = response.body();
            String jsonString = responseBody.string();
            if (responseBody != null) {
                System.out.println(jsonString);
            }
            
            File file = new File("D:\\work\\1302B\\pipproiect\\testulmeu2.json");
            FileWriter writer = new FileWriter(file);
            writer.write(jsonString);
            writer.close();
            System.out.println("Content written to the file successfully.");
        } 
   	 catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, e.g., log it, show an error message, etc.
        }
    }
}
