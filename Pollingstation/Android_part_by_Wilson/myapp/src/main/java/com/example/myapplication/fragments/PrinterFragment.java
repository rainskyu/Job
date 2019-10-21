package com.example.myapplication.fragments;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Context;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.discovery.DeviceInfo;
import com.epson.epos2.discovery.Discovery;
import com.epson.epos2.discovery.DiscoveryListener;
import com.epson.epos2.discovery.FilterOption;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.myapplication.extras.Print;
import com.example.myapplication.extras.ShowMsg;


public class PrinterFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener {

    private Context mContext = null;
    private ArrayList<HashMap<String, String>> mPrinterList = null;
    private SimpleAdapter mPrinterListAdapter = null;
    private FilterOption mFilterOption = null;
    private ArrayList<Map<String, String>> item = null;
    private Button printTest;

    public static PrinterFragment newInstance() {
        PrinterFragment fragment = new PrinterFragment();
        return fragment;
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.print_fragment, container, false);

        mContext = getContext();

        Button printerSearch = (Button) view.findViewById(R.id.printer_search);
        printerSearch.setOnClickListener(this);
        printTest = (Button) view.findViewById(R.id.printer_test);
        printTest.setOnClickListener(this);

        mPrinterList = new ArrayList<>();
        item = new ArrayList<>();

        mPrinterListAdapter = new SimpleAdapter(mContext, mPrinterList, R.layout.liat_at,
                new String[]{"PrinterName", "Target"},
                new int[]{R.id.PrinterName, R.id.Target});
        final ListView list = (ListView) view.findViewById(R.id.lstReceiveData);
        list.setAdapter(mPrinterListAdapter);
        list.setOnItemClickListener(this);

        mFilterOption = new FilterOption();
        mFilterOption.setDeviceType(Discovery.TYPE_PRINTER);
        mFilterOption.setEpsonFilter(Discovery.FILTER_NAME);
        try {
            Discovery.start(mContext, mFilterOption, mDiscoveryListener);
        } catch (Exception e) {
            ShowMsg.showException(e, "start", mContext);
        }

        return view;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {

            case R.id.printer_search:
                restartDiscovery();
                break;

            case R.id.printer_test:
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("selectedconnection", Context.MODE_PRIVATE);
                Print print = new Print(sharedPreferences.getString("printerAddress", ""),"",createContent(),getActivity(),mContext,"test");

                printTest.setClickable(false);
                if (!print.runPrinterSequence()) {
                    printTest.setClickable(true);
                }
                break;

            default:
                // Do nothing
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HashMap<String, String> item = mPrinterList.get(position);
        String printerAddress = item.get("Target");
        SharedPreferences sharedPref = getActivity().getSharedPreferences("selectedconnection", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("printerAddress", printerAddress);
        editor.commit();
        Toast.makeText(getContext(), "The printer " + item.get("PrinterName") + " has been selected.", Toast.LENGTH_SHORT).show();
    }

    private String createContent() {
        StringBuilder textData = new StringBuilder();

        textData.append("----Printing Test Receipt ----\n");
        textData.append("Election ID:            000001\n");
        textData.append("Ballot ID:              000001\n");
        textData.append("Question ID:            000001\n");
        textData.append("Confirmation Code:     CVSA-WA\n");


        return textData.toString();
    }


    private void restartDiscovery() {
        while (true) {
            try {
                Discovery.stop();
                break;
            } catch (Epos2Exception e) {
                if (e.getErrorStatus() != Epos2Exception.ERR_PROCESSING) {
                    ShowMsg.showException(e, "stop", mContext);
                    return;
                }
            }
        }

        mPrinterList.clear();
        mPrinterListAdapter.notifyDataSetChanged();

        try {
            Discovery.start(mContext, mFilterOption, mDiscoveryListener);
        } catch (Exception e) {
            ShowMsg.showException(e, "stop", mContext);
        }
    }

    private DiscoveryListener mDiscoveryListener = new DiscoveryListener() {
        @Override
        public void onDiscovery(final DeviceInfo deviceInfo) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public synchronized void run() {
                    HashMap<String, String> temp = new HashMap<>();
                    Map<String, String> z = new HashMap<>();
                    temp.put("PrinterName", deviceInfo.getDeviceName());
                    temp.put("Target", deviceInfo.getTarget());
                    z.put(deviceInfo.getDeviceName(), deviceInfo.getDeviceName());
                    mPrinterList.add(temp);
                    item.add(z);
                    mPrinterListAdapter.notifyDataSetChanged();
                }
            });
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();

        while (true) {
            try {
                Discovery.stop();
                break;
            } catch (Epos2Exception e) {
                if (e.getErrorStatus() != Epos2Exception.ERR_PROCESSING) {
                    break;
                }
            }
        }

        mFilterOption = null;
    }


}
