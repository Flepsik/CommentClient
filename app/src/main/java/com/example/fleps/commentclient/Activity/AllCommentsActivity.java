package com.example.fleps.commentclient.Activity;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fleps.commentclient.Adapters.MyAdapter;
import com.example.fleps.commentclient.Model.Comment;
import com.example.fleps.commentclient.Parser.JSONParser;
import com.example.fleps.commentclient.R;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fleps_000 on 04.07.2015.
 */
public class AllCommentsActivity extends Activity implements View.OnClickListener {
    Activity activity;
    private static String url_all_comments = "http://192.168.56.1:8080/get/all";
    private static final String TAG_SUCCESS = "success";
    public static final String TAG_COMMENTS = "comments";
    private static final String TAG_DEVICEID = "deviceID";
    private static final String TAG_COMMENT = "comment";
    private static final String TAG_NAME = "name";
    private static final String TAG_SURNAME = "surname";
    private ProgressDialog pDialog;
    ListView lv;
    ArrayList<Comment> comments;
    JSONParser jParser = new JSONParser();
    JSONArray jComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_comments);
        lv = (ListView) findViewById(R.id.listView);
        activity = this;
        comments = new ArrayList<>();
        new LoadAllComments().execute();
    }

    class LoadAllComments extends AsyncTask<String, String, String> {

        /**
         * Перед началом фонового потока Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AllCommentsActivity.this);
            pDialog.setMessage("Loading. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * Получаем все продукт из url
         */
        protected String doInBackground(String... args) {
            // Будет хранить параметры
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // получаем JSON строк с URL
            JSONObject json = jParser.makeHttpRequest(url_all_comments, "GET", params);
            if(json == null) {
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(activity.getApplicationContext(), "Something went wrong, check internet connection", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(activity, MainActivity.class);
                        startActivity(intent);
                    }
                });
            } else {
                Log.d("All Comments: ", json.toString());
                try {
                    jComments = json.getJSONArray(TAG_COMMENTS);
                    for (int i = 0; i < jComments.length(); i++) {
                        JSONObject c = jComments.getJSONObject(i);
                        comments.add(new Comment(c.getString(TAG_NAME)
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

        /**
         * После завершения фоновой задачи закрываем прогрес диалог
         * *
         */
        protected void onPostExecute(String file_url) {
            // закрываем прогресс диалог после получение все продуктов
            pDialog.dismiss();
            // обновляем UI форму в фоновом потоке
            runOnUiThread(new Runnable() {
                public void run() {
                    lv.setAdapter(new MyAdapter(activity, comments));
                }
            });

        }


    }

    public void onClick(View view) {

    }
}
