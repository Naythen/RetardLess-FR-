package PR.Pip.ro;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

public class RequestTest {

    private File tempFile;

    @Before
    public void setUp() throws IOException {
        tempFile = File.createTempFile("test", ".json");
    }

    @After
    public void tearDown() {
        tempFile.delete();
    }

    @Test
    public void testRequest() throws IOException {
        Request myClass = new Request();
        myClass.request(tempFile);

        Response response = myClass.client.newCall(myClass.request).execute();
        ResponseBody responseBody = response.body();
        String jsonString = responseBody.string();

        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(tempFile))) {
            String line;
            while ((line = br.readLine())!= null) {
                contentBuilder.append(line);
                contentBuilder.append(System.lineSeparator());
            }
        } catch (IOException e) {
            Assert.assertFalse("An error occurred while reading the file: " + e.getMessage(),true);
        }
        String contentTempFile = contentBuilder.toString();

        contentTempFile = contentTempFile.trim();
        jsonString = jsonString.trim();

        Assert.assertTrue(jsonString.equals(contentTempFile));
    }
}
