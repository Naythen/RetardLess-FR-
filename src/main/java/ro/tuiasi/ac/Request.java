package ro.tuiasi.ac;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

public class Request {
	/**
	 *
	 */
	private OkHttpClient client = new OkHttpClient();
	/**
	 *
	 */
	private com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
			.url("https://api.tranzy.dev/v1/opendata/vehicles")
			.get()
			.addHeader("X-Agency-Id", "1")
			.addHeader("Accept", "application/json")
			.addHeader("X-API-KEY", "UdgWKaz1hA3y1Ubw2C779cLFvxuRcBa7bN8X4uRa")
			.build();

	/**
	 *
	 */
	private com.squareup.okhttp.Request requestRoutes = new com.squareup.okhttp.Request.Builder()
			.url("https://api.tranzy.ai/v1/opendata/routes")
			.get()
			.addHeader("X-Agency-Id", "1")
			.addHeader("Accept", "application/json")
			.addHeader("X-API-KEY", "UdgWKaz1hA3y1Ubw2C779cLFvxuRcBa7bN8X4uRa")
			.build();

	/**
	 *
	 * @param file
	 * @throws IOException
	 */
	public void request(final File file) throws IOException {
		Response response = this.client.newCall(this.request).execute();
		// Handle the response here
		ResponseBody responseBody = response.body();
		String jsonString = responseBody.string();

		FileWriter writer = new FileWriter(file);
		writer.write(jsonString);
		writer.close();
		System.out.println("Request content written to the file successfully.");
	}

	/**
	 *
	 * @param file
	 * @throws IOException
	 */
	public void requestRoutes(final File file) throws IOException {
		Response response = this.client.newCall(this.requestRoutes).execute();
		// Handle the response here
		ResponseBody responseBody = response.body();
		String jsonString = responseBody.string();

		FileWriter writer = new FileWriter(file);
		writer.write(jsonString);
		writer.close();
		System.out.println("Request routs content written to the file successfully.");
	}

	/**
	 *
	 * @param x
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void timedrequest(final int x) throws InterruptedException, IOException {
		int copiex = x;
		int displayMinutes = 0;
		long starttime = System.currentTimeMillis();
		String s = System.getProperty("user.dir") + "/Request/req";
		System.out.println("Timer:");
		while (x != 0) {

			TimeUnit.SECONDS.sleep(1);
			long timepassed = System.currentTimeMillis() - starttime;
			long secondspassed = timepassed / 1000;

			if (secondspassed == 3) {

				secondspassed = 0;
				starttime = System.currentTimeMillis();

				String temp1 = s + Integer.toString(displayMinutes) + ".json";
				File file1 = new File(temp1);

				String temp2 = s + "Routs" + Integer.toString(displayMinutes) + ".json";
				File file2 = new File(temp2);

				Request d = new Request();
				d.request(file1);

				Request f = new Request();
				f.requestRoutes(file2);
			}
			if ((secondspassed % 3) == 0) {
				displayMinutes++;
				copiex--;
			}

			System.out.println(displayMinutes + "::" + secondspassed);
		}

	}
}
