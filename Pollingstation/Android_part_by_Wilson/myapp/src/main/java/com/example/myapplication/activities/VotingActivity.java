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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSONObject;
import com.example.myapplication.R;
import com.example.myapplication.extras.HttpUtils;
import com.example.myapplication.extras.Print;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

import okhttp3.Call;
import okhttp3.Response;

public class VotingActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView candidateList;
    private TextView iddisplay;
    private Context context;
    private Activity activity;
    private Intent intent;
    private String electionID, serverAddress, printerAddress, passcode, candidate,electionTitle,ballotID;
    private String[] candidates;
    private Button cancel, confirm;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting);
        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        context = this;
        activity = this;
        intent = getIntent();
        electionID = intent.getExtras().getString("electionID");
        serverAddress = intent.getExtras().getString("serverAddress");
        printerAddress = intent.getExtras().getString("printerAddress");
        passcode = intent.getExtras().getString("passcode");
        candidates = intent.getExtras().getStringArray("options");

        iddisplay = (TextView) findViewById(R.id.voting_iddisplay);
        electionTitle = intent.getExtras().getString("title");
        iddisplay.setText(electionTitle);

        candidateList = (ListView) findViewById(R.id.candidate_name);
        updateCandidate();
        candidateList.setOnItemClickListener(this);

        cancel = (Button) findViewById(R.id.voting_cancel);
        confirm = (Button) findViewById(R.id.voting_confirm);

        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);

        cancel.setEnabled(false);
        confirm.setEnabled(false);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.voting_cancel:
                stageTwo("cancel");
                candidateList.setEnabled(true);
                cancel.setEnabled(false);
                confirm.setEnabled(false);
                break;
            case R.id.voting_confirm:
                stageTwo("confirm");
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        candidate = candidateList.getItemAtPosition(position).toString();
        System.out.println(candidate);
        candidateList.setEnabled(false);
        stageOne();
        cancel.setEnabled(true);
        confirm.setEnabled(true);
    }

    private void updateCandidate(){
        ArrayList optionList = new ArrayList<>(Arrays.asList(candidates));
        ArrayAdapter<String> optionsAdapter = new ArrayAdapter<String>(this,R.layout.voting_list,R.id.candidate_name,optionList);
        candidateList.setAdapter(optionsAdapter);
    }

    private void stageOne(){
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Fetching receipt, please wait...");
        dialog.setCancelable(false);
        dialog.show();

        final JSONObject jsonObj = new JSONObject();
        jsonObj.put("type","stageOne");
        jsonObj.put("electionID",electionID);
        jsonObj.put("passcode",passcode);
        jsonObj.put("candidate",candidate);

        String path = "http://"+serverAddress+":8080/app/voteStageOne";
        HttpUtils.sendHttpRequestPost(path, jsonObj.toString(),new okhttp3.Callback(){

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                Log.e("responseData", responseData);


                JSONObject responseJSON = null;
                try {
                    responseJSON = new JSONObject(JSONObject.parseObject(responseData));
                    responseJSON.get("result");
                } catch (Exception e) {
                    Looper.prepare();
                    Toast.makeText(context, "Some thing wrong with server \n Please try again after a while", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }

                String confirmecode = responseJSON.getString("confirmationCode").toUpperCase();
                ballotID = responseJSON.getString("ballotID");

                StringBuilder content = new StringBuilder();
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                df.setTimeZone(TimeZone.getTimeZone("Europe/London"));

                content.append("     "+df.format(new Date())+"     \n");
                content.append("-------Voting Stage One--------\n");
                content.append("\n");
                content.append("Election ID:"+  String.format("%19s", electionID)+"\n");
                content.append("Ballot ID:"+  String.format("%22s", ballotID +"\n"));
                content.append("Confirmation Code:             \n");
                content.append("\n");
                content.append(confirmecode.substring(0, 5) + " " + confirmecode.substring(5, 10) + " " + confirmecode.substring(10, 15) + " " + confirmecode.substring(15, 20) + " " + confirmecode.substring(20, 25) +"\n\n" +
                        confirmecode.substring(25, 30) + " " + confirmecode.substring(30, 35) + " " + confirmecode.substring(35, 40)+ " " + confirmecode.substring(40, 45) + " "+ confirmecode.substring(45, 50) + "\n");


                Print print = new Print(printerAddress,electionTitle,content.toString(),activity,context,"stageOne");

                if (print.runPrinterSequence()){
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }

            }

            @Override
            public void onFailure(Call call, IOException e) {
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

    private void stageTwo(String audit){
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Fetching receipt, please wait...");
        dialog.setCancelable(false);
        dialog.show();

        final JSONObject jsonObj = new JSONObject();
        jsonObj.put("type","stageTwo");
        jsonObj.put("ballotID",ballotID);
        jsonObj.put("electionID",electionID);
        jsonObj.put("passcode",passcode);
        jsonObj.put("result",audit);
        if (audit.equals("cancel")){
            jsonObj.put("candidate",candidate);
        }

        String path = "http://"+serverAddress+":8080/app/voteStageTwo";
        HttpUtils.sendHttpRequestPost(path, jsonObj.toString(), new okhttp3.Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                Log.e("responseData", responseData);

                JSONObject responseJSON = null;
                try {
                    responseJSON = new JSONObject(JSONObject.parseObject(responseData));
                    responseJSON.getString("result");
                } catch (Exception e) {
                    Looper.prepare();
                    Toast.makeText(context, "Some thing wrong with server \n Please try again after a while", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
                String result = responseJSON.getString("result");
                String ballotResult;
                StringBuilder content = new StringBuilder();

                if (result.equals("cancel")){
                    content.append("-------Voting Stage Two--------\n");
                    content.append("\n");
                    content.append("Canceled candidate name \n");
                    content.append("\n");
                    content.append(candidate +"\n");

                    ballotResult = "BALLOT CANCELLED";
                }else if(result.equals("confirm")) {
                    content.append("-------Voting Stage Two--------\n");
                    content.append("\n");

                    ballotResult = "BALLOT CONFIRMED";
                }else{
                    ballotResult = "ERROR";
                }

                Print print = new Print(printerAddress,ballotResult,content.toString(),activity,context,"stageTwo");

                if (print.runPrinterSequence()){
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }

                if (result.equals("confirm")){
                    nextPage();
                }

            }

            @Override
            public void onFailure(Call call, IOException e) {
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


    private void nextPage(){
        Intent nextIntent = new Intent(activity, ThanksActivity.class);
        nextIntent.putExtra("title",electionTitle);
        nextIntent.putExtra("serverAddress",serverAddress);
        nextIntent.putExtra("printerAddress",printerAddress);
        nextIntent.putExtra("electionID",electionID);
        nextIntent.putExtra("password",intent.getExtras().getString("password"));
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(nextIntent);
        activity.finish();
    }

}
