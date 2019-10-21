package com.example.myapplication.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.extras.HttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class ServerFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {


    private EditText serverNameValue, serverAddressValue;
    private Button serverInfoSubmit, serverTest, serverClear;
    private ListView serverListView;
    private ArrayAdapter<String> mServerListAdapter;
    private ArrayList<Map<String, String>> item;
    private ArrayList<String> mServerList;
    private String serverAddress;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;


    public static ServerFragment newInstance() {
        ServerFragment fragment = new ServerFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.server_fragment, container, false);

        serverNameValue = (EditText) view.findViewById(R.id.server_nameValue);
        serverAddressValue = (EditText) view.findViewById(R.id.server_addressValue);
        serverInfoSubmit = (Button) view.findViewById(R.id.server_submit);
        serverTest = (Button) view.findViewById(R.id.server_connectionTest);
        serverClear = (Button) view.findViewById(R.id.server_clear);

        item = new ArrayList<>();
        mServerList = new ArrayList<>();


        mServerListAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mServerList);
        serverListView = (ListView) view.findViewById(R.id.server_listValue);
        serverListView.setAdapter(mServerListAdapter);
        getPreferences();

        serverInfoSubmit.setOnClickListener(this);
        serverTest.setOnClickListener(this);
        serverClear.setOnClickListener(this);
        serverListView.setOnItemClickListener(this);
        serverListView.setOnItemLongClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.server_submit:
                String name, address;
                name = serverNameValue.getText().toString();
                address = serverAddressValue.getText().toString();

                if(!Patterns.WEB_URL.matcher(address).matches()){
                    Toast.makeText(getContext(),"Please enter a valid server address",Toast.LENGTH_SHORT).show();
                    break;
                }

                sharedPref = getActivity().getSharedPreferences("server", Context.MODE_PRIVATE);
                editor = sharedPref.edit();
                Map<String, String> in = new HashMap<>();
                in.put(name, address);
                for (String s : in.keySet()) {
                    editor.putString(s, in.get(s));
                }
                editor.commit();
                getPreferences();
                serverNameValue.setText("");
                serverAddressValue.setText("");
                serverNameValue.requestFocus();
                break;

            case R.id.server_connectionTest:
                sharedPref = getActivity().getSharedPreferences("selectedconnection", Context.MODE_PRIVATE);
                String serveraddress = sharedPref.getString("serverAddress", "");
                if (serveraddress.equals("")){
                    Toast.makeText(getContext(),"Please select a server",Toast.LENGTH_SHORT).show();
                    break;
                }

                connectionTest(view);


                break;

            case R.id.server_clear:
                sharedPref = getActivity().getSharedPreferences("server", Context.MODE_PRIVATE);
                editor = sharedPref.edit();
                editor.clear();
                editor.commit();
                getPreferences();
                mServerListAdapter.notifyDataSetChanged();
                break;

            default:
                //Do nothing
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
        String temp = serverListView.getItemAtPosition(position).toString().trim();
        for (Map<String, String> t : item) {
            if (t.get(temp) != null) {
                serverAddress = t.get(temp);
                break;
            }
        }

        sharedPref = getActivity().getSharedPreferences("selectedconnection", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putString("serverAddress", serverAddress);
        editor.commit();
        Toast.makeText(getContext(), "The server " + temp + " has been selected.", Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Are you sure you want to delete this?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String temp = serverListView.getItemAtPosition(position).toString().trim();
                sharedPref = getActivity().getSharedPreferences("server", Context.MODE_PRIVATE);
                editor = sharedPref.edit();
                editor.remove(temp);
                editor.commit();
                getPreferences();
                mServerListAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
            }
        });


        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
        return true;
    }


    private void getPreferences() {
        item.clear();
        mServerList.clear();
        sharedPref = getActivity().getSharedPreferences("server", Context.MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPref.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Map<String, String> z = new HashMap<>();
            z.put(entry.getKey(), entry.getValue().toString());
            item.add(z);
            mServerList.add(entry.getKey());
            mServerListAdapter.notifyDataSetChanged();
        }
    }

    private void connectionTest(View v){
        sharedPref = getActivity().getSharedPreferences("selectedconnection", Context.MODE_PRIVATE);
        String serveraddress = sharedPref.getString("serverAddress", "");
        String path = "http://" + serveraddress + ":8080/app/connectionTest";
        System.out.println(path);
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Testing, please wait...");
        dialog.setCancelable(false);
        dialog.show();

        HttpUtils.sendHttpRequest(path, new okhttp3.Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String responseData = response.body().string();
                Log.e("responseData", responseData);
                if (responseData.equals("true")) {

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }

                    Looper.prepare();
                    Toast.makeText(getContext(), "Connection Success", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }else{
                    Looper.prepare();
                    Toast.makeText(getContext(), "Some thing wrong with server \n Please try again after a while", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                Looper.prepare();
                Toast.makeText(getContext(), "The Server has no response \n Please check your network setting and try again", Toast.LENGTH_SHORT).show();
                Looper.loop();
                Log.e("okhttpOnFailer", "connection fail");
                e.printStackTrace();
            }
        });

    }
}
