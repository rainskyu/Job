package uk.ac.newcastle.dre_ip.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import uk.ac.newcastle.dre_ip.R;
import uk.ac.newcastle.dre_ip.extras.ShowMsg;

/**
 * The fragment of selecting printer.
 */
public class Printer_Fragment extends Fragment{


    private Context mContext = null;
    private ArrayList<HashMap<String, String>> mPrinterList = null;
    private SimpleAdapter mPrinterListAdapter = null;
    private FilterOption mFilterOption = null;
    private ArrayList<Map<String, String>> item = null;
    private String printerAddress;


    /**
     * New instance printer fragment.
     *
     * @return the printer fragment
     */
    public static Printer_Fragment newInstance() {
        Printer_Fragment fragment = new Printer_Fragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_printer, container, false);

        mContext = getContext();

        Button button = (Button) view.findViewById(R.id.btnRestart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnRestart:
                        restartDiscovery();
                        break;

                    default:
                        // Do nothing
                        break;
                }
            }
        });

        Button noPrinter = (Button)view.findViewById(R.id.noPrinter) ;
        noPrinter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                    SharedPreferences sharedPref = getActivity().getSharedPreferences("selectedconnection", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("printerAddress", "<NONE>");
                    editor.commit();
                }
        });

        mPrinterList = new ArrayList<>();
        item = new ArrayList<>();

        mPrinterListAdapter = new SimpleAdapter(mContext, mPrinterList, R.layout.list_at,
                new String[]{"PrinterName", "Target"},
                new int[]{R.id.PrinterName, R.id.Target});
        final ListView list = (ListView) view.findViewById(R.id.lstReceiveData);
        list.setAdapter(mPrinterListAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            HashMap<String, String> item = mPrinterList.get(position);
                                            printerAddress = item.get("Target");
                                            SharedPreferences sharedPref = getActivity().getSharedPreferences("selectedconnection", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPref.edit();
                                            editor.putString("printerAddress", printerAddress);
                                            editor.commit();
                                            Toast.makeText(getContext(), "The printer " + item.get("PrinterName") + " has been selected.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
        );

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



    /**
     * If there is no printer listed after 30 seconds, then restart the discovery
     */
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

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        try {
//            target = (PrinterAddress) context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString() + " must implement PrinterAddress");
//        }
//    }

//    public interface PrinterAddress{
//        void getPrinterAddress(String address);
//
//    }


}
