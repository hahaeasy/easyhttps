package com.hahaeasy.easyhttps;

import org.apache.http.util.ByteArrayBuffer;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Base64;

/**
 * Created by tonyxue on 11/15/16.
 */
public class HttpsClient {

	/**
	 * HTTPS GET
	 * @param urlString
	 * @param user
	 * @param password
	 * @return results from GET, null: failed
	 */
	public static String get(String urlString, String user, String password) {
		try {
			String encoding = Base64.getEncoder().encodeToString((user + ":" + password).getBytes
					("UTF-8"));

			URL url = new URL(urlString);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty  ("Authorization", "Basic " + encoding);
			connection.setHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session)
				{
					return true;
				}
			});

			InputStream is = connection.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayBuffer baf = new ByteArrayBuffer(1024 * 100);		// with 100K bytes to start
			byte [] blockBuffer = new byte[1024];		// each block 1K

			int len = 0;
			while((len = bis.read(blockBuffer)) > 0){
				baf.append(blockBuffer, 0, len);
			}

			return new String(baf.toByteArray(), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * HTTPS post
	 * @param urlString
	 * @param user
	 * @param password
	 * @param jsonText
	 * @return status of http response, -1 failed
	 */
	public static int post(String urlString, String user, String password, String jsonText) {
		try {
			String encoding = Base64.getEncoder().encodeToString((user + ":" + password).getBytes
					("UTF-8"));

			URL url = new URL(urlString);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty  ("Authorization", "Basic " + encoding);
			connection.setHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session)
				{
					return true;
				}
			});

			connection.setDoOutput(true);
			OutputStream out = connection.getOutputStream();
			out.write(jsonText.getBytes("UTF-8"));
			out.flush();
			out.close();
			return connection.getResponseCode();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

}
