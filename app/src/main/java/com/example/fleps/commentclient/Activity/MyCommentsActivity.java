package com.example.fleps.commentclient.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fleps.commentclient.Adapters.MyAdapter;
import com.example.fleps.commentclient.Model.Comment;
import com.example.fleps.commentclient.Parser.JSONParser;
import com.example.fleps.commentclient.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fleps_000 on 04.07.2015.
 */
public class MyCommentsActivity extends Activity implements View.OnClickListener {

    Activity activity;
    private static String url_all_comments = "http://192.168.56.1:8080/get";
    private static final String TAG_SUCCESS = "success";
    public static final String TAG_COMMENTS = "comments";
    private static final String TAG_DEVICEID = "deviceID";
    private static final String TAG_COMMENT = "comment";
    private static final String TAG_NAME = "name";
    private static final String TAG_SURNAME = "surname";
    private ProgressDialog pDialog;
    ListView lv;
    ArrayList<Comment> myComments;
    JSONParser jParser = new JSONParser();
    JSONArray jComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_comments);
        lv = (ListView) findViewById(R.id.listView);
        activity = this;
        myComments = new ArrayList<>();
        new LoadAllComments().execute(Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));

    }

    class LoadAllComments extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MyCommentsActivity.this);
            pDialog.setMessage("Loading. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", args[0]));
            JSONObject json = jParser.makeHttpRequest(url_all_comments, "GET", params);
            Log.d("All Comments: ", json.toString());
            if(json == null) {
                Toast.makeText(getApplicationContext(), "Something went wrong, check internet connection", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    jComments = json.getJSONArray(TAG_COMMENTS);
                    for (int i = 0; i < jComments.length(); i++) {
                        JSONObject c = jComments.getJSONObject(i);
                        myComments.add(new Comment(c.getString(TAG_NAME)
                                , c.getString(TAG_SURNAME)
                                , c.getString(TAG_DEVICEID),
                                c.getString(TAG_COMMENT)
                        ));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }


        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    lv.setAdapter(new MyAdapter(activity, myComments));
                }
            });

        }
    }
    public void onClick(View view) {
    }
}

