package ro.tuiasi.ac;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

public class Request {
	OkHttpClient client = new OkHttpClient();

	com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
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
	public static void timedrequest(int x) throws InterruptedException, IOException {
		int displayMinutes=0;
		long starttime=System.currentTimeMillis();
		System.out.println("Timer:");
		while(x!=0)
		{
			String s ="D:\\Facultate\\An 3\\Sem II\\PIP proiect\\FreshBeginning\\RetardLess-FR-\\Request\\req";
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
			x--;
		}

	}
}
