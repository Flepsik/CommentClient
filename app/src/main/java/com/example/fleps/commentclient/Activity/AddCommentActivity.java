package com.example.fleps.commentclient.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fleps.commentclient.Model.Comment;

import com.example.fleps.commentclient.R;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Created by Fleps_000 on 04.07.2015.
 */
public class AddCommentActivity extends Activity implements View.OnClickListener {
    private static String url_add_comment = "http://192.168.56.1:8080/add";
    Button sendButton;
    EditText editName, editSurname, editComment;
    TextView nameTW, surnameTW, commentTW;
    private ProgressDialog dialog;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        activity = this;
        sendButton = (Button) findViewById(R.id.sendBTN);
        sendButton.setOnClickListener(this);
        editName = (EditText) findViewById(R.id.editName);
        editSurname = (EditText) findViewById(R.id.editSurname);
        editComment = (EditText) findViewById(R.id.editComment);
        nameTW = (TextView) findViewById(R.id.nameTW);
        surnameTW = (TextView) findViewById(R.id.surnameTW);
        commentTW = (TextView) findViewById(R.id.commentTW);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sendBTN:
                String name = String.valueOf(editName.getText());
                String surname = String.valueOf(editSurname.getText());
                String comment = String.valueOf(editComment.getText());
                new RequestTask().execute(name, surname, Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID), comment);
                /*if (checkName(name) && checkSurname(surname) && checkComment(comment)) {
                    Comments.addComment(new Comment(
                            name
                            , surname
                            , Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID)
                            , comment));
                */

                break;
        }
    }

    private boolean isEmpty(String name) {
        if (name.equals("")) return true;
        return false;
    }

    private boolean checkName(String name) {
        if (isEmpty(name)) {
            nameTW.setTextColor(Color.RED);
            return false;
        }
        nameTW.setTextColor(Color.BLACK);
        return true;
    }

    private boolean checkSurname(String surname) {
        if (isEmpty(surname)) {
            surnameTW.setTextColor(Color.RED);
            return false;
        }
        surnameTW.setTextColor(Color.BLACK);
        return true;
    }

    private boolean checkComment(String comment) {
        if (isEmpty(comment)) {
            commentTW.setTextColor(Color.RED);
            return false;
        }
        commentTW.setTextColor(Color.BLACK);
        return true;
    }


    class RequestTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", params[0]);
                jsonObject.put("surname", params[1]);
                jsonObject.put("deviceID", params[2]);
                jsonObject.put("comment", params[3]);

                DefaultHttpClient httpclient = new DefaultHttpClient();
                HttpPost postMethod = new HttpPost(url_add_comment);
                StringEntity se = new StringEntity(jsonObject.toString());
                postMethod.setEntity(se);
                postMethod.setHeader("Content-type", "application/json");
                ResponseHandler responseHandler = new BasicResponseHandler();
                httpclient.execute(postMethod, responseHandler);
            } catch (Exception e) {
                System.out.println("Exp=" + e);
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(activity, "Something went wrong, check internet connection", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
            super.onPostExecute(result);
            Intent intent = new Intent(activity, AllCommentsActivity.class);
            startActivity(intent);
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(AddCommentActivity.this);
            dialog.setMessage("Sending...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(true);
            dialog.show();
            super.onPreExecute();
        }
    }

}
