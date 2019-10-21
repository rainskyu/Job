package com.example.myapplication.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.myapplication.R;
import com.example.myapplication.extras.HttpUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class PasscodeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView iddisplay;
    private EditText passcode;
    private Button start;
    private Context context;
    private Activity activity;
    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);
        getSupportActionBar().hide();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        context = this;
        activity = this;
        intent = getIntent();
        iddisplay = (TextView) findViewById(R.id.passcode_iddisplay);
        passcode = (EditText) findViewById(R.id.passcode_passcodeValue);
        start = (Button) findViewById(R.id.passcode_start);

        String elctionTitle = intent.getExtras().getString("title");
        iddisplay.setText(elctionTitle);

        start.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.passcode_start:

                if (checkPasscode()) {
                    verify();
                }

                break;

            default:
                //Do Noting
                break;
        }
    }

    private boolean checkPasscode() {
        if (passcode.getText().toString().isEmpty()) {
            Toast.makeText(context, "Passcode cannot be empty", Context.MODE_PRIVATE).show();
            return false;
        }
        return true;
    }

    private void verify() {
        String serveraddress = getIntent().getExtras().getString("serverAddress");
        final String text_passcode = passcode.getText().toString().trim();

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Verifying, please wait...");
        dialog.setCancelable(false);
        dialog.show();

        final JSONObject jsonObj = new JSONObject();
        jsonObj.put("electionID", intent.getExtras().getString("electionID"));
        jsonObj.put("password", intent.getExtras().getString("password"));
        jsonObj.put("passcode", text_passcode);

        String path = "http://" + serveraddress + ":8080/app/confirmPasscode";

        HttpUtils.sendHttpRequestPost(path, jsonObj.toString(), new okhttp3.Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();
                Log.e("responseData", responseData);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

               JSONObject responseJSON = null;
                try {
                    responseJSON = new JSONObject(JSONObject.parseObject(responseData));
                    responseJSON.get("result");
                } catch (Exception e) {
                    Looper.prepare();
                    Toast.makeText(context, "Some thing wrong with server \n Please try again after a while", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }

                nextPage(responseJSON);

            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                Looper.prepare();
                Toast.makeText(context, "Connection failed.\nPlease check you network", Toast.LENGTH_SHORT).show();
                Looper.loop();
                Log.e("okhttpOnFailer", "connection fail");
                e.printStackTrace();
            }
        });
    }

    private void nextPage(JSONObject responseJSON){
        if (responseJSON.get("result").equals("true")) {
            String[] options = JSON.parseObject(responseJSON.get("options").toString(), String[].class);
            Intent nextIntent = new Intent(activity, VotingActivity.class);
            nextIntent.putExtra("title", intent.getExtras().getString("title"));
            nextIntent.putExtra("serverAddress", intent.getExtras().getString("serverAddress"));
            nextIntent.putExtra("printerAddress", intent.getExtras().getString("printerAddress"));
            nextIntent.putExtra("electionID", intent.getExtras().getString("electionID"));
            nextIntent.putExtra("password", intent.getExtras().getString("password"));
            nextIntent.putExtra("passcode", passcode.getText().toString().trim());
            nextIntent.putExtra("options", options);
            nextIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(nextIntent);
            activity.finish();

        } else {
            Looper.prepare();
            Toast.makeText(context, "Passcode invalid", Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }
}
