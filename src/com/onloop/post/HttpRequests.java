package com.onloop.post;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.util.Base64;
import android.util.Log;

/**
 * Helper class with methods for HTTP POST and GET requests.
 */
public class HttpRequests {
	private HttpClient client = new DefaultHttpClient();
	private String responseBody = null;
	private HttpResponse response = null;
	private String failedResponse = "Check Connection or the URL entered.\n<Format: URL - http://www.reddit.com\nParams - key1=value1&key2=value2&...&keyN=valueN>";

	public String postRequest(String... inputs) {
    	HttpPost post = new HttpPost(inputs[0]);

		try {
	        List<NameValuePair> pairs = new ArrayList<NameValuePair>();

	        for (String param: inputs[1].split("&")) {
	        	String[] eachPair = null;
	        	if (param.contains("=")) {
	        		eachPair = param.split("=");
    	        	pairs.add(new BasicNameValuePair(eachPair[0], eachPair[1]));
	        	}
	        }

  	        post.setEntity(new UrlEncodedFormEntity(pairs));

  	        if (inputs[2].contains(":")) {
  	        	byte[] encoded = null;
	        		encoded = inputs[2].getBytes("UTF-8");
	        	  	String encoding = Base64.encodeToString(encoded, Base64.DEFAULT);
	        	  	post.setHeader("Authorization", "Basic " + encoding);
  	        }

	        this.response = this.client.execute(post);
	        HttpEntity responseEntity = this.response.getEntity();

	        if (responseEntity != null) {
    	    	this.responseBody = EntityUtils.toString(responseEntity);
    	    }

        } catch (Exception e) {
            this.responseBody = this.failedResponse;
        }

        return this.responseBody;

	}

	public String getRequest(String... inputs) {
		try {
		    String getURL = inputs[0];
		    HttpGet get = new HttpGet(getURL);
		    this.response = this.client.execute(get);
		    HttpEntity resEntityGet = this.response.getEntity();
		    if (resEntityGet != null) {
		        // do something with the response
		        this.responseBody = EntityUtils.toString(resEntityGet);
		    }
		} catch (Exception e) {
		    this.responseBody = this.failedResponse;
		}
		return this.responseBody;

	}
}
