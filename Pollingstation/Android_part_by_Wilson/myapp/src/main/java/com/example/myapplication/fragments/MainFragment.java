package com.example.myapplication.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.myapplication.R;
import com.example.myapplication.activities.PasscodeActivity;
import com.example.myapplication.extras.HttpUtils;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class MainFragment extends Fragment implements View.OnClickListener{

    private TextView serverAddress, printerAddress;
    private EditText password,electionID;
    private String path;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.main_fragment, container, false);


        electionID = (EditText) view.findViewById(R.id.electionID);
        password =(EditText) view.findViewById(R.id.password);
        serverAddress = (TextView)view.findViewById(R.id.main_serverAddress);
        printerAddress = (TextView)view.findViewById(R.id.main_printerAddress);
        Button login = (Button) view.findViewById(R.id.start);
        login.setOnClickListener(this);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("selectedconnection", Context.MODE_PRIVATE);
        if(!sharedPreferences.getString("serverAddress","").equals(""))
        {serverAddress.setText(sharedPreferences.getString("serverAddress",""));}
        if(!sharedPreferences.getString("printerAddress","").equals(""))
        {printerAddress.setText(sharedPreferences.getString("printerAddress",""));}
        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.start:
                if(checkSetting()){
                    login(view);
                }
                break;

            default:
                // Do nothing
                break;
        }

    }

    private boolean checkSetting(){
        if (serverAddress.getText().toString().equals("NONE")){
            Toast.makeText(getContext(),"Please select a server",Context.MODE_PRIVATE).show();
            return false;
        }
        if (printerAddress.getText().toString().equals("NONE")){
            Toast.makeText(getContext(),"Please select a printer",Context.MODE_PRIVATE).show();
            return false;
        }
        if  (electionID.getText().toString().trim().isEmpty()){
            Toast.makeText(getContext(), "ElectionID can not be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.getText().toString().trim().isEmpty()){
            Toast.makeText(getContext(), "Password can not be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }



    private void login(View v){
        final String text_electionID = electionID.getText().toString().trim();
        final String text_password = password.getText().toString().trim();

        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Logging in, please wait...");
        dialog.setCancelable(false);
        dialog.show();

        final JSONObject jsonObj = new JSONObject();
        jsonObj.put("electionID",text_electionID);
        jsonObj.put("password",text_password);

        final String serveraddress = serverAddress.getText().toString();
        path = "http://"+serveraddress+":8080/app/confirmPassword";
        HttpUtils.sendHttpRequestPost(path, jsonObj.toString(),new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                Looper.prepare();
                Toast.makeText(getContext(), "Connection failed.\nPlease check you network", Toast.LENGTH_SHORT).show();
                Looper.loop();
                Log.e("okhttpOnFailer", "connection fail");
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {

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
                }catch (NullPointerException e){
                    Looper.prepare();
                    Toast.makeText(getContext(), "Some thing wrong with server \n Please try again after a while", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }

                nextPage(responseJSON);
            }
        });
    }

    private void nextPage(JSONObject responseJSON){
        if (responseJSON.get("result").equals("true")){
            Intent intent = new Intent(getActivity(), PasscodeActivity.class);
            intent.putExtra("title",responseJSON.get("title").toString());
            intent.putExtra("serverAddress",serverAddress.getText().toString());
            intent.putExtra("printerAddress",printerAddress.getText().toString());
            intent.putExtra("electionID",electionID.getText().toString().trim());
            intent.putExtra("password",password.getText().toString().trim());
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            getActivity().startActivity(intent);
            getActivity().finish();
        }else{
            Looper.prepare();
            Toast.makeText(getContext(), "Election ID or Password invalid", Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }

}
