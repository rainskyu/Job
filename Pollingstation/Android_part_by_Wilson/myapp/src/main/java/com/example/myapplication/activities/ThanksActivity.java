package com.example.myapplication.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.myapplication.R;

public class ThanksActivity extends AppCompatActivity{

    private Activity activity;
    private Intent intent;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks);
        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        activity = this;
        intent = getIntent();

        Button nextVoter = (Button) findViewById(R.id.thanks_button);

        nextVoter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextIntent = new Intent(activity, PasscodeActivity.class);
                nextIntent.putExtra("title",intent.getExtras().getString("title"));
                nextIntent.putExtra("serverAddress",intent.getExtras().getString("serverAddress"));
                nextIntent.putExtra("printerAddress",intent.getExtras().getString("printerAddress"));
                nextIntent.putExtra("electionID",intent.getExtras().getString("electionID"));
                nextIntent.putExtra("password",intent.getExtras().getString("password"));
                nextIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(nextIntent);
                activity.finish();
            }
        });


    }

}
