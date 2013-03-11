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
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.content.ClipData;
import android.content.ClipboardManager;

import com.onloop.post.HttpRequests;
import com.onloop.post.R.menu;

/**
 * Initial Main Activity Class.
 */
public class MainActivity extends Activity {
	private TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.textView = (TextView) findViewById(R.id.postResponse);
    }

    /**
     * Executes a post request to the user-specified url.
     */
    class executePost extends AsyncTask<String, Void, String> {
        @Override
		protected String doInBackground(String... posturl) {
        	HttpRequests request = new HttpRequests();
        	String responseText = null;

            if (posturl[3] == "GET") {
            	responseText = request.getRequest(posturl);
            	return responseText;
            } else if (posturl[3] == "POST") {
            	responseText = request.postRequest(posturl);
            }

            return responseText;
       }

    	@Override
		protected void onPostExecute(String result) {
    		//MainActivity.this.textView.setMovementMethod(new ScrollingMovementMethod());
    		MainActivity.this.textView.setText(result);
       	}
    }

    /**
    * Gets the user input from the EditText and sends it as a post request.
    *
    * @param view
    */
   	public void postData(View view) {
		executePost task = new executePost();

   		MainActivity.this.textView.setText("In Progress...");

   		EditText userInput = (EditText)findViewById(R.id.postEntry);
   		EditText urlParams = (EditText)findViewById(R.id.postParams);
   		EditText authParams = (EditText)findViewById(R.id.postAuth);

		RadioGroup reqGroup = (RadioGroup) findViewById(R.id.reqGroup);
 	    int typeOfReq  = reqGroup.getCheckedRadioButtonId();
   	    View selectedButton = reqGroup.findViewById(typeOfReq);
   	    int index = reqGroup.indexOfChild(selectedButton);

   	    String reqType = null;
   	    if (index == 0) {
			reqType = "GET";
        } else if (index == 1) {
   	        reqType = "POST";
        }

        String userInputString = userInput.getText().toString();
        String urlParamString = urlParams.getText().toString();
        String authParamString = authParams.getText().toString();

        task.execute(new String[] { userInputString, urlParamString, authParamString, reqType });
    }
   	
   	/**
   	 * Copies view content to Clipboard.
   	 */
   	public void copyToClipboard(View view) {
   		String textContent = this.textView.getText().toString();
   		ClipboardManager clipObj = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
   		ClipData clipContent = ClipData.newPlainText("HTML Response", textContent);
   		clipObj.setPrimaryClip(clipContent);
   	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
