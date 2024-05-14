package PR.Pip.ro;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

public class Devazut {
	OkHttpClient client = new OkHttpClient();

	Request request = new Request.Builder()
	  .url("https://api.tranzy.dev/v1/opendata/vehicles")
	  .get()
	  .addHeader("X-Agency-Id", "1")
	  .addHeader("Accept", "application/json")
	  .addHeader("X-API-KEY", "UdgWKaz1hA3y1Ubw2C779cLFvxuRcBa7bN8X4uRa")
	  .build();
	
	public void request(File file) throws IOException {
		Response response = this.client.newCall(this.request).execute();
        // Handle the response here
        ResponseBody responseBody = response.body();
        String jsonString = responseBody.string();

        FileWriter writer = new FileWriter(file);
        writer.write(jsonString);
        writer.close();
        System.out.println("Content written to the file successfully.");
	}
}
