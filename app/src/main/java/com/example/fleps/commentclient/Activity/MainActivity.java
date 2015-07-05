package com.example.fleps.commentclient.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.crashlytics.android.Crashlytics;
import com.example.fleps.commentclient.Activity.AddCommentActivity;
import com.example.fleps.commentclient.Activity.AllCommentsActivity;
import com.example.fleps.commentclient.Activity.MyCommentsActivity;
import com.example.fleps.commentclient.R;
import io.fabric.sdk.android.Fabric;


public class MainActivity extends Activity implements View.OnClickListener {

    Button addCommentBTN, allCommentsBTN, myCommentsBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        addCommentBTN = (Button) findViewById(R.id.addCommentBTN);
        addCommentBTN.setOnClickListener(this);
        allCommentsBTN = (Button) findViewById(R.id.allCommentsBTN);
        allCommentsBTN.setOnClickListener(this);
        myCommentsBTN = (Button) findViewById(R.id.myCommentsBTN);
        myCommentsBTN.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.addCommentBTN:
                intent =  new Intent(this, AddCommentActivity.class);
                startActivity(intent);
                break;
            case R.id.allCommentsBTN:
                intent = new Intent(this, AllCommentsActivity.class);
                startActivity(intent);
                break;
            case R.id.myCommentsBTN:
                intent = new Intent(this, MyCommentsActivity.class);
                startActivity(intent);
                break;
        }
    }
}