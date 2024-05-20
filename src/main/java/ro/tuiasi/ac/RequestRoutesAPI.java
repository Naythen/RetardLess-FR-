package ro.tuiasi.ac;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

public class RequestRoutesAPI {
    OkHttpClient client = new OkHttpClient();

    com.squareup.okhttp.Request requestRoutes = new com.squareup.okhttp.Request.Builder()
            .url("https://api.tranzy.ai/v1/opendata/routes")
            .get()
            .addHeader("X-Agency-Id", "1")
            .addHeader("Accept", "application/json")
            .addHeader("X-API-KEY", "UdgWKaz1hA3y1Ubw2C779cLFvxuRcBa7bN8X4uRa")
            .build();

    public void requestRoutes(File file) throws IOException {
        Response response = this.client.newCall(this.requestRoutes).execute();
        // Handle the response here
        ResponseBody responseBody = response.body();
        String jsonString = responseBody.string();

        FileWriter writer = new FileWriter(file);
        writer.write(jsonString);
        writer.close();
        System.out.println("Content written to the file successfully.");
    }

    public static void timedrequestRoutes(int x) throws InterruptedException, IOException {
        int displayMinutes = 0;
        long starttime = System.currentTimeMillis();
        String s = System.getProperty("user.dir") + "/Request/reqRoutes";
        System.out.println("Timer:");
        while (x != 0) {

            TimeUnit.SECONDS.sleep(1);
            long timepassed = System.currentTimeMillis() - starttime;
            long secondspassed = timepassed / 1000;

            if (secondspassed == 3) {

                secondspassed = 0;
                starttime = System.currentTimeMillis();

                String temp = s + Integer.toString(displayMinutes) + ".json";
                File file = new File(temp);

                RequestRoutesAPI d = new RequestRoutesAPI();
                d.requestRoutes(file);
            }
            if ((secondspassed % 3) == 0) {
                displayMinutes++;
                x--;
            }

            System.out.println(displayMinutes + "::" + secondspassed);
        }

    }
    public static void main(String[] args) {
        try {

            RequestRoutesAPI.timedrequestRoutes(1);
            String DirectoryPath = System.getProperty("user.dir") + "\\Request";
//            String inputFilePath = DirectoryPath + "\\reqRoutes0.json";
//            String outputFilePath = DirectoryPath + "\\outputRoutes.json";
//            String depouFilePath = DirectoryPath + "\\depou.json";
//            String miscareFilePath = DirectoryPath + "\\miscare.json";

//            JsonBuilder builder = new JsonBuilder(inputFilePath);
//
//            builder.processAllObjects();

//            builder.saveToFile(outputFilePath, "full");
//            builder.saveToFile(miscareFilePath, "miscare");
//            builder.saveToFile(depouFilePath, "depou");

//            System.out.println("numar depou : " + builder.getDepouCount());
//            System.out.println("numar miscare : " + builder.getMiscareCount());

            System.out.println("JSON has been successfully modified and saved.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
