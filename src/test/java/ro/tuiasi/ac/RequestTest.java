package ro.tuiasi.ac;

import junit.framework.TestCase;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

public class RequestTest extends TestCase {

    /**
     * Tests the request method by making a request and verifying that the response is written to a file
     * @throws IOException
     */
    public void testRequest() throws IOException {
        Request request = new Request();
        File file = new File("test.json");
        Response response = request.client.newCall(request.request).execute();
        request.request(file, response);
        assertTrue(file.exists());
    }

    /**
     * Tests the requestRoutes method by making a request and verifying that the response is written to a file
     * @throws IOException
     */
    public void testRequestRoutes() throws IOException {
        Request request = new Request();
        File file = new File("test_routes.json");
        request.requestRoutes(file);
        assertTrue(file.exists());
    }

    /**
     * Tests the RequestVehicule method by making a request and verifying that the response is written to a file
     * @throws IOException
     */
    public void testRequestVehicule() throws IOException {
        Request request = new Request();
        File file = new File("test_vehicule.json");
        request.requestVehicule(file);
        assertTrue(file.exists());
    }

    /**
     * Tests the RequestStops method by making a request and verifying that the response is written to a file
     * @throws IOException
     */
    public void testRequestStops() throws IOException {
        Request request = new Request();
        File file = new File("test_stops.json");
        request.requestStops(file);
        assertTrue(file.exists());
    }

    /**
     * Tests the RequestStopsTimes method by making a request and verifying that the response is written to a file
     * @throws IOException
     */
    public void testRequestStopsTimes() throws IOException {
        Request request = new Request();
        File file = new File("test_stops_times.json");
        request.requestStopsTimes(file);
        assertTrue(file.exists());
    }

    /**
     * Tests the TimesRequest method by making a fixed number of requests and verifying that the responses are written to the corresponding files
     * @throws IOException
     * @throws InterruptedException
     */
    public void testTimedrequest() throws IOException, InterruptedException {
        Request request = new Request();
        int x = 4;
        request.timedrequest(x);
        // Verify that the files were created successfully
        for (int i = 0; i < x; i++) {
            File file1 = new File("Request/reqVehicule" + i + ".json");
            File file2 = new File("Request/reqRouts" + i + ".json");
            File file3 = new File("Request/reqStops" + i + ".json");
            File file4 = new File("Request/reqStopTimes" + i + ".json");
            assertTrue(file1.exists());
            assertTrue(file2.exists());
            assertTrue(file3.exists());
            assertTrue(file4.exists());
        }
    }
}