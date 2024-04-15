package ro.tuiasi.ac.Proiect_PIP;

import java.io.IOException;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class Devazut {
	OkHttpClient client = new OkHttpClient();

	Request request = new Request.Builder()
	  .url("https://api.tranzy.dev/v1/opendata/vehicles")
	  .get()
	  .addHeader("X-Agency-Id", "1")
	  .addHeader("Accept", "application/json")
	  .addHeader("X-API-KEY", "UdgWKaz1hA3y1Ubw2C779cLFvxuRcBa7bN8X4uRa")
	  .build();
}
