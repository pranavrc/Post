package com.onloop.post;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.textView = (TextView) findViewById(R.id.postResponse);
    }

    class executePost extends AsyncTask<String, Void, String> {
        @Override
		protected String doInBackground(String... posturl) {
        	HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(posturl[0]);
            HttpResponse response = null;
            String responseBody = null;
            Log.d("MyApp", "HIIIII");
         
            try {
    	        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
    	        pairs.add(new BasicNameValuePair("blogurlinput", "droid"));
    	        pairs.add(new BasicNameValuePair("blogtitleinput", "foo"));
    	        pairs.add(new BasicNameValuePair("blogdescinput", "bar"));
    	        pairs.add(new BasicNameValuePair("bloglayoutinput", "1"));
    	        pairs.add(new BasicNameValuePair("password", "strand1prp2"));
     
    	        post.setEntity(new UrlEncodedFormEntity(pairs));

    	        response = client.execute(post);
    	        HttpEntity responseEntity = response.getEntity();
    	               
    	        if (responseEntity != null) {
    	        	responseBody = EntityUtils.toString(responseEntity);
    	        }
    	        Log.d("MyApp", responseBody);
            } catch (Exception e) {
                Log.d("MyApp","Exception was encountered");
            }
            
            return responseBody;
       }

    	@Override
		protected void onPostExecute(String result) {
    		// TODO Auto-generated method stub.
    		MainActivity.this.textView.setMovementMethod(new ScrollingMovementMethod());
    		MainActivity.this.textView.setText(result);
       	}
    }
    
   	  public void postData(View view) {
   		  executePost task = new executePost();
   		  EditText userInput = (EditText)findViewById(R.id.postEntry);
          String userInputString = userInput.getText().toString();
   		  task.execute(new String[] { userInputString });
     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}